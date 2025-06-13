package com.example.wallet;

import java.security.MessageDigest;

public class SHA256Digest {

    /**
     * 任意のテキストをSHA-256でハッシュし、16進数文字列で返す
     */
    public static String hash(String text) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // 動作確認用 main
    public static void main(String[] args) throws Exception {
        String text = "Hello Bitcoin!";
        String hashValue = SHA256Digest.hash(text);
        System.out.println("SHA-256: " + hashValue);
    }
    
}
