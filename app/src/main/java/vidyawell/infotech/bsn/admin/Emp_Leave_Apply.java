package vidyawell.infotech.bsn.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.RelativeLayout;
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

import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Leave_History_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Emp_Leave_Apply extends AppCompatActivity {


    ApplicationControllerAdmin applicationControllerAdmin;
    DatePickerDialog datePickerDialog;
    TextView tv_fromdate,tv_todate;
    Spinner select_leave_type,select_leave_for,spinr_commu_mode;
    EditText reason;
    Button submit_leave;
    String leave_type, TotalLeaves,AllotedLeaves,LeaveTaken,Balance;
    public ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    RelativeLayout layout_leave_app;
    String ression_name,reason_type,leave_address,contact_no,remarks;
    String dateto,fromdate,todaysDate;
    int month_from,month_to,day_from,day_to;
    String DepartmentId,DesignationId,leave_typeID,leave_for,communition_mode;
    Typeface typeface;
    EditText txt_on_leave_address,txt_contact_no,txt_remark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_leave_apply);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin=(ApplicationControllerAdmin)getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout)findViewById(R.id.layout_leave_app));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Apply Leave");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
        Intent intent=getIntent();
        leave_typeID=intent.getStringExtra("leave_typeID");
        layout_leave_app=(RelativeLayout)findViewById(R.id.layout_leave_app);
        tv_fromdate=(TextView)findViewById(R.id.set_fromdate) ;
        tv_todate=(TextView)findViewById(R.id.to_setdate_to);
        select_leave_for=(Spinner)findViewById(R.id.select_leave_for);
        spinr_commu_mode=(Spinner)findViewById(R.id.spinr_commu_mode);
        reason=(EditText)findViewById(R.id.reason);
        submit_leave=(Button)findViewById(R.id.submit_leave);
        txt_on_leave_address=(EditText)findViewById(R.id.txt_on_leave_address);
        txt_contact_no=(EditText)findViewById(R.id.txt_contact_no);
        txt_remark=(EditText)findViewById(R.id.txt_remark);



        new LeaveApplyDepartment().execute();


        select_leave_for.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leave_for = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinr_commu_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                communition_mode = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                reason_type= reason.getText().toString();
                leave_address= txt_on_leave_address.getText().toString();
                contact_no= txt_contact_no.getText().toString();
                remarks= txt_remark.getText().toString();
                 if(reason.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(),"Enter Reason",Toast.LENGTH_LONG).show();
                }else if(txt_on_leave_address.getText().toString().length()==0) {
                     Toast.makeText(getApplicationContext(),"Enter On Leave Address",Toast.LENGTH_LONG).show();

                    /* try {
                         Date fdate = sdf.parse(fromdate);
                         Date todate = sdf.parse(dateto);
                         int s=month_from;
                         int ss=month_to;
                         if(month_from==month_to){
                         if(day_from>day_to){
                                 Toast.makeText(getApplicationContext(),"To Date not valid",Toast.LENGTH_LONG).show();
                         }else{
                             new LeaveApply().execute();
                         }
                         }else {
                             if(fdate.compareTo(todate)>0){
                                 Toast.makeText(getApplicationContext(),"Select valid Date",Toast.LENGTH_LONG).show();
                             }else{
                                 new LeaveApply().execute();
                             }
                         }
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }*/
                }else if(txt_contact_no.getText().toString().length()<10){
                     Toast.makeText(getApplicationContext(),"Enter Contact No Minimum 10 digit",Toast.LENGTH_LONG).show();

                 }else {
                     try {
                         Date fdate = sdf.parse(fromdate);
                         Date todate = sdf.parse(dateto);
                         int s=month_from;
                         int ss=month_to;
                         if(month_from==month_to){
                             if(day_from>day_to){
                                 Toast.makeText(getApplicationContext(),"To Date not valid",Toast.LENGTH_LONG).show();
                             }else{
                                 new LeaveApply().execute();
                             }
                         }else {
                             if(fdate.compareTo(todate)>0){
                                 Toast.makeText(getApplicationContext(),"Select valid Date",Toast.LENGTH_LONG).show();
                             }else{
                                new LeaveApply().execute();
                             }
                         }
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
                 }
            }
        });

        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        final int cDay = c.get(Calendar.DAY_OF_MONTH);
        tv_fromdate.setText(cDay+ "-"+ (mDay + 1) + "-" + mYear);
        tv_todate.setText(cDay+ "-"+ (mDay + 1) + "-" + mYear);
        fromdate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        dateto=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        month_from=(mDay + 1);
        month_to=(mDay + 1);
        day_from=cDay;
        day_to=cDay;

        RelativeLayout rtv_fromdate=(RelativeLayout)findViewById(R.id.layout_fromdate);
        RelativeLayout rtv_todate=(RelativeLayout)findViewById(R.id.layout_todate);

        rtv_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR); // current year
                final int mMonth = c.get(java.util.Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Emp_Leave_Apply.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                tv_fromdate.setText(dayOfMonth+ "-"+ (monthOfYear + 1) + "-" + year);
                                fromdate =year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                month_from=(monthOfYear + 1);
                                day_from=dayOfMonth;
                                tv_todate.setText(dayOfMonth+ "-"+ (monthOfYear + 1) + "-" + year);
                                dateto=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                month_to=(monthOfYear + 1);
                                day_to=dayOfMonth;

                              //  applicationController.setDate(year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
                                     /* date.setText(new StringBuilder().append(dayOfMonth)
                                              .append("/").append(monthOfYear + 1).append("/").append(year));*/
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });
        rtv_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR); // current year
                final int mMonth = c.get(java.util.Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Emp_Leave_Apply.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                tv_todate.setText(dayOfMonth+ "-"+ (monthOfYear + 1) + "-" + year);
                                dateto=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                month_to=(monthOfYear + 1);
                                day_to=dayOfMonth;
                                //  applicationController.setDate(year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
                                     /* date.setText(new StringBuilder().append(dayOfMonth)
                                              .append("/").append(monthOfYear + 1).append("/").append(year));*/
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        new LeaveStatus().execute();
        select_leave_type=(Spinner)findViewById(R.id.spinner_leave_typea);
        select_leave_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leave_type    = ((TextView) view.findViewById(R.id.text_cID)).getText().toString();
             //   Toast.makeText(getApplicationContext(),leave_type,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }
    private class LeaveStatus extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Leave_Apply.this);
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
                            String  AbbrTypeId=object.getString("AbbrTypeId");
                            final ClassList_Helper sched = new ClassList_Helper();
                            sched.setClassID(AbbrTypeId);
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
                    select_leave_type.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_leave_app, "Leave Data Not found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:

                    Snackbar snackbar1 = Snackbar
                            .make(layout_leave_app, "Leave Data Not found Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Network Congestion! Please try Again", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

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

    //////////////////////////////Leave Apply API///////////////////////////////////////


    private class LeaveApply extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Leave_Apply.this);

        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status = 0;
            JsonParser josnparser = new JsonParser(getApplicationContext());
            String response = josnparser.executePostinsert(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_APPLY, Para("1",  applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(), applicationControllerAdmin.getSeesionID(), applicationControllerAdmin.getFyID(), applicationControllerAdmin.getActiveempcode()), "1");
            if (response != null) {
                response=response.replace("\"","");
                if(response.equals("1")){
                    status = 1;
                }else if(response.equals("-1")){
                    status = -2;
                }else{
                    status = -1;
                }



            } else {
                status = -1;
            }


            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            // progressDialog.dismiss();
            switch (s){
                case 1:
                    reason.getText().clear();
                    showSuccessDialog();
                   /* Resources res = getResources();
                    ClassList_Adapter adapter = new ClassList_Adapter(getApplicationContext(), R.layout.spinner_class_item, CustomListViewValuesArr,res);
                    select_leave_type.setAdapter(adapter);*/
                    break;
                case -2:
                   // Toast.makeText(getApplicationContext(),"Leave Apply Not Apply",Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar
                            .make(layout_leave_app, "Leave already taken for select date.", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                   // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    Snackbar snackbar1 = Snackbar
                            .make(layout_leave_app, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }
    public String Para(String Action, String SchoolCode, String BranchCode, String SessionId, String FYId, String EmpAttendanceId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", Action);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("EmpAttendanceId", "");
            jsonParam.put("EmployeeCode", EmpAttendanceId);//EmpAttendanceId
            jsonParam.put("LeaveFrom",fromdate );
            jsonParam.put("LeaveTo", dateto);
            jsonParam.put("LeaveReason", reason_type);
            jsonParam.put("LeaveTypeId", leave_typeID);
            jsonParam.put("CommunicationPrefferedMode", communition_mode);
            jsonParam.put("CreatedBy", "1");
            jsonParam.put("DesignationId", applicationControllerAdmin.getDesignationId());
            jsonParam.put("DepartmentId", applicationControllerAdmin.getDepartmentId());
            jsonParam.put("VoucherDate", todaysDate);
            jsonParam.put("OnLeaveContactNo", contact_no);
            jsonParam.put("LeaveFor", leave_for);
            jsonParam.put("OnLeaveAddress", leave_address);
            jsonParam.put("Remarks", remarks);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    private void showSuccessDialog() {
        final Dialog dialog = new Dialog(Emp_Leave_Apply.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.success_dialog);
        dialog.setTitle("");
        // set the custom dialog components - text, image and button
        TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        text_label.setText("Leave Submit Successfully");
        TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
        text_message.setText("");
        text_toplabel.setTypeface(typeface);
        Button dialogButton = (Button) dialog.findViewById(R.id.button_closed);
        text_toplabel.setTypeface(typeface);
        text_label.setTypeface(typeface);
        text_message.setTypeface(typeface);
        dialogButton.setTypeface(typeface);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //////////////////////Leave Apply Depatment id  API///////////////////////////////////////
    private class LeaveApplyDepartment extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Leave_Apply.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_History_API,Para2(applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        CustomListViewValuesArr= new ArrayList<ClassList_Helper>();
                        JSONObject object= array.getJSONObject(0);
                        DepartmentId=object.getString("DepartmentId");
                        DesignationId=object.getString("DesignationId");
                           /* item = new Leave_History_Helper(LeaveFrom,LeaveTo,LeaveReason,Remarks,IsApproved,NoOfDaysApproved);
                            leave_history_helpers.add(item);*/
                        status=1;

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
                   /* Leave_History_Adapter adapter = new Leave_History_Adapter(getApplicationContext(), R.layout.item_leave_history,leave_history_helpers);
                    leave_history_list.setAdapter(adapter);*/
                    break;
                case -2:
                    break;
                case -1:
                    break;
            }
        }
    }
    public String Para2(String EmployeeCode,String SchoolCode, String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", EmployeeCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("Action", "5");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


}


