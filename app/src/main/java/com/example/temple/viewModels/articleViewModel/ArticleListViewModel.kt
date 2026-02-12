package com.example.temple.viewModels.articleViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.temple.dataClasses.ArticleDataClass
import com.example.temple.repositories.articleRepository.ArticleListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel(private val articleListRepository: ArticleListRepository): ViewModel() {

    private val _articleList = MutableStateFlow<List<ArticleDataClass>> (emptyList())

    val articleList: StateFlow<List<ArticleDataClass>> = _articleList

    init {
        fetchArticleList()
    }

    fun fetchArticleList() {
        viewModelScope.launch {
            _articleList.value = articleListRepository.getArticleLists()
        }
    }
}

class ArticleListViewModelFactory(private val articleListRepository: ArticleListRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleListViewModel(articleListRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}