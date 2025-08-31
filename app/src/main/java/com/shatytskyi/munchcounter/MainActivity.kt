package com.shatytskyi.munchcounter

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shatytskyi.munchcounter.ui.screens.DetailsScreen
import com.shatytskyi.munchcounter.ui.screens.FightScreen
import com.shatytskyi.munchcounter.ui.screens.ListScreen
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            MunchkinTheme {
                MunchkinApp()
            }
        }
    }
}

@Composable
fun MunchkinApp() {
    val navController = rememberNavController()

    val sharedViewModel: CommonViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "character_list"
    ) {
        composable("character_list") {
            ListScreen(
                viewModel = sharedViewModel,
                onCharacterClick = { characterId ->
                    navController.navigate("solo/$characterId")
                }
            )
        }

        composable(
            route = "solo/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.LongType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getLong("characterId") ?: -1
            DetailsScreen(
                viewModel = sharedViewModel,
                characterId = characterId,
                onBack = { navController.popBackStack() },
                onFight = { navController.navigate("fight/$characterId") }
            )
        }

        composable(
            route = "fight/{playerId}",
            arguments = listOf(navArgument("playerId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getLong("playerId") ?: -1
            FightScreen(
                viewModel = sharedViewModel,
                playerId = playerId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

