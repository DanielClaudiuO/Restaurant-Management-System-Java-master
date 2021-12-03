/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagement;

import java.util.ArrayList;
import java.util.Scanner;


public class Menu {

    Scanner input = new Scanner(System.in);
    private ArrayList<Item> item = new ArrayList<>();


    public Menu(ArrayList<Item> items) {
        this.item = items;
    }

    Menu() {

    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public ArrayList<Item> editItem() {

        String chooseP = "y";
        int index;
        while (!"n".equalsIgnoreCase(chooseP)) {
            int found = 0;
            double nprice, nprofit;
            System.out.println("Enter the item ID: ");
            String cusId = input.next();
            for (int i = 0; i < item.size(); i++) {

                if (cusId.equalsIgnoreCase(item.get(i).getId())) {
                    index = i;
                    System.out.println(" ");
                    System.out.println("You have selected " + item.get(index).getName());
                    System.out.println("Current Price: " + item.get(index).getPrice() + "\nCurrent Profit:" + item.get(index).getProfit());
                    System.out.println("Enter The New Price For This Item: ");
                    nprice = input.nextDouble();
                    item.get(index).price = nprice;
                    System.out.println("Enter The New Profit For This Item: ");
                    nprofit = input.nextDouble();
                    item.get(index).profit = nprofit;

                } else {
                    found++;
                }

            }

            if (found == item.size()) {
                System.out.println("Item didn't exist!");
            }

            System.out.println("Do you want to edit price of another item from here ? (y/n)");
            chooseP = input.next();

        }

        return (item);

    }

    public ArrayList<Order> chooseItem() {

        ArrayList<Order> ordertemp = new ArrayList<>();
        ArrayList<String> Cname = new ArrayList<>();
        ArrayList<Integer> Cqty = new ArrayList<>();
        ArrayList<Double> Cprice = new ArrayList<>();
        ArrayList<Double> Cprofit = new ArrayList<>();

        String chooseP = "y";
        int index;
        while (!"n".equalsIgnoreCase(chooseP)) {
            int found = 0;
            System.out.println("Enter the item ID: ");
            String cusId = input.next();
            int qty;
            for (int i = 0; i < item.size(); i++) {

                if (cusId.equalsIgnoreCase(item.get(i).getId())) {
                    index = i;
                    System.out.println("How many " + item.get(index).getName() + " you want ? :");
                    qty = input.nextInt();
                    Cname.add(item.get(index).getName());
                    Cprice.add(item.get(index).getPrice());
                    Cprofit.add(item.get(index).getProfit());
                    Cqty.add(qty);

                } else {
                    found++;
                }

            }

            if (found == item.size()) {
                System.out.println("Item didn't exist!");
            }

            System.out.println("Do you want to add another item from here ? (y/n)");
            chooseP = input.next();

        }
        Order dummy = new Order(Cname, Cqty, Cprice, Cprofit);
        ordertemp.add(dummy);

        return (ordertemp);

    }

    public void displayMenu() {

        System.out.println("Select from Options or Enter ‘B’ to go back.");
        System.out.println("1. Pizza");
        System.out.println("2. Pasta");
        System.out.println("3. Meat");
        System.out.println("4. Side");
        System.out.println("5. Sauce");
        System.out.println("6. Desert");
        System.out.println("7. Drink");
        System.out.println("8. Cart");
        System.out.println("B. Go Back");
    }

    public void displayItem() {
        System.out.println("");
        int count = 1;
        System.out.printf("%2s %10s %15s %10s", "No", "ID", "NAME", "PRICE");
        System.out.println();
        for (int i = 0; i < item.size(); i++) {
            System.out.format("%2d %10s %15s %10s", count, item.get(i).getId(), item.get(i).getName(), item.get(i).getPrice()); //formatting
            System.out.println();
            count++;
        }
    }

}
