package com.kzmen.sczxjf.net;

import android.content.Context;

import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/17.
 */

public class OkhttpUtilManager {
    public static String URL="http://192.168.0.101:8000/";
    private Context mContext;
    private OkhttpUtilManager manager;
    public static void  get(Context mContext, String url, String cachKey, Map<String,String> param, final OkhttpUtilResult result){
        OkHttpUtils.get(URL+url)
                .tag(mContext)
                .params(param)
                .cacheKey(cachKey)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object=new JSONObject(s);
                            if(result==null){
                                return;
                            }
                            int code=object.getInt("code");
                            if(code!=200){
                                result.onError(code,object.getString("msg"));
                            }else{
                                result.onSuccess(100,object.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        try {
                            JSONObject object=new JSONObject(s);
                            if(result==null){
                                return;
                            }
                            int code=object.getInt("code");
                            if(code!=200){
                                result.onError(code,object.getString("msg"));
                            }else{
                                result.onSuccess(101,object.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if(result==null){
                            return;
                        }
                        result.onError(100,e.toString());
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        super.onCacheError(call, e);
                        //result.onError(101,e.toString());
                    }
                });
    }
    public static void  getNoCacah(Context mContext, String url,final OkhttpUtilResult result){
        OkHttpUtils.get(URL+url)
                .tag(mContext)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object=new JSONObject(s);
                            if(result==null){
                                return;
                            }
                            int code=object.getInt("code");
                            if(code!=200){
                                result.onError(code,object.getString("msg"));
                            }else{
                                result.onSuccess(100,object.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        try {
                            JSONObject object=new JSONObject(s);
                            if(result==null){
                                return;
                            }
                            int code=object.getInt("code");
                            if(code!=200){
                                result.onError(code,object.getString("msg"));
                            }else{
                                result.onSuccess(101,object.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if(result==null){
                            return;
                        }
                        result.onError(100,e.toString());
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        super.onCacheError(call, e);
                        result.onError(101,e.toString());
                    }
                });
    }
    public static void  post(Context mContext, String url, String cachKey, final OkhttpUtilResult result){
        OkHttpUtils.post(URL+url)
                .tag(mContext)
                .cacheKey(cachKey)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object=new JSONObject(s);
                            if(result==null){
                                return;
                            }
                            int code=object.getInt("code");
                            if(code!=200){
                                result.onError(code,object.getString("msg"));
                            }else{
                                result.onSuccess(100,object.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        try {
                            JSONObject object=new JSONObject(s);
                            if(result==null){
                                return;
                            }
                            int code=object.getInt("code");
                            if(code!=200){
                                result.onError(code,object.getString("msg"));
                            }else{
                                result.onSuccess(101,object.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if(result==null){
                            return;
                        }
                        result.onError(100,e.toString());
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        super.onCacheError(call, e);
                        result.onError(101,e.toString());
                    }
                });
    }
    public static void  postNoCacah(Context mContext, String url,  final OkhttpUtilResult result){
        OkHttpUtils.post(URL+url)
                .tag(mContext)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object=new JSONObject(s);
                            if(result==null){
                                return;
                            }
                            int code=object.getInt("code");
                            if(code!=200){
                                result.onError(code,object.getString("msg"));
                            }else{
                                result.onSuccess(100,object.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        try {
                            JSONObject object=new JSONObject(s);
                            if(result==null){
                                return;
                            }
                            int code=object.getInt("code");
                            if(code!=200){
                                result.onError(code,object.getString("msg"));
                            }else{
                                result.onSuccess(101,object.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if(result==null){
                            return;
                        }
                        result.onError(100,e.toString());
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        super.onCacheError(call, e);
                        result.onError(101,e.toString());
                    }
                });
    }
}
