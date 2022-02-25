package vidyawell.infotech.bsn.admin.Helpers;

public class DefaultModerator_Checkbox_Helper {
    private  String EmployeeName="";
    private  String EmployeeCode="";
    private  String IsActive="";
    private  String chk="";
    private boolean selected;
    private boolean check_status;



    public void setEmployeeName(String EmployeeName)
    {
        this.EmployeeName = EmployeeName;
    }
    public String getEmployeeName()
    {
        return this.EmployeeName;
    }

    public void setEmployeeCode(String EmployeeCode)
    {
        this.EmployeeCode = EmployeeCode;
    }
    public String getEmployeeCode()
    {
        return this.EmployeeCode;
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
    public boolean getcheck_status() {
        return check_status;
    }

    public void setcheck_status(boolean check_status) {
        this. check_status = check_status;
    }
}
