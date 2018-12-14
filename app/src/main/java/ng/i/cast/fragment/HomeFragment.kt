package ng.i.cast.fragment

import android.annotation.SuppressLint
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import merchant.com.our.nextlounge.helper.Const

import ng.i.cast.R
import ng.i.cast.adapter.HomeAdapter
import ng.i.cast.api.RecentProjectAsync
import ng.i.cast.helper.HttpUtility
import ng.i.cast.listener.ProjectListener
import ng.i.cast.model.AuditionList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(), ProjectListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var recyclerView: RecyclerView ?= null
    private var recyclerView1: RecyclerView ?= null
    private var progress : RelativeLayout?= null
    private var audition_Type: String ?= null

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
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        RecentProjectAsync.projectListener = this

        recyclerView = v.findViewById(R.id.recyclerView)
        recyclerView1 = v.findViewById(R.id.recyclerViewApplied)
        progress = v.findViewById(R.id.relativeProgress)

        audition_Type = "available"
        RecentProjectAsync(audition_Type, activity!!).execute()
        return v
    }

    override fun projectListener(projectList: List<AuditionList>, status: Boolean) {
        if (projectList.isNotEmpty()) {
            progress!!.visibility = View.GONE
            when (audition_Type) {
                "available" -> {

                    val homeAdapter = HomeAdapter(projectList, activity!!)
                    recyclerView!!.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
                    recyclerView!!.adapter = homeAdapter
                    recyclerView!!.setHasFixedSize(true)
                    recyclerView!!.requestFocus()

                    audition_Type = "applied"
                    RecentProjectAsync(audition_Type, activity!!).execute()
                }
                "applied" ->{
                    val homeAdapter = HomeAdapter(projectList, activity!!)
                    recyclerView1!!.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
                    recyclerView1!!.adapter = homeAdapter
                    recyclerView1!!.setHasFixedSize(true)
                    recyclerView1!!.requestFocus()


                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class HomeAsync :AsyncTask<Void,Int,String>(){
        override fun doInBackground(vararg p0: Void?): String {
            val url = Const.CAST_URL+"home"
            return HttpUtility.getRequest(url)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!result.isNullOrEmpty()){
                val gson = Gson()
                try {
                    /*val type = object : TypeToken<HomeModel>() {}.type
                    val homeModel = gson.fromJson<HomeModel>(result, type)
                    if (homeModel != null) {
                        if (homeModel.featuredProjects.isNotEmpty()) {
                            val homeAdapter = HomeAdapter(homeModel.featuredProjects, activity!!)
                            recyclerView!!.layoutManager = LinearLayoutManager(activity)
                            recyclerView!!.adapter = homeAdapter
                            recyclerView!!.setHasFixedSize(true)
                            recyclerView!!.requestFocus()
                        }
                    }*/


                }
                catch (e: Exception) {
                    Log.i("HOME", e.message)
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
