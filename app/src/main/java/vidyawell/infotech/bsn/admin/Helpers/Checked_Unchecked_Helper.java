package vidyawell.infotech.bsn.admin.Helpers;

public class Checked_Unchecked_Helper {

    public String SendNotification;
    public String StudentCode;
    public String StudentName;
    public String TopicName;
    public String SubmissionDate;

    public String IsSubmitted;
    public String TransId;
    public String VoucherNo;



    public Checked_Unchecked_Helper(String SendNotification, String StudentCode, String StudentName, String TopicName, String SubmissionDate, String IsSubmitted, String TransId, String VoucherNo ){
        this.SendNotification=SendNotification;
        this.StudentCode=StudentCode;
        this.StudentName=StudentName;
        this.TopicName=TopicName;
        this.SubmissionDate=SubmissionDate;

        this.IsSubmitted=IsSubmitted;
        this.TransId=TransId;
        this.VoucherNo=VoucherNo;


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
    ////VoucherNo
    public String getVoucherNo() {
        return VoucherNo;
    }
    public void setVoucherNo(String VoucherNo) {
        this. VoucherNo = VoucherNo;
    }


}
