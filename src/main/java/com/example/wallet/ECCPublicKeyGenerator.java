package com.example.wallet;

import java.math.BigInteger;
import java.security.Security;

import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;

public class ECCPublicKeyGenerator {
    public static void main(String[] args) {
        // Bouncy Castleのプロバイダを追加
        Security.addProvider(new BouncyCastleProvider());

        // secp256k1パラメータを取得
        var params = SECNamedCurves.getByName("secp256k1");
        var curve = params.getCurve();
        var G = params.getG();  // 基準点
        var n = params.getN();
        var h = params.getH();

        ECDomainParameters ecParams = new ECDomainParameters(curve, G, n, h);

        // ✅ PrivateKeyGeneratorから秘密鍵を取得
        PrivateKeyGenerator keyGen = new PrivateKeyGenerator();
        BigInteger privKey = keyGen.getPrivateKey();

        // 公開鍵を計算：G × privKey
        ECPoint pubKey = G.multiply(privKey).normalize();

        // 公開鍵の座標
        BigInteger x = pubKey.getAffineXCoord().toBigInteger();
        BigInteger y = pubKey.getAffineYCoord().toBigInteger();

        // 出力
        System.out.println("秘密鍵（hex）: " + keyGen.getPrivateKeyHex());
        System.out.println("公開鍵 X: " + x.toString(16));
        System.out.println("公開鍵 Y: " + y.toString(16));

        // 圧縮形式
        String prefix = y.testBit(0) ? "03" : "02";
        String compressedPubKey = prefix + String.format("%064x", x);
        System.out.println("圧縮公開鍵: " + compressedPubKey);
    }
}
