package com.example.newapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Tour.db";
    private static String user ;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the "users" table
        sqLiteDatabase.execSQL("CREATE TABLE users (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, role INTEGER, phone INTEGER,isDeleted boolean DEFAULT 0 )");

        // Create the "destinations" table
        sqLiteDatabase.execSQL("CREATE TABLE destinations (ID INTEGER PRIMARY KEY AUTOINCREMENT, destination TEXT, description TEXT, price DOUBLE,isDeleted boolean DEFAULT 0)");

        // Create the "tours" table with foreign key constraints
        sqLiteDatabase.execSQL("CREATE TABLE tours (ID INTEGER PRIMARY KEY AUTOINCREMENT, ID_destination INTEGER, start_date DATE, end_date DATE, amount INTEGER, pic1 TEXT, pic2 TEXT, pic3 TEXT, pic4 TEXT,isDeleted boolean DEFAULT 0 , FOREIGN KEY (ID_destination) REFERENCES destinations(ID))");

        // Create the "bill" table with foreign key constraints
        sqLiteDatabase.execSQL("CREATE TABLE bill (ID INTEGER PRIMARY KEY AUTOINCREMENT, ID_tours INTEGER, ID_users INTEGER, bill_date DATE, bill_money DATE, amount INTEGER,isDeleted boolean DEFAULT 0, FOREIGN KEY (ID_tours) REFERENCES tours(ID), FOREIGN KEY (ID_users) REFERENCES users(ID))");

        // Create the "feedback" table with foreign key constraints
        sqLiteDatabase.execSQL("CREATE TABLE feedback (ID INTEGER PRIMARY KEY AUTOINCREMENT, ID_users INTEGER, ID_destination INTEGER, feedback TEXT, date_feedback DATE,isDeleted boolean DEFAULT 0 , FOREIGN KEY (ID_users) REFERENCES users(ID), FOREIGN KEY (ID_destination) REFERENCES destinations(ID))");
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    // SQL code
    public boolean insertDataUser(String username,String password){
        SQLiteDatabase Databasename = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("role",1);
        long result = Databasename.insert("users",null,contentValues);
        if (result == -1) {return false;}
        else return true;
    }

    public boolean checkUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?",new String[]{username});
        if (cursor.getCount() > 0){
            cursor.close();
            myDB.close();
            return true;
        }else return false;
    }

    public boolean checkUser(String username, String pwd) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to retrieve the hashed password based on the username
        String query = "SELECT password FROM users WHERE username = ?";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            // Extract the hashed password from the cursor
            @SuppressLint("Range") String hashedPassword = cursor.getString(cursor.getColumnIndex("password"));

            // Verify if the entered password matches the hashed password
            boolean isPasswordCorrect = BCrypt.checkpw(pwd, hashedPassword);
            setUser(username.toString());

            // Close the cursor and database
            cursor.close();
            db.close();

            return isPasswordCorrect;
        } else {
            // Username not found
            cursor.close();
            db.close();
            return false;
        }
    }

    // retrive data
    public ArrayList<Destination> getDes(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Destination> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from destinations",null);
        while(cursor.moveToNext()){
            String destination = cursor.getString(cursor.getColumnIndex("destination"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            Double price = cursor.getDouble(cursor.getColumnIndex("price"));
            Destination destinationItem = new Destination(destination, description, price);
            list.add(destinationItem);

        }
        return list;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        System.out.println("User set to: " + this.user);
    }
}
