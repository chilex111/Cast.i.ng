package ng.i.cast.helper

import android.content.Context
import android.net.ConnectivityManager
import android.os.NetworkOnMainThreadException
import android.support.v7.app.AlertDialog
import android.util.Log
import ng.i.cast.R
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL
import java.net.UnknownHostException
import java.util.concurrent.*


class AppUtils(private val context: Context) {

    fun showAlert(paramString: String) {
        val localBuilder = AlertDialog.Builder(this.context)
        localBuilder.setMessage(paramString)
        localBuilder.setNeutralButton(R.string.ok) { _, _ -> }
        localBuilder.create().show()
    }

    companion object {
        var PREF_EMAIL: String = "user_email"
        var PREF_IS_LOGGEDIN: String = "isloggedin"
        var PREF_USERNAME: String
        var PREF_ROLE = "password"
        var PREF_PROFILE: String ="profile"
        var PREF_PACKAGE = "token"
        var PREF_USERID: String = "user_id"
        var LOG_TAG: String="Network check:"

        private var sharedPreferenceUtil: SharedPreferenceUtil? = null

        init {
            PREF_USERNAME = "last_name"
        }

        fun getMyEmail(context: Context?): String {
            sharedPreferenceUtil = SharedPreferenceUtil
            try {
                return SharedPreferenceUtil.getValue(context!!.applicationContext, PREF_EMAIL)!!
            } catch (ex: Exception) {
                return ""
            }

        }


        fun getMyProfile(paramContext: Context?): String? {
            sharedPreferenceUtil = SharedPreferenceUtil
            try {
                return SharedPreferenceUtil.getValue(paramContext!!.applicationContext, PREF_PROFILE)
            } catch (e: Exception) {
            }

            return ""
        }

        fun getMyUserName(paramContext: Context?): String? {
            sharedPreferenceUtil = SharedPreferenceUtil
            try {
                return SharedPreferenceUtil.getValue(paramContext!!.applicationContext, PREF_USERNAME)

            } catch (e: Exception) {
            }

            return ""
        }

        fun getMyRole(paramContext: Context?): String? {
            sharedPreferenceUtil = SharedPreferenceUtil
            try {
                return SharedPreferenceUtil.getValue(paramContext!!.applicationContext, PREF_ROLE)

            } catch (e: Exception) {
            }

            return ""
        }

        fun getMyUserId(paramContext: Context?): String ?{
            sharedPreferenceUtil = SharedPreferenceUtil
            try {
                return SharedPreferenceUtil.getValue(paramContext!!.applicationContext, PREF_USERID)

            } catch (e: Exception) {
            }

            return ""
        }

        fun getMyPackage(context: Context?): String? {
            sharedPreferenceUtil = SharedPreferenceUtil
            try {
                return SharedPreferenceUtil.getValue(context!!.applicationContext, PREF_PACKAGE)
            } catch (e: Exception){

            }
            return ""
        }

    }

    fun internetConnectionAvailable( timeOut: Long): Boolean {
        var inetAddress: InetAddress? = null
        try {
            val future = Executors.newSingleThreadExecutor().submit(Callable<InetAddress> {
                try {
                    return@Callable InetAddress.getByName("google.com")
                } catch (e: UnknownHostException) {
                    return@Callable null
                }
            })
            inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS)
            future.cancel(true)
        } catch (e: InterruptedException) {
        } catch (e: ExecutionException) {
        } catch (e: TimeoutException) {
        }

        return inetAddress != null && !inetAddress.equals("")
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }
    fun hasActiveInternetConnection(): Boolean {
        if (isNetworkAvailable()) {
            try {
                val urlc = URL("http://www.google.com").openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "Test")
                urlc.setRequestProperty("Connection", "close")
                urlc.connectTimeout = 15000
                urlc.connect()
                return urlc.responseCode == 200
            } catch (e: Exception) {
                if (e is IOException)
                    Log.e(LOG_TAG, "Error checking internet connection", e)
                if (e is NetworkOnMainThreadException)
                    Log.e(LOG_TAG, " connection", e)

            }

        } else {
            Log.d(LOG_TAG, "No network available!")
        }
        return false
    }
}
