package vidyawell.infotech.bsn.admin.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EventIdRelatedPics {

    public EventIdRelatedPics(){}

    @ColumnInfo(name = "uId")
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int uId;

    @ColumnInfo(name = "eventId")
    @NonNull
    public String eventId;

    @ColumnInfo(name = "eventPictureList")
    @NonNull
    public String eventPictureList;

    public EventIdRelatedPics(@NonNull String eventId, @NonNull String eventPictureList) {
        this.eventId = eventId;
        this.eventPictureList = eventPictureList;
    }

    @NonNull
    public String getEventId() {
        return eventId;
    }

    public void setEventId(@NonNull String eventId) {
        this.eventId = eventId;
    }

    @NonNull
    public String getEventPictureList() {
        return eventPictureList;
    }

    public void setEventPictureList(@NonNull String eventPictureList) {
        this.eventPictureList = eventPictureList;
    }
}
