package vidyawell.infotech.bsn.admin.Helpers;

public class Complaint_View_Helper {
    // TextView student_name,father_name;
    public String student_name;
    public String father_name;
    public String StudentCode;
    public String Cont;
    public  boolean check_status;


    public Complaint_View_Helper(String student_name, String father_name,String StudentCode,String Cont,boolean check_status){
        this.student_name=student_name;
        this.father_name=father_name;
        this.StudentCode=StudentCode;
        this.Cont=Cont;
        this.check_status=check_status;

    }

    public String getstudent_name() {
        return student_name;
    }

    public void setstudent_name(String student_name) {
        this. student_name = student_name;
    }

    public String getfather_name() {
        return father_name;
    }
    public void setfather_name(String father_name) {
        this. father_name = father_name;
    }

    public String getStudentCode() {
        return StudentCode;
    }

    public void setStudentCode(String StudentCode) {
        this. StudentCode = StudentCode;
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
