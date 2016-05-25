package com.karmahostage.ethcrack;

import com.karmahostage.ethcrack.decryption.internal.KeyDecrypter;
import com.karmahostage.ethcrack.decryption.internal.V3KeyDecrypter;
import com.karmahostage.ethcrack.domain.EthKeystore;

public class ThreadedDecryption implements Runnable {

    private final EthKeystore ethKeystore;
    private final String password;
    private KeyDecrypter keyDecrypter;

    public ThreadedDecryption(EthKeystore ethKeystore, String password) {
        this.ethKeystore = ethKeystore;
        this.password = password;
        this.keyDecrypter = new V3KeyDecrypter();
    }

    @Override
    public void run() {
        boolean correctKey = keyDecrypter.isCorrectKey(ethKeystore, password);
        System.out.println(password);
        if (correctKey) {
            System.out.println("key found: " + password);
            System.exit(0);
        }
    }
}
