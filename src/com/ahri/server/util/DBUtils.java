package com.ahri.server.util;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.TextSearchOptions;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zouyingjie on 2017/3/7.
 */
public class DBUtils {

    private static MongoClient client = new MongoClient();

    private static MongoDatabase mongodb_cache = client.getDatabase("cache");

    /**
     * 全文检索 PHP 手册数据
     * @param queryInfo
     * @return
     */
    public static JSONObject queryPHPUrlByTitle(String queryInfo) {

        MongoCollection<Document> title_document = mongodb_cache.getCollection("title_document");
        //Filters.text 用于文本索引检索
        FindIterable<Document> resultIter = title_document.find(Filters.text(queryInfo, new TextSearchOptions().language("english")));

        MongoCursor<Document> iterator = resultIter.iterator();
        JSONArray jsonArray = new JSONArray();
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            JSONObject json = new JSONObject(doc.toJson());
            jsonArray.put(json);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serach_result", jsonArray);
        return jsonObject;

    }


}
