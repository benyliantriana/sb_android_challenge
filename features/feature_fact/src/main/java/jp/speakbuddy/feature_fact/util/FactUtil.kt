package jp.speakbuddy.feature_fact.util

import android.content.Context
import android.content.Intent

const val catImageUrl =
    "https://static.vecteezy.com/system/resources/previews/009/665/315/non_2x/group-of-cute-kitty-cat-family-greeting-cartoon-png.png"

internal fun Context.shareFact(fact: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, fact)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}
