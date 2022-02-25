package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

import vidyawell.infotech.bsn.admin.Adapters.Checked_Unchecked_Adapter;

import vidyawell.infotech.bsn.admin.Helpers.Checked_Unchecked_Helper;

import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Checked_Unchecked extends AppCompatActivity implements Checked_Unchecked_Adapter.customButtonListener,Checked_Unchecked_Adapter.customButtonListener1 {
    ApplicationControllerAdmin applicationControllerAdmin;

    EditText topic_search;

    Checked_Unchecked_Adapter adapter;
    ListView cheked_uncheked_submit_list;
    List<Checked_Unchecked_Helper> checked_unchecked_helpers;
    Checked_Unchecked_Helper item;
    RelativeLayout layout_checked_uncheked;
    String SectionId,ClassId,TopicName,EmployeeCode;
    String SendNotification,TransId,VoucherNo,IsSubmitted,StudentCode,checked_status;
    String submit_id="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked__unchecked);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin = (ApplicationControllerAdmin) getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_checked_uncheked));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Checked Unchecked Status");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);


        layout_checked_uncheked=(RelativeLayout)findViewById(R.id.layout_checked_uncheked);
        cheked_uncheked_submit_list=(ListView)findViewById(R.id.cheked_uncheked_submit_list);


        Intent intent=getIntent();
        SectionId=intent.getStringExtra("SectionId");
        ClassId=intent.getStringExtra("ClassId");
        TopicName=intent.getStringExtra("TopicName");

        new Homework_Topic_GETAPI().execute();

    }

    @Override
    public void onButtonClickListner(int position, Checked_Unchecked_Helper value) {
        submit_id="1";
        TransId=value.getTransId();
        StudentCode=value.getStudentCode();
        TopicName=value.getTopicName();
        VoucherNo=value.getVoucherNo();
        checked_status =value.getSendNotification();
        //submit_id = value.getIsSubmitted();
       // new Homework_notificatin_submit.Topic_SubmitAPI().execute();
        new Topic_SubmitAPI().execute();

    }

    @Override
    public void onButtonClickListner1(int position, Checked_Unchecked_Helper value) {
        submit_id="0";
        TransId=value.getTransId();
        StudentCode=value.getStudentCode();
        TopicName=value.getTopicName();
        VoucherNo=value.getVoucherNo();
        checked_status =value.getSendNotification();
       // new Homework_notificatin_submit.Topic_SubmitAPI().execute();
        new Topic_SubmitAPI().execute();
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


    /////////////////////get api////////
    private class Homework_Topic_GETAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Checked_Unchecked.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Checked_Unchecked.this, "", "Please Wait...", true);
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
                        checked_unchecked_helpers = new ArrayList<Checked_Unchecked_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   SendNotification=object.getString("IscheckedbyTeacher");
                            String   StudentCode=object.getString("StudentCode");
                            String   StudentName=object.getString("StudentName");
                            String   TopicName=object.getString("TopicName");
                            String   SubmissionDate=object.getString("SubmissionDate");
                            String IsSubmitted=object.getString("HomeWorkChecked");
                            String  TransId=object.getString("TransId");
                            String VoucherNo=object.getString("VoucherNo");

                            item = new Checked_Unchecked_Helper(SendNotification,StudentCode,StudentName,TopicName,SubmissionDate,IsSubmitted,TransId,VoucherNo);
                            checked_unchecked_helpers.add(item);
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
                    adapter = new Checked_Unchecked_Adapter(getApplicationContext(),R.layout.item_homework_checked_unchecked, checked_unchecked_helpers);
                    cheked_uncheked_submit_list.setAdapter(adapter);
                    adapter.setCustomButtonListner(Checked_Unchecked.this);
                    adapter.setCustomButtonListner1(Checked_Unchecked.this);



                    break;
                case -2:
                    if(adapter !=null){
                        adapter.clear();
                    }
                    // toast_message="Student Query Data Not Found. Please Try Again";
                    // showErrorDialog();
                    Snackbar snackbar = Snackbar
                            .make(layout_checked_uncheked, "Not Submitted History", Snackbar.LENGTH_LONG)
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
                    if(adapter !=null){
                        adapter.clear();
                    }
                    // toast_message="Network Congestion! Please try Again";
                    // showErrorDialog();
                    Snackbar snackbar1 = Snackbar
                            .make(layout_checked_uncheked, "Not Submitted History ", Snackbar.LENGTH_LONG)
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
            jsonParam.put("IscheckedbyTeacher","1");
            jsonParam1.put("obj", jsonParam);
//SectionId,ClassId,TopicName,EmployeeCode,schoolCode,SessionId,branchCode,fyId
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }





    //////////////////////////////////////submit API////////////////////////////////
    private class Topic_SubmitAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Checked_Unchecked.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Checked_Unchecked.this, "", "Please Wait...", true);
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

                    //  homework_topic_list=(ListView)findViewById(R.id.homework_topic_list);
                    //teachers_list.setVisibility(View.VISIBLE);
                    break;
                case -2:
                    // toast_message="Student Query Data Not Found. Please Try Again";
                    // showErrorDialog();
                    Snackbar snackbar = Snackbar
                            .make(layout_checked_uncheked, "Data Not Submited", Snackbar.LENGTH_LONG)
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
                            .make(layout_checked_uncheked, "Data Not Submited ", Snackbar.LENGTH_LONG)
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
            //jsonParam.put("IsSubmitted", "");
            jsonParam.put("StudentCode", StudentCode);
            jsonParam.put("fyId", applicationControllerAdmin.getFyID());
            jsonParam.put("IscheckedbyTeacher",submit_id);
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new Homework_Topic_GETAPI().execute();
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        new Homework_Topic_GETAPI().execute();
    }*/
}
