package vidyawell.infotech.bsn.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.DecoDrawEffect;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import vidyawell.infotech.bsn.admin.Adapters.Gallery_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Gallery_Vedios_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Gallery_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Gallery_Vedios_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.CustomTypefaceSpan;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class MainActivity_Admin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    GridView gallery_list;
    List<Gallery_Helper> gallery_helpers;
    Gallery_Helper item;

    GridView gallery_list_vedio;
    List<Gallery_Vedios_Helper> gallery_vedios_helpers;
    Gallery_Vedios_Helper item1;

    CircleImageView profile_image;
    String staff_count,staff_not_taken,student_count,student_not_taken, AdminName,Designation,PhotoPath,Address,ContactNo,Email,Headline_notice,Headline_notice2;
    private DecoView mDecoView,mDecoView2,leave_dynamicArcView;
    private final float mSeriesMax = 100f;
    private int mBackIndex;
    LinearLayout student_msg_menu,emp_msg_menu,dashboard_animlayout;
    TextView tv_date,tv_month,textnoticeboard_firstline,textnoticeboard_secondline;
    Typeface typeFace;
    float stu_att_percent,staff_att_percent;
    DatePickerDialog datePickerDialog;
    private Animation animationDown;
    TextView stuatt_total,stu_present,stu_absent,stu_onleave,staff_total,staff_not_taken_layout,student_not_taken_layout,staff_present,staff_absent,staff_onleave,top_username,top_userdesgn;
    LinearLayout admindashboard_layout,notice_line_second;
    String AttendanceCount_absent,AttendanceCount_present,AttendanceCount_leave,AttendanceCount_absentemp,AttendanceCount_presentemp,AttendanceCount_leaveemp;
    ImageView imageView_nav_logo;
    SharedPreferences  sharedpreferences ;
    String Message,Headline_notice2ID,Headline_noticeID="0";
    ScrollView mainScrollView;
    ScheduledExecutorService scheduler;
    ApplicationControllerAdmin applicationControllerAdmin;
    LinearLayout layout_staff_attend,layout_monthly_attend,layout_month_att_status,layout_staff_attend_status,layout_stu_attendance,linear_e_class,linear_live_video;
    String emp_id,date_current;
    TextView staff_total_attendance,staff_present_attendance,staff_absent_attendance,staff_onleave_attendance;
    TextView alloted_leave,leave_taken,leave_balance;
    LinearLayout calendar_disclose_invisible;
    String AllotedLeaves,LeaveTaken,Balance,student_action;
    double leavetaken,balance,allotedleave4;
    String alloted ="0",leave_taken_add="0",balance_add="0";
    double allotedleave;
    TextView btn_app_version;
    NavigationView navigationView;
    Class_Attendance class_attendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.admindashboard_layout));
        typeFace = Typeface.createFromAsset(getAssets(),
                "fonts/" + ServerApiadmin.FONT);
        applicationControllerAdmin = (ApplicationControllerAdmin) getApplication();
        applicationControllerAdmin.setActiviName("A");
        dashboard_animlayout=(LinearLayout)findViewById(R.id.dashboard_animlayout);
        dashboard_animlayout.setVisibility(View.GONE);
        mDecoView = (DecoView) findViewById(R.id.dynamicArcView);
        mDecoView2 = (DecoView) findViewById(R.id.dynamicArcView_2);
        leave_dynamicArcView = (DecoView) findViewById(R.id.leave_dynamicArcView);
        student_msg_menu=(LinearLayout)findViewById(R.id.student_msg_menu);
        emp_msg_menu=(LinearLayout)findViewById(R.id.emp_msg_menu);
        tv_date=(TextView)findViewById(R.id.Text_Date) ;
        tv_month=(TextView)findViewById(R.id.text_monthname) ;
        stuatt_total=(TextView)findViewById(R.id.stuatt_total);
        stu_present=(TextView)findViewById(R.id.stuatt_present);
        staff_not_taken_layout=(TextView)findViewById(R.id.staff_not_taken_layout);
        student_not_taken_layout=(TextView)findViewById(R.id.student_not_taken_layout);
        stu_absent=(TextView)findViewById(R.id.stuatt_absent);
        stu_onleave=(TextView)findViewById(R.id.stuatt_onleave);
        admindashboard_layout=(LinearLayout)findViewById(R.id.admindashboard_layout);
        staff_total=(TextView)findViewById(R.id.staff_total);
        staff_present=(TextView)findViewById(R.id.staff_present);
        staff_absent=(TextView)findViewById(R.id.staff_absent);
        staff_onleave=(TextView)findViewById(R.id.staff_onleave);
        textnoticeboard_firstline=(TextView)findViewById(R.id.noticeboard_firstline);
        textnoticeboard_secondline=(TextView)findViewById(R.id.noticeboard_secondtline);
        top_username=(TextView)findViewById(R.id.top_username);
        top_userdesgn=(TextView)findViewById(R.id.top_userdesgn);
        notice_line_second=(LinearLayout)findViewById(R.id.notice_line_second);
        TextView txt_schoolname=(TextView)findViewById(R.id.text_school_name);
        gallery_list=(GridView)findViewById(R.id.gallery_list);
        gallery_list_vedio=(GridView)findViewById(R.id.gallery_list_vedio);
        layout_staff_attend=(LinearLayout)findViewById(R.id.layout_staff_attend);
        layout_monthly_attend=(LinearLayout)findViewById(R.id.layout_monthly_attend);
        layout_month_att_status=(LinearLayout)findViewById(R.id.layout_month_att_status);
        layout_staff_attend_status=(LinearLayout)findViewById(R.id.layout_staff_attend_status);
        layout_stu_attendance=(LinearLayout)findViewById(R.id.layout_stu_attendance);
        linear_e_class=(LinearLayout)findViewById(R.id.linear_e_class);
        linear_live_video=(LinearLayout)findViewById(R.id.linear_live_video);
        staff_total_attendance=(TextView)findViewById(R.id.staff_total_attendance);
        staff_present_attendance=(TextView)findViewById(R.id.staff_present_attendance);
        staff_absent_attendance=(TextView)findViewById(R.id.staff_absent_attendance);
        staff_onleave_attendance=(TextView)findViewById(R.id.staff_onleave_attendance);
        btn_app_version=(TextView)findViewById(R.id.btn_app_version);
        alloted_leave=(TextView)findViewById(R.id.alloted_leave);
        leave_taken=(TextView)findViewById(R.id.leave_taken);
        leave_balance=(TextView)findViewById(R.id.leave_balance);
        calendar_disclose_invisible=(LinearLayout)findViewById(R.id.calendar_disclose_invisible);
        txt_schoolname.setText(applicationControllerAdmin.getschool_name());
        textnoticeboard_firstline.setSelected(true);
        textnoticeboard_secondline.setSelected(true);
        tv_month.setTypeface(typeFace);
        tv_date.setTypeface(typeFace);
        mainScrollView = (ScrollView)findViewById(R.id.scrool_main);
        mainScrollView.smoothScrollTo(0, 0);
        mainScrollView.fullScroll(ScrollView.FOCUS_UP);
        class_attendance=new Class_Attendance();

       btn_app_version.setText(applicationControllerAdmin.getAppversion());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_menuicon);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        bar.setTitle("Dashboard");

        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        View view= navigationView.getHeaderView(0);
        imageView_nav_logo=view.findViewById(R.id.imageView_nav_logo);
        String imagerplace= ServerApiadmin.LOGO_API+applicationControllerAdmin.getschool_logo();
        imagerplace=imagerplace.replace("..","");
        imageView_nav_logo.setImageURI(Uri.parse(imagerplace));
        Glide.get(getApplicationContext()).clearMemory();
        Glide
                .with(getApplicationContext())
                .load(imagerplace)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView_nav_logo);


