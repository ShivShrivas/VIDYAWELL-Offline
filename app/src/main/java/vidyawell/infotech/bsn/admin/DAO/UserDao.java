package vidyawell.infotech.bsn.admin.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import vidyawell.infotech.bsn.admin.Entities.EventIdRelatedPics;
import vidyawell.infotech.bsn.admin.Entities.EventPicture;
import vidyawell.infotech.bsn.admin.Entities.MainUrlData;
import vidyawell.infotech.bsn.admin.Entities.UserCred;
import vidyawell.infotech.bsn.admin.Entities.UserData;
import vidyawell.infotech.bsn.admin.Entities.UserPermissions;

@Dao
public interface  UserDao {

    @Insert
    void insertUserCred(UserCred userCred);

    @Insert
    void insertUserPermission(UserPermissions userPermissions);

    @Insert
    void insertUserData(UserData userData);

    @Insert
    void insertUrlData(MainUrlData mainUrlData);

    @Insert
    abstract void insertAll(EventPicture eventPictures);

    @Query("DELETE FROM UserCred WHERE UserId= :userid")
    void deleteRowInUserCred(String userid);

    @Query("SELECT * FROM UserCred")
    List<UserCred> getUserCredFromDatabase();

    @Insert
    abstract void insertAllEventImages(EventIdRelatedPics eventPicturesList);

    @Query("SELECT * FROM UserData")
    List<UserData> getUserDataFromDatabase();

    @Query("SELECT * FROM UserPermissions")
    List<UserPermissions> getUserPermissionFromDatabase();

    @Query("DELETE FROM UserPermissions")
    void deleteUserPermissions();

    @Query("SELECT * FROM MainUrlData")
    List<MainUrlData> getMainUrlData();

}
