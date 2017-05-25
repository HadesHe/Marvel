package com.avenging.hades.core.data.ui.character;

import com.avenging.hades.core.data.DataManager;
import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.core.data.model.DataWrapper;
import com.avenging.hades.core.data.network.RemoteCallback;
import com.avenging.hades.core.data.ui.base.BasePresenter;

import java.util.List;

/**
 * Created by Hades on 2017/5/17.
 */

public class CharacterPresenter extends BasePresenter<CharacterContract.CharacterView> implements CharacterContract.ViewActions {
    private static final int SINGLE_ITEM_INDEX = 0;
    private final DataManager mDataManager;
    private CharacterMarvel mCharacter;

    public CharacterPresenter(DataManager instance) {
        mDataManager=instance;
    }

    @Override
    public void onCharacterRequested(long characterId) {
        getCharacter(characterId);

    }

    @Override
    public void onCharacterComicsRequested(long id, int size) {
        getComicList(id,null,size);
    }

    @Override
    public void onCharacterSeriesRequested(long id, int size) {
        getSeriesList(id,null,size);

    }

    @Override
    public void onCharacterStoriesRequested(long id, int size) {
        getStoriesList(id,null,size);
    }

    @Override
    public void onCharacterEventRequested(long id, int size) {
        getEventsList(id,null,size);
    }

    private void getEventsList(long id, Integer offset, int size) {
        if(!isViewAttached())return;
        mView.showMessageLayout(false);
        mView.showProgress();

        mDataManager.getComics(id, offset, size, new RemoteCallback<DataWrapper<List<Comic>>>() {
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
            protected void onSuccess(DataWrapper<List<Comic>> body) {
                if(!isViewAttached())return;
                mView.hideProgress();
                if(body.getData().getResults().isEmpty()){
                    mView.showError("Character has no comics");
                    return;
                }
                mView.showEventsList(body.getData().getResults());

            }
        });
    }

    private void getStoriesList(long id, Integer offset, Integer size) {
        if(!isViewAttached())return;
        mView.showMessageLayout(false);
        mView.showProgress();

        mDataManager.getComics(id, offset, size, new RemoteCallback<DataWrapper<List<Comic>>>() {
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
            protected void onSuccess(DataWrapper<List<Comic>> body) {
                if(!isViewAttached())return;
                mView.hideProgress();
                if(body.getData().getResults().isEmpty()){
                    mView.showError("Character has no comics");
                    return;
                }
                mView.showStoriesList(body.getData().getResults());

            }
        });
    }

    private void getSeriesList(Long id, Integer offset, Integer size) {
        if(!isViewAttached())return;
        mView.showMessageLayout(false);
        mView.showProgress();

        mDataManager.getComics(id, offset, size, new RemoteCallback<DataWrapper<List<Comic>>>() {
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
            protected void onSuccess(DataWrapper<List<Comic>> body) {
                if(!isViewAttached())return;
                mView.hideProgress();
                if(body.getData().getResults().isEmpty()){
                    mView.showError("Character has no comics");
                    return;
                }
                mView.showSeriesList(body.getData().getResults());

            }
        });
    }

    private void getComicList(Long id, Integer offset, Integer size) {
        if(!isViewAttached())return;
        mView.showMessageLayout(false);
        mView.showProgress();

        mDataManager.getComics(id, offset, size, new RemoteCallback<DataWrapper<List<Comic>>>() {
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
            protected void onSuccess(DataWrapper<List<Comic>> body) {
                if(!isViewAttached())return;
                mView.hideProgress();
                if(body.getData().getResults().isEmpty()){
                    mView.showError("Character has no comics");
                    return;
                }
                mView.showComicList(body.getData().getResults());

            }
        });
    }


    private void getCharacter(long id) {
        if(!isViewAttached()) return;

        mView.showMessageLayout(false);
        if(mCharacter!=null&&mCharacter.getId()==id){
            mView.showCharacter(mCharacter);
            return;
        }

        mView.showProgress();
        mDataManager.getCharacter(id, new RemoteCallback<DataWrapper<List<CharacterMarvel>>>() {
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
            protected void onSuccess(DataWrapper<List<CharacterMarvel>> response) {
                if(!isViewAttached())return;
                mView.hideProgress();
                if(response.getData().getResults().isEmpty()){
                    mView.showError("Character does not exist");
                    return;
                }

                mCharacter=response.getData().getResults().get(SINGLE_ITEM_INDEX);
                mView.showCharacter(mCharacter);
            }
        });


    }
}
