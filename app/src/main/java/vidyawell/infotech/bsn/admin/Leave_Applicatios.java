package vidyawell.infotech.bsn.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Leave_Application_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Leave_Application_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Leave_Applicatios extends AppCompatActivity {

    ApplicationControllerAdmin applicationControllerAdmin;
    ListView leave_history_list;
    List<Leave_Application_Helper> leave_history_helpers;
    Leave_Application_Helper item;
    LinearLayout layout_leaveapplication,layout_dateselect;
    String fromdate;
    TextView set_fromdate;
    DatePickerDialog datePickerDialog;
    Leave_Application_Adapter adapter;
    LinearLayout layout_notauthrisation,layout_authrisation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave__applicatios);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin=(ApplicationControllerAdmin)getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.layout_leaveapplication));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Leave Applications");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        layout_leaveapplication=findViewById(R.id.layout_leaveapplication);
        leave_history_list=findViewById(R.id.list_leaveapplication);

       // layout_dateselect=findViewById(R.id.layout_dateselect_new);
       // set_fromdate=findViewById(R.id.set_fromdate_new);
       // layout_notauthrisation=findViewById(R.id.layout_notauthrisation_new);
       // layout_authrisation=findViewById(R.id.layout_authrisation_new);
        leave_history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView_app_id=view.findViewById(R.id.application_id);
               // TextView status=view.findViewById(R.id.status);
                TextView status=view.findViewById(R.id.status_new);
                TextView EmployeeCode=view.findViewById(R.id.emp_code);

                Intent intent=new Intent(getApplicationContext(),Admin_Leave_Approve.class);
                intent.putExtra("APPID", textView_app_id.getText().toString());
                intent.putExtra("STATUS", status.getText().toString());
                intent.putExtra("DATE", fromdate);
                intent.putExtra("EmployeeCode", EmployeeCode.getText().toString());
                startActivityForResult(intent,100);

            }
        });
       /* if(applicationControllerAdmin.getLeaveapplication().equalsIgnoreCase("0")){
            layout_notauthrisation.setVisibility(View.VISIBLE);
            layout_authrisation.setVisibility(View.GONE);
        }else{
            layout_notauthrisation.setVisibility(View.GONE);
            layout_authrisation.setVisibility(View.VISIBLE);
        }*/
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        final int cDay = c.get(Calendar.DAY_OF_MONTH);
       // set_fromdate.setText(cDay+ "-"+ (mDay + 1) + "-" + mYear);
        fromdate=mYear+ "-"+ (mDay + 1) + "-" + cDay;


/*
        layout_dateselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR); // current year
                final int mMonth = c.get(java.util.Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Leave_Applicatios.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                set_fromdate.setText(dayOfMonth+ "-"+ (monthOfYear + 1) + "-" + year);
                                fromdate =year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                new LeaveApplication().execute();
                            }
                        }, mYear, mMonth, mDay);
               // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });
*/

       // final EditText edittext_searchleave=findViewById(R.id.edittext_searchleave);
/*
        edittext_searchleave.addTextChangedListener(new TextWatcher() {
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
                if(adapter !=null){
                    String text = edittext_searchleave.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

            }
        });
*/
        new LeaveApplication().execute();
    }

    private class LeaveApplication extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Leave_Applicatios.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Leave_Applicatios.this, "", "Please Wait...", true);
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
                        leave_history_helpers = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String  LeaveFrom=object.getString("LeaveFrom");
                            String  LeaveTo=object.getString("LeaveTo");
                            String  LeaveReason=object.getString("LeaveReason");
                            String  Remarks=object.getString("Remarks");
                            String  IsApproved=object.getString("IsApproved");
                            String  NoOfDaysApproved=object.getString("NoOfDaysApproved");
                            String  EmployeeCode=object.getString("EmployeeCode");
                            String  EmpName=object.getString("EmpName");
                            String  EmpAttendanceId=object.getString("EmpAttendanceId");
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            Date date = formatter.parse(LeaveFrom);
                            SimpleDateFormat formt = new SimpleDateFormat("dd MMM, yyyy");
                            LeaveFrom = formt.format(date);
                            Date date1 = formatter.parse(LeaveTo);
                            LeaveTo = formt.format(date1);
                            item = new Leave_Application_Helper(LeaveFrom,LeaveTo,LeaveReason,Remarks,IsApproved,NoOfDaysApproved,EmployeeCode,EmpName,EmpAttendanceId);
                            leave_history_helpers.add(item);
                            status=1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-1;
                    } catch (ParseException e) {
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    adapter = new Leave_Application_Adapter(getApplicationContext(), R.layout.item_leave_application,leave_history_helpers);
                    leave_history_list.setAdapter(adapter);
                    break;
                case -1:
                    if(adapter!=null){
                        adapter.clear();
                    }
                    Snackbar snackbar1 = Snackbar
                            .make(layout_leaveapplication, "No Leave applications", Snackbar.LENGTH_LONG)
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
            jsonParam.put("ApprovedFrom", fromdate);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new LeaveApplication().execute();
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
