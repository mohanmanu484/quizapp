package com.mohan.logoquiz

import android.app.Application
import android.content.Context

class QuizApp:Application() {


    override fun onCreate() {
        super.onCreate()
        appContext =this
    }

    companion object{

        private lateinit var appContext:Context

        fun getContext():Context{
            return appContext
        }
    }
}