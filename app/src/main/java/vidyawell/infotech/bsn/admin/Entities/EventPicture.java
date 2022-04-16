package vidyawell.infotech.bsn.admin.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EventPicture {
    public EventPicture(){}

    @ColumnInfo(name = "uId")
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int uId;

    @ColumnInfo(name = "eventId")
    @NonNull
    public String eventId;


    @ColumnInfo(name = "eventName")
    @NonNull
    public String eventName;

    @ColumnInfo(name = "eventDesc")
    @NonNull
    public  String eventDesc;

    public EventPicture(@NonNull String eventId, @NonNull String eventName, @NonNull String eventDesc) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDesc = eventDesc;
    }

    @NonNull
    public String getEventId() {
        return eventId;
    }

    public void setEventId(@NonNull String eventId) {
        this.eventId = eventId;
    }

    @NonNull
    public String getEventName() {
        return eventName;
    }

    public void setEventName(@NonNull String eventName) {
        this.eventName = eventName;
    }

    @NonNull
    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(@NonNull String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
