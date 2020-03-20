package com.arshiya.newsapp.articlelist.domain;

import androidx.annotation.NonNull;

import com.arshiya.newsapp.utils.DateUtil;

/**
 * POJO class holding Article data used to display and store article in db
 * Created by Arshiya on 2020-03-19.
 */
public class Article {

    private String mSourceId;

    private String mSourceName;

    private String mAuthor;

    private String mTitle;

    private String mDescription;

    private String mUrl;

    private String mUrlToImage;

    private String mContent;

    private long mPublishedTimeInMillis;

    private String mDisplayTime;

    private boolean isSaved;

    public Article() {}

    public Article(String sourceId, String sourceName, String author, String description, String title, String url, String urlToImage,
                   String content, long publishedTimeInMillis, boolean isSaved) {
        this.mSourceId = sourceId;
        this.mSourceName = sourceName;
        this.mAuthor = author;
        this.mDescription = description;
        this.mTitle = title;
        this.mUrl = url;
        this.mUrlToImage = urlToImage;
        this.mContent = content;
        this.mPublishedTimeInMillis = publishedTimeInMillis;
        this.mDisplayTime = DateUtil.getDisplayDate(publishedTimeInMillis);
        this.isSaved = isSaved;
    }

    public String getSourceId() {
        return mSourceId;
    }

    public void setSourceId(String sourceId) {
        mSourceId = sourceId;
    }

    public String getSourceName() {
        return mSourceName;
    }

    public void setSourceName(String sourceName) {
        mSourceName = sourceName;
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

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public long getPublishedTimeInMillis() {
        return mPublishedTimeInMillis;
    }

    public void setPublishedTimeInMillis(long publishedTimeInMillis) {
        mPublishedTimeInMillis = publishedTimeInMillis;
    }

    public String getDisplayTime() {
        return mDisplayTime;
    }

    public void setDisplayTime(String displayTime) {
        mDisplayTime = displayTime;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    /**
     * Creates object from {@link ArticleModel} object
     * @param articleModel - {@link ArticleModel}
     * @return - {@link Article}
     */
    public Article fromArticleModel(@NonNull ArticleModel articleModel) {
        this.mSourceId = articleModel.getSourceData().getId();
        this.mSourceName = articleModel.getSourceData().getName();
        this.mAuthor = articleModel.getAuthor();
        this.mTitle = articleModel.getTitle();
        this.mDescription = articleModel.getDescription();
        this.mUrl = articleModel.getUrl();
        this.mUrlToImage = articleModel.getUrlToImage();
        this.mContent = articleModel.getContent();
        this.mPublishedTimeInMillis = DateUtil.toMillis(articleModel.getPublishedAt());
        this.mDisplayTime = DateUtil.getDisplayDate(this.mPublishedTimeInMillis);
        this.isSaved = false;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nmSourceId: " + mSourceId + ",\nmSourceName: " + mSourceName + ",\nauthor: " + mAuthor + ",\ntitle: " + mTitle
                + ",\ndescription: " + mDescription + ",\nurl: " + mUrl + ",\nurlToImage: " + mUrlToImage
                + ",\npublishedAt: " + mPublishedTimeInMillis + ",\ncontent: " + mContent + "\nsaved: " + isSaved + "\n}";
    }
}
