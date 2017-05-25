package com.avenging.hades.mobile.util;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Hades on 2017/5/18.
 */
public class ImageLoaderUtil {
    public static void loadImage(Context context, String characterImageUrl, AppCompatImageView image) {
        Picasso.with(context).load(characterImageUrl).centerCrop().fit().into(image);
    }

    public static void loadImage(Context context, String characterImageUrl, ImageView image) {
        Picasso.with(context).load(characterImageUrl).centerCrop().fit().into(image);
    }

    public static void loadImage(Context context, int drawableId, ImageView viewById) {
        Picasso.with(context).load(drawableId).into(viewById);
    }
}
