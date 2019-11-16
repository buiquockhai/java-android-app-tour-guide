package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmentListTitles = new ArrayList<>();

    Bundle bundle;


//    public ViewPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }

    public ViewPagerAdapter(FragmentManager fm, Bundle bundle){
        super(fm);
        this.bundle=bundle;
    }

    @Override
    public Fragment getItem(int i) {
//        return fragmentList.get(i);
//        Fragment fragment=null;
//        switch (i){
//            case 0:
//                fragment= new FragmentList();
//                fragment.setArguments(bundle);
////                fragmentList.add(fragment);
//                break;
//            case 1:
//                fragment= new FagmentAnalyst();
//                fragment.setArguments(bundle);
////                fragmentList.add(fragment);
//                break;
//            case 2:
//                fragment= new FragmentImage();
//                fragment.setArguments(bundle);
////                fragmentList.add(fragment);
//                break;
//                default: fragment = null;
//        }
//        return fragment;
        fragmentList.get(i).setArguments(bundle);
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return  FragmentListTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentListTitles.get(position);
    }

    public void AddFragment(Fragment fragment, String Title){
        fragmentList.add(fragment);
        FragmentListTitles.add(Title);
    }
}
