package com.mingyi.xpaywatch;


import android.content.Context;
import android.graphics.Bitmap;

import androidx.recyclerview.widget.SortedList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static com.orhanobut.logger.Logger.e;
import  com.mingyi.xpaywatch.Reception;

import org.json.JSONArray;
import org.json.JSONObject;

class HeartBeatCtrl {

    private Context mContext;
    // private static final String BaseUrl = "http://api.wanshangpay.com/";
    private static final String BaseUrl = "http:192.168.16.174:5757/weapp/";
    private static final String heartUrl = "http://api.wanshangpay.com/soft/heartbeat";
    private static final String access_token = "973e365088ac177e6729143fc7";
    // private static final String access_token = "973e365088ac177e6729143fc7ad0772";
    private static final String data = "[{'type':'wechat','account':'test1'}]";
    private static final String uuid = "973e365088ac177e6729143fc7ad0772";

    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<Reception> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Reception>(){
            //请求成功时回调
            @Override
            public void onResponse(Call<Reception> call, Response<Reception> response) {
                // 步骤7：处理返回的数据结果
                response.body().show();
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Reception> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }

    //send heart
    public void sendHeart() {
//步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        HeartBeatData entity = new HeartBeatData();
        entity.access_token = access_token;
        entity.uuid = uuid;
        try {
            JSONObject Jitem = new JSONObject();
            JSONArray gResTable = new JSONArray();
            Jitem.put("type".toString(), "wechat".toString());
            Jitem.put("account", "test1");
            gResTable.put(Jitem);
            entity.data = gResTable;
        }catch (Exception e){
            System.out.println(e);
        }

        System.out.println(">>>entity:"+ entity.data);
        //对 发送请求 进行封装
        Call<Reception> call = request.postHeartBeat(entity);

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Reception>(){
            //请求成功时回调
            @Override
            public void onResponse(Call<Reception> call, Response<Reception> response) {
                // 步骤7：处理返回的数据结果
                response.body().show();
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Reception> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }
}