package vidyawell.infotech.bsn.admin.Helpers;

public class Gallery_Grid_Vedio_Helper {
    //EventDate,Title,Description,AlbumIcon

    String Title;
    String VedioAlbum;
    String VedioUrl;
    String TransId;

//Title,VedioAlbum,VedioUrl

    public Gallery_Grid_Vedio_Helper( String Title, String VedioAlbum, String VedioUrl, String TransId ){
        this.Title=Title;
        this.VedioAlbum=VedioAlbum;
        this.VedioUrl=VedioUrl;
        this.TransId=TransId;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this. Title = Title;
    }




    public String getVedioAlbum() {
        return VedioAlbum;
    }

    public void setVedioAlbum(String VedioAlbum) {
        this. VedioAlbum = VedioAlbum;
    }

    public String getVedioUrl() {
        return VedioUrl;
    }

    public void setVedioUrl(String VedioUrl) {
        this. VedioUrl = VedioUrl;
    }

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String TransId) {
        this. TransId = TransId;
    }
}
