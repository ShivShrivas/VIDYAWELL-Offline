package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Homework_Topic_Submit_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Homework_Topic_Submit_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Homework_notificatin_submit extends AppCompatActivity implements Homework_Topic_Submit_Adapter.customButtonListener,Homework_Topic_Submit_Adapter.customButtonListener1 {

    EditText topic_search;
    ApplicationControllerAdmin applicationControllerAdmin;
    Homework_Topic_Submit_Adapter adapter;
    ListView homework_submit_list;
    List<Homework_Topic_Submit_Helper> homework_topic_submit_helpers;
    Homework_Topic_Submit_Helper item;
    RelativeLayout homework_topic_layout;
    String SectionId,ClassId,TopicName,EmployeeCode;
    String SendNotification,TransId,VoucherNo,IsSubmitted,StudentCode;
    String submit_id="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_notificatin_submit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin = (ApplicationControllerAdmin) getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.homework_topic_layout));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Homework Submitted History");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);

        homework_topic_layout=(RelativeLayout)findViewById(R.id.homework_topic_layout);
        homework_submit_list=(ListView)findViewById(R.id.homework_submit_list);


        Intent intent=getIntent();
        SectionId=intent.getStringExtra("SectionId");
        ClassId=intent.getStringExtra("ClassId");
        TopicName=intent.getStringExtra("TopicName");
        //EmployeeCode=intent.getStringExtra("EmployeeCode");
       // intent.putExtra("TopicName",Base64.encodeToString(TopicName.toString().getBytes(), Base64.DEFAULT));
        new Homework_Topic_SubmitAPI().execute();

    }

    @Override
    public void onButtonClickListner(int position, Homework_Topic_Submit_Helper value) {
        submit_id="1";
        TransId=value.getTransId();
        StudentCode=value.getStudentCode();
        TopicName=value.getTopicName();
        VoucherNo=value.getVoucherNo();
        //submit_id = value.getIsSubmitted();
        new Topic_SubmitAPI().execute();

    }

    @Override
    public void onButtonClickListner1(int position, Homework_Topic_Submit_Helper value) {
        submit_id="0";
        TransId=value.getTransId();
        StudentCode=value.getStudentCode();
        TopicName=value.getTopicName();
        VoucherNo=value.getVoucherNo();
        new Topic_SubmitAPI().execute();
    }


    private class Homework_Topic_SubmitAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Homework_notificatin_submit.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Homework_notificatin_submit.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.HOMEWORK_TOPIC_Details_API,Para(applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            //String api=ServerApiadmin.HOMEWORK_TOPIC_API;
            String test = applicationControllerAdmin.getServicesapplication();
            if(response!=null){
               // response=response.replace("\r","");
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        homework_topic_submit_helpers = new ArrayList<Homework_Topic_Submit_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   SendNotification=object.getString("SendNotification");
                            String   StudentCode=object.getString("StudentCode");
                            String   StudentName=object.getString("StudentName");
                            String   TopicName=object.getString("TopicName");
                            String   SubmissionDate=object.getString("SubmissionDate");
                            String   IsSubmitted=object.getString("SubmittedValue");
                            String   TransId=object.getString("TransId");
                            String   VoucherNo=object.getString("VoucherNo");

                            item = new Homework_Topic_Submit_Helper(SendNotification,StudentCode,StudentName,TopicName,SubmissionDate,IsSubmitted,TransId,VoucherNo);
                            homework_topic_submit_helpers.add(item);
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    //  homework_topic_list=(ListView)findViewById(R.id.homework_topic_list);
                    //teachers_list.setVisibility(View.VISIBLE);
                    adapter = new Homework_Topic_Submit_Adapter(getApplicationContext(),R.layout.item_homework_notification_submit, homework_topic_submit_helpers);
                    homework_submit_list.setAdapter(adapter);
                    adapter.setCustomButtonListner(Homework_notificatin_submit.this);
                    adapter.setCustomButtonListner1(Homework_notificatin_submit.this);



                    break;
                case -2:
                    // toast_message="Student Query Data Not Found. Please Try Again";
                    // showErrorDialog();
                    Snackbar snackbar = Snackbar
                            .make(homework_topic_layout, "Homework Topic Data Not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    //Toast.makeText(getApplicationContext(),"Student Query Data Not Found. Please Try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    // toast_message="Network Congestion! Please try Again";
                    // showErrorDialog();
                    Snackbar snackbar1 = Snackbar
                            .make(homework_topic_layout, "Network Congestion! Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    //teachers_list.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String Para(String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", applicationControllerAdmin.getActiveempcode());
            jsonParam.put("schoolCode", SchoolCode);
            jsonParam.put("branchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("SectionId",SectionId);
            jsonParam.put("ClassId", ClassId);
            jsonParam.put("TopicName", TopicName);
            jsonParam.put("fyId", applicationControllerAdmin.getFyID());
            jsonParam.put("FlagForMobile","1");
            jsonParam1.put("obj", jsonParam);
//SectionId,ClassId,TopicName,EmployeeCode,schoolCode,SessionId,branchCode,fyId
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
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
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

//////////////////////////////////////submit API////////////////////////////////
    private class Topic_SubmitAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Homework_notificatin_submit.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Homework_notificatin_submit.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.HOMEWORK_TOPIC_Submit_API,Para1(applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            //String api=ServerApiadmin.HOMEWORK_TOPIC_API;
            if(response!=null){
                response=response.replace("\r","");
                response=response.replace("\"","");
                if (response.equalsIgnoreCase("2")){
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    Toast.makeText(applicationControllerAdmin, "Home work status Submitted", Toast.LENGTH_SHORT).show();
                    new SendNotificationSendAPI().execute();
                    //  homework_topic_list=(ListView)findViewById(R.id.homework_topic_list);
                    //teachers_list.setVisibility(View.VISIBLE);
                    break;
                case -2:
                    // toast_message="Student Query Data Not Found. Please Try Again";
                    // showErrorDialog();
                    Snackbar snackbar = Snackbar
                            .make(homework_topic_layout, "Data Not Submited", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    //Toast.makeText(getApplicationContext(),"Student Query Data Not Found. Please Try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    // toast_message="Network Congestion! Please try Again";
                    // showErrorDialog();
                    Snackbar snackbar1 = Snackbar
                            .make(homework_topic_layout, "Data Not Submited ", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    //teachers_list.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String Para1(String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", applicationControllerAdmin.getActiveempcode());
            jsonParam.put("schoolCode", SchoolCode);
            jsonParam.put("branchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("VoucherNO", VoucherNo);
            jsonParam.put("TransId", TransId);
            jsonParam.put("IsSubmitted", submit_id);
            jsonParam.put("StudentCode", StudentCode);
            jsonParam.put("fyId", applicationControllerAdmin.getFyID());
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    ///////////////////////////send notification send/////////////////////////////////////
    private class SendNotificationSendAPI extends AsyncTask<String, String, Integer> {
       // ProgressDialog progressDialog = new ProgressDialog(Homework_notificatin_submit.this);
        @Override
        protected void onPreExecute() {
            //progressDialog = ProgressDialog.show(Homework_notificatin_submit.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.SEND_NOTIFICATION_API,Para2(applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            //String api=ServerApiadmin.HOMEWORK_TOPIC_API;
            if(response!=null){
                response=response.replace("\r","");
                response=response.replace("\"","");
                if (response.equalsIgnoreCase("2")){
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
                    //  homework_topic_list=(ListView)findViewById(R.id.homework_topic_list);
                    //teachers_list.setVisibility(View.VISIBLE);


                    break;
            }
        }
    }
    public String Para2(String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", applicationControllerAdmin.getActiveempcode());
            jsonParam.put("schoolCode", SchoolCode);
            jsonParam.put("branchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("StudentCode", StudentCode);
            jsonParam.put("TopicName", TopicName);
            jsonParam.put("VoucherNO", VoucherNo);
            jsonParam.put("TransId", TransId);
            jsonParam.put("SendNotification", "1");
            jsonParam.put("fyId", applicationControllerAdmin.getFyID());
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }




}
