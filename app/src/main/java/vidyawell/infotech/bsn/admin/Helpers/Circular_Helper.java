package vidyawell.infotech.bsn.admin.Helpers;

public class Circular_Helper {

    public String TransId;
    public String CircularDate;
    public String Headline;
    public String Attachment;
    public String CircularDetails;

    public Circular_Helper(String TransId, String CircularDate, String Headline, String Attachment, String CircularDetails ){
        this.TransId=TransId;
        this.CircularDate=CircularDate;
        this.Headline=Headline;
        this.Attachment=Attachment;
        this.CircularDetails=CircularDetails;






    }

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String TransId) {
        this. TransId = TransId;
    }

    public String getCircularDate() {
        return CircularDate;
    }
    public void setCircularDate(String CircularDate) {
        this. CircularDate = CircularDate;
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
    public String getCircularDetails() {
        return CircularDetails;
    }

    public void setCircularDetails(String CircularDetails) {
        this. CircularDetails = CircularDetails;
    }
}
