package com.fatscompany.bookseftonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.databinding.ActivityAdminBinding;
import com.fatscompany.bookseftonline.databinding.ActivityCategoryBookResultBinding;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.BookSearchAdapter;

public class CategoryBookResult extends AppCompatActivity {
    List<Book> bookListWithCategory;
    Adapter.CategoryBookResult categoryBookResult;
    private RecyclerView rcvBookCategoryResult;

    ActivityCategoryBookResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBookResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.btnBackMenu) {
                onBackPressed();
            }
            return false;
        });
        rcvBookCategoryResult = findViewById(R.id.rcvCategoryBook);
        Intent i = getIntent();
        Bundle myBundle = i.getBundleExtra("bundalCateLayout");
        String cateKw = myBundle.getString("stringCate");
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getInstance(CategoryBookResult.this);

                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {

                        bookListWithCategory = database.bookDao().getBooksInCategory(cateKw);
                        if (bookListWithCategory != null) {
                            CategoryBookResult.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    categoryBookResult = new Adapter.CategoryBookResult(CategoryBookResult.this, bookListWithCategory);

                                    rcvBookCategoryResult.setAdapter(categoryBookResult);
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_nav_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnBackMenu) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_cart) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}