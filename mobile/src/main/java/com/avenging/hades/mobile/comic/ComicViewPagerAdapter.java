package com.avenging.hades.mobile.comic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.avenging.hades.core.data.model.Comic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2017/5/25.
 */
public class ComicViewPagerAdapter extends FragmentStatePagerAdapter{
    private final ArrayList<Comic> mComicList;

    public ComicViewPagerAdapter(FragmentManager supportFragmentManager, List<Comic> comicList) {
        super(supportFragmentManager);
        mComicList=new ArrayList<>();
        if(comicList!=null){
            mComicList.addAll(comicList);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return ComicViewPagerFragment.newInstance(mComicList.get(position));
    }

    @Override
    public int getCount() {
        return mComicList.size();
    }
}
