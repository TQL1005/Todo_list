package com.fatscompany.bookseftonline.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.fatscompany.bookseftonline.AppDatabase;
import com.fatscompany.bookseftonline.Database.DatabaseAdapter;
import com.fatscompany.bookseftonline.Database.DatabaseController;
import com.fatscompany.bookseftonline.DbData;
import com.fatscompany.bookseftonline.Entitis.User;
import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.databinding.ActivitySignUpAppBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpAppBinding binding;
    private DatabaseAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpAppBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppDatabase db = AppDatabase.getInstance(SignUp.this);
        Glide.with(this)
                .load(R.drawable.logo)
                .into(binding.imgSignUp);


        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FName = binding.signUpFName.getText().toString();
                String LName = binding.signUpLName.getText().toString();
                String email = binding.signUpEmail.getText().toString();
                String phone = binding.signUpSDT.getText().toString();
                String acName = binding.signUpAName.getText().toString();
                String pw = binding.signUpPw.getText().toString();
                Boolean active = false;
                String userRole = "CUSTOMER";

                if (FName.isEmpty() || LName.isEmpty() || email.isEmpty() || phone.isEmpty() || acName.isEmpty() || pw.isEmpty()) {
                    Toast.makeText(SignUp.this, "Thiếu thông tin vui lòng nhập đủ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (acName.length() < 6 || pw.length() < 6) {
                    Toast.makeText(SignUp.this, "Username và Password phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidUsernameFormat(acName)) {
                    Toast.makeText(SignUp.this, "Username phải chứa ít nhất 1 ký tự chữ cái và không có ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidNameFormat(FName) || !isValidNameFormat(LName)) {
                    Toast.makeText(SignUp.this, "Tên phải chứa ít nhất 2 ký tự và không có ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(email)) {
                    Toast.makeText(SignUp.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidPhoneNumber(phone)) {
                    Toast.makeText(SignUp.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.runInTransaction(new Runnable() {
                            @Override
                            public void run() {
                                User existingUsernameUser = db.userDao().findUserByUsername(acName);
                                if (existingUsernameUser != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SignUp.this, "Tên người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    return;
                                }

                                User existingEmailUser = db.userDao().findUserByEmail(email);
                                if (existingEmailUser != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SignUp.this, "Email đã được sử dụng", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    return;
                                }

                                db.userDao().insert(new User(acName, pw, FName, LName, email, phone, false, userRole));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SignUp.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                });


            }
        });
    }

    private boolean isValidUsernameFormat(String username) {
        String pattern = "^[a-zA-Z][a-zA-Z0-9]*$";
        return username.matches(pattern);
    }

    private boolean isValidNameFormat(String name) {

        String pattern = "^[a-zA-Z]{2,}$";
        return name.matches(pattern);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        String cleanPhoneNumber = phone.replaceAll("[^0-9]", "");


        return cleanPhoneNumber.length() == 10;
    }

}