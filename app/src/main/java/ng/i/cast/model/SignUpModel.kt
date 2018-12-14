package ng.i.cast.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignUpModel {

    @SerializedName("email_status")
    var emailStatus: Any? = null
    @Expose
    var redirect: String? = null
    @SerializedName("sms_status")
    var smsStatus: String? = null
    @Expose
    var status: Boolean? = null
    @SerializedName("status_msg")
    var statusMsg: String? = null

}
