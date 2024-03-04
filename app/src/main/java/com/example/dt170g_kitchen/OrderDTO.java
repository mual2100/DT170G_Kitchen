package com.example.dt170g_kitchen;


import java.util.*;

public class OrderDTO {


    private int order_ID;

    private String statusAppetizer;

    private String statusMain;

    private String statusDessert;

    private int restaurantTableId;

    private String comment;
    private int orderStatus;


    private ArrayList<ALaCarteItem> foods;

    private ArrayList<Drink> drinks;

    public OrderDTO(int order_ID, String statusAppetizer, String statusMain, String statusDessert, int restaurantTableId, String comment, ArrayList<ALaCarteItem> foods, ArrayList<Drink> drinks) {
        this.order_ID = order_ID;
        this.statusAppetizer = statusAppetizer;
        this.statusMain = statusMain;
        this.statusDessert = statusDessert;
        this.restaurantTableId = restaurantTableId;
        this.comment = comment;
        this.foods = foods;
        this.drinks = drinks;
    }
    public String getTime() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int day= calendar.get(Calendar.DATE);
        int year= calendar.get(Calendar.YEAR);
        int month= calendar.get(Calendar.MONTH);
        // Format the time string (you can adjust the format as needed)
        String formattedTime = String.format("%02d:%02d:%02d:%02d:%02d", hour, minute,day,month,year);

        return formattedTime;
    }
    public OrderDTO() {
        foods = new ArrayList<ALaCarteItem>();
        drinks = new ArrayList<Drink>();
    }

    public int getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(int order_ID) {
        this.order_ID = order_ID;
    }

    public String getStatusAppetizer() {
        return statusAppetizer;
    }

    public void setStatusAppetizer(String statusAppetizer) {
        this.statusAppetizer = statusAppetizer;
    }

    public String getStatusMain() {
        return statusMain;
    }

    public void setStatusMain(String statusMain) {
        this.statusMain = statusMain;
    }

    public String getStatusDessert() {
        return statusDessert;
    }

    public void setStatusDessert(String statusDessert) {
        this.statusDessert = statusDessert;
    }

    public int getRestaurantTableId() {
        return restaurantTableId;
    }

    public void setRestaurantTableId(int restaurantTableId) {
        this.restaurantTableId = restaurantTableId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<ALaCarteItem> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<ALaCarteItem> foods) {
        this.foods = foods;
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }
    public void addDrink(Drink drink){
        drinks.add(drink);
    };
    public void addFood(ALaCarteItem food){
        foods.add(food);
    };

    public int isOrderStatus() {
        return orderStatus;
    }
    public int getOrderStatus(){
        return orderStatus;
    }
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
