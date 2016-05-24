package com.karmahostage.ethcrack.domain;

public class KdfParams {
    private String dklen;
    private String n;
    private String p;
    private String r;
    private String salt;

    public String getDklen() {
        return dklen;
    }

    public KdfParams setDklen(String dklen) {
        this.dklen = dklen;
        return this;
    }

    public String getN() {
        return n;
    }

    public KdfParams setN(String n) {
        this.n = n;
        return this;
    }

    public String getP() {
        return p;
    }

    public KdfParams setP(String p) {
        this.p = p;
        return this;
    }

    public String getR() {
        return r;
    }

    public KdfParams setR(String r) {
        this.r = r;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public KdfParams setSalt(String salt) {
        this.salt = salt;
        return this;
    }
}
