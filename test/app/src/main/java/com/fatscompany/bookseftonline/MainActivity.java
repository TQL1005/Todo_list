
package com.fatscompany.bookseftonline;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.fatscompany.bookseftonline.Database.DatabaseAdapter;
import com.fatscompany.bookseftonline.FragmentCode.SearchFragment;
import com.fatscompany.bookseftonline.FragmentCode.ViewPagerAdapter;
import com.fatscompany.bookseftonline.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    DatabaseAdapter db;
    private ActivityMainBinding binding;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);
        setSupportActionBar(binding.toolbar);

        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_search) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.action_cart) {
                Intent intent = new Intent(MainActivity.this, CartLayout.class);
                startActivity(intent);
            }
            return false;
        });

        binding.navbottom.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                binding.viewPager2.setCurrentItem(0);
            } else if (item.getItemId() == R.id.favorite) {
                binding.viewPager2.setCurrentItem(1);
            } else {
                binding.viewPager2.setCurrentItem(2);
            }

            return true;

        });
        replaceViewPager(0);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_nav_top, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);

                /// tạo bundel để chứa dữ liệu
                Bundle myBundle = new Bundle();
                /// thêm dữ kiệu vào bundel
                myBundle.putString("querySearch", query);

                /// đữa bundel vào intent
                i.putExtra("keySearch", myBundle);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }


    private void replaceViewPager(int position) {
        viewPagerAdapter = new ViewPagerAdapter(this);
        binding.viewPager2.setAdapter(viewPagerAdapter);
        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        binding.navbottom.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        binding.navbottom.getMenu().findItem(R.id.favorite).setChecked(true);
                        break;
                    case 2:
                        binding.navbottom.getMenu().findItem(R.id.more).setChecked(true);
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        UserSessionManager sessionManager = new UserSessionManager(this);
        sessionManager.clearUserDetails();
    }

}