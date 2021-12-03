/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cart {

    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<Integer> qty = new ArrayList<>();
    private ArrayList<Double> price = new ArrayList<>();
    private ArrayList<Double> taxes = new ArrayList<>();
    Scanner input = new Scanner(System.in);

    public Cart(ArrayList<Order> pzcart, ArrayList<Order> pscart, ArrayList<Order> mtcart, ArrayList<Order> socart, ArrayList<Order> sscart, ArrayList<Order> dscart, ArrayList<Order> bocart) { //arraylist items being passed

        ArrayList<String> iname = new ArrayList<>();
        ArrayList<Integer> iqty = new ArrayList<>();
        ArrayList<Double> iprice = new ArrayList<>();
        ArrayList<Double> itaxes = new ArrayList<>();

        for (int i = 0; i < pzcart.size(); i++) {
            iname.addAll(pzcart.get(i).getOName());
            iqty.addAll(pzcart.get(i).getQuantity());
            iprice.addAll(pzcart.get(i).getPrice());
            itaxes.add(0.09);
        }

        for (int i = 0; i < pscart.size(); i++) {
            iname.addAll(pscart.get(i).getOName());
            iqty.addAll(pscart.get(i).getQuantity());
            iprice.addAll(pscart.get(i).getPrice());
            itaxes.add(0.09);
        }

        for (int i = 0; i < mtcart.size(); i++) {
            iname.addAll(mtcart.get(i).getOName());
            iqty.addAll(mtcart.get(i).getQuantity());
            iprice.addAll(mtcart.get(i).getPrice());
            itaxes.add(0.09);
        }
        for (int i = 0; i < socart.size(); i++) {
            iname.addAll(socart.get(i).getOName());
            iqty.addAll(socart.get(i).getQuantity());
            iprice.addAll(socart.get(i).getPrice());
            itaxes.add(0.09);
        }
        for (int i = 0; i < sscart.size(); i++) {
            iname.addAll(sscart.get(i).getOName());
            iqty.addAll(sscart.get(i).getQuantity());
            iprice.addAll(sscart.get(i).getPrice());
            itaxes.add(0.09);
        }
        for (int i = 0; i < dscart.size(); i++) {
            iname.addAll(dscart.get(i).getOName());
            iqty.addAll(dscart.get(i).getQuantity());
            iprice.addAll(dscart.get(i).getPrice());
            itaxes.add(0.10);
        }
        for (int i = 0; i < bocart.size(); i++) {
            iname.addAll(bocart.get(i).getOName());
            iqty.addAll(bocart.get(i).getQuantity());
            iprice.addAll(bocart.get(i).getPrice());
            if(iname.get(i)== "Lemonade" || iname.get(i)=="Fresh"){
                itaxes.add(0.05);
            }else{
                itaxes.add(0.12);
            }
        }

        this.name = iname;
        this.qty = iqty;
        this.price = iprice;
        this.taxes = itaxes;

    }

    double getSubtotal() {
        double subtotal = 0.0;
        for (int i = 0; i < name.size(); i++) {
            double amt;
            amt = price.get(i) * qty.get(i);
            subtotal = subtotal + amt;
        }

        return (subtotal);
    }

    double getGross() { //calculate gross

        double gross = 0.0;
        for (int i = 0; i < name.size(); i++) {
            double grs;
            grs = taxes.get(i) * qty.get(i);
            gross = gross + grs;
        }

        return (gross);
    }

    public String getTime() { //get the current time

        String jam;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date datey = new Date();
        jam = formatter.format(datey);

        return (jam);
    }

    public String getDate() { //get the today's time'

        String hari;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        hari = formatter.format(today);

        return (hari);
    }

    public Object displayCart(String id, String staff) { 

        DecimalFormat df = new DecimalFormat("#.##");
        String orderTime = getTime(); //getting time
        String orderDate = getDate(); //getting date
        System.out.println("Order ID: " + id + "                         " + orderTime);
        System.out.println("Staff Name: " + staff + "                    " + orderDate);
        int count = 1;
        System.out.printf("%2s %20s %5s %10s", "No", "NAME", "QTY", "PRICE");
        System.out.println();
        double taxtotal = 0;
        for (int i = 0; i < name.size(); i++) {
            System.out.println("-------------------------------------------------");
            System.out.format("%2d %20s %5s %10s", count, name.get(i), qty.get(i), df.format((price.get(i) * qty.get(i))));
            System.out.println();
            count++;
            taxtotal+=(price.get(i)*taxes.get(i))*qty.get(i);
        }
        double stotal = getSubtotal();

        double ftotal = stotal + taxtotal;
        System.out.println("\n-------------------------------------------------");
        System.out.println("\nSUBTOTAL                       " + df.format(stotal));
        System.out.println("VAT TAX                         " + df.format(taxtotal));
        System.out.println("Total                          " + df.format(ftotal));
        //all the items is now being passed to create an object dummy for the class Receipt
        Receipt dummy = new Receipt(id, staff, orderTime, orderDate, name, qty, price, getGross(), stotal, taxtotal, ftotal); 

        return (dummy); //the object dummy is being return to the main

    }

    public double payment() { //Paying

        double cashin, balance;
        System.out.println("Cash In :"); 
        cashin = input.nextDouble(); //get money from user
        while (cashin < (getSubtotal() + (getSubtotal() * 0.06))) { //this will loop until the user enter enought cash
            System.out.println("Not enough cash. Enter again!");
            System.out.println("Cash In :");
            cashin = input.nextDouble();
        }
        balance = cashin - (getSubtotal() + (getSubtotal() * 0.06)); //will display the balance that should be return to the customer

        return (balance); //returnning the balance
    }

    public int confirmOrder(String pay) { //passing the option that user chose to pay or not

        DecimalFormat df = new DecimalFormat("#.##"); 
        int s;
        if ("y".equalsIgnoreCase(pay)) { //if he choose yes to pay, this happen

            System.out.println("Balance : " + df.format(payment())); //The program will display the balance 
            s = 1; //if s = 1 it indicate the transaction is success

        } else {
            name.clear(); //else all the item in cart will be cleared 
            qty.clear();
            price.clear();
            taxes.clear();
            s = 0; //s will be set to 0, so the entire order is not counted. This will also wont make the Order ID to increase

        }

        return (s); //returning s which indicates the success of the order
    }

}
