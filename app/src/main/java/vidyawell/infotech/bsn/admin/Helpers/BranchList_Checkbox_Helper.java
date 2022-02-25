package vidyawell.infotech.bsn.admin.Helpers;

public class BranchList_Checkbox_Helper {
    private  String Brach_Name="";
    private  String Branch_ID="";
    private  String IsActive="";
    private  String chk="";
    private boolean selected;
    public  boolean check_status;


    public void setBrach_Name(String Brach_Name)
    {
        this.Brach_Name = Brach_Name;
    }
    public String getBrach_Name()
    {
        return this.Brach_Name;
    }

    public void setBranch_ID(String Branch_ID) {
        this.Branch_ID = Branch_ID;
    }
    public String getBranch_ID() {
        return this.Branch_ID;
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
