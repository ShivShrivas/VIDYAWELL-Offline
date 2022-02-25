package vidyawell.infotech.bsn.admin.ServerApis;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import vidyawell.infotech.bsn.admin.ApplicationControllerAdmin;

/**
 * Created by AmitAIT on 15-09-2018.
 */

public class ServerApiadmin{

    public static String MAIN_IPLINK;
    public static void seturl(String imageurl){
        MAIN_IPLINK=imageurl;
    }


   // public static String MAIN_IP="http://vidyawellservices.bsninfotech.org";
    public static String LOGO_API="http://masterapplication.bsninfotech.org";
    public static String API_SERVICE="http://masterservices.bsninfotech.org";

   /*  public static String MAIN_IP="http://192.168.0.215/SMSService";
     public static String MAIN_IPLINK="http://192.168.0.215/Sms";*/

     public static String LOGIN_API="/Service1.svc/mobile/MGetUserId";
     public static String FORGOT_PASSWORD= "/MailMessageService.svc/mobile/MRecoverPassword";


    public static String GETURL_API= "/Service1.svc/mobile/MGetUrl";
    public static String STAFFATT= "/AttendanceService.svc/mobile/MEmployeeAttendanceDateWiseList";
    public static String STUDENT_ATTEND= "/AttendanceService.svc/mobile/MStudentAttendanceDateWiseList";
    public static String STUDENT_ATTENDANCE= "/AttendanceService.svc/mobile/MStudentAttendanceDateWiseList";
    public static String STAFF_ATTENDANCE= "/AttendanceService.svc/mobile/MEmployeeAttendanceDateWiseList";
    public static String STAFF_DETAILS_COUNT="/AttendanceService.svc/mobile/MEmployeeAttendanceDateWiseList";
    public static String STUDENT_DETAILS_API="/AttendanceService.svc/mobile/MStudentAttendanceDateWiseList";
    public static String SCHOOL_CALENDAR="/EventCalendarService.svc/mobile/MGetEventCalenderList";
    public static String CIRCULAR_API_ONE="/GuardianDashBoardService.svc/mobile/MGetCircular";
    public static String NOTICEBOARD_API_ONE= "/GuardianDashBoardService.svc/mobile/MGetStudentNotice";
    public static String TIMETABLE_API= "/TimeTableService.svc/mobile/MGetDayWiseTimeTable";
    public static String ATTENDANCE_SELECT= "/Service1.svc/mobile/MClassMasterList";
    public static String HOMEWORK_INSERT_API= "/HomeworkService.svc/mobile/MGetAllHomeWork";
    public static String APPROVED_GET_API= "/HomeworkService.svc/mobile/MGetAllVedioHomeWork";
    public static String HOMEWORK_CLASSFILTER_API= "/HomeworkService.svc/mobile/MGetEmployeeClass";
    public static String STUDENTLIST_API= "/ExamService.svc/mobile/MGetExamType";
    public static String INSERTMARKS_API= "/ExamService.svc/mobile/MInsertEditExam";


    public static String SELECT_STREAM= "/Service1.svc/mobile/MStreamMasterList";
    public static String SELECT_MEDIUM="/Service1.svc/mobile/MMediumMasterList";
    public static String SELECT_SUBJECT= "/ExamService.svc/mobile/MGetExamType";
    public static String SELECT_DOCUMENT= "/DocumentService.svc/mobile/MGetDocument";
    public static String SELECT_DEPARTMENT="/EmployeeService.svc/mobile/MDepartmentMasterList";
    public static String IMAGE_GETAPI= "/DocumentService.svc/mobile/MGetEmpDocument";
    public static String IMAGE_SAVE="/DocumentService.svc/mobile/MInsertEditDeleteEmpDocument";


    public static String ATTENDANCE_SECTION= "/Service1.svc/mobile/MSectionMasterList";
    public static String ATTENDANCE_SELECTSECTION= "/Service1.svc/mobile/MSectionMasterList";
    public static String ATTENDANCE_SELECTBRANCH="/Service1.svc/mobile/MBranchMasterList";
    public static String SELECTBRANCH_NEW="/Service1.svc/mobile/MGetBranchStreambyClassId";
    public static String HOMEWORK_SELECTBRANCH="/HomeworkService.svc/mobile/MGetEmployeeSection";
    public static String LIVE_STUDENT_LIST="/StudentService.svc/mobile/MGetStudentList";
    public static String LIVE_MODERATOR_LIST="/LiveClassService.svc/mobile/MGetModerator";
    public static String LIVE_Class_Submit="/LiveClassService.svc/mobile/MInsertUpdateDeleteCreateMeeting";
    public static String SUBJECT_LIST= "/HomeworkService.svc/mobile/MSubjectEmpWise";
    //public static String SUBJECT_SUBMIT_API= "/HomeworkService.svc/mobile/MHomeworkInsert";
    public static String SUBJECT_SUBMIT_API= "/api/HomeWorkApi/UpdateHomework";
    public static String E_CLASS_INSERT_SUBMIT_API= "/HomeworkService.svc/mobile/MInsertUpdateDeleteLiveClass";
    public static String E_CLASS_GET_API= "/HomeworkService.svc/mobile/MGetLiveClassList";
    public static String UPDATE_VEDIO_API= "/HomeworkService.svc/mobile/MUpdateHomeworkApprove";

