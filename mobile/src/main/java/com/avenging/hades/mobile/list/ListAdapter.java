package com.avenging.hades.mobile.list;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.mobile.R;
import com.avenging.hades.mobile.util.ImageLoaderUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2017/5/18.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int VIEW_TYPE_GALLERY = 0;
    public static final int VIEW_TYPE_LIST = 1;
    public static final int VIEW_TYPE_LOADING = 2;
    private static final String TAG = ListAdapter.class.getSimpleName();
    private final ArrayList<CharacterMarvel> mCharacterList;

    public boolean addLoadingView() {

        if(getItemViewType(mCharacterList.size()-1)!=VIEW_TYPE_LOADING){
            add(null);
            return true;
        }
        return false;
    }

    private void add(CharacterMarvel item) {
        add(null,item);
    }

    private void add(@Nullable Integer position, CharacterMarvel item) {
        if(position!=null){
            mCharacterList.add(position,item);
            notifyItemInserted(position);

        }else{
            mCharacterList.add(item);
            notifyItemInserted(mCharacterList.size()-1);

        }
    }

    public void removeAll() {
        mCharacterList.clear();
        notifyDataSetChanged();
    }

    public void setViewType(@ViewType int viewType){
        mViewType=viewType;
    }

    public int getViewType() {
        return mViewType;
    }

    public void addItems(List<CharacterMarvel> itemsList) {
        mCharacterList.addAll(itemsList);
        notifyItemRangeInserted(getItemCount(),mCharacterList.size()-1);
    }

    public boolean removeLoadingView() {
        if(mCharacterList.size()>1){
            int loadingViewPosition=mCharacterList.size()-1;
            if(getItemViewType(loadingViewPosition)==VIEW_TYPE_LOADING){
                remove(loadingViewPosition);
                return true;
            }
        }
        return false;
    }

    public void remove(int position) {
        if(mCharacterList.size()<position){
            Log.w(TAG,"The item at position: "+position+" doesn't exist");
            return;
        }
        mCharacterList.remove(position);
        notifyItemRemoved(position);

    }

    @IntDef({VIEW_TYPE_LOADING,VIEW_TYPE_GALLERY,VIEW_TYPE_LIST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType{

    }

    private InteractionListener mListInteractionListener;

    @ViewType
    private int mViewType;

    public ListAdapter(){
        mCharacterList=new ArrayList<>();
        mViewType=VIEW_TYPE_GALLERY;
        mListInteractionListener=null;
    }

    public void setListInteractionListener(InteractionListener listener) {
        mListInteractionListener=listener;
    }

    @Override
    public int getItemViewType(int position) {
        return mCharacterList.get(position)==null?VIEW_TYPE_LOADING:mViewType;
    }

    public boolean isEmpty() {
        return getItemCount()==0;
    }

    @Override
    public long getItemId(int position) {
        return mCharacterList.size()>=position?mCharacterList.get(position).getId():-1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_LOADING){
            return onIndicationViewHolder(parent);
        }
        return onGenericItemViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()==VIEW_TYPE_LOADING){
            return ;
        }
        onBindGenericItemViewHolder((CharacterViewHolder)holder,position);

    }

    private RecyclerView.ViewHolder onIndicationViewHolder(ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar,parent,false);
        return new ProgressBarViewHolder(view);
    }

    private RecyclerView.ViewHolder onGenericItemViewHolder(ViewGroup parent,int viewType){
        View view=null;
        switch (viewType){
            case VIEW_TYPE_GALLERY:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character_gallery,parent,false);
                break;
            case VIEW_TYPE_LIST:
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character_list,parent,false);
                break;
        }
        return new CharacterViewHolder(view);
    }

    private void onBindGenericItemViewHolder(CharacterViewHolder holder,int position){
        holder.name.setText(mCharacterList.get(position).getName());

        String characterImageUrl=mCharacterList.get(position).getImageUrl();
        if(!TextUtils.isEmpty(characterImageUrl)){
            ImageLoaderUtil.loadImage(holder.listItem.getContext(),characterImageUrl,holder.image);
        }
    }

    public class ProgressBarViewHolder extends RecyclerView.ViewHolder{
        public final ProgressBar progressBar;


        public ProgressBarViewHolder(View itemView) {
            super(itemView);
            progressBar= (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder{

        private final TextView name;
        private final AppCompatImageView image;
        private final View listItem;

        public CharacterViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            image=(AppCompatImageView)itemView.findViewById(R.id.image);
            listItem=itemView;

            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListInteractionListener!=null){
                        mListInteractionListener.onListClick(mCharacterList.get(getAdapterPosition()),image,getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString()+" '"+name.getText()+" '";
        }
    }



    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    public interface InteractionListener{
        void onListClick(CharacterMarvel character, View sharedElementView, int adapterPosition);
    }
}
