package vidyawell.infotech.bsn.admin.Helpers;

public class EmployeeList_Helper {

    public String EmployeeCode;
    public String ContactNo;
    public String PhotoPath;
    public String FullName;
    public  String SubjectName;
   // public  String count;
    public  String IsClassTeacher;

    public EmployeeList_Helper(String EmployeeCode, String ContactNo, String PhotoPath, String FullName, String SubjectName, String IsClassTeacher){
        this.EmployeeCode=EmployeeCode;
        this.ContactNo=ContactNo;
        this.PhotoPath=PhotoPath;
        this.FullName=FullName;
        this.SubjectName=SubjectName;
       // this.count=count;
        this.IsClassTeacher=IsClassTeacher;

        // EmployeeCode,ContactNo,PhotoPath,FullName,SubjectName,IsClassTeacher

    }




    public String getEmployeeCode() {
        return EmployeeCode;
    }
    public void setEmployeeCode(String EmployeeCode) {
        this. EmployeeCode = EmployeeCode;
    }


    public String getContactNo() {
        return ContactNo;
    }
    public void setContactNo(String ContactNo) {
        this. ContactNo = ContactNo;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }
    public void setPhotoPath(String PhotoPath) {
        this. PhotoPath = PhotoPath;
    }

    public String getFullName() {
        return FullName;
    }
    public void setFullName(String FullName) {
        this. FullName = FullName;
    }
    public String getSubjectName() {
        return SubjectName;
    }
    public void setSubjectName(String SubjectName) {
        this. SubjectName = SubjectName;
    }


    /*public String getcount() {
        return count;
    }
    public void setcount(String count) {
        this. count = count;
    }
*/
    public String getIsClassTeacher() {
        return IsClassTeacher;
    }

    public void setIsClassTeacher(String IsClassTeacher) {
        this. IsClassTeacher = IsClassTeacher;
    }

}
