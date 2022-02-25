package vidyawell.infotech.bsn.admin.Helpers;

/**
 * Created by AmitAIT on 03-11-2018.
 */

public class EMP_Presenter_Helper {
    private  String FullName="";
    private  String EmployeeCode="";



    public void setFullName(String FullName) {
        this.FullName = FullName;
    }
    public String getFullName() {
        return this.FullName;
    }

    public void setEmployeeCode(String EmployeeCode) {
        this.EmployeeCode = EmployeeCode;
    }
    public String getEmployeeCode() {
        return this.EmployeeCode;
    }
}
