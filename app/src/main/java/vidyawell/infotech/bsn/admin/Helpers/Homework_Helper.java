package vidyawell.infotech.bsn.admin.Helpers;

public class Homework_Helper {
    public String ClassName;
    public String SubjectName;
    public String SectionName;
    public String TopicName;
    public String VedioPath;
    public String TransId;
    public String VoucherNo;
    public String imagerplace;

///TransId,VoucherNo
    public Homework_Helper(String ClassName, String SubjectName, String SectionName,String TopicName,String VedioPath,String TransId,String VoucherNo,String imagerplace ){
        this.ClassName=ClassName;
        this.SubjectName=SubjectName;
        this.SectionName=SectionName;
        this.TopicName=TopicName;
        this.VedioPath=VedioPath;
        this.TransId=TransId;
        this.VoucherNo=VoucherNo;
        this.imagerplace=imagerplace;

    }


    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this. ClassName = ClassName;
    }

    public String getSubjectName() {
        return SubjectName;
    }
    public void setSubjectName(String SubjectName) {
        this. SubjectName = SubjectName;
    }


    public String getSectionName() {
        return SectionName;
    }
    public void setSectionName(String SectionName) {
        this. SectionName = SectionName;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String TopicName) {
        this. TopicName = TopicName;
    }


    public String getVedioPath() {
        return VedioPath;
    }

    public void setVedioPath(String VedioPath) {
        this. VedioPath = VedioPath;
    }

    //TransId
    public String getTransId() {
        return TransId;
    }

    public void setTransId(String TransId) {
        this. TransId = TransId;
    }

    public String getVoucherNo() {
        return VoucherNo;
    }

    public void setVoucherNo(String VoucherNo) {
        this. VoucherNo = VoucherNo;
    }

    public String getimagerplace() {
        return imagerplace;
    }

    public void setimagerplace(String imagerplace) {
        this. imagerplace = imagerplace;
    }

}
