package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Adapters.Student_Query_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Student_Query_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Student_Query extends AppCompatActivity{


    Student_Query_Adapter adapter;

    TextView topic, subject_title, homelistvalue, submissiondate, homedate;
    ListView teachers_list;
    List<Student_Query_Helper> student_query_helpers;
    Student_Query_Helper item;
    String number;
    private Animation animationDown;
    ApplicationControllerAdmin applicationControllerAdmin;
    RelativeLayout stu_query_layout;
    EditText studeant_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__query);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin = (ApplicationControllerAdmin) getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.stu_query_layout));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Student Query");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        stu_query_layout=(RelativeLayout)findViewById(R.id.stu_query_layout);
        teachers_list = (ListView) findViewById(R.id.teachers_list);
        studeant_search=(EditText)findViewById(R.id.studeant_search);
        new Teachersdtails().execute();

        teachers_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_tname=(TextView)view.findViewById(R.id.teachers_names);
                TextView TeacherId=(TextView)view.findViewById(R.id.studentcode);
                Intent intent= new Intent(Student_Query.this,Conversation_Thread.class);
                intent.putExtra("FullName",tv_tname.getText().toString());
                intent.putExtra("Code",TeacherId.getText().toString());
                startActivity(intent);
            }
        });




        studeant_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()>1){
                    adapter.getFilter().filter(s);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String text = studeant_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });

    }


    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                //callcomplete();
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission else is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                   // callcomplete();

                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private class Teachersdtails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Student_Query.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Student_Query.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.Student_Query_API,Para(applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            String api=ServerApiadmin.Student_Query_API;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        student_query_helpers = new ArrayList<Student_Query_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String FullName=object.getString("FullName");
                            String FatherName=object.getString("FatherName");
                            String MotherName=object.getString("MotherName");
                            String GuardianContactNo=object.getString("GuardianContactNo");
                            String StudentPhoto=object.getString("StudentPhoto");
                            String StudentCode=object.getString("Code");
                            item = new Student_Query_Helper(FullName,FatherName,MotherName,GuardianContactNo,StudentPhoto,StudentCode);
                            student_query_helpers.add(item);
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
                    teachers_list.setVisibility(View.VISIBLE);
                    adapter = new Student_Query_Adapter(getApplicationContext(),R.layout.student_query_item_list, student_query_helpers);
                    teachers_list.setAdapter(adapter);
                   //adapter.setCustomButtonListner((Student_Query_Adapter.customButtonListener) Student_Query.this);
                    animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    teachers_list.setVisibility(View.VISIBLE);
                    teachers_list.startAnimation(animationDown);
                    break;
                case -2:
                   // toast_message="Student Query Data Not Found. Please Try Again";
                   // showErrorDialog();
                    Snackbar snackbar = Snackbar
                            .make(stu_query_layout, "Student Query Data Not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    //Toast.makeText(getApplicationContext(),"Student Query Data Not Found. Please Try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                   // toast_message="Network Congestion! Please try Again";
                   // showErrorDialog();
                    Snackbar snackbar1 = Snackbar
                            .make(stu_query_layout, "Network Congestion! Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                   // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    teachers_list.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String Para(String StudentCode, String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("StudentCode", applicationControllerAdmin.getActiveempcode());
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
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
}


