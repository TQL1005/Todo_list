package com.fatscompany.bookseftonline.Entitis;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "book",
        foreignKeys = {
                @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id", onDelete = ForeignKey.SET_NULL, onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = Publishers.class, parentColumns = "id", childColumns = "publisher_id", onDelete = ForeignKey.SET_NULL, onUpdate = ForeignKey.NO_ACTION)
        })
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "price")
    private double price;
    @ColumnInfo(name = "authors")
    private String authors;
    @ColumnInfo(name = "publication_year")
    private int publicationYear;
    @ColumnInfo(name = "condition")
    private boolean condition;
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "publisher_id")
    private int publisherId;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "soldBook")
    private int soldBook;
    private int currentQuantity = 1; // Mặc định số lượng là 1

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int quantity) {
        currentQuantity = quantity;
    }

    public Book(String title, String description, double price, String authors, int publicationYear, boolean condition, int categoryId, int publisherId, String image, int soldBook) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.condition = condition;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
        this.image = image;
        this.soldBook = soldBook;
        this.currentQuantity = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSoldBook() {
        return soldBook;
    }

    public void setSoldBook(int soldBook) {
        this.soldBook = soldBook;
    }
}