package com.samarthhms.utils

import android.util.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import dagger.Module
import dagger.Provides
import org.json.JSONObject
import java.time.LocalDateTime
import kotlin.reflect.full.memberProperties


//@Module
class FirestoreUtils {
    companion object{
//        @Provides
//        fun getFirestoreInstance(): FirebaseFirestore {
//            return FirebaseFirestore.getInstance()
//        }

        public fun getGson(): Gson{
            return GsonBuilder().create()
        }

        private fun getGsonWithSerializer(): Gson{
            return GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
                .create()
        }

        fun toJson(customObject: Any): Map<String, Any> {
            return ObjectMapper().convertValue(customObject, object: TypeReference<HashMap<String, Any>>(){})
        }

        fun<T> toObjectFromSnapshot(snapshot: DocumentSnapshot, clazz: Class<T>): T {
            val json = getGson().toJson(snapshot.data)
            Log.i("FROM SNAPSHOT JSON","JSON : $json")
            return getGsonWithSerializer().fromJson(json, clazz)
        }
    }
}