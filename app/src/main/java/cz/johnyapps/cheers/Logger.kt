package cz.johnyapps.cheers

import android.util.Log

object Logger {
    private const val TAG = "Logger"

    fun v(tag: String, message: String?) {
        Log.v(addLoggerTag(tag), message ?: "null")
    }

    fun d(tag: String, message: String?, tr: Throwable?) {
        if (tr == null) {
            Log.d(addLoggerTag(tag), message ?: "null")
        } else {
            Log.d(addLoggerTag(tag), message ?: "null", tr)
        }
    }

    fun d(tag: String, message: String?) {
        d(tag, message, null)
    }

    fun i(tag: String, message: String?) {
        Log.i(addLoggerTag(tag), message ?: "null")
    }

    fun w(tag: String, message: String?, tr: Throwable?) {
        if (tr == null) {
            Log.w(addLoggerTag(tag), message ?: "null")
        } else {
            Log.w(addLoggerTag(tag), message ?: "null", tr)
        }
    }

    fun w(tag: String, message: String?) {
        w(tag, message, null)
    }

    fun e(tag: String, message: String?, tr: Throwable?) {
        if (tr == null) {
            Log.e(addLoggerTag(tag), message ?: "null")
        } else {
            Log.e(addLoggerTag(tag), message ?: "null", tr)
        }
    }

    fun e(tag: String, message: String?) {
        e(tag, message, null)
    }

    private fun addLoggerTag(tag: String): String {
        return "$TAG: $tag"
    }
}