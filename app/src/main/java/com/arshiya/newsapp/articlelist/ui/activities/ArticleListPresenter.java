package com.arshiya.newsapp.articlelist.ui.activities;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.articlelist.ui.task.FetchArticlesAsyncTask;
import com.arshiya.newsapp.articlelist.data.local.ArticlesDBManager;
import com.arshiya.newsapp.articlelist.data.local.tasks.DeleteTask;
import com.arshiya.newsapp.articlelist.data.local.tasks.ITaskCompleteListener;
import com.arshiya.newsapp.articlelist.data.local.tasks.InsertTask;
import com.arshiya.newsapp.articlelist.data.local.tasks.TaskResult;
import com.arshiya.newsapp.articlelist.data.local.tasks.TaskType;
import com.arshiya.newsapp.login.data.local.UserSharedPreferences;
import com.arshiya.newsapp.login.ui.LoginActivity;
import com.arshiya.newsapp.utils.ConnectivityUtils;
import com.moe.pushlibrary.MoEHelper;

import java.util.HashMap;
import java.util.List;

/**
 * Presenter class for {@link ArticleListActivity}
 * Created by Arshiya on 2020-03-18.
 */
public class ArticleListPresenter implements IArticleListPresenter, FetchArticlesAsyncTask.FetchTaskCallback, ITaskCompleteListener {

    private static final String TAG = ArticleListPresenter.class.getSimpleName();

    /**
     * Callback to notify data retrieval completion
     */
    private EventCallback mEventCallback;
    private Context mContext;

    private HashMap<Article, Integer> mQueuedTasks;

    public ArticleListPresenter(@NonNull Context context, EventCallback eventCallback) {
        mContext = context;
        mEventCallback = eventCallback;
        mQueuedTasks = new HashMap<>();
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
        if (article.isSaved()) {
            new DeleteTask(mContext, this, TaskType.DELETE_ARTICLE).execute(article, position);
        } else {
            new InsertTask(mContext, this).execute(article, position);

        }
        mQueuedTasks.put(article, position);
    }

    @Override
    public void onFetchComplete(List<Article> articles) {
        if (mEventCallback != null) {
            mEventCallback.onArticlesLoadComplete(articles);
        }
    }

    @Override
    public void onTaskComplete(TaskResult taskResult, TaskType taskType) {
        if (taskResult.getResult() == TaskResult.RESULT.SUCCESS) {
            Article article = (Article) taskResult.getAssociatedObj();
            if (article != null && mQueuedTasks.containsKey(article)) {
                int position = mQueuedTasks.get(article);
                if (mEventCallback != null) {
                    if (taskType == TaskType.INSERT) {
                        mEventCallback.onSaveArticleComplete(position);
                    } else if (taskType == TaskType.DELETE_ARTICLE){
                        mEventCallback.onDeleteArticleComplete(position);
                    }
                }
            }
        }
    }

    @Override
    public void logOut(Context context) {
        clearArticles();
        clearUserData();

        MoEHelper.getInstance(context).logoutUser();

        Intent intent = new Intent(mContext, LoginActivity.class);
        context.startActivity(intent);
    }

    private void clearArticles() {
        new DeleteTask(mContext, this, TaskType.DELETE_ALL_ARTICLES).execute();
    }

    private void clearUserData() {
        UserSharedPreferences sharedPreferences = UserSharedPreferences.getInstance(mContext);
        sharedPreferences.saveLoginStatus(false);
        sharedPreferences.saveUniqueId(null);
        sharedPreferences.saveUserName(null);
    }


}