    public static String ATTENDANCE_EMPLOYEEMONTH="/AttendanceService.svc/mobile/MEmployeeAttendanceDateWiseList";
    public static String LEAVE_MASTER= "/AttendanceService.svc/mobile/MLeaveTypeMasterList";
    public static String LEAVE_History_API= "/AttendanceService.svc/mobile/MEmployeeAttendanceLeaveMasterList";
    public static String LEAVE_History_STUDENT_API= "/StudentService.svc/mobile/MGetAllStudentLeave";
    public static String LEAVE_APPLY= "/AttendanceService.svc/mobile/MInsertEditEmployeeAttendanceLeaveMaster";
    public static String LEAVE_APPLY_APPROVE_TEACHER= "/StudentService.svc/mobile/MSetLeaveApproval";
    public static String LEAVE_MASTER_DATA= "/AttendanceService.svc/mobile/MEmployeeAttendanceLeaveMasterList";
    public static String VISITOR_API= "/VisitorService.svc/mobile/MSearchEmployee";
    public static String VISITOR_APIUPDATE= "/VisitorService.svc/mobile/MSaveResponse";
    public static String ADMINDETAILS_API= "/Service1.svc/mobile/MGetUser";
    public static String STUDENT_LIST= "/AttendanceService.svc/mobile/MStudentAttendanceDateWiseList";
    public static String NOTIFICATION_API="/GuardianDashBoardService.svc/mobile/MGetNotification";
    public static String COMPLAINT_CCOUNT="/ComplainRegister.svc/mobile/MComplainRegisters";
    public static String LIVECLASS_LIST_API= "/LiveClassService.svc/mobile/MBindLiveClass";
    public static String LIVECLASS_VALIDTIME_API= "/LiveClassService.svc/mobile/MGetVaildTime";
    public static String LIVECLASS_startmiting= "/LiveClassService.svc/mobile/MStartMeeting";

    public static String COMPLAINT_DETAILSLIST="/ComplainRegister.svc/mobile/MComplainRegisters";
    public static String COMPLAINT_SEND= "/ComplainRegister.svc/mobile/MInsertDeleteComplainRegister";
    public static String STUDENT_ATTENDANCE_SUBMIT= "/AttendanceService.svc/mobile/MInsertEditStudentAttendanceDateWise";
    public static String EMPLOYEE_ATTENDANCE_SUBMIT= "/AttendanceService.svc/mobile/MInsertEditEmployeeAttendanceDateWise";
    public static String EMPLOYEE_ATTENDANCE_Update= "/AttendanceService.svc/mobile/MUpdateEmployeeAttendanceDateWiseThroughMobileApp";
    public static String STUDENT_ATTENDANCE_SUBMIT_UPDATE= "/AttendanceService.svc/mobile/MUpdateStudentAttendanceDateWiseThroughMobileApp";
    public static String API_ABBREVATION= "/AttendanceService.svc/mobile/MMasterAbbreviationList";
    public static String COMPLAINT_API_FIRST="/ComplainRegister.svc/mobile/MComplainRegisters";
    public static String Student_Query_API="/StudentService.svc/mobile/MStudentClassmateList";
    public static String HOMEWORK_TOPIC_API="/HomeworkService.svc/mobile/MHomeworkTopic";
    public static String HOMEWORK_TOPIC_Details_API="/HomeworkService.svc/mobile/MGetHomework";
    public static String HOMEWORK_FILE_DOWANLOAD_Details_API="/GuardianDashBoardService.svc/mobile/MGetSelectedHomeWork";
    public static String HOMEWORK_TOPIC_Submit_API="/HomeworkService.svc/mobile/MSubmittedHomework";
 //   public static String RMARK_API_SUBMIT="/HomeworkService.svc/mobile/SubmittedHomework";
    public static String SEND_NOTIFICATION_API="/HomeworkService.svc/mobile/MSendNotification";

    public static String EVENT_GALLERY_API= "/PhotoGalleryService.svc/mobile/MGetTopAlbum";
    public static String EVENT_FULLGALLERY_API="/PhotoGalleryService.svc/mobile/MGetPhotoPassTransId";
    public static String EVENT_GALLERY_VEDIO_API="/VideoGalleryService.svc/mobile/MGetTopVideoAlbum";
    public static String EVENT_FULLGALLERY_VEDIO_API="/VideoGalleryService.svc/mobile/MGetVideoPassTransId";
    public static String CHANGE_PASSWORD_API="/Service1.svc/mobile/McheckPassCode";
    public static String CHANGE_PASSWORD_NEW_API="/Service1.svc/mobile/MChangePassCode";
    public static String BUS_TYPE_API="/TransportService.svc/mobile/MGetVehicle";
    public static String BUS_ROUTE_API="/TransportService.svc/mobile/MGetRouteVehicleRouteMapping";
    public static String Teachers_API="/StudentService.svc/mobile/MSubjectTeacherList";
    public static String EMPLOYEE_API="/EmployeeService.svc/mobile/MGetEmployeeList";
    public static String STUDENTQUERYDATA_API="/ComplainRegister.svc/mobile/MConversationRegisters";
    public static String STUDENTQUERY_API="/ComplainRegister.svc/mobile/MInsertDeleteConversationRegister";
    public static String STUDENTQUERYDATA_API_CHAT="/ComplainRegister.svc/mobile/MTopConversationRegisters";
    public static String MOBILEATTENDANCE="/EmployeeAttendanceIMINoService.svc/mobile/MInsertEditDeleteEmpAttendanceIMINo";
    public static String PERMISSION_API="/Service1.svc/mobile/MMenuPermissionForMobile";
    ////object name
    public static String API_CONURL= "conurl";
    public static String API_SDKUrl= "SDKUrl";
    public static String API_SERV= API_SERVICE+"/ConHandShake.svc/mobile/HandShake";

//EVENT_GALLERY_VEDIO_API

    public static String FONT="OpenSans-Semibold.ttf";

    public static String FONT_DASHBOARD="OpenSans-Regular.ttf";
}
