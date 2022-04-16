package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class Staff_Details extends AppCompatActivity {

    ApplicationControllerAdmin applicationController;
    TextView staff_name,contact_number,subject_staff;
    CircularImageView imgstaff_details;
    ImageView staff_present;
    RelativeLayout layout_emp_details;
    String AttendanceCount_absentemp,AttendanceCount_presentemp,AttendanceCount_leaveemp;
    String staff_count;
    TextView staff_total_count,staff_present_count,staff_absent_count,staff_onleave_count;
    Button current_month,last_month;
    String date_current,emp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff__details);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        layout_emp_details=(RelativeLayout)findViewById(R.id.layout_emp_details);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout)findViewById(R.id.layout_emp_details));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Staff Details");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        Intent intent=getIntent();
        emp_id=intent.getStringExtra("EMPID");
        staff_total_count=(TextView)findViewById(R.id.staff_total_count);
        staff_present_count=(TextView)findViewById(R.id.staff_present_count);
        staff_absent_count=(TextView)findViewById(R.id.staff_absent_count);
        staff_onleave_count=(TextView)findViewById(R.id.staff_onleave_count);
        staff_present=(ImageView)findViewById(R.id.staff_present);
        staff_name=(TextView)findViewById(R.id.staff_name);
        contact_number=(TextView)findViewById(R.id.contact_number);
        subject_staff=(TextView)findViewById(R.id.subject_staff);
        imgstaff_details=(CircularImageView)findViewById(R.id.imgstaff_details);
        current_month=(Button)findViewById(R.id.current_month);
        last_month=(Button)findViewById(R.id.last_month);
        date_current=applicationController.getDate();

        current_month.setBackgroundColor(Color.parseColor("#07a36a"));
        current_month.setTextColor(Color.parseColor("#FFFFFF"));
        last_month.setBackgroundColor(Color.parseColor("#8B8B8B"));
        last_month.setTextColor(Color.parseColor("#FFFFFF"));

        current_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_month.setBackgroundColor(Color.parseColor("#07a36a"));
                current_month.setTextColor(Color.parseColor("#FFFFFF"));
                last_month.setBackgroundColor(Color.parseColor("#8B8B8B"));
                last_month.setTextColor(Color.parseColor("#FFFFFF"));


                date_current=applicationController.getDate();
                new staffapidetails().execute();
            }
        });

        last_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                last_month.setBackgroundColor(Color.parseColor("#07a36a"));
                last_month.setTextColor(Color.parseColor("#FFFFFF"));
                current_month.setBackgroundColor(Color.parseColor("#8B8B8B"));
                current_month.setTextColor(Color.parseColor("#FFFFFF"));

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                month=month-1;
				int day = 1;
                c.set(year, month, day);
                int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                date_current= year+"-"+month+"-"+numOfDaysInMonth+"";
                new staffapidetails().execute();
            }
        });
         new staffapidetails().execute();
        if(applicationController.getATTTYPE().equals("Absent")){
            staff_present.setImageResource(R.drawable.ic_absent_list);
        }else if(applicationController.getATTTYPE().equals("Leave")){
            staff_present.setImageResource(R.drawable.ic_onleave);
        }else{
            staff_present.setImageResource(R.drawable.ic_present);
        }
        staff_name.setText(applicationController.getappusername());
        contact_number.setText(applicationController.getappusermobile());
        subject_staff.setText(applicationController.getappusersubject());
        String imagerplace= ServerApiadmin.MAIN_IPLINK+applicationController.getappuserurl();
        imagerplace=imagerplace.replace("..","");
        imgstaff_details.setImageURI(Uri.parse(imagerplace));
        Glide
                .with(this)
                .load(imagerplace)
                .into(imgstaff_details);
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






    private class staffapidetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Staff_Details.this);
        @Override
        protected void onPreExecute() {
             progressDialog = ProgressDialog.show(Staff_Details.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STAFF_DETAILS_COUNT,Para1("12",applicationController.getBranchcode(),applicationController.getschoolCode(),emp_id,applicationController.getSeesionID(),applicationController.getFyID(),date_current),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        AttendanceCount_absentemp="0";
                         AttendanceCount_presentemp="0";
                        AttendanceCount_leaveemp="0";
                        staff_count="0";
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
                                staff_count=AttendanceCount;
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
             progressDialog.dismiss();
            switch (s){
                case 1:
                    staff_present_count.setText(AttendanceCount_presentemp);
                    staff_absent_count.setText(AttendanceCount_absentemp);
                    staff_onleave_count.setText(AttendanceCount_leaveemp);
                    staff_total_count.setText(staff_count);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_emp_details, "Attendance not found ", Snackbar.LENGTH_LONG)
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
                            .make(layout_emp_details, "Attendance not found ", Snackbar.LENGTH_LONG)
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
    public String Para1(String action,String BranchCode,String SchoolCode,String EmployeeCode, String SessionId,String FYId,String AttendanceDate){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("EmployeeCode", EmployeeCode);
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
