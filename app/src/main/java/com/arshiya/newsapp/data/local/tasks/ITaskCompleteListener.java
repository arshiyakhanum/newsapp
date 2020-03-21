package com.arshiya.newsapp.data.local.tasks;

/**
 * Interface definition for a callback to be invoked when task is completed
 *
 * Created by Arshiya on 2020-03-21.
 */
public interface ITaskCompleteListener {

    /**
     * Called when the Task is completed
     * @param taskResult - {@link TaskResult}
     * @param taskType - {@link TaskType}
     */
    void onTaskComplete(TaskResult taskResult, TaskType taskType);
}
