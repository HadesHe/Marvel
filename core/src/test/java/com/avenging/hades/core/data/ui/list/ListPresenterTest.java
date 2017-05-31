package com.avenging.hades.core.data.ui.list;


import android.text.TextUtils;
import android.text.style.EasyEditSpan;

import com.avenging.hades.core.data.DataManager;
import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.model.DataContainer;
import com.avenging.hades.core.data.model.DataWrapper;
import com.avenging.hades.core.data.network.RemoteCallback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Hades on 2017/5/29.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class ListPresenterTest {

    @Mock
    private DataManager mDataManager;

    @Mock
    private ListContract.ListView mView;
    private ListPresenter mPresenter;
    @Captor
    private ArgumentCaptor<RemoteCallback<DataWrapper<List<CharacterMarvel>>>> mGetCharactersListCallbackCaptor;

    @Before
    public void setUp(){
        mockStatic(TextUtils.class);
        mPresenter=new ListPresenter(mDataManager);
        mPresenter.attachView(mView);
    }

    @After
    public void tearDown(){
        mPresenter.detachView();
    }

    @Test
    public void onCharacterListRequestedSuccess(){
        List<CharacterMarvel> results=asList(new CharacterMarvel(),new CharacterMarvel());

        DataContainer<List<CharacterMarvel>> data=new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response=new DataWrapper<>();
        response.setData(data);

        mPresenter.onInitialListRequested();

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        String searchQuery=null;
        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(),anyInt(),eq(searchQuery),
                mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showCharacters(response.getData().getResults());

    }

    @Test
    public void testCharacterListRequestedNoResult() throws Exception {
        List<CharacterMarvel> results= Collections.emptyList();
        DataContainer<List<CharacterMarvel>> data=new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>>  response=new DataWrapper<>();
        response.setData(data);

        mPresenter.onInitialListRequested();

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        String searchQuery=null;
        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);
        verify(mDataManager).getCharactersList(anyInt(),anyInt(),eq(searchQuery),mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showEmpty();
    }

    @Test
    public void testCharacterListRequestedFailed() throws Exception {
        mPresenter.onInitialListRequested();
        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable error=new Throwable("Unknown error");
        String searchQuery=null;
        verify(mDataManager).getCharactersList(anyInt(),anyInt(),eq(searchQuery),mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());
    }

    @Test
    public void testListEndReachedNoSearchQuerySuccess() throws Exception {
        int offset=2;
        int limit=30;
        List<CharacterMarvel> results=asList(new CharacterMarvel(),new CharacterMarvel());
        DataContainer<List<CharacterMarvel>> data=new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response=new DataWrapper<>();
        response.setData(data);

        String searchQuery=null;
        mPresenter.onListEndReached(offset,limit,searchQuery);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(),anyInt(),eq(searchQuery),mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showCharacters(response.getData().getResults());

    }

    @Test
    public void testListEndReachedNoSearchQueryNoResult() throws Exception {
        int offset=2;
        int limit=30;
        List<CharacterMarvel>  results=Collections.emptyList();
        DataContainer<List<CharacterMarvel>>  data=new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response=new DataWrapper<>();
        response.setData(data);

        String searchQuery=null;
        mPresenter.onListEndReached(offset,limit,searchQuery);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(),anyInt(),eq(searchQuery),mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showEmpty();

    }

    @Test
    public void testListEndReachedNoSearchQueryUnauthorized() throws Exception {
        int offset=2;
        int limit=30;

        String searchQuery=null;
        mPresenter.onListEndReached(offset,limit,searchQuery);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharactersList(anyInt(),anyInt(),eq(searchQuery),mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @Test
    public void testListEndReachedNoSearchQueryFailed() throws Exception {

        Integer offset=2;
        Integer limit=30;
        String searchQuery=null;
        mPresenter.onListEndReached(offset,limit,searchQuery);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        Throwable throwable=new Throwable("Unknown error");
        verify(mDataManager).getCharactersList(anyInt(),anyInt(),eq(searchQuery),mGetCharactersListCallbackCaptor.capture());
        mGetCharactersListCallbackCaptor.getValue().onFailed(throwable);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(throwable.getMessage());

    }


}