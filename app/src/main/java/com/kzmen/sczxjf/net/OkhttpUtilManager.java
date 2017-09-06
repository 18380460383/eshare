package com.kzmen.sczxjf.net;

import android.content.Context;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.bean.kzbean.BaseBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.model.HttpHeaders;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/17.
 */

public class OkhttpUtilManager {
    public static String URL1 = "http://192.168.0.101:8000/";
    public static String URL = "http://api.kzmen.cn/api.php/";
    public static String URL2 = "http://cocopeng.com/manage/api.php/";
    private Context mContext;
    private OkhttpUtilManager manager;

    public static void get(Context mContext, String url, String cachKey, Map<String, String> param, final OkhttpUtilResult result) {
        OkHttpUtils.get(URL + url)
                .tag(mContext)
                .params(param)
                .cacheKey(cachKey)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            int code = object.getInt("code");
                            if (code != 200) {
                                result.onErrorWrong(code, object.getString("message"));
                            } else {
                                result.onSuccess(100, object.getString("data"));
                            }
                        } catch (JSONException e) {
                            result.onSuccess(9999, e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            int code = object.getInt("code");
                            if (code != 200) {
                                result.onErrorWrong(code, object.getString("message"));
                            } else {
                                result.onSuccess(101, object.getString("data"));
                            }
                        } catch (JSONException e) {
                            result.onSuccess(9999, e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (result == null) {
                            return;
                        }
                        result.onErrorWrong(100, e.toString());
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        super.onCacheError(call, e);
                        //result.onErrorWrong(101,e.toString());
                    }
                });
    }

    public static void getNoCacah(Context mContext, String url, final OkhttpUtilResult result) {
        OkHttpUtils.get(URL + url)
                .tag(mContext)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            int code = object.getInt("code");
                            if (code != 200) {
                                result.onErrorWrong(code, object.getString("message"));
                            } else {
                                result.onSuccess(100, object.getString("data"));
                            }
                        } catch (JSONException e) {
                            result.onSuccess(9999, e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            int code = object.getInt("code");
                            if (code != 200) {
                                result.onErrorWrong(code, object.getString("message"));
                            } else {
                                result.onSuccess(101, object.getString("data"));
                            }
                        } catch (JSONException e) {
                            result.onSuccess(9999, e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (result == null) {
                            return;
                        }
                        result.onErrorWrong(100, e.toString());
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        super.onCacheError(call, e);
                        result.onErrorWrong(101, e.toString());
                    }
                });
    }

    public static void post(Context mContext, String url, String cachKey, final OkhttpUtilResult result) {
        OkHttpUtils.post(URL + url)
                .tag(mContext)
                .cacheKey(cachKey)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            int code = object.getInt("code");
                            if (code != 200) {
                                result.onErrorWrong(code, object.getString("message"));
                            } else {
                                result.onSuccess(100, object.getString("data"));
                            }
                        } catch (JSONException e) {
                            result.onSuccess(9999, e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            int code = object.getInt("code");
                            if (code != 200) {
                                result.onErrorWrong(code, object.getString("message"));
                            } else {
                                result.onSuccess(101, object.getString("data"));
                            }
                        } catch (JSONException e) {
                            result.onSuccess(9999, e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (result == null) {
                            return;
                        }
                        result.onErrorWrong(100, e.toString());
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        super.onCacheError(call, e);
                        result.onErrorWrong(101, e.toString());
                    }
                });
    }

    public static void postNoCacah(Context mContext, String url, Map<String, String> param, final OkhttpUtilResult result) {
        Gson gson=new Gson();
        String data=gson.toJson(param);
        //Log.e("tst",data);
        HttpHeaders headers = new HttpHeaders();
        headers.put("sign", AppContext.sign);    //所有的 header 都 不支持 中文
        headers.put("token", AppContext.token);
        headers.put("app_bate", AppContext.app_bate);
        headers.put("from", AppContext.from);
        OkHttpUtils.post(URL + url)
                .tag(mContext)
                .params(param)
                .headers(headers)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            if (result == null) {
                                return;
                            }
                            BaseBean bean = BaseBean.parseEntity(object);
                            if (bean.getCode() == 200) {
                                result.onSuccess(100, bean.getData());
                            }else if(bean.getCode()==998){
                                AppContext.getInstance().setPersonageOnLine(false);
                            } else {
                                result.onErrorWrong(bean.getCode(), bean.getMessage());
                            }
                        } catch (JSONException e) {
                            result.onErrorWrong(99, "测试" + e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (result == null) {
                            return;
                        }
                        result.onErrorWrong(99, e.toString());
                    }
                });
    }
}
