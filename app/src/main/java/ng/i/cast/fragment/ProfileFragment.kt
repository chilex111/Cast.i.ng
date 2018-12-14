package ng.i.cast.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ng.i.cast.helper.AppUtils
import merchant.com.our.nextlounge.helper.Const

import ng.i.cast.R
import ng.i.cast.adapter.EducationAdapter
import ng.i.cast.adapter.FilmAdapter
import ng.i.cast.helper.HttpUtility
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@SuppressLint("StaticFieldLeak")
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var recyclerView: RecyclerView ?= null
    private var recyclerView1: RecyclerView ?= null
    private var editTitle : EditText ?= null
    private var editName : EditText ?= null
    private var spinnerYear : Spinner ?= null
    private var editProduction : EditText ?= null
    private var spinnerRole : Spinner ?= null
    private var spinnerJobType : Spinner ?= null
    private var yearFilmText: String?= null

    private var editSchool : EditText ?= null
    private var spinnerYearEdu : Spinner ?= null
    private var editDegree : EditText ?= null
    private var progress: RelativeLayout ?= null
    private var adapter : ArrayAdapter<String>?= null


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
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        recyclerView = v.findViewById(R.id.recyclerView)
        recyclerView1 = v.findViewById(R.id.recyclerView1)

        progress = v.findViewById(R.id.relativeProgress)

        val years = ArrayList<String>()
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 1990..thisYear) {
            years.add(Integer.toString(i))
        }
         adapter = ArrayAdapter(activity, R.layout.spinner_text, years)


        v.findViewById<Button>(R.id.buttonEducation).setOnClickListener {
            dialogEducation()
        }
        v.findViewById<Button>(R.id.buttonFilm).setOnClickListener {
            dialogFilm()
        }
        EducationAsync().execute()
        FilmAsync().execute()
        return v
    }

    private fun dialogFilm() {
        val dialog = Dialog(activity)
        dialog.setContentView(R.layout.dialog_film)
        dialog.setCanceledOnTouchOutside(false)
        editProduction = dialog.findViewById(R.id.editProduction)
        editName = dialog.findViewById(R.id.editDirectorName)
        editTitle = dialog.findViewById(R.id.editProjectTitle)
        spinnerYear = dialog.findViewById(R.id.editYear)

        spinnerJobType = dialog.findViewById(R.id.spinnerJobType)
        spinnerRole = dialog.findViewById(R.id.spinnerRole)

        spinnerYear!!.adapter = adapter

        spinnerYear!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                    Log.i("PersonalFragment", "Nothing selected")
                } else {
                    yearFilmText = adapterView.selectedItem.toString()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }
        dialog.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.buttonSave).setOnClickListener {
            val proText = editProduction!!.text.toString()
            val nameText = editName!!.text.toString()
            val titleText = editTitle!!.text.toString()

            dialog.dismiss()
            progress!!.visibility = View.VISIBLE

            SaveFilmAsync(proText,nameText,titleText).execute()


        }

        dialog.show()

    }

    private fun dialogEducation() {
        val dialog = Dialog(activity)
        dialog.setContentView(R.layout.dialog_education)
        dialog.setCanceledOnTouchOutside(false)
        var yearText: String?= null
        spinnerYearEdu = dialog.findViewById(R.id.spinnerEducationYear)
        editSchool = dialog.findViewById(R.id.editSchool)
        editDegree = dialog.findViewById(R.id.editDegree)

        spinnerYearEdu!!.adapter = adapter

        spinnerYearEdu!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
          dialog.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.buttonSave).setOnClickListener {
            val schoolText = editSchool!!.text.toString()
            val degreeText = editDegree!!.text.toString()
            dialog.dismiss()
            progress!!.visibility = View.VISIBLE
            SaveEducationAsync(schoolText,degreeText,yearText!!).execute()

        }
        dialog.show()

    }

    inner class EducationAsync: AsyncTask<Void,Int,String>(){
        override fun doInBackground(vararg p0: Void?): String? {
            val userId = AppUtils.getMyUserId(activity)
            val url = Const.CAST_URL+""+userId
            try {
                return HttpUtility.getRequest(url)
            }catch (e:Exception){
                Log.i("EDUCATION", e.message)
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val homeAdapter = EducationAdapter(null, activity!!)
            recyclerView!!.layoutManager = LinearLayoutManager(activity)
            recyclerView!!.adapter = homeAdapter
            recyclerView!!.setHasFixedSize(true)
            recyclerView!!.requestFocus()
        }

    }
    inner class FilmAsync: AsyncTask<Void,Int,String>(){
        override fun doInBackground(vararg p0: Void?): String? {
            val userId = AppUtils.getMyUserId(activity)
            val url = Const.CAST_URL+""+userId
            try {
                return HttpUtility.getRequest(url)
            }catch (e:Exception){
                Log.i("EDUCATION", e.message)
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val homeAdapter = FilmAdapter(null, activity!!)
            recyclerView!!.layoutManager = LinearLayoutManager(activity)
            recyclerView!!.adapter = homeAdapter
            recyclerView!!.setHasFixedSize(true)
            recyclerView!!.requestFocus()
        }

    }


    inner class SaveFilmAsync(private var proText: String, private var nameText: String, private var titleText: String) : AsyncTask<Void,Int,String>(){
        override fun doInBackground(vararg p0: Void?): String {
            val map = HashMap<String, Any?>()
            map[""]= proText
            map[""]= nameText
            map[""]= titleText
            map[""]= yearFilmText

            val userId = AppUtils.getMyUserId(activity)
            val url = Const.CAST_URL+""+userId
            return HttpUtility.sendPostRequest(url, map)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val homeAdapter = FilmAdapter(null, activity!!)
            recyclerView!!.layoutManager = LinearLayoutManager(activity)
            recyclerView!!.adapter = homeAdapter
            recyclerView!!.setHasFixedSize(true)
            recyclerView!!.requestFocus()
        }

    }

    inner class SaveEducationAsync(private var school: String, private var degreeText: String, private var yearText: String) : AsyncTask<Void,Int,String>(){
        override fun doInBackground(vararg p0: Void?): String {
            val map = HashMap<String, Any?>()
            map[""]= school
            map[""]= degreeText
            map[""]= yearText

            val userId = AppUtils.getMyUserId(activity)
            val url = Const.CAST_URL+""+userId
            return HttpUtility.sendPostRequest(url, map)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val homeAdapter = FilmAdapter(null, activity!!)
            recyclerView!!.layoutManager = LinearLayoutManager(activity)
            recyclerView!!.adapter = homeAdapter
            recyclerView!!.setHasFixedSize(true)
            recyclerView!!.requestFocus()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
