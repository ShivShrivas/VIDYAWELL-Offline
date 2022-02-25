package vidyawell.infotech.bsn.admin.Helpers;

public class Conversation_Helper {

    String msg;
    String datetime;
    String send_type;
    String ConversationId;

    public Conversation_Helper(String msg, String datetime, String send_type, String ConversationId){
        this.msg=msg;
        this.datetime=datetime;
        this.send_type=send_type;
        this.ConversationId=ConversationId;
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

    public String getConversationId() {
        return ConversationId;
    }
    public void setConversationId(String ConversationId) {
        this. ConversationId = ConversationId;
    }
}
