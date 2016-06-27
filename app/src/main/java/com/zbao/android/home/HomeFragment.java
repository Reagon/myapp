package com.zbao.android.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbao.android.R;
import com.zbao.android.base.BaseFrgment;
import com.zbao.android.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangbao on 16/6/27.
 */
public class HomeFragment extends BaseFrgment {

    @BindView(R.id.mLinear)
    LinearLayout mm;
    @BindView(R.id.text1)
    TextView mText1;
    @BindView(R.id.text2)
    TextView mText2;
    @BindView(R.id.text3)
    TextView mText3;
    @BindView(R.id.title)
    TextView barTitle;
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    @Override
    public View initView(LayoutInflater inflater,ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_contacts,container, false);
        ButterKnife.bind(this,view);
        barTitle.setText(R.string.homepage);
        return view;
    }

    @Override
    public void lazyLoad() {

    }

    @OnClick(R.id.text1)
    void clickText1(){
        T.showShort(mActivity,"Text1");
    }
    @OnClick(R.id.text2)
    void clickText2(){
        T.showShort(mActivity,"Text2");
    }
    @OnClick(R.id.text3)
    void clickText4(){
        T.showShort(mActivity,"Text3");
    }

}
