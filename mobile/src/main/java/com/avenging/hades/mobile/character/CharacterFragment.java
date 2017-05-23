package com.avenging.hades.mobile.character;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avenging.hades.core.data.DataManager;
import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.core.data.ui.character.CharacterContract;
import com.avenging.hades.core.data.ui.character.CharacterPresenter;
import com.avenging.hades.mobile.R;
import com.avenging.hades.mobile.util.ImageLoaderUtil;
import com.avenging.hades.mobile.util.widgets.ComicFrameWrapper;
import com.avenging.hades.mobile.util.widgets.DescritionFrameWrapper;

import java.util.List;

/**
 * Created by Hades on 2017/5/21.
 */
public class CharacterFragment extends Fragment implements CharacterContract.CharacterView {
    private static final String ARG_CHARACTER = "arg_character";
    private CharacterPresenter mCharacterPresenter;
    private CharacterMarvel mCharacterMarvel;
    private AppCompatActivity mActivity;
    private LinearLayout mContentFrame;
    private DescritionFrameWrapper mDescriptionWrapper;
    private ProgressBar mContentLoadingProgress;
    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;

    public static Fragment newInstance(CharacterMarvel characterMarvel) {
        Bundle args=new Bundle();
        args.putParcelable(ARG_CHARACTER,characterMarvel);
        CharacterFragment characterFragment=new CharacterFragment();
        characterFragment.setArguments(args);
        return characterFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().supportStartPostponedEnterTransition();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mCharacterPresenter=new CharacterPresenter(DataManager.getInstance());
        if(savedInstanceState!=null){
            mCharacterMarvel=savedInstanceState.getParcelable(ARG_CHARACTER);
        }else if(getArguments()!=null){
            mCharacterMarvel=getArguments().getParcelable(ARG_CHARACTER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ARG_CHARACTER,mCharacterMarvel);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_character,container,false);
        mCharacterPresenter.attachView(this);
        initView(view);
        mCharacterPresenter.onCharacterRequested(mCharacterMarvel.getId());
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        mActivity=(AppCompatActivity)getActivity();
        toolbar.setTitle(mCharacterMarvel!=null?mCharacterMarvel.getName():mActivity.getString(R.string.character_details));

        mActivity.setSupportActionBar(toolbar);
        ActionBar actionBar=mActivity.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageLoaderUtil.loadImage(mActivity,mCharacterMarvel.getImageUrl(),(ImageView)view.findViewById(R.id.iv_header));

        mContentFrame=(LinearLayout)view.findViewById(R.id.details_content_frame);
        if(!mCharacterMarvel.getDescription().isEmpty()){
            mDescriptionWrapper=new DescritionFrameWrapper(mActivity,mActivity.getResources().getString(R.string.description),
                    mCharacterMarvel.getDescription());
            mContentFrame.addView(mDescriptionWrapper);
        }

        mContentLoadingProgress=(ProgressBar)view.findViewById(R.id.progress);
        mMessageLayout=view.findViewById(R.id.message_layout);
        mMessageImage=(ImageView)view.findViewById(R.id.iv_message);
        mMessageText=(TextView)view.findViewById(R.id.tv_message);
        mMessageButton=(Button)view.findViewById(R.id.btn_try_again);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCharacterPresenter.onCharacterRequested(mCharacterMarvel.getId());
            }
        });
    }

    @Override
    public void onDestroy() {
        mCharacterPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showUnauthorizedError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    @Override
    public void showCharacter(CharacterMarvel mCharacter) {
        mCharacterMarvel=mCharacter;
        if(mDescriptionWrapper==null&&!mCharacterMarvel.getDescription().isEmpty()){
            mDescriptionWrapper=new DescritionFrameWrapper(mActivity,mActivity.getResources().getString(R.string.description),
                    mCharacterMarvel.getDescription());
            mContentFrame.addView(mDescriptionWrapper);

        }

        List<Comic> characterComics=mCharacter.getComics().getItems();
        if(!characterComics.isEmpty()){
            mComicWrapper=new ComicFrameWrapper(mActivity,getString(R.string.comics),characterComics,this);
        }

    }
}
