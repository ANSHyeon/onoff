package com.anshyeon.onoff.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anshyeon.onoff.data.source.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _hasUserInfo = MutableSharedFlow<Boolean>()
    val hasUserInfo = _hasUserInfo.asSharedFlow()

    private val _isSaveUserInfo = MutableSharedFlow<Boolean>()
    val isSaveUserInfo = _isSaveUserInfo.asSharedFlow()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserInfo() {
        viewModelScope.launch {
            _isLoading.value = true
            _hasUserInfo.emit(!repository.getUser().body().isNullOrEmpty())
        }
    }

    fun saveUserInfo() {
        viewModelScope.launch {
            repository.saveIdToken()
            _isSaveUserInfo.emit(true)
        }
    }

    companion object {

        fun provideFactory(repository: AuthRepository) = viewModelFactory {
            initializer {
                SignInViewModel(repository)
            }
        }
    }
}