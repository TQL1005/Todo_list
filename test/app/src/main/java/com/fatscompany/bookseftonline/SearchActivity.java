package com.fatscompany.bookseftonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.databinding.ActivityMainBinding;
import com.fatscompany.bookseftonline.databinding.ActivitySearchBinding;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.BookAdapter;
import Adapter.BookSearchAdapter;
import Adapter.CategoryAdapter;

public class SearchActivity extends AppCompatActivity {
    private List<Book> myListResult;
    BookSearchAdapter bookSearchAdapter;
    ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);
        setSupportActionBar(binding.toolbar);
        LinearLayoutManager bookLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        RecyclerView rcvResult = findViewById(R.id.rcvResult);
        rcvResult.setLayoutManager(bookLayoutManager);
        Intent i = getIntent();
        Bundle myBundle = i.getBundleExtra("keySearch");
        String kw = myBundle.getString("querySearch");
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getInstance(SearchActivity.this);

                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {

                        myListResult = database.bookDao().findByTitleContainingKeyword(kw);
                        if (myListResult != null) {
                            SearchActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bookSearchAdapter = new BookSearchAdapter(SearchActivity.this, myListResult);

                                    rcvResult.setAdapter(bookSearchAdapter);
                                }
                            });
                        }
                    }
                });
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_nav_top, menu);
        return super.onCreateOptionsMenu(menu);
    }
}