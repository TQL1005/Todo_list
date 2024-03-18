package com.fatscompany.bookseftonline.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.fatscompany.bookseftonline.Entitis.Category;
import com.fatscompany.bookseftonline.Entitis.Publishers;

import java.util.List;

@Dao
public interface PublishersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPublisher(Publishers publisher);

    @Update
    void updatePublisher(Publishers publisher);

    @Delete
    void deletePublisher(Publishers publisher);

    @Query("SELECT * FROM Publishers")
    List<Publishers> getAllPublishers();

    @Query("SELECT * FROM Publishers where name = :name ")
    Publishers checkPublisherExits(String name);


}
