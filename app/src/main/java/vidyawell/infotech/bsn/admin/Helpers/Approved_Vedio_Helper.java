package vidyawell.infotech.bsn.admin.Helpers;

public class Approved_Vedio_Helper {
    public String ClassName;
    public String SubjectName;
    public String SectionName;
    public String TopicName;
    public String VedioPath;
    public String TransId;
    public String VoucherNo;
    public String IsVedioApproval;

///ClassName,SubjectName,SectionName,TopicName
    public Approved_Vedio_Helper(String ClassName, String SubjectName, String SectionName, String TopicName, String VedioPath,String TransId,String VoucherNo,String IsVedioApproval){
        this.ClassName=ClassName;
        this.SubjectName=SubjectName;
        this.SectionName=SectionName;
        this.TopicName=TopicName;
        this.VedioPath=VedioPath;
        this.TransId=TransId;
        this.VoucherNo=VoucherNo;
        this.IsVedioApproval=IsVedioApproval;


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
    //VedioPath
    public void setVedioPath(String VedioPath) {
        this. VedioPath = VedioPath;
    }


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
////IsVedioApproval

    public String getIsVedioApproval() {
        return IsVedioApproval;
    }

    public void setIsVedioApproval(String IsVedioApproval) {
        this. IsVedioApproval = IsVedioApproval;
    }
}
