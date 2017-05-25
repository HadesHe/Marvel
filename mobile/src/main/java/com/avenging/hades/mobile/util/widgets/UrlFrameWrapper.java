package com.avenging.hades.mobile.util.widgets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avenging.hades.core.data.model.Url;
import com.avenging.hades.mobile.R;

import java.util.List;

/**
 * Created by Hades on 2017/5/25.
 */
public class UrlFrameWrapper extends LinearLayout {

    private TextView mTitle;
    private LinearLayout mLinksContentFrame;

    public UrlFrameWrapper(Context context){
        super(context);
        init(context);
        mTitle.setText(context.getResources().getString(R.string.related_links));

    }

    public UrlFrameWrapper(Context context, @Nullable String title,@Nullable List<Url> urls) {
        super(context);
        init(context);
        if(!TextUtils.isEmpty(title)){
            mTitle.setText(title);
        }

        if(urls == null&&!urls.isEmpty()){
            addLinks(context,urls);
        }
    }

    private void addLinks(final Context context, List<Url> urls) {
        UrlFrame subItemUrl;
        for (int i = 0; i < urls.size(); i++) {
            Url urlItem=urls.get(i);
            subItemUrl=new UrlFrame(context,urlItem.getType(),i==urls.size()-1);
            final String currentLinkUrl=urlItem.getUrl();
            subItemUrl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browerIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(currentLinkUrl));
                    context.startActivity(browerIntent);
                }
            });

            mLinksContentFrame.addView(subItemUrl);

        }


    }

    private void init(Context context){
        inflate(context,R.layout.frame_wrapper_url,this);
        mTitle=(TextView)findViewById(R.id.tv_title_links);
        mLinksContentFrame=(LinearLayout)findViewById(R.id.url_content_frame);
    }

    private class UrlFrame extends LinearLayout{
        private AppCompatTextView mLink;

        public UrlFrame(Context context, String linkName, boolean isLastLink) {
            super(context);
            init(context,linkName,isLastLink);
        }

        private void init(Context context,String linkName,boolean isLastLink){
            inflate(context,R.layout.frame_url,this);
            mLink=(AppCompatTextView)findViewById(R.id.tv_link);
            mLink.setText(linkName);
            if(isLastLink){
                findViewById(R.id.line).setVisibility(GONE);
            }
        }

        public void setOnClickListener(OnClickListener onClickListener){
            mLink.setOnClickListener(onClickListener);
        }
    }
}
