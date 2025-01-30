package jp.speakbuddy.edisonandroidexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import jp.speakbuddy.feature_main.FactAppGraph

/**
 * Main Activity could be moved to feature and make the app more clean
 * Make app gradle dependency-less and only need hilt and feature lib
 * Using activity-alias to implement it
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FactAppGraph()
        }
    }
}
