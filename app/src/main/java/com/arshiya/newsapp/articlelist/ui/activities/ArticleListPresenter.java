package com.arshiya.newsapp.articlelist.ui.activities;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.articlelist.ui.task.FetchArticlesAsyncTask;
import com.arshiya.newsapp.data.local.ArticlesDBManager;
import com.arshiya.newsapp.utils.ConnectivityUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Presenter class for {@link ArticleListActivity}
 * Created by Arshiya on 2020-03-18.
 */
public class ArticleListPresenter implements IArticleListPresenter, FetchArticlesAsyncTask.FetchTaskCallback {

    private static final String TAG = ArticleListPresenter.class.getSimpleName();

    /**
     * Callback to notify data retrieval completion
     */
    private EventCallback mEventCallback;
    private Context mContext;

    public ArticleListPresenter(@NonNull Context context, EventCallback eventCallback) {
        mContext = context;
        mEventCallback = eventCallback;
    }

    @Override
    public void loadArticles(long timeInMillis) {
        if (ConnectivityUtils.isNetworkAvailable(mContext)) {
            new FetchArticlesAsyncTask(this).execute(null, null);
        } else {
            loadSavedArticles();
        }
    }

    @Override
    public void loadSavedArticles() {
        ArticlesDBManager dbManager = new ArticlesDBManager(mContext);
        List<Article> articles = dbManager.fetchArticles();

        if (articles != null && articles.size() > 0) {
            mEventCallback.onArticlesLoadComplete(articles);
        }
    }

    @Override
    public void filterArticles(HashMap<Integer, String> filters) {

    }

    @Override
    public void saveArticle(Article article, int position) {
        ArticlesDBManager dbManager = new ArticlesDBManager(mContext);
        Uri uri = dbManager.saveArticle(article);
        if (uri != null && mEventCallback != null) {
            article.setSaved(true);
            mEventCallback.onSaveArticleSuccess(position);
        }
    }

    @Override
    public void onFetchComplete(List<Article> articles) {
        if (mEventCallback != null) {
            mEventCallback.onArticlesLoadComplete(articles);
        }
    }
}
