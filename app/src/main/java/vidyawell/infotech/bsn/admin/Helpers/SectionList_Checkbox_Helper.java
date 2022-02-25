package vidyawell.infotech.bsn.admin.Helpers;

public class SectionList_Checkbox_Helper {
    private  String SecName="";
    private  String SecID="";
    private  boolean IsActive;
    private  boolean chk;
    private boolean selected;
    public  boolean check_status;


    public void setSecName(String SecName)
    {
        this.SecName = SecName;
    }
    public String getSecName()
    {
        return this.SecName;
    }

    public void setSecID(String SecID) {
        this.SecID = SecID;
    }
    public String getSecID() {
        return this.SecID;
    }




    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }
    public boolean getIsActive() {
        return this.IsActive;
    }

    public void setChk(boolean chk) {
        this.chk = chk;
    }
    public boolean getChk() {
        return this.chk;
    }



    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getcheck_status() {
        return check_status;
    }

    public void setcheck_status(boolean check_status) {
        this. check_status = check_status;
    }
}
