package com.karmahostage.ethcrack.decryption.internal;

import com.karmahostage.ethcrack.domain.EthCrypto;
import com.karmahostage.ethcrack.domain.EthKeystore;
import com.lambdaworks.crypto.PBKDF;
import com.lambdaworks.crypto.SCrypt;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Mac;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class V3KeyDecrypter implements KeyDecrypter{

    @Override
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
            int c = parseInt(crypto.getKdfparams().getC());
            String prf = crypto.getKdfparams().getPrf();

            if (!"hmac-sha256".equals(prf)) {
                throw new IllegalArgumentException(String.format("Unsupported PBKDF2 PRF: %s", prf));
            }

            try {
                return PBKDF.pbkdf2("HmacSHA256", authArray, salt, c, dkLen);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new IllegalArgumentException("Unable to calculate key for pbkdf implementation");
            }
        } else {
            throw new IllegalArgumentException("kdf not supported");
        }
    }

}
