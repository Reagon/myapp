package com.zbao.android.weixinhot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.zbao.android.R;
import com.zbao.android.base.BaseFrgment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbao on 16/6/29.
 */
public class NewsFragment extends BaseFrgment{
    private Context ct;
    @BindView(R.id.title)
    TextView barTitle;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.hot_tab)
    SlidingTabLayout tab;
    @BindView(R.id.hot_viewPager)
    ViewPager mViewPager;
    private boolean isVisible = false;
    private String[] mTitles = {"实事","头条", "热门", "科技", "娱乐","汽车","房产","手机","笑话","动漫","读书"};
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    public static NewsFragment newInstance(){
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ct = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater,container);
    }

    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        barTitle.setText(R.string.hot);
        back.setVisibility(View.GONE);
        mViewPager.setOffscreenPageLimit(1);
        return view;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(WXHotFragment.newInstance(mTitles[i]));
        }

        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        tab.setViewPager(mViewPager);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return WXHotFragment.newInstance(mTitles[position]);
//            return mFragments.get(position);
        }
    }


}
