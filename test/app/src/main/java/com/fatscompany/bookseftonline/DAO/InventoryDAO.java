package com.fatscompany.bookseftonline.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.fatscompany.bookseftonline.Entitis.Inventory;

import java.util.List;

@Dao
public interface InventoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInventory(Inventory inventory);

    @Update
    void updateInventory(Inventory inventory);

    @Delete
    void deleteInventory(Inventory inventory);

    @Query("SELECT * FROM Inventory")
    List<Inventory> getAllInventories();

    // Add more queries as needed for your application
}
