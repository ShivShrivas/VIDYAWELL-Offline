package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Adapters.EmployeeList_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.EmployeeList_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class EmployeeList extends AppCompatActivity implements EmployeeList_Adapter.customButtonListener {
   // String []count={"2","1","2","0","2","1","2","0","2","1","2","0","2","1","2","1","2","1","2","1","2","1","2","1"};
    ListView teachers_list;
    List<EmployeeList_Helper> employeeList_helpers;
    EmployeeList_Helper item;
    ApplicationControllerAdmin applicationControllerAdmin;
    SharedPreferences sharedprefer;
    private Animation animationDown;
    RelativeLayout layout_teachers;
    EditText studeant_search;
    EmployeeList_Adapter adapter;
    String dipartment_id;
    SharedPreferences sharedpreferences;
    String EmployeeCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        applicationControllerAdmin=(ApplicationControllerAdmin) getApplicationContext();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_teachers));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sharedpreferences = getSharedPreferences("EMPLIST", Context.MODE_PRIVATE);
        Intent in=getIntent();
        dipartment_id =in.getStringExtra("DepartmentId");
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Employee List");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        teachers_list = (ListView) findViewById(R.id.teachers_list);
        layout_teachers=(RelativeLayout)findViewById(R.id.layout_teachers);
        studeant_search=(EditText)findViewById(R.id.studeant_search);
        teachers_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView emp_name =(TextView)view.findViewById(R.id.teachers_names);
                TextView emp_id =(TextView)view.findViewById(R.id.mobile_number);
                TextView emp_pic =(TextView)view.findViewById(R.id.text_image_gone);
                TextView emp_father_name =(TextView)view.findViewById(R.id.subject_history);
                TextView emp_code =(TextView)view.findViewById(R.id.emloyee_code);
                TextView emp_designation =(TextView)view.findViewById(R.id.designation);
                sharedpreferences = getSharedPreferences("EMPLIST", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FullName", emp_name.getText().toString());
                editor.putString("EmployeeId", emp_id.getText().toString());
                editor.putString("EmployeePhotoPath", emp_pic.getText().toString());
                editor.putString("FatherName", emp_father_name.getText().toString());
                editor.putString("EmployeeCode", emp_code.getText().toString());
                editor.putString("Designation", emp_designation.getText().toString());
                editor.clear();
                editor.commit();
                finish();
                //Toast.makeText(getApplicationContext(),emp_code.getText().toString(),Toast.LENGTH_LONG).show();
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
          new EmployeeDetails().execute();
    }

    @Override
    public void onButtonClickListner(int position, EmployeeList_Helper value) {
    }
    private class EmployeeDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(EmployeeList.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(EmployeeList.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.EMPLOYEE_API,Para(applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID(),dipartment_id),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        employeeList_helpers = new ArrayList<EmployeeList_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String EmployeeCode=object.getString("EmployeeCode");
                            String ContactNo=object.getString("EmployeeId");
                            String PhotoPath=object.getString("EmployeePhotoPath");
                            String FullName=object.getString("FullName");
                            String SubjectName=object.getString("FatherName");
                            String IsClassTeacher=object.getString("Designation");
                            item = new EmployeeList_Helper(EmployeeCode,ContactNo,PhotoPath,FullName,SubjectName,IsClassTeacher);
                            employeeList_helpers.add(item);
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
                    adapter = new EmployeeList_Adapter(getApplicationContext(),R.layout.teachers_item_list, employeeList_helpers);
                    teachers_list.setAdapter(adapter);
                    adapter.setCustomButtonListner(EmployeeList.this);
                    animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    teachers_list.setVisibility(View.VISIBLE);
                    teachers_list.startAnimation(animationDown);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_teachers, "Employee Data not found. Please try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_teachers, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Para(String SchoolCode, String BranchCode, String SessionId,String DepartmentId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("DepartmentId", DepartmentId);
            jsonParam.put("Action", "4");
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
