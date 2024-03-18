package com.fatscompany.bookseftonline;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatscompany.bookseftonline.Entitis.User;

public class UserSessionManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USER_ID = "user_id"; // Thêm trường user ID

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserSessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserDetails(User user) {
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putInt(KEY_USER_ID, user.getId()); // Lưu user ID

        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1); // Trả về -1 nếu không có giá trị
    }

    public void clearUserDetails() {
        editor.clear();
        editor.apply();
    }
}
