package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OnlineStoreApp {
    public static void main(String[] args) {

        ArrayList<Product> inventory = getInventory();

        for (Product p : inventory) {
            System.out.println(p.getSKU() + " | " + p.getProductName() + " | $" + p.getPrice() + " | " + p.getDepartment());
        }
    }
    public static ArrayList<Product> getInventory() {

        ArrayList<Product> inventory = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/products.csv"));

            bufReader.readLine();

            String line;

            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String sku = parts[0];
                String productName = parts[1];
                double price = Double.parseDouble(parts[2]);
                String department = parts[3];

                Product p = new Product(sku, productName, price, department);

                inventory.add(p);
            }

            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }
    public static void createNewInventory(ArrayList<Product> inventory) {
        try {

        } catch () {

        }
    }
}
