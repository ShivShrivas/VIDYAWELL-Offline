package vidyawell.infotech.bsn.admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import vidyawell.infotech.bsn.admin.MobileAttendance.EmployeeAttendance;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Profile extends AppCompatActivity {
    LinearLayout upload_documents,layout_marks_entry;
    TextView change_password;
    CircleImageView profile_image;
    ApplicationControllerAdmin applicationControllerAdmin;
    String   BranchLat,BranchLog,BranchRedius,imeiNumber;
    TelephonyManager telephonyManager;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin=(ApplicationControllerAdmin)getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout)findViewById(R.id.layout_main_profile));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Profile");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        final View img_logo=findViewById(R.id.main_profilepic_big);
        profile_image=findViewById(R.id.main_profilepic_big);
        upload_documents=(LinearLayout)findViewById(R.id.upload_documents);
        layout_marks_entry=(LinearLayout)findViewById(R.id.layout_marks_entry);
        //change_password=findViewById(R.id.change_password);
        LinearLayout linearLayout_att=(LinearLayout)findViewById(R.id.attendance_status_layout);


        layout_marks_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Profile.this,Result_Submit_Classwise.class);
                startActivity(intent);
            }
        });

        upload_documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Profile.this,Upload_Document.class);
                startActivity(intent);
            }
        });

        linearLayout_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Employee_AttStatus.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayout_routine=(LinearLayout)findViewById(R.id.class_routine);
        linearLayout_routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applicationControllerAdmin.setuser_event("R");
                Intent intent = new Intent(Profile.this, Time_table.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayout_leavestatus=(LinearLayout)findViewById(R.id.layout_leave_status);
        linearLayout_leavestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Emp_Leave_Status.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayout_stasff_Bus=(LinearLayout)findViewById(R.id.stasff_Bus);
        linearLayout_stasff_Bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Vehicle_Activity.class);
                startActivity(intent);
            }
        });

       LinearLayout student_query=(LinearLayout)findViewById(R.id.student_query);
        student_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent =new Intent(Profile.this,Student_Query.class);
              startActivity(intent);
            }
        });

        LinearLayout layout_mobileatt=(LinearLayout)findViewById(R.id.layout_mobileatt);
        layout_mobileatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceId();
            }
        });
        TextView tv_sname=(TextView)findViewById(R.id.students_name) ;
        TextView tv_sdesgn=(TextView)findViewById(R.id.students_class) ;
        tv_sname.setText(applicationControllerAdmin.getuserName());
        tv_sdesgn.setText(applicationControllerAdmin.getUser_desgn());
        String imagerplace= ServerApiadmin.MAIN_IPLINK+applicationControllerAdmin.getProfilePIC();
        imagerplace=imagerplace.replace("..","");
        profile_image.setImageURI(Uri.parse(imagerplace));
        Glide
                .with(Profile.this)
                .load(imagerplace)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(profile_image);

        checkPermissions();
    }


    private class GetULR extends AsyncTask<String, String, Integer> {
           //ProgressDialog progressDialog = new ProgressDialog(Login.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Login.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.GETURL_API,Paracode("1"),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        JSONObject jsonobject = jsonArray.getJSONObject(0);
                        BranchLat=jsonobject.getString("BranchLat");
                        BranchLog=jsonobject.getString("BranchLog");
                        BranchRedius=jsonobject.getString("BranchRedius");
                        applicationControllerAdmin.setBranchLat(BranchLat);
                        applicationControllerAdmin.setBranchLon(BranchLog);
                        applicationControllerAdmin.setBranchRedius(BranchRedius);
                        status=1;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-2;
                    }
                }else{
                    status=-2;
                }
            }else{
                status=-2;
            }
            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            // progressDialog.dismiss();
            switch (s){
                case 1:
                    Intent intent =new Intent(Profile.this, EmployeeAttendance.class);
                    intent.putExtra("LAT",BranchLat);
                    intent.putExtra("LON",BranchLog);
                    intent.putExtra("RED",BranchRedius);
                    intent.putExtra("IMEI",imeiNumber);
                    startActivity(intent);
                    break;
                case -2:
                    Toast.makeText(getApplicationContext(),"Geo-fence not set by administrator ",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Paracode(String school_code){
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "1");
            jsonParam.put("BranchCode", applicationControllerAdmin.getschoolCode()+applicationControllerAdmin.getBranchcode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam.toString();
    }
    private void deviceId() {
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }else{
            imeiNumber = telephonyManager.getDeviceId();
            new GetULR().execute();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        return;
                    }
                    imeiNumber = telephonyManager.getDeviceId();
                    new GetULR().execute();

                } else {
                    Toast.makeText(Profile.this,"Without permission we check",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(Profile.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission();
            }
        }
    }
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Profile.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(Profile.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        } else {
            ActivityCompat.requestPermissions(Profile.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_password, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.changepassword:
            //  Toast.makeText(getApplicationContext(),"toast",Toast.LENGTH_SHORT).show();
            Intent in=new Intent(getApplicationContext(),Change_Password.class);
            startActivity(in);
            return(true);
        case android.R.id.home:
            // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
            // if this doesn't work as desired, another possibility is to call `finish()` here.
            onBackPressed();
            return true;
    }
        return(super.onOptionsItemSelected(item));
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
