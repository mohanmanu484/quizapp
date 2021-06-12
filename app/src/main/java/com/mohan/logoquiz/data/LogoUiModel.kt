package com.mohan.logoquiz.data

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import java.util.*

data class LogoUiModel(var name: String, var imgUrl: String, val qNumber: Int) : BaseObservable() {

    private val scrambledList = mutableMapOf<Char, Boolean>()

    @get:Bindable
    var ansString: ObservableField<String> = ObservableField<String>("")

    private var score = 0

    private var answered = false

    private var scrambledWord = ""

    fun getScrambledChars(): List<Char> {
        Log.d("model","scrambled word is "+scrambledWord)
        scrambledWord = name.scrambleWord()
        return scrambledWord.toList()
    }

    fun updateAns(char: String) {
        if (!answered) {
            ansString.set(ansString.get() + char)
        }
        answered = name == ansString.get()
    }

    fun isAnsered(): Boolean {
        return answered
    }

    private fun String.scrambleWord(): String {
        val a = toCharArray()
        val random = Random()
        for (i in a.indices) {
            val j: Int = random.nextInt(a.size)
            val temp = a[i]
            a[i] = a[j]
            a[j] = temp
        }
        return String(a)
    }

}

