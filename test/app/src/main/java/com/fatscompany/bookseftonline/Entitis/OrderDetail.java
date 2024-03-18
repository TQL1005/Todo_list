package com.fatscompany.bookseftonline.Entitis;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_detail",
        foreignKeys = {
                @ForeignKey(entity = SaleOrder.class, parentColumns = "id", childColumns = "sale_order_id", onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION),
                @ForeignKey(entity = Book.class, parentColumns = "id", childColumns = "book_id", onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION)
        },
        indices = {
                @Index("sale_order_id"),
                @Index("book_id")
        })
public class OrderDetail {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "book_id")
    private int bookId;

    @ColumnInfo(name = "sale_order_id")
    private int saleOrderId;

    @ColumnInfo(name = "amount")
    private int amount;

    public OrderDetail(String name, int bookId, int saleOrderId, int amount) {
        this.name = name;
        this.bookId = bookId;
        this.saleOrderId = saleOrderId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(int saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}