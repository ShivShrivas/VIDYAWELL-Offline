package vidyawell.infotech.bsn.admin.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserCred {

    public UserCred(){}

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int uid;
    @NonNull
    @ColumnInfo(name = "SDKUrl")
    public String sDKUrl;

    @NonNull
    @ColumnInfo(name = "School_code")
    public String school_code;

    @NonNull
    @ColumnInfo(name = "UserId")
    public String userId;

    @NonNull
    @ColumnInfo(name = "Password")
    public String password;

    @NonNull
    @ColumnInfo(name = "Authorized")
    public String authorized;

    public UserCred( String sDKUrl,  String school_code,  String userId,  String password,  String authorized) {
        this.sDKUrl = sDKUrl;
        this.school_code = school_code;
        this.userId = userId;
        this.password = password;
        this.authorized = authorized;
    }


    @NonNull
    public String getsDKUrl() {
        return sDKUrl;
    }

    public void setsDKUrl(@NonNull String sDKUrl) {
        this.sDKUrl = sDKUrl;
    }

    @NonNull
    public String getSchool_code() {
        return school_code;
    }

    public void setSchool_code(@NonNull String school_code) {
        this.school_code = school_code;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(@NonNull String authorized) {
        this.authorized = authorized;
    }
}
