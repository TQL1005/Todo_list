package com.fatscompany.bookseftonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.fatscompany.bookseftonline.FragmentCode.AdminBookManagement;
import com.fatscompany.bookseftonline.FragmentCode.AdminProductManagement;
import com.fatscompany.bookseftonline.FragmentCode.AdminStatisticFrag;
import com.fatscompany.bookseftonline.FragmentCode.AdminUserManagementFrag;
import com.fatscompany.bookseftonline.databinding.ActivityAdminBinding;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityAdminBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.adminToolBar);
        /*
        toggle = new ActionBarDrawerToggle(
            this, drawlayout cua admin (trang ben ngoai), materialToolbar (cai menu truot),
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.adminDrawerlayout, binding.adminToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.adminDrawerlayout.addDrawerListener(toggle);
        toggle.syncState();


        binding.adminNav.setNavigationItemSelectedListener(this);
        binding.adminNav.bringToFront();

        // mac dinh la statistic
        binding.adminNav.setCheckedItem(R.id.statistic);
        replaceFragment(new AdminStatisticFrag());
    }


    /* khong cho out app khi back pressed */
    @Override
    public void onBackPressed() {
        if (binding.adminDrawerlayout.isDrawerOpen(GravityCompat.START)){
            binding.adminDrawerlayout.closeDrawer(GravityCompat.START);
        }
        else super.onBackPressed();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.statistic){
            replaceFragment(new AdminStatisticFrag());
        }
        else if (itemID == R.id.user_management) {
            replaceFragment(new AdminUserManagementFrag());
        }
        else if (itemID == R.id.Category_management) {
            replaceFragment(new AdminProductManagement());
        }
        else if (itemID == R.id.Book_management) {
            replaceFragment(new AdminBookManagement());
        }

        binding.adminDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.AdminFragLayout, fragment);
        fragmentTransaction.commit();
    }
}