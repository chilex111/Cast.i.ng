package ng.i.cast.fragment.child_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ng.i.cast.helper.AppUtils
import merchant.com.our.nextlounge.helper.Const

import ng.i.cast.R
import ng.i.cast.adapter.AuditionAdapter
import ng.i.cast.helper.HttpUtility
import ng.i.cast.model.AuditionModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val AUDITION_TYPE = "param1"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChildAuditionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChildAuditionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ChildAuditionFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var audition_Type: String? = null
    private var recyclerView : RecyclerView ?= null
    private var progress : RelativeLayout ?= null
    private var empty : TextView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            audition_Type = it.getString(AUDITION_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_child_audition, container, false)

        recyclerView = v.findViewById(R.id.recyclerView)
        progress = v.findViewById(R.id.relativeProgress)
        empty = v.findViewById(R.id.textEmpty)

        empty!!.text = audition_Type
        RecentProjectAsync(audition_Type,activity!!).execute()
        progress!!.visibility = View.GONE
        return v
    }

/*
    override fun projectListener(projectList: List<AuditionList>, status: Boolean) {
        progress!!.visibility = View.GONE

        if (projectList.isNotEmpty()) {
            when(audition_Type){
                "available"->{
                    if (status) {
                        val homeAdapter = HomeAdapter(projectList, activity!!)
                        recyclerView!!.layoutManager = LinearLayoutManager(activity)
                        recyclerView!!.adapter = homeAdapter
                        recyclerView!!.setHasFixedSize(true)
                        recyclerView!!.requestFocus()
                    }else{
                        val msg = "You have no role to Apply for. Update your profile to get fitting roles"
                        empty!!.text =msg
                    }
                }
                "applied"->{
                    if (status) {
                        val homeAdapter = HomeAdapter(projectList, activity!!)
                        recyclerView!!.layoutManager = LinearLayoutManager(activity)
                        recyclerView!!.adapter = homeAdapter
                        recyclerView!!.setHasFixedSize(true)
                        recyclerView!!.requestFocus()
                    }else{

                        val msg = "You have no role to Apply for. Update your profile to get fitting roles"
                        empty!!.text =msg
                    }
                }

            }
        }
    }
*/


    @SuppressLint("StaticFieldLeak")
   inner class RecentProjectAsync(private val audition_Type: String?, private var context: Context) : AsyncTask<Void, Int, String>() {

        override fun doInBackground(vararg p0: Void?): String? {
            var url: String ?= null
            val userId = AppUtils.getMyUserId(context)
            if (audition_Type =="available"){
                url = Const.CAST_URL +"auditions/available/"+152
            }
            if (audition_Type =="applied")
                url = Const.CAST_URL +"auditions/applied/"+152

            try {
                return HttpUtility.getRequest(url)
            }catch (e:Exception){
                Log.i("RECENT", e.message)
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (!result.isNullOrEmpty()){
                val gson = Gson()
                try{
                    val type = object : TypeToken<AuditionModel>() {}.type
                    val auditionModel = gson.fromJson<AuditionModel>(result, type)
                    if (auditionModel != null){
                        when(audition_Type){
                            "available"->{
                                if (auditionModel.status) {
                                    val homeAdapter = AuditionAdapter(auditionModel.list, activity!!)
                                    recyclerView!!.layoutManager = LinearLayoutManager(activity)
                                    recyclerView!!.adapter = homeAdapter
                                    recyclerView!!.setHasFixedSize(true)
                                    recyclerView!!.requestFocus()
                                }else{
                                    val msg = "You have no role to Apply for. Update your profile to get fitting roles"
                                    empty!!.text =msg
                                }
                            }
                            "applied"->{
                                if (auditionModel.status) {
                                    val homeAdapter = AuditionAdapter(auditionModel.list, activity!!)
                                    recyclerView!!.layoutManager = LinearLayoutManager(activity)
                                    recyclerView!!.adapter = homeAdapter
                                    recyclerView!!.setHasFixedSize(true)
                                    recyclerView!!.requestFocus()
                                }else{

                                    val msg = "You have no role to Apply for. Update your profile to get fitting roles"
                                    empty!!.text =msg
                                }
                            }

                        }
                    }

                }catch (e:Exception){
                    Log.i("AUDITION", e.message)
                }
            }
        }
    }    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChildAuditionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
                ChildAuditionFragment().apply {
                    arguments = Bundle().apply {
                        putString(AUDITION_TYPE, param1)

                    }
                }
    }
}
