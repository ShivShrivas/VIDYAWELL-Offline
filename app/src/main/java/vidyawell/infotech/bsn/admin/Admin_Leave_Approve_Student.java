package vidyawell.infotech.bsn.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Admin_Leave_Approve_Student extends AppCompatActivity {

    ApplicationControllerAdmin applicationControllerAdmin;
    String APPID,STATUS,DATE,LeaveFromA,LeaveToA,LeaveReasonA,EmpNameA, ResClassNameA,ResSubjectNameA,ResEmpNameA;
    LinearLayout layout_responsible,layout_applicationapprove_student;
    TextView text_view_username,formdate,todate,reason,textview_respons_name,textview_respons_subject,textview_respons_class;
    EditText edittext_remarks;
    String msgtitle,fullmsg,IsApproved="0",LeaveFrom,LeaveTo,leave_typeIDA,EmployeeCodetakenby;
    String NoOfDaysApproved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__leave__approve_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin=(ApplicationControllerAdmin)getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.layout_applicationapprove_student));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Leave Application Student");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        Intent intent=getIntent();

        NoOfDaysApproved= intent.getStringExtra("NoOfDaysApproved");
        STATUS=intent.getStringExtra("Status");
        DATE=intent.getStringExtra("DATE");
        APPID=intent.getStringExtra("APPID");

        TextView text_view_status=findViewById(R.id.text_view_status);
        layout_responsible=findViewById(R.id.layout_responsible);
        text_view_username=findViewById(R.id.text_view_username);
        formdate=findViewById(R.id.formdate);
        todate=findViewById(R.id.todate);
        reason=findViewById(R.id.reason);
        textview_respons_name=findViewById(R.id.textview_respons_name);
        textview_respons_subject=findViewById(R.id.textview_respons_subject);
        textview_respons_class=findViewById(R.id.textview_respons_class);
        edittext_remarks=findViewById(R.id.edittext_remarks);
        layout_applicationapprove_student=findViewById(R.id.layout_applicationapprove_student);

        if(STATUS.equalsIgnoreCase("Pending")){
            text_view_status.setText(STATUS);
            text_view_status.setTextColor(Color.parseColor("#000000"));
        }else if(STATUS.equalsIgnoreCase("Reject")){
            text_view_status.setText(STATUS);
            text_view_status.setTextColor(Color.parseColor("#D31002"));
        }else{
            text_view_status.setText(STATUS);
            text_view_status.setTextColor(Color.parseColor("#009688"));
        }

        Button button_reject=findViewById(R.id.button_reject);
        Button button_approved=findViewById(R.id.button_approved);
        button_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgtitle="Reject this application";
                fullmsg="Do you really want to reject this leave application";
                IsApproved="2";
                alert_verified();
                edittext_remarks.getText().toString();
            /*
                if(edittext_remarks.getText().toString().length()>1){
                    IsApproved="2";
                    alert_verified();
                }else{
                    Snackbar snackbar1 = Snackbar
                            .make(layout_applicationapprove_student, "Enter Remarks", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   *//* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*//*

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                }*/

            }
        });
        button_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgtitle="Approve this application";
                fullmsg="Do you really want to approve this leave application";

                IsApproved="1";
                alert_verified();
                edittext_remarks.getText().toString();

               /* if(edittext_remarks.getText().toString().length()>1){
                    IsApproved="1";
                    alert_verified();
                }else{
                    Snackbar snackbar1 = Snackbar
                            .make(layout_applicationapprove_student, "Enter Remarks", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   *//* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*//*

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                }*/
            }
        });


        new LeaveApplication().execute();
    }

    private class LeaveApplication extends AsyncTask<String, String, Integer> {
       // ProgressDialog progressDialog = new ProgressDialog(Leave_Applicatios.this);
        @Override
        protected void onPreExecute() {
          //  progressDialog = ProgressDialog.show(Leave_Applicatios.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_History_STUDENT_API,Para1(applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            LeaveFrom=object.getString("EndDate");
                            LeaveTo=object.getString("StartDate");
                            String  LeaveReason=object.getString("LeaveReason");
                            String  Remarks=object.getString("Remark");
                            String  IsApproved=object.getString("Status");
                            String  NoOfDaysApproved=object.getString("LeaveId");
                            String  EmpName=object.getString("StudentName");
                            String  EmpAttendanceId=object.getString("EmployeeCode");

                            //status=1;
                            if(EmpAttendanceId.equalsIgnoreCase(APPID)){
                                //ResEmpNameA=ResEmpName;
                                EmpNameA=EmpName;
                                LeaveFromA=LeaveFrom;
                                LeaveToA=LeaveTo;
                                LeaveReasonA=LeaveReason;
                                leave_typeIDA=NoOfDaysApproved;
                                EmployeeCodetakenby=EmpAttendanceId;
                                status=1;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-1;
                    }
                }else{
                    status=-1;
                }
            }else{
                status=-1;
            }

            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
         //   progressDialog.dismiss();
            switch (s){
                case 1:
                    layout_responsible.setVisibility(View.GONE);
                    /*if(ResEmpNameA.equalsIgnoreCase("null")){
                        layout_responsible.setVisibility(View.GONE);
                    }else{
                        layout_responsible.setVisibility(View.VISIBLE);
                    }*/
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//'T'HH:mm:ss
                    Date date = null;
                    try {
                        date = formatter.parse(LeaveFromA);
                        SimpleDateFormat formt = new SimpleDateFormat("dd MMM, yyyy");
                        LeaveFromA = formt.format(date);
                        Date date1 = formatter.parse(LeaveToA);
                        LeaveToA = formt.format(date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    text_view_username.setText(EmpNameA);
                    formdate.setText(LeaveFromA);
                    todate.setText(LeaveToA);
                    reason.setText(LeaveReasonA);

                    break;
                case -1:

                    break;
            }
        }
    }
    public String Para1(String EmployeeCode,String SchoolCode, String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", EmployeeCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            // jsonParam.put("FYId", FYId);
            //jsonParam.put("Action", "7");
            jsonParam.put("StartDate", DATE);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    private void alert_verified(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(fullmsg);
        alertDialogBuilder.setTitle(msgtitle);
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        new LeaveApplicationupdate().execute();
                    }
                });
        alertDialogBuilder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });


        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    private class LeaveApplicationupdate extends AsyncTask<String, String, Integer> {
        // ProgressDialog progressDialog = new ProgressDialog(Leave_Applicatios.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Leave_Applicatios.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_APPLY_APPROVE_TEACHER,Para(applicationControllerAdmin.getUserID(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            String api = applicationControllerAdmin.getServicesapplication();
            if(response!=null)
            {
                response=response.replace("\"","");
                if(response.equalsIgnoreCase("2")){
                status=1;
            }
            }else{
                status=-1;
            }

            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            //   progressDialog.dismiss();
            switch (s){
                case 1:
                    Toast.makeText(getApplicationContext(),"Application update successfully",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

                    break;
            }
        }
    }
    public String Para(String EmployeeCode,String SchoolCode, String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
        Date date = null;
        try {
            date = formatter.parse(LeaveFromA);
            SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
            LeaveFrom = formt.format(date);
            Date date1 = formatter.parse(LeaveToA);
            LeaveTo = formt.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            //jsonParam.put("EmpAttendanceId", APPID);
            jsonParam.put("Remark", edittext_remarks.getText().toString());
            jsonParam.put("Status", IsApproved);
            jsonParam.put("UpdatedBy", EmployeeCode);
            jsonParam.put("LeaveId", NoOfDaysApproved);
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
}
