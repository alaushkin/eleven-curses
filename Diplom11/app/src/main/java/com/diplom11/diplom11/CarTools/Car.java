package com.diplom11.diplom11.CarTools;

import com.diplom11.diplom11.DictionaryTools.Dictionary;
import com.diplom11.diplom11.UserTools.User;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

/**
 * Created by Mak on 10.05.2015.
 */
public class Car {
    private String loadingCity;
    private String unloadingCity;
    private String bodyType;
    private String loadType;
    private double weight;
    private double volume;
    private Date createdAt;
    private String arriveDate;
    private String otprDate;
    private String id;
    private User owner;

    public Car(ParseObject object) {
        loadingCity = (String) object.get("loadingCity");
        unloadingCity = (String) object.get("unloadingCity");
        bodyType = (String) object.get("bodyType");
        loadType = (String) object.get("loadType");
        weight = object.getDouble("weight");
        volume = object.getDouble("volume");
        createdAt = object.getCreatedAt();
        arriveDate = (String) object.get("arriveDate");
        otprDate = (String) object.get("otprDate");
        id = object.getObjectId();

        ParseQuery parseQuery = new ParseQuery("UserData");
        String usrId = (String) object.get("user");
        parseQuery.whereEqualTo("userId", usrId);
        try {
            owner = new User((ParseObject) parseQuery.find().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("Город загрузки: " + Dictionary.getCityById(loadingCity) + "\n");
        result.append("Город разгрузки: " + Dictionary.getCityById(unloadingCity) + "\n");
        result.append("Тип корпуса: " + Dictionary.getBodyTypeById(bodyType) + "\n");
        result.append("Тип загрузки: " + Dictionary.getLoadTypeById(loadType) + "\n");
        result.append("Тоннаж: " + weight + "\n");
        result.append("Объем: " + volume + "\n");
        result.append("Дата добавления заявки: " + new java.sql.Date(createdAt.getTime()) + "\n");
        result.append("Дата отправки: " + otprDate + "\n");
        result.append("Дата прибытия: " + arriveDate + "\n");
        result.append("Рейтинг владельца: " + owner.getReiting() + "\n");
        result.append("ИД владельца: " + owner.getUserId() + "\n");
        return result.toString();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return owner.getUserId();
    }

    public String getLoadingCity() {
        return loadingCity;
    }

    public String getUnloadingCity() {
        return unloadingCity;
    }
}
