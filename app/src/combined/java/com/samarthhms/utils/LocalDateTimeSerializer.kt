package com.samarthhms.utils

import android.R.attr.src
import android.util.Log
import com.google.firebase.Timestamp
import com.google.gson.*
import org.json.JSONObject
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


class LocalDateTimeSerializer : JsonSerializer<LocalDateTime?>, JsonDeserializer<LocalDateTime?> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        val gson = FirestoreUtils.getGson()
        val timestamp = gson.fromJson(json, Timestamp::class.java)
        Log.i("SER","JSON ELEMENT : $json, timestamp : $timestamp")
        return DateTimeUtils.getLocalDateTime(timestamp)!!
    }

    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val timestamp = DateTimeUtils.getTimestamp(src!!)
        val gson = FirestoreUtils.getGson()
        val json = gson.toJson(timestamp)
//        return gson.fromJson(json, JsonElement::class.java)
        return context!!.serialize(timestamp.toString())
    }
}