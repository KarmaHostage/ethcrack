package com.karmahostage.ethcrack.decryption;

import com.google.gson.Gson;
import com.karmahostage.ethcrack.decryption.internal.V3KeyDecrypter;
import com.karmahostage.ethcrack.domain.EthKeystore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.fest.assertions.Assertions.assertThat;

public class V3KeyDecrypterTest {

    private static final String CORRECT_PASSWORD = "test";
    private static final String INCORRECT_PASSWORD = "anothertest";

    @Test
    public void testCorrectPassword() throws Exception {
        V3KeyDecrypter v3KeyDecrypter = new V3KeyDecrypter();

        assertThat(v3KeyDecrypter.isCorrectKey(
                getEthKeystoreFile("/keystore/mypassword.json"),
                CORRECT_PASSWORD)
        ).isTrue();
    }

    @Test
    public void testIncorrectPassword() throws Exception {
        V3KeyDecrypter v3KeyDecrypter = new V3KeyDecrypter();

        assertThat(v3KeyDecrypter.isCorrectKey(
                getEthKeystoreFile("/keystore/mypassword.json"),
                INCORRECT_PASSWORD)
        ).isFalse();
    }

    private EthKeystore getEthKeystoreFile(String filename) throws IOException {
        InputStream inputFile = this.getClass().getResourceAsStream(filename);
        BufferedReader actualFileAsReader = new BufferedReader(new InputStreamReader(inputFile));
        return new Gson().getAdapter(EthKeystore.class).fromJson(actualFileAsReader);
    }
}