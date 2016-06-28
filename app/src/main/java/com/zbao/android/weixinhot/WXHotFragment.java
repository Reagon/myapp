package com.zbao.android.weixinhot;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zbao.android.R;
import com.zbao.android.base.BaseFrgment;
import com.zbao.android.customview.xrecyclerview.ProgressStyle;
import com.zbao.android.customview.xrecyclerview.XRecyclerView;
import com.zbao.android.entity.WeiXinHotInfo;
import com.zbao.android.utils.API;
import com.zbao.android.utils.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhangbao on 16/6/27.
 */
public class WXHotFragment extends BaseFrgment {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R.id.title)
    TextView barTitle;

    private int pageNum = 1;
    private final int number = 15;
    private String keyWords = "";
    private WXHotAdapter mAdapter;


    public static WXHotFragment newInstance() {
        WXHotFragment fragment = new WXHotFragment();
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_wxhot, container, false);
        ButterKnife.bind(this, view);
        barTitle.setText(R.string.hot);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ct);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

//        View header = LayoutInflater.from(ct).inflate(R.layout.recyclerview_header, (ViewGroup)getActivity().findViewById(android.R.id.content),false);
//        mRecyclerView.addHeaderView(header);


        initListener();

        return view;
    }

    private void initListener(){
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                keyWords = "头条";
                httpGet(pageNum, keyWords);

            }
            @Override
            public void onLoadMore() {

            }
        });



    }


    @Override
    public void lazyLoad() {
        pageNum = 1;
        keyWords = "头条";
        httpGet(pageNum, keyWords);
    }

    private void httpGet(int pageNum, String keyWords) {
        WeixinHotService mWeixinHotService = API.retrofit.create(WeixinHotService.class);
        Call<WeiXinHotInfo> call = mWeixinHotService.getWeiXinHotList(API.apikey, number, 1, keyWords, pageNum, "");
        call.enqueue(mCallback);
    }

    private Callback<WeiXinHotInfo> mCallback = new Callback<WeiXinHotInfo>() {
        @Override
        public void onResponse(Call<WeiXinHotInfo> call, Response<WeiXinHotInfo> response) {
            if (response.isSuccessful()) {
                L.e("isSuccessful~~~~~");
                WeiXinHotInfo infos = response.body();
                List<WeiXinHotInfo.NewslistBean> beans = infos.getNewslist();

                setListAdapter(beans);
            }
        }

        @Override
        public void onFailure(Call<WeiXinHotInfo> call, Throwable t) {
            L.e("onFailure response" + t.getMessage());
        }
    };

    private void setListAdapter( List<WeiXinHotInfo.NewslistBean> beans) {
        mAdapter = new WXHotAdapter(beans,ct);
        mRecyclerView.setAdapter(mAdapter);
//                mRecyclerView.setRefreshing(true);


        mAdapter.setOnItemClickListener(new WXHotAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {
                WeiXinHotInfo.NewslistBean nb = (WeiXinHotInfo.NewslistBean)object;
                Intent intent = new Intent(ct,WXHotDetail.class);
                intent.putExtra("url",nb.getUrl());
                intent.putExtra("title",nb.getTitle());
                startActivity(intent);
            }
        });
    }


}
