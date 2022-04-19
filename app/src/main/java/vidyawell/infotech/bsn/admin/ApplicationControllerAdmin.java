package vidyawell.infotech.bsn.admin;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

/**
 * Created by AmitAIT on 15-09-2018.
 */

public class ApplicationControllerAdmin extends Application {

    private String userid;
    private String logintype;
    private String SeesionID;
    private String FyID;
    private String schoolCode;
    private String branchcode;
    private String active_code;
    private String date;
    private String Att_type;
    private String Emp_code;
    private String staff_names;
    private String mobile;
    private String subject;
    private String url;
    private String activityName;
    private String userName;
    private String User_desgn,school_name,school_logo;
    private String user_event,active_notify,purl;
    private String present_code;
    private  String absent_code;
    private  String leave_code;
    private  String school_type;
    private String TimeTable;
    private String ClassAttendance;
    private String Homework;
    private String Calendar;
    private String BusesRoute;
    private String VideoGallery;
    private String Visitors;
    private String Complain;
    private String Notice;
    private String Circular;
    private String GuardianComplain;
    private String Videogallery;
    private String PhotoGallery;
    private String Appversion;
    private String BranchLat;
    private String BranchLon;
    private String BranchRedius;
    private String DesignationId;
    private String DepartmentId;
    private String Leaveapplication;
    private String LiveClass;
    private String Servicesapplication;
    private String ProductTypeId;

    public String getProductTypeId() {
        return ProductTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        ProductTypeId = productTypeId;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void setServicesapplication(String Servicesapplication) {
        this.Servicesapplication = Servicesapplication;
    }
    public String getServicesapplication() {
        return Servicesapplication;
    }


    public void setLeaveapplication(String Leaveapplication) {
        this.Leaveapplication = Leaveapplication;
    }
    public String getLeaveapplication() {
        return Leaveapplication;
    }



    public void setLiveClass(String LiveClass) {
        this.LiveClass = LiveClass;
    }
    public String getLiveClass() {
        return LiveClass;
    }


    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
    }
    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDesignationId(String DesignationId) {
        this.DesignationId = DesignationId;
    }
    public String getDesignationId() {
        return DesignationId;
    }


    public void setBranchRedius(String BranchRedius) {
        this.BranchRedius = BranchRedius;
    }
    public String getBranchRedius() {
        return BranchRedius;
    }

    public void setBranchLon(String BranchLon) {
        this.BranchLon = BranchLon;
    }
    public String getBranchLon() {
        return BranchLon;
    }

    public void setBranchLat(String BranchLat) {
        this.BranchLat = BranchLat;
    }
    public String getBranchLat() {
        return BranchLat;
    }




    public void setAppversion(String Appversion) {
        this.Appversion = Appversion;
    }
    public String getAppversion() {
        return Appversion;
    }


    public String getabsent_code() {
        return absent_code;
    }
    public void setabsent_code(String absent_code) {
        this.absent_code = absent_code;
    }

    public String getpresent_code() {
        return present_code;
    }
    public void setpresent_code(String present_code) {
        this.present_code = present_code;
    }

    public String getleave_code() {
        return leave_code;
    }
    public void setleave_code(String leave_code) {
        this.leave_code = leave_code;
    }


    public String getschool_name() {
        return school_name;
    }
    public void setschool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getschool_logo() {
        return school_logo;
    }
    public void setschool_logo(String school_logo) {
        this.school_logo = school_logo;
    }

    public String getNotify() {
        return active_notify;
    }
    public void setNotify(String active_notify) {
        this.active_notify = active_notify;
    }

    public String getuser_event() {
        return user_event;
    }
    public void setuser_event(String user_event) {
        this.user_event = user_event;
    }

    public String getuserName() {
        return userName;
    }
    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getUser_desgn() {
        return User_desgn;
    }
    public void setUser_desgn(String User_desgn) {
        this.User_desgn = User_desgn;
    }



