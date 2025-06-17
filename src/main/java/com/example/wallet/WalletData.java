package com.example.wallet;

public class WalletData {
    public final String privateKey;
    public final String publicKey;
    public final String address;

    public WalletData(String privateKey, String publicKey, String address) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.address = address;
    }
}