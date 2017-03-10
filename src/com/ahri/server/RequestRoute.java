package com.ahri.server;

import com.ahri.server.bean.Person;
import com.ahri.server.constants.Constant;
import com.ahri.server.data.DataContanier;
import com.ahri.server.util.DBUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by zouyingjie on 2017/2/15.
 */
public class RequestRoute {

    public static JSONObject routeGetResponse(JSONArray requestKeys) {

        JSONObject result = new JSONObject();
        Iterator<Object> keys = requestKeys.iterator();

        while (keys.hasNext()) {
            String name = (String) keys.next();
            Person person = DataContanier.getValue(name);
            if (person != null) {
                result.put(person.getName(), new JSONObject(person));
            }
        }
        return result;
    }

    public static JSONObject routePostResponse(String postInfo) {

        if (postInfo != null && postInfo.length() > 0) {
            JSONObject jsonObject = new JSONObject(postInfo);
            String serviceName = jsonObject.optString("SERVICE_NAME");

            if (serviceName.equals(Constant.SERVICE_SERACH_PHP) || serviceName.equals(Constant.SERVICE_STACKOVER)) {
                String info = jsonObject.optString("SEARCH_KEY");
                return DBUtils.queryPHPUrlByTitle(info, serviceName);
            }
        }
        return new JSONObject("{result:no response}");

    }

}
