/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagement;

/**
 * @author Michael Depp
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantManagement {

    ArrayList<Item> pzmenu = new ArrayList<>();
    ArrayList<Item> pmenu = new ArrayList<>(); //Creating an arraylist based on the Item Class
    ArrayList<Item> mmenu = new ArrayList<>();
    ArrayList<Item> smenu = new ArrayList<>();
    ArrayList<Item> ssmenu = new ArrayList<>();
    ArrayList<Item> dsmenu = new ArrayList<>(); //Creating an arraylist based on the Item Class
    ArrayList<Item> bmenu = new ArrayList<>();

    ArrayList<Receipt> allreceipt = new ArrayList<>(); //Creating an arraylist based on Receipt Class
    Scanner input = new Scanner(System.in);
    int count = 101; //Initializing count as 101. So the order number will start from 101

    public static void main(String[] args) throws IOException {

        clearScreen(); //To clear the cmd
        RestaurantManagement ass1 = new RestaurantManagement(); //a new instance for this main
        ass1.start(); //Calling function start

    }

    public static void writeNew(ArrayList<Item> editlist, String type) throws IOException {
        File fout = new File(type + ".txt"); //f name depends on the type variable that being passed
        FileOutputStream fos = new FileOutputStream(fout); //creating new txt file

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < editlist.size(); i++) { //will loop until the arraylist size
            bw.write(editlist.get(i).getId()); //writing the line based on the arraylist
            bw.newLine(); //going the nextline
            bw.write(editlist.get(i).getName());
            bw.newLine();
            bw.write(Double.toString(editlist.get(i).getPrice()));
            bw.newLine();
            bw.write(Double.toString(editlist.get(i).getProfit()));
            bw.newLine();
        }

        bw.close(); //closing the file
    }

    public static void clearScreen() { //to clear the text that already displayed in the cmd

        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public void start() throws IOException {

        initData(); //Calling initData function to load the menu items to the program
        String staff = login(); //Calling login function, staff name will be initialize here
        String option = "";
        option = menu(); //Calling menu function, option will gets it value from the return value
        while (!option.trim().equalsIgnoreCase("Q")) { //This will loop until the user enter q for quit
            switch (option) {
                case "1":
                    clearScreen(); //clearing the cmd
                    PlaceOrder(staff); //calling PlaceOrder function and passing the staff variable
                    break;
                case "2":
                    clearScreen();
                    OrderStatus();
                    break;
                case "3":
                    clearScreen();
                    EditPrice();
                    break;
                default:
                    System.out.println("Invalid Input!");
                    break;
            }
            option = menu();
        }

        System.out.println("");
        System.out.println("Thanks for Using our Restaurant Management System!");
        System.out.println("");
    }

    public void PlaceOrder(String staff) {

        String choose1 = "";
        String OrderID;
        int success = 0;
        while (!choose1.trim().equalsIgnoreCase("M")) { //Will loop until user enter M
            System.out.println("******************* Placing New Order ******************");
            System.out.println("Select from Options or Enter ‘M’ for Main Menu");
            System.out.println("1. Dine In");
            System.out.println("2. Take Away");
            System.out.println("M. Go to main Menu");
            choose1 = input.next(); //Getting choice input
            switch (choose1) {

                case "1":
                    clearScreen();
                    OrderID = "DI" + count;
                    success = ordering(OrderID, choose1, staff); //Calling ordering function and passing the required values
                    break;
                case "2":
                    clearScreen();
                    OrderID = "TA" + count; //Setting the order ID from the count and adding the TA word to indicate its a TAKE AWAY Order
                    success = ordering(OrderID, choose1, staff);
                    break;
                case "M":
                    break;
            }
            if (success == 1) { //if the return s value which is the success is equal to 1, it indicates the success of the previous order
                count++; //So the count will be plus 1 so the next orderId will have a new number.
            }
        }
    }

    public void OrderStatus() {

        String choose2 = "";
        while (!choose2.trim().equalsIgnoreCase("M")) { //Will loop until user enter M
            System.out.println("******************* Order Status ******************");
            System.out.println("Select from Options or Enter ‘M’ for Main Menu");
            System.out.println("1. Check Order Status");
            System.out.println("M. Go to main Menu");
            choose2 = input.next();
            switch (choose2) {

                case "1":
                    String statusid;
                    int found = 404;
                    System.out.println("Enter Your Order ID: ");
                    statusid = input.next(); //The user have to enter his Order ID

                    for (int i = 0; i < allreceipt.size(); i++) { //This will loop the arraylist allreceipt which store all the orders to find the order ID

                        if (statusid.equalsIgnoreCase(allreceipt.get(i).getOrderId())) {
                            found = i; //if it found the it will copy the index of the arraylist that the order Id is being stored
                        }
                    }

                    if (found != 404) { //if the ORDER ID found

                        //A new object will be created for the class Receipt and all the infos from the arraylist allreceipt will be passed into the object.
                        Receipt os = new Receipt((allreceipt.get(found).getOrderId()), (allreceipt.get(found).getStaff()), (allreceipt.get(found).getOrderTime()), (allreceipt.get(found).getOrderDate()), (allreceipt.get(found).getName()), (allreceipt.get(found).getQuantity()), (allreceipt.get(found).getPrice()), (allreceipt.get(found).getProfit()), (allreceipt.get(found).getSubtotal()), (allreceipt.get(found).getTaxtotal()), (allreceipt.get(found).getFtotal()));
                        os.Order(); //calling function Order in the class Receipt
                    } else {
                        System.out.println("Order ID didnt exist!"); //Error message if the order ID is wrong

                    }
                    break;
                case "M":
                    break;
            }
        }

    }

    public void EditPrice() throws IOException {

        String choose3 = "";
        clearScreen();
        while (!choose3.trim().equalsIgnoreCase("M")) { //Will loop until user enter M
            System.out.println("******************* Edit Price ******************");
            System.out.println("Select from Options or Enter ‘M’ for Main Menu");
            System.out.println("1. Food");
            System.out.println("2. Beverage");
            System.out.println("3. Side");
            System.out.println("M. Go to main Menu");
            Menu pz = new Menu(pzmenu);
            Menu ps = new Menu(pmenu);
            Menu mt = new Menu(mmenu);
            Menu sd = new Menu(smenu);
            Menu sa = new Menu(ssmenu);
            Menu ds = new Menu(dsmenu);
            Menu bo = new Menu(bmenu);
            choose3 = input.next();
            String type = " ";
            switch (choose3) {

                case "1":
                    type = "food"; //Setting the item type based on the choice user enter
                    pz.displayItem(); //Displaying all the items under the arraylist
                    pzmenu = pz.editItem(); //setting the existing arraylist based on the return value by calling function editItem
                    File f = new File("pizza.txt"); //setting the file name as the  present txt file name that storing all the datas
                    if (f.exists()) {
                        f.delete(); //it will delete the txt file that present now
                    }
                    writeNew(pzmenu, type); //Calling function writeNew and passing list type and arraylist
                    System.out.println("Price Edited Succesfully!");
                    break;
                case "2":
                    type = "drink";
                    bo.displayItem();
                    bmenu = bo.editItem();
                    File b = new File("drink.txt");
                    if (b.exists()) {
                        b.delete();
                    }
                    writeNew(bmenu, type);
                    System.out.println("Price Edited Succesfully!");
                    break;
                case "3":
                    type = "side";
                    sd.displayItem();
                    smenu = sd.editItem();
                    File s = new File("side.txt");
                    if (s.exists()) {
                        s.delete();
                    }
                    writeNew(smenu, type);
                    System.out.println("Price Edited Succesfully!");
                    break;
                case "M":
                    break;
            }
        }
    }

    public int ordering(String order, String type, String staff) {

        String Di = "";
        int s = 0;
        Menu ordermenu = new Menu();
        ArrayList<Order> pzorder = new ArrayList<>();
        ArrayList<Order> psorder = new ArrayList<>();
        ArrayList<Order> mtorder = new ArrayList<>();
        ArrayList<Order> soorder = new ArrayList<>();
        ArrayList<Order> ssorder = new ArrayList<>();
        ArrayList<Order> dsorder = new ArrayList<>();
        ArrayList<Order> boorder = new ArrayList<>();
        while (!Di.trim().equalsIgnoreCase("B")) { //Will loop until user enters B

            String title;
            if ("1".equals(type)) {
                title = "Dine In";
            } else if ("2".equals(type)) {
                title = "Take Away";
            } else if ("3".equals(type)) {
                title = "Grab Food/Food Panda";
            } else {
                title = "";
            }
            System.out.println("******************* " + title + " ******************");
            ordermenu.displayMenu(); //calling displayMenu function inside Menu Class
            Menu pz = new Menu(pzmenu);
            Menu ps = new Menu(pmenu);
            Menu mt = new Menu(mmenu);
            Menu so = new Menu(smenu);
            Menu ss = new Menu(ssmenu);
            Menu ds = new Menu(dsmenu);
            Menu bo = new Menu(bmenu);
            Di = input.next();

            switch (Di) {

                case "1": //When food is selected
                    pz.displayItem(); //Calling displayItem function from Menu class
                    pzorder.addAll(pz.chooseItem()); //Calling chooseItem function and the return value will be added to pzorder arraylist
                    System.out.print("Enter Anything to continue...");
                    input.next(); //This is like getch funtion
                    clearScreen(); //To clear the cmd
                    break;
                case "2": //When pasta is selected
                    ps.displayItem();
                    psorder.addAll(ps.chooseItem());
                    System.out.print("Enter Anything to continue...");
                    input.next();
                    clearScreen();
                    break;
                case "3":
                    mt.displayItem();
                    mtorder.addAll(mt.chooseItem());
                    System.out.print("Enter Anything to continue...");
                    input.next();
                    clearScreen();
                    break;
                case "4":
                    so.displayItem();
                    soorder.addAll(so.chooseItem());
                    System.out.print("Enter Anything to continue...");
                    input.next();
                    clearScreen();
                    break;
                case "5":
                    ss.displayItem();
                    ssorder.addAll(ss.chooseItem());
                    System.out.print("Enter Anything to continue...");
                    input.next();
                    clearScreen();
                    break;
                case "6":
                    ds.displayItem();
                    dsorder.addAll(ss.chooseItem());
                    System.out.print("Enter Anything to continue...");
                    input.next();
                    clearScreen();
                    break;
                case "7":
                    bo.displayItem();
                    boorder.addAll(ss.chooseItem());
                    System.out.print("Enter Anything to continue...");
                    input.next();
                    clearScreen();
                    break;
                case "8": //When cart is selected
                    int check = 1;
                    if (pzorder.size() == 0 || psorder.size() == 0 || mtorder.size() == 0 || soorder.size() == 0 || ssorder.size() == 0 || dsorder.size() == 0 || boorder.size() == 0) { //this line will check whether any items have been added or no
                        check = 0; //If there is no any item has been added, the check will be set to 0


                    }

                    if (check != 0) { //if the check is not 0, which means there is item been added
                        Cart dn = new Cart(pzorder, psorder, mtorder,soorder, ssorder, dsorder, boorder ); //creating a object for the class Cart
                        Receipt rc = new Receipt(); //creating an object for the class Receipt
                        rc = (Receipt) (dn.displayCart(order, staff)); //Calling function displayCart inside Cart and copying the value to rc object
                        System.out.println("\n-------------------------------------------------");
                        System.out.println("Confirm Order & Pay ? (y/n)"); //Asking user whether he want to pay or not
                        String pay = input.next();
                        s = dn.confirmOrder(pay); //Calling the payment function and this will return variable s indicating the success of the payment
                        if ("y".equalsIgnoreCase(pay)) {
                            allreceipt.add(rc); //The receipt of this order will be added to allreceipt class, which will be use to track the order later
                        }
                    } else { //if there is no any item in any of the arraylist
                        System.out.println("You havent order anything!");
                    }
                    System.out.print("Enter Anything to continue...");
                    input.next();
                    Di = "B"; //will set Di to B to break the loop
                    clearScreen(); //to clear cmd
                    break;
                case "B":
                    break;
            }
        }

        return (s); //program will return s which indicates success of the order
    }

    public String menu() {

        clearScreen(); //clearing cmd
        System.out.println("******************* Welcome To Restaurant X ******************");
        System.out.println("Select any Option you want to perform. Enter ‘Q’ to quit the program ");
        System.out.println("1. Place Order");
        System.out.println("2. Order Status");
        System.out.println("3. Edit Price");
        System.out.println("");

        return input.next(); //It will return the option that has been key-in
    }

    public String login() {

        String[] worker = new String[3]; //An array created with 3 users
        worker[0] = "Daniel";
        worker[1] = "Claudiu";
        worker[2] = "Leinad";

        String[] pwd = new String[3]; //Password has been hard coded into the array
        pwd[0] = "asd";
        pwd[1] = "qwe";
        pwd[2] = "zxc";

        String uname = "", pass;
        int temp = 0; //for loop
        System.out.println("******************* Login ******************");
        System.out.println("");
        while (temp == 0) {
            int invalidname = 0;
            int invalidpass = 0;
            System.out.println("Enter Username: ");
            uname = input.next(); //Getting username
            System.out.println("Enter Password: ");
            pass = input.next(); //Getting password
            for (int i = 0; i < worker.length; i++) //Will loop according to the array size
            {
                if (uname.equalsIgnoreCase(worker[i])) //if a username matched
                {
                    if (pass.equals(pwd[i])) //it will check whether the username index and password index is matching or no
                    {                        //this technique use the real world algorithm
                        System.out.println("Login Succesful!");
                        System.out.println("Welcome " + worker[i]);
                        uname = worker[i]; //username will be copied to this variable
                        temp = 1;
                        invalidname = 0;
                        System.out.println("Enter anything to continue...");
                        input.next();
                    } else {
                        invalidpass = 1;
                    }
                } else {
                    invalidname++;
                }
            }

            if (invalidname == worker.length) { //Error message for wrong username & password
                if (invalidpass == 0) {
                    System.out.println("Wrong Username!");
                }
            }

            if (invalidpass == 1) //this is for the user who put correct username but wrong password
            {
                System.out.println("Password Mismatch!");
            }

        }

        return (uname); //returning to uname to the staff variable
    }

    public void initData() throws FileNotFoundException, IOException {

        pzmenu.clear(); //clearing the arraylist first
        bmenu.clear();
        smenu.clear();
        pmenu.clear();
        mmenu.clear();
        ssmenu.clear();
        dsmenu.clear();


        String filename1 = "pizza.txt"; //Setting the filename to our txt file name
        try (BufferedReader br = new BufferedReader(new FileReader(filename1))) { //opening the text file
            while (br.ready()) { //while there is a new line this will loop

                String id = br.readLine(); // the id copied the line from the text file
                String name = br.readLine();
                Double price = Double.parseDouble(br.readLine()); //Copying the string into a double data
                Double profit = Double.parseDouble(br.readLine());
                Item pz = new Item(id, name, price, profit); //a new instance being created for the class item with the above datas
                pzmenu.add(pz); //that object being added to this same data type arraylist
            }
        }

        String filename2 = "drink.txt";
        try (BufferedReader dr = new BufferedReader(new FileReader(filename2))) {
            while (dr.ready()) {

                String id = dr.readLine();
                String name = dr.readLine();
                Double price = Double.parseDouble(dr.readLine());
                Double profit = Double.parseDouble(dr.readLine());
                Item dm = new Item(id, name, price, profit);
                bmenu.add(dm);
            }
        }

        String filename3 = "side.txt";
        try (BufferedReader sr = new BufferedReader(new FileReader(filename3))) {
            while (sr.ready()) {

                String id = sr.readLine();
                String name = sr.readLine();
                Double price = Double.parseDouble(sr.readLine());
                Double profit = Double.parseDouble(sr.readLine());
                Item sm = new Item(id, name, price, profit);
                smenu.add(sm);
            }
        }

        String filename4 = "pasta.txt";
        try (BufferedReader pr = new BufferedReader(new FileReader(filename3))) {
            while (pr.ready()) {

                String id = pr.readLine();
                String name = pr.readLine();
                Double price = Double.parseDouble(pr.readLine());
                Double profit = Double.parseDouble(pr.readLine());
                Item ps = new Item(id, name, price, profit);
                pmenu.add(ps);
            }
        }

        String filename5 = "meat.txt";
        try (BufferedReader mr = new BufferedReader(new FileReader(filename3))) {
            while (mr.ready()) {

                String id = mr.readLine();
                String name = mr.readLine();
                Double price = Double.parseDouble(mr.readLine());
                Double profit = Double.parseDouble(mr.readLine());
                Item mt = new Item(id, name, price, profit);
                mmenu.add(mt);
            }
        }

        String filename6 = "sauce.txt";
        try (BufferedReader qr = new BufferedReader(new FileReader(filename3))) {
            while (qr.ready()) {

                String id = qr.readLine();
                String name = qr.readLine();
                Double price = Double.parseDouble(qr.readLine());
                Double profit = Double.parseDouble(qr.readLine());
                Item ss = new Item(id, name, price, profit);
                ssmenu.add(ss);
            }
        }
        String filename7 = "desert.txt";
        try (BufferedReader der = new BufferedReader(new FileReader(filename3))) {
            while (der.ready()) {

                String id = der.readLine();
                String name = der.readLine();
                Double price = Double.parseDouble(der.readLine());
                Double profit = Double.parseDouble(der.readLine());
                Item ds = new Item(id, name, price, profit);
                dsmenu.add(ds);
            }
        }
    }
}
