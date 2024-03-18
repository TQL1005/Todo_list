package com.example.newapp;

public class Destination {
    String Destinations;
    String Descriptions;
    Double price;

    public Destination(String Destinations,String Description, Double price){
        this.setDestinations(Destinations);
        this.setDescriptions(Description);
        this.setPrice(price);
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
