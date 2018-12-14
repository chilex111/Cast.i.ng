package ng.i.cast.api

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ng.i.cast.helper.AppUtils
import merchant.com.our.nextlounge.helper.Const
import ng.i.cast.helper.HttpUtility
import ng.i.cast.listener.ProjectListener
import ng.i.cast.model.AuditionModel

@SuppressLint("StaticFieldLeak")
class RecentProjectAsync(private val audition_Type: String?, private var context: Context) : AsyncTask<Void, Int, String>() {
    companion object {
        var projectListener : ProjectListener?= null
    }
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
                    projectListener!!.projectListener(auditionModel.list, auditionModel.status)
                }

            }catch (e:Exception){
                Log.i("AUDITION", e.message)
            }
        }
    }
}