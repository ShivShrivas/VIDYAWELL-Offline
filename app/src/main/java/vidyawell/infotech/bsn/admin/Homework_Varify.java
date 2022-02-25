package vidyawell.infotech.bsn.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Checked_Unchecked_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.HomeworkDetails_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.SectionList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Topic_Selection_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Checked_Unchecked_Helper;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.HomeworkDetails_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Homework_Topic_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Topic_Selction_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static vidyawell.infotech.bsn.admin.MainActivity_Admin.theMonth;

public class Homework_Varify extends AppCompatActivity implements HomeworkDetails_Adapter.customButtonListener,HomeworkDetails_Adapter.customButtonListener1   {
    ApplicationControllerAdmin applicationController;
    Typeface typeface;
    DatePickerDialog datePickerDialog;
    TextView tv_date,tv_date_2,tv_date_3,tv_month,tv_month_2,tv_month_3;
    TextView date_text,date_text_2,date_text_3;

    String from_Date,todate_homework;
    Spinner select_class,select_section,select_homework_topic;
    String class_ID,section_ID,homework_topic_ID;
    RelativeLayout layout_varified;
    HomeworkDetails_Adapter adapter;
    public ArrayList<ClassList_Helper> CustomListViewValuesArr = new ArrayList<ClassList_Helper>();
    public  ArrayList<SectionList_Helper> CustomListSection= new ArrayList<SectionList_Helper>();
    public  ArrayList<Topic_Selction_Helper> CustomListSectiontopic= new ArrayList<Topic_Selction_Helper>();
    List<HomeworkDetails_Helper> homeworkDetails_helper;
    HomeworkDetails_Helper item;
    ListView list_homeworkdetails;
    AppCompatButton search_list_topic;
    String trans_id,stu_code,topic_name,remark_click,voucher,IsSubmitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework__varify);
        applicationController=(ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_varified));
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Verify Homework");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
       // tv_date=(TextView)findViewById(R.id.Text_Datev);
        tv_date_2=(TextView)findViewById(R.id.Text_Datev_2);
       tv_date_3=(TextView)findViewById(R.id.Text_Datev_3);
      //  tv_month=(TextView)findViewById(R.id.text_monthnamev);
        tv_month_2=(TextView)findViewById(R.id.text_monthnamev_2);
        tv_month_3=(TextView)findViewById(R.id.text_monthnamev_3);

       // date_text=(TextView)findViewById(R.id.date_text);
        date_text_2=(TextView)findViewById(R.id.date_text_2);
       date_text_3=(TextView)findViewById(R.id.date_text_3);
        layout_varified=(RelativeLayout)findViewById(R.id.layout_varified);
        select_class=findViewById(R.id.select_classd);
        select_section=findViewById(R.id.select_sectiond);
        select_homework_topic=findViewById(R.id.select_homework_topic);
        list_homeworkdetails=(ListView)findViewById(R.id.list_homeworkdetails);
        search_list_topic=(AppCompatButton)findViewById(R.id.search_list_topic);


        /////////////current date set///////////
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);
       // tv_list.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        //date_text.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        date_text_2.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
       date_text_3.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        String month=theMonth(mDay);
       // tv_date.setText(cDay+"");
        tv_date_2.setText(cDay+"");
        tv_date_3.setText(cDay+"");
        //tv_month.setText(month);
        tv_month_2.setText(month);
        tv_month_3.setText(month);
        from_Date=mYear+ "-"+ (mDay + 1) + "-" + cDay;//// string name todayssate ... homeworkdate
       // currentdate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        todate_homework=mYear+ "-"+ (mDay + 1) + "-" + cDay;
       // end_date_homework=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        //todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(from_Date);
            SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
            from_Date= formt.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }


        SimpleDateFormat formatter_2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date_2 = formatter_2.parse(todate_homework);
            SimpleDateFormat formt_2 = new SimpleDateFormat("yyyy-MM-dd");
            todate_homework= formt_2.format(date_2);
        }catch (ParseException e) {
            e.printStackTrace();
        }
       // tv_month.setTypeface(typeface);
        tv_month_2.setTypeface(typeface);
        tv_month_3.setTypeface(typeface);
       ///tv_date.setTypeface(typeface);
        tv_date_2.setTypeface(typeface);
        tv_date_3.setTypeface(typeface);
      /*  SimpleDateFormat formatter_3 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date_3 = formatter_3.parse(end_date_homework);
            SimpleDateFormat formt_3 = new SimpleDateFormat("yyyy-MM-dd");
            end_date_homework= formt_3.format(date_3);
        }catch (ParseException e) {
            e.printStackTrace();
        }*/


        search_list_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Homework_Topic_GETAPI().execute();
            }
        });




        LinearLayout layout_calendar=(LinearLayout)findViewById(R.id.layout_from_date);
        layout_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Homework_Varify.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date_2.setText(dayOfMonth+"");
                                tv_month_2.setText(month);
                                from_Date=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                date_text_2.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = formatter.parse(from_Date);
                                    SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
                                    from_Date= formt.format(date);
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

        LinearLayout layout_calendar_2=(LinearLayout)findViewById(R.id.layout_to_date);
        layout_calendar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                final int mYear = c.get(java.util.Calendar.YEAR);
                final int mDay = c.get(Calendar.MONTH);
                final int cDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Homework_Varify.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date_3.setText(dayOfMonth+"");
                                tv_month_3.setText(month);
                                todate_homework=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                date_text_3.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);

                                SimpleDateFormat formatter_2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date_2 = formatter_2.parse(todate_homework);
                                    SimpleDateFormat formt_2 = new SimpleDateFormat("yyyy-MM-dd");
                                    todate_homework= formt_2.format(date_2);
                                }catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                new SelectTopic_Homework().execute();
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
        new SelectclassDetails().execute();
       // new SelectTopic_Homework().execute();
        select_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                class_ID    = ((TextView) view.findViewById(R.id.text_cID)).getText().toString();
                new SelectSection().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        select_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                section_ID    = ((TextView) view.findViewById(R.id.text_sID)).getText().toString();
                new SelectTopic_Homework().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        select_homework_topic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homework_topic_ID    = ((TextView) view.findViewById(R.id.text_topic_name)).getText().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
    public void onButtonClickListner(int position, HomeworkDetails_Helper value) {
        trans_id = value.getTransId();
        stu_code = value.getStudentCode();
        topic_name= value.getTopicName();
        voucher= value.getVoucherNo();
        IsSubmitted= value.getIsSubmitted();

        Intent intent = new Intent(Homework_Varify.this,DownloadImageHomework.class);
        intent.putExtra("TransId",trans_id);
        intent.putExtra("StudentCode",stu_code);
        intent.putExtra("TopicName",topic_name);
        intent.putExtra("VoucherNo",voucher);
        intent.putExtra("IsSubmitted",IsSubmitted);
        startActivityForResult(intent,100);
    }

    @Override
    public void onButtonClickListner1(int position, HomeworkDetails_Helper value) {
        remark_click = value.getRemark();
        showdialogRemark_Details();


    }


    ////////////////////////////////////////CLASSES//////////////////////////////////////////////////////////
    private class SelectclassDetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Homework_Varify.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Homework_Varify.this, "", "Please Wait...", true);
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
                    ClassList_Adapter       adapter = new ClassList_Adapter(getApplicationContext(), R.layout.spinner_class_item, CustomListViewValuesArr,res);
                    select_class.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_varified, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_varified, "Data not Found. Please Try Again", Snackbar.LENGTH_LONG)
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
        ProgressDialog progressDialog = new ProgressDialog(Homework_Varify.this);
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
                            .make(layout_varified, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_varified, "Data Not Found. Please Try Again", Snackbar.LENGTH_LONG)
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


    ///////////////////////////////////////////Topic Selection//////////////////////////////////
    private class SelectTopic_Homework extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Homework_Varify.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ ServerApiadmin.HOMEWORK_TOPIC_API,Para(applicationController.getActiveempcode(),applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            String api=applicationController.getServicesapplication();
            if(response!=null){
                if (response.length()>5){
                    try {
                        CustomListSectiontopic = new ArrayList<Topic_Selction_Helper>();
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            //String   SectionId=object.getString("TopicId");
                            String   SectionName=object.getString("TopicName");
                            final Topic_Selction_Helper sched = new Topic_Selction_Helper();
                           // sched.setSecID(SectionId);
                            sched.setTopicname(SectionName);
                            CustomListSectiontopic.add(sched);
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
                    Topic_Selection_Adapter adapter = new Topic_Selection_Adapter(getApplicationContext(), R.layout.spinner_homework_topic_item, CustomListSectiontopic,res);
                    select_homework_topic.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_varified, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_varified, "This Date Homework Topic Not Found. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Para(String EmployeeCode,String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", EmployeeCode);
            jsonParam.put("schoolCode", SchoolCode);
            jsonParam.put("branchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("SectionId",section_ID);
            jsonParam.put("ClassId", class_ID);
            jsonParam.put("fyId", applicationController.getFyID());
            jsonParam.put("FromDate",from_Date);
            jsonParam.put("ToDate", todate_homework);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    //////////////homework details api /////////////////////////



    private class Homework_Topic_GETAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Homework_Varify.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Homework_Varify.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.HOMEWORK_TOPIC_Details_API,Para(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            String api=ServerApiadmin.HOMEWORK_TOPIC_Details_API;
            String test = applicationController.getServicesapplication();
            if(response!=null){
                // response=response.replace("\r","");
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        homeworkDetails_helper = new ArrayList<HomeworkDetails_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   SendNotification=object.getString("SendNotification");
                            String   StudentCode=object.getString("StudentCode");
                            String   StudentName=object.getString("StudentName");
                            String   TopicName=object.getString("TopicName");
                            String   SubmissionDate=object.getString("SubmissionDate");
                            String  IsSubmitted=object.getString("IsSubmitted");
                            String  TransId=object.getString("TransId");
                            String  IsHomeworkSubmitted=object.getString("IsHomeworkSubmitted");
                            String  Remark=object.getString("Remark");
                            String  VoucherNo=object.getString("VoucherNo");
                            String  FatherName=object.getString("FatherName");
                            String  StudentId=object.getString("StudentId");

                            item = new HomeworkDetails_Helper(SendNotification,StudentCode,StudentName,TopicName,SubmissionDate,IsSubmitted,TransId,IsHomeworkSubmitted,Remark,VoucherNo,FatherName,StudentId);
                            homeworkDetails_helper.add(item);
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
                    //  homework_topic_list=(ListView)findViewById(R.id.homework_topic_list);
                    //teachers_list.setVisibility(View.VISIBLE);
                    adapter = new HomeworkDetails_Adapter(getApplicationContext(),R.layout.item_homeworkdetails, homeworkDetails_helper);
                    list_homeworkdetails.setAdapter(adapter);
                      adapter.setCustomButtonListner(Homework_Varify.this);
                      adapter.setCustomButtonListner1(Homework_Varify.this);



                    break;
                case -2:
                    if(adapter !=null){
                        adapter.clear();
                    }
                    // toast_message="Student Query Data Not Found. Please Try Again";
                    // showErrorDialog();
                    Snackbar snackbar = Snackbar
                            .make(layout_varified, "Data Not Found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    //Toast.makeText(getApplicationContext(),"Student Query Data Not Found. Please Try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    if(adapter !=null){
                        adapter.clear();
                    }
                    // toast_message="Network Congestion! Please try Again";
                    // showErrorDialog();
                    Snackbar snackbar1 = Snackbar
                            .make(layout_varified, "Data Not Found ", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    //teachers_list.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String Para(String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", applicationController.getActiveempcode());
            jsonParam.put("schoolCode", SchoolCode);
            jsonParam.put("branchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("SectionId",section_ID);
            jsonParam.put("ClassId", class_ID);
            jsonParam.put("TopicName", homework_topic_ID);
            jsonParam.put("fyId", applicationController.getFyID());
            jsonParam.put("Action","28");
            jsonParam1.put("obj", jsonParam);
//SectionId,ClassId,TopicName,EmployeeCode,schoolCode,SessionId,branchCode,fyId
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    /////////////////////dailog box ///////////////////

    private void showdialogRemark_Details() {
        final Dialog dialog = new Dialog(Homework_Varify.this);
        //  R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
        dialog.setContentView(R.layout.dialog_remark_details);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)dialog.findViewById(R.id.dialog_layout_vedio));
        dialog.setCanceledOnTouchOutside(false);

        final TextView txt_write_details=(TextView)dialog.findViewById(R.id.txt_write_details);
        TextView   button_cancel=(TextView)dialog.findViewById(R.id.button_cancel);

        txt_write_details.setText(remark_click);

        dialog.show();
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         new  Homework_Topic_GETAPI().execute();
    }
}
