package com.fatscompany.bookseftonline.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fatscompany.bookseftonline.Entitis.Book;

import java.util.List;

@Dao
public interface BookDAO {
    @Query("SELECT * FROM Book")
    List<Book> getAllBook();

    @Query("SELECT * FROM Book WHERE id=:id")
    Book findById(int id);

    @Query("SELECT * FROM Book WHERE title LIKE '%' || :keyword || '%'")
    List<Book> findByTitleContainingKeyword(String keyword);

    @Insert
    void insert(Book... books);

    @Query("SELECT * FROM Book LIMIT 10")
    List<Book> getAnyTenBooks();

    @Update
    void update(Book... books);

    @Delete
    void delete(Book... books);

    @Query("SELECT * FROM Book where title = :title")
    Book checkBookExist(String title);

    @Query("SELECT * FROM Book where id = :id")
    Book findBookByID(int id);

    @Query("SELECT * FROM book WHERE category_id IN (SELECT id FROM category WHERE name = :categoryName)")
    List<Book> getBooksInCategory(String categoryName);

    @Query("SELECT * FROM Book ORDER BY soldBook DESC LIMIT 15")
    List<Book> getTopSoldBooks();

    @Query("SELECT * FROM Book WHERE category_id=:categoryId")
    List<Book> findByCategoryId(int categoryId);

    @Query("UPDATE Book SET category_id = null WHERE category_id = :categoryId")
    void updateCategoryToNull(int categoryId);

    @Query("SELECT * FROM Book WHERE id IN (SELECT DISTINCT book_id FROM order_detail)")
    List<Book> getSoldBooks();

    @Query("SELECT book.*, SUM(order_detail.amount) AS total_amount " +
            "FROM book " +
            "INNER JOIN order_detail ON book.id = order_detail.book_id " +
            "GROUP BY book.id")
    int getBooksTotalAmount();
}
