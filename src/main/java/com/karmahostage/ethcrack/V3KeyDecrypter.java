package com.karmahostage.ethcrack;

import com.karmahostage.ethcrack.domain.EthCrypto;
import com.karmahostage.ethcrack.domain.EthKeystore;
import com.lambdaworks.crypto.SCrypt;
import com.lambdaworks.crypto.SCryptUtil;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import java.security.GeneralSecurityException;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class V3KeyDecrypter {

    public boolean isCorrectKey(EthKeystore keyProtected, String auth) {
        if (!"aes-128-ctr".equals(keyProtected.getCrypto().getCipher())) {
            throw new IllegalArgumentException("cipher not supported");
        }

        byte[] mac = Hex.decode(keyProtected.getCrypto().getMac());
        byte[] iv = Hex.decode(keyProtected.getCrypto().getCipherparams().getIv());
        byte[] cipherText = Hex.decode(keyProtected.getCrypto().getCiphertext());
        byte[] derivedKey = getKdfKey(keyProtected.getCrypto(), auth);

        byte[] calculatedMac = keccak256(Arrays.copyOfRange(derivedKey, 16, 32), cipherText);
    
        return Arrays.equals(mac, calculatedMac);
    }

    private byte[] keccak256(byte[] bytes, byte[] cipherText) {
        SHA3.Digest256 digest256 = new SHA3.Digest256();
        digest256.update(bytes);
        digest256.update(cipherText);
        return digest256.digest();
    }

    private byte[] getKdfKey(EthCrypto crypto, String auth) {
        byte[] authArray = auth.getBytes();
        byte[] salt = Hex.decode(crypto.getKdfparams().getSalt());
        int dkLen = parseInt(crypto.getKdfparams().getDklen());

        if ("scrypt".equals(crypto.getKdf())) {
            int n = parseInt(crypto.getKdfparams().getN());
            int r = parseInt(crypto.getKdfparams().getR());
            int p = parseInt(crypto.getKdfparams().getP());
            try {
                return SCrypt.scrypt(authArray, salt, n, r, p, dkLen);
            } catch (GeneralSecurityException e) {
                throw new IllegalArgumentException("unable to derive key");
            }
        } else if ("pbkdf2".equals(crypto.getKdf())) {
            throw new IllegalArgumentException("not yet implemented");
        } else {
            throw new IllegalArgumentException("kdf not supported");
        }
    }

}
