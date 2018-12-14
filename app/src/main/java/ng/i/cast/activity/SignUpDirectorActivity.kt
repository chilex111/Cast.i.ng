package ng.i.cast.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ng.i.cast.helper.AppUtils
import merchant.com.our.nextlounge.helper.Const
import ng.i.cast.R
import ng.i.cast.helper.HttpUtility
import ng.i.cast.model.SignUpModel
import java.io.ByteArrayOutputStream

class SignUpDirectorActivity : AppCompatActivity() {
    private val PICK_PHOTO_REQUEST = 13
    private val TAKE_PHOTO_REQUEST = 14

    private var editFirstName : EditText?= null
    private var editLastName : EditText?= null
    private var editEmail : EditText?= null
    private var editPhone : EditText?= null
    private var editPassword : EditText?= null

    private var radioGender : RadioGroup?= null
    private var genderStatus : String ?= null
    private var appUtils: AppUtils?= null
    private var dialog: Dialog? = null
    private var profile: ImageView?= null
    private var filePath: String? = null
    private var progress:RelativeLayout ?= null
    private var convertedImage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_director)

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 13)
        }

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 14)
        }

        appUtils = AppUtils(this)

        editFirstName = findViewById(R.id.editFirstName)
        editLastName = findViewById(R.id.editLastName)
        editEmail = findViewById(R.id.editEmailAddress)
        editPhone = findViewById(R.id.editPhone)
        editPassword = findViewById(R.id.editPassword)
        profile = findViewById(R.id.imagePhoto)
        progress = findViewById(R.id.relativeProgress)

        radioGender = findViewById(R.id.radioGender)

        findViewById<Button>(R.id.buttonSignUp).setOnClickListener {
            submitForm()
        }
        radioGender!!.setOnCheckedChangeListener { _, i ->
            if (i == R.id.radioFemale) {
                genderStatus = "0"
            } else if (i == R.id.radioMale) {
                genderStatus = "1"
            }
        }
        findViewById<FrameLayout>(R.id.imageFrame).setOnClickListener {
            showDialog()
        }
        findViewById<TextView>(R.id.textLogin).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    private fun showDialog() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Profile Photo")
        // Get the layout inflater
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        @SuppressLint("InflateParams") val dialogView = inflater.inflate(R.layout.dialog_profile_photo, null)

        val buttonGallery = dialogView.findViewById<LinearLayout>(R.id.buttonGallery)
        val buttonCamera = dialogView.findViewById<LinearLayout>(R.id.buttonCamera)

        buttonGallery.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_PHOTO_REQUEST)

            dialog!!.dismiss()
        }

        buttonCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {

                startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST)
            }

            dialog!!.dismiss()
        }
        builder.setView(dialogView)
        dialog = builder.create()
        dialog!!.setCancelable(true)
        dialog!!.setCanceledOnTouchOutside(true)
        dialog!!.show()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var profilePicture: Bitmap?
        if (requestCode == PICK_PHOTO_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            try {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                assert(selectedImage != null)
                val cursor = this.contentResolver.query(
                        selectedImage!!, filePathColumn, null, null, null)!!
                cursor.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                filePath = cursor.getString(columnIndex)

                cursor.close()


                profilePicture = BitmapFactory.decodeFile(filePath)

                if (profilePicture != null)
                    profile!!.setImageBitmap(profilePicture)
                val baos = ByteArrayOutputStream()
                assert(profilePicture != null)
                profilePicture!!.compress(Bitmap.CompressFormat.PNG, 100, baos)
                Log.i("TAG", "width: " + profilePicture.width + ", height: " + profilePicture.height)
                val b = baos.toByteArray()
                convertedImage = Base64.encodeToString(b, Base64.DEFAULT)
                Log.d("TAG", convertedImage)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        if (requestCode == TAKE_PHOTO_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            try {
                val extras = data.extras!!
                profilePicture = extras.get("data") as Bitmap

                profile!!.setImageBitmap(profilePicture)

                val bao = ByteArrayOutputStream()
                profilePicture.compress(Bitmap.CompressFormat.PNG, 90, bao)
                val ba = bao.toByteArray()

                convertedImage = Base64.encodeToString(ba, Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    private fun submitForm() {
        val fNameText = editFirstName!!.text.toString()
        val lNameText = editLastName!!.text.toString()
        val emailText = editEmail!!.text.toString()
        val passwordText = editPassword!!.text.toString()
        val phoneText = editPhone!!.text.toString()
        if (fNameText.isEmpty())
            editFirstName!!.error = "This field is required"
        if (lNameText.isEmpty())
            editLastName!!.error = "This field is required"
        if (passwordText.isEmpty())
            editPassword!!.error = "This field is required"
        if (phoneText.isEmpty())
            editPhone!!.error = "This field is required"
        if (emailText.isEmpty())
            editEmail!!.error = "This field is required"
        else
            if (appUtils!!.hasActiveInternetConnection())
                SignUpAsync(emailText,passwordText,fNameText,lNameText,phoneText).execute()
            else
                Toast.makeText(this, "It seems that you have no Internet Access", Toast.LENGTH_LONG).show()

    }

    @SuppressLint("StaticFieldLeak")
    inner class SignUpAsync(private var emailText: String, private var passwordText: String,private var fNameText: String,
                            private var lNameText: String,private var phoneText: String) : AsyncTask<Void, Int, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            progress!!.visibility = View.VISIBLE
        }
        override fun doInBackground(vararg p0: Void?): String {
            val map = HashMap<String, Any?>()
            map["email"] = emailText
            map["password"] = passwordText
            map["phone"] = phoneText
            map["gender"] =genderStatus
            map["profile"] = convertedImage
            map["firstname"] = fNameText
            map["lastname"] = lNameText
            map["password2"] = passwordText
            val url = Const.CAST_URL+"signup/director"
            return HttpUtility.sendPostRequest(url, map)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progress!!.visibility = View.GONE

            if (!result.isNullOrEmpty()){
                val gson = Gson()
                try{
                    val type = object : TypeToken<SignUpModel>() {}.type
                    val userModel = gson.fromJson<SignUpModel>(result, type)
                    if (userModel != null){
                        if (userModel.status!!){
                            appUtils!!.showAlert(userModel.statusMsg!!)
                            startActivity(Intent(this@SignUpDirectorActivity, LoginActivity::class.java))
                        }else
                            appUtils!!.showAlert(userModel.statusMsg!!)
                    }
                }catch (e:Exception){
                    Log.i("LOGIN", e.message)
                }
            }
        }
    }
}
