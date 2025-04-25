package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OnlineStoreApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Product> inventory = getInventory();
        ArrayList<Product> cart = new ArrayList<>();

//        for (Product p : inventory) {
//            System.out.println(p.getSKU() + " | " + p.getProductName() + " | $" + p.getPrice() + " | " + p.getDepartment());
//        }

        while (true) {
            System.out.println("\nWelcome to Vil, Max and Andrew the Online Store");
            System.out.println("1. Display Products");
            System.out.println("2. Display Cart");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int userChoice = input.nextInt();
            input.nextLine();

            switch (userChoice) {
                case 1:
                    displayProducts(inventory, cart, input);
                    break;
                case 2:
                    displayCart(cart, input);
                    break;
                case 3:
                    System.out.println("See you soon dear guest!");
                    System.exit(userChoice);
                default:
                    System.out.println("Invalid option");
            }
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

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner input) {
        for (Product p : inventory) {
            System.out.println(p.getSKU() + " | " + p.getProductName() + " | $" + p.getPrice() + " | " + p.getDepartment());
        }

        System.out.println("\n1. Search by Department");
        System.out.println("2. Add to Cart");
        System.out.println("3. Go Back");
        System.out.print("Choose an option: ");
        int option = input.nextInt();
        input.nextLine();

        switch (option) {
            case 1:
                System.out.print("\nEnter Department Name: ");
                String dep = input.nextLine();
                for (Product p : inventory) {
                    if (p.getDepartment().equalsIgnoreCase(dep)) {
                        System.out.println(p.getSKU() + " | " + p.getProductName() + " | $" + p.getPrice() + " | " + p.getDepartment());
                    }
                }
                break;
            case 2:
                System.out.print("\nEnter SKU to add to cart: ");
                String sku = input.nextLine();
                for (Product p : inventory) {
                    if (p.getSKU().equalsIgnoreCase(sku)) {
                        cart.add(p);
                        System.out.println("The item " + "'" + p.getProductName() + "'" + " was added to cart");
                        return;
                    }
                }
                System.out.println("Product not found");
                saveCartToFile(cart);
                System.out.println("Added");
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid option");
        }
    }

    public static void displayCart(ArrayList<Product> cart, Scanner input) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty");
            return;
        }

        double total = 0;
        System.out.println("Your cart");
        for (Product p : cart) {
            System.out.println(p.getSKU() + " | " + p.getProductName() + " | $" + p.getPrice());
            total += p.getPrice();
        }

        System.out.printf("Total: $%.2f\n", total);

        System.out.println("\n1. Remove Product");
        System.out.println("2. Check Out");
        System.out.println("3. Go Back");
        System.out.print("Choose an option: ");

        int option = input.nextInt();
        input.nextLine();

        switch (option) {
            case 1:
                System.out.print("\nEnter SKU to remove: ");
                String sku = input.nextLine();
                for (int i = 0; i < cart.size(); i++) {
                    if (cart.get(i).getSKU().equalsIgnoreCase(sku)) {
                        cart.remove(i);
                        System.out.println("Removed from cart");
                        return;
                    }
                }
                System.out.println("Product not found");
                break;
            case 2:
                System.out.println("Thanks for your purchase");
                cart.clear();
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
    public static void saveCartToFile(ArrayList<Product> cart) {
        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/user_cart.csv", true));

            bufWriter.write("SKU|Product Name|Price|Department");
            bufWriter.newLine();

            for (Product p : cart) {
                bufWriter.write(p.getSKU() + "|" + p.getProductName() + "|" + p.getPrice() + "|" + p.getDepartment());
                bufWriter.newLine();
            }

            bufWriter.close();
            System.out.println("The cart saved to 'resources' in 'user_cart.csv'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
