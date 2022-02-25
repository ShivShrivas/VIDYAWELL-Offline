package vidyawell.infotech.bsn.admin;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import vidyawell.infotech.bsn.admin.Adapters.BranchList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.BranchList_Checkbox_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.DefaultModerator_Checkbox_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Default_Moderator_List_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.EMP_Presenter_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Moderator_Checkbox_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Moderator_List_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Section_Checkbox_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.StreamList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.StreamList_Checkbox_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Student_Checkbox_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Student_List_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.BranchList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.BranchList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.DefaultModerator_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Default_Moderator_List_Helper;
import vidyawell.infotech.bsn.admin.Helpers.EMP_Presenter_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Moderator_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Moderator_List_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.StreamList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Stream_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_List_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static java.lang.Integer.parseInt;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static org.apache.commons.beanutils.WrapDynaClass.clear;
import static vidyawell.infotech.bsn.admin.GlobalClass.Std_List;
import static vidyawell.infotech.bsn.admin.MainActivity_Admin.theMonth;
import static vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin.MAIN_IPLINK;

public class Virtual_Class extends AppCompatActivity implements Section_Checkbox_Adapter.CheckcustomButtonListener, BranchList_Checkbox_Adapter.CheckcustomButtonListener2,StreamList_Checkbox_Adapter.CheckcustomButtonListener3,Student_List_Adapter.CheckcustomButtonListener,Student_List_Adapter.CheckcustomButtonListener_2,Default_Moderator_List_Adapter.CheckcustomButtonListener_default_moderator,Default_Moderator_List_Adapter.CheckcustomButtonListener_default_moderator_new,Moderator_List_Adapter.CheckcustomButtonListener_moderator,Moderator_List_Adapter.CheckcustomButtonListener_moderator_new   {
    ApplicationControllerAdmin applicationController;
    Typeface typeface;
    Spinner select_class,select_section,select_branch,select_stream,select_is_active;
    RelativeLayout layout_live_class;
    ClassList_Adapter adapter;
    EMP_Presenter_Adapter EMP_Presener_adapter;
    Section_Checkbox_Adapter adapter1;
    BranchList_Checkbox_Adapter adapter2;
    StreamList_Checkbox_Adapter     StreamListadapter;
    Student_Checkbox_Adapter adapterstudent;
    Moderator_Checkbox_Adapter moderator_adapter;
  //  DefaultModerator_Checkbox_Adapter defaultModerator_checkbox_adapter;
    DefaultModerator_Checkbox_Adapter defaultModerator_checkbox_adapter;
    String class_ID,section_ID,branch_ID,stream_ID;
    String class_Name="";
    boolean section_ID_multi;
    TextView txt_count;
    int count;
    int abcd;
    String selectedDiv;
    String response;

    public ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    public  ArrayList<SectionList_Checkbox_Helper> CustomListSectionchk= new ArrayList<SectionList_Checkbox_Helper>();
    public  ArrayList<BranchList_Checkbox_Helper> CustomListBranch= new ArrayList<BranchList_Checkbox_Helper>();
    public  ArrayList<StreamList_Checkbox_Helper> CustomListStream = new ArrayList<StreamList_Checkbox_Helper>();
    public  ArrayList<Student_Checkbox_Helper> CustomListStudent = new ArrayList<Student_Checkbox_Helper>();
    public  ArrayList<Moderator_Checkbox_Helper> CustomListModerator = new ArrayList<Moderator_Checkbox_Helper>();
    public  List<DefaultModerator_Checkbox_Helper> CustomListDefaultModerator;
    public ArrayList<EMP_Presenter_Helper> CustomListEMP_Presenter = new ArrayList<EMP_Presenter_Helper>();

    DatePickerDialog datePickerDialog;
    TextView tv_date_2,tv_month_2;
    TextView date_text_2;
    String show_date_homework;
    Calendar calendar;
    private int mHour, mMinute;
    String time_set;
    String format;
    TextView date_text,Text_Datev,text_monthnamev;
    EditText edit_duration,edit_meeting,edt__topic,edt_description,edit_massage;
    Button live_create_submit;
    EditText txt_subject,set_subject,txt_topic,set_topic,set_date,set_time,set_subject_new,set_topic_new,set_class_new;
    EditText set_date_new,set_time_new,edit_name_presenter,edit_name_presenter_one;
    EditText set_class;
    String multipale_id,multipale_name;
    boolean check_box;
    CheckBox checkbox_sec;
    HashMap<String,String> store_att_hashmap;
    HashMap<String,String> store_att_hashmap_section;
    HashMap<String,String> store_att_hashmap_branch;
    HashMap<String,String> store_att_hashmap_stream;
    HashMap<String,String> store_att_hashmap_defaultmoderator;
    HashMap<String,String> store_att_hashmap_moderator;
    boolean check_status=false;
    boolean check_status_moderator=false;
    boolean check_status_defaultmoderator=false;
    private static int select_student_count;
    private static int select_student_count_section;
    private static int select_student_count_branch;
    private static int select_student_count_stream;
    private static int select_student_count_defaultmoderator;
    private static int select_student_count_moderator;
    StringBuilder sb_codes;
    StringBuilder sb_codes_section;
    StringBuilder sb_codes_branch;
    StringBuilder sb_codes_stream;
    StringBuilder sb_codes_defaultmoderator;
    StringBuilder sb_codes_moderator;
    int abc;
    Spinner select_student,select_presenter,select_moderator,select_default_moderator;
    Button click_student;
    String SectionIds="",BranchIds="",StreamIds="",StudentIds="",Moderatords="",DefaultModeratords="";
    String Mobile_No,Email_send;
    String EMPPresenter,EMPPresenter_name="";
    CheckBox send_sms_checkbox,send_email_checkbox;
    boolean smssnd_mail=false;
    boolean smssnd_sms=false;
    String mobilevalue="";
    String emailevalue="";
    String Duration="",Subject_name="",Topic_name="",active_id;
    String value_store="";
    String presenter="",subject="",topic="",classname="",datename="",Timename="";
    String presenter_new="",subject_new="",topic_new="",classname_new="",datename_new="",Timename_new="";
    String description="",message="";
    EditText txt_description,txt_message;
    CheckBox check_select_all,check_select_All_default_moderator,check_select_All_moderator;
    EditText student_search,default_moderator_search_live,moderator_search_live;
    boolean check_data;
    String std_list="";
    TextView txt_count_st_list,txt_count_default_modrator,txt_count_modrator;
    SharedPreferences sharedpreferences;
    List<Student_List_Helper> student_list_helpers;
    List<Default_Moderator_List_Helper> default_moderator_list_helpers;
    List<Moderator_List_Helper> moderator_list_helpers;
    Student_List_Adapter student_list_adapter;
    Default_Moderator_List_Adapter default_moderator_list_adapter;
    Moderator_List_Adapter moderator_list_adapter;
    ListView student_list_list,default_moderator_list,moderator_list;
    RelativeLayout layout_select_all,layout_select_all_defaultmoderator,layout_select_all_moderator;
    LinearLayout chat_layout;
    ValueAnimator mAnimator;
    private Animation animationDown;
    boolean studentchk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual__class);

        applicationController=(ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_live_class));
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Live Class");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);

        select_class=(Spinner)findViewById(R.id.select_class);
        select_section=(Spinner)findViewById(R.id.select_section);
        select_branch=(Spinner)findViewById(R.id.select_branch);
        select_stream=(Spinner)findViewById(R.id.select_stream);
        select_is_active=(Spinner)findViewById(R.id.select_is_active);
        layout_live_class=(RelativeLayout)findViewById(R.id.layout_live_class);
        tv_date_2=(TextView)findViewById(R.id.Text_Datev_2);
        tv_month_2=(TextView)findViewById(R.id.text_monthnamev_2);
        date_text_2=(TextView)findViewById(R.id.date_text_2);
        date_text=(TextView)findViewById(R.id.date_text);
        Text_Datev=(TextView)findViewById(R.id.Text_Datev);
        text_monthnamev=(TextView)findViewById(R.id.text_monthnamev);
        edit_duration=(EditText)findViewById(R.id.edit_duration);
        //edit_meeting=(EditText)findViewById(R.id.edit_meeting);
        //edt__topic=(EditText)findViewById(R.id.edt__topic);
        edt_description=(EditText)findViewById(R.id.edt_description);
       // edit_massage=(EditText)findViewById(R.id.edit_massage);
        live_create_submit=(Button)findViewById(R.id.live_create_submit);
        txt_count=(TextView)findViewById(R.id.txt_count);
        txt_subject=(EditText)findViewById(R.id.txt_subject);
        set_subject=(EditText)findViewById(R.id.set_subject);
        txt_topic=(EditText)findViewById(R.id.txt_topic);
     //   set_topic=(EditText)findViewById(R.id.set_topic);
        set_class=(EditText) findViewById(R.id.set_class);
        set_date=(EditText)findViewById(R.id.set_date);
        set_time=(EditText)findViewById(R.id.set_time);
        set_subject_new=(EditText)findViewById(R.id.set_subject_new);
    //    set_topic_new=(EditText)findViewById(R.id.set_topic_new);
        set_class_new=(EditText)findViewById(R.id.set_class_new);
        set_date_new=(EditText)findViewById(R.id.set_date_new);
        set_time_new=(EditText)findViewById(R.id.set_time_new);
        checkbox_sec = (CheckBox)findViewById(R.id.checkbox_sec);
        //select_student=(Spinner)findViewById(R.id.select_student);
        click_student=(Button)findViewById(R.id.click_student);
        select_presenter=(Spinner)findViewById(R.id.select_presenter);
        edit_name_presenter=(EditText)findViewById(R.id.edit_name_presenter);
        edit_name_presenter_one=(EditText)findViewById(R.id.edit_name_presenter_one);
       // select_moderator=(Spinner)findViewById(R.id.select_moderator);
       // select_default_moderator=(Spinner)findViewById(R.id.select_default_moderator);
        send_sms_checkbox=(CheckBox)findViewById(R.id.send_sms_checkbox);
        send_email_checkbox=(CheckBox)findViewById(R.id.send_email_checkbox);
        txt_description=(EditText)findViewById(R.id.txt_description);
        txt_message=(EditText)findViewById(R.id.txt_message);
        txt_count_st_list=(TextView)findViewById(R.id.txt_count_st_list);
        txt_count_default_modrator=(TextView)findViewById(R.id.txt_count_default_modrator);
        txt_count_modrator=(TextView)findViewById(R.id.txt_count_modrator);
        //check_select_all=(CheckBox)findViewById(R.id.check_select_All);
        student_list_list=(ListView)findViewById(R.id.student_list_list);

       // student_search=(EditText)findViewById(R.id.student_search);
       // check_select_all=(CheckBox)findViewById(R.id.check_select_All);
        chat_layout=(LinearLayout)findViewById(R.id.chat_layout);
        layout_select_all=(RelativeLayout)findViewById(R.id.layout_select_all);
       // layout_select_all.setVisibility(View.GONE);
        //sharedpreferences = getSharedPreferences("STSCOUNT", Context.MODE_PRIVATE);



        store_att_hashmap = new HashMap();
        store_att_hashmap_section = new HashMap();
        store_att_hashmap_branch= new HashMap();
        store_att_hashmap_stream= new HashMap();
        store_att_hashmap_defaultmoderator= new HashMap();
        store_att_hashmap_moderator= new HashMap();


        send_sms_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                smssnd_mail =true;
               mobilevalue=  Mobile_No;
            }
        });

        send_email_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                smssnd_sms =true;
                emailevalue= Email_send;

            }
        });



