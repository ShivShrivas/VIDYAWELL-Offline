package vidyawell.infotech.bsn.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Adapters.LiveClass_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.LiveClass_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;


public class LiveClass extends AppCompatActivity implements LiveClass_Adapter.customButtonListener,LiveClass_Adapter.customButtonListener1 {
    ApplicationControllerAdmin applicationController;
    TextView notice_status;
    ListView liveClass_list;
    List<LiveClass_Helper> liveClass_helpers;
    LiveClass_Helper item;

    SharedPreferences sharedprefer;
   // String toast_message,N_ID="0";
    LiveClass_Adapter adapter;
    EditText edit_text_liveclass_search;
    Typeface typeface;
    FloatingActionButton fab;
    String ValidTime;
    String Meeting_id,main_url;
    String response;
    SwipeRefreshLayout swiperefresh;
    int count;

  //  String[]  topicname = {"English lession","Math three","Science lession","Hindi lession"};
   // String[]  description = {"Learning English, Lesson One or Learning English","Learning English, Lesson One or Learning English","Learning English, Lesson One or Learning English","Learning English, Lesson One or Learning English"};
   // String[]  date = {"07-1-2020","08-1-2020","09-1-2020","10-1-2020"};
   // String[]  massage = {"This master list covers all the basic topics","This master list covers all the basic topics","This master list covers all the basic topics","This master list covers all the basic topics"};

     String TAG;
    String notice_ID;
    boolean active_id=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_class);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_liveclass));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Live Class");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);

        Intent in=getIntent();
        notice_ID=in.getStringExtra("notice_ID");

        swiperefresh=(SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        liveClass_list=(ListView)findViewById(R.id.liveClass_list);
        applicationController=(ApplicationControllerAdmin) getApplication();
        fab=(FloatingActionButton)findViewById(R.id.fab);

        edit_text_liveclass_search=(EditText)findViewById(R.id.edit_text_liveclass_search);
        edit_text_liveclass_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(adapter != null) {
                    String text = edit_text_liveclass_search.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LiveClass.this,Virtual_Class.class);
                startActivityForResult(intent,100);
            }
        });

