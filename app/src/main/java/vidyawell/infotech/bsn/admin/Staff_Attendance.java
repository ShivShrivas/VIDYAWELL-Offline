package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Adapters.Staff_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Student_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Staff_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Staff_Attendance extends AppCompatActivity {


    Staff_Adapter adapter;
    ListView staff_list;
    List<Staff_Helper> staff_helpers;
    Staff_Helper item;
    RelativeLayout layout_staff;
    ApplicationControllerAdmin applicationController;
    TextView staff_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff__attendance);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout)findViewById(R.id.layout_staff));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Staff Attendance");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        layout_staff=(RelativeLayout)findViewById(R.id.layout_staff);
        staff_search=(TextView)findViewById(R.id.staff_search);

        staff_list = (ListView) findViewById(R.id.staff_list);
        staff_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView Empcode = (TextView) view.findViewById(R.id.text_empcode);
                TextView staff_names=(TextView)view.findViewById(R.id.staff_names);
                TextView mobile=(TextView)view.findViewById(R.id.mobile);
                TextView subject=(TextView)view.findViewById(R.id.subject);
                TextView url=(TextView)view.findViewById(R.id.url);

                applicationController.setappusername(staff_names.getText().toString());
                applicationController.setappusermobile(mobile.getText().toString());
                applicationController.setappusersubject(subject.getText().toString());
                applicationController.setappuserurl(url.getText().toString());
                Intent intent= new Intent(Staff_Attendance.this,Staff_Details.class);
                intent.putExtra("EMPID",Empcode.getText().toString());
                startActivity(intent);
            }
        });

        staff_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(adapter !=null){
                   adapter.getFilter().filter(s);
               }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(adapter !=null){
                    String text = staff_search.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);

                }

            }
        });

        new StaffATT().execute();
    }

    private class StaffATT extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Staff_Attendance.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Staff_Attendance.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STAFFATT,Para("11",applicationController.getBranchcode(),applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getDate(),applicationController.getATTTYPE()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        staff_helpers = new ArrayList<Staff_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String EmployeeCode=object.getString("EmployeeCode");
                            String ContactNo=object.getString("ContactNo");
                            String PhotoPath=object.getString("PhotoPath");
                            String FullName=object.getString("FullName");
                            String SubjectName=object.getString("SubjectName");
                            String DesignationName=object.getString("DesignationName");
                            String DepartmentName=object.getString("DepartmentName");
                            String Atttype=applicationController.getATTTYPE();
                            Staff_Helper item = new Staff_Helper(FullName,DepartmentName,SubjectName,PhotoPath,ContactNo,EmployeeCode,Atttype);
                            staff_helpers.add(item);
                            status=1;
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                     adapter = new Staff_Adapter(Staff_Attendance.this,R.layout.staff_item_list, staff_helpers);
                    staff_list.setAdapter(adapter);
                    break;
                case -2:
                    staff_list.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar
                            .make(layout_staff, "Attendance not found for selected date", Snackbar.LENGTH_LONG)
                            .setAction("BACK TO DASHBOARD", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                    onBackPressed();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    staff_list.setVisibility(View.GONE);
                    Snackbar snackbar1 = Snackbar
                            .make(layout_staff, "Attendance not found for selected date", Snackbar.LENGTH_LONG)
                            .setAction("BACK TO DASHBOARD", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                    onBackPressed();
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }

    public String Para(String action,String BranchCode,String SchoolCode,String SessionId,String FYId,String AttendanceDate,String LeaveGroup){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("AttendanceDate", AttendanceDate);
            jsonParam.put("LeaveGroup", LeaveGroup);
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
