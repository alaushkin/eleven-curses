package com.diplom11.diplom11.CargoSearchTools;

/**
 * Created by Mak on 11.03.2015.
 */
public class DictPair {
    private String key;
    private String value;

    public DictPair(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
