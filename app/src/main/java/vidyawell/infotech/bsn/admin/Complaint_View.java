package vidyawell.infotech.bsn.admin;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Complaint_View_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.SectionList_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Complaint_View_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Complaint_View extends AppCompatActivity implements Complaint_View_Adapter.CheckcustomButtonListener {
    private static int select_student_count;
    ListView complaint_view_list;
    List<Complaint_View_Helper> complaint_view_helpers;
    Complaint_View_Helper item;
    SharedPreferences sharedprefer;
    ApplicationControllerAdmin applicationController;
    Spinner select_class,select_section;
    public  ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    public  ArrayList<SectionList_Helper> CustomListSection= new ArrayList<SectionList_Helper>();
    ClassList_Adapter adapter;
    Class_Attendance activity = null;
    String class_ID,section_ID,todaysDate;
    Button btn_search;
    RelativeLayout font_complaint_view;
    ValueAnimator mAnimator;
    private Animation animationDown;
    EditText student_search,edttext_complaint;
    Complaint_View_Adapter complaint_view_adapter;
    HashMap <String,String>store_att_hashmap;
    StringBuilder sb_codes;
    String complaint_txt;
    CheckBox check_select_all;
    boolean check_status=false;
    RelativeLayout layout_select_all;
    LinearLayout chat_layout;
    TextView text_comp_class;
    TextView text_comp_section;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.font_complaint_view));
        applicationController = (ApplicationControllerAdmin) getApplicationContext();
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Compose New Feedback/Suggestion");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
        text_comp_section=findViewById(R.id.text_comp_section);
        text_comp_class=findViewById(R.id.text_comp_class);
        if(applicationController.getschooltype().equals("0")){
            text_comp_class.setText("Select Class");
            text_comp_section.setText("Select Section");
        }else{
            text_comp_class.setText("Select Course");
            text_comp_section.setText("Select Semester");
        }
        font_complaint_view=(RelativeLayout)findViewById(R.id.font_complaint_view);
        select_class=(Spinner)findViewById(R.id.select_classc);
        select_section=(Spinner)findViewById(R.id.select_sectionc);
        btn_search=(Button)findViewById(R.id.button_searchlistc);
        student_search=(EditText)findViewById(R.id.student_search);
        edttext_complaint=(EditText)findViewById(R.id.Edit_complaint);
        check_select_all=(CheckBox)findViewById(R.id.check_select_All);
        chat_layout=(LinearLayout)findViewById(R.id.chat_layout);
        layout_select_all=(RelativeLayout)findViewById(R.id.layout_select_all);
        layout_select_all.setVisibility(View.GONE);
        sharedprefer = getSharedPreferences("STUPROFILE", Context.MODE_PRIVATE);
        String theme_code = sharedprefer.getString("Theme_Code", "0");

        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);
        todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        complaint_view_list = (ListView) findViewById(R.id.complaint_view_list);
        store_att_hashmap = new HashMap();
        new SelectclassDetails().execute();
        select_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                class_ID    = ((TextView) view.findViewById(R.id.text_cID)).getText().toString();
                // Toast.makeText(getApplicationContext(), class_ID + "---ClassID", Toast.LENGTH_LONG).show();
                new SelectSection().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        select_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                section_ID    = ((TextView) view.findViewById(R.id.text_sID)).getText().toString();
               // Toast.makeText(getApplicationContext(), section_ID + "---section_ID", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_status=false;
                if(check_select_all.isChecked()==true){
                    check_select_all.setChecked(false);
                }
                new StudentList().execute();
            }
        });

        student_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(complaint_view_adapter != null){
                    complaint_view_adapter.getFilter().filter(s);
                }


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(complaint_view_adapter != null){
                    String text = student_search.getText().toString().toLowerCase(Locale.getDefault());
                    complaint_view_adapter.filter(text);
                }

            }
        });


        Button btn_sendmsg=(Button)findViewById(R.id.complaint_send) ;
        btn_sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaint_txt=edttext_complaint.getText().toString();
                if(complaint_txt.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter Your Message",Toast.LENGTH_LONG).show();
                }else{
                    retrieveValuesFromListMethod(store_att_hashmap);
                }

            }
        });
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
          new StudentComplaintSend().execute();
        }
    }
    @Override
    public void onCheckClickListner(int position, Complaint_View_Helper value,boolean isChecked) {
        String studentcode=value.getStudentCode();
        String studentname=value.getstudent_name();
        if(isChecked==true){
            store_att_hashmap.put(studentname,studentcode);
          //  Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            store_att_hashmap.remove(studentname);
          // Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---DELETE", Toast.LENGTH_LONG).show();
        }
    }
    ///////////////////////////////////Class Complaint Send////////////////////////////////////////

    private class StudentComplaintSend extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Complaint_View.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Complaint_View.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.COMPLAINT_SEND,ParaComplaintSend(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
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
                     // Toast.makeText(getApplicationContext(), sb_codes, Toast.LENGTH_LONG).show();
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
                    complaint_view_list.setVisibility(View.GONE);
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
                    complaint_view_list.setVisibility(View.GONE);
                    break;
            }
        }
    }
    public String ParaComplaintSend(String SchoolCode,String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",FYId);
            jsonParam.put("CreatedBy","1");
            jsonParam.put("Action","1");
            jsonParam.put("ComplainText",complaint_txt);
            jsonParam.put("ClassCode",class_ID);
            jsonParam.put("SectionCode",section_ID);
            jsonParam.put("ComplainBy",applicationController.getActiveempcode());
            jsonParam.put("IsActive","1");
            jsonParam.put("Codes",sb_codes);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    private void showSuccessDialog() {

        final Dialog dialog = new Dialog(Complaint_View.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.success_dialog);
        dialog.setTitle("");
        // set the custom dialog components - text, image and button
        TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        text_label.setText("Message Sent Successfully");
        TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
        text_message.setText("");
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
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    ///////////////////////////ClassStudent_List////////////////////////////////////////////
    private class StudentList extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Complaint_View.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Complaint_View.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STUDENT_LIST,ParastudentList(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        sb_codes = new StringBuilder(array.length());
                        complaint_view_helpers = new ArrayList<Complaint_View_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   StudentCode=object.getString("StudentCode");
                            String   StudentName=object.getString("StuName");
                            String   FatherName=object.getString("FatherName");
                            Complaint_View_Helper item = new Complaint_View_Helper(StudentName+" ("+StudentCode+")",FatherName,StudentCode,i + 1 + " .",check_status);
                            complaint_view_helpers.add(item);
                            if(check_status==true){
                                store_att_hashmap.put(StudentName,StudentCode);
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
                    complaint_view_adapter = new Complaint_View_Adapter(Complaint_View.this, R.layout.item_complaint_view, complaint_view_helpers);
                    complaint_view_list.setAdapter(complaint_view_adapter);
                    complaint_view_adapter.setCheckcustomButtonListener(Complaint_View.this);
                    animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    complaint_view_list.setVisibility(View.VISIBLE);
                   // complaint_view_list.startAnimation(animationDown);
                    layout_select_all.setVisibility(View.VISIBLE);
                    chat_layout.setVisibility(View.VISIBLE);
                   // Toast.makeText(getApplicationContext(),"sb_codes"+sb_codes,Toast.LENGTH_SHORT).show();
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
                    complaint_view_list.setVisibility(View.GONE);
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
                    complaint_view_list.setVisibility(View.GONE);
                    layout_select_all.setVisibility(View.GONE);
                    chat_layout.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String ParastudentList(String SchoolCode,String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",FYId);
            jsonParam.put("AttendanceDate",todaysDate);
            jsonParam.put("Action","5");
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("SectionId",section_ID);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    ///////////////////////////////////Spinner////////////////////////////////////////
    private class SelectclassDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Complaint_View.this);
        @Override
        protected void onPreExecute() {
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

            }
            return status;
        }
        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
         //   progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    adapter = new ClassList_Adapter(getApplicationContext(), R.layout.spinner_class_item, CustomListViewValuesArr,res);
                    select_class.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(font_complaint_view, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
            jsonParam.put("FYId",FYId);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    ///////////////////////////////////////////Section Selection//////////////////////////////////
    private class SelectSection extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Complaint_View.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(Complaint_View.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
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
                    Resources res = getResources();
                    SectionList_Adapter adapter = new SectionList_Adapter(getApplicationContext(), R.layout.spinner_section_item, CustomListSection,res);
                    select_section.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(font_complaint_view, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
            jsonParam.put("Action","4");
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
