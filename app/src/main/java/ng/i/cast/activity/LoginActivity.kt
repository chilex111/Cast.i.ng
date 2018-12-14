package ng.i.cast.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ng.i.cast.helper.AppUtils
import ng.i.cast.helper.AppUtils.Companion.PREF_IS_LOGGEDIN
import ng.i.cast.helper.AppUtils.Companion.PREF_PACKAGE
import ng.i.cast.helper.AppUtils.Companion.PREF_PROFILE
import ng.i.cast.helper.AppUtils.Companion.PREF_ROLE
import ng.i.cast.helper.AppUtils.Companion.PREF_USERID
import merchant.com.our.nextlounge.helper.Const
import ng.i.cast.R
import ng.i.cast.helper.HttpUtility
import ng.i.cast.model.LoginModel
import android.os.StrictMode
import ng.i.cast.helper.SharedPreferenceUtil


class LoginActivity : AppCompatActivity() {
    private var editEmail: EditText ?= null
    private var editPassword: EditText ?= null
    private var progress: RelativeLayout ?= null
    private var appUtils: AppUtils?= null
    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        setContentView(R.layout.activity_login)

        appUtils = AppUtils(this)

        editEmail = findViewById(R.id.editEmailAddress)
        editPassword = findViewById(R.id.editPassword)
        progress = findViewById(R.id.relativeProgress)
        findViewById<TextView>(R.id.textSignUp).setOnClickListener {
            signUpDialog()
        }
        findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            submitForm()
        }
        findViewById<Button>(R.id.buttonForgot).setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

    }

    private fun signUpDialog() {
        val dialog = Dialog(this,R.style.Dialog)
        dialog.setContentView(R.layout.dialog_signup)
        dialog.setCanceledOnTouchOutside(false)
        dialog.findViewById<Button>(R.id.buttonActor).setOnClickListener {
            startActivity(Intent(this, SignUpActorActivity::class.java))

        }
        dialog.findViewById<Button>(R.id.buttonDirector).setOnClickListener {
            startActivity(Intent(this, SignUpDirectorActivity::class.java))
        }
        dialog.show()
    }

    private fun submitForm() {
        val emailText = editEmail!!.text.toString()
        val passwordText = editPassword!!.text.toString()

        if (emailText.isEmpty())
            editEmail!!.error = "This field is required"
        if (passwordText.isEmpty())
            editPassword!!.error = "This field is required"
        else{
            if (appUtils!!.hasActiveInternetConnection())
                LoginAsync(emailText, passwordText).execute()
            else
                Toast.makeText(this, "It seems that you have no Internet Access", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class LoginAsync(private var emailText: String, private var passwordText: String) :AsyncTask<Void, Int, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            progress!!.visibility = View.VISIBLE
        }
        override fun doInBackground(vararg p0: Void?): String? {
            val map = HashMap<String, Any?>()
            map["email"] = emailText
            map["password"] = passwordText
            val url = Const.CAST_URL+"login"
            try {
                return HttpUtility.sendPostRequest(url, map)
            }catch (e: Exception){
                Toast.makeText(this@LoginActivity,e.message,Toast.LENGTH_LONG).show()
            }
            return  null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progress!!.visibility = View.GONE
            if (!result.isNullOrEmpty()){
                val gson = Gson()
                try{
                    val type = object : TypeToken<LoginModel>() {}.type
                    val userModel = gson.fromJson<LoginModel>(result, type)
                    if (userModel != null){
                        if (userModel.status != null) {
                            if (!userModel.status!!) {
                                appUtils!!.showAlert(userModel.status_msg!!)
                            }
                        }
                        else {
                            val sharedPreferences = SharedPreferenceUtil
                            sharedPreferences.save(this@LoginActivity, userModel.userId!!, PREF_USERID)
                            sharedPreferences.save(this@LoginActivity, userModel.authPackageId!!, PREF_PACKAGE)
                            sharedPreferences.save(this@LoginActivity, userModel.role!!, PREF_ROLE)
                            sharedPreferences.save(this@LoginActivity, "1", PREF_IS_LOGGEDIN)

                            if (userModel.userImage != null)
                                sharedPreferences.save(this@LoginActivity, userModel.userImage!!, PREF_PROFILE)
                            startActivity(Intent(this@LoginActivity, DashBoardActivity::class.java))
                        }
                    }
                }catch (e:Exception){
                    Log.i("LOGIN", e.message.toString())
                }
            }
        }
    }
}
