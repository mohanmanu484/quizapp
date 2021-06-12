package com.mohan.logoquiz.presentation

import android.util.Log
import androidx.lifecycle.*
import com.mohan.logoquiz.data.LogoUiModel
import com.mohan.logoquiz.data.Repository
import kotlinx.coroutines.launch

class QuizViewModel(val repository: Repository) : ViewModel(){


    private val logoList= MutableLiveData<List<LogoUiModel>>()

    private val currentItem = MediatorLiveData<LogoUiModel>()


    init {
        currentItem.addSource(logoList){
             logoList.value?.firstOrNull()?.let {
                 currentItem.value = it
            }
        }
    }

    fun getCurrentLogo(): LiveData<LogoUiModel> {
        return currentItem
    }

    private fun fetchNext(){
        val curr = logoList.value?.indexOf(currentItem.value)?:0
        val size:Int = logoList.value?.size?:0
        if(curr<=size-2){
            currentItem.value = logoList.value?.get(curr+1)
        }
    }

    fun markSelected(char: String){
        currentItem.value?.run {
            updateAns(char)
            if(isAnsered()){
                fetchNext()
            }
        }
    }


    fun fetchLogos(){
        viewModelScope.launch {
            val logoModelList:List<LogoUiModel> = repository.fetchLogos()
            logoList.value = logoModelList
        }
    }

}