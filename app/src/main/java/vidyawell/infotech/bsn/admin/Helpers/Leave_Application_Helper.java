package vidyawell.infotech.bsn.admin.Helpers;

public class Leave_Application_Helper {

    public String LeaveFrom;
    public String LeaveTo;
    public String LeaveReason,Remarks,IsApproved,NoOfDaysApproved,EmployeeCode,Empname,EmpAttendanceId;
    public String status;

    public Leave_Application_Helper(String LeaveFrom, String LeaveTo, String LeaveReason, String Remarks, String IsApproved, String NoOfDaysApproved,String EmployeeCode, String Empname, String EmpAttendanceId){
        this.LeaveFrom=LeaveFrom;
        this.LeaveTo=LeaveTo;
        this.LeaveReason=LeaveReason;
        this.Remarks=Remarks;
        this.IsApproved=IsApproved;
        this.NoOfDaysApproved=NoOfDaysApproved;
        this.EmployeeCode=EmployeeCode;
        this.Empname=Empname;
        this.EmpAttendanceId=EmpAttendanceId;

    }

    //    LeaveFrom,LeaveTo,LeaveReason,Remarks,IsApproved,NoOfDaysApproved,EmpName,EmpAttendanceId


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

    ////EmployeeCode
    public String getEmployeeCode() {
        return EmployeeCode;
    }
    public void setEmployeeCode(String EmployeeCode) {
        this. EmployeeCode = EmployeeCode;
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
