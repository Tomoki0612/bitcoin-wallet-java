package com.example.wallet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class WalletFileManager {

    private static final String FILE_NAME = "wallet.properties";

    public static void saveWallet(String privateKey, String publicKey, String address) throws IOException {
        Properties props = new Properties();
        props.setProperty("privateKey", privateKey);
        props.setProperty("publicKey", publicKey);
        props.setProperty("address", address);
        try (FileOutputStream out = new FileOutputStream(FILE_NAME)) {
            props.store(out, "Wallet Information");
        }
    }

    public static String[] loadWallet() throws IOException {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(FILE_NAME)) {
            props.load(in);
        }
        String privateKey = props.getProperty("privateKey");
        String publicKey = props.getProperty("publicKey");
        String address = props.getProperty("address");
        return new String[] { privateKey, publicKey, address };
    }
}
