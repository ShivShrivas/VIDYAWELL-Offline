package vidyawell.infotech.bsn.admin.Helpers;

public class Student_List_Helper {
    public String student_name;

    public String StudentCode;
    public String CorrespondanceEmail;
    public String CorrespondanceMobileNo;
    public String Cont;
    public  boolean check_status;


    //CorrespondanceEmail,CorrespondanceMobileNo,StudentName+" ("+StudentCode+")",StudentCode,i + 1 + " .",check_status

    public Student_List_Helper(String student_name, String StudentCode,String CorrespondanceEmail,String CorrespondanceMobileNo,String Cont,boolean check_status){
        this.student_name=student_name;
        this.StudentCode=StudentCode;
        this.CorrespondanceEmail=CorrespondanceEmail;
        this.CorrespondanceMobileNo=CorrespondanceMobileNo;
        this.Cont=Cont;
        this.check_status=check_status;

    }

    public String getstudent_name() {
        return student_name;
    }

    public void setstudent_name(String student_name) {
        this. student_name = student_name;
    }


    public String getStudentCode() {
        return StudentCode;
    }

    public void setStudentCode(String StudentCode) {
        this. StudentCode = StudentCode;
    }



    public String getCorrespondanceEmail() {
        return CorrespondanceEmail;
    }

    public void setCorrespondanceEmail(String CorrespondanceEmail) {
        this. CorrespondanceEmail = CorrespondanceEmail;
    }


    public String getCorrespondanceMobileNo() {
        return CorrespondanceMobileNo;
    }

    public void setCorrespondanceMobileNo(String CorrespondanceMobileNo) {
        this. CorrespondanceMobileNo = CorrespondanceMobileNo;
    }


    public String getCont() {
        return Cont;
    }

    public void setCont(String Cont) {
        this. Cont = Cont;
    }


    public boolean getcheck_status() {
        return check_status;
    }

    public void setcheck_status(boolean check_status) {
        this. check_status = check_status;
    }
}
