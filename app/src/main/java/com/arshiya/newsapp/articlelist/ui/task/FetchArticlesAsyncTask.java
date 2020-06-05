package com.arshiya.newsapp.articlelist.ui.task;

import android.net.Uri;
import android.os.AsyncTask;

import com.arshiya.newsapp.common.restclient.Request;
import com.arshiya.newsapp.common.restclient.RequestBuilder;
import com.arshiya.newsapp.common.restclient.Response;
import com.arshiya.newsapp.common.restclient.RestClient;
import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.articlelist.parser.ArticlesParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Async tas to fetch articles from server
 * Created by Arshiya on 2020-03-20.
 */
public class FetchArticlesAsyncTask extends AsyncTask<Void, Void, List<Article>> {

    private static final String TAG = FetchArticlesAsyncTask.class.getSimpleName();

    private final Uri mUri = Uri.parse("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json");

    private FetchTaskCallback mFetchTaskCallback;

    /**
     * Callabck to notify fetch data compelte
     */
    public interface FetchTaskCallback {
        /**
         * Notify when fetch data is complete
         * @param articles - List of {@link Article}
         */
        void onFetchComplete(List<Article> articles);
    }

    private FetchArticlesAsyncTask() {}

    public FetchArticlesAsyncTask(FetchTaskCallback callback) {
        this.mFetchTaskCallback = callback;
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        List<Article> list = null;
        try {
            Request request = new RequestBuilder(mUri, RequestBuilder.RequestType.GET).build();
            Response response = new RestClient(request).execute();
            if (response != null &&  response.getResponseBody() != null) {
                list = new ArticlesParser().parse(new JSONObject(response.getResponseBody()));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        super.onPostExecute(articles);
        if (mFetchTaskCallback != null) {
            mFetchTaskCallback.onFetchComplete(articles);
        }
    }
}
