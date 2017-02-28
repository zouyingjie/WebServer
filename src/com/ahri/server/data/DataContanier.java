package com.ahri.server.data;

import com.ahri.server.bean.Person;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zouyingjie on 2017/2/15.
 */
public class DataContanier {

    private static Map<String, Person> container = new HashMap<>();

    static {
        Person p1 = new Person("p1", 12, "beijing");
        Person p2 = new Person("p2", 13, "shanghai");
        Person p3 = new Person("p3", 14, "shenzhen");
        Person p4 = new Person("p4", 15, "hangzhou");
        container.put(p1.getName(), p1);
        container.put(p2.getName(), p2);
        container.put(p3.getName(), p3);
        container.put(p4.getName(), p4);
    }
    public static void put(String key, Person p) {
        container.put(key,p);
    }

    public static Person getValue(String key){
        if (container.containsKey(key)) {
            return container.get(key);
        }else {
            return null;
        }

    }

    public static int size(){
        return container.size();
    }

}
