package com.example.app_ext

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

data class CustomTea(
    val name: String,
    val category: String,
    val steepTime: String,
    val temp: String,
    val portion: String,
    val instructions: String,
    val benefits: String,
    val imageUri: String? = null
)

object TeaStorage {

    private const val PREF_NAME = "custom_teas_pref"
    private const val KEY_CUSTOM_TEAS = "key_custom_teas"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveTea(context: Context, tea: CustomTea) {
        val teas = getSavedTeas(context).toMutableList()
        teas.add(tea)
        saveAllTeas(context, teas)
    }

    fun getSavedTeas(context: Context): List<CustomTea> {
        val jsonString = getPreferences(context).getString(KEY_CUSTOM_TEAS, null) ?: return emptyList()
        val list = mutableListOf<CustomTea>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    CustomTea(
                        name = obj.optString("name", "Novo Chá"),
                        category = obj.optString("category", "Chá de Ervas"),
                        steepTime = obj.optString("steepTime", "5 min"),
                        temp = obj.optString("temp", "90°C"),
                        portion = obj.optString("portion", "1 colher de chá"),
                        instructions = obj.optString("instructions", ""),
                        benefits = obj.optString("benefits", ""),
                        imageUri = if (obj.has("imageUri") && !obj.isNull("imageUri")) obj.getString("imageUri") else null
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    private fun saveAllTeas(context: Context, teas: List<CustomTea>) {
        val jsonArray = JSONArray()
        for (tea in teas) {
            val obj = JSONObject().apply {
                put("name", tea.name)
                put("category", tea.category)
                put("steepTime", tea.steepTime)
                put("temp", tea.temp)
                put("portion", tea.portion)
                put("instructions", tea.instructions)
                put("benefits", tea.benefits)
                put("imageUri", tea.imageUri ?: JSONObject.NULL)
            }
            jsonArray.put(obj)
        }
        getPreferences(context).edit().putString(KEY_CUSTOM_TEAS, jsonArray.toString()).apply()
    }
}