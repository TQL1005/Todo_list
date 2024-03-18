package com.fatscompany.bookseftonline.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fatscompany.bookseftonline.Entitis.Category;

import java.util.List;
@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM category ")
    List<Category> selectAll();
    @Query("SELECT * FROM category where name = :name ")
    Category checkCateExits(String name);
    @Query("SELECT * FROM Category WHERE id=:id")
    Category findById(String id);

    @Insert
    void insert(Category... cates);

    @Update
    void update(Category... cates);

    @Delete
    void delete(Category... cates);

    @Query("SELECT * FROM category WHERE name <> 'Empty'")
    List<Category> selectAllExceptEmpty();
}
