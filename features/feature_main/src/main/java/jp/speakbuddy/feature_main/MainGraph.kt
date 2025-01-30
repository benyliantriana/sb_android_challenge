package jp.speakbuddy.feature_main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.speakbuddy.feature_fact.ui.fact.FactScreen
import jp.speakbuddy.feature_fact.ui.favorite.FavoriteScreen
import jp.speakbuddy.feature_main.navigation.FactFavoriteRoute
import jp.speakbuddy.feature_main.navigation.FactRoute
import jp.speakbuddy.lib_ui.theme.EdisonAndroidExerciseTheme


/**
 * Navigation should be better on lib_navigation
 */
@Suppress("TopLevelPropertyNaming", "FunctionNaming")
@Composable
fun FactAppGraph() {
    EdisonAndroidExerciseTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = FactRoute) {
            composable(FactRoute) {
                FactScreen(
                    navigateToFavoriteScreen = {
                        navController.navigate(FactFavoriteRoute)
                    }
                )
            }
            composable(FactFavoriteRoute) {
                FavoriteScreen(
                    navigateUp = navController::navigateUp
                )
            }
        }
    }
}