/*

        liveClass_helpers = new ArrayList<LiveClass_Helper>();
        for (int i = 0; i < topicname.length; i++) {
            LiveClass_Helper    item = new LiveClass_Helper(topicname[i],description[i],date[i],massage[i]);
            liveClass_helpers.add(item);
        }

        liveClass_list = (ListView) findViewById(R.id.liveClass_list);
        LiveClass_Adapter adapter = new LiveClass_Adapter(this,R.layout.liveclass_listitem, liveClass_helpers);
        liveClass_list.setAdapter(adapter);
        adapter.setCustomButtonListner(LiveClass.this);
*/


          new LiveClassAPI().execute();
        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        swiperefresh.setEnabled(false);
                        refreshContent();
                        new LiveClassAPI().execute();
                        swiperefresh.setEnabled(true);

                    }
                }
        );


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
       /* Intent intent=new Intent(this,MainActivity.class);
        //intent.putExtra("EMPID",id);
        startActivity(intent);
        finish();*/
    }

    @Override
    public void onButtonClickListner(int position, LiveClass_Helper value) {
       // Toast.makeText(getApplicationContext(),"click test",Toast.LENGTH_SHORT).show();
        Meeting_id = value.getMeetingId();
        main_url          =value.geturl();
        new LiveClass_ValidTime_API().execute();

       // String url = finalHolder.attechment.getText().toString();


    }





    private void refreshContent(){
        if(adapter!= null){
            liveClass_list.setAdapter(null);
            swiperefresh.setRefreshing(false);
           adapter.clear();
        }
       // adapter.clear();
        // clear the card list
        count = 0;           // reset the counter
        // build cards and counter
        swiperefresh.setRefreshing(false);
    }

    @Override
    public void onButtonClickListner1(int position, LiveClass_Helper value) {
        Meeting_id = value.getMeetingId();
        alertdeactiv();

       // Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
    }

    /////////////////////////LiveClass List API ///////////////////////////////////

    private class LiveClassAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(LiveClass.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LiveClass.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVECLASS_LIST_API,Para(applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getBranchcode(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");
            String api=ServerApiadmin.LIVECLASS_LIST_API;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        liveClass_helpers = new ArrayList<LiveClass_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String MeetingId=object.getString("MeetingId");
                            String MeetingName=object.getString("MeetingName");
                            String Topic=object.getString("Topic");
                            String Description=object.getString("Description");
                            String StartDate=object.getString("StartDate");
                            String StartTime=object.getString("StartTime");
                            String Duration=object.getString("Duration");
                            String UserMsg=object.getString("UserMsg");
                            String url=object.getString("url");
                            String BackUrl=object.getString("BackUrl");
                            String Moderator=object.getString("Moderator");
                            String Presenter=object.getString("Presenter");
                           // String RunningMeetingUrl=object.getString("RunningMeetingUrl");
                            String Name=object.getString("Name");
                            String ClassName=object.getString("ClassName");
                            String SectionName=object.getString("SectionName");
                            boolean IsActive=object.getBoolean("IsActive");

                            item = new LiveClass_Helper(MeetingId,MeetingName,Topic,Description,StartDate,StartTime,Duration,UserMsg,url,BackUrl,Moderator,Presenter,Name,ClassName,SectionName,IsActive);
                            liveClass_helpers.add(item);
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

                    adapter = new LiveClass_Adapter(getApplicationContext(),R.layout.liveclass_listitem, liveClass_helpers);
                    liveClass_list.setAdapter(adapter);
                    adapter.setCustomButtonListner(LiveClass.this);
                    adapter.setCustomButtonListner1(LiveClass.this);
                    break;

                case -2:
                    if(adapter!= null){
                        liveClass_list.setAdapter(null);
                        adapter.clear();
                    }
                    // Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_LONG).show();
                  /*  toast_message="Data not found. Please try again";
                    showErrorDialog();*/
                    break;
                case -1:
                    if(adapter!= null){
                        liveClass_list.setAdapter(null);
                    }
                  /*  toast_message="Data not found. Please try again";
                    showErrorDialog();*/
                    // Toast.makeText(getApplicationContext(),"User Credentials are not Valid",Toast.LENGTH_LONG).show();

                    break;
            }
        }
    }
    public String Para(String SchoolCode, String SessionId, String BranchCode, String FYId, String EmployeeCode){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "6");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("FYId", FYId);
            jsonParam.put("EmployeeCode", EmployeeCode);
            jsonParam.put("loginTypeId", applicationController.getLoginType());
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    ////////////////////////valid date time api///////////////////////////



    private class LiveClass_ValidTime_API extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(LiveClass.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LiveClass.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVECLASS_VALIDTIME_API,Paratime(applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getBranchcode(),applicationController.getFyID()),"1");
            String api = applicationController.getServicesapplication();

            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        JSONObject jsonobject = jsonArray.getJSONObject(0);
                        String  MeetingId=jsonobject.getString("MeetingId");
                        String StartDate=jsonobject.getString("StartDate");
                        String EndDate=jsonobject.getString("EndDate");
                        ValidTime=jsonobject.getString("ValidTime");//Active

                        status = 1;




                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-1;
                    }
                }else{
                    status=-2;
                }
            }else{
                status=-2;
            }
            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            switch (s){
                case 1:
                    if(ValidTime.equalsIgnoreCase("1")){
                       new LiveClass_CHK_URL_API().execute();

                    /*    Intent intent = new Intent(LiveClass.this,LiveClass_Video.class);
                        intent.putExtra("test","");
                        startActivity(intent);*/
                    }else {
                        Toast.makeText(getApplicationContext(),""+ValidTime,Toast.LENGTH_LONG).show();

                    }


                    break;
                case -2:

                    break;
                case -1:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;

                case -3:

                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Paratime(String SchoolCode, String SessionId, String BranchCode, String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "7");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("FYId", FYId);
            jsonParam.put("StudentCode", applicationController.getActiveempcode());
            jsonParam.put("loginTypeId", applicationController.getLoginType());
            jsonParam.put("MeetingId", Meeting_id);
            jsonParam.put("EmployeeCode", applicationController.getActiveempcode());
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    /////////////////////API ///////////////////////////


    private class LiveClass_CHK_URL_API extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(LiveClass.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(LiveClass.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVECLASS_startmiting,Paratchkurl(Meeting_id),"1");

            String api= applicationController.getServicesapplication();
            //Log.d("request!", response);
            if(response!=null){
                response=response.replace("\"","");
                response=response.replace("\r","");
                if (response.length()<3){
                    if(response.equalsIgnoreCase("1")){
                        status=1;
                    }else{
                        status=2;
                    }

                }else{
                    status=-2;
                }
            }else{
                status=-2;
            }
            return status;
        }
        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            // progressDialog.dismiss();
            switch (s){
                case 1:
                    if(response.equalsIgnoreCase("1")){

                       /* Intent intent = new Intent(LiveClass.this,LiveClass_Video.class);
                        intent.putExtra("main_url",main_url);
                        startActivity(intent);*/
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setData(Uri.parse(main_url));
                        i.setPackage("com.android.chrome");
                        startActivity(i);

                        try {
                            Log.d(TAG, "onClick: inTryBrowser");
                            startActivity(i);
                        } catch (ActivityNotFoundException ex) {
                            Log.e(TAG, "onClick: in inCatchBrowser", ex );
                            i.setPackage(null);
                            startActivity(Intent.createChooser(i, "Select Browser"));
                        }

                    }else {
                        Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();

                    }


                    break;
                case 2:
                   // Toast.makeText(getApplicationContext(),"Please wait! Class is not running.",Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();

                    break;
                case -1:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;

                case -2:
                    Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Paratchkurl(String MeetingId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "5");
            jsonParam.put("MeetingId", MeetingId);
            jsonParam.put("SessionId", applicationController.getSeesionID());
            jsonParam.put("FYId", applicationController.getFyID());
            jsonParam.put("SchoolCode", applicationController.getschoolCode());
            jsonParam.put("BranchCode", applicationController.getBranchcode());
            jsonParam.put("UserId", applicationController.getActiveempcode());
            jsonParam.put("loginTypeId", applicationController.getLoginType());
            jsonParam.put("CreatedBy", applicationController.getUserID());
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    //MeetingId
    //SessionId
    //FYId
    //SchoolCode
    //BranchCode
    //UserId
    //loginTypeId
    //CreatedBy

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new LiveClassAPI().execute();

    }




    //////////////////////////////////////API DeactivateAPI///////////////////////
    private class DeactivateAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(LiveClass.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LiveClass.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVE_Class_Submit,ParaSubmit(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            //Log.d("request!", response);
            String api = applicationController.getServicesapplication();
            if(response!=null){
                response=response.replace("\"", "");
                response=response.replace("\r", "");
                if(response.equals("2")) {
                    status = 1;
                }else  if(response.equals("0")) {
                    status=2;
                } else{
                    status=3;
                }

            }else{
                status=2;
            }
            return status;
        }
        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            switch (s){
                case 1:
                    new LiveClassAPI().execute();
                  //  Toast.makeText(getApplicationContext(), "Live Class Created Sucessfully", Toast.LENGTH_LONG).show();
                   // finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_LONG).show();

                    break;


            }
        }
    }

    public String ParaSubmit(String schoolCode, String branchCode, String SessionId, String fyId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();

        //JSONObject attachmentList = new JSONObject();


        try {

            jsonParam.put("Action", "2");
            jsonParam.put("SchoolCode", schoolCode);
            jsonParam.put("BranchCode", branchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",fyId);
            jsonParam.put("IsActive",active_id);
            jsonParam.put("UpdatedBy",applicationController.getUserID());
            jsonParam.put("MeetingId", Meeting_id);
            jsonParam.put("StudentList","");


            jsonParam.put("ClassId","");
            jsonParam.put("SectionList","");
            jsonParam.put("BranchList","");
            jsonParam.put("StreamList","");

            jsonParam.put("Description","");
            jsonParam.put("Duration","0");
            jsonParam.put("MeetingName","");
            jsonParam.put("Message","");
            jsonParam.put("PresenterCode","");
            jsonParam.put("ModeratorList","");
            jsonParam.put("DefaultModeratorList","");
            jsonParam.put("SendEmail",false);
            jsonParam.put("SendSMS",false);
            jsonParam.put("StartDate","");
            jsonParam.put("StartTime","");
            jsonParam.put("StdContactList","");
            jsonParam.put("StdEmailList","");
            jsonParam.put("Topic","");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


       private void  alertdeactiv (){
           new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Deactivate")
                   .setMessage("Do you want the deactivate liveclass?")
                   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           active_id=false;
                           new DeactivateAPI().execute();
                          // finish();
                       }
                   })
                   .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            active_id=true;
                            new LiveClassAPI().execute();
                        }
                   }).show();

       }

}
