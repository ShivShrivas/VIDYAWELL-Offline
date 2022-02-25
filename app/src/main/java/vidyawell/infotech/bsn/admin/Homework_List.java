package vidyawell.infotech.bsn.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

import vidyawell.infotech.bsn.admin.Adapters.Homework_Insert_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Homework_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static vidyawell.infotech.bsn.admin.MainActivity_Admin.theMonth;
import static vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin.MAIN_IPLINK;

public class Homework_List extends AppCompatActivity {
    FloatingActionButton fab;
    ApplicationControllerAdmin applicationController;
    ListView list_womework;
    List<Homework_Helper> homework_helpers;
    TextView tv_date,tv_month;
    DatePickerDialog datePickerDialog;
    String todaysDate;
    String currentdate;
    TextView date_text;
    Typeface typeface;
    RelativeLayout layout_homework_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework__list);
        applicationController = (ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_homework_list));
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        SpannableString str = new SpannableString("Homework List");
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        list_womework=findViewById(R.id.list_womework);
        tv_date=(TextView)findViewById(R.id.Text_Datev) ;
        tv_month=(TextView)findViewById(R.id.text_monthnamev) ;
        date_text=(TextView)findViewById(R.id.date_text) ;
        layout_homework_list=(RelativeLayout)findViewById(R.id.layout_homework_list);

        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);
        // tv_list.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        date_text.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        String month=theMonth(mDay);
        tv_date.setText(cDay+"");
        tv_month.setText(month);
        todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        currentdate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        //todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(todaysDate);
            SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
            todaysDate= formt.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
       /* if(String.valueOf(mDay).length()==1){
            todaysDate=mYear+ "-0"+ (mDay + 1) + "-0" + cDay;
        }else{
            todaysDate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        }*/
        //Toast.makeText(getApplicationContext(),applicationController.getActiveempcode(),Toast.LENGTH_LONG).show();

        //////////////////new date select calender/////////////////////

        tv_month.setTypeface(typeface);
        tv_date.setTypeface(typeface);
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
                datePickerDialog = new DatePickerDialog(Homework_List.this,
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

                                new HomeworkGETAPI().execute();

                            }
                        }, mYear, mDay, cDay);
                // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Homework_List.this, Dialy_Diary.class);
                startActivityForResult(intent, 100);
            }
        });

        new HomeworkGETAPI().execute();


    }


    ////////////////////////////////////////////////API Homework Get api details //////////////////////////////////////
    private class HomeworkGETAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Homework_List.this);
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
                            .make(layout_homework_list, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new HomeworkGETAPI().execute();
    }
}
