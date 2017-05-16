package com.avenging.hades.core.data.network;

import com.avenging.hades.core.data.model.CharacterMarvel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hades on 2017/5/16.
 */

public interface MarvelService {

    @GET("characters")
    Call<DataWrapper<List<CharacterMarvel>>>
}
