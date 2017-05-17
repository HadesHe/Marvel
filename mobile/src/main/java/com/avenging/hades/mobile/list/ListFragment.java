package com.avenging.hades.mobile.list;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Slide;

import com.avenging.hades.core.data.ui.list.ListContract;

/**
 * Created by Hades on 2017/5/17.
 */
public class ListFragment extends Fragment implements ListContract.ListView {

    public ListFragment(){}

    public static ListFragment newInstance(){
        return newInstance(null);
    }

    private static ListFragment newInstance(@Nullable Bundle arguments) {

        ListFragment fragment=new ListFragment();
        if(arguments!=null){
            fragment.setArguments(arguments);
        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            fragment.setEnterTransition(new Slide());
        }

        return fragment;
    }
}
