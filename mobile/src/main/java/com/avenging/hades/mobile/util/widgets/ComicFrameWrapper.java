package com.avenging.hades.mobile.util.widgets;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.mobile.R;
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

    private void init(Context context) {
        inflate(context, R.layout.frame_wrapper_comic,this);

        mTitle= (TextView) findViewById(R.id.tv_title);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.rv_comic_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mComicAdapter);

    }

    public void loadImages(List<Comic> comicList){
        for (int i = 0; i < comicList.size(); i++) {
         mComicAdapter.getItem(i).setThumbnail(comicList.get(i).getThumbnail());
            mComicAdapter.notifyItemChanged(i);
        }
    }
}
