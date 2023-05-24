package com.arsenyGo.marketplace.utils

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

object CityHelper {
    fun getCountries(context: Context): ArrayList<String> {
        var tmpList = ArrayList<String>()
        try {
            val inputStream: InputStream = context.assets.open("countriesToCities.json")
            val size: Int = inputStream.available()
            val bytesArray = ByteArray(size)
            inputStream.read(bytesArray)
            val jsonFile = String(bytesArray)
            val jsonObj = JSONObject(jsonFile)
            val countriesNames = jsonObj.names()
            if (countriesNames != null)
                for (i in 0 until countriesNames.length())
                    tmpList.add(countriesNames.getString(i))
        } catch (e: IOException) {}
        return tmpList
    }

    fun getCities(country: String, context: Context): ArrayList<String> {
        var tmpList = ArrayList<String>()
        try {
            val inputStream: InputStream = context.assets.open("countriesToCities.json")
            val size: Int = inputStream.available()
            val bytesArray = ByteArray(size)
            inputStream.read(bytesArray)
            val jsonFile = String(bytesArray)
            val jsonObj = JSONObject(jsonFile)
            val countriesNames = jsonObj.getJSONArray(country)
            for (i in 0 until countriesNames.length())
                tmpList.add(countriesNames.getString(i))
        } catch (e: IOException) {}
        return tmpList
    }

    fun filterData(list: ArrayList<String>, searchText: String?): ArrayList<String> {
        if (searchText == null) return list
        val tmpList = ArrayList<String>()
        for (s: String in list)
            if (s.lowercase().startsWith(searchText.lowercase()))
                tmpList.add(s)
        if (tmpList.isEmpty()) tmpList.add("Ничего не найдено")
        return tmpList
    }
}