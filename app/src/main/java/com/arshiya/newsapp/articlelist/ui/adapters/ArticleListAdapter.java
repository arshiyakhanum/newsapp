package com.arshiya.newsapp.articlelist.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arshiya.newsapp.R;
import com.arshiya.newsapp.articlelist.domain.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for binding {@link Article} data set to view
 * Created by Arshiya on 2020-03-18.
 */
public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListItemHolder> implements ArticleListItemHolder.EventCallback {

    private static final String TAG = ArticleListAdapter.class.getSimpleName();
    private List<Article> mArticleDAOList;
    private ArticleEventCallback mArticleEventCallback;

    public ArticleListAdapter(ArticleEventCallback callback) {
        mArticleDAOList = new ArrayList<>();
        mArticleEventCallback = callback;
    }

    public ArticleListAdapter(@NonNull List<Article> articleList) {
        super();
        mArticleDAOList.addAll(articleList);
    }

    public void addArticles(List<Article> articles) {
        mArticleDAOList.addAll(articles);
        notifyDataSetChanged();
    }

    public void updateArticle(int position, boolean isSaved) {
        mArticleDAOList.get(position).setSaved(isSaved);
        notifyDataSetChanged();
    }

    public void refreshList(List<Article> articles) {
        mArticleDAOList = new ArrayList<>();
        addArticles(articles);
    }

    @NonNull
    @Override
    public ArticleListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ArticleListItemHolder(inflater.inflate(R.layout.item_artcile_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleListItemHolder holder, int position) {
        holder.bind(mArticleDAOList.get(position), position, this);
    }

    @Override
    public int getItemCount() {
        return mArticleDAOList.size();
    }

    @Override
    public long getItemId(int position) {
        return mArticleDAOList.get(position).hashCode();
    }

    @Override
    public void onSaveArticleClick(Article article, int position) {
        if (mArticleEventCallback != null) {
            mArticleEventCallback.onSaveArticleClick(article, position);
        }
    }

    @Override
    public void onHeadlineClick(Article article) {
        if (mArticleEventCallback != null) {
            mArticleEventCallback.onHeadlineClick(article);
        }
    }

    /**
     * interface to handle user actions on the view
     */
    public interface ArticleEventCallback {

        /**
         * Notifies when user clicks save article
         * @param article - {@link Article}
         * @param position - position of the object in the list
         */
        void onSaveArticleClick(Article article, int position);

        /**
         * Notifies when user clicks on the headline of the article
         * @param article - {@link Article}
         */
        void onHeadlineClick(Article article);

    }
}
