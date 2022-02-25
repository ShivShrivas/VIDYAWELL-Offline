package vidyawell.infotech.bsn.admin;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vidyawell.infotech.bsn.admin.Adapters.BranchList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Homework_Insert_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.SectionList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Section_Checkbox_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.SubjectList_Adapter;

import vidyawell.infotech.bsn.admin.Helpers.BranchList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Homework_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_Attendance_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SubjectList_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static vidyawell.infotech.bsn.admin.MainActivity_Admin.theMonth;
import static vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin.MAIN_IPLINK;

public class Dialy_Diary extends AppCompatActivity {

    ListView list_womework;
    List<Homework_Helper> homework_helpers;
    Spinner select_class,select_section,select_subject;
    String class_ID,section_ID,todaysDate,subject_ID,show_date_homework,end_date_homework;
    String  topic_name,homework_detlais;
    Button click_approved;
     public ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
     public  ArrayList<SectionList_Helper> CustomListSection= new ArrayList<SectionList_Helper>();
     public  ArrayList<SubjectList_Helper> CustomListSubject= new ArrayList<SubjectList_Helper>();
     public  ArrayList<BranchList_Helper> CustomListBranch= new ArrayList<BranchList_Helper>();
    public  ArrayList<SectionList_Checkbox_Helper> CustomListSectionchk= new ArrayList<SectionList_Checkbox_Helper>();
    boolean check_status=false;
    HashMap<String,String> store_att_hashmap_section;
    Section_Checkbox_Adapter adapter1;

    ClassList_Adapter adapter;
    Class_Attendance activity = null;
    RelativeLayout layout_main_diary;
    ApplicationControllerAdmin applicationController;
    EditText edt_homework,edt_homework_topic;
    Button subject_submit;
    StringBuilder  arraylist_codes;
    Typeface typeface;
    LinearLayout layout_attachment_file,layout_vedio_upload;
    BottomSheetDialog dialog;
    private String userChoosenTask="Permit";
    private int REQUEST_CAMERA = 100, SELECT_FILE = 1;
    private int EMPDATA = 2;
    private int SELECT_PDF=3;
    String DOC_TYPE="";
    String profile_imageString="";
   // String[]  profile_imageString;

    String realpath,branch_ID;
    ImageView image_uploadwomework,image_uploadwomework_2,image_uploadwomework_3,image_uploadwomework_4,image_uploadwomework_5,image_uploadwomework_6,image_uploadwomework_7,image_uploadwomework_8,image_uploadwomework_9,image_uploadwomework_10;
    Spinner select_branch;
    String section_ID_history,class_ID_history;
    EditText txt_write;
    String link_vedio;
    TextView button_cancel,button_submitcode;
    TextView txt_add_file,txt_add_file_2,txt_add_file_3,txt_add_file_4,txt_add_file_5,txt_add_file_6,txt_add_file_7,txt_add_file_8,txt_add_file_9,txt_add_file_10;
    HashMap<String,String> store_att_hashmap;
    JSONArray jlist=new JSONArray();
    int count_image = 1;
    Spinner spineer_requred_submision;
    String requred_submision;
    String value_store="";
    TextView tv_date,tv_date_2,tv_date_3,tv_month,tv_month_2,tv_month_3;
    DatePickerDialog datePickerDialog;
    String selecte_ddate;
    String currentdate;
    TextView date_text,date_text_2,date_text_3;
    String end_date_video;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    VideoView  audio_upload;
    LinearLayout layout_attachment_audio;
    private  int SELECT_AUDIO = 5;
    TextView camera_sel,gallery_sel,upload_pdf,txt_video_audio,txt_audio;
    int flag=0;
    List<String> permissionList= Collections.emptyList();
    String TAG;
    private String mCurrentPhotoPath;
    Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_dialy_diary);
        applicationController=(ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_main_diary));
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Home Work");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
        layout_main_diary = findViewById(R.id.layout_main_diary);
        select_class=findViewById(R.id.select_classd);
        select_section=findViewById(R.id.select_sectiond);
        select_subject=findViewById(R.id.select_subject);
        spineer_requred_submision=findViewById(R.id.spineer_requred_submision);
        edt_homework=findViewById(R.id.edt_homework) ;
        edt_homework_topic=findViewById(R.id.edt_homework_topic) ;
        subject_submit=findViewById(R.id.subject_submit);
        list_womework=findViewById(R.id.list_womework);
        layout_attachment_file=findViewById(R.id.layout_attachment_file);
        image_uploadwomework=findViewById(R.id.image_uploadwomework);
        //image_uploadwomework_2=findViewById(R.id.image_uploadwomework_2);
        tv_date=(TextView)findViewById(R.id.Text_Datev);
        tv_date_2=(TextView)findViewById(R.id.Text_Datev_2);
        tv_date_3=(TextView)findViewById(R.id.Text_Datev_3);
        tv_month=(TextView)findViewById(R.id.text_monthnamev);
        tv_month_2=(TextView)findViewById(R.id.text_monthnamev_2);
        tv_month_3=(TextView)findViewById(R.id.text_monthnamev_3);

        date_text=(TextView)findViewById(R.id.date_text);
        date_text_2=(TextView)findViewById(R.id.date_text_2);
        date_text_3=(TextView)findViewById(R.id.date_text_3);



        image_uploadwomework_2=(ImageView) findViewById(R.id.image_uploadwomework_2);
        image_uploadwomework_3=(ImageView)findViewById(R.id.image_uploadwomework_3);
        image_uploadwomework_4=(ImageView) findViewById(R.id.image_uploadwomework_4);
        image_uploadwomework_5=(ImageView) findViewById(R.id.image_uploadwomework_5);
        image_uploadwomework_6=(ImageView) findViewById(R.id.image_uploadwomework_6);
        image_uploadwomework_7=(ImageView)findViewById(R.id.image_uploadwomework_7);
        image_uploadwomework_8=(ImageView) findViewById(R.id.image_uploadwomework_8);
        image_uploadwomework_9=(ImageView)findViewById(R.id.image_uploadwomework_9);
        image_uploadwomework_10=(ImageView)findViewById(R.id.image_uploadwomework_10);
        audio_upload=(VideoView)findViewById(R.id.audio_upload);
        layout_attachment_audio=(LinearLayout) findViewById(R.id.layout_attachment_audio);


        select_branch=findViewById(R.id.select_branch);
        layout_vedio_upload=(LinearLayout)findViewById(R.id.layout_vedio_upload);
        click_approved=(Button)findViewById(R.id.click_approved);
        txt_add_file=(TextView)findViewById(R.id.txt_add_file);
        txt_add_file_2=(TextView)findViewById(R.id.txt_add_file_2);
        txt_add_file_3=(TextView)findViewById(R.id.txt_add_file_3);
        txt_add_file_4=(TextView)findViewById(R.id.txt_add_file_4);
        txt_add_file_5=(TextView)findViewById(R.id.txt_add_file_5);
        txt_add_file_6=(TextView)findViewById(R.id.txt_add_file_6);
        txt_add_file_7=(TextView)findViewById(R.id.txt_add_file_7);
        txt_add_file_8=(TextView)findViewById(R.id.txt_add_file_8);
        txt_add_file_9=(TextView)findViewById(R.id.txt_add_file_9);
        txt_add_file_10=(TextView)findViewById(R.id.txt_add_file_10);

        store_att_hashmap = new HashMap();
        store_att_hashmap_section = new HashMap();


        click_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dialy_Diary.this,Approved_Vedio.class);
                startActivity(intent);
            }
        });

        if(applicationController.getLoginType().equalsIgnoreCase("4")){
            click_approved.setVisibility(View.GONE);
        }else {
            click_approved.setVisibility(View.VISIBLE);

        }



        //////////////////////permission in camera and gallery photo ///////////////
