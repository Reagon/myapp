package com.zbao.android.weixinhot;

import com.zbao.android.entity.WeiXinHotInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by zhangbao on 16/6/28.
 */
public interface WeixinHotService {

    @GET("/txapi/weixin/wxhot")
    Call<WeiXinHotInfo> getWeiXinHotList(@Header("apikey") String apiKey, //apikey    默认值 10
                                               @Query("num") int number,    //文章返回数量   默认值 1
                                               @Query("rand") int r,    //是否随机，1表示随机   默认值
                                               @Query("word") String keyWords,//检索关键词   默认值 盗墓笔记
                                               @Query("page") int pageNum, //翻页，输出数量跟随num参数 默认值 1
                                               @Query("src") String pageSrc);//指定文章来源    默认值 人民日报



}
