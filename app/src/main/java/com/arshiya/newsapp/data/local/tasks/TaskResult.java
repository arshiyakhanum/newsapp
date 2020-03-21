package com.arshiya.newsapp.data.local.tasks;

/**
 * Created by Arshiya on 2020-03-21.
 */
public class TaskResult {

    public enum RESULT {
        SUCCESS,
        FAIL
    }

    private RESULT mResult;

    private int mTaskId;

    private String mMessage;

    private Object mAssociatedObj;

    private TaskResult() {}

    public TaskResult(RESULT result, String message, Object associatedObj, int taskId) {
        mResult = result;
        mMessage = message;
        mAssociatedObj = associatedObj;
        mTaskId = taskId;
    }

    public RESULT getResult() {
        return mResult;
    }

    public int getTaskId() {
        return mTaskId;
    }

    public void setTaskId(int taskId) {
        mTaskId = taskId;
    }

    public void setResult(RESULT result) {
        mResult = result;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Object getAssociatedObj() {
        return mAssociatedObj;
    }

    public void setAssociatedObj(Object associatedObj) {
        mAssociatedObj = associatedObj;
    }
}
