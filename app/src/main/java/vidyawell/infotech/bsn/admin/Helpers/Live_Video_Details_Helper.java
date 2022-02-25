package vidyawell.infotech.bsn.admin.Helpers;

public class Live_Video_Details_Helper {
    //EventDate,Title,Description,AlbumIcon

    String Title;
    String VedioAlbum;
    String VedioUrl;
    String TransId;
    String IsApproved;
    String IsLiveClassApproval;

//Title,VedioAlbum,VedioUrl,TransId,IsApproved

    public Live_Video_Details_Helper(String Title, String VedioAlbum, String VedioUrl, String TransId,String IsApproved,String IsLiveClassApproval ){
        this.Title=Title;
        this.VedioAlbum=VedioAlbum;
        this.VedioUrl=VedioUrl;
        this.TransId=TransId;
        this.IsApproved=IsApproved;
        this.IsLiveClassApproval=IsLiveClassApproval;
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

    ///IsLiveClassApproval

    public String getIsApproved() {
        return IsApproved;
    }

    public void setIsApproved(String IsApproved) {
        this. IsApproved = IsApproved;
    }


    public String getIsLiveClassApproval() {
        return IsLiveClassApproval;
    }

    public void setIsLiveClassApproval(String IsLiveClassApproval) {
        this. IsLiveClassApproval = IsLiveClassApproval;
    }
}
