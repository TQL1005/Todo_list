package com.example.newapp;

public class Destination {
    String Destinations;
    String Descriptions;
    Double price;
    String pic1;
    String start_date;
    String end_date;

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    String tour_name;
    int amount;

    public Destination(String destination, String description, Double price, String img) {
        this.setDestinations(destination);
        this.setDescriptions(description);
        this.setPrice(price);
        this.setPic1(img);
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


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

    public Destination(String Destinations,String Description, Double price, String pic1, String start_date, String end_date, int amount,String tour_name){
        this.setDestinations(Destinations);
        this.setDescriptions(Description);
        this.setPrice(price);
        this.setPic1(pic1);
        this.setStart_date(start_date);
        this.setEnd_date(end_date);
        this.setAmount(amount);
        this.setTour_name(tour_name);
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
