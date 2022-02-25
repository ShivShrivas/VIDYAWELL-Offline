package vidyawell.infotech.bsn.admin.Helpers;

public class Gallery_Grid_Helper {
    //EventDate,Title,Description,AlbumIcon

    String EventDate;
    String Title;
    String Description;
    String AlbumIcon;
    String TransId;



    public Gallery_Grid_Helper(String EventDate, String Title, String Description, String AlbumIcon, String TransId ){
        this.EventDate=EventDate;
        this.Title=Title;
        this.Description=Description;
        this.AlbumIcon=AlbumIcon;
        this.TransId=TransId;
    }


    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String EventDate) {
        this. EventDate = EventDate;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this. Title = Title;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this. Description = Description;
    }

    public String getAlbumIcon() {
        return AlbumIcon;
    }

    public void setAlbumIcon(String AlbumIcon) {
        this. AlbumIcon = AlbumIcon;
    }

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String TransId) {
        this. TransId = TransId;
    }
}
