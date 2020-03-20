package com.arshiya.newsapp.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

import static com.arshiya.newsapp.data.local.ArticlesDataContract.ArticleEntity;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.getAuthority;

/**
 * Created by Arshiya on 2020-03-18.
 */
public class ArticlesContentProvider extends ContentProvider {

    private static final String TAG = ArticlesContentProvider.class.getSimpleName();

    private UriMatcher mUriMatcher;
    private ArticlesDatabaseHelper mArticlesOpenHelper;


    /**
     * Denotes Uri matched single Article
     */
    private static final int ARTICLES = 1;

    private static HashMap<String, String> sArticlesProjectionMap;

    static {
        sArticlesProjectionMap = new HashMap<>();
        sArticlesProjectionMap.put(ArticleEntity._ID, ArticleEntity._ID);
        sArticlesProjectionMap.put(ArticleEntity.SOURCE_ID, ArticleEntity.SOURCE_ID);
        sArticlesProjectionMap.put(ArticleEntity.SOURCE_NAME, ArticleEntity.SOURCE_NAME);
        sArticlesProjectionMap.put(ArticleEntity.AUTHOR, ArticleEntity.AUTHOR);
        sArticlesProjectionMap.put(ArticleEntity.TITLE, ArticleEntity.TITLE);
        sArticlesProjectionMap.put(ArticleEntity.DESCRIPTION, ArticleEntity.DESCRIPTION);
        sArticlesProjectionMap.put(ArticleEntity.URL, ArticleEntity.URL);
        sArticlesProjectionMap.put(ArticleEntity.URL_TO_IMAGE, ArticleEntity.URL_TO_IMAGE);
        sArticlesProjectionMap.put(ArticleEntity.TIMESTAMP, ArticleEntity.TIMESTAMP);
        sArticlesProjectionMap.put(ArticleEntity.CONTENT, ArticleEntity.CONTENT);
        sArticlesProjectionMap.put(ArticleEntity.IS_SAVED, ArticleEntity.IS_SAVED);
    }

    private void initialize() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(getAuthority(getContext()), "articlestable", ARTICLES);
    }

    @Override
    public boolean onCreate() {
        mArticlesOpenHelper = new ArticlesDatabaseHelper(getContext());
        initialize();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mArticlesOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String limit = uri.getQueryParameter(ArticlesDataContract.QUERY_PARAMETER_LIMIT);
        if (mUriMatcher.match(uri) == ARTICLES){
                qb.setProjectionMap(sArticlesProjectionMap);
                qb.setTables(ArticlesDatabaseHelper.TABLE_NAME_ARTICLES);
        }
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, limit);
        return cursor;    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (mUriMatcher.match(uri) == ARTICLES) {
            return ArticleEntity.CONTENT_TYPE;
        } else {
            throw new IllegalArgumentException("No matching Uri found.");
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (values == null) {
            return null;
        }
        long rowId = 0;
        Uri newUri = null;
        SQLiteDatabase db = mArticlesOpenHelper.getReadableDatabase();
        switch (mUriMatcher.match(uri)) {
            case ARTICLES:
                rowId = db.insert(ArticlesDatabaseHelper.TABLE_NAME_ARTICLES, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(ArticleEntity.getContentUri(getContext()), rowId);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return newUri;
    }


    @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mArticlesOpenHelper.getReadableDatabase();
        int count = 0;
        if  (mUriMatcher.match(uri) == ARTICLES) {
            count = db.delete(ArticlesDatabaseHelper.TABLE_NAME_ARTICLES, selection, selectionArgs);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mArticlesOpenHelper.getReadableDatabase();
        int count = 0;
        if (mUriMatcher.match(uri) == ARTICLES) {
            count = db.update(ArticlesDatabaseHelper.TABLE_NAME_ARTICLES, values, selection, selectionArgs);
        }

        return count;
    }
}
