package vidyawell.infotech.bsn.admin.Helpers;

public class Student_Checkbox_Helper {
    private  String StudentName="";
    private  String StudentCode="";
    private  String CorrespondanceEmail="";
    private  String CorrespondanceMobileNo="";
    private  String IsActive="";
    private  String chk="";
    private boolean selected;
    private boolean check_status;



    public void setStudentName(String StudentName)
    {
        this.StudentName = StudentName;
    }
    public String getStudentName()
    {
        return this.StudentName;
    }

    public void setStudentCode(String StudentCode)
    {
        this.StudentCode = StudentCode;
    }
    public String getStudentCode()
    {
        return this.StudentCode;
    }

    public void setCorrespondanceEmail(String CorrespondanceEmail)
    {
        this.CorrespondanceEmail = CorrespondanceEmail;
    }
    public String getCorrespondanceEmail()
    {
        return this.CorrespondanceEmail;
    }


    public void setCorrespondanceMobileNo(String CorrespondanceMobileNo)
    {
        this.CorrespondanceMobileNo = CorrespondanceMobileNo;
    }
    public String getCorrespondanceMobileNo()
    {
        return this.CorrespondanceMobileNo;
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
