package com.example.temple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.temple.repositories.EmergencyRepository
import com.example.temple.ui.theme.TempleTheme
import com.example.temple.view.EmergencyContactScreen
import com.example.temple.viewModels.EmergencyViewModel
import com.example.temple.viewModels.EmergencyViewModelFactory

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

            AppNavHost(
                navController = navController,
                emergencyContactViewModel = emergencyViewModel
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    emergencyContactViewModel: EmergencyViewModel
) {
    NavHost(navController, startDestination = "emergency") {
        composable("emergency") { EmergencyContactScreen(navController, emergencyContactViewModel) }
    }
}