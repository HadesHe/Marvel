package com.avenging.hades.mobile.comic;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.mobile.R;
import com.avenging.hades.mobile.util.ImageLoaderUtil;
import com.avenging.hades.mobile.util.animation.Pulse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2017/5/23.
 */
public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder>{

    private static final String TRANSACTION_PREFIX_ = "transactionPrefix_";
    private final List<Comic> mComicList;
    private final IteractionListener mListInteractionListener;

    public ComicAdapter(List<Comic> comicList, IteractionListener listener) {
        mComicList=new ArrayList<>();
        mListInteractionListener=listener;
        addItems(comicList);

    }

    public void addItems(List<Comic> itemsList){
        mComicList.addAll(itemsList);

    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic,parent,false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        holder.title.setText(mComicList.get(position).getName());

        String imageUrl=mComicList.get(position).getThumbnailUri();
        if(!imageUrl.isEmpty()){
            ImageLoaderUtil.loadImage(holder.listItem.getContext(),mComicList.get(position).getThumbnailUri(),holder.imageView);
        }else{
            holder.imageView.setAnimation(new Pulse());
        }
        

    }

    @Override
    public int getItemCount() {
        return mComicList.size();
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder{


        private final View listItem;
        private final TextView title;
        private final ImageView imageView;

        public ComicViewHolder(View view) {
            super(view);
            listItem=view;
            title=(TextView)view.findViewById(R.id.tv_title);
            imageView=(ImageView)view.findViewById(R.id.iv_image);
            ViewCompat.setTransitionName(imageView,TRANSACTION_PREFIX_+getAdapterPosition());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListInteractionListener!=null){
                        mListInteractionListener.onComicClick(mComicList,imageView,getAdapterPosition());
                    }
                }
            });
        }
    }



    public interface IteractionListener{
        void onComicClick(List<Comic> comicList, ImageView sharedImageView,int clickedPosition);
    }
}
