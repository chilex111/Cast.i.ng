package ng.i.cast.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ng.i.cast.helper.AppUtils
import merchant.com.our.nextlounge.helper.Const
import ng.i.cast.R
import ng.i.cast.helper.HttpUtility
import ng.i.cast.model.BooleanModel

class ForgotPasswordActivity : AppCompatActivity() {
    private var email: EditText? = null
    private var appUtils : AppUtils?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        appUtils = AppUtils(this)
        email = findViewById(R.id.editEmailAddress)

        findViewById<Button>(R.id.buttonForgot).setOnClickListener {
            val emailText = email!!.text.toString()
            if (emailText.isEmpty())
                email!!.error = "This field is required"
            else {
                ForgotAsync(emailText).execute()
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class ForgotAsync(private var emailText: String) : AsyncTask<Void, Int, String>() {
        override fun doInBackground(vararg p0: Void?): String {
            val map = HashMap<String, Any?>()
            map["email"] = emailText
            val url = Const.CAST_URL + "forgotpassword"
            return HttpUtility.sendPostRequest(url, map)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!result.isNullOrEmpty()) {
                val gson = Gson()
                try {
                    val type = object : TypeToken<BooleanModel>() {}.type
                    val userModel = gson.fromJson<BooleanModel>(result, type)
                    if (userModel != null) {
                        if (userModel.status) {
                            val localBuilder = AlertDialog.Builder(this@ForgotPasswordActivity)
                            localBuilder.setMessage(userModel.statusMsg)
                            localBuilder.setNeutralButton(R.string.ok) { _, _ ->
                                startActivity(Intent(this@ForgotPasswordActivity,LoginActivity::class.java))
                            }
                            localBuilder.create().show()
                            //resetDialog()
                        }
                    }


                }
                catch (e: Exception) {
                    Log.i("FORGOT", e.message)
                }
            }
        }
    }

    private fun resetDialog() {
        val dialog = Dialog(this@ForgotPasswordActivity, R.style.Dialog)
        dialog.setContentView(R.layout.dialog_reset_password)
        dialog.setCanceledOnTouchOutside(false)
        val password = dialog.findViewById<EditText>(R.id.editPassword)
        val con_password = dialog.findViewById<EditText>(R.id.editConPassword)
        dialog.show()
        dialog.findViewById<Button>(R.id.buttonReset).setOnClickListener {
            val passwordText = password.text.toString()
            val conPasswordText = con_password.text.toString()
            if (passwordText.isEmpty())
                password.error = "Thi field is required"
            if (conPasswordText.isEmpty())
                con_password.error = "This field is required"
            if (passwordText != conPasswordText)
                con_password.error = "Password don't match"
            else
                ResetAsync(conPasswordText, passwordText).execute()
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class ResetAsync(private var conPasswordText: String, private var password: String?) : AsyncTask<Void, Int, String>() {
        override fun doInBackground(vararg p0: Void?): String {
            val map = HashMap<String, Any?>()
            map["password"] = password
            map["password2"] = conPasswordText
            val url = Const.CAST_URL + "resetpassword/5c1151209abef"
            return HttpUtility.sendPostRequest(url, map)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!result.isNullOrEmpty()) {
                val gson = Gson()

                try {
                    val type = object : TypeToken<BooleanModel>() {}.type
                    val userModel = gson.fromJson<BooleanModel>(result, type)
                    if (userModel != null) {
                        if (userModel.status) {
                            startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
                            Toast.makeText(this@ForgotPasswordActivity, userModel.statusMsg, Toast.LENGTH_LONG).show()

                        }
                    }


                } catch (e: Exception) {
                }
            }

        }
    }
}

