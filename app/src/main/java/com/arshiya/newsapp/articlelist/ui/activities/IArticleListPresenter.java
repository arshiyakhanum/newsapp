package com.arshiya.newsapp.articlelist.ui.activities;

import com.arshiya.newsapp.articlelist.domain.Article;

import java.util.HashMap;
import java.util.List;

/**
 * interface for retrieving data for {@link ArticleListActivity} and notifying the View
 *
 * Created by Arshiya on 2020-03-18.
 */
public interface IArticleListPresenter {

    /**
     * Fetches articles from server
     * @param timeInMillis - last fetched article timestamp
     */
    void loadArticles(long timeInMillis);


    /**
     * Fetches articles from either location storage
     */
    void loadSavedArticles();

    /**
     * Filters the article list based on the given filters
     * @param filters - HashMap of filters to be applied
     */
    void filterArticles(HashMap<Integer, String> filters);

    /**
     * Saves article to the local database
     * @param article - {@link Article}
     * @param position - Position of the article in article list
     */
    void saveArticle(Article article, int position);

    /**
     * interface to notify and handle store and retrieve task completion
     */
    interface EventCallback {

        void onArticlesLoadComplete(List<Article> articleList);

        void onSaveArticleSuccess( int position);

    }
}
