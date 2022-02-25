package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.BranchList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Examination_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.MediumList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.SectionList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.StreamList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Subject_List_Adapter;
import vidyawell.infotech.bsn.admin.Const.CustomTypefaceSpan;
import vidyawell.infotech.bsn.admin.Database.MyDatabaseHelper;
import vidyawell.infotech.bsn.admin.Database.Note;
import vidyawell.infotech.bsn.admin.Helpers.BranchList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Examination_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Medium_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Stream_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Subject_List_Helper;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Result_Submit_Classwise extends AppCompatActivity {

    ApplicationControllerAdmin applicationController;
    public  ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    public  ArrayList<SectionList_Helper> CustomListSection= new ArrayList<SectionList_Helper>();
    public  ArrayList<BranchList_Helper> CustomListBranch= new ArrayList<BranchList_Helper>();
    public  ArrayList<Stream_Helper> CustomListStream = new ArrayList<Stream_Helper>();
    public  ArrayList<Medium_Helper> medium_helpers = new ArrayList<Medium_Helper>();
    public  ArrayList<Subject_List_Helper> subject_list_helpers = new ArrayList<Subject_List_Helper>();
    public  ArrayList<Examination_Helper> examination_helpers = new ArrayList<Examination_Helper>();
    SectionList_Adapter SectionListadapter;
    BranchList_Adapter BranchListadapter;
    StreamList_Adapter     StreamListadapter;
    Subject_List_Adapter Subject_Listadapter;
    ClassList_Adapter adapter;
    Spinner select_class,select_section,select_branch,spinner_streamresult,select_medium,select_subject,select_examination;
    LinearLayout class_attendance_layout;
    String class_ID,section_ID,branch_ID,stream_ID,medium_ID,subject_ID,todaysDate,ExamCode,MinMarks,MaxMarks,practicalmarks;
    MyDatabaseHelper database;
    List<Note> helpers;
    SharedPreferences preferences;
    RadioGroup radiogroup_examtype;
    String YearlyOrHalf="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result__submit__classwise);
        applicationController=(ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.layout_classresult));
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        SpannableString str = new SpannableString("Student Marks Entry");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        str.setSpan (new CustomTypefaceSpan("",font2), 0, str.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(str);
        preferences = getSharedPreferences("MARKS", Context.MODE_PRIVATE);
        database=new MyDatabaseHelper(this);
        class_attendance_layout=findViewById(R.id.layout_classresult);
        select_class=findViewById(R.id.select_classresult);
        select_section=findViewById(R.id.spinner_sectionresult);
        select_branch=findViewById(R.id.spinner_branchresult);
        radiogroup_examtype=findViewById(R.id.radiogroup_examtype);
        spinner_streamresult=(Spinner) findViewById(R.id.spinner_streamresult);
        select_medium=(Spinner)findViewById(R.id.select_medium);
        select_subject=(Spinner)findViewById(R.id.select_subject);
        select_examination=(Spinner)findViewById(R.id.select_examination);


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

                new SelectBranch().execute();
                // Toast.makeText(getApplicationContext(), section_ID + "---section_ID", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        select_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch_ID    = ((TextView)view.findViewById(R.id.brach_text)).getText().toString();
                new SelectStream().execute();
                //Toast.makeText(getApplicationContext(), branch_ID + "---section_ID", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_streamresult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>adapterView, View view, int i, long l) {
                stream_ID    = ((TextView)view.findViewById(R.id.text_streamID)).getText().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        select_medium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>adapterView, View view, int i, long l) {
                medium_ID    = ((TextView) view.findViewById(R.id.text_mediumID)).getText().toString();
                new SelectSubject().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        select_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              subject_ID    = ((TextView) view.findViewById(R.id.text_subject_id)).getText().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        select_examination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ExamCode  = ((TextView) view.findViewById(R.id.text_exam_id)).getText().toString();
                new SelectSubject().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radiogroup_examtype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radiouttonpaymode = findViewById(selectedId);
                if(radiouttonpaymode.getText().toString().equalsIgnoreCase("Half Yearly Exam.")){
                    YearlyOrHalf="1";
                 }else{
                    YearlyOrHalf="2";
                }
            }
        });


        Button buttonsearchlist=findViewById(R.id.button_studentlist);
        buttonsearchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpers = new ArrayList<Note>();
                helpers.addAll(database.getAllNotes());
                if(helpers.size()>0){
                    alertsavemarks();
                }else{
                    if(YearlyOrHalf.equalsIgnoreCase("0")){
                        Snackbar snackbar1 = Snackbar
                                .make(class_attendance_layout, "Select Exam. Type Half yearly or Yearly", Snackbar.LENGTH_LONG)
                                .setAction("Select Exam. Type", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                    }
                                });
                        snackbar1.setActionTextColor(Color.RED);
                        snackbar1.show();
                    }else{
                        Intent intent= new Intent(Result_Submit_Classwise.this,Marks_Entry.class);
                        intent.putExtra("ClassId",class_ID);
                        intent.putExtra("SectionId",section_ID);
                        intent.putExtra("BranchId",branch_ID);
                        intent.putExtra("StreamId",stream_ID);
                        intent.putExtra("MediumId",medium_ID);
                        intent.putExtra("ExamCode",ExamCode);
                        intent.putExtra("MinMarks",MinMarks);
                        intent.putExtra("MaxMarks",MaxMarks);
                        intent.putExtra("PRACTICAL",practicalmarks);
                        intent.putExtra("subject_ID",subject_ID);
                        intent.putExtra("YearlyOrHalf",YearlyOrHalf);
                        startActivity(intent);

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("ClassId", class_ID);
                        editor.putString("SectionId", section_ID);
                        editor.putString("BranchId", branch_ID);
                        editor.putString("StreamId", stream_ID);
                        editor.putString("MediumId", medium_ID);
                        editor.putString("ExamCode", ExamCode);
                        editor.putString("MaxMarks", MaxMarks);
                        editor.putString("PRACTICAL", practicalmarks);
                        editor.putString("subject_ID", subject_ID);
                        editor.putString("YearlyOrHalf", YearlyOrHalf);
                        editor.commit();

                    }

                }
            }
        });
        new SelectclassDetails().execute();
        new SelectMedium().execute();
        new SelectExamination().execute();

        helpers = new ArrayList<Note>();
        helpers.addAll(database.getAllNotes());
        if(helpers.size()>0){
            alertsavemarks();
        }
    }
    private class SelectclassDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Result_Submit_Classwise.this);
        @Override
        protected void onPreExecute() {
          //  progressDialog = ProgressDialog.show(Result_Submit_Classwise.this, "", "Please Wait...", true);
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
           // progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    adapter = new ClassList_Adapter(getApplicationContext(), R.layout.spinner_class_item, CustomListViewValuesArr,res);
                    select_class.setAdapter(adapter);
                    break;
                case -2:
                    if(adapter!= null){
                        select_class.setAdapter(null);
                    }
                    Snackbar snackbar = Snackbar
                            .make(class_attendance_layout, "Class not Found", Snackbar.LENGTH_LONG)
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
                            .make(class_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
            jsonParam.put("CreatedBy", applicationController.getUserID());
            jsonParam.put("Action","7");
            jsonParam.put("FYId",FYId);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    ///////////////////////////////////////////Section Selection//////////////////////////////////
    private class SelectSection extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Result_Submit_Classwise.this);
        @Override
        protected void onPreExecute() {
          //  progressDialog = ProgressDialog.show(Result_Submit_Classwise.this, "", "Please Wait...", true);
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
           // progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    SectionListadapter = new SectionList_Adapter(getApplicationContext(), R.layout.spinner_section_item, CustomListSection,res);
                    select_section.setAdapter(SectionListadapter);

                    break;
                case -2:
                    if(SectionListadapter!= null){
                        select_section.setAdapter(null);
                    }
                    Snackbar snackbar = Snackbar
                            .make(class_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                    if(SectionListadapter!= null){
                        select_section.setAdapter(null);
                    }
                    Snackbar snackbar1 = Snackbar
                            .make(class_attendance_layout, "Section not Found", Snackbar.LENGTH_LONG)
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

    /////////////////////////////////////select branch name///////////////////////////////
    private class SelectBranch extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Result_Submit_Classwise.this);
        @Override
        protected void onPreExecute() {
          //  progressDialog = ProgressDialog.show(Result_Submit_Classwise.this, "", "Please Wait...", true);
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
                        CustomListBranch = new ArrayList<BranchList_Helper>();
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
           // progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    BranchListadapter = new BranchList_Adapter(getApplicationContext(), R.layout.spinner_branch_item, CustomListBranch,res);
                    select_branch.setAdapter(BranchListadapter);

                    break;
                case -2:
                    if(BranchListadapter!= null){
                        select_branch.setAdapter(null);
                    }
                    Snackbar snackbar = Snackbar
                            .make(class_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                    if(BranchListadapter!= null){
                        select_branch.setAdapter(null);
                    }
                    Snackbar snackbar1 = Snackbar
                            .make(class_attendance_layout, "Branch not Found", Snackbar.LENGTH_LONG)
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

    /////////////////////////select stream ////////////
    private class SelectStream extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Result_Submit_Classwise.this);
        @Override
        protected void onPreExecute() {
          //  progressDialog = ProgressDialog.show(Result_Submit_Classwise.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SELECT_STREAM,Parastream(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),branch_ID,class_ID,section_ID),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        CustomListStream = new ArrayList<Stream_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   StreamId=object.getString("StreamId");
                            String   StreamName=object.getString("StreamName");

                            final Stream_Helper sched = new Stream_Helper();
                            sched.setStreamID(StreamId);
                            sched.setStreamName(StreamName);
                            CustomListStream.add(sched);

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
           // progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    StreamListadapter = new StreamList_Adapter(getApplicationContext(), R.layout.spinner_stream_item, CustomListStream,res);
                    spinner_streamresult.setAdapter(StreamListadapter);
                    break;
                case -2:
                    if(StreamListadapter!= null){
                        spinner_streamresult.setAdapter(null);
                    }
                    Snackbar snackbar = Snackbar
                            .make(class_attendance_layout, "Stream not Found", Snackbar.LENGTH_LONG)
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
                    if(StreamListadapter!= null){
                        spinner_streamresult.setAdapter(null);
                    }
                    Snackbar snackbar1 = Snackbar
                            .make(class_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Parastream(String SchoolCode,String BranchCode,String SessionId,String BranchId,String ClassId,String SectionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("BranchId", BranchId);
            jsonParam.put("Action","5");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    //////////////////////////select medium////////
    private class SelectMedium extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Result_Submit_Classwise.this);
        @Override
        protected void onPreExecute() {
            //progressDialog = ProgressDialog.show(Result_Submit_Classwise.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SELECT_MEDIUM,Paramedium(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        medium_helpers = new ArrayList<Medium_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   MediumId=object.getString("MediumId");
                            String   MediumName=object.getString("MediumName");

                            final Medium_Helper sched = new Medium_Helper();
                            sched.setMediumID(MediumId);
                            sched.setMediumName(MediumName);
                            medium_helpers.add(sched);

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
           // progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    MediumList_Adapter adapter = new MediumList_Adapter(getApplicationContext(), R.layout.spinner_medium_item, medium_helpers,res);
                    select_medium.setAdapter(adapter);
                    break;
                case -2:

                    Snackbar snackbar = Snackbar
                            .make(class_attendance_layout, "Medium not Found", Snackbar.LENGTH_LONG)
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
                            .make(class_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Paramedium(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("Action","5");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    ///////////////////////// select subject/////////////////////////////
    private class SelectExamination extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Result_Submit_Classwise.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(Result_Submit_Classwise.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SELECT_SUBJECT,Paradexam(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");//,branch_ID,class_ID,section_ID,stream_ID,medium_ID
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        examination_helpers = new ArrayList<Examination_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   Exam=object.getString("Exam");
                            String   ExamCode=object.getString("ExamCode");
                            final Examination_Helper sched = new Examination_Helper();
                            sched.setExamName(Exam);
                            sched.setExamid(ExamCode);
                            examination_helpers.add(sched);
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
           // progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    Examination_Adapter adapter = new Examination_Adapter(getApplicationContext(), R.layout.spinner_exam_item, examination_helpers,res);
                    select_examination.setAdapter(adapter);
                    break;
                case -2:

                    Snackbar snackbar = Snackbar
                            .make(class_attendance_layout, "Examination not Found", Snackbar.LENGTH_LONG)
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
                            .make(class_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Paradexam(String SchoolCode,String BranchCode,String SessionId){//,String BranchId,String ClassId,String SectionId, String StreamId,String MediumId
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("BranchId", "000B010000004");
            jsonParam.put("ClassId", "000B010000009");
            jsonParam.put("SectionId", "000B010000006");
            jsonParam.put("Action","5");
            jsonParam.put("StreamId","000B010000004");
            jsonParam.put("MediumId","000B010000002");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

           //////////////////////////////select examination api//////////
    private class SelectSubject extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Result_Submit_Classwise.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(Result_Submit_Classwise.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SELECT_SUBJECT,Parasubject(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");//,branch_ID,class_ID,section_ID,stream_ID,medium_ID
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        subject_list_helpers = new ArrayList<Subject_List_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   SubjectName=object.getString("SubjectName");
                            String   SubjectCode=object.getString("SubjectCode");
                            final Subject_List_Helper sched = new Subject_List_Helper();
                            sched.setSubjectName(SubjectName);
                            sched.setSubjectID(SubjectCode);
                            subject_list_helpers.add(sched);
                            if(i==0){
                                MinMarks=object.getString("MinMarks");
                                MaxMarks=object.getString("MaxMarks");
                                practicalmarks=object.getString("PracticalMaxMarks");
                            }
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
          //  progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    Subject_Listadapter = new Subject_List_Adapter(getApplicationContext(), R.layout.spinner_subject_name_item, subject_list_helpers,res);
                    select_subject.setAdapter(Subject_Listadapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(class_attendance_layout, "Subject not Found", Snackbar.LENGTH_LONG)
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
                            .make(class_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Parasubject(String SchoolCode,String BranchCode,String SessionId){//,String BranchId,String ClassId,String SectionId, String StreamId,String MediumId
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("BranchId", "000B010000004");
            jsonParam.put("ClassId", "000B010000009");
            jsonParam.put("SectionId", "000B010000006");
            jsonParam.put("Action","6");
            jsonParam.put("StreamId","000B010000004");
            jsonParam.put("MediumId","000B010000002");
            jsonParam.put("ExamCode","000B010000001");
            jsonParam.put("LoginId","3");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    private void alertsavemarks(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Last mark entry of students not completed. If complete last mark click to continue ");
        alertDialogBuilder.setTitle("Last Marks Entry not Completed");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Continue..",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String class_ID=preferences.getString("ClassId","0");
                        String section_ID=preferences.getString("SectionId","0");
                        String branch_ID=preferences.getString("BranchId","0");
                        String stream_ID=preferences.getString("StreamId","0");
                        String medium_ID=preferences.getString("MediumId","0");
                        String ExamCode=preferences.getString("ExamCode","0");
                        String MinMarks=preferences.getString("MinMarks","0");
                        String MaxMarks=preferences.getString("MaxMarks","0");
                        String practicalmarks=preferences.getString("PRACTICAL","0");
                        String subject_ID=preferences.getString("subject_ID","0");
                        String YearlyOrHalf=preferences.getString("YearlyOrHalf","0");

                        Intent intent= new Intent(Result_Submit_Classwise.this,Marks_Entry.class);
                        intent.putExtra("ClassId",class_ID);
                        intent.putExtra("SectionId",section_ID);
                        intent.putExtra("BranchId",branch_ID);
                        intent.putExtra("StreamId",stream_ID);
                        intent.putExtra("MediumId",medium_ID);
                        intent.putExtra("ExamCode",ExamCode);
                        intent.putExtra("MinMarks",MinMarks);
                        intent.putExtra("MaxMarks",MaxMarks);
                        intent.putExtra("PRACTICAL",practicalmarks);
                        intent.putExtra("subject_ID",subject_ID);
                        intent.putExtra("YearlyOrHalf",YearlyOrHalf);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Delete last Sheet",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                database.deleteNote();
            }
        });
        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
