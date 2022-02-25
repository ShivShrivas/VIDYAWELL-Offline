package vidyawell.infotech.bsn.admin.Helpers;

public class Student_Leave_Application_Helper {

    public String LeaveFrom;
    public String LeaveTo;
    public String LeaveReason,Remarks,status,IsApproved,NoOfDaysApproved,Empname,EmpAttendanceId;

    public Student_Leave_Application_Helper(String LeaveFrom, String LeaveTo, String LeaveReason, String Remarks, String IsApproved, String NoOfDaysApproved, String Empname, String EmpAttendanceId){
        this.LeaveFrom=LeaveFrom;
        this.LeaveTo=LeaveTo;
        this.LeaveReason=LeaveReason;
        this.Remarks=Remarks;
        this.IsApproved=IsApproved;
        this.NoOfDaysApproved=NoOfDaysApproved;
        this.Empname=Empname;
        this.EmpAttendanceId=EmpAttendanceId;

    }

    //     ApprovedFrom,ApprovedTo


    public String getLeaveFrom() {
        return LeaveFrom;
    }

    public void setLeaveFrom(String LeaveFrom) {
        this. LeaveFrom = LeaveFrom;
    }

    public String getLeaveTo() {
        return LeaveTo;
    }

    public void setLeaveTo(String LeaveTo) {
        this. LeaveTo = LeaveTo;
    }

    public String getLeaveReason() {
        return LeaveReason;
    }

    public void setLeaveReason(String LeaveReason) {
        this. LeaveReason = LeaveReason;
    }


    public String getRemarks() {
        return Remarks;
    }
    public void setRemarks(String remarks) {
        this. Remarks = Remarks;
    }

    public String getIsApproved() {
        return IsApproved;
    }
    public void setIsApproved(String IsApproved) {
        this. IsApproved = IsApproved;
    }


    public String getNoOfDaysApproved() {
        return NoOfDaysApproved;
    }
    public void setNoOfDaysApproved(String NoOfDaysApproved) {
        this. NoOfDaysApproved = NoOfDaysApproved;
    }


    public String getEmpname() {
        return Empname;
    }
    public void setEmpname(String Empname) {
        this. Empname = Empname;
    }

    public String getEmpAttendanceId() {
        return EmpAttendanceId;
    }
    public void setEmpAttendanceId(String EmpAttendanceId) {
        this. EmpAttendanceId = EmpAttendanceId;
    }

}
