package com.arshiya.newsapp.data.local;

import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Arshiya on 2020-03-19.
 */
public class ArticlesDataContract {

    public static final String TAG = ArticlesDataContract.class.getSimpleName();

    public interface Columns extends BaseColumns {

        String SOURCE_ID = "source_id";
        String SOURCE_NAME = "source_name";
        String AUTHOR = "author";
        String TITLE = "title";
        String DESCRIPTION = "description";
        String URL = "url";
        String URL_TO_IMAGE = "url_to_image";
        String TIMESTAMP = "timestamp";
        String CONTENT = "content";
        String IS_SAVED = "is_saved";

    }

    public static class ArticleEntity implements Columns {

        /**
         * Returns the content uri for the article table
         * @param context application context
         * @return content uri of Article table
         */
        public static Uri getContentUri(Context context) {
            return Uri.parse("content://" + getAuthority(context) + "/articlestable");
        }

        /**
         * The MIME type of CONTENT_URI providing a directory of
         * messages.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.pm.articlestable";
        /**
         * The MIME type of a CONTENT_URI sub-directory of a single
         * message.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.pm.articlestable";

        public static final String[] PROJECTION = {
                _ID, SOURCE_ID, SOURCE_NAME, AUTHOR, TITLE, DESCRIPTION, TIMESTAMP, URL, URL_TO_IMAGE, CONTENT, IS_SAVED
        };
    }

    private static final String AUTHORITY_PART = ".pm.provider";

    public static final String QUERY_PARAMETER_LIMIT = "LIMIT";

    /**
     * This authority is used for writing to or querying from the
     * provider. Note: This is set at first run and cannot be changed without
     * breaking apps that access the provider.
     */
    private static String AUTHORITY = null;

    /**
     * @param context application context
     * @return Authority string pointing to the Provider
     */
    public static String getAuthority(Context context) {
        if (null == AUTHORITY) {
            String packageName = context.getPackageName();
            AUTHORITY = packageName + AUTHORITY_PART;
        }
        return AUTHORITY;
    }
}
