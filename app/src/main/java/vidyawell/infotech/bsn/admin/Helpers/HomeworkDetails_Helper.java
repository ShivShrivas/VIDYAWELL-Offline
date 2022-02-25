package vidyawell.infotech.bsn.admin.Helpers;

public class HomeworkDetails_Helper {

    public String SendNotification;
    public String StudentCode;
    public String StudentName;
    public String TopicName;
    public String SubmissionDate;

    public String IsSubmitted;
    public String TransId;
    public String IsHomeworkSubmitted;
    public String Remark;
    public String VoucherNo;
    public String FatherName;
    public String StudentId;

////FatherName,StudentId

    public HomeworkDetails_Helper(String SendNotification, String StudentCode, String StudentName, String TopicName, String SubmissionDate, String IsSubmitted, String TransId, String IsHomeworkSubmitted,String Remark,String VoucherNo,String FatherName,String StudentId ){
        this.SendNotification=SendNotification;
        this.StudentCode=StudentCode;
        this.StudentName=StudentName;
        this.TopicName=TopicName;
        this.SubmissionDate=SubmissionDate;
        this.IsSubmitted=IsSubmitted;
        this.TransId=TransId;
        this.IsHomeworkSubmitted=IsHomeworkSubmitted;
        this.Remark=Remark;
        this.VoucherNo=VoucherNo;
        this.FatherName=FatherName;
        this.StudentId=StudentId;


        //SendNotification,StudentCode,StudentName,TopicName,SubmissionDate,SendNotification1,IsSubmitted,TransId,VoucherNo

    }




    public String getSendNotification() {
        return SendNotification;
    }

    public void setSendNotification(String SendNotification) {
        this. SendNotification = SendNotification;
    }

    public String getStudentCode() {
        return StudentCode;
    }
    public void setStudentCode(String StudentCode) {
        this. StudentCode = StudentCode;
    }

    public String getStudentName() {
        return StudentName;
    }
    public void setStudentName(String StudentName) {
        this. StudentName = StudentName;
    }

    public String getTopicName() {
        return TopicName;
    }
    public void setTopicName(String TopicName) {
        this. TopicName = TopicName;
    }

    public String getSubmissionDate() {
        return SubmissionDate;
    }
    public void setHomeSubmissionDate(String SubmissionDate) {
        this. SubmissionDate = SubmissionDate;
    }
    ///SendNotification1



    ///IsSubmitted
    public String getIsSubmitted() {
        return IsSubmitted;
    }
    public void setIsSubmitted(String IsSubmitted) {
        this. IsSubmitted = IsSubmitted;
    }

    ////TransId
    public String getTransId() {
        return TransId;
    }
    public void setTransId(String TransId) {
        this. TransId = TransId;
    }
    ////Remark
    public String getIsHomeworkSubmitted() {
        return IsHomeworkSubmitted;
    }
    public void setIsHomeworkSubmitted(String IsHomeworkSubmitted) {
        this. IsHomeworkSubmitted = IsHomeworkSubmitted;
    }
    public String getRemark() {
        return Remark;
    }
    public void setRemark(String Remark) {
        this. Remark = Remark;
    }

  //  FatherName
    public String getVoucherNo() {
        return VoucherNo;
    }
    public void setVoucherNo(String VoucherNo) {
        this. VoucherNo = VoucherNo;
    }


    public String getFatherName() {
        return FatherName;
    }
    public void setFatherName(String FatherName) {
        this. FatherName = FatherName;
    }

    ///StudentId
    public String getStudentId() {
        return StudentId;
    }
    public void setStudentId(String StudentId) {
        this. StudentId = StudentId;
    }
}
