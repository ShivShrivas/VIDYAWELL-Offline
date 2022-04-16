package vidyawell.infotech.bsn.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Chat_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Chat_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Chat_Activity extends AppCompatActivity {

    ListView list_chat;
    Chat_Adapter chatAdapter;
    Chat_Helper chatHelper;
    List<Chat_Helper> chat_helpers;
    String []count={"2","1","2","1","2","1","2","1","2"};
    String []msg={"hi","hello","i am fine","what hr u doing","nothing important situation around location simulation, background tasks and notifications could benefit significantly from quite ","ok","come for shopping"," situation around location simulation, background tasks and notifications could benefit significantly from quite ","ok ","ok thanks","welcome","0","2","1","2","1","2","1","2","1","2","1","2","1"};
    String []datetime={"10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","10:00 am 12-01-2019","2","1","2","1","2","1"};
    ApplicationControllerAdmin applicationController;
    String StudentId,Conversation;
    EditText edit_chat;
    String Conversion_name,Conversion_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        applicationController = (ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent in=getIntent();
        String t_name=in.getStringExtra("FullName");
        StudentId=in.getStringExtra("Code");
        Conversion_name=in.getStringExtra("Conversation");
        Conversion_id=in.getStringExtra("ConversationId");
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout)findViewById(R.id.relative_chat));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Chat");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        list_chat=(ListView)findViewById(R.id.list_chat);
        edit_chat=(EditText)findViewById(R.id.edit_chat) ;
       /* chat_helpers = new ArrayList<Chat_Helper>();
        for (int i = 0; i < count.length; i++) {
            chatHelper = new Chat_Helper(msg[i],datetime[i],count[i]);
            chat_helpers.add(chatHelper);
        }*/


        Button btn_send=(Button)findViewById(R.id.button_qsend);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_chat.getText().toString().length()>0){
                    Conversation=edit_chat.getText().toString();
                    new SendStudentQuery().execute();
                    edit_chat.setText("");
                   /* chatHelper = new Chat_Helper(edit_chat.getText().toString(),"12-01-2019","1");
                    chat_helpers.add(chatHelper);
                    chatAdapter = new Chat_Adapter(getApplicationContext(),R.layout.chat_list_item_send, chat_helpers);
                    list_chat.setAdapter(chatAdapter);
                    chatAdapter.notifyDataSetChanged();
                    list_chat.setSelection(list_chat.getAdapter().getCount()-1);
                    edit_chat.setText("");*/
                }else{
                     Toast.makeText(getApplicationContext(), "Enter your message", Toast.LENGTH_SHORT).show();
                }
            }
        });
        new StudentQuery().execute();
    }
    private class StudentQuery extends AsyncTask<String, String, Integer> {
       // ProgressDialog progressDialog = new ProgressDialog(Chat_Activity.this);
        @Override
        protected void onPreExecute() {
          //  progressDialog = ProgressDialog.show(Chat_Activity.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STUDENTQUERYDATA_API_CHAT,Para(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        chat_helpers = new ArrayList<Chat_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String Conversation=object.getString("QueryMessage");
                            String CMode=object.getString("CMode");
                            String CreatedOn=object.getString("CreatedOn");
                               chatHelper = new Chat_Helper(Conversation,CreatedOn,CMode);
                                chat_helpers.add(chatHelper);
                                status=1;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-1;
                    }
                }else{
                    status=-2;
                }
            }else{
                status=-1;
            }

            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
           // progressDialog.dismiss();
            switch (s){
                case 1:
                    chatAdapter = new Chat_Adapter(getApplicationContext(),R.layout.chat_list_item_send, chat_helpers);
                    list_chat.setAdapter(chatAdapter);
                    chatAdapter.notifyDataSetChanged();
                    list_chat.setSelection(list_chat.getAdapter().getCount()-1);
                    break;
                case -2:

                    //  Toast.makeText(getApplicationContext(),"Teacher Data not found",Toast.LENGTH_LONG).show();
                    break;
                case -1:

                    // Toast.makeText(getApplicationContext(),"User Credentials are not Valid",Toast.LENGTH_LONG).show();

                    break;
            }
        }
    }
    public String Para( String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {

            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("Action", "7");
            jsonParam.put("ConversationId", Conversion_id);
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    private class SendStudentQuery extends AsyncTask<String, String, Integer> {
        // ProgressDialog progressDialog = new ProgressDialog(Chat_Activity.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Chat_Activity.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STUDENTQUERY_API,Parasend(StudentId,applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>=1){
                    status=1;

                }else{
                    status=-2;
                }
            }else{
                status=-1;
            }

            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            // progressDialog.dismiss();
            switch (s){
                case 1:
                    new StudentQuery().execute();
                    break;
                case -2:

                    //  Toast.makeText(getApplicationContext(),"Teacher Data not found",Toast.LENGTH_LONG).show();
                    break;
                case -1:

                    // Toast.makeText(getApplicationContext(),"User Credentials are not Valid",Toast.LENGTH_LONG).show();

                    break;
            }
        }
    }
    public String Parasend(String StudentCode, String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("StudentId", StudentCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("Action", "8");
            jsonParam.put("TeacherId", applicationController.getActiveempcode());
            jsonParam.put("Code", "T");
            jsonParam.put("Conversation", Conversation);
            jsonParam.put("ConversationId", Conversion_id);
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }





}
