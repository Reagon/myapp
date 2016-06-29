package com.zbao.android.weixinhot;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zbao.android.R;
import com.zbao.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangbao on 16/6/28.
 */
public class WXHotDetail extends BaseActivity {
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_hot_info_details);
        ButterKnife.bind(this);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);

        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        title.setText("详情");
    }

    @OnClick(R.id.back)
    void onBack(){
        finish();
    }

    @Override
    protected void initData() {
        Intent i = getIntent();
        String url = i.getStringExtra("url");
        mWebview.loadUrl(url);
    }

}
