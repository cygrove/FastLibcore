package com.cygrove.libcore.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.orhanobut.logger.Logger;
import com.cygrove.libcore.bean.BaseBean;
import com.cygrove.libcore.bean.BasePageBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cygrove
 * @time 2018-11-16 11:33
 */
public class GsonUtils {
    private static GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializeNulls();

    public static Gson getGson() {
        Gson gson = gsonBuilder.create();
        return gson;
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String gsonToString(Object object) {
        String gsonString = null;
        try {
            Gson gson = gsonBuilder.create();
            if (gson != null) {
                gsonString = gson.toJson(object);
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String json, Class<T> cls) {
        T t = null;
        try {
            Gson gson = gsonBuilder.create();
            if (gson != null && !TextUtils.isEmpty(json)) {
                t = gson.fromJson(json, cls);
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return t;
    }

    /**
     * 转成Object对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T gsonToObject(String json, Class<T> cls) {
        T t = (T) new Object();
        try {
            Gson gson = gsonBuilder.create();
            if (json != null && !TextUtils.isEmpty(json)) {
                BaseBean<T> baseResponseBean = gson.fromJson(json, new TypeToken<BaseBean<T>>() {
                }.getType());

                if (baseResponseBean != null && baseResponseBean.getBody() != null) {
                    JsonObject jsonObject = new JsonParser().parse(gson.toJson(baseResponseBean.getBody()))
                            .getAsJsonObject();
                    t = gson.fromJson(jsonObject, cls);
                }
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return t;
    }

    /**
     * 转成BaseResponseBean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> BaseBean<T> gsonToBaseBean(String json, Class<T> cls) {
        T t = (T) new Object();
        BaseBean<T> baseResponseBean = new BaseBean<T>();
        try {
            Gson gson = gsonBuilder.create();
            if (json != null && !TextUtils.isEmpty(json)) {
                baseResponseBean = gson.fromJson(json, new TypeToken<BaseBean<T>>() {
                }.getType());

                JsonObject jsonObject = new JsonParser().parse(gson.toJson(baseResponseBean.getBody())).getAsJsonObject();
                t = gson.fromJson(jsonObject, cls);
                baseResponseBean.setBody(t);
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return baseResponseBean;
    }

    /**
     * 获取分页总数，当前页等数据
     *
     * @param json
     * @return
     */

    public static <T> BasePageBean<T> gsonToBasePageBean(String json) {
        BasePageBean data = null;
        try {
            Gson gson = gsonBuilder.create();
            if (gson != null && !TextUtils.isEmpty(json)) {
                BaseBean<BasePageBean<T>> baseResponseBean = gson.fromJson(json,
                        new TypeToken<BaseBean<BasePageBean<T>>>() {
                        }.getType());
                data = baseResponseBean.getBody();
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return data;
    }


//    /**
//     * 获取分页总数，当前页等数据
//     *
//     * @param json
//     * @return
//     */
//
//    public static <T> BaseResponseBean<BasePageListBean<T>> gsonToBaseResponseBean(String json, Class<T> cls) {
//        BaseResponseBean<BasePageListBean<T>> result = null;
//        try {
//            if (gson != null && !TextUtils.isEmpty(json)) {
//
//                BaseResponseBean<BasePageListBean<T>> baseResponseBean = gson.fromJson(json,
//                        new TypeToken<BaseResponseBean<BasePageListBean<T>>>() {
//                        }.getType());
//
//                BasePageListBean<T> data = baseResponseBean.data;
//                List<T> list = new ArrayList<>();
//                if (data != null && data.data != null) {
//                    JsonArray array = new JsonParser().parse(gson.toJson(data.data)).getAsJsonArray();
//                    for (final JsonElement elem : array) {
//                        list.add(gson.fromJson(elem, cls));
//                    }
//                }
//
//                result = baseResponseBean;
//                (result.data).data = list;
//
//            }
//        } catch (Exception e) {
//            Logger.e(e.getMessage());
//        }
//        return result;
//    }

    /**
     * 转成PageList
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToPageList(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            Gson gson = gsonBuilder.create();
            if (gson != null && !TextUtils.isEmpty(json)) {
                BaseBean<BasePageBean<T>> baseResponseBean = gson.fromJson(json,
                        new TypeToken<BaseBean<BasePageBean<T>>>() {
                        }.getType());
                BasePageBean<T> data = baseResponseBean.getBody();

                if (data != null && data.getList() != null) {
                    JsonArray array = new JsonParser().parse(gson.toJson(data.getList())).getAsJsonArray();
                    for (final JsonElement elem : array) {
                        list.add(gson.fromJson(elem, cls));
                    }
                }
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return list;
    }

    /**
     * 转成List
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            Gson gson = gsonBuilder.create();
            if (gson != null && !TextUtils.isEmpty(json)) {
                BaseBean<List<T>> baseResponseBean = gson.fromJson(json, new
                        TypeToken<BaseBean<List<T>>>
                                () {
                        }.getType());

                JsonArray array = new JsonParser().parse(gson.toJson(baseResponseBean.getBody())).getAsJsonArray();
                for (final JsonElement elem : array) {
                    list.add(gson.fromJson(elem, cls));
                }
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return list;
    }

    /**
     * 转成List
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToListBean(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            Gson gson = gsonBuilder.create();
            if (gson != null && !TextUtils.isEmpty(json)) {
                JsonArray array = new JsonParser().parse(json).getAsJsonArray();
                for (final JsonElement elem : array) {
                    list.add(gson.fromJson(elem, cls));
                }
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param json
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String json) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = gsonBuilder.create();
            if (gson != null && !TextUtils.isEmpty(json)) {
                list = gson.fromJson(json, new TypeToken<List<Map<String, T>>>() {
                }.getType());
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param json
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String json) {
        Map<String, T> map = null;
        try {
            Gson gson = gsonBuilder.create();
            if (gson != null && !TextUtils.isEmpty(json)) {
                map = gson.fromJson(json, new TypeToken<Map<String, T>>() {
                }.getType());
            }
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return map;
    }

    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }
}