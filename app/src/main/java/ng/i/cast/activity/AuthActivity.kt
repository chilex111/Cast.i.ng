package ng.i.cast.activity

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ng.i.cast.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        findViewById<Button>(R.id.buttonSignUp).setOnClickListener {
            signUpDialog()
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
}
