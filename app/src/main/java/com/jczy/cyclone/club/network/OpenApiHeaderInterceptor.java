package com.jczy.cyclone.club.network;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jczy.cyclone.club.mmkv.AuthMMKV;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Invocation;

/**
 * 商城 OpenApi 公共参数注入 + 签名拦截器
 * 对所有 POST 请求自动追加：service / token / requestNo / version / partnerId
 * 并在 Header 中添加 x-api-sign / x-api-signType / x-api-accessKey
 */
public class OpenApiHeaderInterceptor implements Interceptor {

    private static final String TAG = "OpenApiInterceptor";
    private static final String PARTNER_ID = "23022113430907CF0113";

    final Gson gson = new GsonBuilder().registerTypeAdapter(
            new TypeToken<TreeMap<String, Object>>() {}.getType(),
            (JsonDeserializer<TreeMap<String, Object>>) (json, typeOfT, context) -> {
                TreeMap<String, Object> treeMap = new TreeMap<>();
                JsonObject jsonObject = json.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    treeMap.put(entry.getKey(), entry.getValue());
                }
                return treeMap;
            }).create();

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();

        if (original.method().equals("POST")) {
            String paramsStr = bodyToString(original);
            Type type = new TypeToken<TreeMap<String, Object>>() {}.getType();

            // 读取 @OpenApiService 注解中的 service 名
            String service = null;
            Invocation tag = original.tag(Invocation.class);
            if (tag != null) {
                OpenApiService openApiService = tag.method().getAnnotation(OpenApiService.class);
                if (openApiService != null) {
                    service = openApiService.service();
                }
            }
            Log.d(TAG, "service -> " + service);

            // 解析已有参数（可能为 null）
            TreeMap<String, Object> params = gson.fromJson(paramsStr, type);
            if (params == null) {
                params = new TreeMap<>();
            }

            // 注入公共参数
            if (service != null) params.put("service", service);
            params.putIfAbsent("channel", "ANDROID");
            String token = AuthMMKV.INSTANCE.getOpenApiAccessToken();
            params.put("token", token != null ? token : "");
            params.put("requestNo", genRequestNo());
            params.put("version", "1.0");
            params.put("partnerId", PARTNER_ID);

            requestBuilder.post(RequestBody.create(
                    MediaType.get("application/json"),
                    gson.toJson(params)));
            Request request = requestBuilder.build();

            // 计算签名
            String sign = signFn(request);
            String accessKey = AuthMMKV.INSTANCE.getOpenApiAccessKey();

            Request.Builder finalBuilder = request.newBuilder()
                    .addHeader("platform", "ANDROID")
                    .addHeader("phoneModel", Build.BRAND + "_" + Build.MODEL)
                    .addHeader("x-api-sign", sign != null ? sign : "")
                    .addHeader("x-api-signType", "MD5")
                    .addHeader("x-api-accessKey", accessKey != null ? accessKey : "");

            Log.d(TAG, "service -> " + service + ", params: " + gson.toJson(params));
            return chain.proceed(finalBuilder.build());
        }

        return chain.proceed(original);
    }

    private String signFn(Request request) {
        if (request.method().equals("POST")) {
            String paramsStr = bodyToString(request);
            String secretKey = AuthMMKV.INSTANCE.getOpenApiSecretKey();
            return sign(paramsStr, secretKey != null ? secretKey : "");
        }
        return "";
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            if (copy.body() != null) {
                copy.body().writeTo(buffer);
            }
            return buffer.readUtf8();
        } catch (IOException e) {
            return "";
        }
    }

    private static String sign(Object body, String securityKey) {
        String str = body.toString();
        Buffer buffer = new Buffer();
        buffer.write(str.getBytes(StandardCharsets.UTF_8));
        buffer.write(securityKey.getBytes(StandardCharsets.UTF_8));
        return buffer.md5().hex();
    }

    private static final AtomicInteger _counter = new AtomicInteger(0);

    private static String genRequestNo() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(new Date())
                + new Random().nextInt(_counter.addAndGet(1));
    }
}
