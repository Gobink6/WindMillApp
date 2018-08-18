package com.robotics.katamaron.windmill;

import android.app.ActionBar;
import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> pages = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private  List<String> list = new ArrayList<>();
    private Map<Fragment, Integer> fragmentsPosition = new HashMap<>();
    private List<ClipData.Item> items = new ArrayList<ClipData.Item>();
    private ViewPager mPager;
    private ActionBar mActionBar;

    private long baseId = 0;
    int mNoOfTabs,mNoOfPager;
  public  boolean mStoploop;

    public PageAdapter(FragmentManager fm, List<Fragment> pages, List<String> list)
    {
        super (fm);

        //this . mFragments = NumberOfTabs;
       // this.mFragments = fragment;
        this.pages = pages;
        this.list = list;
    }

public void   notify(List<Fragment> pages ,List<String> list){
    this.pages = pages;
    this.list =     list;
    notifyDataSetChanged();
    notify();
}
    @Override
    public Fragment getItem(int position) {
       return pages.get(position);

    }


    @Override
    public int getCount() {
       // return mFragments.size();
       return pages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (true){

        }
        return list.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
      //  return super.isViewFromObject(view, object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {

        // refresh all fragments when data set changed
        return PageAdapter.POSITION_NONE;
    }




}
