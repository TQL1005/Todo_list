package Adapter;

import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.Category;

import java.util.List;

public interface iClickItemBookListener {
    public void onClickItemBook(List<Book> book);

    public void onClickItemBookEdit(Book book);

    public void onClickItemBook(Category category);

    void onPositiveClick(String title, String description);

    void onNegativeClick();
}