/*
        check_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked==true){
                    check_status=true;
                    new SelectStudent().execute();

                }else{
                    check_status=false;
                    store_att_hashmap.clear();
                    new SelectStudent().execute();

                }



               // Toast.makeText(getApplicationContext(),"sb_codes"+sb_codes,Toast.LENGTH_SHORT).show();

            }
        });
*/

      //  txt_description.setText(EMPPresenter_name);

/////description////////

/*
        description =  "Dear Student,\n"+"Select Presenter"+
                (EMPPresenter_name)+" is inviting you a schedule ,"+Subject_name+" class. \n"+
                "Topic: "+Topic_name +"\n"+"Class: "+class_Name+"\n"+
                "Date:  "+show_date_homework+" HRS \n"+"Duration: "+Duration+" min";

        Log.e("DESC",description);



        message =  "Dear Student,\n"+"Select Presenter"+
                (EMPPresenter_name)+" is inviting you a schedule ,"+Subject_name+" class. \n"+
                "Topic: "+Topic_name +"\n"+"Class: "+class_Name+"\n"+
                "Date:  "+show_date_homework+" HRS \n"+"Duration: "+Duration+" min";

        Log.e("DESC",description);

        txt_description.setText(description);
        txt_message.setText(description);*/

//////////////////////////////////close////////////////////////////////////


        ///////////////////////////////////sms/////////////////////////////////////

       /* message = new StringBuilder(presenter_new).append("\n")
                .append(subject_new).append("\n")
                .append(topic_new).append("\n")
                .append(classname_new).append("\n")
                .append(datename_new).append("\n")
                .append(Timename).toString();

        presenter_new =   edit_name_presenter.getText().toString();
        subject_new=  set_subject_new.getText().toString();
       // topic_new= set_topic_new.getText().toString();
        classname_new=  set_class_new.getText().toString();
        datename_new= set_date_new.getText().toString();
        Timename_new= set_time_new.getText().toString();*/


        //////////////////////////////close///////////////////////////////////

        select_is_active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                active_id=parent.getSelectedItem().toString();


                if(active_id.equals("Yes")){
                    value_store = "true";
                    active_id=parent.getSelectedItem().toString();
                }else {
                    value_store = "false";
                    active_id=parent.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        live_create_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  store_att_hashmap.put(multipale_id,multipale_name);
                retrieveValuesFromListMethod(store_att_hashmap);
                store_att_hashmap.clear();*/

          Duration=      edit_duration.getText().toString();
          Subject_name=      txt_subject.getText().toString();
          Topic_name=      txt_topic.getText().toString();
//SectionIds,BranchIds,StreamIds,
              //  Toast.makeText(getApplicationContext(), "Live Class Created Sucessfully", Toast.LENGTH_LONG).show();
                if(SectionIds.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Select Selection", Toast.LENGTH_LONG).show();

                }else if(BranchIds.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Select Branch", Toast.LENGTH_LONG).show();

                }else if(StreamIds.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Select Stream", Toast.LENGTH_LONG).show();

                }/*else if(StudentIds.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Select Student", Toast.LENGTH_LONG).show();

                }*/
                else if(date_text_2.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Please Select Start Date", Toast.LENGTH_LONG).show();
                } else if(Text_Datev.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please Select Time", Toast.LENGTH_LONG).show();

                }else if(edit_duration.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Please Enter Duration", Toast.LENGTH_LONG).show();

                }
                else if(txt_subject.getText().toString().length()==0){
                  Toast.makeText(getApplicationContext(), "Please Enter Meeting", Toast.LENGTH_LONG).show();

                }else  if(txt_topic.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "Please Enter Topic Name", Toast.LENGTH_LONG).show();


                } else {
                     new SubmitMeetingAPI().execute();
                }







               // new SelectStream().execute();  send api call

            }
        });

        txt_subject.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

              /*  txt_description = (EditText) findViewById(R.id.txt_description);
                txt_message = (EditText) findViewById(R.id.txt_message);
                txt_description.setText(s);
                txt_message.setText(s);*/
                Subject_name=s.toString();
                Editmessagebind(EMPPresenter_name,Subject_name,Topic_name,class_Name,show_date_homework,Duration,time_set);



            }
        });




       /* edit_name_presenter.setText(EMPPresenter_name);
        edit_name_presenter_one.setText(EMPPresenter_name);
        set_class.setText(class_ID);
        set_class_new.setText(class_ID);
        set_date.setText(show_date_homework);*/
/////////////////////////////////////////////
        txt_topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              /*  txt_description = (EditText) findViewById(R.id.txt_description);
                txt_message = (EditText) findViewById(R.id.txt_message);
                txt_description.setText(s);
                txt_message.setText(s);*/
                Topic_name=s.toString();
                Editmessagebind(EMPPresenter_name,Subject_name,Topic_name,class_Name,show_date_homework,Duration,time_set);

            }
        });

        edit_duration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               /* txt_description = (EditText) findViewById(R.id.txt_description);
                txt_message = (EditText) findViewById(R.id.txt_message);
                txt_description.setText(s);
                txt_message.setText(s);*/
               Duration=s.toString();

                Editmessagebind(EMPPresenter_name,Subject_name,Topic_name,class_Name,show_date_homework,Duration,time_set);

            }
        });



/*
        txt_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
           */
/*   txt_description = (EditText) findViewById(R.id.txt_description);
                txt_message = (EditText) findViewById(R.id.txt_message);
                txt_description.setText(s);
                txt_message.setText(s);*//*


               // Duration=s.toString();
                        EMPPresenter_name="";
                        Subject_name="";
                        Topic_name="";
                        class_Name="";
                        show_date_homework="";
                        Duration="";
                        time_set="";




            }
        });
*/

/*
        txt_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              */
/* txt_description = (EditText) findViewById(R.id.txt_description);
                txt_message = (EditText) findViewById(R.id.txt_message);
                txt_description.setText(s);
                txt_message.setText(s);*//*


                // Duration=s.toString();
                EMPPresenter_name="";
                Subject_name="";
                Topic_name="";
                class_Name="";
                show_date_homework="";
                Duration="";
                time_set="";



            }
        });
*/



        new SelectclassDetails().execute();
        new SelectEMPPresenter().execute();
         select_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                class_ID    =((TextView) view.findViewById(R.id.text_cID)).getText().toString();
                class_Name    =((TextView) view.findViewById(R.id.text_cname)).getText().toString();
/*                txt_description.setText(class_Name);
                txt_message.setText(class_Name);
                */
                Editmessagebind(EMPPresenter_name,Subject_name,Topic_name,class_Name,show_date_homework,Duration,time_set);



//txt_description,txt_message;
                // Toast.makeText(getApplicationContext(), class_ID + "---ClassID", Toast.LENGTH_LONG).show();
                new SelectSection().execute();
                new SelectBranch().execute();
                new SelectStream().execute();



            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

       //  txt_count.setText(count);

      select_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


              /*  store_att_hashmap.put(multipale_id,multipale_name);
                retrieveValuesFromListMethod(store_att_hashmap);*/



               // section_ID = ((TextView) view.findViewById(R.id.checkbox_sec)).getText().toString();

               // count=   section_ID.split(",").length;

              //  retrieveValuesFromListMethod(store_att_hashmap);
               // store_att_hashmap.put(multipale_id,multipale_name);


               // store_att_hashmap.put(multipale_id,multipale_name);
               // store_att_hashmap.get(store_att_hashmap);
               // store_att_hashmap.put(multipale_id,multipale_name);
               // retrieveValuesFromListMethod(store_att_hashmap);



              /*  if(section_ID == null || section_ID.isEmpty()) {
                    section_ID = ((TextView) view.findViewById(R.id.text_sID_chk_sec)).getText().toString();


                }
                else {
                    section_ID    =section_ID+","+ ((TextView) view.findViewById(R.id.text_sID_chk_sec)).getText().toString();
                }
                if (section_ID != null && !section_ID.isEmpty()) {
                    count = section_ID.split(",").length;
                }
*/




               // select_section.getChildCount();
               // Toast.makeText(getApplicationContext(),""+section_ID,Toast.LENGTH_SHORT).show();
              // Toast.makeText(getApplicationContext(),""+store_att_hashmap.get(multipale_id)+"--id",Toast.LENGTH_SHORT).show();
             //   new SelectBranch().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        select_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch_ID    = ((TextView) view.findViewById(R.id.text_sID_chk_bran)).getText().toString();
               // new SelectStream().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         select_stream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stream_ID    = ((TextView) view.findViewById(R.id.text_sID_chk_stream)).getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


