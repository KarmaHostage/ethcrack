package com.karmahostage.ethcrack.domain;

public class EthCrypto {
    private String cipher;
    private String ciphertext;
    private CipherParams cipherparams;
    private String kdf;
    private KdfParams kdfparams;
    private String mac;

    public String getCipher() {
        return cipher;
    }

    public EthCrypto setCipher(String cipher) {
        this.cipher = cipher;
        return this;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public EthCrypto setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
        return this;
    }

    public CipherParams getCipherparams() {
        return cipherparams;
    }

    public EthCrypto setCipherparams(CipherParams cipherparams) {
        this.cipherparams = cipherparams;
        return this;
    }

    public String getKdf() {
        return kdf;
    }

    public EthCrypto setKdf(String kdf) {
        this.kdf = kdf;
        return this;
    }

    public KdfParams getKdfparams() {
        return kdfparams;
    }

    public EthCrypto setKdfparams(KdfParams kdfparams) {
        this.kdfparams = kdfparams;
        return this;
    }

    public String getMac() {
        return mac;
    }

    public EthCrypto setMac(String mac) {
        this.mac = mac;
        return this;
    }
}
