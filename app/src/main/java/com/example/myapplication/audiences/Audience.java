package com.example.myapplication.audiences;

public class Audience {
    private int favourite;
    private int tf, number, floor;
    private String description;

    public Audience(int tf, int number, int floor, String description, int favourite) {
        this.tf = tf;
        this.number = number;
        this.floor = floor;
        this.description = description;
        this.favourite = favourite;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
