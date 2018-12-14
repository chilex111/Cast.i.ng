package ng.i.cast.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("auth_package_id")
    var authPackageId: String? = null
    @Expose
    var redirect: String? = null
    @Expose
    var role: String? = null
    @SerializedName("user_id")
    var userId: String? = null
    @SerializedName("user_image")
    var userImage: String? = null
    @Expose
     var status: Boolean ?= null
    @Expose
     var status_msg: String? = null

}
