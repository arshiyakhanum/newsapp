package com.arshiya.newsapp.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * Helper class to set logging level
 * Created by Arshiya
 */
public class Logger {

    private static final String TAG = Logger.class.getSimpleName();


    /**
     * Priority constant for the println method; use Logger.v.
     */
    public static final int VERBOSE = 0;

    /**
     * Priority constant for the println method; use Logger.v.
     */
    public static final int DEBUG = 1;

    /**
     * Priority constant for the println method; use Logger.v.
     */
    public static final int INFO = 2;

    /**
     * Priority constant for the println method; use Logger.v.
     */
    public static final int WARNING = 3;

    /**
     * Priority constant for the println method; use Logger.v.
     */
    public static final int ERROR = 4;

    /**
     * Priority constant for the println method; use Logger.v.
     */
    public static final int ASSERT = 5;

    /**
     * Default log level for logging MIN_LOG_LEVEL
     */
    private static int MIN_LOG_LEVEL = VERBOSE;

    /**
     * Default log status
     * <p>
     * Set to true when initialize() is called in debug build
     * <p>
     * To enable logs for release build call setLog()
     */
    private static boolean LOG_STATUS = false;

    private Logger() {
    }

    /**
     * Initialize Logger to print logs in debug build
     *
     * @param context - Application context
     */
    public static void initialize(Context context) {
        if (null == context) {
            return;
        }
        try {
            boolean debuggable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
            if (debuggable) {
                LOG_STATUS = true;
            }
        } catch (Exception e) {
            Logger.e(TAG, "initialize", e);
        }

    }

    /**
     * Equivalent of [Log.v()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     */
    public static void v(String tag, String message) {
        if (LOG_STATUS && VERBOSE >= MIN_LOG_LEVEL) {
            Log.v(tag, message);
        }
    }

    /**
     * Equivalent of [Log.d()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     */
    public static void d(String tag, String message) {
        if (LOG_STATUS && DEBUG >= MIN_LOG_LEVEL) {
            Log.d(tag, message);
        }
    }

    /**
     * Equivalent of [Log.i()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     */
    public static void i(String tag, String message) {
        if (LOG_STATUS && INFO >= MIN_LOG_LEVEL) {
            Log.i(tag, message);
        }
    }

    /**
     * Equivalent of [Log.w()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     */
    public static void w(String tag, String message) {
        if (LOG_STATUS && WARNING >= MIN_LOG_LEVEL) {
            Log.w(tag, message);
        }
    }

    /**
     * Equivalent of [Log.e()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     */
    public static void e(String tag, String message) {
        if (LOG_STATUS && ERROR >= MIN_LOG_LEVEL) {
            Log.e(tag, message);
        }
    }

    /**
     * Equivalent of [Log.wtf()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     */
    public static void wtf(String tag, String message) {
        if (LOG_STATUS && ASSERT >= MIN_LOG_LEVEL) {
            Log.wtf(tag, message);
        }
    }


    /**
     * Equivalent of [Log.v()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     * @param tr      - An exception to log
     */
    public static void v(String tag, String message, Throwable tr) {
        if (LOG_STATUS && VERBOSE >= MIN_LOG_LEVEL) {
            Log.v(tag, message, tr);
        }
    }

    /**
     * Equivalent of [Log.d()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     * @param tr      - An exception to log
     */
    public static void d(String tag, String message, Throwable tr) {
        if (LOG_STATUS && DEBUG >= MIN_LOG_LEVEL) {
            Log.d(tag, message, tr);
        }
    }

    /**
     * Equivalent of [Log.i()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     * @param tr      - An exception to log
     */
    public static void i(String tag, String message, Throwable tr) {
        if (LOG_STATUS && INFO >= MIN_LOG_LEVEL) {
            Log.i(tag, message, tr);
        }
    }

    /**
     * Equivalent of [Log.w()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     * @param tr      - An exception to log
     */
    public static void w(String tag, String message, Throwable tr) {
        if (LOG_STATUS && WARNING >= MIN_LOG_LEVEL) {
            Log.w(tag, message, tr);
        }
    }

    /**
     * Equivalent of [Log.e()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     * @param tr      - An exception to log
     */
    public static void e(String tag, String message, Throwable tr) {
        if (LOG_STATUS && ERROR >= MIN_LOG_LEVEL) {
            Log.e(tag, message, tr);
        }
    }

    /**
     * Equivalent of [Log.wtf()] of {@link Log}
     *
     * @param tag     - Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message - The message you would like logged.
     * @param tr      - An exception to log
     */
    public static void wtf(String tag, String message, Throwable tr) {
        if (LOG_STATUS && ASSERT >= MIN_LOG_LEVEL) {
            Log.wtf(tag, message, tr);
        }
    }
}