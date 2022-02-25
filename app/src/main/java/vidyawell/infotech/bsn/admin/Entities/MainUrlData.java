package vidyawell.infotech.bsn.admin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MainUrlData {
    public MainUrlData(){}

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "BranchWebsite")
    public String branchWebsite;

    @ColumnInfo(name = "AVer")
    public String aVer;

    @ColumnInfo(name = "IVer")
    public String iVer;

    @ColumnInfo(name = "InstitutionType")
    public String institutionType;

    @ColumnInfo(name = "BranchLat")
    public String branchLat;

   @ColumnInfo(name = "BranchLog")
    public String branchLog;

   @ColumnInfo(name = "BranchRedius")
    public String branchRedius;

   @ColumnInfo(name = "IsHeadOffice")
    public String isHeadOffice;

   @ColumnInfo(name = "GUrl")
    public String gUrl;

    public MainUrlData(String branchWebsite, String aVer, String iVer, String institutionType, String branchLat, String branchLog, String branchRedius, String isHeadOffice, String gUrl) {
        this.branchWebsite = branchWebsite;
        this.aVer = aVer;
        this.iVer = iVer;
        this.institutionType = institutionType;
        this.branchLat = branchLat;
        this.branchLog = branchLog;
        this.branchRedius = branchRedius;
        this.isHeadOffice = isHeadOffice;
        this.gUrl = gUrl;
    }

    public String getBranchWebsite() {
        return branchWebsite;
    }

    public void setBranchWebsite(String branchWebsite) {
        this.branchWebsite = branchWebsite;
    }

    public String getaVer() {
        return aVer;
    }

    public void setaVer(String aVer) {
        this.aVer = aVer;
    }

    public String getiVer() {
        return iVer;
    }

    public void setiVer(String iVer) {
        this.iVer = iVer;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public String getBranchLat() {
        return branchLat;
    }

    public void setBranchLat(String branchLat) {
        this.branchLat = branchLat;
    }

    public String getBranchLog() {
        return branchLog;
    }

    public void setBranchLog(String branchLog) {
        this.branchLog = branchLog;
    }

    public String getBranchRedius() {
        return branchRedius;
    }

    public void setBranchRedius(String branchRedius) {
        this.branchRedius = branchRedius;
    }

    public String getIsHeadOffice() {
        return isHeadOffice;
    }

    public void setIsHeadOffice(String isHeadOffice) {
        this.isHeadOffice = isHeadOffice;
    }

    public String getgUrl() {
        return gUrl;
    }

    public void setgUrl(String gUrl) {
        this.gUrl = gUrl;
    }
}
