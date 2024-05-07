package com.eagletech.takenote.payment

import android.app.Application
import android.content.Context
import com.eagletech.takenote.sharepref.SharedPreferencesManager


class App : Application() {
    var cnt: Context? = null
    override fun onCreate() {
        super.onCreate()
        cnt = this
        QuizPref.init(this)

    }
}
