package vidyawell.infotech.bsn.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Leave_History_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Leave_History_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Emp_Leave_History extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    RelativeLayout layout_fromdate,layout_todate;
    ApplicationControllerAdmin applicationControllerAdmin;
    ListView leave_history_list;
    List<Leave_History_Helper>leave_history_helpers;
    Leave_History_Helper item;
    String ApprovedFrom,ApprovedTo,todaysDate;
    TextView formdate_history,todate_history;
    Button history_search;
    RelativeLayout history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp__leave__history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin=(ApplicationControllerAdmin)getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout)findViewById(R.id.history));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Leave History");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        layout_fromdate=(RelativeLayout) findViewById(R.id.layout_fromdate);
        layout_todate=(RelativeLayout) findViewById(R.id.layout_todate);
        formdate_history=(TextView)findViewById(R.id.formdate_history);
        todate_history=(TextView)findViewById(R.id.todate_history);
        leave_history_list=(ListView)findViewById(R.id.leave_history_list);
        history_search=(Button)findViewById(R.id.history_search);
        history=(RelativeLayout)findViewById(R.id.history);



        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        final int cDay = c.get(Calendar.DAY_OF_MONTH);
        formdate_history.setText(cDay+ "-"+ (mDay + 1) + "-" + mYear);
        todate_history.setText(cDay+ "-"+ (mDay + 1) + "-" + mYear);
        ApprovedFrom=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        ApprovedTo=mYear+ "-"+ (mDay + 1) + "-" + (cDay +1);
        todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;

        new LeaveHistory().execute();
        history_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LeaveHistory().execute();
            }
        });
        layout_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR); // current year
                final int mMonth = c.get(java.util.Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                DatePickerDialog datePickerDialog = new DatePickerDialog(Emp_Leave_History.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth)  {
                                // set day of month , month and year value in the edit text
                                formdate_history.setText(dayOfMonth+ "-"+ (monthOfYear + 1) + "-" + year);
                                ApprovedFrom =year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
               // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        layout_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR); // current year
                final int mMonth = c.get(java.util.Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                DatePickerDialog datePickerDialog = new DatePickerDialog(Emp_Leave_History.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth)  {
                                // set day of month , month and year value in the edit text
                                todate_history.setText(dayOfMonth+ "-"+ (monthOfYear + 1) + "-" + year);
                                ApprovedTo =year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
              //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
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

    private class LeaveHistory extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Leave_History.this);
        @Override
        protected void onPreExecute() {
             progressDialog = ProgressDialog.show(Emp_Leave_History.this, "", "Please Wait...", true);
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
                        leave_history_helpers = new ArrayList<Leave_History_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String  LeaveFrom=object.getString("LeaveFrom");
                            String  LeaveTo=object.getString("LeaveTo");
                            String  LeaveReason=object.getString("LeaveReason");
                            String  Remarks=object.getString("Remarks");
                            String  IsApproved=object.getString("Status");
                            String  NoOfDaysApproved=object.getString("NoOfDaysApproved");
                            String  ApprovedFrom=object.getString("ApprovedFrom");
                            String  ApprovedTo=object.getString("ApprovedTo");
                            item = new Leave_History_Helper(LeaveFrom,LeaveTo,LeaveReason,Remarks,IsApproved,NoOfDaysApproved,ApprovedFrom,ApprovedTo);
                            leave_history_helpers.add(item);
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
                    Leave_History_Adapter adapter = new Leave_History_Adapter(getApplicationContext(), R.layout.item_leave_history,leave_history_helpers);
                    leave_history_list.setAdapter(adapter);
                    break;
                case -1:
                    //Toast.makeText(getApplicationContext(),"Leave History Not Found",Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar
                            .make(history, "Leave History Not Found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
            }
        }
    }
    public String Para1(String EmployeeCode,String SchoolCode, String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", EmployeeCode);
            jsonParam.put("ApprovedFrom", ApprovedFrom);
            jsonParam.put("ApprovedTo", ApprovedTo);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("Action", "12");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }



}
