package ng.i.cast.fragment.child_fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.dialog_bts.*

import ng.i.cast.R
import ng.i.cast.helper.HttpUtility
import java.io.ByteArrayOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private val PICK_PHOTO_REQUEST = 13
private val TAKE_PHOTO_REQUEST = 14

class ChildGalleryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var recyclerView: RecyclerView ?= null
    private var recyclerView1: RecyclerView ?= null

    private var dialog: Dialog? = null
    private var filePath: String? = null
    private var convertedImage: String? = null
    private var progress: RelativeLayout?= null
    private var imageView1: ImageView ?= null
    private var imageView2: ImageView ?= null
    private var imageView3: ImageView ?= null
    private var imageView4: ImageView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_child_gallery, container, false)

        recyclerView = v.findViewById(R.id.recyclerView)
        recyclerView1 = v.findViewById(R.id.recyclerViewBTS)


        v.findViewById<ImageButton>(R.id.fab).setOnClickListener {
            photoDialog()

        }
        return v
    }
    private fun photoDialog() {
        val dialog= Dialog(activity!!)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_photo)
        dialog.findViewById<Button>(R.id.buttonHeadShot).setOnClickListener {
            showDialog()
        }
        dialog.findViewById<Button>(R.id.buttonBTS).setOnClickListener {
            btsDialog()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun btsDialog() {
        val dialog= Dialog(activity)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_bts)

        imageView1 =dialog.findViewById(R.id.image1)
        imageView1!!.setOnClickListener {
        }
        imageView2 = dialog.findViewById(R.id.image2)
       imageView3 = dialog.findViewById(R.id.image3)
        imageView4 = dialog.findViewById(R.id.image4)


        dialog.findViewById<Button>(R.id.buttonSavePhoto).setOnClickListener {
            showDialog()
        }
        dialog.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun showDialog() {

        val builder = AlertDialog.Builder(activity!!)

        builder.setTitle("Profile Photo")
        // Get the layout inflater
        val inflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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
            if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {

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
                val cursor = activity!!.contentResolver.query(
                        selectedImage!!, filePathColumn, null, null, null)!!
                cursor.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                filePath = cursor.getString(columnIndex)

                cursor.close()


                profilePicture = BitmapFactory.decodeFile(filePath)

                /* if (profilePicture != null)
                     profile!!.setImageBitmap(profilePicture)*/
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

                //profile!!.setImageBitmap(profilePicture)

                val bao = ByteArrayOutputStream()
                profilePicture.compress(Bitmap.CompressFormat.PNG, 90, bao)
                val ba = bao.toByteArray()

                convertedImage = Base64.encodeToString(ba, Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
    @SuppressLint("StaticFieldLeak")
    inner class SaveHeadShotAsync:AsyncTask<Void, Int, String>(){
        override fun doInBackground(vararg p0: Void?): String {
            return HttpUtility.getRequest("")
        }

    }
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
                ChildGalleryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}
