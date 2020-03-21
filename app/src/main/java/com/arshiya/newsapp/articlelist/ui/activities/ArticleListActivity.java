package com.arshiya.newsapp.articlelist.ui.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arshiya.newsapp.R;
import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.articlelist.ui.adapters.ArticleListAdapter;
import com.arshiya.newsapp.utils.ConnectivityUtils;
import com.arshiya.newsapp.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Home page for displaying list of articles
 */
public class ArticleListActivity extends AppCompatActivity implements IArticleListPresenter.EventCallback,
        ArticleListAdapter.ArticleEventCallback {

    private static final String TAG = ArticleListActivity.class.getSimpleName();

    @BindView(R.id.rv_article_list)
    RecyclerView mArticleListRV;

    private ArticleListPresenter mArticleListPresenter;
    private ArticleListAdapter mArticleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mArticleListPresenter = new ArticleListPresenter(this,this);
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ConnectivityUtils.isNetworkAvailable(this)) {
            showOfflineToast();
        }
        mArticleListPresenter.loadArticles(System.currentTimeMillis());
    }

    /**
     *
     * Initializes UI components
     */
    private void initUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mArticleListRV.setLayoutManager(linearLayoutManager);
        mArticleListRV.setHasFixedSize(true);
        mArticleListRV.setItemViewCacheSize(15);

        mArticleListAdapter = new ArticleListAdapter(this);
        mArticleListAdapter.setHasStableIds(true);

        mArticleListRV.setAdapter(mArticleListAdapter);
    }

    /**
     * Shows toast when the device has no internet connection
     */
    private void showOfflineToast() {
        Toast.makeText(this, getResources().getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
    }

    /**
     * Opens the article in browser
     * @param article - {@link Article}
     */
    private void openArticleInBrowser(Article article) {
        Util.openInBrowser(this, article.getUrl());
    }

    @Override
    public void onArticlesLoadComplete(List<Article> articleList) {
        mArticleListAdapter.addArticles(articleList);
    }

    @Override
    public void onSaveArticleComplete(int position) {
        mArticleListAdapter.updateArticle(position, true);
    }

    @Override
    public void onDeleteArticleComplete(int position) {
        mArticleListAdapter.updateArticle(position, false);
    }

    @Override
    public void onSaveArticleClick(Article article, int position) {
        mArticleListPresenter.saveArticle(article, position);
    }

    @Override
    public void onHeadlineClick(Article article) {
        openArticleInBrowser(article);
    }

}
