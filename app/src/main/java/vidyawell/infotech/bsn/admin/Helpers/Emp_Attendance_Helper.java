package vidyawell.infotech.bsn.admin.Helpers;

public class Emp_Attendance_Helper {
    public String EmployeeCode;
    public String EmpName;
    public String FatherName;
    public String AttendanceDate;
    public  String AttendanceAbbrId;
    public  String count;
    public  String EmployeeId;

    public Emp_Attendance_Helper(String EmployeeCode, String EmpName, String FatherName, String AttendanceDate, String AttendanceAbbrId,String count, String EmployeeId){
        this.EmployeeCode=EmployeeCode;
        this.EmpName=EmpName;
        this.FatherName=FatherName;
        this.AttendanceDate=AttendanceDate;
        this.AttendanceAbbrId=AttendanceAbbrId;
        this.count=count;
        this.EmployeeId=EmployeeId;

       // EmployeeCode
              //  EmpName
       // FatherName
              //  AttendanceDate
      //  AttendanceAbbrId
             //   EmployeeId

    }




    public String getEmployeeCode() {
        return EmployeeCode;
    }
    public void setEmployeeCode(String EmployeeCode) {
        this. EmployeeCode = EmployeeCode;
    }


    public String getEmpName() {
        return EmpName;
    }
    public void setEmpName(String EmpName) {
        this. EmpName = EmpName;
    }

    public String getFatherName() {
        return FatherName;
    }
    public void setFatherName(String FatherName) {
        this. FatherName = FatherName;
    }

    public String getAttendanceDate() {
        return AttendanceDate;
    }
    public void setAttendanceDate(String AttendanceDate) {
        this. AttendanceDate = AttendanceDate;
    }
    public String getAttendanceAbbrId() {
        return AttendanceAbbrId;
    }
    public void setAttendanceAbbrId(String AttendanceAbbrId) {
        this. AttendanceAbbrId = AttendanceAbbrId;
    }


   public String getcount() {
        return count;
    }
    public void setcount(String count) {
        this. count = count;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String EmployeeId) {
        this. EmployeeId = EmployeeId;
    }

}
