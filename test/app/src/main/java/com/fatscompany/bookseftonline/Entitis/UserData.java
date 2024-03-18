package com.fatscompany.bookseftonline.Entitis;


public class UserData {
    private String name;
    private String createDate;
    private int mount;

    private String lName;

    private String fNanme;

    public UserData(String fNanme, String lName, String createDate, int mount) {
        this.fNanme = fNanme;
        this.lName = lName;
        this.createDate = createDate;
        this.mount = mount;
    }

    public String getName(){
        return this.fNanme + " " + this.lName;
    }

    public String getFName() {
        return name;
    }

    public String getLName() {
        return name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getMount() {
        return mount;
    }
}