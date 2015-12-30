package com.aupic.aupic.Storage;

import java.util.HashMap;

/**
 * Created by saheb on 24/12/15.
 */
public class TransientDataRepo {

    private static TransientDataRepo instance = null;
    private static HashMap<String, Object> dataMap = null;

    private TransientDataRepo(){

    }

    public static void init(){
        if(instance == null){
            instance = new TransientDataRepo();
            dataMap = new HashMap<>();
        }
    }

    public static TransientDataRepo getInstance(){
        return instance;
    }

    public void putData(String key,Object object){
        if(dataMap != null){
            dataMap.put(key, object);
        }
    }

    public Object getData(String key){
        return dataMap.get(key);
    }

    public void clear(String key){
        if(dataMap.containsKey(key)){
            dataMap.remove(key);
        }
    }

    public void clearAll() {

        dataMap = new HashMap<>();
    }
}
