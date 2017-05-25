package com.avenging.hades.mobile.comic;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.mobile.R;
import com.avenging.hades.mobile.util.ImageLoaderUtil;

/**
 * Created by Hades on 2017/5/25.
 */
public class ComicViewPagerFragment extends Fragment{
    private static final String ARG_COMIC = "arg_comic";
    private Comic mComic;

    public static ComicViewPagerFragment newInstance(Comic comic) {
        ComicViewPagerFragment comicViewPagerFragment=new ComicViewPagerFragment();
        Bundle args=new Bundle();
        args.putParcelable(ARG_COMIC,comic);
        comicViewPagerFragment.setArguments(args);
        setupTransactions(comicViewPagerFragment);
        return comicViewPagerFragment;
    }

    private static void setupTransactions(Fragment fragment) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mComic=getArguments().getParcelable(ARG_COMIC);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container==null){
            return null;
        }

        View view=inflater.inflate(R.layout.fragment_comic_view_pager,container,false);
        ((TextView)view.findViewById(R.id.tv_title)).setText(mComic.getName());

        String imageUrl=mComic.getThumbnailUri();
        if(!TextUtils.isEmpty(imageUrl)){
            ImageLoaderUtil.loadImage(getContext(),imageUrl,(ImageView) view.findViewById(R.id.iv_image));
        }else{
            ImageLoaderUtil.loadImage(getContext(),R.drawable.ic_error_list,(ImageView) view.findViewById(R.id.iv_image));
        }
        return view;
    }
}
