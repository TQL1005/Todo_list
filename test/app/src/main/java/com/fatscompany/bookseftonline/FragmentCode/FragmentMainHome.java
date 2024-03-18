package com.fatscompany.bookseftonline.FragmentCode;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.Category;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.SeeAllActivity;
import com.fatscompany.bookseftonline.UserSessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.BookAdapter;
import Adapter.CategoryAdapter;


public class FragmentMainHome extends Fragment {


    public FragmentMainHome() {
    }

    private List<Book> myLBook;
    private List<Book> bestSellerBook;
    private List<Category> cates;

    private BookAdapter bookAdapter, bestSellerAdapter;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        // Thiết lập LayoutManager
        LinearLayoutManager bookLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager bookSoldLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        // Tìm RecyclerView bằng findViewById
        RecyclerView rcv = view.findViewById(R.id.rcvDisplayBook);
        RecyclerView rcvBestSeller = view.findViewById(R.id.rcvBestSeller);
        RecyclerView rcvCate = view.findViewById(R.id.rcvCategory);
        rcv.setLayoutManager(bookLayoutManager);
        rcvCate.setLayoutManager(categoryLayoutManager);
        rcvBestSeller.setLayoutManager(bookSoldLayout);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getInstance(getActivity());

                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        myLBook = database.bookDao().getAnyTenBooks();
                        cates = database.categoryDao().selectAll();
                        bestSellerBook = database.bookDao().getTopSoldBooks();
                        if (myLBook != null || cates != null || bestSellerBook != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bookAdapter = new BookAdapter(getContext(), myLBook);
                                    bestSellerAdapter = new BookAdapter(getContext(), bestSellerBook);
                                    categoryAdapter = new CategoryAdapter(getContext(), cates);
                                    rcvCate.setAdapter(categoryAdapter);
                                    rcv.setAdapter(bookAdapter);
                                    rcvBestSeller.setAdapter(bestSellerAdapter);

                                }
                            });
                        }
                    }
                });
            }
        });


        Button btnSeeAll = (Button) view.findViewById(R.id.btnSeeAll);
        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeeAllActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void openBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.book_detail, null);

        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.show();

    }


}