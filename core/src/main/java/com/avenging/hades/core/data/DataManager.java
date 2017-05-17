package com.avenging.hades.core.data;

import android.support.annotation.StringDef;

import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.core.data.model.DataWrapper;
import com.avenging.hades.core.data.network.MarvelService;
import com.avenging.hades.core.data.network.MarvelServiceFactory;
import com.avenging.hades.core.network.RemoteCallback;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Hades on 2017/5/16.
 */

public class DataManager {

    private static DataManager sInstance;
    private final MarvelService mMarvelService;

    private DataManager(){
        mMarvelService= MarvelServiceFactory.makeMarvelService();
    }

    public static DataManager getInstance(){
        if(sInstance==null){
            sInstance=new DataManager();
        }
        return sInstance;
    }

    public void getCharactersList(int offset,int limit,String searchQuery,RemoteCallback<DataWrapper<List<CharacterMarvel>>> listener){
        long timeStamp=System.currentTimeMillis();
        mMarvelService.getCharacters(timeStamp,offset,limit,searchQuery).enqueue(listener);
    }

    public void getCharacter(long characterId,  Callback<DataWrapper<List<CharacterMarvel>>> listener){
        long timeStamp=System.currentTimeMillis();
        mMarvelService.getCharacter(characterId,timeStamp).enqueue(listener);
    }

    private static final String COMIC_TYPE_COMICS="comics";
    private static final String COMIC_TYPE_SERIES="series";
    private static final String COMIC_TYPE_STORIES="stories";
    private static final String COMIC_TYPE_EVENTS="events";

    @StringDef({COMIC_TYPE_COMICS,COMIC_TYPE_SERIES,COMIC_TYPE_STORIES,COMIC_TYPE_EVENTS})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Type{}

    public void getComics(long characterId, Integer offset, Integer limit, Callback<DataWrapper<List<Comic>>> listener){
        getComicListByType(characterId,COMIC_TYPE_COMICS,offset,limit).enqueue(listener);
    }

    public void getSeries(long characterId, Integer offset, Integer limit, Callback<DataWrapper<List<Comic>>> listener){
        getComicListByType(characterId,COMIC_TYPE_SERIES,offset,limit).enqueue(listener);
    }

    public void getStories(long characterId, Integer offset, Integer limit, Callback<DataWrapper<List<Comic>>> listener){
        getComicListByType(characterId,COMIC_TYPE_STORIES,offset,limit).enqueue(listener);
    }

    public void getEvents(long characterId, Integer offset, Integer limit, Callback<DataWrapper<List<Comic>>> listener){
        getComicListByType(characterId,COMIC_TYPE_EVENTS,offset,limit).enqueue(listener);
    }


    private Call<DataWrapper<List<Comic>>> getComicListByType(long id,@Type String comicType,Integer offset,Integer limit){
        long timeStamp=System.currentTimeMillis();
        return mMarvelService.getCharacterComics(id,comicType,offset,limit,timeStamp);
    }





}
