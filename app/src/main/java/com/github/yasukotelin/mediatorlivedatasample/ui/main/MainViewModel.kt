package com.github.yasukotelin.mediatorlivedatasample.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val message: MutableLiveData<String> = MutableLiveData()
    val count: MediatorLiveData<Int> = MediatorLiveData()
    val completeMessage: MediatorLiveData<String> = MediatorLiveData()

    private val isCompleted: Boolean
        get() = count.value ?: 0 >= REQUIREMENT_WORDS

    init {
        count.value = 0

        count.addSource(message) {
            val cnt = message.value?.length ?: 0
            count.postValue(cnt)
        }
        completeMessage.addSource(count) {
            val msg = if (isCompleted) "Completed!" else "$REQUIREMENT_WORDS words required"
            completeMessage.postValue(msg)
        }
    }

    fun postMessage(message: String) {
        this.message.postValue(message)
    }

    companion object {
        private val REQUIREMENT_WORDS = 6
    }
}
