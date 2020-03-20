package com.arshiya.newsapp.articlelist.domain;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static com.arshiya.newsapp.articlelist.domain.ArticleConstants.*;

/**
 * POJO class for deserializing json object
 * Created by Arshiya on 2020-03-18.
 */
public class ArticleModel {

    @SerializedName(KEY_SOURCE)
    private SourceModel mSourceModel;

    @SerializedName(KEY_AUTHOR)
    private String mAuthor;

    @SerializedName(KEY_TITLE)
    private String mTitle;

    @SerializedName(KEY_DESCRIPTION)
    private String mDescription;

    @SerializedName(KEY_URL)
    private String mUrl;

    @SerializedName(KEY_URL_TO_IMAGE)
    private String mUrlToImage;

    @SerializedName(KEY_PUBLISHED_AT)
    private String mPublishedAt;

    @SerializedName(KEY_CONTENT)
    private String mContent;

    public SourceModel getSourceData() {
        return mSourceModel;
    }

    public void setSourceData(SourceModel sourceModel) {
        mSourceModel = sourceModel;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\n" + mSourceModel + ",\nauthor: " + mAuthor + ",\ntitle: " + mTitle
                + ",\ndescription: " + mDescription + ",\nurl: " + mUrl + ",\nurlToImage: "
                + mUrlToImage + " ,\npublishedAt: " + mPublishedAt + ",\ncontent: " + mContent + "\n}";
    }
}
