package vidyawell.infotech.bsn.admin;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vidyawell.infotech.bsn.admin.Adapters.BranchList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Emp_Attendance_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.SectionList_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.BranchList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Class_Attendance_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Emp_Attendance_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Employee_Attendance_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Emp_Attendance extends AppCompatActivity implements Emp_Attendance_Adapter.customButtonListener,Emp_Attendance_Adapter.customButtonListener2,Emp_Attendance_Adapter.customButtonListener3 {

    TextView topic, subject_title, homelistvalue, submissiondate, homedate;
    //ListView class_attendance_list;
    List<Class_Attendance_Helper> class_attendance_helpers;
   // Class_Attendance_Helper item;
   // RelativeLayout class_attendance_layout;
    Button stu_present, stu_absent;
    HashMap <String,String> store_att_hashmap;
    LinearLayout list_classatt;
    ValueAnimator mAnimator;
    private Animation animationDown;
    Spinner select_class,select_section,select_branch;
    ApplicationControllerAdmin applicationController;
    public  ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    public  ArrayList<SectionList_Helper> CustomListSection= new ArrayList<SectionList_Helper>();
    public  ArrayList<BranchList_Helper> CustomListBranch= new ArrayList<BranchList_Helper>();
    Emp_Attendance_Adapter adapter;
    Emp_Attendance activity = null;
    String class_ID,section_ID,branch_ID,todaysDate,Message;
    Button btn_search, buttonfooter,att_submit_update;
    String[]  arraylist_codes;
    boolean att_missed=true,list_button_show=true;
    Employee_Attendance_Helper st;
    Typeface typeface;
    String AttendanceAbbrId_present;
    ApplicationControllerAdmin applicationControllerAdmin;
    String Abbreviation="P";
    String AttendanceCount_absent,AttendanceCount_present,AttendanceCount_leave,AttendanceCount_absentemp,AttendanceCount_presentemp,AttendanceCount_leaveemp;
    TextView stuatt_total,student_present,student_absent,stu_onleave,staff_total,staff_not_taken_layout,student_not_taken_layout,staff_present,staff_absent,staff_onleave,top_username,top_userdesgn;
    String staff_count,staff_not_taken,student_count,student_not_taken, AdminName,Designation,PhotoPath,Address,ContactNo,Email,Headline_notice,Headline_notice2;
    String AllotedLeaves,LeaveTaken,Balance,student_action;
    float stu_att_percent;
    ListView employee_list;
    List<Emp_Attendance_Helper> emp_attendance_helpers;
    Emp_Attendance_Helper item1;
    RelativeLayout emp_attendance_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp__attendance);
        applicationController=(ApplicationControllerAdmin) getApplication();
        applicationControllerAdmin=(ApplicationControllerAdmin) getApplicationContext();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.emp_attendance_layout));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Employee Attendance");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
      //  class_attendance_layout = (RelativeLayout) findViewById(R.id.class_attendance_layout);
        list_classatt = (LinearLayout) findViewById(R.id.list_classatt);
      //  class_attendance_list = (ListView) findViewById(R.id.class_attendance_list);
        select_class=(Spinner)findViewById(R.id.select_class);
        select_section=(Spinner)findViewById(R.id.select_section);
        select_branch=(Spinner)findViewById(R.id.select_branch);
        btn_search=(Button)findViewById(R.id.button_searchlist);
        student_present=(TextView)findViewById(R.id.student_present);
        student_absent=(TextView)findViewById(R.id.student_absent);
        stuatt_total=(TextView)findViewById(R.id.stuatt_total);
        stu_onleave=(TextView)findViewById(R.id.stu_onleave);
        emp_attendance_layout=(RelativeLayout)findViewById(R.id.emp_attendance_layout);
        employee_list =(ListView)findViewById(R.id.employee_list);
        list_classatt.setVisibility(View.GONE);
        store_att_hashmap = new HashMap();
        activity=this;
       // TextView text_viewselect_class=(TextView)findViewById(R.id.text_viewselect_class);
       // TextView text_viewselect_Section=(TextView)findViewById(R.id.text_viewselect_Section);
       /* if(applicationController.getschooltype().equals("0")){
            text_viewselect_class.setText("Select Class");
            text_viewselect_Section.setText("Select Section");
        }else{
            text_viewselect_class.setText("Select Course");
            text_viewselect_Section.setText("Select Semester");
        }*/
        TextView tv_list=(TextView)findViewById(R.id.date_list);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);
        tv_list.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;

        if(applicationController.getLoginType().equalsIgnoreCase("4")){
            student_action="20";
        }else {
            student_action="4";
        }
       // store_att_hashmap.clear();
       /* btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store_att_hashmap.clear();
                list_button_show=true;
                //new StudentList().execute();
                new Studentapicount().execute();
            }
        });*/
        new EmployeeDetails().execute();
        new AbbreviationList().execute();
      //  new SelectclassDetails().execute();
       // new SelectBranch().execute();

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.empatt_listview_footer, employee_list, false);
        buttonfooter = (Button) footer.findViewById(R.id.att_submit_footer);
        buttonfooter.setFocusable(false);
        buttonfooter.setText("SUBMIT");
        employee_list.addFooterView(footer, null, false);


        ////Submit button///////

        buttonfooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(buttonfooter.getText().toString().equalsIgnoreCase("SUBMIT")) {
                    for (int i = 0; i < arraylist_codes.length; i++) {
                        if(store_att_hashmap.containsKey(arraylist_codes[i])){

                        }else{
                            att_missed=false;
                        }}
                    if(att_missed==false){
                        Snackbar snackbar = Snackbar
                                .make(emp_attendance_layout,"Please mark all student attendance", Snackbar.LENGTH_LONG)
                                .setAction("Error", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        snackbar.setActionTextColor(Color.RED);
                        snackbar.show();
                    }else{
                        retrieveValuesFromListMethod(store_att_hashmap);
                    }


                }else {
                    retrieveValuesFromListMethod(store_att_hashmap);

                }

            }
        });
    }
    /*    buttonfooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // att_submit_update.setVisibility(View.VISIBLE);
                for (int i = 0; i < arraylist_codes.length; i++) {
                if(store_att_hashmap.containsKey(arraylist_codes[i])){

                }else{
                    att_missed=false;
                    //Abbreviation="P";
                   // retrieveValuesFromListMethod(store_att_hashmap);
                }}
                 if(att_missed==false){
                     Snackbar snackbar = Snackbar
                             .make(class_attendance_layout,"Please mark all student attendance", Snackbar.LENGTH_LONG)
                             .setAction("Error", new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                 }
                             });
                     snackbar.setActionTextColor(Color.RED);
                     snackbar.show();
                 }else{
                     retrieveValuesFromListMethod(store_att_hashmap);
                 }
            }
        });*/

