package vidyawell.infotech.bsn.admin.Helpers;

public class Complaint_Helper {

//ClassName,Compcount,ClassId,SectionId,SectionName

    public String class_name;
    public String comp_count;
    public String class_id,SectionId,SectionName;

    public Complaint_Helper(String class_name, String comp_count, String class_id,String SectionId,String SectionName){
        this.class_name=class_name;
        this.comp_count=comp_count;
        this.class_id=class_id;
        this.SectionId=SectionId;
        this.SectionName=SectionName;
    }

    public String getclass_name() {
        return class_name;
    }

    public void setclass_name(String class_name) {
        this. class_name = class_name;
    }

    public String getcomp_count() {
        return comp_count;
    }

    public void setcomp_count(String comp_count) {
        this. comp_count = comp_count;
    }

    public String getclass_id() {
        return class_id;
    }

    public void setclass_id(String class_id) {
        this. class_id = class_id;
    }


    public String getSectionId() {
        return SectionId;
    }
    public void setSectionId(String SectionId) {
        this. SectionId = SectionId;
    }

    public String getSectionName() {
        return SectionName;
    }
    public void setSectionName(String SectionName) {
        this. SectionName = SectionName;
    }
}
