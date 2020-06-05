package com.arshiya.newsapp.articlelist.data.local.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.arshiya.newsapp.R;
import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.articlelist.data.local.ArticlesDBManager;

/**
 * Task to save Article
 * Created by Arshiya on 2020-03-21.
 */
public class InsertTask extends AsyncTask<Object, Void, TaskResult> {

    private static final String TAG =  InsertTask.class.getSimpleName();

    private Context mContext;

    private ITaskCompleteListener mITaskCompleteListener;

    private InsertTask() {}

    public InsertTask(Context context, ITaskCompleteListener taskCompletListener) {
        mContext = context;
        mITaskCompleteListener = taskCompletListener;
    }

    @Override
    protected TaskResult doInBackground(Object... objects) {
        TaskResult taskResult = null;

        if (objects[0] != null) {
            ArticlesDBManager dbManager = new ArticlesDBManager(mContext);
            Uri uri = dbManager.saveArticle((Article) objects[0]);

            if (uri != null) {
                taskResult = new TaskResult(TaskResult.RESULT.SUCCESS,
                        mContext.getString(R.string.insert_success), objects[0], (Integer) objects[1]);
            }
        }

        if (taskResult == null) {
            taskResult = new TaskResult(TaskResult.RESULT.FAIL,
                    mContext.getString(R.string.insert_fail), objects[0], (Integer) objects[1]);
        }
        return taskResult;
    }

    @Override
    protected void onPostExecute(TaskResult taskResult) {
        super.onPostExecute(taskResult);

        if (mITaskCompleteListener != null) {
            mITaskCompleteListener.onTaskComplete(taskResult, TaskType.INSERT);
        }
    }
}
