package vidyawell.infotech.bsn.admin.Helpers;

public class Notification_Helper {
    public String TransId;
    public String UserId;
    public String StudentPhoto;
    public String Title;
    public String font;
    public String Notification;
    public String link;
    public String letter;

    public Notification_Helper(String TransId, String UserId, String StudentPhoto, String Title, String font, String Notification, String link ,String letter){
        this.TransId=TransId;
        this.UserId=UserId;
        this.StudentPhoto=StudentPhoto;
        this.Title=Title;
        this.font=font;
        this.Notification=Notification;
        this.link=link;
        this.letter=letter;

        //TransId,UserId,StudentPhoto,Title,font,Notification,link


    }

    public String getletter() {
        return letter;
    }

    public void setletter(String letter) {
        this. letter = letter;
    }

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String TransId) {
        this. TransId = TransId;
    }

    public String getUserId() {
        return UserId;
    }
    public void setUserId(String UserId) {
        this. UserId = UserId;
    }
    public String getStudentPhoto() {
        return StudentPhoto;
    }
    public void setStudentPhoto(String StudentPhoto) {
        this. StudentPhoto = StudentPhoto;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this. Title = Title;
    }
    public String getfont() {
        return font;
    }

    public void setfont(String font) {
        this. font = font;
    }
    public String getNotification() {
        return Notification;
    }

    public void setNotification(String Notification) {
        this. Notification = Notification;
    }
    public String getlink() {
        return link;
    }

    public void setlink(String link) {
        this. link = link;
    }
}
