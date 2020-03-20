package com.arshiya.newsapp.articlelist.domain;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static com.arshiya.newsapp.articlelist.domain.ArticleConstants.*;

/**
 * POJO class for deserializing json object
 * Created by Arshiya on 2020-03-18.
 */
public class SourceModel {

    @SerializedName(KEY_SOURCE_ID)
    private String mId;

    @SerializedName(KEY_SOURCE_NAME)
    private String mName;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "source : {\nid: " + mId + ",\nname: " + mName + "\n}";
    }
}
