package com.karmahostage.ethcrack.decryption.internal;

import com.karmahostage.ethcrack.domain.EthKeystore;

public interface KeyDecrypter {
    boolean isCorrectKey(EthKeystore keyProtected, String auth);
}
