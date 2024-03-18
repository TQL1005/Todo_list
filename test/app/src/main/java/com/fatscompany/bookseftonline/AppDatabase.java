package com.fatscompany.bookseftonline;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.fatscompany.bookseftonline.DAO.BookDAO;
import com.fatscompany.bookseftonline.DAO.CategoryDAO;
import com.fatscompany.bookseftonline.DAO.InventoryDAO;
import com.fatscompany.bookseftonline.DAO.OrderDetailDAO;
import com.fatscompany.bookseftonline.DAO.PaymentHistoryDao;
import com.fatscompany.bookseftonline.DAO.PublishersDAO;
import com.fatscompany.bookseftonline.DAO.SaleOrderDAO;

import com.fatscompany.bookseftonline.DAO.UserDAO;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.Category;
import com.fatscompany.bookseftonline.Entitis.Inventory;
import com.fatscompany.bookseftonline.Entitis.OrderDetail;
import com.fatscompany.bookseftonline.Entitis.PaymentHistory;
import com.fatscompany.bookseftonline.Entitis.Publishers;
import com.fatscompany.bookseftonline.Entitis.SaleOrder;
import com.fatscompany.bookseftonline.Entitis.User;

@Database(entities = {OrderDetail.class, Publishers.class, SaleOrder.class, Inventory.class, User.class, Book.class, Category.class, PaymentHistory.class}, version = 1, exportSchema = true)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OrderDetailDAO orderDetailDao();

    public abstract PublishersDAO publishersDao();

    public abstract SaleOrderDAO saleOrderDao();

    public abstract InventoryDAO inventoryDao();

    public abstract BookDAO bookDao();

    public abstract UserDAO userDao();

    public abstract CategoryDAO categoryDao();
    public abstract PaymentHistoryDao paymentHistoryDao();


    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "bookstore_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
