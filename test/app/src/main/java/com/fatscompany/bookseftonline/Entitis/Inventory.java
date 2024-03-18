package com.fatscompany.bookseftonline.Entitis;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Inventory", foreignKeys = {
        @ForeignKey(entity = Book.class, parentColumns = "id", childColumns = "book_id", onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION)})
public class Inventory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "stock")
    private int stock;

    @ColumnInfo(name = "book_id")
    private int bookId;

    public Inventory(int stock, int bookId) {
        this.stock = stock;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
