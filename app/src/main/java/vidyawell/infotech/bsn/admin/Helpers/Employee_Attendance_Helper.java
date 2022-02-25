package vidyawell.infotech.bsn.admin.Helpers;

import java.util.List;

public class Employee_Attendance_Helper {

    private List<EmployeeDetails> employeeList;


    public List<EmployeeDetails> getEmployeeList() {
        return employeeList;
    }
    public void setEmployeeList(List<EmployeeDetails> phoneList) {
        this.employeeList = phoneList;
    }



    public class EmployeeDetails {

        private String EmployeeCode;
        private String SrNo;
        private String AttendanceAbbrId;
        private String AttendanceDate;
        private String CreatedBy;


      /*  public StudentDetails(String StudentCode, String SrNo,String AttendanceAbbrId, String AttendanceDate,String CreatedBy ){
            this.StudentCode=StudentCode;
            this.SrNo=SrNo;
            this.AttendanceAbbrId=AttendanceAbbrId;
            this.AttendanceDate=AttendanceDate;
            this.CreatedBy=CreatedBy;

        }*/



        public String getStudentCode() {
            return EmployeeCode;
        }
        public void setStudentCode(String EmployeeCode) {
            this.EmployeeCode = EmployeeCode;
        }
        public String getSrNo() {
            return SrNo;
        }
        public void setSrNo(String SrNo) {

            this.SrNo = SrNo;
        }

        public String getAttendanceAbbrId() {
            return AttendanceAbbrId;
        }
        public void setAttendanceAbbrId(String AttendanceAbbrId) {

            this.AttendanceAbbrId = AttendanceAbbrId;
        }

        public String getAttendanceDate() {
            return AttendanceDate;
        }
        public void setAttendanceDate(String AttendanceDate) {

            this.AttendanceDate = AttendanceDate;
        }

        public String getCreatedBy() {
            return CreatedBy;
        }
        public void setCreatedBy(String CreatedBy) {

            this.CreatedBy = CreatedBy;
        }

    }
}
