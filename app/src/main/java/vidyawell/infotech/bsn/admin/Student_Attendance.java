package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Adapters.Student_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Student_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Student_Attendance extends AppCompatActivity {


    Student_Adapter adapter;
    ListView student_list;
    List<Student_Helper> student_helpers;
    Student_Helper item;
    RelativeLayout student_layout;
    ApplicationControllerAdmin applicationController;
    TextView search_stu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__attendance);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout)findViewById(R.id.student_layout));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Student Attendance");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        student_layout=(RelativeLayout)findViewById(R.id.student_layout);
        search_stu=(TextView)findViewById(R.id.search_stu);
        student_list = (ListView) findViewById(R.id.student_list);
        student_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text_stucode = (TextView) view.findViewById(R.id.text_stucode);
                TextView student_names=(TextView)view.findViewById(R.id.student_names);
                TextView mobile_stu=(TextView)view.findViewById(R.id.mobile_stu);
                TextView class_name=(TextView)view.findViewById(R.id.class_name);
                TextView url_stu=(TextView)view.findViewById(R.id.url_stu);
                applicationController.setappusername(student_names.getText().toString());
                applicationController.setappusermobile(mobile_stu.getText().toString());
                applicationController.setappusersubject(class_name.getText().toString());
                applicationController.setappuserurl(url_stu.getText().toString());
                Intent intent= new Intent(Student_Attendance.this,Student_Details.class);
                intent.putExtra("STUID",text_stucode.getText().toString());
                startActivity(intent);
            }
        });

        search_stu.addTextChangedListener(new TextWatcher() {
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
                    String text = search_stu.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

            }
        });

        new StudentApi().execute();
    }
    private class StudentApi extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Student_Attendance.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Student_Attendance.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STUDENT_ATTEND,Para("11",applicationController.getBranchcode(),applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getDate(),applicationController.getATTTYPE()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        student_helpers = new ArrayList<Student_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String StudentCode=object.getString("StudentCode");
                            String MobileNo=object.getString("MobileNo");
                            String PhotoPath=object.getString("PhotoPath");
                            String FullName=object.getString("FullName");
                            String FatherName=object.getString("FatherName");
                            String ClassName=object.getString("ClassName");
                            String SectionName=object.getString("SectionName");
                            String Atttype=applicationController.getATTTYPE();
                            Student_Helper item = new Student_Helper(FullName,FatherName,MobileNo,ClassName,PhotoPath,StudentCode,SectionName,Atttype);
                            student_helpers.add(item);
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
                     adapter = new Student_Adapter(Student_Attendance.this,R.layout.student_item_list, student_helpers);
                     student_list.setAdapter(adapter);
                    break;
                case -2:
                    student_list.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar
                            .make(student_layout, "Attendance not found for selected date", Snackbar.LENGTH_LONG)
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
                    student_list.setVisibility(View.GONE);
                    Snackbar snackbar1 = Snackbar
                            .make(student_layout, "Attendance not found for selected date", Snackbar.LENGTH_LONG)
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

    public String Para(String action,String BranchCode,String SchoolCode,String SessionId,String FYId,String AttendanceDate,String AttendanceAbbrId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("LeaveGroup", AttendanceAbbrId);
            jsonParam.put("AttendanceDate", AttendanceDate);
            jsonParam.put("AttendanceAbbrId", null);
            jsonParam.put("ClassId", null);
            jsonParam.put("BranchId", null);
            jsonParam.put("SectionId", null);
            jsonParam.put("StreamId", null);
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



