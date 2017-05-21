package com.avenging.hades.core.data.ui.list;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.avenging.hades.core.data.DataManager;
import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.model.DataWrapper;
import com.avenging.hades.core.data.network.RemoteCallback;
import com.avenging.hades.core.data.ui.base.BasePresenter;

import java.util.List;

/**
 * Created by Hades on 2017/5/17.
 */

public class ListPresenter extends BasePresenter<ListContract.ListView> implements ListContract.ViewActions{

    private static final int ITEM_REQUEST_INITIAL_OFFSET = 0;
    private static final int ITEM_REQUEST_LIMIT = 6;
    private final DataManager mDataManager;


    public ListPresenter(@NonNull DataManager dataManager) {
        mDataManager=dataManager;
    }


    @Override
    public void onInitialListRequested() {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET,ITEM_REQUEST_LIMIT,null);
    }

    @Override
    public void onListEndReached(Integer offset, Integer limit, String searchQuery) {
        getCharacters(offset,limit==null?ITEM_REQUEST_LIMIT:limit,searchQuery);
    }

    @Override
    public void onCharacterSearched(String mSearchQuery) {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET,ITEM_REQUEST_LIMIT,mSearchQuery);

    }

    private void getCharacters(Integer offset, Integer limit, final String searchQuery) {
        if(!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mDataManager.getCharactersList(offset, limit, searchQuery, new RemoteCallback<DataWrapper<List<CharacterMarvel>>>() {
            @Override
            protected void onFailed(Throwable throwable) {
                if(!isViewAttached())return;
                mView.hideProgress();
                mView.showError(throwable.getMessage());

            }

            @Override
            protected void onUnauthorized() {
                if(!isViewAttached())return;
                mView.hideProgress();
                mView.showUnauthorizedError();

            }

            @Override
            protected void onSuccess(DataWrapper<List<CharacterMarvel>> body) {
                if(!isViewAttached())return;
                mView.hideProgress();
                List<CharacterMarvel> responseResults=body.getData().getResults();
                if(responseResults.isEmpty()){
                    mView.showEmpty();
                    return;
                }

                if(TextUtils.isEmpty(searchQuery)){
                    mView.showCharacters(responseResults);
                }else{
                    mView.showSearchedCharacter(responseResults);
                }

            }
        });
    }


}
