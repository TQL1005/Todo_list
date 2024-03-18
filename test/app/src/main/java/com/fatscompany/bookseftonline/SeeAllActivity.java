package com.fatscompany.bookseftonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.R;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.BookAdapter;
import Adapter.CategoryAdapter;

public class SeeAllActivity extends AppCompatActivity {

    private RecyclerView rcvSeeAll;
    private BookAdapter bookAdapterer;
    List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        rcvSeeAll = findViewById(R.id.rcvSeeAll);
        rcvSeeAll.setLayoutManager(new GridLayoutManager(this, 2));

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getInstance(SeeAllActivity.this);

                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        bookList = database.bookDao().getAllBook();

                        if (bookList != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bookAdapterer = new BookAdapter(SeeAllActivity.this, bookList);

                                    rcvSeeAll.setAdapter(bookAdapterer);

                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
