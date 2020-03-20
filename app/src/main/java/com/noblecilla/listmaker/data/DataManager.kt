package com.noblecilla.listmaker.data

import android.content.Context
import android.content.SharedPreferences
import com.noblecilla.listmaker.BuildConfig
import com.noblecilla.listmaker.model.ListEntity

@Suppress("UNCHECKED_CAST")
object DataManager {

    private const val PREFERENCES = "${BuildConfig.APPLICATION_ID}.list"

    private fun preferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    private fun editor(context: Context): SharedPreferences.Editor {
        return preferences(context).edit()
    }

    fun all(context: Context): List<ListEntity> {
        return preferences(context).all.map {
            ListEntity(it.key, ArrayList(it.value as HashSet<String>))
        }
    }

    fun insert(context: Context, listEntity: ListEntity) = editor(context).apply {
        putStringSet(listEntity.name, listEntity.items.toHashSet())
        apply()
    }

    fun delete(context: Context, listEntity: ListEntity) = editor(context).apply {
        remove(listEntity.name)
        apply()
    }

    fun items(context: Context, listEntity: ListEntity): ListEntity {
        val items =
            ArrayList(preferences(context).getStringSet(listEntity.name, null) as HashSet<String>)
        return ListEntity(listEntity.name, items)
    }
}
