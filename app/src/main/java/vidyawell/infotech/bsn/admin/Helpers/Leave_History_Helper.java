package vidyawell.infotech.bsn.admin.Helpers;

public class Leave_History_Helper {

    public String LeaveFrom;
    public String LeaveTo;
    public String LeaveReason,Remarks,status,IsApproved,NoOfDaysApproved,ApprovedFrom,ApprovedTo;

    public Leave_History_Helper(String LeaveFrom, String LeaveTo, String LeaveReason, String Remarks, String IsApproved, String NoOfDaysApproved,String ApprovedFrom,String ApprovedTo){
        this.LeaveFrom=LeaveFrom;
        this.LeaveTo=LeaveTo;
        this.LeaveReason=LeaveReason;
        this.Remarks=Remarks;
        this.IsApproved=IsApproved;
        this.NoOfDaysApproved=NoOfDaysApproved;
        this.ApprovedFrom=ApprovedFrom;
        this.ApprovedTo=ApprovedTo;

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


    public String getApprovedFrom() {
        return ApprovedFrom;
    }
    public void setApprovedFrom(String ApprovedFrom) {
        this. ApprovedFrom = ApprovedFrom;
    }

    public String getApprovedTo() {
        return ApprovedTo;
    }
    public void setApprovedTo(String ApprovedTo) {
        this. ApprovedTo = ApprovedTo;
    }

}
