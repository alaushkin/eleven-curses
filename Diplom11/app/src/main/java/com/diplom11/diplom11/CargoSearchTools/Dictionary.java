package com.diplom11.diplom11.CargoSearchTools;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Mak on 10.03.2015.
 */
public class Dictionary {
    private static HashMap<String,String> dop = new HashMap();
    private static HashMap<String,String> loadType = new HashMap();
    private static HashMap<String,String> bodyType = new HashMap();

    static public void setDop(List list){
        for(Object object : list){
            ParseObject parseObject = (ParseObject) object;
            dop.put(parseObject.getObjectId(), (String)parseObject.get("value"));
        }
    }

    static public void setLoadType(List list){
        for(Object object : list){
            ParseObject parseObject = (ParseObject) object;
            loadType.put(parseObject.getObjectId(), (String)parseObject.get("value"));
        }
    }

    static public void setBodyType(List list){
        for(Object object : list){
            ParseObject parseObject = (ParseObject) object;
            bodyType.put(parseObject.getObjectId(), (String)parseObject.get("value"));
        }
    }

    static public String getDopById(String key){
        return dop.get(key);
    }

    static public String getLoadTypeById(String key){
        return loadType.get(key);
    }

    static public String getBodyTypeById(String key){
        return bodyType.get(key);
    }

    static public ArrayList<DictPair> getDopArray(){
        ArrayList<DictPair> result = new ArrayList<>();
        Iterator it = dop.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result.add(new DictPair((String)pair.getKey(),(String)pair.getValue()));
        }
        return result;
    }

    static public ArrayList<DictPair> getLoadTypeArray(){
        ArrayList<DictPair> result = new ArrayList<>();
        Iterator it = loadType.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result.add(new DictPair((String)pair.getKey(),(String)pair.getValue()));
        }
        return result;
    }

    static public ArrayList<DictPair> getBodyTypeArray(){
        ArrayList<DictPair> result = new ArrayList<>();
        Iterator it = bodyType.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result.add(new DictPair((String)pair.getKey(),(String)pair.getValue()));
        }
        return result;
    }
}