////////////////////// hide drawer item with login type///////////
         if(applicationControllerAdmin.getLoginType().equals("2")){
             /// leave student admin drawer hide ////
             navigationView = (NavigationView) findViewById(R.id.nav_view);
             Menu nav_Menu = navigationView.getMenu();
             nav_Menu.findItem(R.id.nav_Student_leave_status).setVisible(false);
         }else {

             hideItem();
         }
/////////////////////////////hide dasboard///////////////////////////
        if(applicationControllerAdmin.getLoginType().equals("4")){
            student_action="20";
            layout_monthly_attend.setVisibility(View.VISIBLE);
            layout_month_att_status.setVisibility(View.GONE);
            layout_staff_attend.setVisibility(View.GONE);
            layout_staff_attend_status.setVisibility(View.GONE);
            layout_stu_attendance.setVisibility(View.VISIBLE);////today
            calendar_disclose_invisible.setVisibility(View.INVISIBLE);


            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new Runnable() {
                public void run() {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            new staffapidetails().execute();

                        }
                    });
                }
            }, 0, 100, TimeUnit.SECONDS);


        }else{
            student_action="4";
            layout_monthly_attend.setVisibility(View.GONE);
            layout_month_att_status.setVisibility(View.GONE);
            layout_staff_attend.setVisibility(View.VISIBLE);
            layout_staff_attend_status.setVisibility(View.VISIBLE);
            layout_stu_attendance.setVisibility(View.VISIBLE);
            calendar_disclose_invisible.setVisibility(View.VISIBLE);


            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new Runnable() {
                public void run() {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            new staffapiattendance().execute();

                        }
                    });
                }
            }, 0, 100, TimeUnit.SECONDS);

        }
         gallery_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(applicationControllerAdmin.getPhotoGallery().equals("1")){
                   TextView tv_grid_heading=(TextView)view.findViewById(R.id.event_title);
                   TextView tv_grid_desc=(TextView)view.findViewById(R.id.event_description);
                   TextView tv_grid_tran=(TextView)view.findViewById(R.id.transection_id);
                   Intent intent=new Intent(MainActivity_Admin.this, Zoom_Image_Gallery.class);
                   intent.putExtra("HEADLINE",tv_grid_heading.getText().toString());
                   intent.putExtra("DESC",tv_grid_desc.getText().toString());
                   intent.putExtra("TRANID",tv_grid_tran.getText().toString());
                   startActivityForResult(intent,100);
                   applicationControllerAdmin.setActiviName("G");
               }else {
                   showAlertDialog();
               }
            }
        });
        applicationControllerAdmin.setActiviName("A");
        gallery_list_vedio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(applicationControllerAdmin.getVideoGallery().equals("1")){
                    TextView tv_grid_heading=(TextView)view.findViewById(R.id.event_title);
                    TextView tv_grid_desc=(TextView)view.findViewById(R.id.event_description);
                    TextView tv_grid_tran=(TextView)view.findViewById(R.id.transection_id_v);
                    Intent intent=new Intent(MainActivity_Admin.this, Vedios_gallery_Activity.class);
                    intent.putExtra("HEADLINE",tv_grid_heading.getText().toString());
                    intent.putExtra("DESC",tv_grid_desc.getText().toString());
                    intent.putExtra("TRANID",tv_grid_tran.getText().toString());
                    startActivityForResult(intent,100);
                    applicationControllerAdmin.setActiviName("G");
                }else {
                   showAlertDialog();
                }
            }
        });
         if(applicationControllerAdmin.getClassAttendance().equals("1")){
             student_msg_menu.setVisibility(View.VISIBLE);
         }else {
             student_msg_menu.setVisibility(View.GONE);
         }
        student_msg_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent= new Intent(MainActivity_Admin.this, Class_Attendance.class);
                    startActivityForResult(intent,100);
            }
        });

         if(applicationControllerAdmin.getLoginType().equalsIgnoreCase("2")){
             emp_msg_menu.setVisibility(View.VISIBLE);
         }else {
             emp_msg_menu.setVisibility(View.GONE);

         }

        emp_msg_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity_Admin.this, Emp_Attendance.class);
                startActivityForResult(intent,100);
            }
        });

        LinearLayout admin_school_calender= findViewById(R.id.admin_school_calender);
        if(applicationControllerAdmin.getCalendar().equals("1")){
            admin_school_calender.setVisibility(View.VISIBLE);
        }else {
            admin_school_calender.setVisibility(View.GONE);
        }
        admin_school_calender.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                    Intent intent= new Intent(MainActivity_Admin.this, Admin_School_Calendar.class);
                    startActivityForResult(intent,100);
            }
        });
        LinearLayout linear_timetable=(LinearLayout)findViewById(R.id.linear_timetable);
        if(applicationControllerAdmin.getTimeTable().equals("1")){
            linear_timetable.setVisibility(View.VISIBLE);
        }else {
            linear_timetable.setVisibility(View.GONE);
        }
        linear_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applicationControllerAdmin.setuser_event("T");
                    Intent intent= new Intent(MainActivity_Admin.this, Time_table.class);
                    startActivityForResult(intent,100);
            }
        });
       /* LinearLayout linear_classresult=(LinearLayout)findViewById(R.id.linear_classresult);
        linear_classresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity_Admin.this,Result_Submit_Classwise.class);
                startActivityForResult(intent,100);
            }
        });*/
        LinearLayout linear_visitor=(LinearLayout)findViewById(R.id.linear_visitors);
        if(applicationControllerAdmin.getVisitors().equals("1")){
            linear_visitor.setVisibility(View.VISIBLE);
        }else {
            linear_visitor.setVisibility(View.GONE);
        }
        linear_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent= new Intent(MainActivity_Admin.this, Visiter_Activity.class);
                    startActivityForResult(intent,100);
            }
        });
        LinearLayout linear_complaint=(LinearLayout)findViewById(R.id.linear_complaint);
        if(applicationControllerAdmin.getComplain().equals("1")){
            linear_complaint.setVisibility(View.VISIBLE);
        }else {
            linear_complaint.setVisibility(View.GONE);
        }
        linear_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent= new Intent(MainActivity_Admin.this, Complaint_Box.class);
                    startActivityForResult(intent,100);
            }
        });
        LinearLayout linear_diary=(LinearLayout)findViewById(R.id.linear_diary);
        if(applicationControllerAdmin.getHomework().equals("1")){
            linear_diary.setVisibility(View.VISIBLE);
        }else {
            linear_diary.setVisibility(View.GONE);
        }
        linear_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent= new Intent(MainActivity_Admin.this, Homework_List.class);
                    startActivityForResult(intent,100);
            }
        });

        LinearLayout linear_varify=(LinearLayout)findViewById(R.id.linear_varify);
        linear_varify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity_Admin.this, Homework_Varify.class);
                startActivityForResult(intent,100);
            }
        });



        linear_e_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity_Admin.this, E_Class_Video_Details.class);
                startActivityForResult(intent,100);
            }
        });

        if(applicationControllerAdmin.getLiveClass().equals("1")){
            linear_live_video.setVisibility(View.VISIBLE);
        }else {
            linear_live_video.setVisibility(View.GONE);
        }

        LinearLayout linear_live_video=(LinearLayout)findViewById(R.id.linear_live_video);
        linear_live_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity_Admin.this, LiveClass.class);
                intent.putExtra("notice_ID","D");
                startActivityForResult(intent,100);
            }
        });

        LinearLayout linear_transportation=(LinearLayout)findViewById(R.id.linear_transportation);
        linear_transportation.setVisibility(View.GONE);
        if(applicationControllerAdmin.getBusesRoute().equals("1")){
            linear_transportation.setVisibility(View.VISIBLE);
        }else {
            linear_transportation.setVisibility(View.GONE);
        }
        linear_transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent= new Intent(MainActivity_Admin.this, Bus_on_Route.class);
                    startActivityForResult(intent,100);
            }
        });

       /* LinearLayout linear_salary=findViewById(R.id.linear_salary);
        linear_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity_Admin.this,Salary_Slip.class);
                startActivityForResult(intent,100);
            }
        });*/



        //////////////////////////////////Dashboard Staff Attendance////////////////////////////////////
        LinearLayout staff_present_layout=(LinearLayout)findViewById(R.id.staff_present_layout);
        staff_present_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applicationControllerAdmin.setATTTYPE("Present");
                if(staff_present.getText().toString().equalsIgnoreCase("0")){
                }else{
                        Intent intent= new Intent(MainActivity_Admin.this, Staff_Attendance.class);
                        startActivityForResult(intent,100);
                }
            }
        });
        LinearLayout staff_absent_layout=(LinearLayout)findViewById(R.id.staff_absent_layout);
        staff_absent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applicationControllerAdmin.setATTTYPE("Absent");
                if(staff_absent.getText().toString().equalsIgnoreCase("0")){
                }else{
                        Intent intent= new Intent(MainActivity_Admin.this, Staff_Attendance.class);
                        startActivityForResult(intent,100);
                }
            }
        });
        LinearLayout staff_leave_layout=(LinearLayout)findViewById(R.id.staff_leave_layout);
        staff_leave_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applicationControllerAdmin.setATTTYPE("Leave");
                if(staff_onleave.getText().toString().equalsIgnoreCase("0")){
                }else{
                        Intent intent= new Intent(MainActivity_Admin.this, Staff_Attendance.class);
                        startActivityForResult(intent,100);
                }
            }
        });
        textnoticeboard_firstline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     if(applicationControllerAdmin.getNotice().equals("1")){
                         if(Headline_noticeID.equals("0")){
                         }else{
                             Intent intent= new Intent(MainActivity_Admin.this, Notice_Board.class);
                             intent.putExtra("notice_ID",Headline_noticeID);
                             startActivityForResult(intent,100);
                         }
                     }else {
                        showAlertDialog();
                     }
            }
        });
        textnoticeboard_secondline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(applicationControllerAdmin.getNotice().equals("1")){
                    if(Headline_notice2ID.equals("0")){
                    }else{
                        Intent intent= new Intent(MainActivity_Admin.this, Notice_Board.class);
                        intent.putExtra("notice_ID",Headline_notice2ID);
                        startActivityForResult(intent,100);
                    }
                }else {
                    showAlertDialog();
                }
            }
        });
        //////////////////////////////////Student ATT////////////////////////////////////
        LinearLayout student_present_layout=(LinearLayout)findViewById(R.id.student_present_layout);
        student_present_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applicationControllerAdmin.setATTTYPE("Present");
                if(stu_present.getText().toString().equalsIgnoreCase("0")){
                }else{
                    if(applicationControllerAdmin.getLoginType().toString().equalsIgnoreCase("2")){
                        Intent intent= new Intent(MainActivity_Admin.this, Student_Attendance.class);
                        startActivityForResult(intent,100);
                    }
                }
            }
        });
        LinearLayout stu_absent_layout=(LinearLayout)findViewById(R.id.student_absent_layout);
        stu_absent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applicationControllerAdmin.setATTTYPE("Absent");
                if(stu_absent.getText().toString().equalsIgnoreCase("0")){
                }else{
                    if(applicationControllerAdmin.getLoginType().toString().equalsIgnoreCase("2")){
                        Intent intent= new Intent(MainActivity_Admin.this, Student_Attendance.class);
                        startActivityForResult(intent,100);
                    }
                }
            }
        });
        LinearLayout stu_leave_layout=(LinearLayout)findViewById(R.id.student_leave_layout);
        stu_leave_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applicationControllerAdmin.setATTTYPE("Leave");
                if(stu_onleave.getText().toString().equalsIgnoreCase("0")){
                }else{
                    if(applicationControllerAdmin.getLoginType().toString().equalsIgnoreCase("2")){
                        Intent intent= new Intent(MainActivity_Admin.this, Student_Attendance.class);
                        startActivityForResult(intent,100);
                    }
                }
            }
        });
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);
        String month=theMonth(mDay);
        tv_date.setText(cDay+"");
        tv_month.setText(month);
        date_current=(mYear+ "-"+ (mDay + 1) + "-" + cDay);
        applicationControllerAdmin.setDate(mYear+ "-"+ (mDay + 1) + "-" + cDay);
        LinearLayout layout_calendar= findViewById(R.id.layout_calendar);
        layout_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MainActivity_Admin.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date.setText(dayOfMonth+"");
                                tv_month.setText(month);
                                applicationControllerAdmin.setDate(year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
                                AttendanceCount_absent="0";
                                AttendanceCount_present ="0";
                                AttendanceCount_leave ="0";
                                student_count="0";
                                AttendanceCount_presentemp="0";
                                AttendanceCount_absentemp ="0";
                                AttendanceCount_leaveemp ="0";
                                staff_count="0";
                                new staffapiattendance().execute();
                              //  new Admindetails().execute();
                               // new Homeworkprocess().execute();
                                     /* date.setText(new StringBuilder().append(dayOfMonth)
                                              .append("/").append(monthOfYear + 1).append("/").append(year));*/
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        dashboard_animlayout.setVisibility(View.VISIBLE);
        dashboard_animlayout.startAnimation(animationDown);
        profile_image= findViewById(R.id.main_profilepic);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mainScrollView.scrollTo(0, profile_image.getBottom());
            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_Admin.this, Profile.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MainActivity_Admin.this, profile_image, getString(R.string.activity_image_trans));
                    startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });
        ////////////////////////////////////////load data///////////////////////////
        new Admindetails().execute();
        if(applicationControllerAdmin.getNotify().equals("1")){
            Intent intent = new Intent(MainActivity_Admin.this, Notification.class);
            startActivity(intent);

        }
        /////////////////////////update version app servicess/////////////////
        if(applicationControllerAdmin.getAppversion().equalsIgnoreCase("1.40")){

        }else{
            updatedialog();
        }
