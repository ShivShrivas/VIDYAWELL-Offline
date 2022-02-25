package vidyawell.infotech.bsn.admin.Helpers;

public class LiveClass_Helper {


    public String MeetingId;
    public String MeetingName;
    public String Topic;
    public String Description;
    public String StartDate;
    public String StartTime;
    public String Duration;
    public String UserMsg;
    public String url;
    public String BackUrl;
    public String Moderator;
    public String Presenter;
   // public String RunningMeetingUrl;
   public String Name;
    public String ClassName;
    public String SectionName;

    public boolean IsActive;


    public LiveClass_Helper(String MeetingId, String MeetingName, String Topic, String Description,String StartDate,String StartTime,String Duration,String UserMsg,String url,String BackUrl,String Moderator,String Presenter,String Name,String ClassName,String SectionName,boolean IsActive  ){

        this.MeetingId=MeetingId;
        this.MeetingName=MeetingName;
        this.Topic=Topic;
        this.Description=Description;
        this.StartDate=StartDate;
        this.StartTime=StartTime;
        this.Duration=Duration;
        this.UserMsg=UserMsg;
        this.url=url;
        this.BackUrl=BackUrl;
        this.Moderator=Moderator;
        this.Presenter=Presenter;
        this.Name=Name;
        this.ClassName=ClassName;
        this.SectionName=SectionName;

        this.IsActive=IsActive;
        //this.RunningMeetingUrl=RunningMeetingUrl;
//MeetingId,MeetingName,Topic,Description,StartDate,StartTime,Duration,UserMsg,url,BackUrl,RunningMeetingUrl
//Moderator,Presenter
    }



    public String getMeetingId() {
        return MeetingId;
    }
    public void setMeetingId(String MeetingId) {
        this. MeetingId = MeetingId;
    }
    public String getMeetingName() {
        return MeetingName;
    }
    public void setMeetingName(String MeetingName) {
        this. MeetingName = MeetingName;
    }
    public String getTopic() {
        return Topic;
    }

    public void setTopic(String Topic) {
        this. Topic = Topic;
    }
    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this. Description = Description;
    }

    /////StartDate

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this. StartDate = StartDate;
    }

    //StartTime
    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this. StartTime = StartTime;
    }

    //Duration

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String Duration) {
        this. Duration = Duration;
    }

    /////UserMsg//
    public String getUserMsg() {
        return UserMsg;
    }

    public void setUserMsg(String UserMsg) {
        this. UserMsg = UserMsg;
    }

    /////url////

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this. url = url;
    }

    ///BackUrl///

    public String getBackUrl() {
        return BackUrl;
    }

    public void setBackUrl(String BackUrl) {
        this. BackUrl = BackUrl;
    }

    ///Presenter/////


    public String getModerator() {
        return Moderator;
    }

    public void setModerator(String Moderator) {
        this. Moderator = Moderator;
    }

    public String getPresenter() {
        return Presenter;
    }

    public void setPresenter(String Presenter) {
        this. Presenter = Presenter;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this. Name = Name;
    }

    //////ClassName
    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this. ClassName = ClassName;
    }


    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String SectionName) {
        this. SectionName = SectionName;
    }


    ///IsActive

    public boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean IsActive) {
        this. IsActive = IsActive;
    }
}
