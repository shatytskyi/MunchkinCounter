package com.shatytskyi.munchcounter

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.shatytskyi.munchcounter.ui.screens.DetailsScreen
import com.shatytskyi.munchcounter.ui.screens.FightScreen
import com.shatytskyi.munchcounter.ui.screens.SettingsScreen
import com.shatytskyi.munchcounter.ui.screens.list.ListScreen
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel
import com.shatytskyi.munchcounter.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            val themeViewModel: ThemeViewModel = koinViewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()
            
            MunchkinTheme(themeMode = themeMode) {
                MunchkinApp(themeViewModel = themeViewModel)
            }
        }
    }
}

@Composable
fun MunchkinApp(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()

    val sharedViewModel: CommonViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = "character_list"
    ) {
        composable(
            route = "character_list",
            enterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            }
        ) {
            ListScreen(
                viewModel = sharedViewModel,
                onCharacterClick = { characterId ->
                    navController.navigate("solo/$characterId")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                }
            )
        }

        composable(
            route = "solo/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.LongType }),
            enterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            }
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
            arguments = listOf(navArgument("playerId") { type = NavType.LongType }),
            enterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            }
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getLong("playerId") ?: -1
            FightScreen(
                viewModel = sharedViewModel,
                playerId = playerId,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = "settings",
            enterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            }
        ) {
            val themeMode by themeViewModel.themeMode.collectAsState()
            SettingsScreen(
                currentThemeMode = themeMode,
                onThemeModeChange = { mode ->
                    themeViewModel.setThemeMode(mode)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

