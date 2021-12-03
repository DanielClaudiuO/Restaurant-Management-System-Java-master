/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagement;


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
    ArrayList<Item> pmenu = new ArrayList<>();
    ArrayList<Item> mmenu = new ArrayList<>();
    ArrayList<Item> smenu = new ArrayList<>();
    ArrayList<Item> ssmenu = new ArrayList<>();
    ArrayList<Item> dsmenu = new ArrayList<>();
    ArrayList<Item> bmenu = new ArrayList<>();

    ArrayList<Receipt> allreceipt = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    int count = 101;

    private String enter="Enter Anything to continue...";

    public static void main(String[] args) throws IOException {

        clearScreen();
        RestaurantManagement ass1 = new RestaurantManagement();
        ass1.start();

    }

    public static void writeNew(ArrayList<Item> editlist, String type) throws IOException {
        File fout = new File(type + ".txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < editlist.size(); i++) {
            bw.write(editlist.get(i).getId());
            bw.newLine();
            bw.write(editlist.get(i).getName());
            bw.newLine();
            bw.write(Double.toString(editlist.get(i).getPrice()));
            bw.newLine();
            bw.write(Double.toString(editlist.get(i).getProfit()));
            bw.newLine();
        }

        bw.close();
    }

    public static void clearScreen() {

        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public void start() throws IOException {

        initData();
        String staff = login();
        String option = "";
        option = menu();
        while (!option.trim().equalsIgnoreCase("Q")) {
            switch (option) {
                case "1":
                    clearScreen();
                    PlaceOrder(staff);
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
            choose1 = input.next();
            switch (choose1) {

                case "1":
                    clearScreen();
                    OrderID = "DI" + count;
                    success = ordering(OrderID, choose1, staff);
                    break;
                case "2":
                    clearScreen();
                    OrderID = "TA" + count;
                    success = ordering(OrderID, choose1, staff);
                    break;
                case "M":
                    break;
            }
            if (success == 1) {
                count++;
            }
        }
    }

    public void OrderStatus() {

        String choose2 = "";
        while (!choose2.trim().equalsIgnoreCase("M")) {
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
                    statusid = input.next();

                    for (int i = 0; i < allreceipt.size(); i++) {

                        if (statusid.equalsIgnoreCase(allreceipt.get(i).getOrderId())) {
                            found = i;
                        }
                    }

                    if (found != 404) {
                        Receipt os = new Receipt((allreceipt.get(found).getOrderId()), (allreceipt.get(found).getStaff()), (allreceipt.get(found).getOrderTime()), (allreceipt.get(found).getOrderDate()), (allreceipt.get(found).getName()), (allreceipt.get(found).getQuantity()), (allreceipt.get(found).getPrice()), (allreceipt.get(found).getProfit()), (allreceipt.get(found).getSubtotal()), (allreceipt.get(found).getTaxtotal()), (allreceipt.get(found).getFtotal()));
                        os.Order();
                    } else {
                        System.out.println("Order ID didnt exist!");

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
        while (!choose3.trim().equalsIgnoreCase("M")) {
            System.out.println("******************* Edit Price ******************");
            System.out.println("Select from Options or Enter ‘M’ for Main Menu");
            System.out.println("1. Pizza");
            System.out.println("2. Pasta");
            System.out.println("3. Meat");
            System.out.println("4. Side");
            System.out.println("5. Sauce");
            System.out.println("6. Desert");
            System.out.println("7. Drink");
            System.out.println("8. Cart");
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
                    type = "pizza";
                    pz.displayItem();
                    pzmenu = pz.editItem();
                    File f = new File("pizza.txt");
                    if (f.exists()) {
                        f.delete();
                    }
                    writeNew(pzmenu, type);
                    System.out.println("Price Edited Succesfully!");
                    break;
                case "2":
                    type = "pasta";
                    ps.displayItem();
                    pmenu = ps.editItem();
                    File c = new File("pasta.txt");
                    if (c.exists()) {
                        c.delete();
                    }
                    writeNew(pmenu, type);
                    System.out.println("Price Edited Succesfully!");
                    break;
                case "3":
                    type = "meat";
                    mt.displayItem();
                    mmenu = mt.editItem();
                    File b = new File("meat.txt");
                    if (b.exists()) {
                        b.delete();
                    }
                    writeNew(mmenu, type);
                    System.out.println("Price Edited Succesfully!");
                    break;
                case "4":
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
                case "5":
                    type = "sauce";
                    sa.displayItem();
                    ssmenu = sa.editItem();
                    File d = new File("sauce.txt");
                    if (d.exists()) {
                        d.delete();
                    }
                    writeNew(ssmenu, type);
                    System.out.println("Price Edited Succesfully!");
                    break;
                case "6":
                    type = "desert";
                    ds.displayItem();
                    dsmenu = ds.editItem();
                    File e = new File("desert.txt");
                    if (e.exists()) {
                        e.delete();
                    }
                    writeNew(dsmenu, type);
                    System.out.println("Price Edited Succesfully!");
                    break;
                case "7":
                    type = "drink";
                    bo.displayItem();
                    bmenu = bo.editItem();
                    File g = new File("drink.txt");
                    if (g.exists()) {
                        g.delete();
                    }
                    writeNew(bmenu, type);
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
        while (!Di.trim().equalsIgnoreCase("B")) {

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
            ordermenu.displayMenu();
            var pz = new Menu(pzmenu);
            var ps = new Menu(pmenu);
            var mt = new Menu(mmenu);
            var so = new Menu(smenu);
            var ss = new Menu(ssmenu);
            var ds = new Menu(dsmenu);
            var bo = new Menu(bmenu);
            Di = input.next();

            switch (Di) {

                case "1":
                    pz.displayItem();
                    pzorder.addAll(pz.chooseItem());
                    System.out.print(enter);
                    input.next();
                    clearScreen();
                    break;
                case "2":
                    ps.displayItem();
                    psorder.addAll(ps.chooseItem());
                    System.out.print(enter);
                    input.next();
                    clearScreen();
                    break;
                case "3":
                    mt.displayItem();
                    mtorder.addAll(mt.chooseItem());
                    System.out.print(enter);
                    input.next();
                    clearScreen();
                    break;
                case "4":
                    so.displayItem();
                    soorder.addAll(so.chooseItem());
                    System.out.print(enter);
                    input.next();
                    clearScreen();
                    break;
                case "5":
                    ss.displayItem();
                    ssorder.addAll(ss.chooseItem());
                    System.out.print(enter);
                    input.next();
                    clearScreen();
                    break;
                case "6":
                    ds.displayItem();
                    dsorder.addAll(ds.chooseItem());
                    System.out.print(enter);
                    input.next();
                    clearScreen();
                    break;
                case "7":
                    bo.displayItem();
                    boorder.addAll(bo.chooseItem());
                    System.out.print(enter);
                    input.next();
                    clearScreen();
                    break;
                case "8":
                    int check = 1;
                    if (pzorder.isEmpty() && psorder.isEmpty() && mtorder.isEmpty() && soorder.isEmpty() && ssorder.isEmpty() && dsorder.isEmpty() && boorder.isEmpty()) {
                        check = 0;
                    }

                    if (check != 0) {
                        Cart dn = new Cart(pzorder, psorder, mtorder, soorder, ssorder, dsorder, boorder);
                        Receipt rc = new Receipt();
                        rc = (Receipt) (dn.displayCart(order, staff));
                        System.out.println("\n-------------------------------------------------");
                        System.out.println("Confirm Order & Pay ? (y/n)");
                        String pay = input.next();
                        s = dn.confirmOrder(pay);
                        if ("y".equalsIgnoreCase(pay)) {
                            allreceipt.add(rc);
                        }
                    } else {
                        System.out.println("You havent order anything!");
                    }
                    System.out.print(enter);
                    input.next();
                    Di = "B";
                    clearScreen();
                    break;
                case "B":
                    break;
            }
        }

        return (s);
    }

    public String menu() {

        clearScreen();
        System.out.println("******************* Welcome To Restaurant X ******************");
        System.out.println("Select any Option you want to perform. Enter ‘Q’ to quit the program ");
        System.out.println("1. Place Order");
        System.out.println("2. Order Status");
        System.out.println("3. Edit Price");
        System.out.println("");

        return input.next();
    }

    public String login() {

        String[] worker = new String[3];
        worker[0] = "Daniel";
        worker[1] = "Claudiu";
        worker[2] = "Leinad";

        String[] pwd = new String[3];
        pwd[0] = "asd";
        pwd[1] = "qwe";
        pwd[2] = "zxc";

        String uname = "", pass;
        int temp = 0;
        System.out.println("******************* Login ******************");
        System.out.println("");
        while (temp == 0) {
            var invalidname = 0;
            var invalidpass = 0;
            System.out.println("Enter Username: ");
            uname = input.next();
            System.out.println("Enter Password: ");
            pass = input.next();
            for (int i = 0; i < worker.length; i++)
            {
                if (uname.equalsIgnoreCase(worker[i]))
                {
                    if (pass.equals(pwd[i]))
                    {
                        System.out.println("Login Succesful!");
                        System.out.println("Welcome " + worker[i]);
                        uname = worker[i];
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

            if (invalidname == worker.length) {
                if (invalidpass == 0) {
                    System.out.println("Wrong Username!");
                }
            }

            if (invalidpass == 1)
            {
                System.out.println("Password Mismatch!");
            }

        }

        return (uname);
    }

    public void initData() throws FileNotFoundException, IOException {

        pzmenu.clear();
        bmenu.clear();
        smenu.clear();
        pmenu.clear();
        mmenu.clear();
        ssmenu.clear();
        dsmenu.clear();


        String filename1 = "pizza.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename1))) {
            while (br.ready()) {

                String id = br.readLine();
                String name = br.readLine();
                Double price = Double.parseDouble(br.readLine());
                Double profit = Double.parseDouble(br.readLine());
                Item pz = new Item(id, name, price, profit);
                pzmenu.add(pz);
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
        try (BufferedReader pr = new BufferedReader(new FileReader(filename4))) {
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
        try (BufferedReader mr = new BufferedReader(new FileReader(filename5))) {
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
        try (BufferedReader qr = new BufferedReader(new FileReader(filename6))) {
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
        try (BufferedReader der = new BufferedReader(new FileReader(filename7))) {
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
