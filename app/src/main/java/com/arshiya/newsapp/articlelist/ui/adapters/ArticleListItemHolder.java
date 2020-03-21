package com.arshiya.newsapp.articlelist.ui.adapters;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.arshiya.newsapp.R;
import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.utils.Logger;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Arshiya on 2020-03-18.
 */
public class ArticleListItemHolder extends RecyclerView.ViewHolder {

    private static final String TAG = ArticleListItemHolder.class.getSimpleName();

    private Article mArticle;
    private int mPosition;
    private EventCallback mEventCallback;

    @BindView(R.id.iv_article_image)
    ImageView mArticleImage;

    @BindView(R.id.tv_headline)
    TextView mHeadline;

    @BindView(R.id.tv_description)
    TextView mDescription;

    @BindView(R.id.tv_source)
    TextView mSource;

    @BindView(R.id.tv_published_at)
    TextView mPublishedAt;

    @BindView(R.id.bt_save_article)
    Button mBtnSaveArticle;

    public ArticleListItemHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Article data, int postion, EventCallback callback) {
        mArticle = data;
        mPosition = postion;
        mEventCallback = callback;

        Log.d(TAG, "bind: " + mArticle.toString());

        if (!TextUtils.isEmpty(mArticle.getUrlToImage())) {
            Glide.with(this.itemView)
                    .load(mArticle.getUrlToImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            mArticleImage.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            mArticleImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(mArticleImage);
        }

        mHeadline.setText(mArticle.getTitle());
        mDescription.setText(mArticle.getDescription());
        mSource.setText(mArticle.getSourceName());
        mPublishedAt.setText(mArticle.getDisplayTime());

        mBtnSaveArticle.setText(mArticle.isSaved()? "Remove": "Save");
    }

    @OnClick({R.id.bt_save_article, R.id.tv_headline})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_save_article:
                if (mEventCallback != null) {
                    mEventCallback.onSaveArticleClick(mArticle, mPosition);
                }
                break;

            case R.id.tv_headline:
                if (mEventCallback != null) {
                    mEventCallback.onHeadlineClick(mArticle);
                }
                break;

                default:
                    Logger.e(TAG, "Unknown onClick() event.");
        }
    }

    /**
     * interface to notify user actions
     *
     */
    public interface EventCallback {

        /**
         * callback to notify save article click
         * @param article - {@link Article}
         * @param position - position of the item
         */
        void onSaveArticleClick(Article article, int position);

        /**
         * callback to notify headline click event
         * @param article - {@link Article}
         */
        void onHeadlineClick(Article article);
    }
}
