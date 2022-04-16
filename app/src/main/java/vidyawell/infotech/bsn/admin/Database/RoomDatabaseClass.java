package vidyawell.infotech.bsn.admin.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import vidyawell.infotech.bsn.admin.DAO.UserDao;
import vidyawell.infotech.bsn.admin.Entities.EventIdRelatedPics;
import vidyawell.infotech.bsn.admin.Entities.EventPicture;
import vidyawell.infotech.bsn.admin.Entities.MainUrlData;
import vidyawell.infotech.bsn.admin.Entities.UserCred;
import vidyawell.infotech.bsn.admin.Entities.UserData;
import vidyawell.infotech.bsn.admin.Entities.UserPermissions;

@Database(entities = {UserCred.class, UserPermissions.class, UserData.class, MainUrlData.class, EventIdRelatedPics.class, EventPicture.class},version = 1)
public abstract class RoomDatabaseClass extends RoomDatabase {

public abstract UserDao userDao();
}
