package vidyawell.infotech.bsn.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vidyawell.infotech.bsn.admin.Adapters.BranchList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.SectionList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.SubjectList_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.BranchList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SubjectList_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static vidyawell.infotech.bsn.admin.MainActivity_Admin.theMonth;

public class E_Class_Live_Video extends AppCompatActivity {

    LinearLayout layout_vedio_upload;
    Spinner select_class,select_section,select_branch,select_subject;
    String todaysDate;
    ClassList_Adapter adapter;
    public ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    public  ArrayList<SectionList_Helper> CustomListSection= new ArrayList<SectionList_Helper>();
    public  ArrayList<SubjectList_Helper> CustomListSubject= new ArrayList<SubjectList_Helper>();
    public  ArrayList<BranchList_Helper> CustomListBranch= new ArrayList<BranchList_Helper>();
    ApplicationControllerAdmin applicationController;
    Typeface typeface;
    RelativeLayout layout_e_class_live_video;
    String class_ID,section_ID,subject_ID,branch_ID;
    Button e_class_viedo_submit;
     EditText edt_homework,edt_homework_topic,edt_vedio_link;
    String  topic_name,homework_detlais,link_vedio;
    LinearLayout layout_cal_showtime,layout_calendar_endtime;
    TextView date_text,date_text_endtime,Text_Datev,Text_Datev_endtime,text_monthnamev,text_monthnamev_endtime;
    String time_set,time_set_end;
    String format;
    TimePickerDialog timepickerdialog;
    Calendar calendar;
    private int CalendarHour, CalendarMinute;
    int selectedHour,selectedMinute;
    private int mYear, mMonth, mDay, mHour, mMinute;
    DatePickerDialog datePickerDialog;
    TextView date_text_2,date_text_3;
    TextView tv_date_2,tv_date_3,tv_month_2,tv_month_3;
    String show_date_homework,end_date_homework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e__class__live__video);

        applicationController=(ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_e_class_live_video));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Create e Class");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);

        layout_vedio_upload=(LinearLayout)findViewById(R.id.layout_vedio_upload);
        select_class=findViewById(R.id.select_classd);
        select_section=findViewById(R.id.select_sectiond);
        select_branch=findViewById(R.id.select_branch);
        select_subject=findViewById(R.id.select_subject);
        layout_e_class_live_video=(RelativeLayout)findViewById(R.id.layout_e_class_live_video);
        e_class_viedo_submit=(Button)findViewById(R.id.e_class_viedo_submit);
        edt_homework=(EditText)findViewById(R.id.edt_homework);
        edt_homework_topic=(EditText)findViewById(R.id.edt_homework_topic);
        edt_vedio_link=(EditText)findViewById(R.id.edt_vedio_link);
        layout_cal_showtime=(LinearLayout)findViewById(R.id.layout_cal_showtime);
        layout_calendar_endtime=(LinearLayout)findViewById(R.id.layout_calendar_endtime);
        date_text=(TextView)findViewById(R.id.date_text);
        date_text_endtime=(TextView)findViewById(R.id.date_text_endtime);
        Text_Datev=(TextView)findViewById(R.id.Text_Datev);
        date_text_endtime=(TextView)findViewById(R.id.date_text_endtime);
        text_monthnamev=(TextView)findViewById(R.id.text_monthnamev);
        date_text_endtime=(TextView)findViewById(R.id.date_text_endtime);
        text_monthnamev_endtime=(TextView)findViewById(R.id.text_monthnamev_endtime);
        Text_Datev_endtime=(TextView)findViewById(R.id.Text_Datev_endtime);




        tv_date_2=(TextView)findViewById(R.id.Text_Datev_2);
        tv_date_3=(TextView)findViewById(R.id.Text_Datev_3);
        tv_month_2=(TextView)findViewById(R.id.text_monthnamev_2);
        tv_month_3=(TextView)findViewById(R.id.text_monthnamev_3);


        date_text_2=(TextView)findViewById(R.id.date_text_2);
        date_text_3=(TextView)findViewById(R.id.date_text_3);



      /*  TextView tv_list=(TextView)findViewById(R.id.date_list_diary);
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);
        tv_list.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);

        todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(todaysDate);
            SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
            todaysDate= formt.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }*/


        ////date /////
        LinearLayout layout_cal_2_showdate=(LinearLayout)findViewById(R.id.layout_cal_2_showdate);
        layout_cal_2_showdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(E_Class_Live_Video.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date_2.setText(dayOfMonth+"");
                                tv_month_2.setText(month);
                                show_date_homework=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                date_text_2.setText(dayOfMonth+"-"+ (monthOfYear + 1) +"-"+ year);

                               /* SimpleDateFormat formatter_2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date_2 = formatter_2.parse(show_date_homework);
                                    SimpleDateFormat formt_2 = new SimpleDateFormat("yyyy-MM-dd");
                                    show_date_homework= formt_2.format(date_2);
                                }catch (ParseException e) {
                                    e.printStackTrace();
                                }*/
                                SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = formt.parse(show_date_homework);
                                    SimpleDateFormat formt1 = new SimpleDateFormat("yyyy-MM-dd");
                                    show_date_homework = formt1.format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                              /*  if(String.valueOf(mDay).length()==1){
                                    todaysDate=mYear+ "-0"+ (mDay + 1) + "-0" + cDay;
                                }else{
                                    todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
                                }*/

                                //new HomeworkGETAPI().execute();

                            }
                        }, mYear, mDay, cDay);
                // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        LinearLayout layout_cal_3_end_date=(LinearLayout)findViewById(R.id.layout_cal_3_end_date);
        layout_cal_3_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(E_Class_Live_Video.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date_3.setText(dayOfMonth+"");
                                tv_month_3.setText(month);
                                end_date_homework=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                date_text_3.setText(dayOfMonth+"-"+ (monthOfYear + 1) +"-" + year);

                               /* SimpleDateFormat formatter_3 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date_3 = formatter_3.parse(end_date_homework);
                                    SimpleDateFormat formt_3 = new SimpleDateFormat("yyyy-MM-dd");
                                    end_date_homework= formt_3.format(date_3);
                                }catch (ParseException e) {
                                    e.printStackTrace();
                                }*/

                                SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = formt.parse(end_date_homework);
                                    SimpleDateFormat formt1 = new SimpleDateFormat("yyyy-MM-dd");
                                    end_date_homework = formt1.format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                              /*  if(String.valueOf(mDay).length()==1){
                                    todaysDate=mYear+ "-0"+ (mDay + 1) + "-0" + cDay;
                                }else{
                                    todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
                                }*/

                                //new HomeworkGETAPI().execute();

                            }
                        },  mYear, mDay, cDay);
                // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        //////end/////



        layout_cal_showtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* calendar = Calendar.getInstance();
                CalendarHour = calendar.get(HOUR_OF_DAY);
                CalendarMinute = calendar.get(MINUTE);


                timepickerdialog = new TimePickerDialog(E_Class_Live_Video.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                }
                                else {

                                    format = "AM";
                                }

                                selectedHour=hourOfDay;
                                selectedMinute=minute;
                                time_set = (hourOfDay + ":" + minute + format);
                                date_text.setText(hourOfDay + ":" + minute + format);
                                Text_Datev.setText(hourOfDay + ":" + minute );
                                text_monthnamev.setText(format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();*/

                // Get Current Time
                calendar = Calendar.getInstance();
                mHour = calendar.get(HOUR_OF_DAY);
                mMinute = calendar.get(MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(E_Class_Live_Video.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String Strminute =   Integer.toString(minute);

                                  if(minute<10){
                                      Strminute= "0"+ Strminute   ;
                                  }
                                time_set = (hourOfDay + ":" + Strminute);
                                date_text.setText(hourOfDay + ":" + Strminute);
                                Text_Datev.setText(hourOfDay + ":" + Strminute);
                                text_monthnamev.setText(format);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        layout_calendar_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* calendar = Calendar.getInstance();
                CalendarHour = calendar.get(HOUR_OF_DAY);
                CalendarMinute = calendar.get(MINUTE);


                timepickerdialog = new TimePickerDialog(E_Class_Live_Video.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                }
                                else {

                                    format = "AM";
                                }

                                selectedHour=hourOfDay;
                                selectedMinute=minute;
                                time_set = (hourOfDay + ":" + minute + format);
                                date_text.setText(hourOfDay + ":" + minute + format);
                                Text_Datev.setText(hourOfDay + ":" + minute );
                                text_monthnamev.setText(format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();*/

                // Get Current Time
                calendar = Calendar.getInstance();
                mHour = calendar.get(HOUR_OF_DAY);
                mMinute = calendar.get(MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(E_Class_Live_Video.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String Strminute =   Integer.toString(minute);

                                if(minute<10){
                                    Strminute= "0"+ Strminute   ;
                                }

                                time_set_end = (hourOfDay + ":" + Strminute);
                                date_text_endtime.setText(hourOfDay + ":" + Strminute);
                                Text_Datev_endtime.setText(hourOfDay + ":" + Strminute);
                                text_monthnamev_endtime.setText(format);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });







        e_class_viedo_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  show_date_homework=  date_text_2.getText().toString();
               // end_date_homework=  date_text_3.getText().toString();
                time_set = date_text.getText().toString();
                time_set_end = date_text_endtime.getText().toString();
                topic_name=  edt_homework_topic.getText().toString();
                link_vedio =   edt_vedio_link.getText().toString();
                homework_detlais=  edt_homework.getText().toString();

                if(date_text_2.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Please Select Show Date",Toast.LENGTH_SHORT).show();

                } else if(date_text_3.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Please Select End Date",Toast.LENGTH_SHORT).show();

                } else if(date_text.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Please Select Show Time",Toast.LENGTH_SHORT).show();

                }else if(date_text_endtime.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Please Select End Time",Toast.LENGTH_SHORT).show();

                } else if(edt_homework_topic.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter Topic Name",Toast.LENGTH_LONG).show();

                }else if(edt_vedio_link.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter Video Link",Toast.LENGTH_LONG).show();
                }else if (edt_homework.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "Enter Homework Details Name", Toast.LENGTH_LONG).show();
                } else if (subject_ID.equals("")) {

                    Toast.makeText(getApplicationContext(), "Select Subject", Toast.LENGTH_LONG).show();
                }else{
                   new E_CLASS_LIVE_VIDEO_INSERT_API().execute();

                }//
            }
        });

        layout_vedio_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String url = ("https://www.youtube.com/upload");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

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
                new SelectSubject().execute();
                new SelectBranch().execute();
                // Toast.makeText(getApplicationContext(), section_ID + "---section_ID", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        select_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subject_ID    = ((TextView) view.findViewById(R.id.text_subID)).getText().toString();
                // Toast.makeText(getApplicationContext(), subject_ID + "---subject_ID", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        select_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch_ID    = ((TextView) view.findViewById(R.id.brach_text)).getText().toString();
                //Toast.makeText(getApplicationContext(), branch_ID + "---section_ID", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    ////////////////////////////////////////CLASSES//////////////////////////////////////////////////////////
    private class SelectclassDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(E_Class_Live_Video.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(E_Class_Live_Video.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ ServerApiadmin.HOMEWORK_CLASSFILTER_API,Paradetails(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            String s=applicationController.getServicesapplication()+ServerApiadmin.HOMEWORK_CLASSFILTER_API;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        CustomListViewValuesArr = new ArrayList<>();
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    adapter = new ClassList_Adapter(getApplicationContext(), R.layout.spinner_class_item, CustomListViewValuesArr,res);
                    select_class.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_e_class_live_video, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_e_class_live_video, "Data not Found. Please Try Again", Snackbar.LENGTH_LONG)
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
            jsonParam.put("schoolCode", SchoolCode);
            jsonParam.put("branchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("EmployeeCode", applicationController.getActiveempcode());
            // jsonParam.put("Action","7");
            // jsonParam.put("FYId",FYId);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    ///////////////////////////////////////////Section Selection//////////////////////////////////
    private class SelectSection extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(E_Class_Live_Video.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.HOMEWORK_SELECTBRANCH,ParaSec(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
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
                    SectionList_Adapter adapter = new SectionList_Adapter(getApplicationContext(), R.layout.spinner_section_item, CustomListSection,res);
                    select_section.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_e_class_live_video, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_e_class_live_video, "Data Not Found. Please Try Again", Snackbar.LENGTH_LONG)
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
            jsonParam.put("schoolCode", SchoolCode);
            jsonParam.put("branchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            //jsonParam.put("FYId",FYId);
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("EmployeeCode", applicationController.getActiveempcode());
            // jsonParam.put("Action","7");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    //////////////////////////////////select branch /////////////////////////////////////
    private class SelectBranch extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(E_Class_Live_Video.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
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
                    BranchList_Adapter adapter = new BranchList_Adapter(getApplicationContext(), R.layout.spinner_branch_item, CustomListBranch,res);
                    select_branch.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_e_class_live_video, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_e_class_live_video, "Branch Data Not Found", Snackbar.LENGTH_LONG)
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

    private class SelectSubject extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(E_Class_Live_Video.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SUBJECT_LIST,ParaSec1(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        CustomListSubject = new ArrayList<>();
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   SubjectId=object.getString("SubjectId");
                            String   SubjectName=object.getString("SubjectName");
                            final SubjectList_Helper sched = new SubjectList_Helper();
                            sched.setSubId(SubjectId);
                            sched.setSubName(SubjectName);
                            CustomListSubject.add(sched);
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
            //  progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    SubjectList_Adapter adapter = new SubjectList_Adapter(getApplicationContext(), R.layout.spinner_subject_item, CustomListSubject,res);
                    select_subject.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_e_class_live_video, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                    subject_ID="";
                    select_subject.setAdapter(null);
                    Snackbar snackbar1 = Snackbar
                            .make(layout_e_class_live_video, "Subject data not found", Snackbar.LENGTH_LONG)
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
    public String ParaSec1(String schoolCode, String branchCode, String SessionId, String fyId, String EmployeeId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("schoolCode", schoolCode);
            jsonParam.put("branchCode", branchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("fyId",fyId);
            jsonParam.put("EmployeeId",EmployeeId);
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("SectionId",section_ID);
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

    //////////////////////////////////////API INSERT SUBMIT////////////////////////
    private class E_CLASS_LIVE_VIDEO_INSERT_API extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(E_Class_Live_Video.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.E_CLASS_INSERT_SUBMIT_API,ParaSub(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");

            String api= applicationController.getServicesapplication();
            //Log.d("request!", response);
            if(response!=null){
                response=response.replace("\"","");
                if (response.length()<3){
                    if(response.equalsIgnoreCase("1")){
                        status=1;
                    }else{
                        status=2;
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
            //  progressDialog.dismiss();
            switch (s){
                case 1:
                    finish();
                   // showSuccessDialog();
                    break;
                case 2:
                    Snackbar snackbar = Snackbar
                            .make(layout_e_class_live_video, "e Class Already Submitted. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -2:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_e_class_live_video, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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

    public String ParaSub(String schoolCode, String branchCode, String SessionId, String fyId,String EmployeeCode){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "1");
            jsonParam.put("SchoolCode", schoolCode);
            jsonParam.put("BranchCode", branchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",fyId);
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("SectionId",section_ID);
            jsonParam.put("SubjectId",subject_ID);
            jsonParam.put("BranchId",branch_ID);
            jsonParam.put("ClassDate",show_date_homework);
            jsonParam.put("EndDate",end_date_homework);
            jsonParam.put("EmployeeId",EmployeeCode);
            jsonParam.put("TopicName",topic_name);
            jsonParam.put("Description",homework_detlais);
            jsonParam.put("VedioPath",link_vedio);
            jsonParam.put("ClassTime",time_set);
            jsonParam.put("EndTime",time_set_end);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    private void showSuccessDialog() {
        final Dialog dialog = new Dialog(E_Class_Live_Video.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.success_dialog);
        dialog.setTitle("");
        // set the custom dialog components - text, image and button
        TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        text_label.setText(" Submit Successfully");
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

               // new Dialy_Diary.HomeworkGETAPI().execute();
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
