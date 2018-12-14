
package ng.i.cast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AuditionModel {

    @Expose
    private java.util.List<AuditionList> list;
    @SerializedName("page_links")
    private Object pageLinks;
    @Expose
    private Boolean status;

    public java.util.List<AuditionList> getList() {
        return list;
    }

    public void setList(java.util.List<AuditionList> list) {
        this.list = list;
    }

    public Object getPageLinks() {
        return pageLinks;
    }

    public void setPageLinks(Object pageLinks) {
        this.pageLinks = pageLinks;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
