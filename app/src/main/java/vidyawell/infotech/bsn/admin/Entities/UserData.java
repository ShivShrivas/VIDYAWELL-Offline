package vidyawell.infotech.bsn.admin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserData {

    public UserData(){}

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "userid")
    public String userId;

    @ColumnInfo(name = "loginTypeId")
    public String loginTypeId;

    @ColumnInfo(name = "sessionId")
    public String sessionId;

    @ColumnInfo(name = "fyId")
    public String fyId;

    @ColumnInfo(name = "GSMID")
    public String gSMID;

    @ColumnInfo(name = "LastLogin")
    public String lastLogin;

    @ColumnInfo(name = "CurrentLogin")
    public String currentLogin;

    @ColumnInfo(name = "Active")
    public String active;

    @ColumnInfo(name = "BranchName")
    public String branchName;

    @ColumnInfo(name = "BranchLogo")
    public String branchLogo;

    @ColumnInfo(name = "academicSession")
    public String academicSession;

    @ColumnInfo(name = "financialYear")
    public String financialYear;

    @ColumnInfo(name = "InstitutionType")
    public String InstitutionType;


    public UserData(String userId, String loginTypeId, String sessionId, String fyId, String gSMID, String lastLogin, String currentLogin, String active, String branchName, String branchLogo, String academicSession, String financialYear, String institutionType) {
        this.userId = userId;
        this.loginTypeId = loginTypeId;
        this.sessionId = sessionId;
        this.fyId = fyId;
        this.gSMID = gSMID;
        this.lastLogin = lastLogin;
        this.currentLogin = currentLogin;
        this.active = active;
        this.branchName = branchName;
        this.branchLogo = branchLogo;
        this.academicSession = academicSession;
        this.financialYear = financialYear;
        InstitutionType = institutionType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginTypeId() {
        return loginTypeId;
    }

    public void setLoginTypeId(String loginTypeId) {
        this.loginTypeId = loginTypeId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFyId() {
        return fyId;
    }

    public void setFyId(String fyId) {
        this.fyId = fyId;
    }

    public String getgSMID() {
        return gSMID;
    }

    public void setgSMID(String gSMID) {
        this.gSMID = gSMID;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(String currentLogin) {
        this.currentLogin = currentLogin;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchLogo() {
        return branchLogo;
    }

    public void setBranchLogo(String branchLogo) {
        this.branchLogo = branchLogo;
    }

    public String getAcademicSession() {
        return academicSession;
    }

    public void setAcademicSession(String academicSession) {
        this.academicSession = academicSession;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getInstitutionType() {
        return InstitutionType;
    }

    public void setInstitutionType(String institutionType) {
        InstitutionType = institutionType;
    }
}
