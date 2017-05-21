package com.avenging.hades.mobile.list;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avenging.hades.core.data.DataManager;
import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.ui.list.ListContract;
import com.avenging.hades.core.data.ui.list.ListPresenter;
import com.avenging.hades.mobile.R;
import com.avenging.hades.mobile.character.CharacterActivity;
import com.avenging.hades.mobile.util.DisplayMetricsUtil;

import java.util.List;

/**
 * Created by Hades on 2017/5/17.
 */
public class ListFragment extends Fragment implements ListContract.ListView, ListAdapter.InteractionListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private static final int SCREEN_TABLET_DP_WIDTH = 600;
    private static final int TAB_LAYOUT_SPAN_SIZE = 2;
    private static final int TAB_LAYOUT_ITEM_SPAN_SIZE = 1;
    private ListPresenter mListPresenter;
    private ListAdapter mListCharacterAdapter;
    private AppCompatActivity mActivity;
    private RecyclerView mCharacterRecycler;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mContentLoadingProgress;
    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;
    private String mSearchQuery;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mListPresenter=new ListPresenter(DataManager.getInstance());
        mListCharacterAdapter=new ListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list,container,false);
        initViews(view);
        mListPresenter.attachView(this);
        mListCharacterAdapter.setListInteractionListener(this);
        if(mListCharacterAdapter.isEmpty()){
            mListPresenter.onInitialListRequested();
        }
        return view;
    }

    private void initViews(View view) {
        mActivity=(AppCompatActivity)getActivity();
        mActivity.setSupportActionBar((Toolbar)view.findViewById(R.id.toolbar));
        mCharacterRecycler=(RecyclerView)view.findViewById(R.id.recycler_characters);
        mCharacterRecycler.setHasFixedSize(true);
        mCharacterRecycler.setMotionEventSplittingEnabled(false);
        mCharacterRecycler.setItemAnimator(new DefaultItemAnimator());
        mCharacterRecycler.setAdapter(mListCharacterAdapter);

        boolean isTabletLayout= DisplayMetricsUtil.isScreenW(SCREEN_TABLET_DP_WIDTH);
        mCharacterRecycler.setLayoutManager(setupLayoutManager(isTabletLayout));
        mCharacterRecycler.addOnScrollListener(setupScrollListener(isTabletLayout,mCharacterRecycler.getLayoutManager()));

        mSwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_to_refresh);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mContentLoadingProgress=(ProgressBar)view.findViewById(R.id.progress);

        mMessageLayout=view.findViewById(R.id.message_layout);
        mMessageImage=(ImageView)view.findViewById(R.id.iv_message);
        mMessageText=(TextView)view.findViewById(R.id.tv_message);
        mMessageButton=(Button)view.findViewById(R.id.btn_try_again);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    private EndLessRecyclerViewOnScrollListener setupScrollListener(boolean isTabletLayout, RecyclerView.LayoutManager layoutManager) {
        return new EndLessRecyclerViewOnScrollListener(isTabletLayout?(GridLayoutManager)layoutManager:(LinearLayoutManager)layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if(mListCharacterAdapter.addLoadingView()){
                    mListPresenter.onListEndReached(totalItemsCount,null,mSearchQuery);
                }

            }
        };
    }

    private RecyclerView.LayoutManager setupLayoutManager(boolean isTabletLayout) {
        RecyclerView.LayoutManager layoutManager;
        if(!isTabletLayout){
            layoutManager=new LinearLayoutManager(mActivity);
        }else {
            layoutManager=initGridLayoutManager(TAB_LAYOUT_SPAN_SIZE,TAB_LAYOUT_ITEM_SPAN_SIZE);
        }
        return layoutManager;
    }

    private RecyclerView.LayoutManager initGridLayoutManager(final int spanCount, final int itemSpanCount) {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mActivity,spanCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mListCharacterAdapter.getItemViewType(position)){
                    case ListAdapter.VIEW_TYPE_LOADING:
                        return spanCount;
                    default:
                        return itemSpanCount;
                }
            }
        });
        return gridLayoutManager;
    }

    @Override
    public void onListClick(CharacterMarvel character, View sharedElementView, int adapterPosition) {
        startActivity(CharacterActivity.newStartIntent(mActivity,character),
                makeTransitionBundle(sharedElementView));
    }

    private Bundle makeTransitionBundle(View sharedElementView) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,
                sharedElementView, ViewCompat.getTransitionName(sharedElementView)).toBundle();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final SearchView searchView= (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mSearchQuery="";
                mListCharacterAdapter.setViewType(ListAdapter.VIEW_TYPE_LIST);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onRefresh() {
        mListCharacterAdapter.removeAll();
        mListPresenter.onInitialListRequested();

    }

    @Override
    public void showCharacters(List<CharacterMarvel> responseResults) {
        if(mListCharacterAdapter.getViewType()!=ListAdapter.VIEW_TYPE_GALLERY){
            mListCharacterAdapter.removeAll();
            mListCharacterAdapter.setViewType(ListAdapter.VIEW_TYPE_GALLERY);
        }

        if(!mSwipeRefreshLayout.isActivated()){
            mSwipeRefreshLayout.setEnabled(true);
        }

        mListCharacterAdapter.addItems(responseResults);

    }

    @Override
    public void showSearchedCharacter(List<CharacterMarvel> searchResults) {
        if(mListCharacterAdapter.getViewType()!=ListAdapter.VIEW_TYPE_LIST){
            mListCharacterAdapter.removeAll();
            mListCharacterAdapter.setViewType(ListAdapter.VIEW_TYPE_LIST);
        }

        if(mSwipeRefreshLayout.isActivated()){
            mSwipeRefreshLayout.setEnabled(false);
        }

        mListCharacterAdapter.addItems(searchResults);
    }

    @Override
    public void showProgress() {
        if(mListCharacterAdapter.isEmpty()&&!mSwipeRefreshLayout.isRefreshing()){
            mContentLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
        mContentLoadingProgress.setVisibility(View.GONE);
        mListCharacterAdapter.removeLoadingView();

    }

    @Override
    public void showUnauthorizedError() {
        mMessageImage.setImageResource(R.drawable.ic_error_list);
        mMessageText.setText(getString(R.string.error_generic_server_error,"Unauthorized"));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);

    }

    @Override
    public void showError(String errorMessage) {
        mMessageImage.setImageResource(R.drawable.ic_error_list);
        mMessageText.setText(getString(R.string.error_generic_server_error,errorMessage));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showEmpty() {
        mMessageImage.setImageResource(R.drawable.ic_clear);
        mMessageText.setText(getString(R.string.error_no_items_to_display));
        mMessageButton.setText(getString(R.string.action_check_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        mMessageLayout.setVisibility(show?View.VISIBLE:View.GONE);
        mCharacterRecycler.setVisibility(show?View.GONE:View.VISIBLE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchQuery=newText;
        if(!TextUtils.isEmpty(newText)){
            mListPresenter.onCharacterSearched(mSearchQuery);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        mCharacterRecycler.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mListPresenter.detachView();
        super.onDestroy();
    }
}
