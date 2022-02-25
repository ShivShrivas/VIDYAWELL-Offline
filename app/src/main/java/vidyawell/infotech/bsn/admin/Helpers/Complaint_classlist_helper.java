package vidyawell.infotech.bsn.admin.Helpers;

public class Complaint_classlist_helper {


    public String Complain;
    public String ComplainDate;
    public String StdName;
    public String FatherName;

    public Complaint_classlist_helper(String Complain, String ComplainDate, String StdName, String FatherName ){
        this.Complain=Complain;
        this.ComplainDate=ComplainDate;
        this.StdName=StdName;
        this.FatherName=FatherName;

    }


    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String FatherName) {
        this. FatherName = FatherName;
    }

    public String getStdName() {
        return StdName;
    }

    public void setStdName(String StdName) {
        this. StdName = StdName;
    }

    public String getComplainDate() {
        return ComplainDate;
    }

    public void setComplainDate(String ComplainDate) {
        this. ComplainDate = ComplainDate;
    }


    public String getComplain() {
        return Complain;
    }

    public void setComplain(String Complain) {
        this. Complain = Complain;
    }
}
