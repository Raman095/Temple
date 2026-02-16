package com.example.temple.viewModels.articleViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import com.example.temple.dataClasses.ArticleDataClass
import com.example.temple.modelDataClasses.ArticleUIDataClass
import com.example.temple.repositories.articleRepository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ArticleViewModel @Inject constructor(private val articleRepository: ArticleRepository): ViewModel() {

    private val _articleList = MutableStateFlow<List<ArticleUIDataClass>> (emptyList())
    val articleList: StateFlow<List<ArticleUIDataClass>> = _articleList

    private val _selectedArticle = MutableStateFlow<ArticleUIDataClass?>(null)
    val selectedArticle: StateFlow<ArticleUIDataClass?> = _selectedArticle

    init {
        fetchArticleList()
    }

    fun fetchArticleList() {
        viewModelScope.launch {
            _articleList.value = articleRepository.getArticleLists()
        }
    }

    fun selectArticle(name: String)  {
        _selectedArticle.value = _articleList.value.find { it.name == name }
    }
}