/*
        att_submit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < arraylist_codes.length; i++) {
                    if(store_att_hashmap.containsKey(arraylist_codes[i])){


                    }}
                retrieveValuesFromListMethod(store_att_hashmap);

            }
        });
*/




    private void retrieveValuesFromListMethod(Map map) {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        ArrayList<String> ar = new ArrayList<String>();
        String key;
        String value;
        int counter = 0;
        st=new Employee_Attendance_Helper();
        List<Employee_Attendance_Helper.EmployeeDetails> pnList = new ArrayList<Employee_Attendance_Helper.EmployeeDetails>();
        while(itr.hasNext())
        {
            counter++;
            key = (String)itr.next();
            value = (String)map.get(key);
            System.out.println(value);
            System.out.println(key);
            System.out.println(counter);
            ar.add(value);
            Employee_Attendance_Helper.EmployeeDetails sd=st.new EmployeeDetails();
            sd.setStudentCode(key);
            sd.setAttendanceAbbrId(value);
            sd.setSrNo(counter+"");
            sd.setCreatedBy(applicationController.getUserID());
            sd.setAttendanceDate(todaysDate);
            pnList.add(sd);
        }
        st.setEmployeeList(pnList);
        System.out.println(ar.toString());

       //////////////////API Insert and update/////
        if(buttonfooter.getText().toString().equalsIgnoreCase("SUBMIT")) {
            new StudentAttendance().execute();
        }else {
            new StudentAttendanceupdate().execute();

        }




        // Toast.makeText(getApplicationContext(), store_att_hashmap + "---DELETE", Toast.LENGTH_LONG).show();
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
    @Override
    public void onButtonClickListner(int position, Emp_Attendance_Helper value) {
        //AttendanceAbbrId_present =value.AttendanceAbbrId;
      //  Abbreviation="P";
        String studentid = value.getEmployeeCode();
        store_att_hashmap.put(studentid, applicationController.getpresent_code());
        att_missed=true;
        //Toast.makeText(getApplicationContext(), studentid + "---ID", Toast.LENGTH_LONG).show();

    }
    @Override
    public void onButtonClickListner2(int position, Emp_Attendance_Helper value) {
        String studentid = value.getEmployeeCode();
        store_att_hashmap.put(studentid, applicationController.getabsent_code());
        att_missed=true;

    }
    private void expand() {
        //set Visible
        list_classatt.setVisibility(View.VISIBLE);
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        list_classatt.measure(widthSpec, heightSpec);
        mAnimator = slideAnimator(0,list_classatt.getMeasuredHeight());
        mAnimator.start();
    }
    private void collapse() {
        int finalHeight = list_classatt.getHeight();
        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                list_classatt.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }
    private ValueAnimator slideAnimator(int start, int end) {
           ValueAnimator animator = ValueAnimator.ofInt(start, end);
           animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = list_classatt.getLayoutParams();
                layoutParams.height = value;
                list_classatt.setLayoutParams(layoutParams);
            }

        });
        return animator;
    }

    @Override
    public void onButtonClickListner3(int position, Emp_Attendance_Helper value) {
        String studentid = value.getEmployeeCode();
        store_att_hashmap.put(studentid, applicationController.getleave_code());
        att_missed=true;

    }

    ///////////////////////////ClassStudent_List insert////////////////////////////////////////////
    private class StudentAttendance extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Attendance.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Emp_Attendance.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.EMPLOYEE_ATTENDANCE_SUBMIT,ParastudentAtt(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            String test = applicationController.getServicesapplication();
            if(response!=null){
                status=1;
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
                    showSuccessDialog();
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(emp_attendance_layout, "Employee List not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(emp_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }
    public String ParastudentAtt(String SchoolCode,String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",FYId);
            jsonParam.put("Action","1");
          //  jsonParam.put("ClassId",class_ID);
           // jsonParam.put("SectionId",section_ID);
            jsonParam.put("UpdatedBy", applicationController.getUserID());
           // jsonParam.put("BranchId",branch_ID);

            JSONArray jsonArr = new JSONArray();
            for (Employee_Attendance_Helper.EmployeeDetails pn : st.getEmployeeList() ) {
                JSONObject pnObj = new JSONObject();
                pnObj.put("EmployeeCode", pn.getStudentCode());
                pnObj.put("AttendanceAbbrId", pn.getAttendanceAbbrId());
                pnObj.put("SrNo", pn.getSrNo());
                pnObj.put("CreatedBy", pn.getCreatedBy());
                pnObj.put("AttendanceDate",todaysDate);

                jsonArr.put(pnObj);
            }
            jsonParam.put("attendance", jsonArr);
            jsonParam1.put("obj", jsonParam);
            System.out.println(jsonParam1.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonParam1.toString();
    }

//////////update submit api ///////////////////////////////

    private class StudentAttendanceupdate extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Attendance.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Emp_Attendance.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.EMPLOYEE_ATTENDANCE_Update,ParastudentAtt1(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            String test2 = applicationController.getServicesapplication();
            if(response!=null){
                status=1;
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
                    showSuccessDialog();
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(emp_attendance_layout, "Employee List not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(emp_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }
    public String ParastudentAtt1(String SchoolCode,String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",FYId);
            jsonParam.put("Action","20");
           // jsonParam.put("ClassId",class_ID);
          //  jsonParam.put("SectionId",section_ID);
            jsonParam.put("UpdatedBy", applicationController.getUserID());

          //  jsonParam.put("BranchId",branch_ID);

            JSONArray jsonArr = new JSONArray();
            for (Employee_Attendance_Helper.EmployeeDetails pn : st.getEmployeeList() ) {
                JSONObject pnObj = new JSONObject();
                pnObj.put("EmployeeCode", pn.getStudentCode());
                pnObj.put("AttendanceAbbrId", pn.getAttendanceAbbrId());
                pnObj.put("SrNo", pn.getSrNo());
                pnObj.put("CreatedBy", pn.getCreatedBy());
                pnObj.put("AttendanceDate",todaysDate);
                jsonArr.put(pnObj);
            }
            jsonParam.put("attendance", jsonArr);
            jsonParam1.put("obj", jsonParam);
            System.out.println(jsonParam1.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonParam1.toString();
    }



    private void showSuccessDialog() {
        final Dialog dialog = new Dialog(Emp_Attendance.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.success_dialog);
        dialog.setTitle("");
        // set the custom dialog components - text, image and button
        TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        text_label.setText("Employee Attendance");
        TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
        text_message.setText("Employee Attendance submit successfully");
        text_toplabel.setTypeface(typeface);
        Button dialogButton = (Button) dialog.findViewById(R.id.button_closed);
        text_toplabel.setTypeface(typeface);
        text_label.setTypeface(typeface);
        text_message.setTypeface(typeface);
        dialogButton.setTypeface(typeface);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=getIntent();
                finish();
                startActivity(in);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    ///////////////////////////ClassStudent_List////////////////////////////////////////////
    private class EmployeeDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Attendance.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Emp_Attendance.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.STAFF_ATTENDANCE,Para(applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        arraylist_codes=new String[array.length()];
                        emp_attendance_helpers = new ArrayList<Emp_Attendance_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   EmployeeCode=object.getString("EmployeeCode");///StudentCode StudentId
                            String   EmpName=object.getString("EmpName");
                            String   FatherName=object.getString("FatherName");
                            String   AttendanceDate=object.getString("AttendanceDate");
                            String   AttendanceAbbrId=object.getString("AttendanceAbbrId");
                            String   EmployeeId=object.getString("EmployeeId");

                            item1 = new Emp_Attendance_Helper(EmployeeCode,EmpName,FatherName,AttendanceDate,AttendanceAbbrId,i + 1 + " .",EmployeeId);
                            emp_attendance_helpers.add(item1);

                            arraylist_codes[i]=EmployeeCode;
                            if(AttendanceDate.equalsIgnoreCase("null")){
                                list_button_show=true;
                            }else{
                                list_button_show=false;
                            }
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

                    adapter = new Emp_Attendance_Adapter(getApplicationContext(),R.layout.emp_attendance_item_list, emp_attendance_helpers);
                    employee_list.setAdapter(adapter);
                    adapter.setCustomButtonListner(Emp_Attendance.this);
                    adapter.setCustomButtonListner2(Emp_Attendance.this);
                    adapter.setCustomButtonListner3(Emp_Attendance.this);
                    animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    list_classatt.setVisibility(View.VISIBLE);
                    employee_list.setVisibility(View.VISIBLE);
                    employee_list.startAnimation(animationDown);

                    if(list_button_show==false){
                        //buttonfooter.setVisibility(View.GONE);
                        //buttonfooter.setVisibility(View.VISIBLE);
                        buttonfooter.setText("UPDATE");
                        Snackbar snackbar = Snackbar
                                .make(emp_attendance_layout, "Employee Attendance has been Submitted for this Date", Snackbar.LENGTH_LONG)
                                .setAction("Attendance Done", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                        snackbar.setActionTextColor(Color.RED);
                        snackbar.show();
                    }else{
                        buttonfooter.setText("SUBMIT");
                        //buttonfooter.setVisibility(View.GONE);
                        // att_submit_update.setVisibility(View.GONE);
                    }
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(emp_attendance_layout, "Employee Data not found. Please try Again", Snackbar.LENGTH_LONG)
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
                            .make(emp_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Para(String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
           // jsonParam.put("DepartmentId", DepartmentId);
            jsonParam.put("Action", "5");
            jsonParam.put("AttendanceDate",todaysDate);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
  /*  private class SelectclassDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Attendance.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Emp_Attendance.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.ATTENDANCE_SELECT,Paradetails(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   ClassId=object.getString("ClassId");
                            String   ClassName=object.getString("ClassName");

                            final ClassList_Helper sched = new ClassList_Helper();
                            sched.setClassID(ClassId);
                            sched.setClassName(ClassName);
                            CustomListViewValuesArr.add(sched);

                            status=1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-2;
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
                    Resources res = getResources();
                    adapter = new ClassList_Adapter(getApplicationContext(), R.layout.spinner_class_item, CustomListViewValuesArr,res);
                    select_class.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(class_attendance_layout, "Class Dta Not Found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   *//* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*//*
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(class_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   *//* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*//*
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }
    public String Paradetails(String SchoolCode,String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("CreatedBy", applicationController.getUserID());
            jsonParam.put("Action","7");
           jsonParam.put("FYId",FYId);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }*/
    ///////////////////////////////////////////Section Selection//////////////////////////////////
    private class SelectSection extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Attendance.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Emp_Attendance.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String f=applicationController.getServicesapplication()+ServerApiadmin.ATTENDANCE_SELECTSECTION;
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.ATTENDANCE_SELECTSECTION,ParaSec(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        CustomListSection = new ArrayList<SectionList_Helper>();
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   SectionId=object.getString("SectionId");
                            String   SectionName=object.getString("SectionName");
                            final SectionList_Helper sched = new SectionList_Helper();
                            sched.setSecID(SectionId);
                            sched.setSecName(SectionName);
                            CustomListSection.add(sched);
                            status=1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-2;
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
                    Resources res = getResources();
                    SectionList_Adapter adapter = new SectionList_Adapter(getApplicationContext(), R.layout.spinner_section_item, CustomListSection,res);
                    select_section.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(emp_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(emp_attendance_layout, "Data Not Found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }
    public String ParaSec(String SchoolCode,String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",FYId);
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("CreatedBy", applicationController.getUserID());
            jsonParam.put("Action","7");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    //////////////////////////////////////////////////////////////////////////////////////////// AbbreviationList////////////
    private class AbbreviationList extends AsyncTask<String, String, Integer> {
     // ProgressDialog progressDialog = new ProgressDialog(Class_Attendance.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(Class_Attendance.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.API_ABBREVATION,ParaAbb(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   AttendanceAbbrName=object.getString("Name");
                            String   AttendanceAbbrId=object.getString("AttendanceAbbrId");
                            if(AttendanceAbbrName.equalsIgnoreCase("Absent")){
                                applicationController.setabsent_code(AttendanceAbbrId);
                            }
                            if(AttendanceAbbrName.equalsIgnoreCase("Present")){

                                applicationController.setpresent_code(AttendanceAbbrId);
                            }
                            if(AttendanceAbbrName.equalsIgnoreCase("Leave")){
                                applicationController.setleave_code(AttendanceAbbrId);
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
          //  progressDialog.dismiss();
            switch (s){
                case 1:
                    break;
                case -2:
                    break;
                case -1:
                    break;
            }
        }
    }
    public String ParaAbb(String SchoolCode,String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",FYId);
            jsonParam.put("Action","4");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    /////////////////////////////////////select branch name///////////////////////////////
    private class SelectBranch extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Emp_Attendance.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Emp_Attendance.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.ATTENDANCE_SELECTBRANCH,ParaBranch(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        CustomListBranch = new ArrayList<>();
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   BranchId=object.getString("BranchId");
                            String   BranchName=object.getString("BranchName");
                            final BranchList_Helper sched = new BranchList_Helper();
                            sched.setBranch_ID(BranchId);
                            sched.setBrach_Name(BranchName);
                            CustomListBranch.add(sched);
                            status=1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-2;
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
                    Resources res = getResources();
                    BranchList_Adapter adapter = new BranchList_Adapter(getApplicationContext(), R.layout.spinner_branch_item, CustomListBranch,res);
                    select_branch.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(emp_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(emp_attendance_layout, "Branch Data Not Found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }
    public String ParaBranch(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("CreatedBy", applicationController.getUserID());
            jsonParam.put("Action","7");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    ///////////////////Student_Attendance API ///////////
    private class Studentapicount extends AsyncTask<String, String, Integer> {
        // ProgressDialog progressDialog = new ProgressDialog(MainActivity_Admin.this);

        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ ServerApiadmin.STUDENT_ATTENDANCE,Para(student_action,applicationController.getBranchcode(),applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getDate()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        AttendanceCount_absent="0";
                        AttendanceCount_present ="0";
                        AttendanceCount_leave ="0";
                        student_count="0";
                        student_not_taken="0";
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String  LeaveGroup=object.getString("LeaveGroup");
                            String AttendanceCount=object.getString("AttendanceCount");
                            if(LeaveGroup.equalsIgnoreCase("Absent")){
                                AttendanceCount_absent=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Present")){
                                AttendanceCount_present=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Leave")){
                                AttendanceCount_leave=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Total")){
                                student_count=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Not Taken")){
                                student_not_taken=AttendanceCount;
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
                    student_present.setText(AttendanceCount_present);
                    student_absent.setText(AttendanceCount_absent);
                    stu_onleave.setText(AttendanceCount_leave);
                    int a=Integer.parseInt(AttendanceCount_present);
                    int b=Integer.parseInt(AttendanceCount_absent);
                    int c=Integer.parseInt(AttendanceCount_leave);
                    int d=Integer.parseInt(student_count);
                    stuatt_total.setText(student_count);
                   // student_not_taken_layout.setText("Not Taken: "+student_not_taken);
                   /* if(student_not_taken.equals("0")){
                        student_not_taken_layout.setVisibility(View.GONE);
                    }else {
                        student_not_taken_layout.setVisibility(View.VISIBLE);
                    }*/

                    if (d>0){
                        stu_att_percent=(float)(a*100)/d;
                    }else{
                        stu_att_percent=0;
                    }

                  /*  if (d>0){
                        stu_att_percent=(float)(a*100)/d;
                    }else{
                        stu_att_percent=0;
                    }
                    createprogress();
                    loaddataonprogress();*/

                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(emp_attendance_layout, "Student Attendance not found for selected date", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(emp_attendance_layout, "Student Attendance not found for selected date", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    Message="Student Attendance not Found for Selected Date";
                    // showSuccessDialog();
                    break;
            }
        }
    }
    public String Para(String action,String BranchCode,String SchoolCode,String SessionId,String FYId,String AttendanceDate){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("AttendanceDate", AttendanceDate);
            jsonParam.put("LeaveGroup", null);
            jsonParam.put("AttendanceAbbrId", null);
            jsonParam.put("ClassId", null);
            jsonParam.put("BranchId", null);
            jsonParam.put("SectionId", null);
            jsonParam.put("StreamId", null);
            if(applicationController.getLoginType().equalsIgnoreCase("4")){
                jsonParam.put("StudentCode", applicationController.getActiveempcode());
            }
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

}
