package vidyawell.infotech.bsn.admin.Helpers;

public class Student_Query_Helper {

    public String FullName;
    public String FatherName;
    public String MotherName;
    public String GuardianContactNo;
    public String StudentPhoto,StudentCode;


    public Student_Query_Helper(String FullName, String FatherName, String MotherName, String GuardianContactNo, String StudentPhoto,String StudentCode ){
        this.FullName=FullName;
        this.FatherName=FatherName;
        this.MotherName=MotherName;
        this.GuardianContactNo=GuardianContactNo;
        this.StudentPhoto=StudentPhoto;
        this.StudentCode=StudentCode;

        //FullName,FatherName,MotherName,GuardianContactNo,StudentPhoto

    }


    public String getStudentCode() {
        return StudentCode;
    }

    public void setStudentCode(String StudentCode) {
        this. StudentCode = StudentCode;
    }


    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this. FullName = FullName;
    }

    public String getFatherName() {
        return FatherName;
    }
    public void setFatherName(String FatherName) {
        this. FatherName = FatherName;
    }

    public String getMotherName() {
        return MotherName;
    }
    public void setMotherName(String MotherName) {
        this. MotherName = MotherName;
    }

    public String getGuardianContactNo() {
        return GuardianContactNo;
    }
    public void setGuardianContactNo(String GuardianContactNo) {
        this. GuardianContactNo = GuardianContactNo;
    }
    public String getStudentPhoto() {
        return StudentPhoto;
    }
    public void setStudentPhoto(String StudentPhoto) {
        this. StudentPhoto = StudentPhoto;
    }


}
