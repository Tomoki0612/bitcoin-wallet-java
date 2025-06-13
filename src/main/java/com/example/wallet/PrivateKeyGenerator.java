package com.example.wallet;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrivateKeyGenerator {
    private final BigInteger privateKey;

    public PrivateKeyGenerator() {
        SecureRandom random = new SecureRandom();
        byte[] privateKeyBytes = new byte[32];
        random.nextBytes(privateKeyBytes);
        this.privateKey = new BigInteger(1, privateKeyBytes);
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public String getPrivateKeyHex() {
        return String.format("%064x", privateKey);
    }
}