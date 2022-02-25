package vidyawell.infotech.bsn.admin.Helpers;

public class StreamList_Checkbox_Helper {
    private  String StreamName="";
    private  String StreamID="";
    private  String IsActive="";
    private  String chk="";
    private boolean selected;
    private boolean check_status;



    public void setStreamName(String StreamName)
    {
        this.StreamName = StreamName;
    }
    public String getStreamName()
    {
        return this.StreamName;
    }

    public void setStreamID(String StreamID)
    {
        this.StreamID = StreamID;
    }
    public String getStreamID()
    {
        return this.StreamID;
    }


    public void setIsActive(String IsActive) {
        this.IsActive = IsActive;
    }
    public String getIsActive() {
        return this.IsActive;
    }


    public void setchk(String chk) {
        this.chk = chk;
    }
    public String getchk() {
        return this.chk;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public void setcheck_status(boolean check_status) {
        this. check_status = check_status;
    }
}
