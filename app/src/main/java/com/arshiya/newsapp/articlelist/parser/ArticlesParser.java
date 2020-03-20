package com.arshiya.newsapp.articlelist.parser;

import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.articlelist.domain.ArticleModel;
import com.arshiya.newsapp.utils.DateUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.arshiya.newsapp.articlelist.domain.ArticleConstants.KEY_AUTHOR;
import static com.arshiya.newsapp.articlelist.domain.ArticleConstants.KEY_CONTENT;
import static com.arshiya.newsapp.articlelist.domain.ArticleConstants.KEY_DESCRIPTION;
import static com.arshiya.newsapp.articlelist.domain.ArticleConstants.KEY_PUBLISHED_AT;
import static com.arshiya.newsapp.articlelist.domain.ArticleConstants.KEY_TITLE;
import static com.arshiya.newsapp.articlelist.domain.ArticleConstants.KEY_URL;
import static com.arshiya.newsapp.articlelist.domain.ArticleConstants.KEY_URL_TO_IMAGE;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.SOURCE_ID;
import static com.arshiya.newsapp.data.local.ArticlesDataContract.Columns.SOURCE_NAME;

/**
 * POJO class to generate JAVA class from JSONObject
 * Created by Arshiya on 2020-03-18.
 */
public class ArticlesParser {

    private static final String TAG = ArticlesParser.class.getSimpleName();

    /**
     * fetch and parse articles from JSONObject to arrayList of Articles
     * @param jsonObject - Response body
     * @return - ArrayList of {@link Article}
     * @throws JSONException - exception
     */
    public List<Article> parse(JSONObject jsonObject) throws JSONException {
        List<Article> articleList = new ArrayList<>();
        if (jsonObject.get("status").equals("ok")) {
            JSONArray articles = jsonObject.getJSONArray("articles");

            if (articles.length() > 0) {
                Gson gson = new Gson();
                ArticleModel[] articleModelList = gson.fromJson(articles.toString(), ArticleModel[].class);

                for (ArticleModel articleModel : articleModelList) {
                    articleList.add(new Article().fromArticleModel(articleModel));
                }
            }
        }

        return articleList;
    }

    /**
     * To parse article object received in {@link com.google.firebase.messaging.FirebaseMessagingService} onMessageReceived
     * @param dataMap - key, value map of Article data
     * @return - {@link Article}
     */
    public Article parse(HashMap<String, String> dataMap) {
        if (dataMap != null && dataMap.keySet().size() > 0) {
            String publishedAt = dataMap.get(KEY_PUBLISHED_AT);
            long timeInMillis = DateUtil.toMillis(publishedAt);

            return new Article(dataMap.get(SOURCE_ID),
                    dataMap.get(SOURCE_NAME),
                    dataMap.get(KEY_AUTHOR),
                    dataMap.get(KEY_DESCRIPTION),
                    dataMap.get(KEY_TITLE),
                    dataMap.get(KEY_URL),
                    dataMap.get(KEY_URL_TO_IMAGE),
                    dataMap.get(KEY_CONTENT),
                    timeInMillis,
                    false);


        }

        return null;
    }
}
