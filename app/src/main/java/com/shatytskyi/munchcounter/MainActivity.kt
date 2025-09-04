package com.shatytskyi.munchcounter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
import com.shatytskyi.munchcounter.ui.screens.details.DetailsScreen
import com.shatytskyi.munchcounter.ui.screens.FightScreen
import com.shatytskyi.munchcounter.ui.screens.SettingsScreen
import com.shatytskyi.munchcounter.ui.screens.TimerScreen
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
            val dynamicColors by themeViewModel.dynamicColors.collectAsState()
            val systemFont by themeViewModel.systemFont.collectAsState()
            
            MunchkinTheme(
                themeMode = themeMode,
                dynamicColor = dynamicColors,
                systemFont = systemFont
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
    val context = LocalContext.current

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
                        Toast.makeText(context, "Coming Soon!", Toast.LENGTH_SHORT).show()
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
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getLong("playerId") ?: -1
            FightScreen(
                viewModel = sharedViewModel,
                playerId = playerId,
                onBack = { navController.navigateUp() }
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
            val systemFont by themeViewModel.systemFont.collectAsState()
            SettingsScreen(
                currentThemeMode = themeMode,
                dynamicColors = dynamicColors,
                systemFont = systemFont,
                onThemeModeChange = { mode ->
                    themeViewModel.setThemeMode(mode)
                },
                onDynamicColorsChange = { enabled ->
                    themeViewModel.setDynamicColors(enabled)
                },
                onSystemFontChange = { enabled ->
                    themeViewModel.setSystemFont(enabled)
                },
                onBackClick = {
                    navController.navigateUp()
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

