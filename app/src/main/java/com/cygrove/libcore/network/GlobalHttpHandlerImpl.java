package com.cygrove.libcore.network;

import android.os.Build;

import com.xiongms.libcore.BaseApplication;
import com.xiongms.libcore.network.GlobalHttpHandler;
import com.xiongms.libcore.utils.StrUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author cygrove
 * @time 2018-11-14 16:55
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {

    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) throws IOException {
        return response;
    }

    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) throws IOException {
        RequestBody requestBody = request.body();
        HttpUrl.Builder modifiedUrlBuilder = request.url().newBuilder();
        String method = request.method();

        // 全局配置http头信息
        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        requestBuilder.header("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5");
        requestBuilder.header("Proxy-Connection", "keep-alive");
        requestBuilder.header("Cache-Control", "max-age=0");
        requestBuilder.header("Content-Type", "application/json");
        requestBuilder.header("token", BaseApplication.getInstance().getPreferences().getToken());
        requestBuilder.header("clt", "android");
        requestBuilder.header("device", Build.MODEL);
        requestBuilder.header("appVersion", BaseApplication.getInstance().getEnv().appVersion());

        requestBuilder.method(method, request.body());

        if ("GET".equals(method)) {
            // 修改GET请求的参数
            modifiedUrlBuilder.addQueryParameter("appid", "rqb_android");
            modifiedUrlBuilder.addQueryParameter("cid", "rqb_android");
            modifiedUrlBuilder.addQueryParameter("sign", "");
            modifiedUrlBuilder.addQueryParameter("chn", "");
            modifiedUrlBuilder.addQueryParameter("ov", Build.MODEL + Build.VERSION.RELEASE);
            modifiedUrlBuilder.addQueryParameter("av", BaseApplication.getInstance().getEnv().appVersion());
            modifiedUrlBuilder.addQueryParameter("nt", String.valueOf(BaseApplication.getInstance().getEnv().networkOperator()));

            modifiedUrlBuilder.addQueryParameter("tkn", BaseApplication.getInstance().getPreferences().getToken());

//            return requestBuilder.url(modifiedUrlBuilder.build()).build();//同理如果接口有特殊要求的。可以解开注释、追加以上参数
            return requestBuilder.build();
        } else if (request.body() instanceof FormBody) {
            // 全局修改表单post参数
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oldFormBody = (FormBody) request.body();
            if (oldFormBody != null) {
                for (int i = 0; i < oldFormBody.size(); i++) {
                    newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
                }
            }
            requestBuilder.method(request.method(), newFormBody.build());
            request = requestBuilder.build();
            return request;
        } else if (request.body().contentType().subtype().equalsIgnoreCase("json")) {
            // 全局修改post json参数
            String body = null;
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                body = buffer.readString(charset);
            }

            String newJsonBody = "";
            try {
                JSONObject jsonObject = new JSONObject(body);
                jsonObject.put("appid", "rqb_android");
                jsonObject.put("cid", "rqb_android");
                jsonObject.put("sign", "");
                jsonObject.put("chn", "");
                jsonObject.put("ov", Build.MODEL + Build.VERSION.RELEASE);
                jsonObject.put("av", BaseApplication.getInstance().getEnv().appVersion());
                jsonObject.put("nt", String.valueOf(BaseApplication.getInstance().getEnv().networkOperator()));

                jsonObject.put("tkn", BaseApplication.getInstance().getPreferences().getToken());

                newJsonBody = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/json"), newJsonBody);

            requestBuilder.method(request.method(), requestBody1);
//            request = requestbuilder.build();//如果接口有特殊要求的。可以解开注释、追加以上参数
            return requestBuilder.build();
        }
        return request;
    }
}