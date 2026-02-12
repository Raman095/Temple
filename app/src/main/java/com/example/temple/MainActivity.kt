package com.example.temple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.temple.repositories.EmergencyRepository
import com.example.temple.repositories.articleRepository.ArticleListRepository
import com.example.temple.view.EmergencyContactScreen
import com.example.temple.view.articleScreen.ArticleListScreen
import com.example.temple.viewModels.EmergencyViewModel
import com.example.temple.viewModels.EmergencyViewModelFactory
import com.example.temple.viewModels.articleViewModel.ArticleListViewModel
import com.example.temple.viewModels.articleViewModel.ArticleListViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            // For Emergency Contact Screen
            val emergencyContactRepository = remember {
                EmergencyRepository(applicationContext)
            }
            val emergencyViewModel: EmergencyViewModel = viewModel(
                factory = EmergencyViewModelFactory(emergencyContactRepository)
            )

            // For Article List and Information Scree
            val articleRepository = remember {
                ArticleListRepository(applicationContext)
            }
            val articleViewModel: ArticleListViewModel = viewModel(
                factory = ArticleListViewModelFactory(articleRepository)
            )

            Scaffold(
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                bottomBar = {
                    BottomNavigationBar(navController)
                }
            ) { paddingValues ->

                AppNavHost(
                    navController = navController,
                    emergencyContactViewModel = emergencyViewModel,
                    articleListViewModel = articleViewModel,
                    modifier = Modifier.padding(paddingValues)
                )

            }
        }
    }
}

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val iconRes: Int? = null,
    val imageVector: ImageVector? = null
) {
    object Emergency : BottomNavItem(
        route = "emergency",
        title = "Emergency",
        imageVector = Icons.Default.Call
    )

    object Article : BottomNavItem(
        route = "article",
        title = "Article",
        iconRes = R.drawable.book
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Emergency,
        BottomNavItem.Article
    )

    NavigationBar(
        containerColor = Color(0xFF151a20)
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination

        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination
                    ?.hierarchy
                    ?.any { it.route == item.route } == true,    // hierarchy and any used here because many screen in our app have another screen in them, when we go to that another screen, we want the same navigation bar item to be selected.
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when {
                        item.imageVector != null -> Icon(
                            item.imageVector,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )

                        item.iconRes != null -> Icon(
                            painterResource(id = item.iconRes),
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {
                    Text(text = item.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF135bec),
                    selectedTextColor = Color(0xFF135bec),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    emergencyContactViewModel: EmergencyViewModel,
    articleListViewModel: ArticleListViewModel,
    modifier: Modifier = Modifier
) {

    NavHost(navController, startDestination = BottomNavItem.Emergency.route, modifier = modifier) {

        // Emergency Screen
        composable(BottomNavItem.Emergency.route) {
            EmergencyContactScreen(
                navController,
                emergencyContactViewModel
            )
        }

        // Article list and Article Information Screen
        navigation(
            route = BottomNavItem.Article.route,
            startDestination = "article_list"
        ) {

            // Articles List Screen
            composable("article_list") { ArticleListScreen(navController, articleListViewModel) }

            // Article Information Screen
            /*composable("article_info/{articleId}") { backStackEntry ->
                val articleId = backStackEntry.arguments?.getString("articleId")
                ArticleInfoScreen(articleId)
            } */
        }
    }

}