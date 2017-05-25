package com.avenging.hades.mobile.comic;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;

import com.avenging.hades.core.data.model.Comic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2017/5/25.
 */
public class ComicFragment extends Fragment{
    public static final String TAG = "ComicFragment";
    private static final String ARG_CLICKED_POSITION = "arg_clicked_position";
    private static final String ARG_TRANSACTION_NAME = "arg_transaction_name";
    private static final String ARG_COMIC_LIST = "arg_comic_list";
    private List<Comic> mComicList;
    private int mClickedPosition;
    private String mTransactionName;
    private AppCompatActivity mActivity;
    private ComicViewPagerAdapter mViewPagerAdapter;

    public static ComicFragment newInstance(List<Comic> comicList, int clickedPosition) {
        return newInstance(comicList,null,clickedPosition);
    }

    private static ComicFragment newInstance(List<Comic> comicList, String transactionName, int clickedPosition) {
        ComicFragment fragment=new ComicFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_CLICKED_POSITION,clickedPosition);
        if(transactionName != null){
            args.putString(ARG_TRANSACTION_NAME,transactionName);
        }
        args.putParcelableArrayList(ARG_COMIC_LIST, (ArrayList<Comic>) comicList);
        fragment.setArguments(args);
        setupTransactions(fragment);
        return fragment;
    }

    public static void setupTransactions(Fragment fragment) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            fragment.setSharedElementEnterTransition(new AutoTransition());
            fragment.setSharedElementReturnTransition(new AutoTransition());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mComicList=getArguments().getParcelableArrayList(ARG_COMIC_LIST);
            mClickedPosition=getArguments().getInt(ARG_CLICKED_POSITION);
            mTransactionName=getArguments().getString(ARG_TRANSACTION_NAME);
        }
        mActivity= (AppCompatActivity) getActivity();
        mViewPagerAdapter=new ComicViewPagerAdapter(mActivity.getSupportFragmentManager(),mComicList);
    }
}
