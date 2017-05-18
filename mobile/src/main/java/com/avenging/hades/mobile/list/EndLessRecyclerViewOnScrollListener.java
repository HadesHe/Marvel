package com.avenging.hades.mobile.list;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Hades on 2017/5/18.
 */
public abstract class EndLessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener{

    private static final int STARTING_PAGE_INDEX=0;
    private final RecyclerView.LayoutManager mLayoutManager;
    private static int sVisibleThreshold=2;
    private int mPreviousTotalItemCount=0;
    private int mCurrentPage=0;
    private boolean mLoading=true;

    public EndLessRecyclerViewOnScrollListener(LinearLayoutManager layoutManager){
        mLayoutManager=layoutManager;
    }

    public EndLessRecyclerViewOnScrollListener(GridLayoutManager layoutManager){
        mLayoutManager=layoutManager;
    }

    public EndLessRecyclerViewOnScrollListener(StaggeredGridLayoutManager layoutManager){
        mLayoutManager=layoutManager;
        sVisibleThreshold=sVisibleThreshold*layoutManager.getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItemPosition=0;
        int totalItemCount=mLayoutManager.getItemCount();
        if(mLayoutManager instanceof StaggeredGridLayoutManager){
            int[] lastVisibleItemPositions=((StaggeredGridLayoutManager)mLayoutManager)
                    .findLastVisibleItemPositions(null);
            lastVisibleItemPosition=getLastVisibleItem(lastVisibleItemPositions);
        }else if(mLayoutManager instanceof LinearLayoutManager){
            lastVisibleItemPosition=((LinearLayoutManager)mLayoutManager)
                    .findLastVisibleItemPosition();
        }else if(mLayoutManager instanceof GridLayoutManager){
            lastVisibleItemPosition=((GridLayoutManager)mLayoutManager).findLastVisibleItemPosition();
        }

        if(totalItemCount<mPreviousTotalItemCount){
            mCurrentPage=STARTING_PAGE_INDEX;
            mPreviousTotalItemCount=totalItemCount;
            if(totalItemCount==0){
                mLoading=true;
            }
        }

        if(mLoading&&(totalItemCount>mPreviousTotalItemCount+1)){
            mLoading=false;
            mPreviousTotalItemCount=totalItemCount;
        }

        if(!mLoading&&(lastVisibleItemPosition+sVisibleThreshold)>totalItemCount){
            mCurrentPage++;
            onLoadMore(mCurrentPage,totalItemCount);
            mLoading=true;
        }

    }

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize=0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if(i==0){
                maxSize=lastVisibleItemPositions[i];
            }else if(lastVisibleItemPositions[i]>maxSize){
                maxSize=lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    public abstract void onLoadMore(int page, int totalItemsCount);
}
