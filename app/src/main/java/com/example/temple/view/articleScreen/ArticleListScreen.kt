package com.example.temple.view.articleScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.temple.dataClasses.ArticleDataClass
import com.example.temple.viewModels.articleViewModel.ArticleListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    navController: NavController,
    articleListViewModel: ArticleListViewModel
) {
    val articles by articleListViewModel.articleList.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    val query = searchQuery.trim().lowercase()
    val filteredArticles = if (query.isBlank()) {
        articles
    } else {
        articles.filter {
            buildList<String> {
                add(it.name)
                add(it.definition)
                addAll(it.types)
                addAll(it.symptoms)
                addAll(it.causes)
                addAll(it.preventionStrategy)
            }.any { articleData ->
                articleData.contains(query, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101622))
            .statusBarsPadding()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Articles",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(text = "Search contacts", color = Color.White) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .height(56.dp)
                .background(
                    color = Color(0xFF16202e),
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF16202e),
                unfocusedContainerColor = Color(0xFF16202e),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLeadingIconColor = Color.White,
                unfocusedLeadingIconColor = Color.White
            ),
            singleLine = true
        )
        HorizontalDivider(
            color = Color.Gray.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 25.dp, bottom = 15.dp)
        )
        LazyColumn {
            items(filteredArticles) { articlesData ->
                ArticleItems(articles = articlesData)
            }
        }
    }
}

@Composable
fun ArticleItems(
    articles: ArticleDataClass
) {

    val context = LocalContext.current
    val resId = remember(articles.image) {
        context.resources.getIdentifier(
            articles.image,
            "drawable",
            context.packageName
        )
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
            .height(100.dp)
            .border(
                width = 1.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(14.dp)
            ),
        colors = CardDefaults.cardColors(Color(0xFF101726)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        color = Color(0xFF334155)
                    )
            ) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = articles.name,
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}