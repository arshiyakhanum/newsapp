package com.arshiya.newsapp.data.local.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.arshiya.newsapp.R;
import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.data.local.ArticlesDBManager;

/**
 * task to unsave Article
 * Created by Arshiya on 2020-03-21.
 */
public class DeleteTask extends AsyncTask<Object, Void, TaskResult> {

    private static final String TAG =  DeleteTask.class.getSimpleName();

    private Context mContext;

    private ITaskCompleteListener mITaskCompleteListener;

    private DeleteTask() {}

    public DeleteTask(Context context, ITaskCompleteListener taskCompletListener) {
        mContext = context;
        mITaskCompleteListener = taskCompletListener;
    }

    @Override
    protected TaskResult doInBackground(Object... objects) {
        TaskResult taskResult = null;

        if (objects[0] != null) {
            ArticlesDBManager dbManager = new ArticlesDBManager(mContext);
            int result = dbManager.deleteArticle((Article)objects[0]);

            if (result != -1) {
                taskResult = new TaskResult(TaskResult.RESULT.SUCCESS, mContext.getString(R.string.insert_success),
                        objects[0], (Integer) objects[1]);
            }
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

        if (mITaskCompleteListener != null) {
            mITaskCompleteListener.onTaskComplete(taskResult, TaskType.DELETE);
        }
    }
}
