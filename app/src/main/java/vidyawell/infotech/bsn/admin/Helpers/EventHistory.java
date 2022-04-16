package vidyawell.infotech.bsn.admin.Helpers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventHistory {

    @SerializedName("EventId")
    @Expose
    private String eventId;
    @SerializedName("EventName")
    @Expose
    private String eventName;
    @SerializedName("EventPhotoPath")
    @Expose
    private String eventPhotoPath;
    @SerializedName("EventDesc")
    @Expose
    private String eventDesc;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Long")
    @Expose
    private String _long;
    @SerializedName("EventDateTime")
    @Expose
    private String eventDateTime;
    @SerializedName("LastSyncDate")
    @Expose
    private String lastSyncDate;

    @SerializedName("SyncDate")
    @Expose
    private String SyncDate;
    @SerializedName("SchoolCode")
    @Expose
    private String schoolCode;
    @SerializedName("BranchCode")
    @Expose
    private String branchCode;
    @SerializedName("FYId")
    @Expose
    private Integer fYId;
    @SerializedName("SessionId")
    @Expose
    private String sessionId;
 @SerializedName("CreatedBy")
    @Expose
    private String CreatedBy;

 @SerializedName("UserName")
    @Expose
    private String UserName;

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPhotoPath() {
        return eventPhotoPath;
    }

    public void setEventPhotoPath(String eventPhotoPath) {
        this.eventPhotoPath = eventPhotoPath;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
    }

    public String getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(String lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }

    public String getSyncDate() {
        return SyncDate;
    }

    public void setSyncDate(String syncDate) {
        SyncDate = syncDate;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Integer getfYId() {
        return fYId;
    }

    public void setfYId(Integer fYId) {
        this.fYId = fYId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
