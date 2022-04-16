package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Holiday_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Calenderevent;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class HolidayList_Activity extends AppCompatActivity {

    RelativeLayout coordinatorLayout;
    ApplicationControllerAdmin applicationController;
    List<Calenderevent> valSetCalenderevent;
    ListView list_holiday;
    Spinner holiday_spin;
    int mDay;
    SpannableString str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidaylist);
        coordinatorLayout = (RelativeLayout) findViewById(R.id.holiday_layout);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.holiday_layout));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        if(applicationController.getActiviName().equalsIgnoreCase("H")){
            str = new SpannableString("Holidays List");
        }else{
            str = new SpannableString("Events List");
        }
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        list_holiday=(ListView)findViewById(R.id.list_holiday);
        final java.util.Calendar c = java.util.Calendar.getInstance();
        mDay = c.get(Calendar.MONTH);
        holiday_spin=(Spinner)findViewById(R.id.holi_monthspin) ;
        holiday_spin.setSelection(mDay);
        holiday_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDay=position+1;
                new SchoolcalenderData().execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private class SchoolcalenderData extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(HolidayList_Activity.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(HolidayList_Activity.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SCHOOL_CALENDAR,Para("3",applicationController.getBranchcode(),applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        valSetCalenderevent = new ArrayList<Calenderevent>();
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String Event=object.getString("Event");
                            if(applicationController.getActiviName().equalsIgnoreCase("H")){
                                if(Event.equalsIgnoreCase("Holiday")){
                                    String StartDate=object.getString("StartDate");
                                    String EndDate=object.getString("EndDate");
                                    String Activity=object.getString("Activity");
                                    String IsRange=object.getString("IsRange");
                                    Calenderevent item=new Calenderevent(Event,StartDate,EndDate,Activity,IsRange);
                                    valSetCalenderevent.add(item);
                                }
                            }else{
                                if(Event.equalsIgnoreCase("School Day/Celebration")){
                                    String StartDate=object.getString("StartDate");
                                    String EndDate=object.getString("EndDate");
                                    String Activity=object.getString("Activity");
                                    String IsRange=object.getString("IsRange");
                                    Calenderevent item=new Calenderevent(Event,StartDate,EndDate,Activity,IsRange);
                                    valSetCalenderevent.add(item);
                                }
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    list_holiday.setVisibility(View.VISIBLE);
                    Holiday_Adapter adapter = new Holiday_Adapter(HolidayList_Activity.this,R.layout.holiday_listitem_layout, valSetCalenderevent);
                    list_holiday.setAdapter(adapter);
                    break;
                case -2:
                    list_holiday.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "No Holiday in this month.Try Again", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    break;
                case -1:
                    list_holiday.setVisibility(View.GONE);
                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "No Holiday in this month.Try Again", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                    break;
            }
        }
    }

    public String Para(String action,String BranchCode,String SchoolCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("Month",mDay);
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
