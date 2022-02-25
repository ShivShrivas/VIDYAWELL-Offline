package vidyawell.infotech.bsn.admin.Helpers;

public class Staff_Helper {

    public String staff_names;
    public String qulification;
    public String subject;
    public String staff_image;
    public  String mobile;
    public  String emp_code,Atttype;


    public Staff_Helper(String staff_names, String qulification, String subject, String staff_image,String mobile,String emp_code,String Atttype){
        this.staff_names=staff_names;
        this.qulification=qulification;
        this.subject=subject;
        this.staff_image=staff_image;
        this.mobile=mobile;
        this.emp_code=emp_code;
        this.Atttype=Atttype;

      //staff_names[i],qulification[i],subject[i],staff_image[i],mobile[i]

    }





    public String getstaff_names() {
        return staff_names;
    }

    public void setstaff_names(String staff_names) {
        this. staff_names = staff_names;
    }

    public String getqulification() {
        return qulification;
    }
    public void setqulification(String qulification) {
        this. qulification = qulification;
    }

    public String getsubject() {
        return subject;
    }
    public void setsubject(String subject) {
        this. subject = subject;
    }

    public String getstaff_image() {
        return staff_image;
    }
    public void setstaff_image(String staff_image) {
        this. staff_image = staff_image;
    }
    public String getmobile() {
        return mobile;
    }
    public void setmobile(String mobile) {
        this. mobile = mobile;
    }


    public String getstaffcode() {
        return emp_code;
    }

    public void setstaffcode(String emp_code) {
        this. emp_code = emp_code;
    }

    public String getatttype() {
        return Atttype;
    }

    public void setatttype(String Atttype) {
        this. Atttype = Atttype;
    }

}
