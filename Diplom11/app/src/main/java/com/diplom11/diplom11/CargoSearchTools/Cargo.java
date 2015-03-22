package com.diplom11.diplom11.CargoSearchTools;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.diplom11.diplom11.UserTools.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Mak on 09.03.2015.
 */
public class Cargo {
    private String loadingCity;
    private String unloadingCity;
    private String bodyType;
    private String loadType;
    private double xSize;
    private double ySize;
    private double zSize;
    private double weight;
    private double volume;
    private double cost;
    private String payType;
    private Date createdAt;
    private String arriveDate;
    private String id;
    private User owner;

    public Cargo(ParseObject object){
        loadingCity = (String) object.get("loadingCity");
        unloadingCity = (String) object.get("unloadingCity");
        bodyType = (String) object.get("bodyType");
        loadType = (String) object.get("loadType");
        payType = (String) object.get("payType");
        xSize = Double.valueOf(object.get("xSize").toString());
        ySize = Double.valueOf(object.get("ySize").toString());
        zSize = Double.valueOf(object.get("zSize").toString());
        weight = Double.valueOf(object.get("weight").toString());
        volume = Double.valueOf(object.get("volume").toString());
        cost = Double.valueOf(object.get("cost").toString());
        createdAt = object.getCreatedAt();
        arriveDate = (String) object.get("arriveDate");
        id = object.getObjectId();

        ParseQuery parseQuery = new ParseQuery("UserData");
        String usrId = (String)object.get("user");
        parseQuery.whereEqualTo("userId", usrId);
        try {
            owner = new User((ParseObject)parseQuery.find().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        StringBuffer result = new StringBuffer();
        result.append("Город загрузки: " + loadingCity + "\n");
        result.append("Город разгрузки: " + unloadingCity + "\n");
        result.append("Тип корпуса: " + Dictionary.getBodyTypeById(bodyType) + "\n");
        result.append("Тип загрузки: " + Dictionary.getLoadTypeById(loadType) + "\n");
        result.append("Ширина: " + xSize + "\n");
        result.append("Высота: " + ySize + "\n");
        result.append("Длина: " + zSize + "\n");
        result.append("Вес: " + weight + "\n");
        result.append("Объем: " + volume + "\n");
        result.append("Цена за километр: " + cost + "\n");
        result.append("Тип оплаты: " + Dictionary.getPayTypeById(payType) + "\n");
        result.append("Дата добавления заявки: " + new java.sql.Date(createdAt.getTime()) + "\n");
        result.append("Дата прибытия: " + arriveDate + "\n");
        result.append("Рейтинг владельца: " + owner.getReiting() + "\n");
        result.append("ИД владельца: " + owner.getUserId() + "\n");
        return result.toString();
    }

    public String getId(){
        return id;
    }

    public String getUserId(){
        return owner.getUserId();
    }
}
