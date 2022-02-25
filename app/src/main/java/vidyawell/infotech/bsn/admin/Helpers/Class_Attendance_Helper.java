package vidyawell.infotech.bsn.admin.Helpers;

public class Class_Attendance_Helper {
        public String stu_name;
        public String stu_code;
        public String stu_count;
        public  String FatherName,AttendanceDate,AttendanceAbbrId;
        public String StudentId;



        public Class_Attendance_Helper(String stu_name, String stu_code,String stu_count,String FatherName,String AttendanceDate,String AttendanceAbbrId,String StudentId ){
            this.stu_name=stu_name;
            this.stu_code=stu_code;
            this.stu_count=stu_count;
            this.FatherName=FatherName;
            this.AttendanceDate=AttendanceDate;
            this.AttendanceAbbrId=AttendanceAbbrId;
            this.StudentId=StudentId;



            //staff_names[i],qulification[i],subject[i],staff_image[i],mobile[i]

        }
    public String getAttendanceAbbrId() {
        return AttendanceAbbrId;
    }

    public void setAttendanceAbbrId(String AttendanceAbbrId) {
        this. AttendanceAbbrId = AttendanceAbbrId;
    }


    public String getAttendanceDate() {
        return AttendanceDate;
    }

    public void setAttendanceDate(String AttendanceDate) {
        this. AttendanceDate = AttendanceDate;
    }


    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String FatherName) {
        this. FatherName = FatherName;
    }


        public String getstu_name() {
            return stu_name;
        }

        public void setstu_name(String stu_name) {
            this. stu_name = stu_name;
        }


    public String getstu_code() {
        return stu_code;
    }

    public void setstu_code(String stu_code) {
        this. stu_code = stu_code;
    }

    public String getstu_count() {
        return stu_count;
    }

    public void setStu_count(String stu_count) {
        this. stu_count = stu_count;
    }
    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String StudentId) {
        this. StudentId = StudentId;
    }
}
