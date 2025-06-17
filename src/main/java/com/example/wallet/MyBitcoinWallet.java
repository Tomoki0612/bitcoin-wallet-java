package com.example.wallet;

import java.util.Scanner;

/**
 * 主にCLIを通じてウォレット機能を操作するメインクラス。
 * このクラスでは以下の責務を持ちます:
 *  1. ユーザー入力の受け取りとメニュー表示
 *  2. WalletFileManager, BalanceChecker, AddressGenerator などのサービス呼び出し
 *  3. 結果の表示
 * 
 * Java開発のノウハウとして、以下の点を意識しましょう:
 *  - 単一責任の原則(SRP)
 *  - メソッドの分割とテスト可能性
 *  - 適切な例外ハンドリング
 */
public class MyBitcoinWallet {
    private final Scanner scanner = new Scanner(System.in);
    private WalletFileManager fileManager = new WalletFileManager();
    //private BalanceChecker balanceChecker = new BalanceChecker();
    //private AddressGenerator addressGen = new AddressGenerator();

    public static void main(String[] args) {
        new MyBitcoinWallet().run();
    }

    /**
     * メインの実行ループ
     */
    public void run() {
        while (true) {
            printMenu();
            int choice = readChoice();
            try {
                switch (choice) {
                    case 1:
                        createNewWallet();
                        break;
                    case 2:
                        loadExistingWallet();
                        break;
                    case 3:
                        showBalance();
                        break;
                    case 4:
                        showAddress();
                        break;
                    case 5:
                        System.out.println("終了します。");
                        return;
                    default:
                        System.out.println("無効な選択肢です。再度入力してください。");
                }
            } catch (Exception e) {
                System.err.println("エラーが発生しました: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("=== Bitcoin Wallet CLI ===");
        System.out.println("1. 新しいウォレットを作成");
        System.out.println("2. 既存ウォレットを読み込む");
        System.out.println("3. 残高を確認");
        System.out.println("4. アドレスを表示");
        System.out.println("5. 終了");
        System.out.print("選択してください: ");
    }

    private int readChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void createNewWallet() throws Exception {
        // 秘密鍵・公開鍵・アドレスを生成し、ファイルに保存
        WalletData wallet = fileManager.generateAndSave();
        System.out.println("ウォレットを作成しました。アドレス: " + wallet.address);
    }

    private void loadExistingWallet() throws Exception {
        WalletData wallet = fileManager.loadWallet();
        System.out.println("ウォレットを読み込みました。アドレス: " + wallet.address);
    }

    private void showBalance() throws Exception {
        WalletData wallet = fileManager.loadWallet();
        long satoshi = BalanceChecker.getBalance(wallet.address);
        double btc = satoshi / 1e8;
        System.out.println("残高: " + btc + " BTC (" + satoshi + " satoshi)");
    }

    private void showAddress() throws Exception {
        WalletData wallet = fileManager.loadWallet();
        System.out.println("アドレス: " + wallet.address);
    }
}
