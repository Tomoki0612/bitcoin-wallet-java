package com.example.wallet;

import java.math.BigInteger;
import java.security.Security;

import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;

public final class ECCPublicKeyGenerator {
    private static final ECDomainParameters ecParams;

    static {
        Security.addProvider(new BouncyCastleProvider());
        var params = SECNamedCurves.getByName("secp256k1");
        var curve = params.getCurve();
        var G = params.getG();
        var n = params.getN();
        var h = params.getH();
        ecParams = new ECDomainParameters(curve, G, n, h);
    }

    // 公開鍵の圧縮形式を返す
    public String generateCompressedPublicKeyHex(BigInteger privateKey) {
        ECPoint pubKey = ecParams.getG().multiply(privateKey).normalize();
        BigInteger x = pubKey.getAffineXCoord().toBigInteger();
        BigInteger y = pubKey.getAffineYCoord().toBigInteger();
        String prefix = y.testBit(0) ? "03" : "02";
        return prefix + String.format("%064x", x);
    }
}