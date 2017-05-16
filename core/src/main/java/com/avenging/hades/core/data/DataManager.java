package com.avenging.hades.core.data;

import com.avenging.hades.core.data.network.MarvelServiceFactory;

/**
 * Created by Hades on 2017/5/16.
 */

public class DataManager {

    private static DataManager sInstance;

    private DataManager(){
        mMarvelService= MarvelServiceFactory.makeMarvelService();
    }

    public static DataManager getInstance(){
        if(sInstance==null){
            sInstance=new DataManager();
        }
        return sInstance;
    }
}
