package com.sonu.core.util

class Logger(
    private val tag: String,
    private val isDebug: Boolean = true
) {
    fun log(msg: String) {
        if (!isDebug) {
            //production logging  - Crashlytics or w/e you are using
        } else {
            printD(
                tag, msg
            )
        }
    }

    companion object Factory {

        fun buildDebug(tag: String): Logger {
            return Logger(
                tag = tag,
                isDebug = true
            )
        }

        fun buildRelease(tag: String): Logger {
            return Logger(
                tag = tag,
                isDebug = false
            )
        }
    }
}

private fun printD(tag: String, message: String) {
    println("$tag: $message")
}