
package com.fatscompany.bookseftonline.FragmentCode;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatscompany.bookseftonline.AdminActivity;
import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Database.DatabaseAdapter;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.Category;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.databinding.AddBookBinding;
import com.fatscompany.bookseftonline.databinding.AddCategoryBinding;
import com.fatscompany.bookseftonline.databinding.FragmentAdminBookManagementBinding;
import com.fatscompany.bookseftonline.databinding.FragmentAdminProductManagementBinding;
import com.fatscompany.bookseftonline.databinding.ItemProductBinding;
import com.fatscompany.bookseftonline.databinding.UpdateBookBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.AdminBookAdapter;
import Adapter.ProducAdapter;
import Adapter.iClickItemBookListener;
import Adapter.iClickItemCateListener;


public class AdminBookManagement extends Fragment {

    private FragmentAdminBookManagementBinding binding;
    private AddBookBinding addBookBinding;

    private AddCategoryBinding addCategoryBinding;


    private DatabaseAdapter dbAdapter;

    private List<Book> bookList;


    private AdminBookAdapter adminBookAdapter;

    private AdminActivity adminActivity;

    private iClickItemCateListener listener;

    private UpdateBookBinding updateBookBinding;

    private ItemProductBinding itemProductBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAdminBookManagementBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnAddBookAdmin.setOnClickListener(v -> {
            showAddBookContent();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView rcv = view.findViewById(R.id.bookAdminRecyclerView);
        rcv.setLayoutManager(linearLayoutManager);

        adminActivity = (AdminActivity) getActivity();
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getInstance(getActivity());
                database.runInTransaction(new Runnable() {
                    @Override
                    public void run() {

                        bookList = database.bookDao().getAllBook();
                        if (bookList != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    runBookAdapter(getContext(),bookList,rcv);
                                }
                            });
                        }
                    }
                });
            }
        });
        return view;
    }
    public void runBookAdapter(Context context, List<Book> bookList, RecyclerView rcv){
        adminBookAdapter = new AdminBookAdapter(context,bookList, new iClickItemBookListener() {
            @Override
            public void onClickItemBook(List<Book> book) {
            }

            @Override
            public void onClickItemBookEdit(Book book) {
                showUpdateContent(book);
            }

            @Override
            public void onClickItemBook(Category cate) {

                //showUpdateCate(cate);
            }

            @Override
            public void onPositiveClick(String title, String description) {

            }

            @Override
            public void onNegativeClick() {

            }
        });
        rcv.setAdapter(adminBookAdapter);
    }
    public void showUpdateContent(Book book){
        View successView = LayoutInflater.from(adminActivity).inflate(R.layout.update_book, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.adminActivity);
        builder.setView(successView);

        TextInputEditText txtTitle = successView.findViewById(R.id.titleEditText);
        TextInputEditText txtDescription = successView.findViewById(R.id.descriptionEditText);
        TextInputEditText txtPrice = successView.findViewById(R.id.priceEditText);
        TextInputEditText txtAuthors = successView.findViewById(R.id.authorsEditText);
        TextInputEditText txtPublicationYear = successView.findViewById(R.id.publicationYearEditText);
        TextInputEditText txtSoldBook = successView.findViewById(R.id.conditionEditText);
        TextInputEditText txtImage = successView.findViewById(R.id.imageEditText);

        txtTitle.setText(book.getTitle());
        txtDescription.setText(book.getDescription());
        txtPrice.setText(String.valueOf(book.getPrice()));
        txtAuthors.setText(book.getAuthors());
        txtPublicationYear.setText(String.valueOf(book.getPublicationYear()));
        txtImage.setText(book.getImage());
        txtSoldBook.setText(String.valueOf(book.getSoldBook()));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnUpdate = successView.findViewById(R.id.btnUpdate);
        Button btnClose = successView.findViewById(R.id.btnClose);
        Button btnDel = successView.findViewById(R.id.btnDel);

        btnUpdate.setOnClickListener(v -> {
            book.setTitle(txtTitle.getText().toString());
            book.setDescription(txtDescription.getText().toString());
            book.setAuthors(txtAuthors.getText().toString());
            book.setImage(txtImage.getText().toString());
            book.setPublicationYear(Integer.parseInt(txtPublicationYear.getText().toString()));
            book.setSoldBook(Integer.parseInt(txtSoldBook.getText().toString()));

            AppDatabase db = AppDatabase.getInstance(getActivity());
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    db.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                db.bookDao().update(book);
                                Log.d("UpdateProduct", "Book updated successfully");
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(requireContext(), "Book updated successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                adminActivity.replaceFragment(new AdminBookManagement());
                                alertDialog.cancel();
                            } catch (Exception ex) {
                                Log.e("UpdateProduct", "Error updating book: " + ex.getMessage());
                            }
                        }
                    });
                }
            });
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = AppDatabase.getInstance(getActivity());
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.runInTransaction(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    db.bookDao().delete(book);
                                    Log.d("UpdateCaterory", "Book deteted successfully");
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(requireContext(), "Book deteted successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    adminActivity.replaceFragment(new AdminBookManagement());
                                    alertDialog.cancel();

                                } catch (Exception ex) {
                                    Log.e("UpdateCaterory", "Error deleting book: " + ex.getMessage());
                                }
                            }
                        });
                    }
                });
            }
        });

    }
    /////////
    private void showAddBookContent() {
        addBookBinding = AddBookBinding.inflate(getLayoutInflater());
        // Get the root view of the inflated layout
        View addBookView = addBookBinding.getRoot();
        binding.countainerFrameLayout.removeAllViews();
        // Add the inflated layout to a container in the fragment's layout``
        binding.countainerFrameLayout.addView(addBookView);

        addBookBinding.btnSaveBook.setOnClickListener(v -> {
            try {

                btnsaveAddBookClick();
            }
            catch (Exception e){}
            adminActivity.replaceFragment(new AdminBookManagement());
        });
    }

    private void btnsaveAddBookClick() {
        String title = addBookBinding.titleEditText.getText().toString();
        String decription = addBookBinding.descriptionEditText.getText().toString();
        Double price = Double.parseDouble(addBookBinding.priceEditText.getText().toString());
        String author = addBookBinding.authorsEditText.getText().toString();
        int year = Integer.parseInt(addBookBinding.publicationYearEditText.getText().toString());
        int cateId = Integer.parseInt(addBookBinding.cateEditText.getText().toString());
        //int publisherId = Integer.parseInt(addBookBinding.publisherEditText.getText().toString());
        String img = addBookBinding.imageEditText.getText().toString();

        AppDatabase db = AppDatabase.getInstance(requireContext());
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                db.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        Book book = new Book(title,decription,price,author,year,true,cateId,1,img,0);

                        try {
                            db.bookDao().insert(book);
                        }
                        catch (Exception ex){
                        }
                    }
                });
            }
        });
    }

}
