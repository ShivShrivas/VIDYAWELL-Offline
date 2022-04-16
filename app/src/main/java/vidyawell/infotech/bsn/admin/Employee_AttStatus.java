package vidyawell.infotech.bsn.admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vidyawell.infotech.bsn.admin.Calendar.CaldroidFragment;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

@SuppressLint("SimpleDateFormat")
public class Employee_AttStatus extends AppCompatActivity {
    ApplicationControllerAdmin applicationController;
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    LinearLayout layout_staffAtt;
    int mday;
    String AttendanceDate,AbbrType,save_month="1";
    String selcted_date,selcted_month, absent_Date,present_Date;
    List<String> data_Att = new ArrayList<>();
    ArrayList<String> data_Attdisable = new ArrayList<>();
    BarChart chart;
    ArrayList<String> xAxis = new ArrayList<>();
    ArrayList<BarDataSet> dataSets = null;
    ArrayList<BarEntry> valueSet1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__att_status);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.empatt_status));
        layout_staffAtt=(LinearLayout)findViewById(R.id.empatt_status);
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Attendance Status");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR); // current year
        int mMonth = c.get(java.util.Calendar.MONTH);
        mMonth=mMonth+1;
        mday = c.get(java.util.Calendar.DAY_OF_MONTH);
        selcted_date=mYear+"-"+mMonth+"-"+mday;
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        caldroidFragment = new CaldroidFragment();
        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            caldroidFragment.setArguments(args);
        }

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();
       // getXAxisValues();
        new EmpAttData().execute();
        chart = (BarChart) findViewById(R.id.chart_att);
        new EmpAttDataGraph().execute();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }


    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(100.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        BarEntry v1e7 = new BarEntry(80.000f, 6); // Jun
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry(80.000f, 7); // Jun
        valueSet1.add(v1e8);
        BarEntry v1e9 = new BarEntry(85.000f, 8); // Jun
        valueSet1.add(v1e9);
        BarEntry v1e10 = new BarEntry(10.000f, 9); // Jun
        valueSet1.add(v1e10);
        BarEntry v1e11 = new BarEntry(50.000f, 10); // Jun
        valueSet1.add(v1e11);
        BarEntry v1e12 = new BarEntry(100.000f, 11); // Jun
        valueSet1.add(v1e12);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Month wise Attendance %");
       // barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.PASTEL_COLORS);
       /* BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);*/

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
       // dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
       // ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Jan");
        xAxis.add("Feb");
        xAxis.add("Mar");
        xAxis.add("Apr");
        xAxis.add("May");
        xAxis.add("Jun");
        xAxis.add("Jul");
        xAxis.add("Aug");
        xAxis.add("Sep");
        xAxis.add("Oct");
        xAxis.add("Nov");
        xAxis.add("Dec");


        return xAxis;
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
    //////////////////////////////////////////Employee Att Month wise////////
    private class EmpAttData extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Employee_AttStatus.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Employee_AttStatus.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
             JsonParser josnparser=new JsonParser(getApplicationContext());
             String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.ATTENDANCE_EMPLOYEEMONTH,ParaAtt(applicationController.getSeesionID(),applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getFyID(),applicationController.getActiveempcode(),selcted_date,"13"),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String AttendanceDate=object.getString("AttendanceDate");
                            String AbbrType=object.getString("AbbrType");
                            data_Att.add(AttendanceDate + "," + AbbrType);
                            data_Attdisable.add(AttendanceDate);
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    final Handler handler = new Handler();
                    handler.post( new Runnable(){
                          public void run() {
                            for (int i = 0; i < data_Att.size(); i++) {
                                String st = data_Att.get(i);
                                String temp[] = st.split(",");
                                String AttendanceDate = temp[0];
                                AbbrType = temp[1];
                                if (AbbrType.equalsIgnoreCase("Absent")) {
                                    AbbrType = "1";
                                    absent_Date = AttendanceDate;
                                } else  if (AbbrType.equalsIgnoreCase("Not Taken")) {
                                    AbbrType = "2";
                                    absent_Date = AttendanceDate;

                                } else  if (AbbrType.equalsIgnoreCase("Leave")) {
                                    AbbrType = "3";
                                    absent_Date = AttendanceDate;

                                }else {
                                    AbbrType = "0";
                                    absent_Date = AttendanceDate;
                                }
                                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar cal = Calendar.getInstance();
                                Date blueDate1 = null;
                                try {
                                    blueDate1 = df.parse(absent_Date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                cal.add(Calendar.DATE, -7);
                                if (caldroidFragment != null) {
                                    if (AbbrType.equals("1")) {
                                        ColorDrawable blue1 = new ColorDrawable(Color.RED);
                                        caldroidFragment.setBackgroundDrawableForDate(blue1, blueDate1);
                                        caldroidFragment.setTextColorForDate(R.color.white, blueDate1);
                                    } else if (AbbrType.equals("2")) {
                                        ColorDrawable blue1 = new ColorDrawable(Color.parseColor("#f77445"));
                                        caldroidFragment.setBackgroundDrawableForDate(blue1, blueDate1);
                                        caldroidFragment.setTextColorForDate(R.color.white, blueDate1);
                                    } else if (AbbrType.equals("3")) {
                                        ColorDrawable blue1 = new ColorDrawable(Color.parseColor("#e8dc27"));
                                        caldroidFragment.setBackgroundDrawableForDate(blue1, blueDate1);
                                        caldroidFragment.setTextColorForDate(R.color.white, blueDate1);
                                    }else {
                                        ColorDrawable green1 = new ColorDrawable(Color.GREEN);
                                        caldroidFragment.setBackgroundDrawableForDate(green1, blueDate1);
                                        caldroidFragment.setTextColorForDate(R.color.white, blueDate1);
                                    }
                                }
                               // handler.postDelayed(this, 5);
                            }
                              caldroidFragment.refreshView();
                              caldroidFragment.setShowNavigationArrows(false);
                              caldroidFragment.setEnableSwipe(false);

                        }
                    });
                    break;
                case -2:
                    caldroidFragment.refreshView();
                    caldroidFragment.setShowNavigationArrows(false);
                    caldroidFragment.setEnableSwipe(false);
                    break;
                case -1:
                    caldroidFragment.refreshView();
                    caldroidFragment.setShowNavigationArrows(false);
                    caldroidFragment.setEnableSwipe(false);
                    break;
            }
        }
    }


    public String ParaAtt(String SessionId, String SchoolCode, String BranchCode, String FYId, String StudentCode, String AttendanceDate, String Action){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("FYId", FYId);
            jsonParam.put("EmployeeCode",StudentCode);
            jsonParam.put("AttendanceDate",AttendanceDate);
            jsonParam.put("Action",Action);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    //////////////////////////////////////////// GRAPH////////////////////////////////////
    private class EmpAttDataGraph extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Employee_AttStatus.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Employee_AttStatus.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            float attper =0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.ATTENDANCE_EMPLOYEEMONTH,ParaAttgraph(applicationController.getSeesionID(),applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getFyID(),applicationController.getActiveempcode(),selcted_date,"14"),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String Month=object.getString("Month");
                            String Present=object.getString("Present");
                            Month=Month.substring(0,3);
                          //  getDataSetapi(Present,Month,i);
                           // Present="60";
                            Log.d("PRESENT",Present);
                            if(Present.equalsIgnoreCase("null")){
                                attper=0;
                            }else{
                                attper=Float.parseFloat(Present+".000f");
                                Log.d("attper",attper+"");
                            }
                            if(Month.equalsIgnoreCase("Jan")){
                                BarEntry v1e1 = new BarEntry(attper, 0); // Jan
                                valueSet1.add(v1e1);
                            }else if(Month.equalsIgnoreCase("Feb")){
                                BarEntry v1e2 = new BarEntry(attper, 1); // Feb
                                valueSet1.add(v1e2);
                            }else if(Month.equalsIgnoreCase("Mar")){
                                BarEntry v1e3 = new BarEntry(attper, 2); // Mar
                                valueSet1.add(v1e3);
                            }else if(Month.equalsIgnoreCase("Apr")){
                                BarEntry v1e4 = new BarEntry(attper, 3); // Apr
                                valueSet1.add(v1e4);
                            }else if(Month.equalsIgnoreCase("May")){
                                BarEntry v1e5 = new BarEntry(attper, 4); // May
                                valueSet1.add(v1e5);
                            }else if(Month.equalsIgnoreCase("Jun")){
                                BarEntry v1e6 = new BarEntry(attper, 5); // Jun
                                valueSet1.add(v1e6);
                            }else if(Month.equalsIgnoreCase("Jul")){
                                BarEntry v1e7 = new BarEntry(attper, 6); // Jul
                                valueSet1.add(v1e7);
                            }else if(Month.equalsIgnoreCase("Aug")){
                                BarEntry v1e8 = new BarEntry(attper, 7); // Aug
                                valueSet1.add(v1e8);
                            }else if(Month.equalsIgnoreCase("Sep")){
                                BarEntry v1e9 = new BarEntry(attper, 8); // Sep
                                valueSet1.add(v1e9);
                            }else if(Month.equalsIgnoreCase("Oct")){
                                BarEntry v1e10 = new BarEntry(attper, 9); // Oct
                                valueSet1.add(v1e10);
                            }else if(Month.equalsIgnoreCase("Nov")){
                                BarEntry v1e11 = new BarEntry(attper, 10); // Nov
                                valueSet1.add(v1e11);
                            }else if(Month.equalsIgnoreCase("Dec")){
                                BarEntry v1e12 = new BarEntry(attper, 11); // Dec
                                valueSet1.add(v1e12);
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Month wise Attendance %");
                    barDataSet1.setColors(ColorTemplate.PASTEL_COLORS);
                    dataSets = new ArrayList<>();
                    dataSets.add(barDataSet1);
                    BarData data = new BarData(getXAxisValues(), dataSets);
                    chart.setData(data);
                    chart.setDescription("Attendance Chart");
                    chart.animateXY(4000, 4000);
                    chart.invalidate();
                    break;
                case -2:

                    break;
                case -1:

                    break;
            }
        }
    }
    public String ParaAttgraph(String SessionId, String SchoolCode, String BranchCode, String FYId, String EmployeeCode, String AttendanceDate, String Action){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("FYId", FYId);
            jsonParam.put("EmployeeCode",EmployeeCode);
            jsonParam.put("AttendanceDate",AttendanceDate);
            jsonParam.put("Action",Action);
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    private void getDataSetapi(String att,String month,int count) {
        float attper =0;

        if(att.equalsIgnoreCase("null")){
            attper=0;
        }else{
            attper=Float.parseFloat(att+".000f");
        }
        String i= xAxis.get(0);
        System.out.print(xAxis.get(count)+"////////////"+attper);

        if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e1 = new BarEntry(attper, 0); // Jan
            valueSet1.add(v1e1);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e2 = new BarEntry(attper, 1); // Feb
            valueSet1.add(v1e2);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e3 = new BarEntry(attper, 2); // Mar
            valueSet1.add(v1e3);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e4 = new BarEntry(attper, 3); // Apr
            valueSet1.add(v1e4);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e5 = new BarEntry(attper, 4); // May
            valueSet1.add(v1e5);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e6 = new BarEntry(attper, 5); // Jun
            valueSet1.add(v1e6);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e7 = new BarEntry(attper, 6); // Jul
            valueSet1.add(v1e7);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e8 = new BarEntry(attper, 7); // Aug
            valueSet1.add(v1e8);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e9 = new BarEntry(attper, 8); // Sep
            valueSet1.add(v1e9);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e10 = new BarEntry(attper, 9); // Oct
            valueSet1.add(v1e10);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e11 = new BarEntry(attper, 10); // Nov
            valueSet1.add(v1e11);
        }else if(month.equalsIgnoreCase(xAxis.get(count))){
            BarEntry v1e12 = new BarEntry(attper, 11); // Dec
            valueSet1.add(v1e12);
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Month wise Attendance %");
        // barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.PASTEL_COLORS);
       /* BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);*/

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        // dataSets.add(barDataSet2);
       // return dataSets;
    }




}