    public String getATTTYPE() {
        return Att_type;
    }
    public void setATTTYPE(String Att_type) {
        this.Att_type = Att_type;
    }


    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getUserID() {
        return userid;
    }
    public void setUserID(String userid) {
        this.userid = userid;
    }
    public String getLoginType() {
        return logintype;
    }
    public void setLoginType(String logintype) {
        this.logintype = logintype;
    }
    public String getSeesionID() {
        return SeesionID;
    }
    public void setSeesionID(String SeesionID) {
        this.SeesionID = SeesionID;
    }

    public String getFyID() {
        return FyID;
    }
    public void setFyID(String FyID) {
        this.FyID = FyID;
    }

    public String getschoolCode() {
        return schoolCode;
    }
    public void setschoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
    public String getBranchcode() {
        return branchcode;
    }
    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getActiveempcode() {
        return active_code;
    }
    public void setActiveempcode(String active_code) {
        this.active_code = active_code;
    }




    public String getappusername() {
        return staff_names;
    }
    public void setappusername(String staff_names) {
        this.staff_names = staff_names;
    }




    public String getappusermobile() {
        return mobile;
    }
    public void setappusermobile(String mobile) {
        this.mobile = mobile;
    }

    public String getappusersubject() {
        return subject;
    }
    public void setappusersubject(String subject) {
        this.subject = subject;
    }


    public String getappuserurl() {
        return url;
    }
    public void setappuserurl(String url) {
        this.url = url;
    }

    public String getProfilePIC() {
        return purl;
    }
    public void setProfilePIC(String purl) {
        this.purl = purl;
    }



    public String getActiviName() {
        return activityName;
    }
    public void setActiviName(String activityName) {
        this.activityName = activityName;
    }


    public String getschooltype() {
        return school_type;
    }
    public void setschooltype(String school_type) {
        this.school_type = school_type;
    }


    public String getTimeTable() {
        return TimeTable;
    }
    public void setTimeTable(String TimeTable) {
        this.TimeTable = TimeTable;
    }


    public String getClassAttendance() {
        return ClassAttendance;
    }
    public void setClassAttendance(String ClassAttendance) {
        this.ClassAttendance = ClassAttendance;
    }

    public String getHomework() {
        return Homework;
    }
    public void setHomework(String Homework) {
        this.Homework = Homework;
    }

    public String getCalendar() {
        return Calendar;
    }
    public void setCalendar(String Calendar) {
        this.Calendar = Calendar;
    }

    public String getBusesRoute() {
        return BusesRoute;
    }
    public void setBusesRoute(String BusesRoute) {
        this.BusesRoute = BusesRoute;
    }

    public String getVideoGallery() {
        return VideoGallery;
    }
    public void setVideoGallery(String VideoGallery) {
        this.VideoGallery = VideoGallery;
    }

    public String getVisitors() {
        return Visitors;
    }
    public void setVisitors(String Visitors) {
        this.Visitors = Visitors;
    }

    public String getComplain() {
        return Complain;
    }
    public void setComplain(String Complain) {
        this.Complain = Complain;
    }

    public String getNotice() {
        return Notice;
    }
    public void setNotice(String Notice) {
        this.Notice = Notice;
    }

    public String getCircular() {
        return Circular;
    }
    public void setCircular(String Circular) {
        this.Circular = Circular;
    }

    public String getGuardianComplain() {
        return GuardianComplain;
    }
    public void setGuardianComplain(String GuardianComplain) {
        this.GuardianComplain = GuardianComplain;
    }

    public String getVideogallery() {
        return Videogallery;
    }
    public void setVideogallery(String Videogallery) {
        this.Videogallery = Videogallery;
    }

    public String getPhotoGallery() {
        return PhotoGallery;
    }
    public void setPhotoGallery(String PhotoGallery) {
        this.PhotoGallery = PhotoGallery;
    }
}
