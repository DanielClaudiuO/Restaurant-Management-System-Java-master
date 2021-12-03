/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagement;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receipt {

    String orderId;
    String staff;
    String orderTime;
    String orderDate;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> qty = new ArrayList<>();
    ArrayList<Double> price = new ArrayList<>();
    Double profit;
    Double stotal;
    Double taxtotal;
    Double ftotal;
    Scanner input = new Scanner(System.in);

    public Receipt(String Oid, String staff, String orderTime, String orderDate, ArrayList<String> name, ArrayList<Integer> qty, ArrayList<Double> price, Double profit, Double stotal, Double taxtotal, Double ftotal) {

        this.orderId = Oid;
        this.staff = staff;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.profit = profit;
        this.stotal = stotal;
        this.taxtotal = taxtotal;
        this.ftotal = ftotal;

    }

    public Receipt() {

    }

    public String getOrderId() {
        return orderId;
    }

    public String getStaff() {
        return staff;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public ArrayList<Integer> getQuantity() {
        return qty;
    }

    public ArrayList<Double> getPrice() {
        return price;
    }

    public Double getProfit() {
        return profit;
    }

    public Double getSubtotal() {
        return stotal;
    }

    public Double getTaxtotal() {
        return taxtotal;
    }

    public Double getFtotal() {
        return ftotal;
    }

    public String timecalc() {

        String curr, status;
        int diff;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date datey = new Date();
        curr = formatter.format(datey);
        Date date1 = null;
        try {
            date1 = formatter.parse(orderTime);
        } catch (ParseException ex) {
            Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date date2 = null;
        try {
            date2 = formatter.parse(curr);
        } catch (ParseException ex) {
            Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
        }
        long difference = date2.getTime() - date1.getTime();
        diff = (int) (long) (difference / 1000);

        if (diff < 120) {
            status = "Order Received";
        } else if (diff >= 120 && diff < 240) {
            status = "Cooking";
        } else {
            status = "Delivered";
        }

        return (status);
    }

    public void Order() {

        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println(" ");
        System.out.println("Order ID: " + orderId + "                         " + orderTime);
        System.out.println("Staff Name: " + staff + "                    " + orderDate);
        int count = 1;
        System.out.printf("%2s %20s %5s %10s", "No", "NAME", "QTY", "PRICE");
        System.out.println();
        for (int i = 0; i < name.size(); i++) {
            System.out.println("-------------------------------------------------");
            System.out.format("%2d %20s %5s %10s", count, (name.get(i)), (qty.get(i)), df.format((price.get(i)) * (qty.get(i))));
            System.out.println();
            count++;
        }
        System.out.println("\n-------------------------------------------------");
        System.out.println("\nSUBTOTAL                       " + df.format(stotal));
        System.out.println("VAT TAX                   " + df.format(taxtotal));
        System.out.println("Total                          " + df.format(ftotal));
        System.out.println("\n-------------------------------------------------");
        System.out.println("Order Status: " + timecalc());
        System.out.println(" ");

    }

}
