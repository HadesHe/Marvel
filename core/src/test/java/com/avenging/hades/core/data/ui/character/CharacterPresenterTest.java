package com.avenging.hades.core.data.ui.character;

import com.avenging.hades.core.data.DataManager;
import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.core.data.model.DataContainer;
import com.avenging.hades.core.data.model.DataWrapper;
import com.avenging.hades.core.data.network.RemoteCallback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.inOrder;


/**
 * Created by Hades on 2017/5/31.
 */
public class CharacterPresenterTest {


    @Mock
    private DataManager mDataManager;

    @Mock
    private CharacterContract.CharacterView mView;

    private CharacterPresenter mPresenter;
    private long characterId =1l;
    @Captor
    private ArgumentCaptor<RemoteCallback<DataWrapper<List<CharacterMarvel>>>> mGetCharacterCallbackCaptor;
    private int limit=30;

    @Captor
    private ArgumentCaptor<RemoteCallback<DataWrapper<List<Comic>>>> mGetComicListCallbackCaptor;

    @Before
    public void setUp() throws Exception {

        mPresenter=new CharacterPresenter(mDataManager);
        mPresenter.attachView(mView);
    }

    @After
    public void tearDown() throws Exception {
        mPresenter.detachView();
    }

    @Test
    public void testCharacterRequested_Success() throws Exception {
        mPresenter.onCharacterRequested(characterId);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharacter(anyLong(),mGetCharacterCallbackCaptor.capture());
        DataWrapper<List<CharacterMarvel>> response=fakeSingleList_Success();
        mGetCharacterCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showCharacter(response.getData().getResults().get(CharacterPresenter.SINGLE_ITEM_INDEX));

    }

    @Test
    public void testCharacterRequested_SameId() throws Exception {

        CharacterMarvel characterMarvel=new CharacterMarvel();
        characterMarvel.setId(characterId);
        List<CharacterMarvel> results=Collections.singletonList(characterMarvel);
        DataContainer<List<CharacterMarvel>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response=new DataWrapper<>();
        response.setData(data);

        mPresenter.onCharacterRequested(characterId);
        verify(mDataManager).getCharacter(anyLong(),mGetCharacterCallbackCaptor.capture());
        mGetCharacterCallbackCaptor.getValue().onSuccess(response);
        verify(mView).showCharacter(response.getData().getResults().get(CharacterPresenter.SINGLE_ITEM_INDEX));

        mPresenter.onCharacterRequested(characterId);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showCharacter(response.getData().getResults()
        .get(CharacterPresenter.SINGLE_ITEM_INDEX));
        verify(mView, Mockito.times(1)).showProgress();

    }

    @Test
    public void testCharacterRequested_Unauthorized() throws Exception {
        mPresenter.onCharacterRequested(characterId);
        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharacter(anyLong(),mGetCharacterCallbackCaptor.capture());
        mGetCharacterCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();

    }

