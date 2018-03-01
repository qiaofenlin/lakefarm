package com.lakefarm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rxl on 17-2-28.
 */
public class JsonUtil {
    public static List<String> strToArray(String json) {
        return JSONArray.parseArray(json, String.class);
    }

    /**
     *
     * @param json
     * @return   将json串转为map
     */
    public static Map<String, Object> strToMap(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Map<String, Object> params = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        return params;
    }

    /**
     *
     * @param json
     * @return  将json串转为map
     */
    public static Map<String, String> strToMapstr(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Map<String, String> params = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            params.put(entry.getKey(), (String) entry.getValue());
        }
        return params;
    }

    /**
     *
     * @param data  json字符串
     * @param flag  json中对应的key
     * @return      结果带上了双引号
     */
    public static String getData(String data, String flag) {

        return JSON.parseObject(data).get(flag).toString();
    }

    /**
     *
     * @param string
     * @return  判断当前的字符串是不是json
     */
    public static boolean isJson(String string) {
        try {
            JSON.parseObject(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param list 对应的list对象
     * @return
     */
    public static JSONArray listToJson(List list) {
        return JSON.parseArray(JSON.toJSONString(list, true));
    }

    /**
     *
     * @param object
     * @return
     */
    public static String pojoToJson(Object object) {
        return JSON.toJSONString(object, true);
    }

    /**
     *
     * @param str
     * @return  将json传转为对象
     */
    public static JSONObject str2Json(String str) {
        return JSON.parseObject(str);
    }

    /**
     *
     * @param key
     * @param json
     * @return
     */
    public static JSONArray getArrayFromStr(String key, String json) {
        JSONObject object = JSON.parseObject(json);
        return (JSONArray) object.get(key);
    }

    /**
     *
     * @param key
     * @param json
     * @return
     */
    public static JSONArray getArrayFromJSON(String key, JSONObject json) {
        return (JSONArray) json.get(key);
    }

    /**
     *
     * @param jsonString
     * @return
     */
    public static List getListByArray(String jsonString) {
        List personList = new ArrayList();
        JSONArray jsonArray = new JSONArray(2);
        System.out.println(jsonArray.size() + "长度");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            personList.add(jsonObject.get(i));
        }
        return personList;
    }

    public String mapTojson(Map<String,String> map) {

        return null;
    }
}
