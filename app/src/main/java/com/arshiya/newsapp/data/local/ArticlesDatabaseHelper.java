package com.arshiya.newsapp.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * A helper class to manage database creation and version management.
 * Created by Arshiya on 2020-03-19.
 */
public class ArticlesDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = ArticlesDatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "articles_repository.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_ARTICLES = "articles_table";

    private SQLiteDatabase mSQLiteDatabase;

    public ArticlesDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mSQLiteDatabase =getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createArticleTable(db);
    }

    private void createArticleTable(SQLiteDatabase db) {
        String CREATE_ARTICLE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME_ARTICLES + "("
                + ArticlesDataContract.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ArticlesDataContract.Columns.SOURCE_ID + " TEXT DEFAULT '',"
                + ArticlesDataContract.Columns.SOURCE_NAME + " TEXT DEFAULT '', "
                + ArticlesDataContract.Columns.AUTHOR + " TEXT DEFAULT '', "
                + ArticlesDataContract.Columns.TITLE + " TEXT DEFAULT '', "
                + ArticlesDataContract.Columns.DESCRIPTION + " TEXT DEFAULT '' , "
                + ArticlesDataContract.Columns.URL + " TEXT DEFAULT '', "
                + ArticlesDataContract.Columns.URL_TO_IMAGE + " TEXT DEFAULT '', "
                + ArticlesDataContract.Columns.TIMESTAMP + " TEXT DEFAULT '0', "
                + ArticlesDataContract.Columns.CONTENT + " TEXT DEFAULT '', "
                + ArticlesDataContract.Columns.IS_SAVED + " INTEGER DEFAULT 0"
                + ")";

        db.execSQL(CREATE_ARTICLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ARTICLES);
        onCreate(db);
    }
}