/*
        select_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                store_att_hashmap.put(multipale_id,multipale_name);
                retrieveValuesFromListMethod(store_att_hashmap);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/


        click_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* store_att_hashmap.put(multipale_id,multipale_name);
                retrieveValuesFromListMethod(store_att_hashmap);
                StudentIds= "";
                new SelectStudent().execute();*/

                showSuccessDialog();

              /*  Intent intent = new Intent(Virtual_Class.this, Student_List.class);
                intent.putExtra("ClassId",class_ID);
                intent.putExtra("SectionId",SectionIds);
                intent.putExtra("StreamId",StreamIds);
                intent.putExtra("BranchId",BranchIds);
                startActivityForResult(intent,100);*/


            }
        });


        txt_count_default_modrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialogdefaultmoderator();
            }
        });

        txt_count_modrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialogModerator();
            }
        });



        select_presenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EMPPresenter    = ((TextView) view.findViewById(R.id.text_EMP_ID)).getText().toString();
                EMPPresenter_name    = ((TextView) view.findViewById(R.id.text_emp_name)).getText().toString();

               // txt_description.setText(EMPPresenter_name);
               // txt_message.setText(EMPPresenter_name);
                Editmessagebind(EMPPresenter_name,Subject_name,Topic_name,class_Name,show_date_homework,Duration,time_set);
               // new SelectModerator().execute();
                //new SelectDefaultModerator().execute();
               // new APIDedaultModerator().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

