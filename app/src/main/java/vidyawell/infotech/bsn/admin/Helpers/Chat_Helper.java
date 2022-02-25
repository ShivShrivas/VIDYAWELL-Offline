package vidyawell.infotech.bsn.admin.Helpers;

public class Chat_Helper {

    String msg;
    String datetime;
    String send_type;

    public Chat_Helper(String msg, String datetime, String send_type){
        this.msg=msg;
        this.datetime=datetime;
        this.send_type=send_type;
    }
    public String getmsg() {
        return msg;
    }
    public void setmsg(String msg) {
        this. msg = msg;
    }
    public String getdatetime() {
        return datetime;
    }
    public void setdatetime(String datetime) {
        this. datetime = datetime;
    }
    public String getsend_type() {
        return send_type;
    }
    public void setsend_type(String send_type) {
        this. send_type = send_type;
    }
}
