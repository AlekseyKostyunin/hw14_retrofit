package com.alekseykostyunin.hw14_retrofit

sealed class State {
    data object Initial : State()
    data object Loading : State()
    data class Error(val textError: String = "Ошибка загруки") : State()
    data object Success : State()
}