//        class_attendance.new AbbreviationList().execute();
//        class_attendance.new SelectclassDetails().execute();
//        class_attendance.new SelectBranch().execute();
    }

    ///////////drawer item hide menu methode///////////////
    private void hideItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_leave_status).setVisible(false);

    }
    public static String theMonth(int month){
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }
    private void loaddataonprogress() {
        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#F6608A"))
                .setRange(0, mSeriesMax, 0)
                .setInitialVisibility(false)
                .build();
        int mSeries1Index = mDecoView.addSeries(seriesItem);
        final TextView textPercentage = (TextView) findViewById(R.id.textPercentage);
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                float percentFilled = ((currentPosition - seriesItem.getMinValue()) / (seriesItem.getMaxValue() - seriesItem.getMinValue()));
                textPercentage.setText(String.format("%.0f%%", percentFilled * 100f));
            }
            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {
            }
        });
        mDecoView.addEvent(new DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                .setIndex(mSeries1Index)
                .setDuration(2000)
                .setDelay(1250)
                .build());
        mDecoView.addEvent(new DecoEvent.Builder(42.4f)
                .setIndex(mSeries1Index)
                .setDelay(3250)
                .build());
        mDecoView.addEvent(new DecoEvent.Builder(staff_att_percent).setIndex(mSeries1Index).setDelay(4000).build());
        //////////////////////////////////////second///////////////////////////
        final SeriesItem seriesItem2 = new SeriesItem.Builder(Color.parseColor("#FE7E0C"))
                .setRange(0, mSeriesMax, 0)
                .setInitialVisibility(false)
                .build();
        int mSeries1Index2 = mDecoView2.addSeries(seriesItem2);
        final TextView textPercentage2 = (TextView) findViewById(R.id.textPercentage_2);
        seriesItem2.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                float percentFilled = ((currentPosition - seriesItem.getMinValue()) / (seriesItem.getMaxValue() - seriesItem.getMinValue()));
                textPercentage2.setText(String.format("%.0f%%", percentFilled * 100f));
            }
            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {
            }
        });
        mDecoView2.addEvent(new DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                .setIndex(mSeries1Index2)
                .setDuration(2000)
                .setDelay(1250)
                .build());
        mDecoView2.addEvent(new DecoEvent.Builder(42.4f)
                .setIndex(mSeries1Index2)
                .setDelay(3250)
                .build());
        mDecoView2.addEvent(new DecoEvent.Builder(stu_att_percent).setIndex(mSeries1Index2).setDelay(4000).build());
    }
    private void createprogress() {
        SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, mSeriesMax, 0)
                .setInitialVisibility(true)
                .build();
        mBackIndex = mDecoView.addSeries(seriesItem);
        int mBackIndex2 = mDecoView2.addSeries(seriesItem);
        mDecoView.executeReset();
        mDecoView.addEvent(new DecoEvent.Builder(mSeriesMax)
                .setIndex(mBackIndex)
                .setDuration(3000)
                .setDelay(100)
                .build());
        mDecoView2.executeReset();
        mDecoView2.addEvent(new DecoEvent.Builder(mSeriesMax)
                .setIndex(mBackIndex2)
                .setDuration(3000)
                .setDelay(100)
                .build());
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if(scheduler!=null){
            scheduler.shutdown();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity__admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_circular) {
            if(applicationControllerAdmin.getCircular().equals("1")){
                Intent intent = new Intent(MainActivity_Admin.this, Circular.class);
                intent.putExtra("notice_ID","0");
                startActivity(intent);
            }else {
                showAlertDialog();
            }
            // Handle the camera action
        } if (id == R.id.event_info_upload) {

                Intent intent = new Intent(MainActivity_Admin.this, Submit_Event_Details.class);
                startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_noticeboard) {
            if(applicationControllerAdmin.getNotice().equals("1")){
                Intent intent = new Intent(MainActivity_Admin.this, Notice_Board.class);
                intent.putExtra("notice_ID","D");
                startActivity(intent);
            }else {
                showAlertDialog();
            }
        } else if (id == R.id.nav_notification) {
            Intent intent = new Intent(MainActivity_Admin.this, Notification.class);
            startActivity(intent);
        }else if(id== R.id.nav_e_book){
            Intent intent = new Intent(MainActivity_Admin.this, E_Book.class);
            startActivity(intent);

        } else if (id == R.id.nav_calendar) {
            if(applicationControllerAdmin.getCalendar().equals("1")){
                Intent intent = new Intent(MainActivity_Admin.this, Admin_School_Calendar.class);
                startActivity(intent);
            }else {
                showAlertDialog();
            }
        }else if (id == R.id.nav_time_table) {
            if(applicationControllerAdmin.getTimeTable().equals("1")){
                applicationControllerAdmin.setuser_event("T");
                Intent intent = new Intent(MainActivity_Admin.this, Time_table.class);
                startActivity(intent);
            }else {
                showAlertDialog();
            }
        }else if (id == R.id.nav_class_attendance) {
            if(applicationControllerAdmin.getClassAttendance().equals("1")){
                Intent intent = new Intent(MainActivity_Admin.this, Class_Attendance.class);
                startActivity(intent);
            }else {
                showAlertDialog();
            }
        }else if (id == R.id.nav_leave_status) {
            Intent intent = new Intent(MainActivity_Admin.this, Leave_Applicatios.class);
            startActivity(intent);
        }else if (id == R.id.nav_Student_leave_status) {
            Intent intent = new Intent(MainActivity_Admin.this, Student_Leave_Applicatios.class);
            startActivity(intent);
        } else if (id == R.id.nav_attendance_status) {
            Intent intent = new Intent(MainActivity_Admin.this, Employee_AttStatus.class);
            startActivity(intent);
        }else if (id == R.id.nav_changepassword) {
                Intent intent = new Intent(MainActivity_Admin.this, Change_Password.class);
                startActivity(intent);
        }else if (id == R.id.nav_logout) {
            sharedpreferences = getSharedPreferences("APPDATA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent= new Intent(MainActivity_Admin.this, Login_Activity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_visitor) {
            Intent intent= new Intent(MainActivity_Admin.this, Visiter_Activity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
           //////////////////////////Staff_Attendance///////////
    private class staffapiattendance extends AsyncTask<String, String, Integer> {
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
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.STAFF_ATTENDANCE,Para1("4",applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID(),applicationControllerAdmin.getDate()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        AttendanceCount_presentemp="0";
                        AttendanceCount_absentemp ="0";
                        AttendanceCount_leaveemp ="0";
                        staff_count="0";
                        staff_not_taken="0";
                            JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String  LeaveGroup=object.getString("LeaveGroup");
                            String AttendanceCount=object.getString("AttendanceCount");
                            if(LeaveGroup.equalsIgnoreCase("Absent")){
                                AttendanceCount_absentemp=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Present")){
                                AttendanceCount_presentemp=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Leave")){
                                AttendanceCount_leaveemp=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Total")){
                                staff_count=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Not Taken")){
                                staff_not_taken=AttendanceCount;
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
                        staff_present.setText(AttendanceCount_presentemp);
                        staff_absent.setText(AttendanceCount_absentemp);
                        staff_onleave.setText(AttendanceCount_leaveemp);
                        staff_not_taken_layout.setText("Not Punched: "+staff_not_taken);
                        int a=Integer.parseInt(AttendanceCount_presentemp);
                        int b=Integer.parseInt(AttendanceCount_absentemp);
                        int c=Integer.parseInt(AttendanceCount_leaveemp);
                        staff_total.setText(staff_count);
                        int d=Integer.parseInt(staff_count);
                        if(d>0){
                            staff_att_percent=(float)(a*100)/d;
                        }else{
                            staff_att_percent=0;
                        }
                    if(staff_not_taken.equals("0")){
                        staff_not_taken_layout.setVisibility(View.GONE);
                    }else {
                        staff_not_taken_layout.setVisibility(View.VISIBLE);
                    }

                    new Studentapi().execute();
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(admindashboard_layout, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
                            .setAction("Error", new View.OnClickListener() {
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
                            .make(admindashboard_layout, "Employee Attendance not found for selected date", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                   Intent in=getIntent();
                                    finish();
                                    startActivity(in);
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    stu_att_percent=0;
                    staff_att_percent=0;
                    createprogress();
                    loaddataonprogress();
                    Message="Employee Attendance not Found for Selected Date";
                   // showSuccessDialog();
                    new Studentapi().execute();
                    break;
            }
        }
    }
    public String Para1(String action,String BranchCode,String SchoolCode,String SessionId,String FYId,String AttendanceDate){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("LeaveGroup", "null");
            jsonParam.put("AttendanceDate", AttendanceDate);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    ///////////////////Student_Attendance API ///////////
    private class Studentapi extends AsyncTask<String, String, Integer> {
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
            Log.d("TAG", "doInBackground:student api "+applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.STUDENT_ATTENDANCE+"////////////"+Para(student_action,applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID(),applicationControllerAdmin.getDate()));
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.STUDENT_ATTENDANCE,Para(student_action,applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID(),applicationControllerAdmin.getDate()),"1");
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
                    stu_present.setText(AttendanceCount_present);
                    stu_absent.setText(AttendanceCount_absent);
                    stu_onleave.setText(AttendanceCount_leave);
                    int a=Integer.parseInt(AttendanceCount_present);
                    int b=Integer.parseInt(AttendanceCount_absent);
                    int c=Integer.parseInt(AttendanceCount_leave);
                    int d=Integer.parseInt(student_count);
                    stuatt_total.setText(student_count);
                    student_not_taken_layout.setText("Not Taken: "+student_not_taken);
                    if(student_not_taken.equals("0")){
                        student_not_taken_layout.setVisibility(View.GONE);
                    }else {
                        student_not_taken_layout.setVisibility(View.VISIBLE);
                    }

                    if (d>0){
                        stu_att_percent=(float)(a*100)/d;
                    }else{
                        stu_att_percent=0;
                    }
                    createprogress();
                    loaddataonprogress();

                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(admindashboard_layout, "Student Attendance not found for selected date", Snackbar.LENGTH_LONG)
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
                            .make(admindashboard_layout, "Student Attendance not found for selected date", Snackbar.LENGTH_LONG)
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
            if(applicationControllerAdmin.getLoginType().equalsIgnoreCase("4")){
                jsonParam.put("StudentCode", applicationControllerAdmin.getActiveempcode());
            }
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

        private void showSuccessDialog() {

            final Dialog dialog = new Dialog(MainActivity_Admin.this, R.style.Theme_AppCompat_Dialog_Alert);
            dialog.setContentView(R.layout.error_dialog);
            dialog.setTitle("");
            // set the custom dialog components - text, image and button
            TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
            TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
            text_label.setText(Message);
            TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
            text_message.setText("");
            text_toplabel.setTypeface(typeFace);
            Button dialogButton = (Button) dialog.findViewById(R.id.button_closed);
            text_toplabel.setTypeface(typeFace);
            text_label.setTypeface(typeFace);
            text_message.setTypeface(typeFace);
            dialogButton.setTypeface(typeFace);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            dialog.show();


    }
    //////////////////////////////////////// User Details//////////////////////////////////
    private class Admindetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity_Admin.this);

        @Override
        protected void onPreExecute() {
             progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Fetching Data...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            Log.d("TAG", "doInBackground:Admindetails() "+applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.ADMINDETAILS_API+"/////"+Paradetails(applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getUserID()));
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.ADMINDETAILS_API,Paradetails(applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getUserID()),"1");
            String r=applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.ADMINDETAILS_API;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                      //  for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(0);
                            AdminName=object.getString("Name");
                            Designation=object.getString("Designation");
                            Address=object.getString("Address");
                            ContactNo=object.getString("ContactNo");
                            Email=object.getString("Email");
                            PhotoPath=object.getString("PhotoPath");
                            String code=object.getString("Code");
                            String DesignationId=object.getString("DesignationId");
                            String DepartmentId=object.getString("DepartmentId");
                           applicationControllerAdmin.setuserName(AdminName);
                           applicationControllerAdmin.setUser_desgn(Designation);
                           applicationControllerAdmin.setProfilePIC(PhotoPath);
                           applicationControllerAdmin.setActiveempcode(code);
                           applicationControllerAdmin.setDesignationId(DesignationId);
                           applicationControllerAdmin.setDepartmentId(DepartmentId);
                            status=1;
                      //  }
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                      top_username.setText(AdminName);
                      top_userdesgn.setText(Designation);

                    String imagerplace= ServerApiadmin.MAIN_IPLINK+PhotoPath;
                    imagerplace=imagerplace.replace("..","");
                    profile_image.setImageURI(Uri.parse(imagerplace));
                    Glide.get(getApplicationContext()).clearMemory();
                    Glide
                            .with(MainActivity_Admin.this)
                            .load(imagerplace)
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true))
                            .into(profile_image);

                    if(applicationControllerAdmin.getLoginType().equals("4")){
                        new staffapidetails().execute();
                        new LeaveStatus_data().execute();

                    }
                    new Notice_board_dash().execute();
                    new EventGaller().execute();
                    new EventGallerVedio().execute();
                    break;

                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(admindashboard_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Paradetails(String BranchCode,String SchoolCode,String UserID){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("UserId", UserID);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    /////////////////////////////////////////Notice

    private class Notice_board_dash extends AsyncTask<String, String, Integer> {
      //  ProgressDialog progressDialog = new ProgressDialog(MainActivity_Admin.this);

        @Override
        protected void onPreExecute() {

           // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }



        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            Log.d("TAG", "doInBackground:notice board "+applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.NOTICEBOARD_API_ONE+"//////////////////"+Para(applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getFyID(),"9"));
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.NOTICEBOARD_API_ONE,Para(applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getFyID(),"9"),"1");
            String api= ServerApiadmin.NOTICEBOARD_API_ONE;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);

                            if(i==0){
                                Headline_notice=object.getString("Headline");
                                Headline_noticeID=object.getString("TransId");
                                status=1;
                            }else if(i==1){
                                Headline_notice2=object.getString("Headline");
                                Headline_notice2ID=object.getString("TransId");
                                status=2;
                            }

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
                    notice_line_second.setVisibility(View.GONE);
                    textnoticeboard_firstline.setText(Headline_notice);

                    break;
                case 2:
                    notice_line_second.setVisibility(View.VISIBLE);
                    textnoticeboard_firstline.setText(Headline_notice);
                    textnoticeboard_secondline.setText(Headline_notice2);
                    break;

                case -1:
                    notice_line_second.setVisibility(View.GONE);
                    textnoticeboard_firstline.setText("Waiting for new notice......");

                    break;

            }

        }
    }
    public String Para(String schoolcode, String sessionid, String branchcode, String fyid, String action){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", schoolcode);
            jsonParam.put("SessionId", sessionid);
            jsonParam.put("BranchCode", branchcode);
            jsonParam.put("FyId", fyid);
            jsonParam.put("StudentCode", applicationControllerAdmin.getActiveempcode());
            jsonParam.put("Action", action);
            jsonParam.put("Updatedby", applicationControllerAdmin.getLoginType());
            jsonParam.put("loginTypeId", applicationControllerAdmin.getLoginType());
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    ////////////////////////////////////////////Event Gallery API /////////////////////////////


    private class EventGaller extends AsyncTask<String, String, Integer> {
        //ProgressDialog progressDialog = new ProgressDialog(MainActivity_Admin.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status = 0;
            JsonParser josnparser = new JsonParser(getApplicationContext());
            String response = josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.EVENT_GALLERY_API, Para( applicationControllerAdmin.getschoolCode(), applicationControllerAdmin.getBranchcode(), applicationControllerAdmin.getSeesionID()), "1");
            if (response != null) {
                if (response.length() > 5) {
                    try {
                        JSONArray array = new JSONArray(response);
                        gallery_helpers = new ArrayList<Gallery_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String   EventDate = object.getString("EventDate");
                            String    Title = object.getString("Title");
                            String   Description = object.getString("Description");
                            String   AlbumIcon = object.getString("AlbumIcon");
                            String   TransId=object.getString("PhotoGalleryType");
                            item = new Gallery_Helper(EventDate,Title,Description,AlbumIcon,TransId);
                            gallery_helpers.add(item);
                            status = 1;
                        }

                        /////////////////////////////////////////////////
                       /* for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String   EventDate = object.getString("EventDate");
                            String    Title = object.getString("Title");
                            String   Description = object.getString("Description");
                            String   AlbumIcon = object.getString("AlbumIcon");
                            item = new Gallery_Helper(EventDate,Title,Description,AlbumIcon);
                            gallery_helpers.add(item);
                            status = 1;
                        }*/


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
            //progressDialog.dismiss();
            switch (s) {
                case 1:
                    int size=gallery_helpers.size();
                    int totalWidth =(100*size)*4;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(totalWidth,LinearLayout.LayoutParams.MATCH_PARENT);
                    gallery_list.setLayoutParams(params);
                    gallery_list.setNumColumns(size);
                    gallery_list.setColumnWidth(totalWidth);
                    Gallery_Adapter adapter = new Gallery_Adapter(getApplicationContext(), R.layout.gallery_item_list, gallery_helpers);
                    gallery_list.setAdapter(adapter);
                    mainScrollView.fullScroll(ScrollView.FOCUS_UP);
                    mainScrollView.smoothScrollTo(0, 0);
                    break;
                case -2:


                    // Toast.makeText(getApplicationContext(),"Subject not found",Toast.LENGTH_LONG).show();
                    break;
                case -1:


                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Para(String SchoolCode, String BranchCode,  String SessionId) {
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

////////////////////////////////////////Event Vedios API//////////////////////////////

    private class EventGallerVedio extends AsyncTask<String, String, Integer> {
        //ProgressDialog progressDialog = new ProgressDialog(MainActivity_Admin.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status = 0;
            JsonParser josnparser = new JsonParser(getApplicationContext());
            String response = josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.EVENT_GALLERY_VEDIO_API, Para1( applicationControllerAdmin.getschoolCode(), applicationControllerAdmin.getBranchcode(), applicationControllerAdmin.getSeesionID()), "1");
            if (response != null) {
                if (response.length() > 5) {
                    try {
                        JSONArray array = new JSONArray(response);
                        gallery_vedios_helpers = new ArrayList<Gallery_Vedios_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String   EventDate = object.getString("EventDate");
                            String    Headline = object.getString("Headline");
                            String   Description = object.getString("Description");
                            String   AlbumIcon = object.getString("AlbumIcon");
                            String   TransId=object.getString("TransId");
                            item1 = new Gallery_Vedios_Helper(EventDate,Headline,Description,AlbumIcon,TransId);
                            gallery_vedios_helpers.add(item1);
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
            //progressDialog.dismiss();
            switch (s) {
                case 1:
                    int size=gallery_vedios_helpers.size();
                    int totalWidth =(100*size)*4;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(totalWidth,LinearLayout.LayoutParams.MATCH_PARENT);
                    gallery_list_vedio.setLayoutParams(params);
                    gallery_list_vedio.setNumColumns(size);
                    gallery_list_vedio.setColumnWidth(totalWidth);
                    Gallery_Vedios_Adapter adapter = new Gallery_Vedios_Adapter(getApplicationContext(), R.layout.gallery_item_vedios, gallery_vedios_helpers);
                    gallery_list_vedio.setAdapter(adapter);
                    mainScrollView.fullScroll(ScrollView.FOCUS_UP);
                    mainScrollView.smoothScrollTo(0, 0);
                    profile_image.setFocusable(true);
                    break;
                case -2:


                    // Toast.makeText(getApplicationContext(),"Subject not found",Toast.LENGTH_LONG).show();
                    break;
                case -1:


                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Para1(String SchoolCode, String BranchCode,  String SessionId) {
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(applicationControllerAdmin.getLoginType().equals("2")){
            new staffapiattendance().execute();
            new Notice_board_dash().execute();
        }else{

            new Studentapi().execute();

        }
    }

    private void showAlertDialog() {
        Snackbar snackbar1 = Snackbar
                .make(admindashboard_layout, "You are not permitted. Please contact to administrator.", Snackbar.LENGTH_LONG)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                    }
                });
        snackbar1.setActionTextColor(Color.RED);
        snackbar1.show();
    }

    private class staffapidetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity_Admin.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.STAFF_DETAILS_COUNT,Para1("12",applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID(),date_current),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        AttendanceCount_absentemp="0";
                        AttendanceCount_presentemp="0";
                        AttendanceCount_leaveemp="0";
                        staff_count="0";
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String  LeaveGroup=object.getString("LeaveGroup");
                            String AttendanceCount=object.getString("AttendanceCount");
                            if(LeaveGroup.equalsIgnoreCase("Absent")){
                                AttendanceCount_absentemp=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Present")){
                                AttendanceCount_presentemp=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Leave")){
                                AttendanceCount_leaveemp=AttendanceCount;
                            }else if(LeaveGroup.equalsIgnoreCase("Total")){
                                staff_count=AttendanceCount;
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
                    staff_present_attendance.setText(AttendanceCount_presentemp);
                    staff_absent_attendance.setText(AttendanceCount_absentemp);
                    staff_onleave_attendance.setText(AttendanceCount_leaveemp);
                    staff_total_attendance.setText(staff_count);

                    int a=Integer.parseInt(staff_count);
                    int b=Integer.parseInt(AttendanceCount_presentemp);
                   // int c=Integer.parseInt(AttendanceCount_leave);
                  //  int d=Integer.parseInt(student_count);
                    float staff_att_percentleave;
                    if(b>0){
                        staff_att_percentleave=(float)(b*100)/a;
                    }else{
                        staff_att_percentleave=0;
                    }

                    SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                            .setRange(0, mSeriesMax, 0)
                            .setInitialVisibility(true)
                            .build();
                    int mBackIndex2 = leave_dynamicArcView.addSeries(seriesItem);
                    leave_dynamicArcView.executeReset();
                    leave_dynamicArcView.addEvent(new DecoEvent.Builder(mSeriesMax)
                            .setIndex(mBackIndex2)
                            .setDuration(3000)
                            .setDelay(100)
                            .build());


                    final SeriesItem seriesItem2 = new SeriesItem.Builder(Color.parseColor("#F6608A"))
                            .setRange(0, mSeriesMax, 0)
                            .setInitialVisibility(false)
                            .build();
                    int mSeries1Index = leave_dynamicArcView.addSeries(seriesItem2);


                    final TextView textPercentageleave = (TextView) findViewById(R.id.textPercentageleave);
                    seriesItem2.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
                        @Override
                        public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                            float percentFilled = ((currentPosition - seriesItem2.getMinValue()) / (seriesItem2.getMaxValue() - seriesItem2.getMinValue()));
                            textPercentageleave.setText(String.format("%.0f%%", percentFilled * 100f));
                        }
                        @Override
                        public void onSeriesItemDisplayProgress(float percentComplete) {
                        }
                    });

                    leave_dynamicArcView.addEvent(new DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
                            .setIndex(mSeries1Index)
                            .setDuration(2000)
                            .setDelay(1250)
                            .build());
                    leave_dynamicArcView.addEvent(new DecoEvent.Builder(42.4f)
                            .setIndex(mSeries1Index)
                            .setDelay(3250)
                            .build());
                    leave_dynamicArcView.addEvent(new DecoEvent.Builder(staff_att_percentleave).setIndex(mSeries1Index).setDelay(4000).build());
                    new Studentapi().execute();
                    break;

                case -1:

                    Snackbar snackbar1 = Snackbar
                            .make(admindashboard_layout, "Attendance not found ", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                    Intent in=getIntent();
                                    finish();
                                    startActivity(in);
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();

                    break;
            }
        }
    }
    public String Para1(String action,String BranchCode,String SchoolCode,String EmployeeCode, String SessionId,String FYId,String AttendanceDate){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("EmployeeCode", EmployeeCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            // jsonParam.put("LeaveGroup", "null");
            jsonParam.put("AttendanceDate", AttendanceDate);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    //////////////////////////////////Leave Api Dashboard/////////////////////////////////
    private class LeaveStatus_data extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity_Admin.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(MainActivity_Admin.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            Log.d("TAG", "doInBackground: staff attendence "+applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.LEAVE_MASTER_DATA+"/////////"+Paradata("10",applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID()));
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.LEAVE_MASTER_DATA,Paradata("10",applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getSeesionID(),applicationControllerAdmin.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                       // TotalLeaves="0";
                        AllotedLeaves="0";
                        LeaveTaken="0.0";
                        Balance="0.0";
                        for (int i = 0; i < array.length(); i++) {
                         JSONObject object= array.getJSONObject(i);
                          //TotalLeaves=object.getString("TotalLeaves");
                          AllotedLeaves  =object.getString("AllotedLeaves");
                          LeaveTaken=object.getString("LeaveTaken");
                          Balance=object.getString("Balance");

                           allotedleave   += Double.parseDouble((AllotedLeaves));
                           alloted  = String.valueOf(allotedleave);


                           leavetaken   += Double.parseDouble(LeaveTaken);
                           leave_taken_add = String.valueOf(leavetaken);
                           balance  +=  Double.parseDouble(Balance);
                           balance_add =String.valueOf(balance);
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
                    alloted_leave.setText("Alloted Leave - "  +  alloted);
                    leave_taken.setText("Leave Taken - "   + leave_taken_add);
                    leave_balance.setText("leave_balance - "  +  balance_add);
                    //startCountAnimation(TotalLeaves);
                    break;
                case -1:
                    alloted_leave.setText("Alloted Leave - "  +  "0");
                    leave_taken.setText("Leave Taken - "   + "0");
                    leave_balance.setText("leave_balance - "  +  "0");
                    break;
            }
        }
    }
    public String Paradata(String action,String BranchCode,String SchoolCode,String EmployeeCode, String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("EmployeeCode", EmployeeCode);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + ServerApiadmin.FONT);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    private void updatedialog() {
        final Dialog dialog = new Dialog(MainActivity_Admin.this);
        dialog.setContentView(R.layout.updatedialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        TextView text_remindlater=dialog.findViewById(R.id.text_remindlater);
        TextView text_updatenow=dialog.findViewById(R.id.text_updatenow);
        TextView text_updatemsg=dialog.findViewById(R.id.text_updatemsg);
        TextView text_msgfull=dialog.findViewById(R.id.text_msgfull);
        text_remindlater.setTypeface(typeFace);
        text_updatenow.setTypeface(typeFace);
        text_updatemsg.setTypeface(typeFace);
        text_msgfull.setTypeface(typeFace);
        dialog.show();
        text_remindlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        text_updatenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=vidyawell.infotech.bsn.admin&hl=en";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);





            }
        });

    }

}
