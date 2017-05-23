package com.avenging.hades.mobile.util.widgets;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.mobile.character.CharacterFragment;
import com.avenging.hades.mobile.comic.ComicAdapter;

import java.util.List;

/**
 * Created by Hades on 2017/5/23.
 */
public class ComicFrameWrapper extends LinearLayout {

    private TextView mTitle;
    private ComicAdapter mComicAdapter;

    public ComicFrameWrapper(Context context){
        super(context);
    }
    public ComicFrameWrapper(Context context, String title, List<Comic> comicList, ComicAdapter.IteractionListener listener) {
        super(context);
        mComicAdapter=new ComicAdapter(comicList,listener);
        init(context);
        if(mTitle==null){
            return;
        }

        if(!TextUtils.isEmpty(title)){
            mTitle.setText(title);
        }
    }
}
