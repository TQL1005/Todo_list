package com.fatscompany.bookseftonline.FragmentCode;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentMainHome();
            case 1:
                return new MainFavorite();
            case 2:
                return new FragmentMainMore();
            case 3:
                return new SearchFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
