package com.example.wallet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class UtxoFetcher {

    public static void fetchAndPrintUtxos(String address) throws Exception {
        String apiUrl = "https://mempool.space/testnet4/api/address/" + address + "/utxo";

        URL url = new URL(apiUrl);
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
        if (utxos.length() == 0) {
            System.out.println("UTXOは見つかりませんでした。");
            return;
        }

        for (int i = 0; i < utxos.length(); i++) {
            JSONObject utxo = utxos.getJSONObject(i);
            System.out.println("TxID: " + utxo.getString("txid"));
            System.out.println("Vout: " + utxo.getInt("vout"));
            System.out.println("金額: " + utxo.getLong("value") + " satoshi");

            JSONObject status = utxo.getJSONObject("status");
            
            // 最新ブロック高の取得
            URL tipUrl = new URL("https://mempool.space/testnet4/api/blocks/tip/height");
            HttpURLConnection tipCon = (HttpURLConnection) tipUrl.openConnection();
            BufferedReader tipReader = new BufferedReader(new InputStreamReader(tipCon.getInputStream()));
            int latestHeight = Integer.parseInt(tipReader.readLine());
            tipReader.close();
            if (status.getBoolean("confirmed")) {
                int blockHeight = status.getInt("block_height");
                int confirmations = latestHeight - blockHeight + 1;
                System.out.println("確認数: " + confirmations);
            } else {
                System.out.println("確認数: 0（未確認）");
            }
            System.out.println("--------------------");
        }
    }

    public static void main(String[] args) {
        String address = "myuU1m82E6JEbnuJAuVSPHdPk2xBHmW2oH"; // ← ここに自分のアドレスを入れて
        try {
            fetchAndPrintUtxos(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}