package com.avenging.hades.core.data.ui.list;


import android.text.TextUtils;

import com.avenging.hades.core.data.DataManager;
import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.model.DataContainer;
import com.avenging.hades.core.data.model.DataWrapper;
import com.avenging.hades.core.data.network.RemoteCallback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.mockito.Mockito.when;

/**
 * Created by Hades on 2017/5/27.
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
    private ArgumentCaptor<RemoteCallback<DataWrapper<List<CharacterMarvel>>>> mGetCharacterListCallbackCaptor;


    @Before
    public void setUp() throws Exception {
        mockStatic(TextUtils.class);
        mPresenter=new ListPresenter(mDataManager);
        mPresenter.attachView(mView);
    }

    @After
    public void tearDown() throws Exception {
        mPresenter.detachView();
    }

    @Test
    public void testCharacterListRequestedSuccess() throws Exception {
        List<CharacterMarvel> results=asList(new CharacterMarvel(),new CharacterMarvel());

        DataContainer<List<CharacterMarvel>> data=new DataContainer<>();
        data.setResults(results);

        DataWrapper<List<CharacterMarvel>> response=new DataWrapper<>();
        response.setData(data);

        mPresenter.onInitialListRequested();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showMessageLayout(false);
        inOrder.verify(mView).showProgress();

        String searchQuery=null;
        when(TextUtils.isEmpty(searchQuery)).thenReturn(true);

        verify(mDataManager).getCharactersList(anyInt(),anyInt(),eq(searchQuery),mGetCharacterListCallbackCaptor.capture());

        mGetCharacterListCallbackCaptor.getValue().onSuccess(response);

        inOrder.verify(mView).hideProgress();

        inOrder.verify(mView).showCharacters(response.getData().getResults());
    }

    @Test
    public void testCharacterListRequestedNoResult() throws Exception {
        

    }
}