package com.example.wallet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.json.JSONObject; // Gradleで org.json ライブラリ追加が必要

public class BalanceChecker {

    public static long getBalance(String address) throws Exception {
        String apiUrl = "https://blockstream.info/api/address/" + address;
        URI uri = URI.create(apiUrl);         // 文字列からURIを作成
        URL url = uri.toURL();                // URIからURLに変換
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());

        long funded = json.getJSONObject("chain_stats").getLong("funded_txo_sum");
        long spent = json.getJSONObject("chain_stats").getLong("spent_txo_sum");

        return funded - spent; // 残高（satoshi）
    }

    public static void main(String[] args) {
        String address = "あなたのビットコインアドレス"; // 例: "1PMycacnJaSqwwJqjawXBErnLsZ7RkXUAs"
        try {
            long balanceSatoshi = getBalance(address);
            double balanceBTC = balanceSatoshi / 100_000_000.0;
            System.out.println("残高: " + balanceBTC + " BTC (" + balanceSatoshi + " satoshi)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
