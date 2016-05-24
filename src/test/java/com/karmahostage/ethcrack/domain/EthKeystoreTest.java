package com.karmahostage.ethcrack.domain;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.*;

import static org.fest.assertions.Assertions.assertThat;

public class EthKeystoreTest {

    @Test
    public void fromValidFile() throws IOException {
        InputStream inputFile = this.getClass().getResourceAsStream("/keystore/mypassword.json");
        BufferedReader actualFileAsReader = new BufferedReader(new InputStreamReader(inputFile));
        EthKeystore ethKeystore = new Gson().getAdapter(EthKeystore.class).fromJson(actualFileAsReader);
        assertThat(ethKeystore).isNotNull();
    }

}
