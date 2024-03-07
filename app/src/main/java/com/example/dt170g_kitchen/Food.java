package com.example.dt170g_kitchen;



/**
 * this class represents a menu item in the a la carte menu
 */
public class Food {
    private int aLaCarteID;

    private int price;
    private String name;
    private String type;
    private String description;
    private String status; // Add status field

    // Constructor, getters, setters, etc.

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Food(int aLaCarteID, int price, String name, String type, String description) {
        this.aLaCarteID = aLaCarteID;
        this.price = price;
        this.name = name;
        this.type = type;
        this.description = description;
    }

   /* public ALaCarteItem(ALaCarteMenuEntity aLaCarteEntity){
        this.aLaCarteID = aLaCarteEntity.getaLaCarteId();
        this.price = aLaCarteEntity.getPrice();
        this.name = aLaCarteEntity.getName();
        this.type = aLaCarteEntity.getType();
        this.description = aLaCarteEntity.getDescription();
    }*/


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getaLaCarteID() {
        return aLaCarteID;
    }

    public void setaLaCarteID(int aLaCarteID) {
        this.aLaCarteID = aLaCarteID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
