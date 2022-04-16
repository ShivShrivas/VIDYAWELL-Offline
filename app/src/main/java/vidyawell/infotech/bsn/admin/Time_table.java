package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Timetable_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Timetable_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;


public class Time_table extends AppCompatActivity {

    int[] imageid ={R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten,R.drawable.kitchen_pack};
    ImageView timetable_image;
    TextView subject;
    TextView teacher_name,text_time_class;
    TextView lecture_time;
    ListView timetable_list;
    List<Timetable_Helper> timetable_helpers;
    ApplicationControllerAdmin applicationController;
    String day_name,EmployeeName,SubjectName,PeriodName,FromTime,ToTime;
    Spinner spinner_days,spinner_class;
    private int mDay;
    private Calendar myCalendar;
    boolean imagenumber=true;
    SharedPreferences sharedprefer;
    RelativeLayout layout_timetable;
    HashMap<Integer, String> mClass;
    public  ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    ClassList_Adapter adapter;
    Time_table activity = null;
    String class_ID;
    int image=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_time_table);
        applicationController=(ApplicationControllerAdmin)getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_timetable));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Time Table");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        activity  = this;
        layout_timetable=(RelativeLayout)findViewById(R.id.layout_timetable);
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mDay = c.get(java.util.Calendar.DAY_OF_WEEK);
        mDay=mDay-2;
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        spinner_days=(Spinner) findViewById(R.id.spinner_day);
        spinner_class=(Spinner) findViewById(R.id.spinner_class);
        spinner_days.setSelection(mDay);
        timetable_image=(ImageView) findViewById(R.id.timetable_image);
        subject=(TextView)findViewById(R.id.subject);
        teacher_name=(TextView)findViewById(R.id.teacher_name);
        lecture_time=(TextView)findViewById(R.id.lecture_time);
        text_time_class=findViewById(R.id.text_time_class);
        timetable_list = (ListView) findViewById(R.id.timetable_list);
        timetable_list.setVisibility(View.VISIBLE);
        if(applicationController.getschooltype().equals("0")){
            text_time_class.setText("Select Class");
        }else{
            text_time_class.setText("Select Course");
        }


        spinner_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                day_name=parent.getSelectedItem().toString();
                if(day_name.equalsIgnoreCase("Sunday")){
                    timetable_list.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Time Table not Available for Sunday",Toast.LENGTH_LONG).show();
                }else{
                    imagenumber=true;
                    timetable_list.setVisibility(View.VISIBLE);
                    if(applicationController.getuser_event().equalsIgnoreCase("R")){
                        new Routine().execute();
                    }
                }
             }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        new SelectclassDetails().execute();
        spinner_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                class_ID    = ((TextView) view.findViewById(R.id.text_cID)).getText().toString();
              }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btn_search=(Button)findViewById(R.id.search_list);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagenumber=true;
                image=0;
                timetable_list.setVisibility(View.GONE);
                new Timetable().execute();
            }
        });

        LinearLayout l1=(LinearLayout)findViewById(R.id.layout_class_name);
        CardView l2=(CardView)findViewById(R.id.layout_class_select);
        if(applicationController.getuser_event().equalsIgnoreCase("R")){
            SpannableString str1 = new SpannableString("Lecture Routine");
            str1.setSpan(typefaceSpan, 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            getSupportActionBar().setTitle(str1);
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);
            spinner_class.setVisibility(View.GONE);
            btn_search.setVisibility(View.GONE);
            imagenumber=true;
            image=0;
        }else{
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.VISIBLE);
            spinner_class.setVisibility(View.VISIBLE);
            btn_search.setVisibility(View.VISIBLE);
        }

        //Toast.makeText(getApplicationContext(),applicationController.getActiveempcode(),Toast.LENGTH_LONG).show();
    }
    private class Timetable extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Time_table.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Time_table.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.TIMETABLE_API,Para(class_ID,applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        JSONArray days = jsonObj.getJSONArray(day_name);
                            timetable_helpers = new ArrayList<Timetable_Helper>();
                            for (int i = 0; i < days.length(); i++) {
                                if(days.length()>10){
                                    status=-4;
                                }else{
                                JSONObject object = days.getJSONObject(i);
                                String teachName = object.getString("EmployeeName");
                                String SubjectName = object.getString("SubjectName");
                                String PeriodName = object.getString("PeriodName");
                                String FromTime = object.getString("FromTime");
                                String ToTime = object.getString("ToTime");
                                String PeriodType = object.getString("PeriodType");
                                String lect_name= FromTime+" - "+ToTime;
                                image=i;
                                if(PeriodType.equalsIgnoreCase("LunchPeriod")){
                                    image=10;
                                    imagenumber=false;
                                }else{
                                    if(imagenumber==false){
                                        image=i-1;
                                    }
                                }
                                Timetable_Helper item = new Timetable_Helper(SubjectName, "Subject Teacher - "+teachName, lect_name, imageid[image],PeriodType);
                                timetable_helpers.add(item);
                                status=1;
                         }   }
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
                    timetable_list.setVisibility(View.VISIBLE);
                    Timetable_Adapter adapter = new Timetable_Adapter(Time_table.this, R.layout.timetable_list_item, timetable_helpers);
                    timetable_list.setAdapter(adapter);
                    break;
                case -2:
                    timetable_list.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Updated Time Table Not Available",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    timetable_list.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;

                case 0:
                    timetable_list.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Day Wise Time Table not Available",Toast.LENGTH_LONG).show();
                    break;

                case -4:
                    timetable_list.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Not Show more than 10 Lectures",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public String Para(String StudentCode,String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("ClassId", StudentCode);
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
    private class SelectclassDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Time_table.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(Time_table.this, "", "Please Wait...", true);
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
                       // mClass = new HashMap<double, String>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   ClassId=object.getString("ClassId");
                            String   ClassName=object.getString("ClassName");
                           /* double cid=Double.parseDouble(ClassId);
                            mClass.put(cid, ClassName);*/
                            final ClassList_Helper sched = new ClassList_Helper();
                            sched.setClassID(ClassId);
                            sched.setClassName(ClassName);
                            CustomListViewValuesArr.add(sched);
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
           // progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    adapter = new ClassList_Adapter(getApplicationContext(), R.layout.spinner_class_item, CustomListViewValuesArr,res);
                    spinner_class.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_timetable, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_timetable, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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

    private class Routine extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Time_table.this);
        @Override
        protected void onPreExecute() {
          //  progressDialog = ProgressDialog.show(Time_table.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.TIMETABLE_API,Pararoutine(applicationController.getActiveempcode(),applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        JSONArray days = jsonObj.getJSONArray(day_name);
                        timetable_helpers = new ArrayList<Timetable_Helper>();
                        for (int i = 0; i < days.length(); i++) {
                            if(days.length()>10){
                                status=-4;
                            }else{
                            JSONObject object = days.getJSONObject(i);
                            String teachName = object.getString("Class");
                            String SubjectName = object.getString("SubjectName");
                            String PeriodName = object.getString("PeriodName");
                            String FromTime = object.getString("FromTime");
                            String ToTime = object.getString("ToTime");
                            String PeriodType = object.getString("PeriodType");
                            String lect_name= FromTime+" - "+ToTime;
                            image=i;
                            if(PeriodType.equalsIgnoreCase("LunchPeriod")){
                                image=10;
                                imagenumber=false;
                            }else{
                                if(imagenumber==false){
                                    image=i-1;
                                }
                            }
                            Timetable_Helper item = new Timetable_Helper(SubjectName, "Class - "+teachName, lect_name, imageid[image],PeriodType);
                            timetable_helpers.add(item);
                            status=1;
                        }}
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
          //  progressDialog.dismiss();
            switch (s){
                case 1:
                    timetable_list.setVisibility(View.VISIBLE);
                    Timetable_Adapter adapter = new Timetable_Adapter(Time_table.this, R.layout.timetable_list_item, timetable_helpers);
                    timetable_list.setAdapter(adapter);
                    break;
                case -2:
                    Toast.makeText(getApplicationContext(),"Updated Time Table Not Available",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(),"Day Wise Routine not Available",Toast.LENGTH_LONG).show();
                    break;
                case -4:
                    Toast.makeText(getApplicationContext(),"Not Show more than 10 Lectures",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Pararoutine(String StudentCode,String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", StudentCode);
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
}

