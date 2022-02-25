package vidyawell.infotech.bsn.admin.Helpers;

public class Student_Helper {

    public String student_names;
    public String fother_name;
    public  String mobile;
    public  String class_name;
    public String student_image;
    public  String emp_code,SectionName,Atttype;


    public Student_Helper(String student_names, String fother_name, String mobile,String class_name, String student_image, String emp_code,String SectionName, String Atttype){
        this.student_names=student_names;
        this.fother_name=fother_name;
        this.mobile=mobile;
        this.class_name=class_name;
        this.student_image=student_image;
        this.emp_code=emp_code;
        this.SectionName=SectionName;
        this.Atttype=Atttype;

      //FullName,FatherName,MobileNo,ClassName,PhotoPath,StudentCode,SectionName
// TextView student_names,topic1,topic2,topic3,topic4;
//        TextView fother_name;
//        TextView mobile,Empcode,class_name;
//        RoundedImageView student_image;
//        TextView subject;
//        Button arrow;
    }





    public String getstudent_name() {
        return student_names;
    }

    public void setstudent_name(String student_name) {
        this.student_names = student_name;
    }

    public String getfother_name() {
        return fother_name;
    }
    public void setfother_name(String fother_name) {
        this.fother_name = fother_name;
    }

    public String getmobile() {
        return mobile;
    }
    public void setmobile(String mobile) {
        this. mobile = mobile;
    }

    public String getclass_name() {
        return class_name;
    }
    public void setclass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getstudent_image() {
        return student_image;
    }
    public void setstudent_image(String student_image) {
        this.student_image = student_image;
    }

    public String getstaffcode() {
        return emp_code;
    }

    public void setstaffcode(String emp_code) {
        this.emp_code = emp_code;
    }


    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String SectionName) {
        this.SectionName = SectionName;
    }
    public String getatttype() {
        return Atttype;
    }

    public void setatttype(String Atttype) {
        this.Atttype = Atttype;
    }

}
