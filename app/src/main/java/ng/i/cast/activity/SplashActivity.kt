package ng.i.cast.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ng.i.cast.helper.AppUtils
import ng.i.cast.R
import ng.i.cast.helper.SharedPreferenceUtil

class SplashActivity : AppCompatActivity() {

    private var sharedPreference: SharedPreferenceUtil? = null
    private var splashLoaded: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreference = SharedPreferenceUtil

        Handler().postDelayed({
            val loginVal = sharedPreference!!.getValue(this@SplashActivity, AppUtils.PREF_IS_LOGGEDIN)
            if (loginVal != null && !loginVal.isEmpty()) {
                if (loginVal == "1") {
                    val intent = Intent(this@SplashActivity, DashBoardActivity::class.java)
                    startActivity(intent)
                    this@SplashActivity.finish()
                }
            } else {
                //SplashScreenActivity.this.finish();

                val intent = Intent(this@SplashActivity, GetStartedActivity::class.java)
                startActivity(intent)
                this@SplashActivity.finish()
            }
        }, 3000)

    }
}
