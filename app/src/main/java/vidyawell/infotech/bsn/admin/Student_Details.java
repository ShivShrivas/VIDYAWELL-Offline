package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;
public class Student_Details extends AppCompatActivity {

    ApplicationControllerAdmin applicationController;
    TextView students_name,contactstu_number,subject_stu;
    CircularImageView imgstu_details;
    ImageView student_present;
    TextView stu_total_count,stu_present_count,stu_absent_count,stu_onleave_count;
    RelativeLayout layout_stu_details;
    String AttendanceCount_absentemp,AttendanceCount_presentemp,AttendanceCount_leaveemp;
    String student_count;
    Button current_month_stu,last_month_stu;
    String date_current,stu_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__details);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout)findViewById(R.id.layout_stu_details));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Student Details");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        Intent intent=getIntent();
        stu_id=intent.getStringExtra("STUID");
        layout_stu_details=(RelativeLayout)findViewById(R.id.layout_stu_details);
        stu_total_count=(TextView)findViewById(R.id.stu_total_count);
        stu_present_count=(TextView)findViewById(R.id.stu_present_count);
        stu_absent_count=(TextView)findViewById(R.id.stu_absent_count);
        stu_onleave_count=(TextView)findViewById(R.id.stu_onleave_count);
        student_present=(ImageView)findViewById(R.id.student_present);
        students_name=(TextView)findViewById(R.id.students_name);
        contactstu_number=(TextView)findViewById(R.id.contactstu_number);
        subject_stu=(TextView)findViewById(R.id.subject_stu);
        imgstu_details=(CircularImageView)findViewById(R.id.imgstu_details);
        current_month_stu=(Button)findViewById(R.id.current_month_stu);
        last_month_stu=(Button)findViewById(R.id.last_month_stu);
        date_current=applicationController.getDate();
        current_month_stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_current=applicationController.getDate();
                new Studentapidetails().execute();
            }
        });

        last_month_stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                month=month-1;
                int day = 1;
                c.set(year, month, day);
                int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                date_current= year+"-"+month+"-"+numOfDaysInMonth+"";
                new Studentapidetails().execute();
            }
        });
        new Studentapidetails().execute();
        if(applicationController.getATTTYPE().equals("Absent")){
            student_present.setImageResource(R.drawable.ic_absent_list);
        }else if(applicationController.getATTTYPE().equals("Leave")){
            student_present.setImageResource(R.drawable.ic_onleave);
        }else{
            student_present.setImageResource(R.drawable.ic_present);
        }
        students_name.setText(applicationController.getappusername());
        contactstu_number.setText(applicationController.getappusermobile());
        subject_stu.setText(applicationController.getappusersubject());
        String imagerplace= ServerApiadmin.MAIN_IPLINK+applicationController.getappuserurl();
        imagerplace=imagerplace.replace("..","");
        imgstu_details.setImageURI(Uri.parse(imagerplace));
        Glide
                .with(this)
                .load(imagerplace)
                .into(imgstu_details);
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

    private class Studentapidetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Student_Details.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STUDENT_DETAILS_API,Para1("12",applicationController.getBranchcode(),applicationController.getschoolCode(),stu_id,applicationController.getSeesionID(),applicationController.getFyID(),date_current),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        AttendanceCount_presentemp="0";
                        AttendanceCount_presentemp="0";
                        AttendanceCount_leaveemp="0";
                        student_count="0";
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String  LeaveGroup=object.getString("LeaveGroup");
                            String AttendanceCount=object.getString("AttendanceCount");
                            if(LeaveGroup.equalsIgnoreCase("Absent")){
                                AttendanceCount_absentemp=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Present")){
                                AttendanceCount_presentemp=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Leave")){
                                AttendanceCount_leaveemp=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Total")){
                                student_count=AttendanceCount;
                            }
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
            // progressDialog.dismiss();
            switch (s){
                case 1:
                    stu_present_count.setText(AttendanceCount_presentemp);
                    stu_absent_count.setText(AttendanceCount_absentemp);
                    stu_onleave_count.setText(AttendanceCount_leaveemp);
                    stu_total_count.setText(student_count);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_stu_details, "Attendance not found ", Snackbar.LENGTH_LONG)
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

                    break;
            }
        }
    }
    public String Para1(String action,String BranchCode,String SchoolCode,String StudentCode, String SessionId,String FYId,String AttendanceDate){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("StudentCode", StudentCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            // jsonParam.put("LeaveGroup", "null");
            jsonParam.put("AttendanceDate", AttendanceDate);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

}
