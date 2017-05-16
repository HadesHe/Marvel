package com.avenging.hades.core.data.network;

import okhttp3.OkHttpClient;

/**
 * Created by Hades on 2017/5/16.
 */
public class MarvelServiceFactory {
    public static void makeMarvelService() {
        return makeMarvelService(makeOkHttpClient());
    }

    private static void makeMarvelService(OkHttpClient okHttpClient) {

    }

    private static OkHttpClient makeOkHttpClient() {
        return null;
    }


}
