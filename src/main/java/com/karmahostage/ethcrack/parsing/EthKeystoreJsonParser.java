package com.karmahostage.ethcrack.parsing;

import com.google.gson.Gson;
import com.karmahostage.ethcrack.domain.EthKeystore;

import java.io.*;

public class EthKeystoreJsonParser {

    public EthKeystore parse(File file) {
        try {
            BufferedReader actualFileAsReader = new BufferedReader(new FileReader(file));
            return new Gson().getAdapter(EthKeystore.class).fromJson(actualFileAsReader);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("unable to parse wallet file");
        }

    }

}
