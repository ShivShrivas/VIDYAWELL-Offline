package vidyawell.infotech.bsn.admin.Helpers;

public class Complaint_Helper_history {

    public String Complain;
    public String ComplainDate;
    public String EmpName;


    //Complain,ComplainDate

    public Complaint_Helper_history(String Complain, String ComplainDate, String EmpName){
        this.Complain=Complain;
        this.ComplainDate=ComplainDate;
        this.EmpName=EmpName;


    }





    public String getComplain() {
        return Complain;
    }

    public void setComplain(String Complain) {
        this. Complain = Complain;
    }

    public String getComplainDate() {
        return ComplainDate;
    }
    public void setComplainDate(String ComplainDate) {
        this. ComplainDate = ComplainDate;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String EmpName) {
        this. EmpName = EmpName;
    }
}
