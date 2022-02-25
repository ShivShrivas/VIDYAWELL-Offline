package vidyawell.infotech.bsn.admin.Entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class UserPermissions {
    public UserPermissions(){}
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int uId;


    @ColumnInfo(name = "TimeTable")
    public String TimeTable;


    @ColumnInfo(name = "ClassAttendance")
    public String ClassAttendance;


    @ColumnInfo(name = "Homework")
    public String Homework;



    @ColumnInfo(name = "Calendar")
    public String Calendar;



    @ColumnInfo(name = "BusesRoute")
    public String BusesRoute;



    @ColumnInfo(name = "VideoGallery")
    public String VideoGallery;



    @ColumnInfo(name = "Visitors")
    public String Visitors;




    @ColumnInfo(name = "Complain")
    public String Complain;



    @ColumnInfo(name = "Notice")
    public String Notice;



    @ColumnInfo(name = "Circular")
    public String Circular;



    @ColumnInfo(name = "GuardianComplain")
    public String GuardianComplain;


    @ColumnInfo(name = "PhotoGallery")
    public String PhotoGallery;



    @ColumnInfo(name = "EmployeeLeaveApproval")
    public String EmployeeLeaveApproval;



    @ColumnInfo(name = "LiveClass")
    public String LiveClass;

    public UserPermissions( String timeTable,  String classAttendance,  String homework,  String calendar,  String busesRoute,  String videoGallery,  String visitors,  String complain,  String notice,  String circular,  String guardianComplain,  String photoGallery,  String employeeLeaveApproval,  String liveClass) {
        TimeTable = timeTable;
        ClassAttendance = classAttendance;
        Homework = homework;
        Calendar = calendar;
        BusesRoute = busesRoute;
        VideoGallery = videoGallery;
        Visitors = visitors;
        Complain = complain;
        Notice = notice;
        Circular = circular;
        GuardianComplain = guardianComplain;
        PhotoGallery = photoGallery;
        EmployeeLeaveApproval = employeeLeaveApproval;
        LiveClass = liveClass;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    @NonNull
    public String getTimeTable() {
        return TimeTable;
    }

    public void setTimeTable(@NonNull String timeTable) {
        TimeTable = timeTable;
    }

    @NonNull
    public String getClassAttendance() {
        return ClassAttendance;
    }

    public void setClassAttendance(@NonNull String classAttendance) {
        ClassAttendance = classAttendance;
    }

    @NonNull
    public String getHomework() {
        return Homework;
    }

    public void setHomework(@NonNull String homework) {
        Homework = homework;
    }

    @NonNull
    public String getCalendar() {
        return Calendar;
    }

    public void setCalendar(@NonNull String calendar) {
        Calendar = calendar;
    }

    @NonNull
    public String getBusesRoute() {
        return BusesRoute;
    }

    public void setBusesRoute(@NonNull String busesRoute) {
        BusesRoute = busesRoute;
    }

    @NonNull
    public String getVideoGallery() {
        return VideoGallery;
    }

    public void setVideoGallery(@NonNull String videoGallery) {
        VideoGallery = videoGallery;
    }

    @NonNull
    public String getVisitors() {
        return Visitors;
    }

    public void setVisitors(@NonNull String visitors) {
        Visitors = visitors;
    }

    @NonNull
    public String getComplain() {
        return Complain;
    }

    public void setComplain(@NonNull String complain) {
        Complain = complain;
    }

    @NonNull
    public String getNotice() {
        return Notice;
    }

    public void setNotice(@NonNull String notice) {
        Notice = notice;
    }

    @NonNull
    public String getCircular() {
        return Circular;
    }

    public void setCircular(@NonNull String circular) {
        Circular = circular;
    }

    @NonNull
    public String getGuardianComplain() {
        return GuardianComplain;
    }

    public void setGuardianComplain(@NonNull String guardianComplain) {
        GuardianComplain = guardianComplain;
    }

    @NonNull
    public String getPhotoGallery() {
        return PhotoGallery;
    }

    public void setPhotoGallery(@NonNull String photoGallery) {
        PhotoGallery = photoGallery;
    }

    @NonNull
    public String getEmployeeLeaveApproval() {
        return EmployeeLeaveApproval;
    }

    public void setEmployeeLeaveApproval(@NonNull String employeeLeaveApproval) {
        EmployeeLeaveApproval = employeeLeaveApproval;
    }

    @NonNull
    public String getLiveClass() {
        return LiveClass;
    }

    public void setLiveClass(@NonNull String liveClass) {
        LiveClass = liveClass;
    }
}
