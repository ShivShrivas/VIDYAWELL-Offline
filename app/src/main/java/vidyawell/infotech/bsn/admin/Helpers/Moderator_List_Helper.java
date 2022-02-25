package vidyawell.infotech.bsn.admin.Helpers;

public class Moderator_List_Helper {
    public String EmployeeName;

    public String EmployeeCode;

    public String Cont;
    public  boolean check_status_moderator;

//EmployeeName,EmployeeCode
    //CorrespondanceEmail,CorrespondanceMobileNo,StudentName+" ("+StudentCode+")",StudentCode,i + 1 + " .",check_status

    public Moderator_List_Helper(String EmployeeName, String EmployeeCode, String Cont, boolean check_status_moderator){
        this.EmployeeName=EmployeeName;
        this.EmployeeCode=EmployeeCode;

        this.Cont=Cont;
        this.check_status_moderator=check_status_moderator;

    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String EmployeeName) {
        this. EmployeeName = EmployeeName;
    }


    public String getEmployeeCode() {
        return EmployeeCode;
    }

    public void setEmployeeCode(String EmployeeCode) {
        this. EmployeeCode = EmployeeCode;
    }





    public String getCont() {
        return Cont;
    }

    public void setCont(String Cont) {
        this. Cont = Cont;
    }


    public boolean getcheck_status() {
        return check_status_moderator;
    }

    public void setcheck_status(boolean check_status) {
        this. check_status_moderator = check_status;
    }
}
