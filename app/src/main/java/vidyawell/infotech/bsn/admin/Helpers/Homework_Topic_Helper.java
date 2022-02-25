package vidyawell.infotech.bsn.admin.Helpers;

public class Homework_Topic_Helper {

    public String TopicName;
    public String SectionId;
    public String EmployeeCode;
    public String ClassId;
    public String HomeWorkShowDate;


//  String EmployeeCode, String ClassId,String HomeWorkShowDate
    public Homework_Topic_Helper(String TopicName){
        this.TopicName=TopicName;
       // this.SectionId=SectionId;
        this.EmployeeCode=EmployeeCode;
        this.ClassId=ClassId;
        this.HomeWorkShowDate=HomeWorkShowDate;


        //TopicName,SectionId,EmployeeCode,ClassId

    }




    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String TopicName) {
        this. TopicName = TopicName;
    }

   /* public String getSectionId() {
        return SectionId;
    }
    public void setSectionId(String SectionId) {
        this. SectionId = SectionId;
    }*/

    public String getEmployeeCode() {
        return EmployeeCode;
    }
    public void setEmployeeCode(String EmployeeCode) {
        this. EmployeeCode = EmployeeCode;
    }

    public String getClassId() {
        return ClassId;
    }
    public void setClassId(String ClassId) {
        this. ClassId = ClassId;
    }

    public String getHomeWorkShowDate() {
        return HomeWorkShowDate;
    }
    public void setHomeWorkShowDate(String HomeWorkShowDate) {
        this. HomeWorkShowDate = HomeWorkShowDate;
    }
}
