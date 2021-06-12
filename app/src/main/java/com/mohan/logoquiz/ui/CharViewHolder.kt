package com.mohan.logoquiz.ui

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.mohan.logoquiz.R

class CharViewHolder(itemView: View,callback:(String)->Unit) : RecyclerView.ViewHolder(itemView),
    GenericAdapter.Binder<Char> {

    var textView: AppCompatTextView = itemView.findViewById(R.id.char_item)

    init {
        textView.setOnClickListener {textView->
            if(textView is TextView) {
                callback.invoke(textView.text.toString())
            }
        }
    }
    override fun bind(char: Char) {
        textView.text = char.toString()


    }
}