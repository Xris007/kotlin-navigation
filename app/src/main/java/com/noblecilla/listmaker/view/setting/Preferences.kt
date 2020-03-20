package com.noblecilla.listmaker.view.setting

import android.content.Context
import android.content.SharedPreferences
import com.noblecilla.listmaker.BuildConfig

object Preferences {

    private const val PREFERENCES = BuildConfig.APPLICATION_ID
    private const val MODE = "$PREFERENCES.mode"

    private fun preferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    private fun editor(context: Context): SharedPreferences.Editor {
        return preferences(context).edit()
    }

    fun switchToMode(context: Context, mode: Mode) = editor(context).apply {
        putInt(MODE, mode.ordinal)
        apply()
    }

    fun nightMode(context: Context) = preferences(context).getInt(MODE, 0)
}
