/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagement;


public class Item {
    
    private String id;
    private String name;
    protected double price;
    protected double profit;
    

    public Item(String id, String name, double price, double profit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.profit = profit;}

    Item() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    
    public double getProfit() {
        return profit;
    }
}
