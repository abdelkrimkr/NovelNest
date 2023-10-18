package com.example.novelnest.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.novelnest.NovelApplication
import com.example.novelnest.data.NovelRepository
import com.example.novelnest.network.Item
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface NovelUiState{
    data class Success(val items: List<Item>) : NovelUiState
    object Loading : NovelUiState
    object Error : NovelUiState
}

class NovelViewModel (private val novelRepository: NovelRepository) : ViewModel(){

    var novelUiState : NovelUiState by mutableStateOf(NovelUiState.Loading)
        private set

    var searchNameUiState : String by mutableStateOf("")
        private set

    init {
        getNovel("Jazz History")
    }

     fun getNovel(searchName: String, maxResult: Int = 20){
        viewModelScope.launch {
            novelUiState = try {
                val novel = novelRepository.getNovel(searchName, maxResult)
                NovelUiState.Success(novel)
            } catch (_ : IOException){
                NovelUiState.Error
            }
        }
    }

    fun setSearchName(bookName : String){
        searchNameUiState = bookName
    }


    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NovelApplication)
                val novelRepository = application.container.novelRepository
                NovelViewModel(novelRepository = novelRepository)
            }
        }
    }
}