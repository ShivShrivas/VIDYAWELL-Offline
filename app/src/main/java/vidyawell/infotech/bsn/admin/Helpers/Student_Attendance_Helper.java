package vidyawell.infotech.bsn.admin.Helpers;

import java.util.List;

public class Student_Attendance_Helper {

    private List<StudentDetails> studentList;


    public List<StudentDetails> getStudentList() {
        return studentList;
    }
    public void setStudentList(List<StudentDetails> phoneList) {
        this.studentList = phoneList;
    }



    public class StudentDetails {

        private String StudentCode;
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
            return StudentCode;
        }
        public void setStudentCode(String StudentCode) {
            this.StudentCode = StudentCode;
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
