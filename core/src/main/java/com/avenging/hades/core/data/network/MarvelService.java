package com.avenging.hades.core.data.network;

import android.support.annotation.Nullable;

import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.core.data.model.DataWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hades on 2017/5/16.
 */

public interface MarvelService {

    @GET("characters")
    Call<DataWrapper<List<CharacterMarvel>>> getCharacters(
                                                           @Query("ts") long timestamp,
                                                           @Nullable @Query("offset") Integer offset,
                                                           @Nullable @Query("limit") Integer limit,
                                                           @Nullable @Query("nameStartsWith") String searchQuery);


    @GET("characters/{characterId}")
    Call<DataWrapper<List<CharacterMarvel>>> getCharacter(@Path("characterId") long characterId,
                                                          @Query("ts") long timestamp);

    @GET("characters/{characterId}/{comicType}")
    Call<DataWrapper<List<Comic>>> getCharacterComics(@Path("characterId") long characterId,
                                                      @Path("comicType") String comicType,
                                                      @Query("offset") Integer offset,
                                                      @Query("limit") Integer limit,
                                                      @Query("ts") long timestamp);
}
