package com.mohan.logoquiz.data

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.mohan.logoquiz.BR
import java.util.*

data class LogoUiModel(var name: String, var imgUrl: String, val qNumber: Int) :BaseObservable() {



    @Bindable
    var ansString :String =""
        set(value){
            field = value
            notifyPropertyChanged(BR.ansString)
        }


    private val scrambledList = mutableMapOf<Char, Boolean>()

    private var score = 0

    private var answered = false

    private var scrambledWord = ""

    private var inputCount = 0

    fun getScrambledChars(): List<Char> {
        Log.d("model","scrambled word is "+scrambledWord)
        scrambledWord = name.scrambleWord()
        return scrambledWord.toList()
    }

    fun updateAns(char: String) {
        if (!answered && inputCount<name.length) {
            ansString = (ansString.orEmpty() + char)
            inputCount+=1
        }
        answered = name == ansString
    }

    fun reset(){
        inputCount = 0
        ansString = ""
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

