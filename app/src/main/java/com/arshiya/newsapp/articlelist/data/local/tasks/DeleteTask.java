package com.arshiya.newsapp.articlelist.data.local.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.arshiya.newsapp.R;
import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.articlelist.data.local.ArticlesDBManager;

/**
 * task to unsave Article
 * Created by Arshiya on 2020-03-21.
 */
public class DeleteTask extends AsyncTask<Object, Void, TaskResult> {

    private static final String TAG =  DeleteTask.class.getSimpleName();

    private Context mContext;

    private ITaskCompleteListener mITaskCompleteListener;

    private TaskType mTaskType;

    private DeleteTask() {}

    public DeleteTask(Context context, ITaskCompleteListener taskCompletListener, TaskType taskType) {
        mContext = context;
        mITaskCompleteListener = taskCompletListener;
        mTaskType = taskType;
    }

    @Override
    protected TaskResult doInBackground(Object... objects) {
        TaskResult taskResult = null;

        ArticlesDBManager dbManager = new ArticlesDBManager(mContext);
        int result = -1;
        if (mTaskType == TaskType.DELETE_ARTICLE) {
            if (objects[0] != null) {
                result = dbManager.deleteArticle((Article) objects[0]);
            }
        } else {
            result = dbManager.clearArticles();
        }


        if (result != -1) {
            taskResult = new TaskResult(TaskResult.RESULT.SUCCESS, mContext.getString(R.string.delete_success),
                    objects[0], (Integer) objects[1]);
        }

        if (taskResult == null) {
            taskResult = new TaskResult(TaskResult.RESULT.FAIL, mContext.getString(R.string.insert_fail),
                    objects[0], (Integer) objects[1]);
        }
        return taskResult;
    }

    @Override
    protected void onPostExecute(TaskResult taskResult) {
        super.onPostExecute(taskResult);
        if (mITaskCompleteListener != null && mTaskType == TaskType.DELETE_ARTICLE) {
            mITaskCompleteListener.onTaskComplete(taskResult, TaskType.DELETE_ARTICLE);
        }
    }
}
