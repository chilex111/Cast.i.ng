
package ng.i.cast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AuditionList {

    @Expose
    private Boolean applied;
    @SerializedName("apply_link")
    private String applyLink;
    @Expose
    private String description;
    @SerializedName("dir_video_link")
    private String dirVideoLink;
    @Expose
    private String id;
    @Expose
    private String image;
    @Expose
    private String name;
    @SerializedName("notification_count")
    private String notificationCount;
    @SerializedName("notification_list")
    private java.util.List<Object> notificationList;
    @SerializedName("projectrole_user_id")
    private String projectroleUserId;
    @Expose
    private String status;

    public Boolean getApplied() {
        return applied;
    }

    public void setApplied(Boolean applied) {
        this.applied = applied;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirVideoLink() {
        return dirVideoLink;
    }

    public void setDirVideoLink(String dirVideoLink) {
        this.dirVideoLink = dirVideoLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(String notificationCount) {
        this.notificationCount = notificationCount;
    }

    public java.util.List<Object> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(java.util.List<Object> notificationList) {
        this.notificationList = notificationList;
    }

    public String getProjectroleUserId() {
        return projectroleUserId;
    }

    public void setProjectroleUserId(String projectroleUserId) {
        this.projectroleUserId = projectroleUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
