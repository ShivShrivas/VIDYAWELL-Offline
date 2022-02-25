package vidyawell.infotech.bsn.admin;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Complaint_View_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Student_List_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Complaint_View_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_List_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static vidyawell.infotech.bsn.admin.GlobalClass.Std_List;

public class Student_List extends AppCompatActivity implements  Student_List_Adapter.CheckcustomButtonListener {
    private static int select_student_count;
    ListView student_list_list;
    List<Student_List_Helper> student_list_helpers;
    Student_List_Helper item;
    SharedPreferences sharedprefer;
    ApplicationControllerAdmin applicationController;
    Spinner select_class,select_section;

    ClassList_Adapter adapter;
    Class_Attendance activity = null;
    String class_ID,section_ID,todaysDate;
    Button btn_search;
    RelativeLayout font_complaint_view;
    ValueAnimator mAnimator;
    private Animation animationDown;
    EditText student_search,edttext_complaint;
    Student_List_Adapter student_list_adapter;
    HashMap<String,String> store_att_hashmap;
    StringBuilder sb_codes;
    String complaint_txt;
    CheckBox check_select_all;
    boolean check_status=false;
    RelativeLayout layout_select_all;
    LinearLayout chat_layout;
    TextView text_comp_class;
    TextView text_comp_section;
    Typeface typeface;
    String ClassId,SectionId,StreamId,BranchId;
    Button button_finish;
    String st_code;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.font_student_list));
        applicationController = (ApplicationControllerAdmin) getApplicationContext();
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Student List");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);


        font_complaint_view=(RelativeLayout)findViewById(R.id.font_student_list);
        student_search=(EditText)findViewById(R.id.student_search);
        edttext_complaint=(EditText)findViewById(R.id.Edit_complaint);
        check_select_all=(CheckBox)findViewById(R.id.check_select_All);
        chat_layout=(LinearLayout)findViewById(R.id.chat_layout);
        layout_select_all=(RelativeLayout)findViewById(R.id.layout_select_all);
        button_finish=(Button)findViewById(R.id.button_finish);
        layout_select_all.setVisibility(View.GONE);
        sharedprefer = getSharedPreferences("STUPROFILE", Context.MODE_PRIVATE);
        String theme_code = sharedprefer.getString("Theme_Code", "0");
        sharedpreferences = getSharedPreferences("STSCOUNT", Context.MODE_PRIVATE);
        student_list_list = (ListView) findViewById(R.id.student_list_list);
        store_att_hashmap = new HashMap();


        Intent in=getIntent();
        ClassId=in.getStringExtra("ClassId");
        SectionId=in.getStringExtra("SectionId");
        StreamId=in.getStringExtra("StreamId");
        BranchId=in.getStringExtra("BranchId");

        new StudentList().execute();
        check_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true){
                    check_status=true;
                    new StudentList().execute();

                }else{
                    check_status=false;
                    store_att_hashmap.clear();
                    new StudentList().execute();
                }


            }
        });

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveValuesFromListMethod(store_att_hashmap);



                Std_List=sb_codes.toString();
                finish();
                Intent refresh = new Intent(getApplicationContext(), Virtual_Class.class);
                startActivity(refresh);//Start the same Activity
                finish();

              /*  SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("std_list", sb_codes.toString());
                editor.commit();
                editor.clear();
                finish();*/

              //st_code=sb_codes;

              /*  Intent intent = new Intent(Student_List.this, Virtual_Class.class);
                intent.putExtra("std_list",sb_codes.toString());
                startActivityForResult(intent,100);*/

               // Toast.makeText(getApplicationContext(),"sb_codes"+sb_codes,Toast.LENGTH_SHORT).show();

            }
        });



    }


    private void retrieveValuesFromListMethod(Map map)
    {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        ArrayList<String> ar = new ArrayList<String>();
        sb_codes = new StringBuilder(map.size());
        select_student_count=map.size();
        String key;
        String value;
        while(itr.hasNext())
        {
            key = (String)itr.next();
            value = (String)map.get(key);
            System.out.println(value);
            ar.add(value);
            sb_codes.append(value+",");
        }
        System.out.println(ar.toString());
        System.out.println(sb_codes.toString());
        if(sb_codes.length()==0){
            Toast.makeText(getApplicationContext(),"Select minimum One Student",Toast.LENGTH_LONG).show();
        }else{
           // new Complaint_View.StudentComplaintSend().execute();
        }
    }

    @Override
    public void onCheckClickListner(int position, Student_List_Helper value, boolean isChecked) {
        String studentcode=value.getStudentCode();
        String studentname=value.getstudent_name();
        if(isChecked==true){
            store_att_hashmap.put(studentname,studentcode);
             Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            store_att_hashmap.remove(studentname);
             Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---DELETE", Toast.LENGTH_LONG).show();
        }
    }


    ///////////////////////////ClassStudent_List////////////////////////////////////////////
    private class StudentList extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Student_List.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Student_List.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());

            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVE_STUDENT_LIST,ParaSutdent(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {

                        JSONArray array= new JSONArray(response);
                        sb_codes = new StringBuilder(array.length());
                        student_list_helpers = new ArrayList<Student_List_Helper>();


                      /*  String   StudentCode="";
                        String   StudentName="Select";
                        Student_List_Helper sched = new Student_List_Helper();
                        sched.setStudentCode(StudentCode);
                        sched.setStudentName(StudentName);
                        sched.setCorrespondanceEmail("CorrespondanceEmail");
                        sched.setCorrespondanceMobileNo("CorrespondanceMobileNo");
                        student_list_helpers.add(sched);


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            StudentCode=object.getString("StudentCode");
                            StudentName=object.getString("StudentName");
                            String     CorrespondanceEmail =object.getString("CorrespondanceEmail");
                            String     CorrespondanceMobileNo =object.getString("CorrespondanceMobileNo");
                            sched = new Student_List_Helper();
                            sched.setStudentCode(StudentCode);
                            sched.setStudentName(StudentName);
                            sched.setCorrespondanceEmail(CorrespondanceEmail);
                            sched.setCorrespondanceMobileNo(CorrespondanceMobileNo);
                            sched.setcheck_status(check_status);
                            student_list_helpers.add(sched);
                            if(check_status==true){
                                store_att_hashmap.put(StudentCode,StudentName);
                            }
                            status=1;*/

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);


                            String   StudentName=object.getString("StudentName");
                            String   StudentCode=object.getString("StudentCode");
                            String     CorrespondanceEmail =object.getString("CorrespondanceEmail");
                            String     CorrespondanceMobileNo =object.getString("CorrespondanceMobileNo");


                            Student_List_Helper item = new Student_List_Helper(StudentName,StudentCode,CorrespondanceEmail,CorrespondanceMobileNo,i + 1 + " .",check_status);
                            student_list_helpers.add(item);
                            if(check_status==true){
                                store_att_hashmap.put(StudentName,StudentCode);
                            }
                            status=1;
                        }

           /* String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STUDENT_LIST,ParastudentList(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        sb_codes = new StringBuilder(array.length());
                        student_list_helpers = new ArrayList<Student_List_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   StudentCode=object.getString("StudentCode");
                            String   StudentName=object.getString("StuName");
                            String   FatherName=object.getString("FatherName");
                            Student_List_Helper item = new Student_List_Helper(StudentName+" ("+StudentCode+")",FatherName,StudentCode,i + 1 + " .",check_status);
                            student_list_helpers.add(item);
                            if(check_status==true){
                                store_att_hashmap.put(StudentName,StudentCode);
                            }
                            status=1;
                        }*/
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
                    student_list_adapter = new Student_List_Adapter(Student_List.this, R.layout.item_student_list, student_list_helpers);
                    student_list_list.setAdapter(student_list_adapter);
                    //student_list_adapter.setCheckcustomButtonListener(Student_List.this);
                    animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    student_list_list.setVisibility(View.VISIBLE);
                    // complaint_view_list.startAnimation(animationDown);
                    layout_select_all.setVisibility(View.VISIBLE);
                    chat_layout.setVisibility(View.VISIBLE);

                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(font_complaint_view, "Student List not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    student_list_list.setVisibility(View.GONE);
                    layout_select_all.setVisibility(View.GONE);
                    chat_layout.setVisibility(View.GONE);

                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(font_complaint_view, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    student_list_list.setVisibility(View.GONE);
                    layout_select_all.setVisibility(View.GONE);
                    chat_layout.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String ParaSutdent(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "10");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            //jsonParam.put("FYId",FYId);
            jsonParam.put("ClassId",ClassId);
            jsonParam.put("SectionId",SectionId);
            jsonParam.put("StreamId",StreamId);
            jsonParam.put("BranchId",BranchId);
            // jsonParam.put("Action","7");  SectionIds,BranchIds,StreamIds,
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
