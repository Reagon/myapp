package com.zbao.android.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.zbao.android.R;
import com.zbao.android.utils.SystemStatusManager;

/**
 * Created by zhangbao on 16/6/22.
 */
public abstract class BaseFrgment extends Fragment {
    public FragmentActivity mActivity;
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        setTranslucentStatus();
        View view = initView(inflater,container);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInvisible();
        }
    }

    public abstract View initView(LayoutInflater inflater,ViewGroup container);

    /**
     * fragment可见时
     */
    protected void onVisible(){
        lazyLoad();
    }
    /**
     * fragment不可见时
     */
    protected void onInvisible(){

    }

    /**
     * 延迟加载
     */
    public abstract void lazyLoad();


    // 需要setContentView之前调用
    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getActivity().getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getActivity().getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemStatusManager tintManager = new SystemStatusManager(getActivity());
            tintManager.setStatusBarTintEnabled(true);
            // 设置状态栏的颜色
            tintManager.setStatusBarTintResource(R.color.holo_blue_bright);
            getActivity().getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }
}
