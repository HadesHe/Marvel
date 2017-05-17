package com.avenging.hades.core.data.network;

import com.avenging.hades.core.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Hades on 2017/5/16.
 */
public class MarvelServiceFactory {

    private static final String BASE_URL = "http://gateway.marvel.com/v1/public/";
    private static final int HTTP_READ_TIMEOUT = 10000;
    private static final int HTTP_CONNECT_TIMEOUT = 6000;

    public static MarvelService makeMarvelService() {
        return makeMarvelService(makeOkHttpClient());
    }

    private static MarvelService makeMarvelService(OkHttpClient okHttpClient) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(MarvelService.class);

    }

    private static OkHttpClient makeOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder=new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT,TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(makeLoggingInterceptor());
        return httpClientBuilder.build();
    }

    private static HttpLoggingInterceptor makeLoggingInterceptor(){
        HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG?HttpLoggingInterceptor.Level.BODY:
        HttpLoggingInterceptor.Level.NONE);
        return logging;
    }


}
