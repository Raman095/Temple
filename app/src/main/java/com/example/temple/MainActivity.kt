package com.example.temple

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.temple.repositories.EmergencyRepository
import com.example.temple.view.EmergencyContactScreen
import com.example.temple.view.articleScreen.ArticleDetailScreen
import com.example.temple.view.articleScreen.ArticleListScreen
import com.example.temple.view.medicineScreen.MedicineDetailScreen
import com.example.temple.view.medicineScreen.MedicineListScreen
import com.example.temple.viewModels.EmergencyViewModel
import com.example.temple.viewModels.EmergencyViewModelFactory
import com.example.temple.viewModels.MedicineViewModel
import com.example.temple.viewModels.articleViewModel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.White.toArgb()
            )
        )
        setContent {

            val navController = rememberNavController()

            // For Emergency Contact Screen
            val emergencyContactRepository = remember {
                EmergencyRepository(applicationContext)
            }
            val emergencyViewModel: EmergencyViewModel = viewModel(
                factory = EmergencyViewModelFactory(emergencyContactRepository)
            )

            // For Article List and Information Screen
            val articleViewModel: ArticleViewModel = hiltViewModel()

            // For Medicine List and Medicine Detail Screen
            val medicineViewModel: MedicineViewModel = hiltViewModel()

            Scaffold(
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                bottomBar = {
                    BottomNavigationBar(navController)
                }
            ) { paddingValues ->

                AppNavHost(
                    navController = navController,
                    emergencyContactViewModel = emergencyViewModel,
                    articleViewModel = articleViewModel,
                    medicineViewModel = medicineViewModel,
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

    object Medicine : BottomNavItem(
        route = "medicine",
        title = "Medicine",
        iconRes = R.drawable.medicine
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Emergency,
        BottomNavItem.Article,
        BottomNavItem.Medicine
    )

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .height(80.dp)
            .border(BorderStroke(1.dp, Color.LightGray), shape = RectangleShape)
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
                    selectedIconColor = Color(0xFF22c55e),
                    selectedTextColor = Color(0xFF22c55e),
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
    articleViewModel: ArticleViewModel,
    medicineViewModel: MedicineViewModel,
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
            composable("article_list") { ArticleListScreen(navController, articleViewModel) }

            // Article Information Screen
            composable("article_detail") {
                ArticleDetailScreen(navController, articleViewModel)
            }
        }

        // Medicine Screen
        navigation(
            route = BottomNavItem.Medicine.route,
            startDestination = "medicine_list"
        ) {

            // Medicine List Screen
            composable("medicine_list") { MedicineListScreen(navController, medicineViewModel) }

            // Medicine Detail Screen
            composable("medicine_detail") { MedicineDetailScreen(navController, medicineViewModel) }
        }
    }
}