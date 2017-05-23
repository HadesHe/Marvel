package com.avenging.hades.mobile.util.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.avenging.hades.mobile.R;

/**
 * Created by Hades on 2017/5/23.
 */
public class DescritionFrameWrapper extends LinearLayout{
    private AppCompatTextView mTitle;
    private AppCompatTextView mDescription;

    public DescritionFrameWrapper(Context context) {
        super(context);
        init(context);
    }

    public DescritionFrameWrapper(Context context, @Nullable String title,@Nullable String description){
        super(context);
        init(context);
        if(title==null||title.isEmpty()){
            mTitle.setText(context.getString(R.string.description));
        }else{
            mTitle.setText(title);
        }

        if(description!=null&&!description.isEmpty()){
            mDescription.setText(description);
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.frame_character_description,this);
        mTitle=(AppCompatTextView)findViewById(R.id.tv_title_description);
        mDescription=(AppCompatTextView)findViewById(R.id.tv_description);
    }
}
