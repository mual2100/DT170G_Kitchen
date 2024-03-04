package com.example.dt170g_kitchen;


public class Drink {
    private int drink_ID;

    private String name;
    private String description;
    private int price;

    public Drink(int drink_id, String name, String description, int price) {
        this.drink_ID = drink_id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getDrink_ID() {
        return drink_ID;
    }

    public void setDrink_ID(int drink_ID) {
        this.drink_ID = drink_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
