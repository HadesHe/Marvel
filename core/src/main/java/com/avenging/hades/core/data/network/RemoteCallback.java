package com.avenging.hades.core.data.network;


import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hades on 2017/5/17.
 */
public abstract class RemoteCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        switch (response.code()) {
            case HttpsURLConnection.HTTP_OK:
            case HttpURLConnection.HTTP_CREATED:
            case HttpURLConnection.HTTP_ACCEPTED:
            case HttpURLConnection.HTTP_NOT_AUTHORITATIVE:
                if(response.body()!=null){
                    onSuccess(response.body());
                }
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                onUnauthorized();
                break;
            default:
                onFailed(new Throwable("Default "+response.code()+" "+response.message()));

        }

    }

    public abstract void onFailed(Throwable throwable);

    public abstract void onUnauthorized();

    public abstract void onSuccess(T body);

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        onFailed(t);

    }
}
