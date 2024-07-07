package com.alekseykostyunin.hw14_retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alekseykostyunin.hw14_retrofit.user.Results
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Initial)
    val state = _state.asStateFlow()

    private val _user = MutableStateFlow<Results?>(null)
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            getUser()
        }
    }

    suspend fun getUser() {
        _state.value = State.Loading
        delay(3000)
        _user.value = Retrofit.userInfoApi.getUserInfo().body()
        _state.value = State.Success
    }

}