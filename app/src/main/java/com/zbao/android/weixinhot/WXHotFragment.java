package com.zbao.android.weixinhot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zbao.android.R;
import com.zbao.android.customview.xrecyclerview.ProgressStyle;
import com.zbao.android.customview.xrecyclerview.XRecyclerView;
import com.zbao.android.entity.WeiXinHotInfo;
import com.zbao.android.utils.API;
import com.zbao.android.utils.L;
import com.zbao.android.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhangbao on 16/6/27.
 */
public class WXHotFragment extends Fragment {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private Context ct;
    private int pageNum = 1;
    private final int number = 15;
    private String keyWords = "";
    private WXHotAdapter mAdapter;
    private List<WeiXinHotInfo.NewslistBean>  allDatas = new ArrayList<WeiXinHotInfo.NewslistBean>();
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private static String words = "";
    private View mView;

    public static WXHotFragment newInstance(String keyWords) {
        WXHotFragment fragment = new WXHotFragment();
        Bundle args = new Bundle();
        args.putString("key",keyWords);
        fragment.setArguments(args);
        words = keyWords;
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
        return initView(inflater, container);
    }

    public View initView(LayoutInflater inflater, ViewGroup container) {
        if (mView != null){
            container.removeView(mView);
        }
        mView = inflater.inflate(R.layout.fragment_wxhot, container, false);
        ButterKnife.bind(this, mView);

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

        return mView;
    }

    private void initListener(){
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                keyWords = words;
                isRefresh = true;
                httpGet(pageNum, keyWords);

            }
            @Override
            public void onLoadMore() {
                pageNum += 1;
                keyWords = words;
                isLoadMore = true;
                httpGet(pageNum, keyWords);
            }
        });



    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            lazyLoad();
        }else{

        }
    }

    public void lazyLoad() {
        pageNum = 1;
        keyWords = words;
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
                if (isRefresh){
                    isRefresh = false;
                    allDatas.clear();
                    mRecyclerView.refreshComplete();
                }else if (isLoadMore){
                    isLoadMore = false;
                    mRecyclerView.loadMoreComplete();
                }
                allDatas.addAll(beans);
                setListAdapter(allDatas);
            }
        }

        @Override
        public void onFailure(Call<WeiXinHotInfo> call, Throwable t) {
            L.e("onFailure response" + t.getMessage());
            if (isRefresh){
                isRefresh = false;
                mRecyclerView.refreshComplete();
            }else if (isLoadMore){
                isLoadMore = false;
                pageNum -= 1;
                mRecyclerView.loadMoreComplete();
            }
            T.showShort(ct,t.getLocalizedMessage());
        }
    };

    private void setListAdapter( List<WeiXinHotInfo.NewslistBean> beans) {
        if (beans != null && beans.size() != 0){
            if (mAdapter == null){
                mAdapter = new WXHotAdapter(beans,ct);
                mRecyclerView.setAdapter(mAdapter);
            }else{
                mAdapter.notifyViewUpdate(beans);
            }
        }


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
