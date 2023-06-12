package com.example.appdatdoan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appdatdoan.fragment.CartFragment;
import com.example.appdatdoan.fragment.FeedbackFragment;
import com.example.appdatdoan.fragment.HistoryFragment;
import com.example.appdatdoan.fragment.HomeFragment;
import com.example.appdatdoan.fragment.SettingFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new HomeFragment();
            case 1:
                return new CartFragment();
            case 2:
                return new HistoryFragment();
            case 3:
                return new FeedbackFragment();

            case 4 :
                return new SettingFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
