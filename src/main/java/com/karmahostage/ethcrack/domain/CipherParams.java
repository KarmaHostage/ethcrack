package com.karmahostage.ethcrack.domain;

public class CipherParams {
    private String iv;

    public String getIv() {
        return iv;
    }

    public CipherParams setIv(String iv) {
        this.iv = iv;
        return this;
    }
}
