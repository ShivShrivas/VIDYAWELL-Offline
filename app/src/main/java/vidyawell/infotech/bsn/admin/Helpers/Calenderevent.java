package vidyawell.infotech.bsn.admin.Helpers;

/**
 * Created by AmitAIT on 26-10-2018.
 */

public class Calenderevent {

    public String Event;
    public String StartDate;
    public String EndDate;
    public  String Activity;
    String IsRange;



    public Calenderevent(String Event, String StartDate,String EndDate, String Activity,String IsRange ){
        this.Event=Event;
        this.StartDate=StartDate;
        this.EndDate=EndDate;
        this.Activity=Activity;
        this.IsRange=IsRange;

    }
    public String getEvent() {
        return Event;
    }

    public void setEvent(String Event) {
        this. Event = Event;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this. StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this. EndDate = EndDate;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String Activity) {
        this. Activity = Activity;
    }

    public String getIsRange() {
        return IsRange;
    }

    public void setIsRange(String IsRange) {
        this. IsRange = IsRange;
    }
}
