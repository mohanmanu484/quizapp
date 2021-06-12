package com.mohan.logoquiz.data

import android.util.Log
import com.google.gson.Gson
import com.mohan.logoquiz.QuizApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class Repository {


    suspend fun fetchLogos(): List<LogoUiModel> {
        return withContext(Dispatchers.IO) {
            parseFromAsset()
        }
    }

    private fun parseFromAsset() = Gson().fromJson(loadJSONFromAsset(), Response::class.java).data

    private fun loadJSONFromAsset(): String? {
        return try {
            val `is`: InputStream = with(QuizApp.getContext()) {
                resources.openRawResource(
                    resources.getIdentifier(
                        RESOURCE,
                        FOLDER, packageName
                    )
                )
            }
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            null
        }
    }

    companion object {

        private const val RESOURCE = "data"

        private const val FOLDER = "raw"

        private val instance by lazy { Repository() }

        fun getRepoInstance(): Repository {
            return instance
        }
    }
}