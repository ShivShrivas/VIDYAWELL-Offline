package vidyawell.infotech.bsn.admin.Helpers;

public class Noticeboard_Helper {

    public String TransId;
    public String NoticeDate;
    public String Headline;
    public String Attachment;
    public String NoticeDetails;

    public Noticeboard_Helper(String TransId, String NoticeDate, String Headline, String Attachment, String NoticeDetails ){
        this.TransId=TransId;
        this.NoticeDate=NoticeDate;
        this.Headline=Headline;
        this.Attachment=Attachment;
        this.NoticeDetails=NoticeDetails;


    }

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String TransId) {
        this. TransId = TransId;
    }

    public String getNoticeDate() {
        return NoticeDate;
    }
    public void setNoticeDate(String NoticeDate) {
        this. NoticeDate = NoticeDate;
    }
    public String getHeadline() {
        return Headline;
    }
    public void setHeadline(String Headline) {
        this. Headline = Headline;
    }
    public String getAttachment() {
        return Attachment;
    }

    public void setAttachment(String Attachment) {
        this. Attachment = Attachment;
    }
    public String getNoticeDetails() {
        return NoticeDetails;
    }

    public void setNoticeDetails(String NoticeDetails) {
        this. NoticeDetails = NoticeDetails;
    }
}
