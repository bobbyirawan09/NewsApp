package bobby.irawan.newsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import bobby.irawan.newsapp.utils.Constants.APPS_LOCALE
import bobby.irawan.newsapp.utils.Constants.DATE_FORMAT_API
import bobby.irawan.newsapp.utils.Constants.DATE_FORMAT_DEFAULT
import bobby.irawan.newsapp.utils.Constants.URL_TEST_NETWORK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by bobbyirawan09 on 26/06/20.
 */

fun String?.orEmpty(): String = this ?: ""

fun Context.getDrawableIdentifier(drawableName: String?) =
    this.resources.getIdentifier(drawableName.orEmpty(), "drawable", this.packageName)

fun String.parseServerDateFormatToString(
    oldFormat: String = DATE_FORMAT_API,
    newFormat: String = DATE_FORMAT_DEFAULT
): String {
    try {
        val oldDateFormat = SimpleDateFormat(oldFormat, APPS_LOCALE)
        val newDate = oldDateFormat.parse(this)
        val newDateFormat = SimpleDateFormat(newFormat, APPS_LOCALE)
        return newDateFormat.format(newDate ?: Date())
    } catch (e: Exception) {
        return ""
    }
}

fun View.setGone() { this.visibility = View.GONE }
fun View.setVisible() { this.visibility = View.VISIBLE }

fun Context.isInternetConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        var result = false
        if (activeNetwork != null) {
            result = activeNetwork.isConnectedOrConnecting
        }
        return result
}