    @Test
    public void testCharacterRequested_Empty() throws Exception {
        mPresenter.onCharacterRequested(characterId);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharacter(anyLong(),mGetCharacterCallbackCaptor.capture());
        mGetCharacterCallbackCaptor.getValue().onSuccess(fakeList_Empty());

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());

    }

    @Test
    public void testCharacterRequested_Failed() throws Exception {
        mPresenter.onCharacterRequested(characterId);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getCharacter(anyLong(),mGetCharacterCallbackCaptor.capture());
        Throwable throwable=new Throwable("Unknown error");
        mGetCharacterCallbackCaptor.getValue().onFailed(throwable);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(throwable.getMessage());

    }


    @Test
    public void testCharacterComicListRequsted_Success() throws Exception {
        mPresenter.onCharacterComicsRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getComics(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        DataWrapper<List<Comic>> response=fakeComicList_Success();
        mGetComicListCallbackCaptor.getValue().onSuccess(response);
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showComicList(response.getData().getResults());

    }

    @Test
    public void testCharacterComicListRequested_NoResult() throws Exception {
        mPresenter.onCharacterComicsRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getComics(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(fakeComicEmptyList_NoResult());
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());

    }

    @Test
    public void testCharacterComicListRequsted_Unauthorized() throws Exception {
        mPresenter.onCharacterComicsRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getComics(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onUnauthorized();
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();

    }

    @Test
    public void testCharacterComicListRequested_Failed() throws Exception {
        mPresenter.onCharacterComicsRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getComics(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        Throwable error=new Throwable("Unknown error");
        mGetComicListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());

    }

    @Test
    public void testCharacterSeriesListRequested_Success() throws Exception {
        mPresenter.onCharacterSeriesRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getSeries(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        DataWrapper<List<Comic>> response=fakeSerieList_Success();
        mGetComicListCallbackCaptor.getValue().onSuccess(response);
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showSeriesList(response.getData().getResults());

    }

    @Test
    public void testCharacterSeriesListRequested_NoResult() throws Exception {
        mPresenter.onCharacterSeriesRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);

        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getSeries(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        DataWrapper<List<Comic>> response=fakeSerieList_NoResult();
        mGetComicListCallbackCaptor.getValue().onSuccess(response);
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());

    }

    @Test
    public void testCharacterSeriesListRequsted_Unauthorized() throws Exception {
        mPresenter.onCharacterSeriesRequested(characterId,limit);
        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getSeries(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onUnauthorized();
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();
    }

    @Test
    public void testCharacterSeriesRequested_Failed() throws Exception {
        mPresenter.onCharacterSeriesRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getSeries(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        Throwable throwable=new Throwable("Unknown error");
        mGetComicListCallbackCaptor.getValue().onFailed(throwable);
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(throwable.getMessage());

    }

    @Test
    public void testCharacterStoriesListRequested_Success() throws Exception {

        mPresenter.onCharacterStoriesRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getStories(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        DataWrapper<List<Comic>> response=fakeStoriesList_Success();
        mGetComicListCallbackCaptor.getValue().onSuccess(response);
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showStoriesList(response.getData().getResults());

    }

    private DataWrapper<List<Comic>> fakeStoriesList_Success() {
        List<Comic> results=asList(new Comic(),new Comic());
        DataContainer<List<Comic>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }

    @Test
    public void testCharacterStoriesListRequested_NoResult() throws Exception {
        mPresenter.onCharacterStoriesRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getStories(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(fakeStoriesList_NoResult());
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());


    }

    private DataWrapper<List<Comic>> fakeStoriesList_NoResult() {
        List<Comic> results=Collections.emptyList();
        DataContainer<List<Comic>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }

    @Test
    public void testCharacterStoriesListRequested_Unauthorized() throws Exception {
        mPresenter.onCharacterStoriesRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getStories(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onUnauthorized();

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();

    }

    @Test
    public void testCharacterStoriesListRequested_Failed() throws Exception {

        mPresenter.onCharacterStoriesRequested(characterId,limit);
        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getStories(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        Throwable error=new Throwable("Unknown error");
        mGetComicListCallbackCaptor.getValue().onFailed(error);
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());

    }

    @Test
    public void testCharacterEventsRequsted_Success() throws Exception {

        mPresenter.onCharacterEventRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getEvents(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        DataWrapper<List<Comic>> response=fakeCharacterEvents_Success();
        mGetComicListCallbackCaptor.getValue().onSuccess(response);
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showEventsList(response.getData().getResults());
    }

    private DataWrapper<List<Comic>> fakeCharacterEvents_Success() {

        List<Comic> results=asList(new Comic(),new Comic());
        DataContainer<List<Comic>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }

    @Test
    public void testCharacterEventsRequsted_NoResult() throws Exception {

        mPresenter.onCharacterEventRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        verify(mDataManager).getEvents(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onSuccess(fakeCharacterEvents_NoResult());
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(anyString());

    }

    private DataWrapper<List<Comic>> fakeCharacterEvents_NoResult() {
        List<Comic> results=Collections.emptyList();
        DataContainer<List<Comic>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }

    @Test
    public void testCharacterEventsRequsted_Unauthorized() throws Exception {
        mPresenter.onCharacterEventRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getEvents(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        mGetComicListCallbackCaptor.getValue().onUnauthorized();
        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showUnauthorizedError();

    }

    @Test
    public void testCharacterEventsRequsted_Failed() throws Exception {
        mPresenter.onCharacterEventRequested(characterId,limit);

        InOrder inOrder=inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();
        verify(mDataManager).getEvents(anyLong(),anyInt(),anyInt(),mGetComicListCallbackCaptor.capture());
        Throwable error=new Throwable("Unknowned error");
        mGetComicListCallbackCaptor.getValue().onFailed(error);

        inOrder.verify(mView).hideProgress();
        inOrder.verify(mView).showError(error.getMessage());


    }

    private DataWrapper<List<Comic>> fakeSerieList_NoResult() {
        List<Comic> results=Collections.emptyList();
        DataContainer<List<Comic>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }

    private DataWrapper<List<Comic>> fakeSerieList_Success() {
        List<Comic> results=asList(new Comic(),new Comic());
        DataContainer<List<Comic>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }

    private DataWrapper<List<Comic>> fakeComicEmptyList_NoResult() {
        List<Comic> results=Collections.emptyList();
        DataContainer<List<Comic>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }



    private DataWrapper<List<Comic>> fakeComicList_Success() {
        List<Comic> results=asList(new Comic(),new Comic());
        DataContainer<List<Comic>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<Comic>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }

    private DataWrapper<List<CharacterMarvel>> fakeList_Empty() {
        List<CharacterMarvel> results=Collections.emptyList();
        DataContainer<List<CharacterMarvel>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }

    private DataWrapper<List<CharacterMarvel>> fakeSingleList_Success(){
        List<CharacterMarvel> results=Collections.singletonList(new CharacterMarvel());
        DataContainer<List<CharacterMarvel>> data=new DataContainer<>();
        data.setResults(results);
        DataWrapper<List<CharacterMarvel>> response=new DataWrapper<>();
        response.setData(data);
        return response;
    }
}