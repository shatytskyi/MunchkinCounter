package com.shatytskyi.gamecounter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
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
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.shatytskyi.gamecounter.ui.screens.details.DetailsScreen
import com.shatytskyi.gamecounter.ui.screens.FightScreen
import com.shatytskyi.gamecounter.ui.screens.SettingsScreen
import com.shatytskyi.gamecounter.ui.screens.TimerScreen
import com.shatytskyi.gamecounter.ui.screens.list.ListScreen
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme
import com.shatytskyi.gamecounter.viewmodel.CommonViewModel
import com.shatytskyi.gamecounter.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        
        // Keep screen on while app is active
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            val themeViewModel: ThemeViewModel = koinViewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()
            val dynamicColors by themeViewModel.dynamicColors.collectAsState()

            MunchkinTheme(
                themeMode = themeMode,
                dynamicColor = dynamicColors
            ) {
                MunchkinApp(
                    themeViewModel = themeViewModel,
                    activity = this
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MunchkinApp(
    themeViewModel: ThemeViewModel,
    activity: ComponentActivity
) {
    val navController = rememberNavController()
    val sharedViewModel: CommonViewModel = koinViewModel()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = "character_list"
        ) {
            composable(
                route = "character_list",
                enterTransition = { 
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    )
                },
                exitTransition = { 
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    )
                },
                popEnterTransition = { 
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    )
                },
                popExitTransition = { 
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    )
                }
            ) {
                ListScreen(
                    viewModel = sharedViewModel,
                    onCharacterClick = { characterId ->
                        navController.navigate("solo/$characterId")
                    },
                    onTimerClick = {
                        navController.navigate("timer")
                    },
                    onSettingsClick = {
                        navController.navigate("settings")
                    },
                    animatedContentScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }

            composable(
                route = "solo/{characterId}",
                arguments = listOf(navArgument("characterId") { type = NavType.LongType }),
                enterTransition = { 
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    )
                },
                exitTransition = { 
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    )
                },
                popEnterTransition = { 
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    )
                },
                popExitTransition = { 
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    )
                }
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getLong("characterId") ?: -1
                DetailsScreen(
                    viewModel = sharedViewModel,
                    characterId = characterId,
                    onBack = { navController.navigateUp() },
                    onFight = { 
                        navController.navigate("fight/$characterId")
                    },
                    onTimerClick = { navController.navigate("timer") },
                    animatedContentScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }

        composable(
            route = "fight/{playerId}",
            arguments = listOf(navArgument("playerId") { type = NavType.LongType }),
            enterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getLong("playerId") ?: -1
            FightScreen(
                viewModel = sharedViewModel,
                playerId = playerId,
                onBack = { navController.navigateUp() },
                onTimerClick = { navController.navigate("timer") },
                animatedContentScope = this@composable,
                sharedTransitionScope = this@SharedTransitionLayout
            )
        }
        
        composable(
            route = "timer",
            enterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
        ) {
            TimerScreen(
                onBack = { navController.navigateUp() }
            )
        }
        
        composable(
            route = "settings",
            enterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = { 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = { 
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
        ) {
            val themeMode by themeViewModel.themeMode.collectAsState()
            val dynamicColors by themeViewModel.dynamicColors.collectAsState()
            SettingsScreen(
                currentThemeMode = themeMode,
                dynamicColors = dynamicColors,
                onThemeModeChange = { mode ->
                    themeViewModel.setThemeMode(mode)
                },
                onDynamicColorsChange = { enabled ->
                    themeViewModel.setDynamicColors(enabled)
                },
                onBackClick = {
                    navController.navigateUp()
                },
                onRateAppClick = {
                    // Open Play Store directly from settings
                    sharedViewModel.openPlayStore()
                },
                onShareAppClick = {
                    // Share app link
                    sharedViewModel.shareApp()
                },
                onLanguageClick = {
                    // Open system language settings for the app
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        // Android 13+ - Direct app language settings
                        val intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS).apply {
                            data = android.net.Uri.fromParts("package", activity.packageName, null)
                        }
                        activity.startActivity(intent)
                    } else {
                        // Android 12 and below - Open app info settings
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = android.net.Uri.fromParts("package", activity.packageName, null)
                        }
                        activity.startActivity(intent)
                    }
                }
            )
        }
        }
    }
}