/*
        List<String> permissionList= Collections.emptyList();
        // = PermissionUtils.checkAndRequestPermissions(this);
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N) {
            permissionList=PermissionUtils.checkAndRequestPermissions(this);
            if (permissions(permissionList)) {
            }
        } else {
            if (permissions(permissionList)) {
                permissionList = PermissionUtils.checkAndRequestPermissions(this);
            }

        }*/

        if (permissions(permissionList)==false) {
            permissionList = PermissionUtils.checkAndRequestPermissions(this);
            permissions(permissionList);
            flag=1;
        }


        //////////////////////End ///////////////////////////////////////

//////////////////////audio upload////////




       /////////////current date set///////////
      /*  final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);
       // tv_list.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        date_text.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        date_text_2.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        date_text_3.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        String month=theMonth(mDay);
        tv_date.setText(cDay+"");
        tv_date_2.setText(cDay+"");
        tv_date_3.setText(cDay+"");
        tv_month.setText(month);
        tv_month_2.setText(month);
        tv_month_3.setText(month);
        todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;//// string name todayssate ... homeworkdate
        currentdate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        show_date_homework=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        end_date_homework=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        //todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(todaysDate);
            SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
            todaysDate= formt.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }


        SimpleDateFormat formatter_2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date_2 = formatter_2.parse(show_date_homework);
            SimpleDateFormat formt_2 = new SimpleDateFormat("yyyy-MM-dd");
            show_date_homework= formt_2.format(date_2);
        }catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter_3 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date_3 = formatter_3.parse(end_date_homework);
            SimpleDateFormat formt_3 = new SimpleDateFormat("yyyy-MM-dd");
            end_date_homework= formt_3.format(date_3);
        }catch (ParseException e) {
            e.printStackTrace();
        }*/




       /* if(String.valueOf(mDay).length()==1){
            todaysDate=mYear+ "-0"+ (mDay + 1) + "-0" + cDay;
        }else{
            todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        }*/
       //Toast.makeText(getApplicationContext(),applicationController.getActiveempcode(),Toast.LENGTH_LONG).show();

        //////////////////new date select calender/////////////////////

        tv_month.setTypeface(typeface);
        tv_month_2.setTypeface(typeface);
        tv_month_3.setTypeface(typeface);
        tv_date.setTypeface(typeface);
        tv_date_2.setTypeface(typeface);
        tv_date_3.setTypeface(typeface);
       /* final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        final int cDay = c.get(Calendar.DAY_OF_MONTH);
        String month=theMonth(mDay);
        tv_date.setText(cDay+"");
        tv_month.setText(month);
        todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        currentdate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        date_text.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(todaysDate);
            SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
            todaysDate= formt.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        if(String.valueOf(mDay).length()==1){
            todaysDate=mYear+ "-0"+ (mDay + 1) + "-0" + cDay;
        }else{
            todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        }*/
        LinearLayout layout_calendar=(LinearLayout)findViewById(R.id.layout_cal);
        layout_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Dialy_Diary.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date.setText(dayOfMonth+"");
                                tv_month.setText(month);
                                todaysDate=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                date_text.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = formatter.parse(todaysDate);
                                    SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
                                    todaysDate= formt.format(date);
                                }catch (ParseException e) {
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

        LinearLayout layout_calendar_2=(LinearLayout)findViewById(R.id.layout_cal_2);
        layout_calendar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Dialy_Diary.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date_2.setText(dayOfMonth+"");
                                tv_month_2.setText(month);
                                show_date_homework=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                date_text_2.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                                SimpleDateFormat formatter_2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date_2 = formatter_2.parse(show_date_homework);
                                    SimpleDateFormat formt_2 = new SimpleDateFormat("yyyy-MM-dd");
                                    show_date_homework= formt_2.format(date_2);
                                }catch (ParseException e) {
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


        LinearLayout layout_calendar_3=(LinearLayout)findViewById(R.id.layout_cal_3);
        layout_calendar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Dialy_Diary.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date_3.setText(dayOfMonth+"");
                                tv_month_3.setText(month);
                                end_date_homework=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                date_text_3.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                                SimpleDateFormat formatter_3 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date_3 = formatter_3.parse(end_date_homework);
                                    SimpleDateFormat formt_3 = new SimpleDateFormat("yyyy-MM-dd");
                                    end_date_homework= formt_3.format(date_3);
                                }catch (ParseException e) {
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

        txt_add_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // count_image =2;
               /// showdialogmultipleImage(null);
                //dialog.show();
                 image_uploadwomework_2.setVisibility(View.VISIBLE);

                //image_uploadwomework_4.setVisibility(View.VISIBLE);
            }
        });

        txt_add_file_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // count_image =3;
                image_uploadwomework_3.setVisibility(View.VISIBLE);
                txt_add_file_2.setVisibility(View.GONE);
            }
        });

        txt_add_file_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  count_image =4;
                image_uploadwomework_4.setVisibility(View.VISIBLE);
                txt_add_file_3.setVisibility(View.GONE);
            }
        });

        txt_add_file_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  count_image =5;
                image_uploadwomework_5.setVisibility(View.VISIBLE);
                txt_add_file_4.setVisibility(View.GONE);
            }
        });

        txt_add_file_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // count_image =6;
                image_uploadwomework_6.setVisibility(View.VISIBLE);
                txt_add_file_5.setVisibility(View.GONE);
            }
        });

        txt_add_file_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // count_image =7;
                image_uploadwomework_7.setVisibility(View.VISIBLE);
                txt_add_file_6.setVisibility(View.GONE);
            }
        });

        txt_add_file_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // count_image =8;
                image_uploadwomework_8.setVisibility(View.VISIBLE);
                txt_add_file_7.setVisibility(View.GONE);
            }
        });

        txt_add_file_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  count_image =9;
                image_uploadwomework_9.setVisibility(View.VISIBLE);
                txt_add_file_8.setVisibility(View.GONE);
            }
        });


        txt_add_file_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //count_image =10;
               // image_uploadwomework_10.setVisibility(View.VISIBLE);
                txt_add_file_9.setVisibility(View.GONE);
                txt_add_file_10.setVisibility(View.GONE);
            }
        });

       /* txt_add_file_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // count_image =11;
                image_uploadwomework_10.setVisibility(View.VISIBLE);
                txt_add_file_9.setVisibility(View.GONE);
                txt_add_file_10.setVisibility(View.GONE);
            }
        });*/

        image_uploadwomework_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_image =2;
                openDialog();
                // image_uploadwomework_3.setVisibility(View.VISIBLE);
                txt_add_file_2.setVisibility(View.GONE);
                txt_audio.setVisibility(View.GONE);
               /* if(flag==1){
                    count_image =2;
                    openDialog();
                    // image_uploadwomework_3.setVisibility(View.VISIBLE);
                    txt_add_file_2.setVisibility(View.GONE);
                    txt_audio.setVisibility(View.GONE);
                }else {
                    chkallowpermision();
                }*/

            }
        });
        image_uploadwomework_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    count_image =3;
                    openDialog();
                    txt_add_file_3.setVisibility(View.GONE);
                    txt_audio.setVisibility(View.GONE);
                }else {
                    chkallowpermision();
                }

            }
        });
        image_uploadwomework_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    count_image =4;
                    openDialog();
                    txt_add_file_4.setVisibility(View.GONE);
                    txt_audio.setVisibility(View.GONE);
                }else {
                    chkallowpermision();
                }


            }
        });

        image_uploadwomework_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    count_image =5;
                    openDialog();
                    txt_add_file_5.setVisibility(View.GONE);
                    txt_audio.setVisibility(View.GONE);
                }else {
                    chkallowpermision();
                }

            }
        });

        image_uploadwomework_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    count_image =6;
                    openDialog();
                    txt_add_file_6.setVisibility(View.GONE);
                    txt_audio.setVisibility(View.GONE);
                }else {
                    chkallowpermision();
                }

            }
        });
        image_uploadwomework_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    count_image =7;
                    openDialog();
                    txt_add_file_7.setVisibility(View.GONE);
                    txt_audio.setVisibility(View.GONE);
                }else {
                    chkallowpermision();
                }

            }
        });


        image_uploadwomework_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag==1){
                    count_image =8;
                    openDialog();
                    txt_add_file_8.setVisibility(View.GONE);
                    txt_audio.setVisibility(View.GONE);
                }else {
                    chkallowpermision();
                }

            }
        });
        image_uploadwomework_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    count_image =9;
                    openDialog();
                    txt_add_file_9.setVisibility(View.GONE);
                    txt_add_file_10.setVisibility(View.GONE);
                    txt_audio.setVisibility(View.GONE);
                }else {
                    chkallowpermision();
                }

            }
        });