/*
        select_moderator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/



        LinearLayout layout_calendar_2=(LinearLayout)findViewById(R.id.layout_cal_2);
        layout_calendar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Virtual_Class.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date_2.setText(dayOfMonth+"");
                                tv_month_2.setText(month);
                                show_date_homework=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                                date_text_2.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
                               // txt_description.setText(show_date_homework);
                              //  txt_message.setText(show_date_homework);
                                Editmessagebind(EMPPresenter_name,Subject_name,Topic_name,class_Name,show_date_homework,Duration,time_set);

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

//                                //new HomeworkGETAPI().execute();

                            }
                        }, mYear, mDay, cDay);
                // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        LinearLayout layout_calendar_3=(LinearLayout)findViewById(R.id.layout_calendar_3);
        layout_calendar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                calendar = Calendar.getInstance();
                mHour = calendar.get(HOUR_OF_DAY);
                mMinute = calendar.get(MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Virtual_Class.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                time_set = (hourOfDay+":"+minute);
                                date_text.setText(hourOfDay+":"+minute);
                                Text_Datev.setText(hourOfDay+":"+minute);
                               // txt_description.setText(hourOfDay + ":" + minute);
                               // txt_message.setText(hourOfDay + ":" + minute);
                                Editmessagebind(EMPPresenter_name,Subject_name,Topic_name,class_Name,show_date_homework,Duration,time_set);

                                text_monthnamev.setText(format);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


    }


    private void Editmessagebind(String EMPPresenter_name, String subject_name, String topic_name, String class_Name, String show_date_homework, String duration,String time){

       // description =  "Dear Student,\n"+"Select Presenter"+
        description =  "Dear Student,\n"+""+
                EMPPresenter_name+" is inviting you a schedule ,"+subject_name+" class. \n"+
                "Topic: "+topic_name +"\n"+"Class: "+ class_Name +"\n"+
                "Date:  "+ show_date_homework +" "+time+" HRS \n"+"Duration: "+duration+" min";

       // Log.e("DESC",description);


       // message =  "Dear Student,\n"+"Select Presenter"+
        message =  "Dear Student,\n"+""+
                (this.EMPPresenter_name)+" is inviting you a schedule ,"+Subject_name+" class. \n"+
                "Topic: "+Topic_name +"\n"+"Class: "+ this.class_Name +"\n"+
                "Date:  "+ this.show_date_homework +" "+time+" HRS \n"+"Duration: "+Duration+" min";

       // Log.e("Mess",message);

        txt_description.setText(description);
        txt_message.setText(message);


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

    private void retrieveValuesFromListMethodSection(Map map) {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        ArrayList<String> ar = new ArrayList<String>();
        sb_codes_section = new StringBuilder(map.size());
        select_student_count_section=map.size();
        String key;
        String value;
        while(itr.hasNext())
        {
            key = (String)itr.next();
            value = (String)map.get(key);
            System.out.println(value);
            ar.add(value);
            sb_codes_section.append(value+",");
        }
        System.out.println(ar.toString());
        System.out.println(sb_codes_section.toString());
        if(sb_codes_section.length()==0){
            Toast.makeText(getApplicationContext(),"Select minimum One Student",Toast.LENGTH_LONG).show();
        }else{
            //new SubmitMeetingAPI().execute();
            // new SubmitMeetingAPI().execute();
        }
        //System.out.println(ar.toString());
        //System.out.println(sb_codes.toString());
        // new SelectStream().execute(); send api calll
        // new SelectStudent().execute();
    }

    private void retrieveValuesFromListMethodBranch(Map map) {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        ArrayList<String> ar = new ArrayList<String>();
        sb_codes_branch = new StringBuilder(map.size());
        select_student_count_branch=map.size();
        String key;
        String value;
        while(itr.hasNext())
        {
            key = (String)itr.next();
            value = (String)map.get(key);
            System.out.println(value);
            ar.add(value);
            sb_codes_branch.append(value+",");
        }
        System.out.println(ar.toString());
        System.out.println(sb_codes_branch.toString());
        if(sb_codes_branch.length()==0){
            Toast.makeText(getApplicationContext(),"Select minimum One Student",Toast.LENGTH_LONG).show();
        }else{
            //new SubmitMeetingAPI().execute();
            // new SubmitMeetingAPI().execute();
        }
        //System.out.println(ar.toString());
        //System.out.println(sb_codes.toString());
        // new SelectStream().execute(); send api calll
        // new SelectStudent().execute();
    }

    private void retrieveValuesFromListMethodStream(Map map) {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        ArrayList<String> ar = new ArrayList<String>();
        sb_codes_stream = new StringBuilder(map.size());
        select_student_count_stream=map.size();
        String key;
        String value;
        while(itr.hasNext())
        {
            key = (String)itr.next();
            value = (String)map.get(key);
            System.out.println(value);
            ar.add(value);
            sb_codes_stream.append(value+",");
        }
        System.out.println(ar.toString());
        System.out.println(sb_codes_stream.toString());
        if(sb_codes_stream.length()==0){
            Toast.makeText(getApplicationContext(),"Select minimum One Student",Toast.LENGTH_LONG).show();
        }else{
            //new SubmitMeetingAPI().execute();
            // new SubmitMeetingAPI().execute();
        }
        //System.out.println(ar.toString());
        //System.out.println(sb_codes.toString());
        // new SelectStream().execute(); send api calll
        // new SelectStudent().execute();
    }



    private void retrieveValuesFromListMethodDefaultModerator(Map map) {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        ArrayList<String> ar = new ArrayList<String>();
        sb_codes_defaultmoderator = new StringBuilder(map.size());
        select_student_count_defaultmoderator=map.size();
        String key;
        String value;
        while(itr.hasNext())
        {
            key = (String)itr.next();
            value = (String)map.get(key);
            System.out.println(key);
            ar.add(value);
            sb_codes_defaultmoderator.append(key+",");
        }
        System.out.println(ar.toString());
        System.out.println(sb_codes_defaultmoderator.toString());
       /* if(sb_codes.length()==0){
            Toast.makeText(getApplicationContext(),"Select minimum One Student",Toast.LENGTH_LONG).show();
        }else{
            //new SubmitMeetingAPI().execute();
           // new SubmitMeetingAPI().execute();
        }*/
        //System.out.println(ar.toString());
        //System.out.println(sb_codes.toString());
        // new SelectStream().execute(); send api calll
        // new SelectStudent().execute();
    }



    private void retrieveValuesFromListMethodModerator(Map map) {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        ArrayList<String> ar = new ArrayList<String>();
        sb_codes_moderator = new StringBuilder(map.size());
        select_student_count_moderator=map.size();
        String key;
        String value;
        while(itr.hasNext())
        {
            key = (String)itr.next();
            value = (String)map.get(key);
            System.out.println(key);
            ar.add(value);
            sb_codes_moderator.append(key+",");
        }
        System.out.println(ar.toString());
        System.out.println(sb_codes_moderator.toString());
       /* if(sb_codes.length()==0){
            Toast.makeText(getApplicationContext(),"Select minimum One Student",Toast.LENGTH_LONG).show();
        }else{
            //new SubmitMeetingAPI().execute();
           // new SubmitMeetingAPI().execute();
        }*/
        //System.out.println(ar.toString());
        //System.out.println(sb_codes.toString());
        // new SelectStream().execute(); send api calll
        // new SelectStudent().execute();
    }


    private void retrieveValuesFromListMethod(Map map) {
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
            System.out.println(key);
            ar.add(value);
            sb_codes.append(key+",");
        }
        System.out.println(ar.toString());
        System.out.println(sb_codes.toString());
       /* if(sb_codes.length()==0){
            Toast.makeText(getApplicationContext(),"Select minimum One Student",Toast.LENGTH_LONG).show();
        }else{
            //new SubmitMeetingAPI().execute();
           // new SubmitMeetingAPI().execute();
        }*/
        //System.out.println(ar.toString());
        //System.out.println(sb_codes.toString());
       // new SelectStream().execute(); send api calll
       // new SelectStudent().execute();
    }







    @Override
    public void onCheckClickListner(int position, SectionList_Checkbox_Helper value,boolean isChecked) {
     String   multipale_id =value.getSecID();
      String  multipale_name =value.getSecName();



        //check_box=  value.getcheck_status();
      //  Toast.makeText(getApplicationContext(),""+multipale_id,Toast.LENGTH_SHORT).show();

    /*  if(multipale_id == null || multipale_id.isEmpty()) {
           multipale_id = ((TextView) findViewById(R.id.text_sID_chk_sec)).getText().toString();

        }
        else {

            multipale_id    =multipale_id+","+((TextView) findViewById(R.id.text_sID_chk_sec)).getText().toString();
        }
        if (multipale_id != null && !multipale_id.isEmpty()) {
            count = multipale_id.split(",").length;
        }
*/




        if(isChecked==true){

            store_att_hashmap_section.put(multipale_name,multipale_id);
         //  Toast.makeText(getApplicationContext(), (multipale_id) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                store_att_hashmap_section.remove(multipale_name);
            }
            // Toast.makeText(getApplicationContext(), (multipale_name) + "---DELETE", Toast.LENGTH_LONG).show();
             //Toast.makeText(getApplicationContext(), store_att_hashmap.get(multipale_id) + "---DELETE", Toast.LENGTH_LONG).show();
        }

        if(isChecked==true){
            if(SectionIds == null || SectionIds.isEmpty()) {
                SectionIds = multipale_id;
            }
            else {
                SectionIds    =SectionIds+","+multipale_id;
            }
        }
        else {
            SectionIds=SectionIds.replace(multipale_id+",","");
            SectionIds=SectionIds.replace(","+multipale_id,"");
            SectionIds=SectionIds.replace(multipale_id,"");
        }


        if (SectionIds != null && !SectionIds.isEmpty()) {
            count = SectionIds.split(",").length;
        }
        else {
            count=0;
        }
        //  txt_count.setText(count);


        if(count>0){
            CustomListSectionchk.remove(0);
            //CustomListSectionchk = new ArrayList<SectionList_Checkbox_Helper>();
            String   SectionId="";
            String   SectionName=count+" Selection";
            SectionList_Checkbox_Helper sched = new SectionList_Checkbox_Helper();
            sched.setSecID(SectionId);
            sched.setSecName(SectionName);
            CustomListSectionchk.add(0,sched);
            adapter1.notifyDataSetChanged();
            //count++;

        }else {
            CustomListSectionchk.remove(0);
            String   SectionId="";
            String   SectionName="Select";
            SectionList_Checkbox_Helper sched = new SectionList_Checkbox_Helper();
            sched.setSecID(SectionId);
            sched.setSecName(SectionName);
            CustomListSectionchk.add(0,sched);
            adapter1.notifyDataSetChanged();
        }
    }



    @Override
    public void onCheckClickListner2(int position, BranchList_Checkbox_Helper value, boolean isChecked) {
        String   multipale_id =value.getBranch_ID();
        String  multipale_name =value.getBrach_Name();


        if(isChecked==true){

            store_att_hashmap_branch.put(multipale_name,multipale_id);
          //  Toast.makeText(getApplicationContext(), (multipale_id) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                store_att_hashmap_branch.remove(multipale_name);
            }
           // Toast.makeText(getApplicationContext(), (multipale_name) + "---DELETE", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), store_att_hashmap.get(multipale_id) + "---DELETE", Toast.LENGTH_LONG).show();
        }

        if(isChecked==true){
            if(BranchIds == null || BranchIds.isEmpty()) {
                BranchIds = multipale_id;
            }
            else {
                BranchIds    =BranchIds+","+multipale_id;
            }
        }
        else {
            BranchIds=BranchIds.replace(multipale_id+",","");
            BranchIds=BranchIds.replace(","+multipale_id,"");
            BranchIds=BranchIds.replace(multipale_id,"");
        }


        if (BranchIds != null && !BranchIds.isEmpty()) {
            count = BranchIds.split(",").length;
        }
        else {
            count=0;
        }
        //  txt_count.setText(count);


        if(count>0){



            CustomListBranch.remove(0);
            //CustomListBranch = new ArrayList<BranchList_Checkbox_Helper>();
            String   BranchId="";
            String   BranchName=count+" Selection";
            BranchList_Checkbox_Helper sched = new BranchList_Checkbox_Helper();
            sched.setBranch_ID(BranchId);
            sched.setBrach_Name(BranchName);
            CustomListBranch.add(0,sched);
            adapter2.notifyDataSetChanged();





        }else {
            CustomListBranch.remove(0);
            String   BranchId="";
            String   BranchName="Select";
            BranchList_Checkbox_Helper sched = new BranchList_Checkbox_Helper();
            sched.setBranch_ID(BranchId);
            sched.setBrach_Name(BranchName);
            CustomListBranch.add(0,sched);
            adapter2.notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckClickListner3(int position, StreamList_Checkbox_Helper value, boolean isChecked) {
        String   multipale_id =value.getStreamID();
        String  multipale_name =value.getStreamName();


        if(isChecked==true){

            store_att_hashmap_stream.put(multipale_name,multipale_id);
          //  Toast.makeText(getApplicationContext(), (multipale_id) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                store_att_hashmap_stream.remove(multipale_name);
            }
         //   Toast.makeText(getApplicationContext(), (multipale_name) + "---DELETE", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), store_att_hashmap.get(multipale_id) + "---DELETE", Toast.LENGTH_LONG).show();
        }

        if(isChecked==true){
            if(StreamIds == null || StreamIds.isEmpty()) {
                StreamIds = multipale_id;
            }
            else {
                StreamIds    =StreamIds+","+multipale_id;
            }
        }
        else {
            StreamIds=StreamIds.replace(multipale_id+",","");
            StreamIds=StreamIds.replace(","+multipale_id,"");
            StreamIds=StreamIds.replace(multipale_id,"");
        }


        if (StreamIds != null && !StreamIds.isEmpty()) {
            count = StreamIds.split(",").length;
        }
        else {
            count=0;
        }
        //  txt_count.setText(count);


        ////////

        if(count>0){



            CustomListStream.remove(0);
            //CustomListStream = new ArrayList<StreamList_Checkbox_Helper>();
            String   StreamId="";
            String   StreamName=count+" Selection";
            StreamList_Checkbox_Helper sched = new StreamList_Checkbox_Helper();
            sched.setStreamID(StreamId);
            sched.setStreamName(StreamName);
            CustomListStream.add(0,sched);
            StreamListadapter.notifyDataSetChanged();





        }else {
            CustomListStream.remove(0);
            String   StreamId="";
            String   StreamName="Select";
            StreamList_Checkbox_Helper sched = new StreamList_Checkbox_Helper();
            sched.setStreamID(StreamId);
            sched.setStreamName(StreamName);
            CustomListStream.add(0,sched);
            StreamListadapter.notifyDataSetChanged();
        }
    }

   /* @Override
    public void onCheckClickListner4(int position, Student_Checkbox_Helper value, boolean isChecked) {
        String   multipale_id =value.getStudentCode();
        String  multipale_name =value.getStudentName();
     Mobile_No=   value.getCorrespondanceMobileNo();
     Email_send=   value.getCorrespondanceEmail();
     check_data=            value.getcheck_status();






        if(isChecked==true){

            store_att_hashmap.put(multipale_name,multipale_id);
          //  Toast.makeText(getApplicationContext(), (multipale_id) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                store_att_hashmap.remove(multipale_name);
            }
          //  Toast.makeText(getApplicationContext(), (multipale_name) + "---DELETE", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), store_att_hashmap.get(multipale_id) + "---DELETE", Toast.LENGTH_LONG).show();
        }

        if(isChecked==true){
            if(StudentIds == null || StudentIds.isEmpty()) {
                StudentIds = multipale_id;
            }
            else {
                StudentIds    =StudentIds+","+multipale_id;
            }
        }
        else {
            StudentIds=StudentIds.replace(multipale_id+",","");
            StudentIds=StudentIds.replace(multipale_id,"");
        }


        if (StudentIds != null && !StudentIds.isEmpty()) {
            count = StudentIds.split(",").length;
        }
        else {
            count=0;
        }
        //  txt_count.setText(count);


        ////////

 *//*       if(count>0){



            CustomListStudent.remove(0);
            String   StudentCode="";
            String   StudentName=count+" Selection";
            Student_Checkbox_Helper sched = new Student_Checkbox_Helper();
            sched.setStudentCode(StudentCode);
            sched.setStudentName(StudentName);
            CustomListStudent.add(0,sched);
            //adapterstudent.notifyDataSetChanged();





        }else {
            CustomListStream.remove(0);
            String   StudentCode="";
            String   StudentName=count+" Selection";
            Student_Checkbox_Helper sched = new Student_Checkbox_Helper();
            sched.setStudentCode(StudentCode);
            sched.setStudentName(StudentName);
            CustomListStudent.add(0,sched);
            //adapterstudent.notifyDataSetChanged();

        }*//*
    }
*/
  /*  @Override
    public void onCheckClickListner5(int position, Moderator_Checkbox_Helper value, boolean isChecked) {
        String   multipale_id =value.getEmployeeCode();
        String  multipale_name =value.getEmployeeName();





        if(isChecked==true){

            store_att_hashmap.put(multipale_name,multipale_id);
       //     Toast.makeText(getApplicationContext(), (multipale_id) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                store_att_hashmap.remove(multipale_name);
            }
        //    Toast.makeText(getApplicationContext(), (multipale_name) + "---DELETE", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), store_att_hashmap.get(multipale_id) + "---DELETE", Toast.LENGTH_LONG).show();
        }

        if(isChecked==true){
            if(Moderatords == null || Moderatords.isEmpty()) {
                Moderatords = multipale_id;
            }
            else {
                Moderatords    =Moderatords+","+multipale_id;
            }
        }
        else {
            Moderatords=Moderatords.replace(multipale_id+",","");
            Moderatords=Moderatords.replace(multipale_id,"");
        }


        if (Moderatords != null && !Moderatords.isEmpty()) {
            count = Moderatords.split(",").length;
        }
        else {
            count=0;
        }
        //  txt_count.setText(count);


        ////////

        if(count>0){



            CustomListModerator.remove(0);
            //CustomListStudent = new ArrayList<Student_Checkbox_Helper>();
            String   EmployeeCode="";
            String   EmployeeName=count+" Selection";
            Moderator_Checkbox_Helper sched = new Moderator_Checkbox_Helper();
            sched.setEmployeeCode(EmployeeCode);
            sched.setEmployeeName(EmployeeName);
            CustomListModerator.add(0,sched);
            moderator_adapter.notifyDataSetChanged();





        }else {
            CustomListModerator.remove(0);
            //CustomListStudent = new ArrayList<Student_Checkbox_Helper>();
            String   EmployeeCode="";
            String   EmployeeName=count+" Selection";
            Moderator_Checkbox_Helper sched = new Moderator_Checkbox_Helper();
            sched.setEmployeeCode(EmployeeCode);
            sched.setEmployeeName(EmployeeName);
            CustomListModerator.add(0,sched);
            moderator_adapter.notifyDataSetChanged();

        }


    }*/

  /*  @Override
    public void onCheckClickListner6(int position, DefaultModerator_Checkbox_Helper value, boolean isChecked) {
        String   multipale_id =value.getEmployeeCode();
        String   multipale_name =value.getEmployeeName();
       // boolean  chktrue=value.getcheck_status();

      //  Toast.makeText(getApplicationContext(),chktrue+"Value",Toast.LENGTH_SHORT);



        if(isChecked==true){

            store_att_hashmap.put(multipale_name,multipale_id);
         //   Toast.makeText(getApplicationContext(), (multipale_id) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                store_att_hashmap.remove(multipale_name);
            }
         //   Toast.makeText(getApplicationContext(), (multipale_name) + "---DELETE", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), store_att_hashmap.get(multipale_id) + "---DELETE", Toast.LENGTH_LONG).show();
        }

        if(isChecked==true){
            if(DefaultModeratords == null || DefaultModeratords.isEmpty()) {
                DefaultModeratords = multipale_id;
            }
            else {
                DefaultModeratords    =DefaultModeratords+","+multipale_id;
            }
        }
        else {
            DefaultModeratords=DefaultModeratords.replace(multipale_id+",","");
            DefaultModeratords=DefaultModeratords.replace(multipale_id,"");
        }


        if (DefaultModeratords != null && !DefaultModeratords.isEmpty()) {
            count = DefaultModeratords.split(",").length;
        }
        else {
            count=0;
        }
        //  txt_count.setText(count);


        ////////

        if(count>0){



            CustomListDefaultModerator.remove(0);
            //CustomListStudent = new ArrayList<Student_Checkbox_Helper>();
            String   EmployeeCode="";
            String   EmployeeName=count+" Selection";
            DefaultModerator_Checkbox_Helper sched = new DefaultModerator_Checkbox_Helper();
            sched.setEmployeeCode(EmployeeCode);
            sched.setEmployeeName(EmployeeName);
            CustomListDefaultModerator.add(0,sched);
            defaultModerator_checkbox_adapter.notifyDataSetChanged();





        }else {
            CustomListDefaultModerator.remove(0);
            //CustomListStudent = new ArrayList<Student_Checkbox_Helper>();
            String   EmployeeCode="";
            String   EmployeeName=count+" Selection";
            DefaultModerator_Checkbox_Helper sched = new DefaultModerator_Checkbox_Helper();
            sched.setEmployeeCode(EmployeeCode);
            sched.setEmployeeName(EmployeeName);
            CustomListDefaultModerator.add(0,sched);
            defaultModerator_checkbox_adapter.notifyDataSetChanged();

        }
    }*/

    @Override
    public void onCheckClickListner(int position, Student_List_Helper value, boolean isChecked) {
        String student_code=value.getStudentCode();
        String student_name=value.getstudent_name();
       //  studentchk=value.getcheck_status();


       // Toast.makeText(getApplicationContext(), studentchk + "---ID", Toast.LENGTH_LONG).show();
       // Toast.makeText(getApplicationContext(), check_status + "---ID", Toast.LENGTH_LONG).show();


      /*  if(studentchk==true){


            store_att_hashmap.put(studentname,studentcode);
            Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---ID", Toast.LENGTH_LONG).show();

        }else {

            store_att_hashmap.remove(studentname);
            Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---DELETE", Toast.LENGTH_LONG).show();

        }
*/


        if(isChecked==true){
            store_att_hashmap.put(student_code,student_name);
           // Toast.makeText(getApplicationContext(), store_att_hashmap.get(student_code) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            store_att_hashmap.remove(student_code);
           // Toast.makeText(getApplicationContext(), store_att_hashmap.get(student_code) + "---DELETE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCheckClickListner_2(int position, Student_List_Helper value, boolean isChecked) {
        String studentcode2=value.getStudentCode();
        String studentname2=value.getstudent_name();
       // studentchk=value.getcheck_status();


        // Toast.makeText(getApplicationContext(), studentchk + "---ID", Toast.LENGTH_LONG).show();


      /*  if(studentchk==true){


            store_att_hashmap.put(studentname,studentcode);
            Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---ID", Toast.LENGTH_LONG).show();

        }else {

            store_att_hashmap.remove(studentname);
            Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---DELETE", Toast.LENGTH_LONG).show();

        }
*/


        if(isChecked==true){
            store_att_hashmap.put(studentcode2,studentname2);
          //  Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            store_att_hashmap.remove(studentcode2);
           // Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---DELETE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void CheckcustomButtonListener_default_moderator(int position, Default_Moderator_List_Helper value, boolean isChecked) {
        String emp_code=value.getEmployeeCode();
        String emp_name=value.getEmployeeName();



        if(isChecked==true){
            store_att_hashmap_defaultmoderator.put(emp_code,emp_name);
            // Toast.makeText(getApplicationContext(), store_att_hashmap.get(student_code) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            store_att_hashmap_defaultmoderator.remove(emp_code);
            // Toast.makeText(getApplicationContext(), store_att_hashmap.get(student_code) + "---DELETE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCheckcustomButtonListener_default_moderator_new(int position, Default_Moderator_List_Helper value, boolean isChecked) {
        String emp_code2=value.getEmployeeCode();
        String emp_name2=value.getEmployeeName();
        // studentchk=value.getcheck_status();




        if(isChecked==true){
            store_att_hashmap_defaultmoderator.put(emp_code2,emp_name2);
            //  Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            store_att_hashmap_defaultmoderator.remove(emp_code2);
            // Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---DELETE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void CheckcustomButtonListener_moderator(int position, Moderator_List_Helper value, boolean isChecked) {
        String emp_code=value.getEmployeeCode();
        String emp_name=value.getEmployeeName();



        if(isChecked==true){
            store_att_hashmap_moderator.put(emp_code,emp_name);
            // Toast.makeText(getApplicationContext(), store_att_hashmap.get(student_code) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            store_att_hashmap_moderator.remove(emp_code);
            // Toast.makeText(getApplicationContext(), store_att_hashmap.get(student_code) + "---DELETE", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCheckcustomButtonListener_moderator_new(int position, Moderator_List_Helper value, boolean isChecked) {
        String emp_code2=value.getEmployeeCode();
        String emp_name2=value.getEmployeeName();
        // studentchk=value.getcheck_status();




        if(isChecked==true){
            store_att_hashmap_moderator.put(emp_code2,emp_name2);
            //  Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---ID", Toast.LENGTH_LONG).show();
        }else{
            store_att_hashmap_moderator.remove(emp_code2);
            // Toast.makeText(getApplicationContext(), store_att_hashmap.get(studentname) + "---DELETE", Toast.LENGTH_LONG).show();
        }
    }


    ////////////////////////////////////////CLASSES//////////////////////////////////////////////////////////
    private class SelectclassDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Virtual_Class.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.ATTENDANCE_SELECT,Paradetails(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            String s=applicationController.getServicesapplication()+ServerApiadmin.ATTENDANCE_SELECT;
            if(response!=null){
                if (response.length()>5){
                    try {

                        JSONArray array= new JSONArray(response);
                      //  sb_codes = new StringBuilder(array.length());
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    adapter = new ClassList_Adapter(getApplicationContext(), R.layout.spinner_class_item, CustomListViewValuesArr,res);
                    select_class.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_live_class, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_live_class, "Data not Found. Please Try Again", Snackbar.LENGTH_LONG)
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
            jsonParam.put("ClassId", "-1");
            jsonParam.put("CourseId", "-1");
            //jsonParam.put("EmployeeCode", applicationController.getActiveempcode());
             jsonParam.put("Action","4");
             jsonParam.put("FYId",FYId);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    ///////////////////////////////////////////Section Selection//////////////////////////////////
    private class SelectSection extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.ATTENDANCE_SECTION,ParaSec(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
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
                    adapter1.setCheckcustomButtonListener(Virtual_Class.this);
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
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
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
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

////////////////////////select branch////////////////////////
    private class SelectBranch extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SELECTBRANCH_NEW,ParaBranch(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        store_att_hashmap_branch.clear();
                        JSONObject obj=new JSONObject(response);
                        JSONArray array= new JSONArray(obj.getString("Table"));
                       // sb_codes_branch = new StringBuilder(array.length());
                        CustomListBranch = new ArrayList<BranchList_Checkbox_Helper>();

                        String   BranchId="";
                        String   BranchName="Select";
                        BranchList_Checkbox_Helper sched = new BranchList_Checkbox_Helper();
                        sched.setBranch_ID(BranchId);
                        sched.setBrach_Name(BranchName);
                        CustomListBranch.add(sched);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            BranchId=object.getString("BranchId");
                            BranchName=object.getString("BranchName");
                            String   IsActive=object.getString("IsActive");
                            String   chk=object.getString("chk");
                            sched = new BranchList_Checkbox_Helper();
                            sched.setBranch_ID(BranchId);
                            sched.setBrach_Name(BranchName);
                            sched.setIsActive(IsActive);
                            sched.setchk(chk);
                            sched.setcheck_status(check_status);
                            CustomListBranch.add(sched);
                            if(check_status==true){
                                store_att_hashmap_branch.put(BranchId,BranchName);
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
                     adapter2 = new BranchList_Checkbox_Adapter(getApplicationContext(), R.layout.spinner_branch_item_chekbox, CustomListBranch,res);
                    select_branch.setAdapter(adapter2);
                    adapter2.setCheckcustomButtonListener2(Virtual_Class.this);

                    break;
                case -2:
                    if(adapter2!= null){
                        select_branch.setAdapter(null);
                    }
                    Snackbar snackbar = Snackbar
                            .make(layout_live_class, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                    if(adapter2!= null){
                        select_branch.setAdapter(null);
                    }
                    Snackbar snackbar1 = Snackbar
                            .make(layout_live_class, "Branch Data Not Found", Snackbar.LENGTH_LONG)
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
            jsonParam.put("EmployeeCode", applicationController.getActiveempcode());
            jsonParam.put("Action","9");
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("FYId",applicationController.getFyID());
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    //////////////////select stream ///////////////////////

    /////////////////////////select stream ////////////
    private class SelectStream extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Result_Submit_Classwise.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SELECTBRANCH_NEW,Parastream(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        store_att_hashmap_stream.clear();
                        JSONObject obj=new JSONObject(response);
                        JSONArray array= new JSONArray(obj.getString("Table1"));
                       // sb_codes = new StringBuilder(array.length());
                        CustomListStream = new ArrayList<StreamList_Checkbox_Helper>();

                        String   StreamId="";
                        String   StreamName="Select";
                        StreamList_Checkbox_Helper sched = new StreamList_Checkbox_Helper();
                        sched.setStreamID(StreamId);
                        sched.setStreamName(StreamName);
                        CustomListStream.add(sched);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            StreamId=object.getString("StreamId");
                            StreamName=object.getString("StreamName");
                            String   IsActive=object.getString("IsActive");
                            String   chk=object.getString("chk");
                            sched = new StreamList_Checkbox_Helper();
                            sched.setStreamID(StreamId);
                            sched.setStreamName(StreamName);
                            sched.setIsActive(IsActive);
                            sched.setchk(chk);
                            sched.setcheck_status(check_status);
                            CustomListStream.add(sched);
                            if(check_status==true){
                                store_att_hashmap_stream.put(StreamId,StreamName);
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
                    StreamListadapter = new StreamList_Checkbox_Adapter(getApplicationContext(), R.layout.spinner_stream_item_chekbox, CustomListStream,res);
                    select_stream.setAdapter(StreamListadapter);
                    StreamListadapter.setCheckcustomButtonListener3(Virtual_Class.this);

                    break;
                case -2:
                    if(StreamListadapter!= null){
                        select_stream.setAdapter(null);
                    }
                    Snackbar snackbar = Snackbar
                            .make(layout_live_class, "Stream not Found", Snackbar.LENGTH_LONG)
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
                        select_stream.setAdapter(null);
                    }
                    Snackbar snackbar1 = Snackbar
                            .make(layout_live_class, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Parastream(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("EmployeeCode", applicationController.getActiveempcode());
            jsonParam.put("Action","9");
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("FYId",applicationController.getFyID());
            //jsonParam.put("test", sb_codes);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }





 /*   ///////////////////////////////////////////Section Student//////////////////////////////////
    private class SelectStudent extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
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
                        CustomListStudent = new ArrayList<Student_Checkbox_Helper>();


                        String   StudentCode="";
                        String   StudentName="Select";
                        Student_Checkbox_Helper sched = new Student_Checkbox_Helper();
                        sched.setStudentCode(StudentCode);
                        sched.setStudentName(StudentName);
                        sched.setCorrespondanceEmail("CorrespondanceEmail");
                        sched.setCorrespondanceMobileNo("CorrespondanceMobileNo");
                        CustomListStudent.add(sched);


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                               StudentCode=object.getString("StudentCode");
                               StudentName=object.getString("StudentName");
                       String     CorrespondanceEmail =object.getString("CorrespondanceEmail");
                       String     CorrespondanceMobileNo =object.getString("CorrespondanceMobileNo");
                             sched = new Student_Checkbox_Helper();
                            sched.setStudentCode(StudentCode);
                            sched.setStudentName(StudentName);
                            sched.setCorrespondanceEmail(CorrespondanceEmail);
                            sched.setCorrespondanceMobileNo(CorrespondanceMobileNo);
                            sched.setcheck_status(check_status);
                            CustomListStudent.add(sched);
                            if(check_status==true){
                                store_att_hashmap.put(StudentCode,StudentName);
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
                    adapterstudent = new Student_Checkbox_Adapter(getApplicationContext(), R.layout.spinner_student_item_chekbox, CustomListStudent,res);
                    select_student.setAdapter(adapterstudent);
                    adapterstudent.setCheckcustomButtonListener4(Virtual_Class.this);

                    break;
                case -2:
                    if(adapterstudent!= null){
                        select_student.setAdapter(null);
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
                    if(adapterstudent!= null){
                        select_student.setAdapter(null);
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
    public String ParaSutdent(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "10");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            //jsonParam.put("FYId",FYId);
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("SectionId",SectionIds);
            jsonParam.put("StreamId",StreamIds);
            jsonParam.put("BranchId",BranchIds);
            // jsonParam.put("Action","7");  SectionIds,BranchIds,StreamIds,
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }*/


    ////////////////////////////////////////CLASSES//////////////////////////////////////////////////////////
    private class SelectEMPPresenter extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Virtual_Class.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.EMPLOYEE_API,ParaEMP(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            String s=applicationController.getServicesapplication()+ServerApiadmin.EMPLOYEE_API;
            if(response!=null){
                if (response.length()>5){
                    try {
                        store_att_hashmap.clear();
                        JSONArray array= new JSONArray(response);
                       // sb_codes = new StringBuilder(array.length());
                        CustomListEMP_Presenter = new ArrayList<EMP_Presenter_Helper>();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   EmployeeCode=object.getString("EmployeeCode");
                            String   FullName=object.getString("FullName");
                            final EMP_Presenter_Helper sched = new EMP_Presenter_Helper();
                            sched.setEmployeeCode(EmployeeCode);
                            sched.setFullName(FullName);
                            CustomListEMP_Presenter.add(sched);
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
                    EMP_Presener_adapter = new EMP_Presenter_Adapter(getApplicationContext(), R.layout.spinner_emp_presenter_item, CustomListEMP_Presenter,res);
                    select_presenter.setAdapter(EMP_Presener_adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_live_class, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_live_class, "Data not Found. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String ParaEMP(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("Action", "4");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }




    ///////////////////////////////////////////select moderator//////////////////////////////////
   /* private class SelectModerator extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVE_MODERATOR_LIST,Paramoderator(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        store_att_hashmap.clear();
                        JSONArray array= new JSONArray(response);
                       // sb_codes = new StringBuilder(array.length());
                        CustomListModerator = new ArrayList<Moderator_Checkbox_Helper>();


                        String   EmployeeCode="";
                        String   EmployeeName="Select";
                        Moderator_Checkbox_Helper sched = new Moderator_Checkbox_Helper();
                        sched.setEmployeeCode(EmployeeCode);
                        sched.setEmployeeName(EmployeeName);
                        CustomListModerator.add(sched);


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            EmployeeCode=object.getString("EmployeeCode");
                            EmployeeName=object.getString("EmployeeName");
                            sched = new Moderator_Checkbox_Helper();
                            sched.setEmployeeCode(EmployeeCode);
                            sched.setEmployeeName(EmployeeName);
                            sched.setcheck_status(check_status);
                            CustomListModerator.add(sched);
                            if(check_status==true){
                                store_att_hashmap.put(EmployeeCode,EmployeeName);
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
                    moderator_adapter = new Moderator_Checkbox_Adapter(getApplicationContext(), R.layout.spinner_moderator_item_chekbox, CustomListModerator,res);
                    select_moderator.setAdapter(moderator_adapter);
                  //  moderator_adapter.setCheckcustomButtonListener5(Virtual_Class.this);
                    break;
                case -2:
                    if(moderator_adapter!= null){
                        select_moderator.setAdapter(null);
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
                    if(moderator_adapter!= null){
                        select_moderator.setAdapter(null);
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
    public String Paramoderator(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "2");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            // jsonParam.put("Action","7");  SectionIds,BranchIds,StreamIds,
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }*/







    ///////////////////////////////////////////select defalutmoderator//////////////////////////////////
   /* private class SelectDefaultModerator extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVE_MODERATOR_LIST,Paradefaultmoderator(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        store_att_hashmap.clear();
                        JSONArray array= new JSONArray(response);
                       // sb_codes = new StringBuilder(array.length());
                        CustomListDefaultModerator = new ArrayList<DefaultModerator_Checkbox_Helper>();



                        String   EmployeeCode="";
                        String   EmployeeName="Select";
                        DefaultModerator_Checkbox_Helper sched = new DefaultModerator_Checkbox_Helper();
                        sched.setEmployeeCode(EmployeeCode);
                        sched.setEmployeeName(EmployeeName);
                        CustomListDefaultModerator.add(sched);


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            EmployeeCode=object.getString("EmployeeCode");
                            EmployeeName=object.getString("EmployeeName");
                            sched = new DefaultModerator_Checkbox_Helper();
                            sched.setEmployeeCode(EmployeeCode);
                            sched.setEmployeeName(EmployeeName);
                            sched.setcheck_status(check_status);
                            CustomListDefaultModerator.add(sched);
                            if(check_status==true){
                                store_att_hashmap.put(EmployeeCode,EmployeeName);
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
                     defaultModerator_checkbox_adapter = new DefaultModerator_Checkbox_Adapter(getApplicationContext(), R.layout.spinner_default_moderator_item_chekbox, CustomListDefaultModerator);
                    select_default_moderator.setAdapter(defaultModerator_checkbox_adapter);
                    //defaultModerator_checkbox_adapter.setCheckcustomButtonListener6(Virtual_Class.this);
                    break;
                case -2:
                    if(defaultModerator_checkbox_adapter!= null){
                        select_default_moderator.setAdapter(null);
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
                    if(defaultModerator_checkbox_adapter!= null){
                        select_default_moderator.setAdapter(null);
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
    public String Paradefaultmoderator(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "1");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            // jsonParam.put("Action","7");  SectionIds,BranchIds,StreamIds,
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
*/
    ///////////////////////////////////////create meeting sumited api////////////////////////////////


    //////////////////////////////////////API SUBJECT SUBMIT////////////////////////
    private class SubmitMeetingAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
             progressDialog = ProgressDialog.show(Virtual_Class.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
              response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVE_Class_Submit,ParaSubmit(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            //Log.d("request!", response);
             String api = applicationController.getServicesapplication();
            if(response!=null){
                response=response.replace("\"", "");
                response=response.replace("\r", "");
                if(response.equals("1")) {
                    status = 1;
                }else  if(response.equals("0")) {
                    status=2;
                } else{
                    status=3;
                }

            }else{
                status=2;
            }
            return status;
        }
        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
              progressDialog.dismiss();
            switch (s){
                case 1:
                    Toast.makeText(getApplicationContext(), "Live Class Created Sucessfully", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "Live Class Not Created", Toast.LENGTH_LONG).show();

                    break;

                case 3:
                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_LONG).show();


                    break;


            }
        }
    }

    public String ParaSubmit(String schoolCode, String branchCode, String SessionId, String fyId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();

        //JSONObject attachmentList = new JSONObject();


        try {

            jsonParam.put("Action", "1");
            jsonParam.put("SchoolCode", schoolCode);
            jsonParam.put("BranchCode", branchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",fyId);
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("SectionList",SectionIds);
            jsonParam.put("BranchList",BranchIds);
            jsonParam.put("StreamList",StreamIds);
            jsonParam.put("CreatedBy",applicationController.getUserID());
            jsonParam.put("Description",txt_description.getText().toString());
            jsonParam.put("Duration",Duration);
            jsonParam.put("IsActive",value_store);
            jsonParam.put("MeetingName",Subject_name);
            jsonParam.put("Message",txt_message.getText().toString());
           // jsonParam.put("StudentList",StudentIds);
            jsonParam.put("StudentList",sb_codes);
            jsonParam.put("PresenterCode",EMPPresenter);
            jsonParam.put("ModeratorList",sb_codes_moderator);
            jsonParam.put("DefaultModeratorList",sb_codes_defaultmoderator);
            jsonParam.put("SendEmail",smssnd_mail);
            jsonParam.put("SendSMS",smssnd_sms);
            jsonParam.put("StartDate",show_date_homework);
            jsonParam.put("StartTime",time_set);
            jsonParam.put("StdContactList",Mobile_No);
            jsonParam.put("StdEmailList",Email_send);
            jsonParam.put("Topic",Topic_name);
            jsonParam.put("UpdatedBy",applicationController.getUserID());
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

//////////////////dialog student list //////
    private void showSuccessDialog() {
      store_att_hashmap.clear();


        final Dialog dialog = new Dialog(Virtual_Class.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.activity_student__list);
        dialog.setTitle("");
        student_list_list=(ListView) dialog.findViewById(R.id.student_list_list);
        layout_select_all=(RelativeLayout)dialog.findViewById(R.id.layout_select_all_student);
        check_select_all=(CheckBox)dialog.findViewById(R.id.check_select_All);
        student_search=(EditText)dialog.findViewById(R.id.student_search_live);
        layout_select_all.setVisibility(View.GONE);
        new StudentList().execute();


        student_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(student_list_adapter != null){
                    student_list_adapter.getFilter().filter(s);
                }


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(student_list_adapter != null){
                    String text = student_search.getText().toString().toLowerCase(Locale.getDefault());
                    student_list_adapter.filter(text);
                }

            }
        });



        if(check_status){
            check_select_all.setChecked(true);
        }



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

        // set the custom dialog components - text, image and button
       // TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
       // TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
       // TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
       // text_message.setText("");
       // text_toplabel.setTypeface(typeface);
        Button dialogButton = (Button) dialog.findViewById(R.id.button_finish);
       // text_toplabel.setTypeface(typeface);
       // text_label.setTypeface(typeface);
       // text_message.setTypeface(typeface);
        dialogButton.setTypeface(typeface);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               retrieveValuesFromListMethod(store_att_hashmap);
               // Toast.makeText(getApplicationContext(),"sb_codes"+sb_codes,Toast.LENGTH_SHORT).show();                if(sb_codes != null)

              // int stdcount=sb_codes.toString().split(",").length;
                int stdcount=store_att_hashmap.toString().split(",").length;

                txt_count_st_list.setText("Total Students "+stdcount);

                    if(store_att_hashmap.size()==0){
                        Toast.makeText(getApplicationContext(),"Select minimum One Student",Toast.LENGTH_LONG).show();
                    }else {
                      //  Toast.makeText(getApplicationContext(),"store_att_hashmap"+store_att_hashmap,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }


            }
        });


        dialog.show();
    }



    ///////////////////////////ClassStudent_List////////////////////////////////////////////
    private class StudentList extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Virtual_Class.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());

            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVE_STUDENT_LIST,ParaSutdentcode(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                      store_att_hashmap.clear();

                        JSONArray array= new JSONArray(response);
                        sb_codes = new StringBuilder(array.length());
                        student_list_helpers = new ArrayList<Student_List_Helper>();


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);


                            String   StudentName=object.getString("StudentName");
                            String   StudentCode=object.getString("StudentCode");
                            String     CorrespondanceEmail =object.getString("CorrespondanceEmail");
                            String     CorrespondanceMobileNo =object.getString("CorrespondanceMobileNo");


                            Student_List_Helper item = new Student_List_Helper(StudentName,StudentCode,CorrespondanceEmail,CorrespondanceMobileNo,i + 1 + " .",check_status);
                            student_list_helpers.add(item);
                            if(check_status==true){
                                store_att_hashmap.put(StudentCode,StudentName);
                            }
                            status=1;
                        }

                        //if(sb_codes != null)
                            //std_list=sb_codes.toString();


                        // Intent in=getIntent();
                        //  std_list=in.getStringExtra("std_list");



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
                    //student_list_list=(ListView)findViewById(R.id.student_list_list);
                    student_list_adapter = new Student_List_Adapter(Virtual_Class.this, R.layout.item_student_list, student_list_helpers);
                    student_list_list.setAdapter(student_list_adapter);
                    student_list_adapter.setCheckcustomButtonListener(Virtual_Class.this);
                    student_list_adapter.setCheckcustomButtonListener_2(Virtual_Class.this);
                    animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    student_list_list.setVisibility(View.VISIBLE);
                    // complaint_view_list.startAnimation(animationDown);
                    layout_select_all.setVisibility(View.VISIBLE);
                   // chat_layout.setVisibility(View.VISIBLE);


                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_live_class, "Student List not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                         Snackbar snackbar1 = Snackbar.make(layout_live_class, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    student_list_list.setVisibility(View.GONE);
                    layout_select_all.setVisibility(View.GONE);
                   // chat_layout.setVisibility(View.GONE);

                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_live_class, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbar1 = Snackbar.make(layout_live_class, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    student_list_list.setVisibility(View.GONE);
                    layout_select_all.setVisibility(View.GONE);
                   // chat_layout.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String ParaSutdentcode(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "10");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            //jsonParam.put("FYId",FYId);
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("SectionId",SectionIds);
            jsonParam.put("StreamId",StreamIds);
            jsonParam.put("BranchId",BranchIds);
            // jsonParam.put("Action","7");  SectionIds,BranchIds,StreamIds,
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }




    /////// dialog default moderator //////

    private void showSuccessDialogdefaultmoderator() {
        store_att_hashmap_defaultmoderator.clear();


        final Dialog dialog = new Dialog(Virtual_Class.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.activity_default_moderator_list);
        dialog.setTitle("");
        default_moderator_list=(ListView) dialog.findViewById(R.id.default_moderator_list);
        layout_select_all_defaultmoderator=(RelativeLayout)dialog.findViewById(R.id.layout_select_all_defaultmoderator);
        check_select_All_default_moderator=(CheckBox)dialog.findViewById(R.id.check_select_All_default_moderator);
        default_moderator_search_live=(EditText)dialog.findViewById(R.id.default_moderator_search_live);
        layout_select_all_defaultmoderator.setVisibility(View.GONE);

        new APIDedaultModerator().execute();


        default_moderator_search_live.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(default_moderator_list_adapter != null){
                    default_moderator_list_adapter.getFilter().filter(s);
                }


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(default_moderator_list_adapter != null){
                    String text = default_moderator_search_live.getText().toString().toLowerCase(Locale.getDefault());
                    default_moderator_list_adapter.filter(text);
                }

            }
        });



        if(check_status_defaultmoderator){
            check_select_All_default_moderator.setChecked(true);
        }



        check_select_All_default_moderator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true){
                    check_status_defaultmoderator=true;
                    new APIDedaultModerator().execute();

                }else{
                    check_status_defaultmoderator=false;
                    store_att_hashmap_defaultmoderator.clear();
                    new APIDedaultModerator().execute();
                }




            }
        });

        // set the custom dialog components - text, image and button
        // TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        // TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        // TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
        // text_message.setText("");
        // text_toplabel.setTypeface(typeface);
        Button dialogButton = (Button) dialog.findViewById(R.id.button_finish_defaltmoderator);
        // text_toplabel.setTypeface(typeface);
        // text_label.setTypeface(typeface);
        // text_message.setTypeface(typeface);
        dialogButton.setTypeface(typeface);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                retrieveValuesFromListMethodDefaultModerator(store_att_hashmap_defaultmoderator);
                // Toast.makeText(getApplicationContext(),"sb_codes"+sb_codes,Toast.LENGTH_SHORT).show();                if(sb_codes != null)

                // int stdcount=sb_codes.toString().split(",").length;
                int stdcount=store_att_hashmap_defaultmoderator.toString().split(",").length;

                txt_count_default_modrator.setText("Total  "+stdcount);

                if(store_att_hashmap_defaultmoderator.size()==0){
                    Toast.makeText(getApplicationContext(),"Select minimum One Default Moderator",Toast.LENGTH_LONG).show();
                }else {
                    //  Toast.makeText(getApplicationContext(),"store_att_hashmap"+store_att_hashmap,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


            }
        });


        dialog.show();
    }
    //////////////////////default moderator/////////////////////////

    private class APIDedaultModerator extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Virtual_Class.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());

            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVE_MODERATOR_LIST,Paradefault_moderator(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        store_att_hashmap_defaultmoderator.clear();

                        JSONArray array= new JSONArray(response);
                        sb_codes_defaultmoderator = new StringBuilder(array.length());
                        default_moderator_list_helpers = new ArrayList<Default_Moderator_List_Helper>();


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);


                         String   EmployeeCode=object.getString("EmployeeCode");
                         String   EmployeeName=object.getString("EmployeeName");



                            Default_Moderator_List_Helper item = new Default_Moderator_List_Helper(EmployeeName,EmployeeCode,i + 1 + " .",check_status_defaultmoderator);
                            default_moderator_list_helpers.add(item);
                            if(check_status_defaultmoderator==true){
                                store_att_hashmap_defaultmoderator.put(EmployeeCode,EmployeeName);
                            }
                            status=1;
                        }

                        //if(sb_codes != null)
                        //std_list=sb_codes.toString();


                        // Intent in=getIntent();
                        //  std_list=in.getStringExtra("std_list");



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
                    //student_list_list=(ListView)findViewById(R.id.student_list_list);
                    default_moderator_list_adapter = new Default_Moderator_List_Adapter(Virtual_Class.this, R.layout.item_defaultmoderator_list, default_moderator_list_helpers);
                    default_moderator_list.setAdapter(default_moderator_list_adapter);
                    default_moderator_list_adapter.setCheckcustomButtonListener_default_moderator(Virtual_Class.this);
                    default_moderator_list_adapter.setCheckcustomButtonListener_default_moderator_new(Virtual_Class.this);
                    animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    default_moderator_list.setVisibility(View.VISIBLE);
                    // complaint_view_list.startAnimation(animationDown);
                    layout_select_all_defaultmoderator.setVisibility(View.VISIBLE);
                    // chat_layout.setVisibility(View.VISIBLE);


                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_live_class, "Default Moderator List not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbar1 = Snackbar.make(layout_live_class, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    default_moderator_list.setVisibility(View.GONE);
                    layout_select_all_defaultmoderator.setVisibility(View.GONE);
                    // chat_layout.setVisibility(View.GONE);

                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_live_class, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbar1 = Snackbar.make(layout_live_class, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    default_moderator_list.setVisibility(View.GONE);
                    layout_select_all_defaultmoderator.setVisibility(View.GONE);
                    // chat_layout.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String Paradefault_moderator(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "1");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            // jsonParam.put("Action","7");  SectionIds,BranchIds,StreamIds,
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


///////////////////////////////////////dialog moderator//////////////

    private void showSuccessDialogModerator() {
        store_att_hashmap_defaultmoderator.clear();


        final Dialog dialog = new Dialog(Virtual_Class.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.activity_moderator_list);
        dialog.setTitle("");
        moderator_list=(ListView) dialog.findViewById(R.id.moderator_list);
        layout_select_all_moderator=(RelativeLayout)dialog.findViewById(R.id.layout_select_all_moderator);
        check_select_All_moderator=(CheckBox)dialog.findViewById(R.id.check_select_All_moderator);
        moderator_search_live=(EditText)dialog.findViewById(R.id.moderator_search_live);
        layout_select_all_moderator.setVisibility(View.GONE);

        new APIModerator().execute();


        moderator_search_live.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(moderator_list_adapter != null){
                    moderator_list_adapter.getFilter().filter(s);
                }


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(moderator_list_adapter != null){
                    String text = moderator_search_live.getText().toString().toLowerCase(Locale.getDefault());
                    moderator_list_adapter.filter(text);
                }

            }
        });



        if(check_status_moderator){
            check_select_All_moderator.setChecked(true);
        }



        check_select_All_moderator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true){
                    check_status_moderator=true;
                    new APIModerator().execute();

                }else{
                    check_status_moderator=false;
                    store_att_hashmap_moderator.clear();
                    new APIModerator().execute();
                }




            }
        });

        // set the custom dialog components - text, image and button
        // TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        // TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        // TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
        // text_message.setText("");
        // text_toplabel.setTypeface(typeface);
        Button dialogButton = (Button) dialog.findViewById(R.id.button_finish_moderator);
        // text_toplabel.setTypeface(typeface);
        // text_label.setTypeface(typeface);
        // text_message.setTypeface(typeface);
        dialogButton.setTypeface(typeface);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                retrieveValuesFromListMethodModerator(store_att_hashmap_moderator);
                // Toast.makeText(getApplicationContext(),"sb_codes"+sb_codes,Toast.LENGTH_SHORT).show();                if(sb_codes != null)

                // int stdcount=sb_codes.toString().split(",").length;
                int stdcount=store_att_hashmap_moderator.toString().split(",").length;

                txt_count_modrator.setText("Total  "+stdcount);

                if(store_att_hashmap_moderator.size()==0){
                    Toast.makeText(getApplicationContext(),"Select minimum One Moderator",Toast.LENGTH_LONG).show();
                }else {
                    //  Toast.makeText(getApplicationContext(),"store_att_hashmap"+store_att_hashmap,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


            }
        });


        dialog.show();
    }



    ///////////////////////////Moderator api //////////////////////


    private class APIModerator extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Virtual_Class.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Virtual_Class.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());

            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LIVE_MODERATOR_LIST,moderator(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        store_att_hashmap_moderator.clear();

                        JSONArray array= new JSONArray(response);
                        sb_codes_moderator = new StringBuilder(array.length());
                        moderator_list_helpers = new ArrayList<Moderator_List_Helper>();


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);


                            String   EmployeeCode=object.getString("EmployeeCode");
                            String   EmployeeName=object.getString("EmployeeName");



                            Moderator_List_Helper item = new Moderator_List_Helper(EmployeeName,EmployeeCode,i + 1 + " .",check_status_moderator);
                            moderator_list_helpers.add(item);
                            if(check_status_moderator==true){
                                store_att_hashmap_moderator.put(EmployeeCode,EmployeeName);
                            }
                            status=1;
                        }

                        //if(sb_codes != null)
                        //std_list=sb_codes.toString();


                        // Intent in=getIntent();
                        //  std_list=in.getStringExtra("std_list");



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
                    //student_list_list=(ListView)findViewById(R.id.student_list_list);
                    moderator_list_adapter = new Moderator_List_Adapter(Virtual_Class.this, R.layout.item_moderator_list, moderator_list_helpers);
                    moderator_list.setAdapter(moderator_list_adapter);
                    moderator_list_adapter.setCheckcustomButtonListener_moderator(Virtual_Class.this);
                    moderator_list_adapter.setCheckcustomButtonListener_moderator_new(Virtual_Class.this);
                    animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    moderator_list.setVisibility(View.VISIBLE);
                    // complaint_view_list.startAnimation(animationDown);
                    layout_select_all_moderator.setVisibility(View.VISIBLE);
                    // chat_layout.setVisibility(View.VISIBLE);


                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_live_class, "Default Moderator List not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbar1 = Snackbar.make(layout_live_class, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    moderator_list.setVisibility(View.GONE);
                    layout_select_all_moderator.setVisibility(View.GONE);
                    // chat_layout.setVisibility(View.GONE);

                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_live_class, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbar1 = Snackbar.make(layout_live_class, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    moderator_list.setVisibility(View.GONE);
                    layout_select_all_moderator.setVisibility(View.GONE);
                    // chat_layout.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String moderator(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "2");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            // jsonParam.put("Action","7");  SectionIds,BranchIds,StreamIds,
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

}
