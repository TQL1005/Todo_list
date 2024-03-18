package com.fatscompany.bookseftonline.FragmentCode;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fatscompany.bookseftonline.AdminActivity;
import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Database.DatabaseAdapter;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.Category;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.databinding.AddBookBinding;
import com.fatscompany.bookseftonline.databinding.AddCategoryBinding;
import com.fatscompany.bookseftonline.databinding.AddUserBinding;
import com.fatscompany.bookseftonline.databinding.FragmentAdminProductManagementBinding;
import com.fatscompany.bookseftonline.databinding.ItemProductBinding;
import com.fatscompany.bookseftonline.databinding.UpdateBookBinding;
import com.fatscompany.bookseftonline.databinding.UpdateUserBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.AdminBookAdapter;
import Adapter.BookAdapter;
import Adapter.CateroryViewModel;
import Adapter.ProducAdapter;
import Adapter.UserAdapter;
import Adapter.iClickItemBookListener;
import Adapter.iClickItemCateListener;
import Adapter.iClickItemUserListener;


public class AdminProductManagement extends Fragment {

    private FragmentAdminProductManagementBinding binding;
    private AddBookBinding addBookBinding;

    private AddCategoryBinding addCategoryBinding;

    private List<Category> cateList;

    private ProducAdapter producAdapter;
    private AdminActivity adminActivity;

    private ItemProductBinding itemProductBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAdminProductManagementBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnAddCat.setOnClickListener(v -> {
            showAddCateContent();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView rcv = view.findViewById(R.id.catRecyclerView);
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
                        cateList = database.categoryDao().selectAllExceptEmpty();
                        if (cateList != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    runProductAdapter(getContext(),cateList,rcv, view);
                                }
                            });
                        }
                    }
                });
            }
        });
        return view;
    }
    private void runProductAdapter(Context context, List<Category> cateList, RecyclerView rcv, View view){
        producAdapter = new ProducAdapter(context,cateList, new iClickItemBookListener() {
            @Override
            public void onClickItemBook(List<Book> book) {
            }

            @Override
            public void onClickItemBookEdit(Book book) {

            }

            @Override
            public void onClickItemBook(Category cate) {
                showUpdateCate(cate);
            }

            @Override
            public void onPositiveClick(String title, String description) {

            }

            @Override
            public void onNegativeClick() {

            }
        });
        rcv.setAdapter(producAdapter);
    }
    public void showUpdateCate(Category cate){

        itemProductBinding = ItemProductBinding.inflate(getLayoutInflater());
        TextView txtName = itemProductBinding.txtCatName;

        txtName.setText(cate.getName().toString());

            View successView = LayoutInflater.from(adminActivity).inflate(R.layout.update_category, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this.adminActivity);
            builder.setView(successView);

            TextInputEditText txtNameCate ;
            TextInputEditText txtDesCate ;


            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            txtNameCate=alertDialog.findViewById(R.id.cateroryEditText);
            txtDesCate=alertDialog.findViewById(R.id.desCateEditText);

            txtNameCate.setText(cate.getName().toString());
            txtDesCate.setText(cate.getDescription().toString());

            Button btnUpdate = alertDialog.findViewById(R.id.btnUpdateCate);
            Button btnClose = alertDialog.findViewById(R.id.btnCloseCate);
            Button btnDel = alertDialog.findViewById(R.id.btnDelCate);
            btnUpdate.setOnClickListener(new View.OnClickListener() {

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
                                    cate.setName(txtNameCate.getText().toString());
                                    cate.setDescription(txtDesCate.getText().toString());
                                    db.categoryDao().update(cate);
                                    Log.d("UpdateCaterory", "Caterory updated successfully");
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(requireContext(), "Category updated successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    adminActivity.replaceFragment(new AdminProductManagement());
                                    alertDialog.cancel();

                                } catch (Exception ex) {
                                    Log.e("UpdateCaterory", "Error updating caterory: " + ex.getMessage());
                                    }
                                }
                            });
                        }
                    });
                }
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

                                        List<Book> delBook = db.bookDao().findByCategoryId(cate.getId());
                                        for (Book b: delBook
                                             ) {
                                            b.setCategoryId(0);
                                            //db.bookDao().updateCategoryToNull(b.id);
                                            db.bookDao().update(b);
                                        }
                                        db.categoryDao().delete(cate);

                                        Log.d("UpdateCaterory", "Caterory deteted successfully");
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(requireContext(), "Category deteted successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        adminActivity.replaceFragment(new AdminProductManagement());
                                        alertDialog.cancel();

                                    } catch (Exception ex) {
                                        Log.e("UpdateCaterory", "Error deleting caterory: " + ex.getMessage());
                                    }
                                }
                            });
                        }
                    });
                }
            });
    }
    private void showAddCateContent() {
        addCategoryBinding = AddCategoryBinding.inflate(getLayoutInflater());
        // Get the root view of the inflated layout
        View addCateView = addCategoryBinding.getRoot();
        binding.countainerFrameLayout.removeAllViews();
        // Add the inflated layout to a container in the fragment's layout``
        binding.countainerFrameLayout.addView(addCateView);

        addCategoryBinding.btnAddCate.setOnClickListener(v -> {
            btnsaveAddProductClick();
            adminActivity.replaceFragment(new AdminProductManagement());
        });
    }

    private void btnsaveAddProductClick() {
        String title = addCategoryBinding.cateroryAddEditText.getText().toString();
        String decription = addCategoryBinding.desAddCateEditText.getText().toString();

        AppDatabase db = AppDatabase.getInstance(requireContext());
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                db.runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        Category cate = new Category(title,decription);

                        try {
                            db.categoryDao().insert(cate);
                        }
                        catch (Exception ex){

                        }
                    }
                });
            }
        });
    }

}