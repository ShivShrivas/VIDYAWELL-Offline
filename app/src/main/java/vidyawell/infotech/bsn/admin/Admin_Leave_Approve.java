package vidyawell.infotech.bsn.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static vidyawell.infotech.bsn.admin.MainActivity_Admin.theMonth;

public class Admin_Leave_Approve extends AppCompatActivity {

    ApplicationControllerAdmin applicationControllerAdmin;
    String APPID,STATUS,DATE,LeaveFromA,LeaveToA,LeaveReasonA,EmpNameA, ResClassNameA,ResSubjectNameA,ResEmpNameA;
    LinearLayout layout_responsible,layout_upload_document;
    TextView text_view_username,formdate,todate,reason,textview_respons_name,textview_respons_subject,textview_respons_class;
    EditText edittext_remarks;
    String msgtitle,fullmsg,IsApproved="0",LeaveFrom,LeaveTo,leave_typeIDA,EmployeeCodetakenby;
    TextView leave_formdate,leave_todate;
    DatePickerDialog datePickerDialog;
    String from_Date_str,to_Date_str;
    Button chkt_leave_status;
    LinearLayout layout_applicationapprove;
    String leave_type, TotalLeaves,AllotedLeaves,LeaveTaken,Balance,leave_typeID,TotalAllotedLeave;
    TextView tv_lTotal,allotted_leave,leave_taken,balance_value,leave_type_value,approve_leave_total;
    String approve_leave_max;
    public ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    Spinner spinner_leave_type;
    String EmployeeCode;
    int catalog_outdated;
    int catalog_outdated_to;
    int date_diff,date_diff_To,total_valuedate_diff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__leave__approve);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin=(ApplicationControllerAdmin)getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.layout_applicationapprove));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Leave Application Details");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        Intent intent=getIntent();
        APPID= intent.getStringExtra("APPID");
        STATUS=intent.getStringExtra("STATUS");
        DATE=intent.getStringExtra("DATE");
        EmployeeCode=intent.getStringExtra("EmployeeCode");
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
        layout_upload_document=findViewById(R.id.layout_applicationapprove);
        leave_formdate=(TextView)findViewById(R.id.leave_formdate);
        leave_todate=(TextView)findViewById(R.id.leave_todate);
        chkt_leave_status=(Button)findViewById(R.id.chkt_leave_status);
        layout_applicationapprove=(LinearLayout)findViewById(R.id.layout_applicationapprove);

       // approve_leave_total=(TextView)findViewById(R.id.approve_leave_total);

        if(STATUS.equalsIgnoreCase("Pending")){
            text_view_status.setText(STATUS);
            text_view_status.setTextColor(Color.parseColor("#000000"));
        }else if(STATUS.equalsIgnoreCase("Rejected")){
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
                if(edittext_remarks.getText().toString().length()>1){
                    IsApproved="2";
                    alert_verified();
                }else{
                    Snackbar snackbar1 = Snackbar
                            .make(layout_upload_document, "Enter Remarks", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                }

            }
        });
        button_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgtitle="Approve this application";
                fullmsg="Do you really want to approve this leave application";
                if(edittext_remarks.getText().toString().length()>1){
                    IsApproved="1";
                    alert_verified();
                }else{
                    Snackbar snackbar1 = Snackbar
                            .make(layout_upload_document, "Enter Remarks", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                }
            }
        });


        chkt_leave_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogRemark_Details();
            }
        });

        leave_formdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Admin_Leave_Approve.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                               // tv_date_2.setText(dayOfMonth+"");
                                //tv_month_2.setText(month);
                                from_Date_str=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                leave_formdate.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                 Date date = formatter.parse(from_Date_str);
                                    SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
                                    from_Date_str= formt.format(date);
                                }catch (ParseException e) {
                                    e.printStackTrace();
                                }
                              /*  if(String.valueOf(mDay).length()==1){
                                    todaysDate=mYear+ "-0"+ (mDay + 1) + "-0" + cDay;
                                }else{
                                    todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
                                }*/

                                //new HomeworkGETAPI().execute();
                             // formdate.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                            }
                        }, mYear, mDay, cDay);
                // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        leave_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Admin_Leave_Approve.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                // tv_date_2.setText(dayOfMonth+"");
                                //tv_month_2.setText(month);
                                to_Date_str=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                leave_todate.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = formatter.parse(from_Date_str);
                                    SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
                                    to_Date_str= formt.format(date);
                                }catch (ParseException e) {
                                    e.printStackTrace();
                                }
                              /*  if(String.valueOf(mDay).length()==1){
                                    todaysDate=mYear+ "-0"+ (mDay + 1) + "-0" + cDay;
                                }else{
                                    todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
                                }*/

                                //new HomeworkGETAPI().execute();
                                // formdate.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                            }
                        }, mYear, mDay, cDay);
                // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
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
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_History_API,Para1(applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            LeaveFrom=object.getString("LeaveFrom");
                            LeaveTo=object.getString("LeaveTo");
                            String LeaveReason=object.getString("LeaveReason");
                            String  Remarks=object.getString("Remarks");
                            String  IsApproved=object.getString("IsApproved");
                            String  NoOfDaysApproved=object.getString("NoOfDaysApproved");
                            String EmpName=object.getString("EmpName");
                            String  EmpAttendanceId=object.getString("EmpAttendanceId");
                            String  ResEmpName=object.getString("ResEmpName");
                            String ResSubjectName=object.getString("ResSubjectName");
                            String ResClassName=object.getString("ResClassName");
                            String leave_typeID=object.getString("LeaveTypeId");
                            String EmployeeCode=object.getString("EmployeeCode");


                            if(EmpAttendanceId.equalsIgnoreCase(APPID)){
                                ResEmpNameA=ResEmpName;
                                EmpNameA=EmpName;
                                LeaveFromA=LeaveFrom;
                                LeaveToA=LeaveTo;
                                LeaveReasonA=LeaveReason;
                                ResSubjectNameA=ResSubjectName;
                                ResClassNameA=ResClassName;
                                leave_typeIDA=leave_typeID;
                                EmployeeCodetakenby=EmployeeCode;
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
                   /* if(ResEmpNameA.equalsIgnoreCase("null")){
                        layout_responsible.setVisibility(View.GONE);
                    }else{
                        layout_responsible.setVisibility(View.VISIBLE);
                    }*/
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
                   // textview_respons_name.setText("Name- "+ResEmpNameA);
                    textview_respons_subject.setText("Subject- "+ResSubjectNameA);
                    textview_respons_class.setText("Class- "+ResClassNameA);
                    break;
                case -1:

                    break;
            }
        }
    }
    public String Para1(String EmployeeCode,String SchoolCode, String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            //jsonParam.put("EmployeeCode", "000B010000006");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("Action", "7");
            jsonParam.put("ApprovedFrom", DATE);
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
            String response=josnparser.executePostinsert(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_APPLY,Para(applicationControllerAdmin.getUserID(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID()),"1");
            if(response!=null)
            {
                response=response.replace("\"","");
                if(response.equalsIgnoreCase("1")){
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
    public String Para(String EmployeeCode,String SchoolCode, String BranchCode,String SessionId,String FYId){
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
            jsonParam.put("FYId", FYId);
            jsonParam.put("Action", "2");
            jsonParam.put("EmpAttendanceId", APPID);
            jsonParam.put("Remarks", edittext_remarks.getText().toString());
            jsonParam.put("IsApproved", IsApproved);
            jsonParam.put("ApprovedFrom", LeaveFrom);
            jsonParam.put("ApprovedTo", LeaveTo);
            jsonParam.put("LeaveTypeId", leave_typeIDA);
            jsonParam.put("UpdatedBy", EmployeeCode);
            jsonParam.put("ApprovedBy", EmployeeCode);
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

    /////////////////////dailog box ///////////////////

    private void showdialogRemark_Details() {
        final Dialog dialog = new Dialog(Admin_Leave_Approve.this);
        //  R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
        dialog.setContentView(R.layout.dialog_leave_status);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)dialog.findViewById(R.id.dialog_layout_vedio));
        dialog.setCanceledOnTouchOutside(false);

        final TextView txt_write=(TextView)dialog.findViewById(R.id.txt_write);
        TextView   button_cancel=(TextView)dialog.findViewById(R.id.button_cancel);
        TextView   button_submitcode=(TextView)dialog.findViewById(R.id.button_submitcode);
          spinner_leave_type=(Spinner)dialog.findViewById(R.id.spinner_leave_type);

        allotted_leave=(TextView)dialog.findViewById(R.id.allotted_leave);
        leave_taken=(TextView)dialog.findViewById(R.id.leave_taken);
        balance_value=(TextView)dialog.findViewById(R.id.balance_value);
        leave_type_value=(TextView)dialog.findViewById(R.id.leave_type_value);

        //txt_write.setText(txt_write);
        new LeaveStatus().execute();

        dialog.show();
        spinner_leave_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leave_type    = ((TextView) view.findViewById(R.id.text_cname)).getText().toString();
                leave_typeID    = ((TextView) view.findViewById(R.id.text_cID)).getText().toString();
                leave_type_value.setText(leave_type);
                new LeaveStatus_data().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_submitcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approve_leave_max=   txt_write.getText().toString();

                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
                Date date = null;
                Date date1 = null;
                try {
                    date = formatter.parse(LeaveFromA);
                    SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
                    LeaveFrom = formt.format(date);
                    date1 = formatter.parse(LeaveToA);
                    LeaveTo = formt.format(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long diff= 0;
                long days= 0;
                long diffInMillisec = date.getTime() - date1.getTime();
                diff = date.getTime() - date1.getTime();

                diffInMillisec  = TimeUnit.MILLISECONDS.toDays(diffInMillisec);


                if(txt_write.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter the Approve Leave",Toast.LENGTH_LONG).show();

                }else {
                    double  balnceint =0;
                    int aprove_int =0;
                    balnceint=     Double.valueOf(Balance);
                    aprove_int= Integer.parseInt(approve_leave_max);


                    if(balnceint< Integer.parseInt(String.valueOf(aprove_int))){
                        Toast.makeText(getApplicationContext(),"Balance leave can not < 0",Toast.LENGTH_LONG).show();

                    }else {
                        dialog.dismiss();
                    }

                }


            }
        });
    }


    //////Leave status balnce api //////


    private class LeaveStatus_data extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Admin_Leave_Approve.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_MASTER_DATA,Paradata("10",applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        TotalLeaves="0";
                        AllotedLeaves="0";
                        LeaveTaken="0";
                        Balance="0";
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String  ClassName=object.getString("LeaveType");
                            if(ClassName.equalsIgnoreCase(leave_type)){
                                TotalLeaves=object.getString("TotalLeaves");
                                AllotedLeaves=object.getString("AllotedLeaves");
                                LeaveTaken=object.getString("LeaveTaken");
                                Balance=object.getString("Balance");
                                status=1;
                            }
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
                    allotted_leave.setText(AllotedLeaves);
                    leave_taken.setText(LeaveTaken);
                    balance_value.setText(Balance);
                   // startCountAnimation(AllotedLeaves);
                  // approve_leave_total.setText(TotalAllotedLeave);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_applicationapprove, "Leave data not found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                    Intent in=getIntent();
                                    finish();
                                    startActivity(in);
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_applicationapprove, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                    Intent in=getIntent();
                                    finish();
                                    startActivity(in);
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }
    public String Paradata(String action,String BranchCode,String SchoolCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("EmployeeCode", EmployeeCode);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    private class LeaveStatus extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Admin_Leave_Approve.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_MASTER,Para1("5",applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String  ClassName=object.getString("AbbrType");
                            final ClassList_Helper sched = new ClassList_Helper();
                            sched.setClassID("1");
                            sched.setClassName(ClassName);
                            CustomListViewValuesArr.add(sched);
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
                    Resources res = getResources();
                    ClassList_Adapter adapter = new ClassList_Adapter(getApplicationContext(), R.layout.spinner_class_item, CustomListViewValuesArr,res);
                    spinner_leave_type.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_applicationapprove, "Leave Data Not found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                    Intent in=getIntent();
                                    finish();
                                    startActivity(in);
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_applicationapprove, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                    Intent in=getIntent();
                                    finish();
                                    startActivity(in);
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }
    public String Para1(String action,String BranchCode,String SchoolCode,String EmployeeCode, String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
}
