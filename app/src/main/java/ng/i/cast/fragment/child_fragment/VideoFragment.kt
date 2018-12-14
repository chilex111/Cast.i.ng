package ng.i.cast.fragment.child_fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ng.i.cast.helper.AppUtils
import merchant.com.our.nextlounge.helper.Const

import ng.i.cast.R
import ng.i.cast.helper.HttpUtility
import ng.i.cast.model.BooleanModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class VideoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var roleText : String ?= null
    private var appUtils: AppUtils?= null
    private var progress: RelativeLayout ?= null
    private var adapter : ArrayAdapter<String>?= null
    private var yearText : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_video, container, false)
        appUtils = AppUtils(activity!!)
        progress = v.findViewById(R.id.relativeProgress)

        val years = ArrayList<String>()
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 1990..thisYear) {
            years.add(Integer.toString(i))
        }
        adapter = ArrayAdapter(activity, R.layout.spinner_text, years)

        v.findViewById<ImageButton>(R.id.buttonAdd).setOnClickListener {
            videoDialog()
        }

        return v
    }

    private fun videoDialog() {

        val dialog= Dialog(activity!!)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_video)
        val editTitle = dialog.findViewById<EditText>(R.id.editAudioTitle)
        val spinnerYear = dialog.findViewById<Spinner>(R.id.editYear)
        val editUrl = dialog.findViewById<EditText>(R.id.editAudioUrl)
        val  spinnerRole =
                dialog.findViewById<Spinner>(R.id.spinnerRole)

        spinnerYear!!.adapter = adapter

        spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                    Log.i("PersonalFragment", "Nothing selected")
                } else {
                    yearText = adapterView.selectedItem.toString()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }
        val acctTypeList = ArrayList<String>()
        acctTypeList.add("Role")
        acctTypeList.add("Lead role")
        acctTypeList.add("Supporting Actor")
        //acctTypeList.add("Current")
        val acctAdapter = ArrayAdapter(activity,android.R.layout.simple_spinner_dropdown_item, acctTypeList)
        acctAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole!!.adapter = acctAdapter


        spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                    Log.i("PersonalFragment", "Nothing selected")
                } else {
                    roleText = adapterView.selectedItem.toString()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }
        dialog.findViewById<Button>(R.id.buttonSave).setOnClickListener {
            dialog.dismiss()
            progress!!.visibility = View.VISIBLE
            VideoAsync(editTitle.text.toString(),editUrl.text.toString()).execute()

        }

        dialog.show()
    }

    @SuppressLint("StaticFieldLeak")
    inner class VideoAsync(private var title: String, private var url: String) :AsyncTask<Void, Int, String>(){
        override fun doInBackground(vararg p0: Void?): String {
            val userId = AppUtils.getMyUserId(activity)
            val map = HashMap<String, Any?>()
            map["link"] =url
            map["title"] =title
            map["year"] =yearText
            map["projectrole_id"] =roleText
            val url = Const.CAST_URL+"addvideo/"+userId
            return HttpUtility.sendPostRequest(url, map)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!result.isNullOrEmpty()){
                val gson = Gson()
                try {
                    val type = object : TypeToken<BooleanModel>() {}.type
                    val userModel = gson.fromJson<BooleanModel>(result, type)
                    if (userModel != null) {
                        if (userModel.status) {
                            appUtils!!.showAlert(userModel.statusMsg)
                        }
                        else{
                            appUtils!!.showAlert(userModel.statusMsg)

                        }
                    }

                }
                catch (e: Exception) {
                    Log.i("FORGOT", e.message)
                }
            }
        }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VideoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                VideoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
