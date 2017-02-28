package com.ahri.server;

import com.ahri.server.bean.Person;
import com.ahri.server.data.DataContanier;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by zouyingjie on 2017/2/15.
 */
public class RequestRoute {


    public static String routeGetResponse(JSONArray requestKeys) {

        JSONObject result = new JSONObject();
        Iterator<Object> keys = requestKeys.iterator();

        while (keys.hasNext()) {
            String name = (String) keys.next();
            Person person = DataContanier.getValue(name);
            if (person != null) {
                result.put(person.getName(), new JSONObject(person));
            }
        }
        return result.toString();
    }

    public static String routePostResponse(String s){
        System.out.println("size" + DataContanier.size());
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject(s);
        Set<String> keySet = jsonObject.keySet();
        for (String key: keySet) {
            JSONObject json = jsonObject.getJSONObject(key);
            Person person = gson.fromJson(json.toString(), Person.class);
            DataContanier.put(person.getName(), person);
        }
        return "";
    }

}
