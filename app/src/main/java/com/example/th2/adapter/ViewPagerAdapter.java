package com.example.th2.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.th2.fragments.FragmentHome;
import com.example.th2.fragments.FragmentInfor;
import com.example.th2.fragments.FragmentSearch;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int pageNumber;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int pageNumber) {
        super(fm);
        this.pageNumber = pageNumber;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentInfor();
            case 2:
                return new FragmentSearch();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageNumber;
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:return "Home";
//            case 1: return "Thông tin";
//            case 2: return "Tìm kiếm";
//        }
//        return "";
//    }
}
