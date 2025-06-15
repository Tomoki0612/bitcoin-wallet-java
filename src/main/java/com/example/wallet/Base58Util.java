package com.example.wallet;

import java.math.BigInteger;

public class Base58Util {

    private static final String BASE58_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    public static String base58Encode(byte[] input) {
        // 1. 入力バイト列をBigInteger化（符号なしなのでnew BigInteger(1, input)）
        BigInteger value = new BigInteger(1, input);

        // 2. エンコード結果を貯めるStringBuilder
        StringBuilder sb = new StringBuilder();

        // 3. valueが0になるまで繰り返す
        while (value.compareTo(BigInteger.ZERO) > 0) {
            // valueを58で割り、商と余りを取得
            BigInteger[] divmod = value.divideAndRemainder(BigInteger.valueOf(58));
            value = divmod[0]; // 商
            int digit = divmod[1].intValue(); // 余り

            // 余りをBase58の文字に変換して追加
            sb.append(BASE58_ALPHABET.charAt(digit));
        }

        // 4. 入力バイト列の先頭の0バイトは'1'に変換（Base58の仕様）
        for (int i = 0; i < input.length && input[i] == 0; i++) {
            sb.append('1');
        }

        // 5. 逆順にして返す
        return sb.reverse().toString();
    }

    // 簡単な動作テスト用 main
    public static void main(String[] args) {
        byte[] test = {0, 0, 1, 2, 3, 4, 5};
        String encoded = base58Encode(test);
        System.out.println(encoded);
    }
}
