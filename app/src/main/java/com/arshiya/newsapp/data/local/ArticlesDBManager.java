package com.arshiya.newsapp.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;

import com.arshiya.newsapp.NewsApplication;
import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.utils.Logger;

import java.util.ArrayList;

import static com.arshiya.newsapp.data.local.ArticlesDataContract.ArticleEntity;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.AUTHOR;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.CONTENT;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.DESCRIPTION;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.IS_SAVED;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.SOURCE_ID;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.SOURCE_NAME;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.TIMESTAMP;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.TITLE;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.URL;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.URL_TO_IMAGE;

/**
 * Created by Arshiya on 2020-03-20.
 */
public class ArticlesDBManager {

    private static final String TAG = ArticlesDBManager.class.getSimpleName();

    private ContentResolver mContentResolver;

    public ArticlesDBManager(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public Uri saveArticle(Article article) {
        if (getArticleByUrl(article.getUrl()) != null) {
            Logger.d(TAG, "Article exists in the DB.\n" + article.toString());
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(SOURCE_ID, article.getSourceId());
        contentValues.put(SOURCE_NAME, article.getSourceName());
        contentValues.put(AUTHOR, article.getAuthor());
        contentValues.put(TITLE, article.getTitle());
        contentValues.put(DESCRIPTION, article.getDescription());
        contentValues.put(URL, article.getUrl());
        contentValues.put(URL_TO_IMAGE, article.getUrlToImage());
        contentValues.put(TIMESTAMP, article.getPublishedTimeInMillis());
        contentValues.put(CONTENT, article.getContent());
        contentValues.put(IS_SAVED, 1);

        Uri uri = mContentResolver.insert(ArticleEntity.getContentUri(NewsApplication.getInstance().getApplicationContext()), contentValues);
        fetchArticles();
        return uri;
    }

    public ArrayList<Article> fetchArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        Cursor cursor = mContentResolver.query(ArticleEntity.getContentUri(NewsApplication.getInstance().getApplicationContext()),
                null, null, null,TIMESTAMP + " DESC");
        Logger.d(TAG, DatabaseUtils.dumpCursorToString(cursor));
        assert cursor != null;
        while (cursor.moveToNext()) {
            articles.add(toArticle(cursor));
        }
        return articles;
    }

    public Article getArticleByUrl(String url) {
        Article article = null;
        String selection = URL + " = ?";
        String[] selectionArgs = new String[]{url};
        Cursor cursor = mContentResolver.query(ArticleEntity.getContentUri(NewsApplication.getInstance().getApplicationContext()),
                null, selection, selectionArgs,TIMESTAMP + " DESC");
        Logger.d(TAG, DatabaseUtils.dumpCursorToString(cursor));
        assert cursor != null;
        if (cursor.moveToNext()) {
            article = toArticle(cursor);
        }

        return article;
    }

    private Article toArticle(Cursor cursor) {
        return new Article(
                cursor.getString(cursor.getColumnIndex(SOURCE_ID)),
                cursor.getString(cursor.getColumnIndex(SOURCE_NAME)),
                cursor.getString(cursor.getColumnIndex(AUTHOR)),
                cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(TITLE)),
                cursor.getString(cursor.getColumnIndex(URL)),
                cursor.getString(cursor.getColumnIndex(URL_TO_IMAGE)),
                cursor.getString(cursor.getColumnIndex(CONTENT)),
                Long.valueOf(cursor.getString(cursor.getColumnIndex(TIMESTAMP))),
                cursor.getInt(cursor.getColumnIndex(IS_SAVED)) == 1
        );
    }

}
