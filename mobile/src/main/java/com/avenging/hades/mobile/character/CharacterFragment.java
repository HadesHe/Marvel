package com.avenging.hades.mobile.character;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avenging.hades.core.data.DataManager;
import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.ui.character.CharacterContract;
import com.avenging.hades.core.data.ui.character.CharacterPresenter;
import com.avenging.hades.mobile.R;

/**
 * Created by Hades on 2017/5/21.
 */
public class CharacterFragment extends Fragment implements CharacterContract.CharacterView {
    private static final String ARG_CHARACTER = "arg_character";
    private CharacterPresenter mCharacterPresenter;
    private CharacterMarvel mCharacterMarvel;

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
}