/*
        image_uploadwomework_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_image =10;
                openDialog();
                txt_add_file_10.setVisibility(View.GONE);
            }
        });
*/
        layout_attachment_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    count_image =10;
                    openDialog();
                    camera_sel.setVisibility(View.GONE);
                    gallery_sel.setVisibility(View.GONE);
                    upload_pdf.setVisibility(View.GONE);
                }else {
                    chkallowpermision();
                }


            }
        });

        layout_vedio_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogVedio();
            }
        });

        //new HomeworkGETAPI().execute();
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
        spineer_requred_submision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                requred_submision=parent.getSelectedItem().toString();


                if(requred_submision.equals("Yes")){
                    value_store = "1";
                     requred_submision=parent.getSelectedItem().toString();
                }else {
                    value_store = "0";
                      requred_submision=parent.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subject_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // System.out.println("The Value  is:"+ val);


                /*for(int i =0; i<store_att_hashmap.size();i++){
                    try {
                        JSONObject attachmentList = new JSONObject();
                        String val=store_att_hashmap.get(DOC_TYPE);
                        attachmentList.put("Attachment", val);
                        attachmentList.put("Name", DOC_TYPE);
                        jlist.put(attachmentList);
                    }
                    catch (Exception ex)
                    {

                    }
                    //val=store_att_hashmap.get(doc_type);

                }
                store_att_hashmap.clear();*/
              // String abcd=   date_text.getText().toString();
              topic_name = Base64.encodeToString(edt_homework_topic.getText().toString().getBytes(), Base64.DEFAULT);  ////encode edit text vlaue
              homework_detlais = Base64.encodeToString(edt_homework.getText().toString().getBytes(), Base64.DEFAULT);  ////encode edit text vlaue

                //topic_name =edt_homework_topic.getText().toString();
                //homework_detlais =edt_homework.getText().toString();


               /* try {
                    topic_name = URLEncoder.encode(edt_homework_topic.getText().toString(), "utf-16");
                    homework_detlais = URLEncoder.encode(edt_homework.getText().toString(), "utf-16");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/


               // topic_name = edt_homework_topic.getText().toString();
                //homework_detlais= edt_homework.getText().toString();
                if(date_text.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Select Homework date",Toast.LENGTH_LONG).show();

                }else if(date_text_2.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Please Select Homework show date",Toast.LENGTH_LONG).show();

                } else if(date_text_3.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Please Select Homework end date",Toast.LENGTH_LONG).show();

                }else if(edt_homework_topic.getText().toString().length()==0){
                     Toast.makeText(getApplicationContext(),"Enter Topic Name",Toast.LENGTH_LONG).show();
                 }else if (edt_homework.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "Enter Homework Details Name", Toast.LENGTH_LONG).show();
                 }else if (subject_ID.equals("")) {
                     Toast.makeText(getApplicationContext(), "Select Subject", Toast.LENGTH_LONG).show();
                 }else{
                     new StudentList().execute();

            }
            }
        });
        layout_attachment_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_image =1;
                openDialog();
                txt_audio.setVisibility(View.GONE);
            }
        });
       // final boolean result= Utility.checkPermission(Dialy_Diary.this);
        Button btn_history=findViewById(R.id.button_hist);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Dialy_Diary.this,Homework_Topic.class);
               // class_ID    = ((TextView) findViewById(R.id.text_cID)).getText().toString();
                intent.putExtra("SectionId",section_ID);
                intent.putExtra("ClassId",class_ID);
                startActivity(intent);

            }
        });
    }


    private void showdialogmultipleImage(Bitmap thumbnail) {
        final Dialog dialog = new Dialog(Dialy_Diary.this);
         // R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
        dialog.setContentView(R.layout.dialog_upload_image_pdf);
       // TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
       // fontChanger.replaceFonts((LinearLayout)dialog.findViewById(R.id.dialog_layout_vedio));
        dialog.setCanceledOnTouchOutside(false);

      /* image_uploadwomework_2=(ImageView) dialog.findViewById(R.id.image_uploadwomework_2);
        image_uploadwomework_3=(ImageView) dialog.findViewById(R.id.image_uploadwomework_3);
        image_uploadwomework_4=(ImageView) dialog.findViewById(R.id.image_uploadwomework_4);
        image_uploadwomework_5=(ImageView) dialog.findViewById(R.id.image_uploadwomework_5);
        image_uploadwomework_6=(ImageView) dialog.findViewById(R.id.image_uploadwomework_6);
        image_uploadwomework_7=(ImageView) dialog.findViewById(R.id.image_uploadwomework_7);
        image_uploadwomework_8=(ImageView) dialog.findViewById(R.id.image_uploadwomework_8);
        image_uploadwomework_9=(ImageView) dialog.findViewById(R.id.image_uploadwomework_9);*/
        button_cancel=(TextView)dialog.findViewById(R.id.button_cancel);
        button_submitcode=(TextView)dialog.findViewById(R.id.button_submitcode);


    /*       if(count_image==2){
              image_uploadwomework_2.setImageBitmap(thumbnail);
}*/

/*
        image_uploadwomework_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog();
            }
        });
*/

      /*  image_uploadwomework_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        image_uploadwomework_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        image_uploadwomework_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        image_uploadwomework_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        image_uploadwomework_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        image_uploadwomework_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        image_uploadwomework_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });*/

        dialog.show();

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_submitcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  SubjectName   =txt_write.getText().toString();

                dialog.dismiss();
            }
        });
    }

    private void showdialogVedio() {
        final Dialog dialog = new Dialog(Dialy_Diary.this);
        //  R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
        dialog.setContentView(R.layout.dialog_upload_vedio);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)dialog.findViewById(R.id.dialog_layout_vedio));
        dialog.setCanceledOnTouchOutside(false);

        txt_write=(EditText)dialog.findViewById(R.id.txt_write);
        button_cancel=(TextView)dialog.findViewById(R.id.button_cancel);
        button_submitcode=(TextView)dialog.findViewById(R.id.button_submitcode);
        LinearLayout    layout_cal_video =(LinearLayout)dialog.findViewById(R.id.layout_cal_video);
        final TextView date_text_video=(TextView)dialog.findViewById(R.id.date_text_video);
        final TextView Text_Datev_video=(TextView)dialog.findViewById(R.id.Text_Datev_video);
        final TextView text_monthnamev_video=(TextView)dialog.findViewById(R.id.text_monthnamev_video);

        layout_cal_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Dialy_Diary.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                               Text_Datev_video.setText(dayOfMonth+"");
                                text_monthnamev_video.setText(month);
                                end_date_video=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                date_text_video.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                                SimpleDateFormat formatter_3 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date_3 = formatter_3.parse(end_date_video);
                                    SimpleDateFormat formt_3 = new SimpleDateFormat("yyyy-MM-dd");
                                    end_date_video= formt_3.format(date_3);
                                }catch (ParseException e) {
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

        dialog.show();

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_submitcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  SubjectName   =txt_write.getText().toString();
          link_vedio =   txt_write.getText().toString();

             dialog.dismiss();
            }
        });
    }

    /////////////////////    image upload  ///////////
    private void openDialog() {
       // final boolean result= Utility.checkPermission(Dialy_Diary.this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
         camera_sel =view.findViewById(R.id.take_pic);
         gallery_sel = view.findViewById(R.id.take_library);
         upload_pdf =view.findViewById(R.id.upload_pdf);
         txt_audio =view.findViewById(R.id.txt_audio);
        TextView close = view.findViewById(R.id.close);
        camera_sel.setTypeface(typeface);
        gallery_sel.setTypeface(typeface);
        close.setTypeface(typeface);


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
        }*/
        camera_sel.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                userChoosenTask ="Take Photo";
                cameraIntent();
                dialog.dismiss();
               /* if(result){
                    cameraIntent();
                    dialog.dismiss();
                }else {
                    Utility.checkPermission(Dialy_Diary.this);
                }*/

            }

        });
        gallery_sel.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                userChoosenTask ="Choose from Library";
               // if(result)
                    galleryIntent();
                //pdf();
                dialog.dismiss();
            }

        });
        upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoosenTask ="Upload PDF";
              //  if(result)
                    pdf();
                dialog.dismiss();
            }
        });


        txt_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoosenTask ="Audio";
                //  if(result)
                openGalleryAudio();
                dialog.dismiss();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                    else if(userChoosenTask.equals("Upload PDF"))
                        pdf();
                    else if(userChoosenTask.equals("Audio"))
                        openGalleryAudio();
                } else {
                        chkallowpermision();
                        //Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();
                    }



                /*else if(userChoosenTask.equals("Upload PDF")) {
                    pdf();
                }else {

                }*/
                break;
        }
    }
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    private void cameraIntent() {

       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);*/
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i(TAG, "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(Dialy_Diary.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }



    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image=null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
        {

            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            image = File.createTempFile(
                    imageFileName,  // prefix
                    ".jpg",         // suffix
                    storageDir      // directory
            );

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        }

        return image;

    }

    public Bitmap BITMAP_RESIZER(Bitmap bitmap,int newWidth,int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }


    private void pdf(){
        File file = new File(Environment.getExternalStorageDirectory(),
                ".pdf");
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), SELECT_PDF);
    }


    public void openGalleryAudio(){

        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Audio "), SELECT_AUDIO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA){
                onCaptureImageResult(data);
            } else if(requestCode==SELECT_PDF){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    pdfatteched(data);
                }
            }
            else if(requestCode==SELECT_AUDIO) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    audioatteched(data);
                }
            }


        }
    }
    private void onCaptureImageResult(Intent data) {
       // Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        try {
            thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        thumbnail = BITMAP_RESIZER(thumbnail,1200,1200);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 80, bos);
        byte[] imageBytes = bos.toByteArray();


        ////old
       /* ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        byte[] imageBytes = bytes.toByteArray();*/
       // store_att_hashmap.containsKey(profile_imageString);


        DOC_TYPE="image";

        if(count_image==1){

            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //fillAttachmentList(profile_imageString,DOC_TYPE);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            // DOC_TYPE="image";
            image_uploadwomework.setImageBitmap(thumbnail);
            txt_add_file.setVisibility(View.VISIBLE);

        }else if (count_image==2){


            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
              fillAttachmentList(profile_imageString,DOC_TYPE);
            // showdialogmultipleImage(thumbnail);
            image_uploadwomework_2.setImageBitmap(thumbnail);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_2.setVisibility(View.VISIBLE);
           // image_uploadwomework_3.setVisibility(View.VISIBLE);
        }else if(count_image==3){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
              fillAttachmentList(profile_imageString,DOC_TYPE);
              image_uploadwomework_3.setImageBitmap(thumbnail);
            //image_uploadwomework_4.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_3.setVisibility(View.VISIBLE);
        }else if(count_image==4){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
             fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_4.setImageBitmap(thumbnail);
            //image_uploadwomework_5.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_4.setVisibility(View.VISIBLE);
        }else if(count_image==5){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
             fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_5.setImageBitmap(thumbnail);
            //image_uploadwomework_6.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_5.setVisibility(View.VISIBLE);
        }else if(count_image==6){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_6.setImageBitmap(thumbnail);
           // image_uploadwomework_7.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_6.setVisibility(View.VISIBLE);
        }else if(count_image==7){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_7.setImageBitmap(thumbnail);
           //image_uploadwomework_8.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_7.setVisibility(View.VISIBLE);
        }else if(count_image==8){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_8.setImageBitmap(thumbnail);
           // image_uploadwomework_9.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_8.setVisibility(View.VISIBLE);
        }else if(count_image==9){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_9.setImageBitmap(thumbnail);
            //image_uploadwomework_10.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
           // txt_add_file_9.setVisibility(View.VISIBLE);

        }/*else  if(count_image==10){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_10.setImageBitmap(thumbnail);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_9.setVisibility(View.GONE);

        }*/

    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        byte[] imageBytes = bytes.toByteArray();

       /* profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        image_uploadwomework.setImageBitmap(bm);
        image_uploadwomework_2.setImageBitmap(bm);
        DOC_TYPE="image";
        txt_add_file.setVisibility(View.VISIBLE);*/


        DOC_TYPE="image";

        if(count_image==1){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            // DOC_TYPE="image";
            image_uploadwomework.setImageBitmap(bm);
            txt_add_file.setVisibility(View.VISIBLE);
        }else if(count_image==2){

            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_2.setImageBitmap(bm);
            //image_uploadwomework_3.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_2.setVisibility(View.VISIBLE);
        }else if(count_image==3){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_3.setImageBitmap(bm);
           // image_uploadwomework_4.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_3.setVisibility(View.VISIBLE);
        }else if(count_image==4){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_4.setImageBitmap(bm);
            //image_uploadwomework_5.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_4.setVisibility(View.VISIBLE);
        }else if(count_image==5){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_5.setImageBitmap(bm);
            //image_uploadwomework_6.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_5.setVisibility(View.VISIBLE);
        }else if(count_image==6){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_6.setImageBitmap(bm);
            //image_uploadwomework_7.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_6.setVisibility(View.VISIBLE);
        }else if(count_image==7){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_7.setImageBitmap(bm);
           // image_uploadwomework_8.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_7.setVisibility(View.VISIBLE);
        }else if(count_image==8){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_8.setImageBitmap(bm);
            //image_uploadwomework_9.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_8.setVisibility(View.VISIBLE);
        }else if(count_image==9){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_9.setImageBitmap(bm);
            //image_uploadwomework_10.setVisibility(View.VISIBLE);
            txt_add_file.setVisibility(View.GONE);
           // txt_add_file_9.setVisibility(View.VISIBLE);

        }/*else  if(count_image==10){
            profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            fillAttachmentList(profile_imageString,DOC_TYPE);

            image_uploadwomework_10.setImageBitmap(bm);
            txt_add_file.setVisibility(View.GONE);
            txt_add_file_10.setVisibility(View.GONE);
        }*/



    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void pdfatteched(Intent data) {
        realpath=convertMediaUriToPath(data.getData());
        if(realpath==null){
            enternal();
        }else {
            DOC_TYPE="pdf";

           /* profile_imageString=getBase64FromPath(realpath);
            image_uploadwomework.setImageResource(R.drawable.ic_pdf);
            image_uploadwomework_2.setImageResource(R.drawable.ic_pdf);
            txt_add_file.setVisibility(View.VISIBLE);*/

            if(count_image==1){
                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);
                image_uploadwomework.setImageResource(R.drawable.ic_pdf);                // DOC_TYPE="image";
                txt_add_file.setVisibility(View.VISIBLE);
            }else if(count_image==2){

                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);

                image_uploadwomework_2.setImageResource(R.drawable.ic_pdf);
                //image_uploadwomework_3.setVisibility(View.VISIBLE);
                txt_add_file.setVisibility(View.GONE);
                txt_add_file_2.setVisibility(View.VISIBLE);

            }else if(count_image==3){
                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);

                image_uploadwomework_3.setImageResource(R.drawable.ic_pdf);
               // image_uploadwomework_4.setVisibility(View.VISIBLE);
                txt_add_file.setVisibility(View.GONE);
                txt_add_file_3.setVisibility(View.VISIBLE);
            }else if(count_image==4){
                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);

                image_uploadwomework_4.setImageResource(R.drawable.ic_pdf);
                txt_add_file.setVisibility(View.GONE);
                txt_add_file_3.setVisibility(View.VISIBLE);
               // image_uploadwomework_5.setVisibility(View.VISIBLE);
            }else if(count_image==5){
                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);

                image_uploadwomework_5.setImageResource(R.drawable.ic_pdf);
               // image_uploadwomework_6.setVisibility(View.VISIBLE);
                txt_add_file.setVisibility(View.GONE);
                txt_add_file_5.setVisibility(View.VISIBLE);
            }else if(count_image==6){
                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);

                image_uploadwomework_6.setImageResource(R.drawable.ic_pdf);
                //image_uploadwomework_7.setVisibility(View.VISIBLE);
                txt_add_file.setVisibility(View.GONE);
                txt_add_file_6.setVisibility(View.VISIBLE);
            }else if(count_image==7){
                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);

                image_uploadwomework_7.setImageResource(R.drawable.ic_pdf);
               // image_uploadwomework_8.setVisibility(View.VISIBLE);
                txt_add_file.setVisibility(View.GONE);
                txt_add_file_7.setVisibility(View.VISIBLE);
            }else if(count_image==8){
                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);

                image_uploadwomework_8.setImageResource(R.drawable.ic_pdf);
                //image_uploadwomework_9.setVisibility(View.VISIBLE);
                txt_add_file.setVisibility(View.GONE);
                txt_add_file_8.setVisibility(View.VISIBLE);
            }else if(count_image==9){
                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);

                image_uploadwomework_9.setImageResource(R.drawable.ic_pdf);
              //  image_uploadwomework_10.setVisibility(View.VISIBLE);
                txt_add_file.setVisibility(View.GONE);
              //  txt_add_file_9.setVisibility(View.VISIBLE);

            }/*else if(count_image==10){
                profile_imageString=getBase64FromPath(realpath);
                fillAttachmentList(profile_imageString,DOC_TYPE);

                image_uploadwomework_10.setImageResource(R.drawable.ic_pdf);
                txt_add_file.setVisibility(View.GONE);
                txt_add_file_10.setVisibility(View.GONE);

            }*/


        }
    }
    public String convertMediaUriToPath(Uri uri) {
        String path = null;
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj,  null, null, null);
        if(cursor ==null){
            enternal();
        }else {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        }
        return path;
    }
    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {
            File file = new File(String.valueOf(path));
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }
    private void enternal(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please select .pdf file from internal storage(File Manager)");
        alertDialogBuilder.setTitle("Error!");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    ///////////////audio uri//////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void audioatteched(Intent data) {
        realpath=convertMediaUriToPathaudio(data.getData());


        if(realpath==null){
            enternalaudio();
        }else {
            DOC_TYPE="audio";
            String[]  splits = realpath.split("\\.");

            String ext  = splits[splits.length-1];
            if(ext.equals("mp3")){
                if(count_image==10){
                    profile_imageString=getBase64FromPathaudio(realpath);
                    fillAttachmentList(profile_imageString,DOC_TYPE);
                    final MediaController mediacontroller = new MediaController(this);
                    audio_upload.setMediaController(mediacontroller);
                    mediacontroller.setAnchorView(audio_upload);
                    audio_upload.setVideoURI(Uri.parse((realpath)));
                    audio_upload.setBackgroundColor(Color.RED);
                    audio_upload.requestFocus();
                    audio_upload.start();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Please select .mp3 file from internal storage(File Manager).",Toast.LENGTH_LONG);

            }



        }

    }
    public String convertMediaUriToPathaudio(Uri uri) {
        String path = null;


        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj,  null, null, null);
        if(cursor ==null){
            enternalaudio();
        }else {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        }
        return path;
    }
    public static String getBase64FromPathaudio(String path) {
        String base64 = "";
        try {
            File file = new File(String.valueOf(path));
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }
    private void enternalaudio(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please select .audio file from internal storage(File Manager)");
        alertDialogBuilder.setTitle("Error!");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    ////////////////////////////////////////CLASSES//////////////////////////////////////////////////////////
    private class SelectclassDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Dialy_Diary.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.HOMEWORK_CLASSFILTER_API,Paradetails(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
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
                            .make(layout_main_diary, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_main_diary, "Data not Found. Please Try Again", Snackbar.LENGTH_LONG)
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


   /* ///////////////////////////////////////////Section Selection chekbox//////////////////////////////////
    private class SelectSection extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Dialy_Diary.this);
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
                        store_att_hashmap_section.clear();
                        JSONArray array= new JSONArray(response);
                        // sb_codes_section = new StringBuilder(array.length());
                        CustomListSectionchk = new ArrayList<SectionList_Checkbox_Helper>();


                        String   SectionId="";
                        String   SectionName="Select";

                        SectionList_Checkbox_Helper sched = new SectionList_Checkbox_Helper();
                        sched.setSecID(SectionId);
                        sched.setSecName(SectionName);
                        CustomListSectionchk.add(sched);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            SectionId=object.getString("SectionId");
                            SectionName=object.getString("SectionName");

                            sched = new SectionList_Checkbox_Helper();
                            sched.setSecID(SectionId);
                            sched.setSecName(SectionName);
                            sched.setcheck_status(check_status);
                            CustomListSectionchk.add(sched);
                            if(check_status==true){
                                store_att_hashmap_section.put(SectionId,SectionName);
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
            // progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    adapter1 = new Section_Checkbox_Adapter(getApplicationContext(), R.layout.spinner_section_item_chekbox, CustomListSectionchk,res);
                    select_section.setAdapter(adapter1);
                    adapter1.setCheckcustomButtonListener(Dialy_Diary.this);
                    adapter1.notifyDataSetChanged();

                    break;
                case -2:
                    if(adapter1!= null){
                        select_section.setAdapter(null);
                    }
                    Snackbar snackbar = Snackbar
                            .make(layout_live_class, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                    if(adapter1!= null){
                        select_section.setAdapter(null);
                    }
                    Snackbar snackbar1 = Snackbar
                            .make(layout_live_class, "Data Not Found. Please Try Again", Snackbar.LENGTH_LONG)
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
    }*/

    ///////////////////////////////////////////Section Selection//////////////////////////////////
    private class SelectSection extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Dialy_Diary.this);
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
                            .make(layout_main_diary, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_main_diary, "Section Data Not Found. Please Try Again", Snackbar.LENGTH_LONG)
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

    //////////////////////////////////////////API MSubjectEmpWise ///////////////////////////////

    private class SelectSubject extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Dialy_Diary.this);
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
                            .make(layout_main_diary, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_main_diary, "Subject data not found", Snackbar.LENGTH_LONG)
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
    //////////////////////////////////////API SUBJECT SUBMIT////////////////////////
    private class SubmitHomework extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Dialy_Diary.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(MAIN_IPLINK+ServerApiadmin.SUBJECT_SUBMIT_API,ParaSub(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");
            //Log.d("request!", response);
            if(response!=null){
                response=response.replace("\"", "");
                response=response.replace("\\", "");
                if(Integer.parseInt(response)== 1){
                    status = 1;
                }else if(Integer.parseInt(response)== -1){
                    status = 3;
                }else{
                    status = -2;
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
                     edt_homework.getText().clear();
                     edt_homework_topic.getText().clear();
                     showSuccessDialog();
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_main_diary, "Homework not submitted. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_main_diary, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;

                case 3:
                    Snackbar snackbar3 = Snackbar
                            .make(layout_main_diary, "Homework Already Submitted", Snackbar.LENGTH_LONG)
                            .setAction("Select Other Subject", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar3.setActionTextColor(Color.RED);
                    snackbar3.show();
                    break;
            }
        }
    }

    public String ParaSub(String schoolCode, String branchCode, String SessionId, String fyId,String EmployeeCode){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();

        //JSONObject attachmentList = new JSONObject();


        try {
           /* attachmentList.put("Attachment", val);
            attachmentList.put("Name", DOC_TYPE);
            jlist.put(attachmentList);*/
            jsonParam.put("schoolCode", schoolCode);
            jsonParam.put("branchCode", branchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("fyId",fyId);
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("SectionId",section_ID);
            jsonParam.put("SubjectId",subject_ID);
            jsonParam.put("HomeWorkDate",todaysDate);
            jsonParam.put("ShowDate",show_date_homework);
            jsonParam.put("ShowTime","00:00");
            jsonParam.put("HideDate",end_date_homework);
            jsonParam.put("EmployeeCode",EmployeeCode);
            jsonParam.put("CreatedBy","1");
            jsonParam.put("UpdatedBy","1");
            jsonParam.put("HomeworkFor","0");
            jsonParam.put("HideTime","23:59");
            jsonParam.put("TopicName",topic_name);
            jsonParam.put("HomeworkDetails",homework_detlais);
            jsonParam.put("FlagForMobile","1");
            jsonParam.put("Active","1");
            jsonParam.put("TransId","0");
            jsonParam.put("VoucherNo","0");
            jsonParam.put("StudentsCode",arraylist_codes);
            jsonParam.put("AttachmentList",jlist);// Attachment  ///store_att_hashmap
           //  [{"Attachment":val,"Name":"image"}]
             // jsonParam.put("AttachmentList",profile_imageString);// Attachment
            jsonParam.put("Name",DOC_TYPE);
            jsonParam.put("VedioPath",link_vedio);
            jsonParam.put("VedioHideDate",end_date_video);
            jsonParam.put("VedioHideTime","23:59");
            jsonParam.put("IsSubmissionRequired",value_store);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    ///////////////////////////ClassStudent_List////////////////////////////////////////////
    private class StudentList extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Dialy_Diary.this);

        @Override
        protected void onPreExecute() {
            //progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status = 0;
            JsonParser josnparser = new JsonParser(getApplicationContext());
            String response = josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STUDENT_LIST, ParastudentList(applicationController.getschoolCode(), applicationController.getBranchcode(), applicationController.getSeesionID(), applicationController.getFyID()), "1");
            if (response != null) {
                if (response.length() > 5) {
                    try {
                        JSONArray array = new JSONArray(response);
                        arraylist_codes= new StringBuilder(array.length());
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String StudentCode = object.getString("StudentCode");
                            arraylist_codes.append(StudentCode + ",");
                            status = 1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status = -1;
                    }
                } else {
                    status = -2;
                }
            } else {
                status = -1;
            }
            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
           // progressDialog.dismiss();
            switch (s) {
                case 1:
                 new SubmitHomework().execute();
                    break;
                case -2:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_main_diary, "Student not found in this class", Snackbar.LENGTH_LONG)
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
                case -1:
                    Snackbar snackbar2 = Snackbar
                            .make(layout_main_diary, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar2.setActionTextColor(Color.RED);
                    snackbar2.show();
            }
        }
        public String ParastudentList(String SchoolCode, String BranchCode, String SessionId, String FYId) {
            JSONObject jsonParam1 = new JSONObject();
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("SchoolCode", SchoolCode);
                jsonParam.put("BranchCode", BranchCode);
                jsonParam.put("SessionId", SessionId);
                jsonParam.put("FYId", FYId);
                jsonParam.put("AttendanceDate", todaysDate);
                jsonParam.put("Action", "5");
                jsonParam.put("ClassId", class_ID);
                jsonParam.put("SectionId", section_ID);
                jsonParam.put("BranchId",branch_ID);
                jsonParam1.put("obj", jsonParam);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonParam1.toString();
        }
    }
    private void showSuccessDialog() {
        final Dialog dialog = new Dialog(Dialy_Diary.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.success_dialog);
        dialog.setTitle("");
        // set the custom dialog components - text, image and button
        TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        text_label.setText("Home work Submit Successfully");
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
               // new HomeworkGETAPI().execute();
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    ////////////////////////////////////////////////API Homework Get api details //////////////////////////////////////
    private class HomeworkGETAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Dialy_Diary.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.HOMEWORK_INSERT_API,Paradetails(applicationController.getSeesionID(),applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        homework_helpers = new ArrayList<Homework_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   ClassName=object.getString("ClassName");
                            String   SubjectName=object.getString("SubjectName");
                            String SectionName=object.getString("SectionName");
                            String TopicName=object.getString("TopicName");
                            String SubmissionDate=object.getString("SubmissionDate");
                            String VedioPath=object.getString("VedioPath");
                            String TransId=object.getString("TransId");
                            String VoucherNo=object.getString("VoucherNo");

                            String Attachment=object.getString("Attachment");
                            if(Attachment.length()>5){
                                String imagerplace= MAIN_IPLINK+Attachment;
                                Attachment=imagerplace.replace("..","");
                            }else{
                                Attachment="";
                            }

                            if(SubmissionDate.equals(todaysDate)){
                                Homework_Helper item = new Homework_Helper(ClassName,SubjectName,SectionName,TopicName,VedioPath,TransId,VoucherNo,Attachment);
                                homework_helpers.add(item);
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
                    //list_womework.setVisibility(View.VISIBLE);
                    Homework_Insert_Adapter adapter = new Homework_Insert_Adapter(getApplicationContext(),R.layout.item_homework_insert, homework_helpers);
                    list_womework.setAdapter(adapter);
                    break;
                case -2:
                  /*  Snackbar snackbar = Snackbar
                            .make(layout_main_diary, "Data Not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   *//* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*//*

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();*/
                   // list_womework.setVisibility(View.INVISIBLE);
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_main_diary, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Paradetails(String SessionId,String schoolCode,String branchCode,String fyId,String EmployeeCode){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("schoolCode", schoolCode);
            jsonParam.put("branchCode", branchCode);
            jsonParam.put("fyId",fyId);
            jsonParam.put("EmployeeId",EmployeeCode);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    private class SelectBranch extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Dialy_Diary.this);
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
                            .make(layout_main_diary, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_main_diary, "Branch Data Not Found", Snackbar.LENGTH_LONG)
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
    private void retrieveValuesFromListMethod(Map map) {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        ArrayList<String> ar = new ArrayList<String>();
        String key;
        String value;
        int counter = 0;
      //  st=new Student_Attendance_Helper();
        List<Student_Attendance_Helper.StudentDetails> pnList = new ArrayList<Student_Attendance_Helper.StudentDetails>();
        while(itr.hasNext())

      //  st.setStudentList(pnList);
        System.out.println(ar.toString());



        // Toast.makeText(getApplicationContext(), store_att_hashmap + "---DELETE", Toast.LENGTH_LONG).show();
    }
    
    
    
    private void fillAttachmentList(String base64Url, String docType){
        JSONObject attachmentList = new JSONObject();
        try{
            attachmentList.put("Attachment", base64Url);
            attachmentList.put("Name", docType);
            jlist.put(attachmentList);
        }
        catch (Exception ex){
            
        }
    }


    private boolean permissions(List<String> listPermissionsNeeded) {

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return true;
        }
        return false;
    }


    private  void  chkallowpermision(){
        AlertDialog alertDialog = new AlertDialog.Builder(Dialy_Diary.this).create();
        alertDialog.setTitle("Permission necessary");
        alertDialog.setMessage("External storage permission is necessary");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (permissions(permissionList)==false) {
                            permissionList = PermissionUtils.checkAndRequestPermissions(Dialy_Diary.this);
                            permissions(permissionList);
                            flag=1;
                        }

                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}
