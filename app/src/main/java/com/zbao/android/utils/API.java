package com.zbao.android.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangbao on 16/6/28.
 */
public class API {
    /**API key*/
    public static final String apikey = "8e005234a1335b48e10fd7381481641d";
    /**微信热门*/
    public static final String WeiXinHot = "http://apis.baidu.com";



    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://apis.baidu.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
