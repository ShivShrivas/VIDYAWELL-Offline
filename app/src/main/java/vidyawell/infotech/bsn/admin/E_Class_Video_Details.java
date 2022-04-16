 package vidyawell.infotech.bsn.admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Live_Video_Details_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Live_Video_Details_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;


 public class E_Class_Video_Details extends AppCompatActivity implements Live_Video_Details_Adapter.customButtonListener,Live_Video_Details_Adapter.customButtonListener1,Live_Video_Details_Adapter.customButtonListener3{
    ListView time_live_details_list;
    FloatingActionButton fab;
    List<Live_Video_Details_Helper> live_video_details_helpers;
    Live_Video_Details_Helper item;
    ApplicationControllerAdmin applicationController;
    Live_Video_Details_Adapter adapter;
    String format;
    TimePickerDialog timepickerdialog;
    Calendar calendar;
    private int CalendarHour, CalendarMinute;
    int selectedHour,selectedMinute;
    String time_set;
    RelativeLayout layout_live_video;
    String TransId;
    LinearLayout layout_cal;
    TextView date_text,Text_Datev,text_monthnamev;
    String trans_id;
     private int mYear, mMonth, mDay, mHour, mMinute;



     @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e__class__video__details);
        applicationController = (ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_live_video));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        SpannableString str = new SpannableString("e Class");
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        layout_cal=(LinearLayout)findViewById(R.id.layout_cal);
        date_text=(TextView)findViewById(R.id.date_text);
        Text_Datev=(TextView)findViewById(R.id.Text_Datev);
        text_monthnamev=(TextView)findViewById(R.id.text_monthnamev);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        time_live_details_list = (ListView)findViewById(R.id.time_live_details_list);
        layout_live_video=(RelativeLayout)findViewById(R.id.layout_live_video);


        if(applicationController.getLoginType().equalsIgnoreCase("4")){
            layout_cal.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }else {
            layout_cal.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);

        }

       /* time_live_details_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView    transection_id = (TextView)view.findViewById(R.id.transection_id);

                trans_id = transection_id.getText().toString();

            }
        });*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(E_Class_Video_Details.this, E_Class_Live_Video.class);
                startActivityForResult(intent, 100);
            }
        });
        new GET_API_LIVE_VIDEO().execute();

{}






       /* calendar = Calendar.getInstance();
        CalendarHour = calendar.get(HOUR_OF_DAY);
        CalendarMinute = calendar.get(MINUTE);

        time_set = (HOUR_OF_DAY + ":" + MINUTE + format);
        date_text.setText(HOUR_OF_DAY + ":" + MINUTE + format);
        Text_Datev.setText(HOUR_OF_DAY + ":" + MINUTE );*/

     /*   if (HOUR_OF_DAY == 0) {

          //  HOUR_OF_DAY += 12;

            format = "AM";
        }
        else if (HOUR_OF_DAY == 12) {

            format = "PM";

        }
        else if (HOUR_OF_DAY > 12) {

         //   HOUR_OF_DAY -= 12;

            format = "PM";

        }
        else {

            format = "AM";
        }*/
      //  text_monthnamev.setText(format);

        layout_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* calendar = Calendar.getInstance();
                CalendarHour = calendar.get(HOUR_OF_DAY);
                CalendarMinute = calendar.get(MINUTE);


                timepickerdialog = new TimePickerDialog(E_Class_Video_Details.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                              //  int hourOfDay=0,minute=0;
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
                              //  textView.setText(String.format(format, time));



                                time_set = (hourOfDay + ":" + minute + format);

                                date_text.setText(hourOfDay + ":" + minute + format);
                                  Text_Datev.setText(hourOfDay + ":" + minute +format );
                                  text_monthnamev.setText(format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
*/

                // Get Current Time
                calendar = Calendar.getInstance();
                mHour = calendar.get(HOUR_OF_DAY);
                mMinute = calendar.get(MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(E_Class_Video_Details.this,
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
    }

    @Override
    public void onButtonClickListner(int position, Live_Video_Details_Helper value) {
       // Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();
       // TextView    transection_id = (TextView)findViewById(R.id.transection_id);
      //  trans_id = transection_id.getText().toString();

        trans_id= value.getTransId();


        time_set = date_text.getText().toString();
        if(date_text.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Select Time",Toast.LENGTH_SHORT).show();

        }else {
            new Timeset_SAVE().execute();

        }

    }

    @Override
    public void onButtonClickListner2(int position, Live_Video_Details_Helper value) {
       // Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();


     /*   calendar = Calendar.getInstance();
        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = calendar.get(Calendar.MINUTE);


        timepickerdialog = new TimePickerDialog(E_Class_Video_Details.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

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
                    }
                }, CalendarHour, CalendarMinute, false);
        timepickerdialog.show();*/

    }

     @Override
     public void onButtonClickListner3(int position, Live_Video_Details_Helper value) {
         trans_id= value.getTransId();
         alert_delete();
     }

     private class GET_API_LIVE_VIDEO extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(E_Class_Video_Details.this);

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(E_Class_Video_Details.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status = 0;
            JsonParser josnparser = new JsonParser(getApplicationContext());
            String response = josnparser.executePost(applicationController.getServicesapplication() + ServerApiadmin.E_CLASS_GET_API, Para(applicationController.getschoolCode(), applicationController.getBranchcode(), applicationController.getSeesionID(),applicationController.getActiveempcode()), "1");
            if (response != null) {
                if (response.length() > 5) {
                    try {
                        JSONArray array = new JSONArray(response);
                        live_video_details_helpers = new ArrayList<Live_Video_Details_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String Title = object.getString("TopicName");
                            String VedioAlbum = object.getString("Description");
                            String VedioUrl = object.getString("VedioPath");
                            String TransId = object.getString("TransId");
                            String IsApproved = object.getString("ClassTime");
                            String IsLiveClassApproval = object.getString("IsLiveClassApproval");//IsLiveClassApproval
                            item = new Live_Video_Details_Helper(Title, VedioAlbum, VedioUrl, TransId, IsApproved,IsLiveClassApproval);
                            live_video_details_helpers.add(item);
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
            progressDialog.dismiss();
            switch (s) {
                case 1:
                     adapter = new Live_Video_Details_Adapter(getApplicationContext(), R.layout.item_live_video_details, live_video_details_helpers);
                    time_live_details_list.setAdapter(adapter);
                    adapter.setCustomButtonListner(E_Class_Video_Details.this);
                    adapter.setCustomButtonListner1(E_Class_Video_Details.this);
                    adapter.setCustomButtonListner3(E_Class_Video_Details.this);

                    break;
                case -2:

                    if(adapter!= null){
                        time_live_details_list.setAdapter(null);
                        adapter.clear();
                    }
                    // Toast.makeText(getApplicationContext(),"e Class video not found",Toast.LENGTH_LONG).show();
                    break;
                case -1:

                    if(adapter!= null){
                        time_live_details_list.setAdapter(null);
                        adapter.clear();
                    }
                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public String Para(String SchoolCode, String BranchCode, String SessionId,String EmployeeId) {
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "4");
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", applicationController.getFyID());
            jsonParam.put("EmployeeId",EmployeeId);

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
          new   GET_API_LIVE_VIDEO().execute();
    }


    //////////////////////////////////////API INSERT SUBMIT////////////////////////
    private class Timeset_SAVE extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(E_Class_Video_Details.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.E_CLASS_INSERT_SUBMIT_API,ParaSub(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");

            String api= applicationController.getServicesapplication();
            //Log.d("request!", response);
            if(response!=null){
                response=response.replace("\"","");
                if (response.length()<3){
                    if(response.equalsIgnoreCase("2")){
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
                    Toast.makeText(getApplicationContext(),"Save video successful",Toast.LENGTH_SHORT).show();
                   new   GET_API_LIVE_VIDEO().execute();
                   // finish();
                    // showSuccessDialog();
                    break;
                case 2:
                    Snackbar snackbar = Snackbar
                            .make(layout_live_video, "Video not submitted. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_live_video, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String ParaSub(String schoolCode, String branchCode, String SessionId, String fyId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "6");
            jsonParam.put("SchoolCode", schoolCode);
            jsonParam.put("BranchCode", branchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",fyId);
            jsonParam.put("TransId",trans_id);
            jsonParam.put("ClassTime",time_set);
            jsonParam.put("IsApproved","1");


            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }



     //////////////////////////////////////API Delete E Class////////////////////////
     private class E_CLASS_LIVE_DELETE_API extends AsyncTask<String, String, Integer> {
         ProgressDialog progressDialog = new ProgressDialog(E_Class_Video_Details.this);
         @Override
         protected void onPreExecute() {
             //  progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
             super.onPreExecute();
         }
         @Override
         protected Integer doInBackground(String... strings) {
             int status=0;
             JsonParser josnparser=new JsonParser(getApplicationContext());
             String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.E_CLASS_INSERT_SUBMIT_API,ParaSubDelete(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");

             String api= applicationController.getServicesapplication();
             //Log.d("request!", response);
             if(response!=null){
                 response=response.replace("\"","");
                 if (response.length()<3){
                     if(response.equalsIgnoreCase("3")){
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
                     new   GET_API_LIVE_VIDEO().execute();
                    // finish();
                     // showSuccessDialog();
                     break;
                 case 2:
                     Snackbar snackbar = Snackbar
                             .make(layout_live_video, "Video not submitted. Please Try Again", Snackbar.LENGTH_LONG)
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
                             .make(layout_live_video, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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

     public String ParaSubDelete(String schoolCode, String branchCode, String SessionId, String fyId){
         JSONObject jsonParam1 = new JSONObject();
         JSONObject jsonParam = new JSONObject();
         try {
             jsonParam.put("Action", "3");
             jsonParam.put("SchoolCode", schoolCode);
             jsonParam.put("BranchCode", branchCode);
             jsonParam.put("SessionId", SessionId);
             jsonParam.put("FYId",fyId);
             jsonParam.put("ClassId","");
             jsonParam.put("SectionId","");
             jsonParam.put("SubjectId","");
             jsonParam.put("BranchId","");
             jsonParam.put("ClassDate","");
             jsonParam.put("EmployeeId","");
             jsonParam.put("TopicName","");
             jsonParam.put("Description","");
             jsonParam.put("VedioPath","");
             jsonParam.put("ClassTime","");
             jsonParam.put("TransId",trans_id);
             jsonParam1.put("obj", jsonParam);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return jsonParam1.toString();
     }




     ////////////////////alert////////
     private void alert_delete(){
         android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
         alertDialogBuilder.setMessage("Do you want to Delete this e Class");
         alertDialogBuilder.setTitle("Warning!");
         alertDialogBuilder.setPositiveButton("YES",
                 new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface arg0, int arg1) {
                         new E_CLASS_LIVE_DELETE_API().execute();
                     }
                 });
         alertDialogBuilder.setNegativeButton("NO",
                 new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface arg0, int arg1) {


                     }
                 });


         final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
         alertDialog.show();
     }

}