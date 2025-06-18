package com.example.wallet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class BalanceChecker {

    public static long getBalance(String address) throws Exception {
        //String apiUrl = "https://mempool.space/api/address/" + address + "/utxo";// Mainnet API URL
        String apiUrl = "https://mempool.space/testnet4/api/address/" + address + "/utxo";// Testnet API URL
        URI uri = URI.create(apiUrl);
        URL url = uri.toURL();
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

        JSONArray utxos = new JSONArray(response.toString());

        long total = 0;
        for (int i = 0; i < utxos.length(); i++) {
            JSONObject utxo = utxos.getJSONObject(i);
            total += utxo.getLong("value");
        }

        return total; // satoshi
    }

    public static void main(String[] args) {
        String address = "myuU1m82E6JEbnuJAuVSPHdPk2xBHmW2oH";
        try {
            long balanceSatoshi = getBalance(address);
            double balanceBTC = balanceSatoshi / 100_000_000.0;
            System.out.println("残高: " + balanceBTC + " BTC (" + balanceSatoshi + " satoshi)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
