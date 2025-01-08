package jp.speakbuddy.edisonandroidexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.speakbuddy.edisonandroidexercise.navigation.FactFavoriteRoute
import jp.speakbuddy.edisonandroidexercise.navigation.FactRoute
import jp.speakbuddy.feature_fact.ui.fact.FactScreen
import jp.speakbuddy.feature_fact.ui.favorite.FavoriteScreen
import jp.speakbuddy.lib_ui.theme.EdisonAndroidExerciseTheme

/**
 * Main Activity could be moved to feature lib and make the app more clean
 * Make app gradle dependency-less and only need hilt and feature lib
 * Using activity-alias to implement it
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdisonAndroidExercise()
        }
    }
}

@Suppress("TopLevelPropertyNaming", "FunctionNaming")
@Composable
private fun EdisonAndroidExercise() {
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
