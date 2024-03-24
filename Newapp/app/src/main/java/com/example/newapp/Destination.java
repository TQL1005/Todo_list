package com.example.newapp;

public class Destination {
    String Destinations;
    String Descriptions;
    Double price;
    String pic1;

    public Destination(String destination) {

    }

    public void getDestinations(String destinations) {
        Destinations = destinations;
    }


    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public Destination(String Destinations,String Description, Double price, String pic1){
        this.setDestinations(Destinations);
        this.setDescriptions(Description);
        this.setPrice(price);
        this.setPic1(pic1);
    }

    public String getDestinations() {
        return Destinations;
    }

    public void setDestinations(String destinations) {
        Destinations = destinations;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String descriptions) {
        Descriptions = descriptions;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
