package com.example.ibr_instantbookreviews.Utilities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity


class SharedPrefCommon (activity: AppCompatActivity) {

    var sharedPreferences : SharedPreferences
    var editor : SharedPreferences.Editor
    init {
        sharedPreferences = activity.getSharedPreferences("MainShared", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun putPrefString(key: String, value: String){
        editor.putString(key, value)
        editor.apply()
    }
    fun putPrefInt(key: String, value: Int){
        editor.putInt(key, value)
        editor.apply()
    }
    fun putPrefLong(key: String, value: Long){
        editor.putLong(key, value)
        editor.apply()
    }

    fun getPrefString(key: String, default: String): String?{
        return sharedPreferences.getString(key, default)
    }
    fun getPrefInt(key: String, default: Int): Int{
        return sharedPreferences.getInt(key, default)
    }
    fun getPrefLong(key: String, default: Long): Long{
        return sharedPreferences.getLong(key, default)
    }
}
