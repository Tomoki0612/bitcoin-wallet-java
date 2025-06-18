package com.example.wallet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;

public class AddressGenerator {

    public static String generateP2PKHAddress(byte[] publicKeyBytes) throws Exception {
        // 1. SHA-256
        byte[] sha256 = sha256(publicKeyBytes);

        // 2. RIPEMD-160
        byte[] ripemd160 = ripemd160(sha256);

        // 3. ネットワークバイト追加
        byte[] extendedRipemd160 = new byte[ripemd160.length + 1];
        //extendedRipemd160[0] = 0x00; // Mainネットワークの場合は0x00を使用
        extendedRipemd160[0] = 0x6f; // Testネットワークの場合は0x6fを使用
        System.arraycopy(ripemd160, 0, extendedRipemd160, 1, ripemd160.length);

        // 4. チェックサム（SHA256 x2 → 先頭4バイト）
        byte[] checksum = Arrays.copyOfRange(sha256(sha256(extendedRipemd160)), 0, 4);

        // 5. 全体を連結
        byte[] binaryAddress = new byte[extendedRipemd160.length + 4];
        System.arraycopy(extendedRipemd160, 0, binaryAddress, 0, extendedRipemd160.length);
        System.arraycopy(checksum, 0, binaryAddress, extendedRipemd160.length, 4);

        // 6. Base58エンコード
        return Base58Util.base58Encode(binaryAddress);
    }

    private static byte[] sha256(byte[] input) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256").digest(input);
    }

    private static byte[] ripemd160(byte[] input) {
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(input, 0, input.length);
        byte[] out = new byte[digest.getDigestSize()];
        digest.doFinal(out, 0);
        return out;
    }
}