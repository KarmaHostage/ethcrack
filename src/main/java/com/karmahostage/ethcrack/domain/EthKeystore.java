package com.karmahostage.ethcrack.domain;

public class EthKeystore {
    private String address;
    private EthCrypto Crypto;
    private String id;
    private String version;

    public String getAddress() {
        return address;
    }

    public EthKeystore setAddress(String address) {
        this.address = address;
        return this;
    }

    public EthCrypto getCrypto() {
        return Crypto;
    }

    public EthKeystore setCrypto(EthCrypto crypto) {
        Crypto = crypto;
        return this;
    }

    public String getId() {
        return id;
    }

    public EthKeystore setId(String id) {
        this.id = id;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public EthKeystore setVersion(String version) {
        this.version = version;
        return this;
    }
}
