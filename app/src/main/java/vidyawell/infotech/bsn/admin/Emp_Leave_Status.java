package vidyawell.infotech.bsn.admin;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Class_Attendance_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Emp_Leave_Status extends AppCompatActivity {

    ApplicationControllerAdmin applicationControllerAdmin;
    TextView tv_lTotal,allotted_leave,leave_taken,balance_value,leave_type_value;
    RelativeLayout layout_emp_details;
    public ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    Spinner select_leave_type;
    List<Class_Attendance_Helper> class_attendance_helpers;
    String leave_type, TotalLeaves,AllotedLeaves,LeaveTaken,Balance,leave_typeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_leave_status);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin=(ApplicationControllerAdmin)getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout)findViewById(R.id.layout_emp_status));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Leave Status");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        layout_emp_details=(RelativeLayout)findViewById(R.id.layout_emp_status);
        tv_lTotal=(TextView)findViewById(R.id.leave_total_value);
        allotted_leave=(TextView)findViewById(R.id.allotted_leave);
        leave_taken=(TextView)findViewById(R.id.leave_taken);
        balance_value=(TextView)findViewById(R.id.balance_value);
        leave_type_value=(TextView)findViewById(R.id.leave_type_value);
        new LeaveStatus().execute();
        select_leave_type=(Spinner)findViewById(R.id.spinner_leave_type);
        select_leave_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leave_type    = ((TextView) view.findViewById(R.id.text_cname)).getText().toString();
                leave_typeID    = ((TextView) view.findViewById(R.id.text_cID)).getText().toString();
                leave_type_value.setText(leave_type);
                 new LeaveStatus_data().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LinearLayout lyout_leave=(LinearLayout)findViewById(R.id.layout_leave_apply);
        lyout_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leave_type.equalsIgnoreCase("Leave")){
                    Intent in=new Intent(getApplicationContext(),Emp_Leave_Apply.class);
                    in.putExtra("leave_typeID",leave_typeID);
                    startActivity(in);
                }else{
                    if(AllotedLeaves.equalsIgnoreCase("0")){
                        Snackbar snackbar = Snackbar
                                .make(layout_emp_details, leave_type+" not pending for this year ", Snackbar.LENGTH_LONG)
                                .setAction("Change Leave type Try Again", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                        snackbar.setActionTextColor(Color.RED);
                        snackbar.show();
                    }else{
                        Intent in=new Intent(getApplicationContext(),Emp_Leave_Apply.class);
                        in.putExtra("leave_typeID",leave_typeID);
                        startActivity(in);
                    }
                }
            }
        });
        LinearLayout layout_history=(LinearLayout)findViewById(R.id.leave_history);
        layout_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Emp_Leave_History.class);
                startActivity(intent);
            }
        });
    }
    private void startCountAnimation(String value) {
        int values=Integer.parseInt(value);
        ValueAnimator animator = ValueAnimator.ofInt(0, values);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_lTotal.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
    private class LeaveStatus extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Leave_Status.this);
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
                    select_leave_type.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_emp_details, "Leave Data Not found", Snackbar.LENGTH_LONG)
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
                            .make(layout_emp_details, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
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
    private class LeaveStatus_data extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Leave_Status.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            Log.d("TAG", "doInBackground:leave type "+applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_MASTER_DATA+"/////////"+Paradata("10",applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID()));
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.LEAVE_MASTER_DATA,Paradata("10",applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID()),"1");
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
                    startCountAnimation(AllotedLeaves);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_emp_details, "Leave data not found", Snackbar.LENGTH_LONG)
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
                            .make(layout_emp_details, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
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
    public String Paradata(String action,String BranchCode,String SchoolCode,String EmployeeCode, String SessionId,String FYId){
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
