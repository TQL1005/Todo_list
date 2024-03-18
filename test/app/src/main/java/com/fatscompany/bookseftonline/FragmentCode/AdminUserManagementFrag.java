package com.fatscompany.bookseftonline.FragmentCode;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fatscompany.bookseftonline.AdminActivity;
import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Database.DatabaseAdapter;
import com.fatscompany.bookseftonline.Entitis.User;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.databinding.AddUserBinding;
import com.fatscompany.bookseftonline.databinding.FragmentAdminUserManagementBinding;
import com.fatscompany.bookseftonline.databinding.UpdateUserBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Adapter.UserAdapter;
import Adapter.iClickItemUserListener;


public class AdminUserManagementFrag extends Fragment {

    private FragmentAdminUserManagementBinding binding;
    private AddUserBinding addUserBinding;
    private DatabaseAdapter dbAdapter;

    private List<User> userList;

    private UserAdapter userAdapter;

    private AdminActivity adminActivity;

    private iClickItemUserListener listener;

    private UpdateUserBinding updateUserBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentAdminUserManagementBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.btnCreateUser.setOnClickListener(v -> {
            showAddUserContent();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView rcv = view.findViewById(R.id.userRecyclerView);
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
                        userList = database.userDao().selectAll();

                        if (userList != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    userAdapter = new UserAdapter(userList, getContext(), new iClickItemUserListener() {
                                        @Override
                                        public void onClickItemUser(User user) {
                                            showUpdateContent(user);
                                        }
                                    });
                                    rcv.setAdapter(userAdapter);
                                }
                            });
                        }
                    }
                });
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rcv);



        return view;
    }

    public void showUpdateContent(User user){
        updateUserBinding = UpdateUserBinding.inflate(getLayoutInflater());
        TextInputEditText txtuName = updateUserBinding.txtuName2;
        TextInputEditText txtPw = updateUserBinding.txtpassword2;
        TextInputEditText txtfName = updateUserBinding.txtfName2;
        TextInputEditText txtlName = updateUserBinding.txtlName2;
        TextInputEditText txtMail = updateUserBinding.txtMail2;
        TextInputEditText txtRole = updateUserBinding.txtRole2;
        TextInputEditText txtSDT = updateUserBinding.txtSDT2;

        txtuName.setText(user.getUsername());
        txtMail.setText(user.getEmail());
        txtfName.setText(user.getFirstName());
        txtPw.setText(user.getPassword());
        txtRole.setText(user.getUserRole());
        txtlName.setText(user.getLastName());
        txtSDT.setText(user.getPhone());

        View updateUserView = updateUserBinding.getRoot();
        binding.countainerFrameLayout.removeAllViews();
        binding.countainerFrameLayout.addView(updateUserView);

        updateUserBinding.btnUpdate.setOnClickListener(v -> {
            user.setEmail(txtMail.getText().toString());
            user.setUserRole(txtRole.getText().toString());
            user.setPassword(txtPw.getText().toString());
            user.setPhone(txtSDT.getText().toString());
            user.setFirstName(txtfName.getText().toString());
            user.setLastName(txtlName.getText().toString());
            user.setUsername(txtuName.getText().toString());

            AppDatabase db = AppDatabase.getInstance(getActivity());
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    db.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                db.userDao().update(user);
                                Log.d("UpdateUser", "User updated successfully");
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(requireContext(), "User updated successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception ex) {
                                Log.e("UpdateUser", "Error updating user: " + ex.getMessage());
                            }
                        }
                    });
                }
            });
        });
    }

    private void showDeleteUserContent(){

    }
    private void showAddUserContent() {
        addUserBinding = AddUserBinding.inflate(getLayoutInflater());
        // Get the root view of the inflated layout
        View addUserView = addUserBinding.getRoot();
        binding.countainerFrameLayout.removeAllViews();
        // Add the inflated layout to a container in the fragment's layout``
        binding.countainerFrameLayout.addView(addUserView);

        addUserBinding.btnSave.setOnClickListener(v -> {
            btnsaveAddUserClick();
            adminActivity.replaceFragment(new AdminUserManagementFrag());
        });
    }



    private void btnsaveAddUserClick() {
        String uName = addUserBinding.txtuName.getText().toString();
        String pw = addUserBinding.txtpassword.getText().toString();
        String role = addUserBinding.txtRole.getText().toString();
        String fName = addUserBinding.txtfName.getText().toString();
        String lName = addUserBinding.txtfName.getText().toString();
        String mail = addUserBinding.txtMail.getText().toString();
        String sdt = addUserBinding.txtSDT.getText().toString();
        Boolean active = true;
        if (uName.isEmpty() || pw.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill out the fields", Toast.LENGTH_SHORT).show();
        } else {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    db.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(uName, pw, fName, lName, mail, sdt, active, role);

                            try {
                                db.userDao().insert(user);
                            }
                            catch (Exception ex){

                            }
                        }
                    });
                }
            });
        }
    }

    //swipe to delete item SimpleCallback(Drag=0 - false, Swipe dir - left)
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Snackbar snackbar = Snackbar.make(binding.countainerFrameLayout, "Item deleted successfully", Snackbar.LENGTH_LONG);
            snackbar.show();

            int position = viewHolder.getAdapterPosition();
            User userDeleted = userList.get(position);

            userList.remove(viewHolder.getAdapterPosition());
            userAdapter.notifyDataSetChanged();

            AppDatabase db = AppDatabase.getInstance(getActivity());
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    db.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                db.userDao().delete(userDeleted);
                                Log.d("UpdateUser", "User delete successfully");
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });
                            } catch (Exception ex) {
                                Log.e("UpdateUser", "Error deleting user: " + ex.getMessage());
                            }
                        }
                    });
                }
            });
        }
    };
}