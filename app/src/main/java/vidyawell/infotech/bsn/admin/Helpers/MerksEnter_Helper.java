package vidyawell.infotech.bsn.admin.Helpers;

public class MerksEnter_Helper {

    String studentname;
    String studentcode;
    String Cont;
    String MinMarks;
    String MaxMarks;
    String practicalmarks;

    public MerksEnter_Helper(String studentname, String studentcode, String Cont,String MinMarks,String MaxMarks,String practicalmarks){
        this.studentname=studentname;
        this.studentcode=studentcode;
        this.Cont=Cont;
        this.MinMarks=MinMarks;
        this.MaxMarks=MaxMarks;
        this.practicalmarks=practicalmarks;
    }

    public String getpracticalmarks() {
        return practicalmarks;
    }
    public void setpracticalmarks(String practicalmarks) {
        this. practicalmarks = practicalmarks;
    }

    public String getMaxMarks() {
        return MaxMarks;
    }
    public void setMaxMarks(String MaxMarks) {
        this. MaxMarks = MaxMarks;
    }

    public String getMinMarkse() {
        return MinMarks;
    }
    public void setMinMarks(String MinMarks) {
        this. MinMarks = MinMarks;
    }

    public String getstudentname() {
        return studentname;
    }
    public void setstudentname(String studentname) {
        this. studentname = studentname;
    }

    public String getstudentcode() {
        return studentcode;
    }
    public void setstudentcode(String studentcode) {
        this. studentcode = studentcode;
    }

    public String getCount() {
        return Cont;
    }
    public void setCount(String Cont) {
        this. Cont = Cont;
    }
}
