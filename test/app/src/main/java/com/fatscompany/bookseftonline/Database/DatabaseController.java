package com.fatscompany.bookseftonline.Database;

import static com.fatscompany.bookseftonline.Database.Settings.TABLE_NAME_01;
import static com.fatscompany.bookseftonline.Database.Settings.TABLE_NAME_06;
import static com.fatscompany.bookseftonline.Database.Settings.TABLE_NAME_05;
import static com.fatscompany.bookseftonline.Database.Settings.TABLE_NAME_04;

import com.fatscompany.bookseftonline.Entitis.User;
import com.fatscompany.bookseftonline.Entitis.UserData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "bookseftdb.db";
    static final int DATABASE_VERSION = 1;

    //TABLE book
    private static final String TABLE_NAME = "book";

    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //q = "CREATE TABLE "+Settings.TABLE_NAME_01+"( "+Settings.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+Settings.COLUMN_TITLE+" TEXT NOT NULL, "+Settings.COLUMN_DECRIPTION+" TEXT NOT NULL, "+Settings.COLUMN_PRICE+" DOUBLE NOT NULL, "+Settings.COLUMN_AUTHORS+" TEXT, "+Settings.COLUMN_PUBLICATION_YEAR+" TEXT, "+Settings.COLUMN_CONDITION+" BOOLEAN NOT NULL, "+Settings.COLUMN_CATEGORY_ID+" INTEGER NOT NULL, "+Settings.COLUMN_PUBLISHER_ID+" INTEGER NOT NULL, "+Settings.COLUMN_IMAGE+" TEXT, CONSTRAINT "+Settings.TABLE_NAME_07+"_"+Settings.TABLE_NAME_01+" FOREIGN KEY ("+Settings.COLUMN_PUBLISHER_ID+") REFERENCES "+Settings.TABLE_NAME_07+" ("+Settings.COLUMN_ID+") ON DELETE No action ON UPDATE No action, CONSTRAINT "+Settings.TABLE_NAME_02+"_"+Settings.TABLE_NAME_01+" FOREIGN KEY ("+Settings.COLUMN_CATEGORY_ID+") REFERENCES "+Settings.TABLE_NAME_02+" ("+Settings.COLUMN_ID+") ON DELETE No action ON UPDATE No action);    CREATE TABLE "+Settings.TABLE_NAME_02+" ("+Settings.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+Settings.COLUMN_NAME+" TEXT, "+Settings.COLUMN_DECRIPTION+" TEXT);                CREATE TABLE "+Settings.TABLE_NAME_03+"( "+Settings.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+Settings.COLUMN_STOCK+" INTEGER NOT NULL, "+Settings.COLUMN_BOOK_ID+ " INTEGER NOT NULL, CONSTRAINT "+Settings.TABLE_NAME_01+"_"+Settings.TABLE_NAME_03+" FOREIGN KEY ("+Settings.COLUMN_BOOK_ID+") REFERENCES "+Settings.TABLE_NAME_01+" ("+Settings.COLUMN_ID+") ON DELETE No action ON UPDATE No action);                CREATE TABLE "+Settings.TABLE_NAME_04+"( "+Settings.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+Settings.COLUMN_NAME+" TEXT NOT NULL, "+Settings.COLUMN_BOOK_ID+" INTEGER NOT NULL, sale_orderid INTEGER NOT NULL, "+Settings.COLUMN_AMOUNT+" INTEGER NOT NULL, CONSTRAINT "+Settings.TABLE_NAME_05+"_"+Settings.TABLE_NAME_04+" FOREIGN KEY ("+Settings.COLUMN_SALEORDER_ID+") REFERENCES "+Settings.TABLE_NAME_05+" ("+Settings.COLUMN_ID+") ON DELETE No action ON UPDATE No action, CONSTRAINT "+Settings.TABLE_NAME_01+"_"+Settings.TABLE_NAME_04+" FOREIGN KEY ("+ Settings.COLUMN_BOOK_ID +") REFERENCES "+Settings.TABLE_NAME_01+" ("+Settings.COLUMN_ID+") ON DELETE No action ON UPDATE No action                CREATE TABLE "+Settings.TABLE_NAME_07+" ("+Settings.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+Settings.COLUMN_NAME+" TEXT NOT NULL);                CREATE TABLE "+Settings.TABLE_NAME_05+"( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+Settings.COLUMN_CREATED_DATE+" DATETIME NOT NULL, "+Settings.COLUMN_USER_ID+" INTEGER NOT NULL, CONSTRAINT "+Settings.TABLE_NAME_06+"_"+Settings.TABLE_NAME_05+" FOREIGN KEY ("+Settings.COLUMN_USER_ID+") REFERENCES "+Settings.TABLE_NAME_06+" ("+Settings.COLUMN_ID+") ON DELETE No action ON UPDATE No action);                CREATE TABLE "+Settings.TABLE_NAME_06+"( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+Settings.COLUMN_USERNAME+" TEXT NOT NULL, "+Settings.COLUMN_PASSWORD+" TEXT NOT NULL, "+Settings.COLUMN_FIRSTNAME+" TEXT NOT NULL, "+Settings.COLUMN_LASTNAME+" TEXT NOT NULL, "+Settings.COLUMN_EMAIL+" TEXT, "+Settings.COLUMN_PHONE+" TEXT, "+Settings.COLUMN_ACTIVE+" BOOLEAN NOT NULL, "+Settings.COLUMN_USEROLE+" TEXT NOT NULL);";
        String q;
        //String q = "CREATE TABLE book( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT NOT NULL, decription TEXT NOT NULL, price DOUBLE NOT NULL, authors TEXT, publication_year TEXT, condition BOOLEAN NOT NULL, category_id INTEGER NOT NULL, publisher_id INTEGER NOT NULL, image TEXT, CONSTRAINT publishers_book FOREIGN KEY (publisher_id) REFERENCES publishers (id) ON DELETE No action ON UPDATE No action, CONSTRAINT category_book FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE No action ON UPDATE No action);                CREATE TABLE category (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, decription TEXT);                CREATE TABLE inventory( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, stock INTEGER NOT NULL, book_id INTEGER NOT NULL, CONSTRAINT book_inventory FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE No action ON UPDATE No action);                CREATE TABLE order_detail( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, book_id INTEGER NOT NULL, sale_orderid INTEGER NOT NULL, amount INTEGER NOT NULL, CONSTRAINT sale_order_order_detail FOREIGN KEY (sale_orderid) REFERENCES sale_order (id) ON DELETE No action ON UPDATE No action, CONSTRAINT book_order_detail FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE No action ON UPDATE No action                CREATE TABLE publishers (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL);                CREATE TABLE sale_order( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, created_date DATETIME NOT NULL, user_id INTEGER NOT NULL, CONSTRAINT user_sale_order FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE No action ON UPDATE No action);                CREATE TABLE user( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username TEXT NOT NULL, password TEXT NOT NULL, first_name TEXT NOT NULL, last_name TEXT NOT NULL, email TEXT, phone TEXT, active BOOLEAN NOT NULL, user_role TEXT NOT NULL);";

        q = "CREATE TABLE IF NOT EXISTS " + Settings.TABLE_NAME_02 + " (" +
                Settings.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Settings.COLUMN_NAME + " TEXT, " +
                Settings.COLUMN_DECRIPTION + " TEXT);";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS " + Settings.TABLE_NAME_07 + " (" +
                Settings.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Settings.COLUMN_NAME + " TEXT NOT NULL);";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS " + Settings.TABLE_NAME_01 + " (" +
                Settings.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Settings.COLUMN_TITLE + " TEXT NOT NULL, " +
                Settings.COLUMN_DECRIPTION + " TEXT NOT NULL, " +
                Settings.COLUMN_PRICE + " DOUBLE NOT NULL, " +
                Settings.COLUMN_AUTHORS + " TEXT, " +
                Settings.COLUMN_PUBLICATION_YEAR + " TEXT, " +
                Settings.COLUMN_CONDITION + " BOOLEAN NOT NULL, " +
                Settings.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
                Settings.COLUMN_PUBLISHER_ID + " INTEGER NOT NULL, " +
                Settings.COLUMN_IMAGE + " TEXT, " +
                "FOREIGN KEY (" + Settings.COLUMN_PUBLISHER_ID + ") REFERENCES " +
                Settings.TABLE_NAME_07 + " (" + Settings.COLUMN_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION, " +
                "FOREIGN KEY (" + Settings.COLUMN_CATEGORY_ID + ") REFERENCES " +
                Settings.TABLE_NAME_02 + " (" + Settings.COLUMN_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_06 + " (" +
                Settings.COLUMN_ID +"INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Settings.COLUMN_USERNAME + " TEXT NOT NULL, " +
                Settings.COLUMN_PASSWORD + " TEXT NOT NULL, " +
                Settings.COLUMN_FIRSTNAME + " TEXT NOT NULL, " +
                Settings.COLUMN_LASTNAME + " TEXT NOT NULL, " +
                Settings.COLUMN_EMAIL + " TEXT, " +
                Settings.COLUMN_PHONE + " TEXT, " +
                Settings.COLUMN_ACTIVE + " BOOLEAN NOT NULL, " +
                Settings.COLUMN_USEROLE + " TEXT NOT NULL);";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS " + Settings.TABLE_NAME_05 + " (" +
                Settings.COLUMN_ID +"INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Settings.COLUMN_CREATED_DATE + " DATETIME NOT NULL, " +
                Settings.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + Settings.COLUMN_USER_ID + ") REFERENCES " +
                TABLE_NAME_06 + " (" + Settings.COLUMN_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS " + Settings.TABLE_NAME_04 + " (" +
                Settings.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Settings.COLUMN_NAME + " TEXT NOT NULL, " +
                Settings.COLUMN_BOOK_ID + " INTEGER NOT NULL, " +
                Settings.COLUMN_SALEORDER_ID+" INTEGER NOT NULL, " +
                Settings.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + Settings.COLUMN_SALEORDER_ID + ") REFERENCES " +
                Settings.TABLE_NAME_05 + " (" + Settings.COLUMN_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION, " +
                "FOREIGN KEY (" + Settings.COLUMN_BOOK_ID + ") REFERENCES " +
                Settings.TABLE_NAME_01 + " (" + Settings.COLUMN_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS " + Settings.TABLE_NAME_03 + " (" +
                Settings.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Settings.COLUMN_STOCK + " INTEGER NOT NULL, " +
                Settings.COLUMN_BOOK_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + Settings.COLUMN_BOOK_ID + ") REFERENCES " +
                Settings.TABLE_NAME_01 + " (" + Settings.COLUMN_ID + ") ON DELETE NO ACTION ON UPDATE NO ACTION);";
        db.execSQL(q);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public long  InsertOrderDetail(String name, int bookId, int saleOrderId, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Settings.COLUMN_NAME, name);
        values.put(Settings.COLUMN_BOOK_ID, bookId);
        values.put(Settings.COLUMN_SALEORDER_ID, saleOrderId);
        values.put(Settings.COLUMN_AMOUNT, amount);
        long newRowId = db.insert(Settings.TABLE_NAME_04, null, values);
        db.close();
        return  newRowId;
    }
    public long  InsertInventory(int stock, int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Settings.COLUMN_STOCK, stock);
        values.put(Settings.COLUMN_BOOK_ID, bookId);
        long newRowId = db.insert(Settings.TABLE_NAME_03, null, values);
        db.close();
        return  newRowId;
    }
    public long  InsertSaleOrder(Time createdDate, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Settings.COLUMN_CREATED_DATE, createdDate.getTime());
        values.put(Settings.COLUMN_USER_ID, userId);
        long newRowId = db.insert(Settings.TABLE_NAME_05, null, values);
        db.close();
        return  newRowId;
    }
    public long InsertUser(String username, String pass, String firstName, String lastName, String email, String phone, Boolean active, String userole ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Settings.COLUMN_USERNAME, username);
        values.put(Settings.COLUMN_PASSWORD, pass);
        values.put(Settings.COLUMN_FIRSTNAME, firstName);
        values.put(Settings.COLUMN_LASTNAME, lastName);
        values.put(Settings.COLUMN_EMAIL, email);
        values.put(Settings.COLUMN_PHONE, phone);
        values.put(Settings.COLUMN_ACTIVE, active);
        values.put(Settings.COLUMN_USEROLE, userole);

        long newRowId = db.insert(Settings.TABLE_NAME_06, null, values);
        db.close();
        return  newRowId;
    }
    public long  InsertCategory(String cateName, String decription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Settings.COLUMN_NAME, cateName);
        values.put(Settings.COLUMN_DECRIPTION, cateName);
        long newRowId = db.insert(Settings.TABLE_NAME_02, null, values);
        db.close();
        return  newRowId;
    }

    public long  InsertPublisher(String cateName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Settings.COLUMN_NAME, cateName);
        long newRowId = db.insert(Settings.TABLE_NAME_07, null, values);
        db.close();
        return  newRowId;
    }

    public long  InsertBook(String title, String decription, double price, String author, String pubYear,
                                 Boolean condition, int cateId, int publisherId, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Settings.COLUMN_TITLE, title);
        values.put(Settings.COLUMN_DECRIPTION, decription);
        values.put(Settings.COLUMN_PRICE, price);
        values.put(Settings.COLUMN_AUTHORS, author);
        values.put(Settings.COLUMN_PUBLICATION_YEAR, pubYear);
        values.put(Settings.COLUMN_CONDITION, condition);
        values.put(Settings.COLUMN_CATEGORY_ID, cateId);
        values.put(Settings.COLUMN_PUBLISHER_ID, publisherId);
        values.put(Settings.COLUMN_IMAGE, image);
        long newRowId = db.insert(Settings.TABLE_NAME_01, null, values);
        db.close();
        return  newRowId;
    }


    public boolean CheckUser(String username, String pw) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + Settings.TABLE_NAME_06 +
                        " WHERE " + Settings.COLUMN_USERNAME + " = ? AND " + Settings.COLUMN_PASSWORD + " = ?",
                new String[]{username, pw});
        cursor.close();
        db.close();
        return cursor.getCount() > 0;
    }
    public Cursor getListBook() {
        String query = "SELECT * FROM " + TABLE_NAME_01;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);

        return cursor;
    }

    public List<UserData> getUserData(){
        List<UserData> userDataList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT user.first_name, user.last_name, sale_order.create_date, order_detail.mount " +
                "FROM user " +
                "JOIN sale_order ON user.id = sale_order.user_id " +
                "JOIN order_detail ON sale_order.id = order_detail.order_id;";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                try{
                    String fName = cursor.getString((cursor.getColumnIndex("first_name")));
                    String lName = cursor.getString((cursor.getColumnIndex("last_name")));
                    //String userName = fName + " " + lName;
                    String createDate = cursor.getString(cursor.getColumnIndex("create_date"));
                    int orderDetailMount = cursor.getInt(cursor.getColumnIndex("mount"));

                    UserData userdData = new UserData(fName, lName, createDate, orderDetailMount);
                    userDataList.add(userdData);

                }catch(Exception ex){

                }


            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();

        return userDataList;
    }

}
