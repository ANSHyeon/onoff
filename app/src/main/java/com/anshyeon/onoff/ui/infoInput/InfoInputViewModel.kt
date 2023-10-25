package com.anshyeon.onoff.ui.infoInput

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anshyeon.onoff.data.source.repository.AuthRepository
import com.anshyeon.onoff.util.isValidNickname
import kotlinx.coroutines.launch

class InfoInputViewModel(private val repository: AuthRepository) : ViewModel() {

    private val nickName = MutableLiveData<String>()
    private val imageUri = MutableLiveData<Uri>()

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isValidInfo = MutableLiveData<Boolean>(false)
    val isValidInfo: LiveData<Boolean> = _isValidInfo

    private val _isSave = MutableLiveData<Boolean>(false)
    val isSave: LiveData<Boolean> = _isSave

    fun updateNickName(text: String) {
        nickName.value = text
        _isValidInfo.value = isValidNickname(text)
    }

    fun updateProfileImage(uri: Uri?) {
        uri?.let {
            imageUri.value = it
        }
    }

    fun saveUserInfo() {
        viewModelScope.launch {
            _isLoading.value = true
            nickName.value?.let {
                repository.createUser(it, imageUri.value)
                repository.saveIdToken()
            }
            _isSave.value = true
        }
    }

    companion object {

        fun provideFactory(repository: AuthRepository) = viewModelFactory {
            initializer {
                InfoInputViewModel(repository)
            }
        }
    }
}