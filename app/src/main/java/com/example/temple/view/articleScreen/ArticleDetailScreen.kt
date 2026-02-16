package com.example.temple.view.articleScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.temple.viewModels.articleViewModel.ArticleViewModel
import androidx.compose.material.icons.filled.CheckCircle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    navController: NavController,
    articleViewModel: ArticleViewModel
) {
    val articleDetail by articleViewModel.selectedArticle.collectAsStateWithLifecycle()

    articleDetail ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {
        TopAppBar(
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                        .padding(start = 20.dp, end = 15.dp)
                )
            },
            title = {
                Text(
                    text = articleDetail!!.name,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
        LazyColumn(
            //contentPadding = PaddingValues(bottom = 10.dp)
        ) {
            item { ImageCard(image = articleDetail!!.image) }

            item { ArticleDefinition(definition = articleDetail!!.definition) }

            item { ArticleTypesSection(type = articleDetail!!.types) }

            item { ArticleCausesSection(cause = articleDetail!!.causes) }

            item { ArticleSymptomsSection(symptoms = articleDetail!!.symptoms) }

            item { ArticlePreventionStrategySection(prevention = articleDetail!!.preventionStrategy) }
        }
    }

}

// Image
@Composable
fun ImageCard(
    image: Int
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 12.dp, bottom = 18.dp)
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}

// Definition
@Composable
fun ArticleDefinition(definition: String) {
    Column(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp, 27.dp)
                    .background(
                        color = Color(0xFF135bec),
                        shape = RoundedCornerShape(10.dp)
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Definition",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = definition,
            color = Color(0xFF4b596c),
            fontSize = 17.sp,
            fontWeight = FontWeight.Light
        )
    }
}


// Types
@Composable
fun ArticleTypesSection(type: List<String>) {
    Text(
        text = "Types",
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 30.dp)
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(type) {
            ArticleTypesCard(it)
        }
    }
}

@Composable
fun ArticleTypesCard(types: String) {
    Column(
        modifier = Modifier
            .wrapContentWidth()  // auto adjust width according to content
            .defaultMinSize(minWidth = 180.dp)   // minimum width
            .widthIn(max = 240.dp)   // maximum width so it doesn’t stretch too much
            .background(
                Color(0xFFf8fafc),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
    ) {
        Text(
            text = types,
            color = Color(0xFF68788e),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Causes
@Composable
fun ArticleCausesSection(cause: List<String>) {
    Text(
        text = "Causes",
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 30.dp)
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cause) {
            ArticleCausesCard(it)
        }
    }
}

@Composable
fun ArticleCausesCard(causes: String) {
    Column(
        modifier = Modifier
            .wrapContentWidth()  // auto adjust width according to content
            .defaultMinSize(minWidth = 180.dp)   // minimum width
            .widthIn(max = 240.dp)   // maximum width so it doesn’t stretch too much
            .background(
                Color(0xFFf8fafc),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
    ) {
        Text(
            text = causes,
            color = Color(0xFF4f5d70),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Symptoms
@Composable
fun ArticleSymptomsSection(symptoms: List<String>) {
    Text(
        text = "Symptoms",
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 30.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        symptoms.forEach { symptom ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF135bec),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = symptom,
                    color = Color(0xFF4a576b),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

// Prevention Strategy
@Composable
fun ArticlePreventionStrategySection(prevention: List<String>) {
    Text(
        text = "Prevention Strategy",
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 30.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        prevention.forEach {
            ArticlePreventionStrategyCard(it)
        }
    }
}

@Composable
fun ArticlePreventionStrategyCard(preventions: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFf8fafc),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
    ) {
        Text(
            text = preventions,
            color = Color(0xFF4f5d70),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}