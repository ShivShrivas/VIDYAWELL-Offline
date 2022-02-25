package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desai.vatsal.mydynamiccalendar.EventModel;
import com.desai.vatsal.mydynamiccalendar.GetEventListListener;
import com.desai.vatsal.mydynamiccalendar.MyDynamicCalendar;
import com.desai.vatsal.mydynamiccalendar.OnDateClickListener;
import com.desai.vatsal.mydynamiccalendar.OnEventClickListener;
import com.desai.vatsal.mydynamiccalendar.OnWeekDayViewClickListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import vidyawell.infotech.bsn.admin.Const.BuilderManager;
import vidyawell.infotech.bsn.admin.Helpers.Calenderevent;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Admin_School_Calendar extends AppCompatActivity {


    String[] name = {"Holidays List", "Event & School Function List"};
    String[] subtitle = {"Holiday with all information", "Add Event & School Function with all information"};
    ///schooldays-celebration
    private BoomMenuButton bmb;
    private ArrayList<Pair> piecesAndButtons = new ArrayList<>();
    private MyDynamicCalendar myCalendar;
    ApplicationControllerAdmin applicationController;
    String Event,StartDate,EndDate,Activity,addnewDate;
    RelativeLayout coordinatorLayout;
    List<Calenderevent> valSetCalenderevent;
    Calenderevent ITEM;
    List<String> event_data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_school_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.relative_calendar));
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("My School Calendar");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        coordinatorLayout = (RelativeLayout) findViewById(R.id.relative_calendar);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        myCalendar = (MyDynamicCalendar) findViewById(R.id.myCalendar);
        bmb = (BoomMenuButton) findViewById(R.id.bmb_button);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            //  bmb.addBuilder(BuilderManager.getPieceCornerRadiusHamButtonBuilder());

            bmb.addBuilder(new HamButton.Builder().normalText(name[i]).normalImageRes(BuilderManager.getImageResource())
                    .subNormalText(subtitle[i])
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if(index==0){
                                applicationController.setActiviName("H");
                                Intent in=new Intent(getApplicationContext(),HolidayList_Activity.class);
                                startActivity(in);
                            }else{
                                applicationController.setActiviName("E");
                                Intent in=new Intent(getApplicationContext(),HolidayList_Activity.class);
                                startActivity(in);
                            }


                        }
                    }));

                  ///////////////////////////////////////Calender/////////////////////////////////////////////////
        ///{"obj":{"SchoolCode":"20","BranchCode":"10","SessionId":"1","FYId":"1","Action":"3","Month":"10"}}

        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR); // current year
        int mMonth = c.get(java.util.Calendar.MONTH); // current month
        int mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
        myCalendar.setCalendarDate(mDay, (mMonth + 1), mYear);
        myCalendar.showMonthView();
        myCalendar.deleteAllEvent();
        new SchoolcalenderData().execute();

        myCalendar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                //  Toast.makeText(getApplicationContext(),"Change layout",Toast.LENGTH_SHORT).show();
            }
        });


        myCalendar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
              //  Toast.makeText(getApplicationContext(),"Change layout",Toast.LENGTH_SHORT).show();

               /* TextView tv_date=(TextView)view.findViewById(R.id.tv_month_year);
                String Date=tv_date.getText().toString();
                Toast.makeText(getApplicationContext(),Date+"Attt",Toast.LENGTH_SHORT).show();*/
            }
        });


        myCalendar.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
               // Toast.makeText(getApplicationContext(),"onViewAttachedToWindow layout",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onViewDetachedFromWindow(View view) {

              /*  TextView tv_date=(TextView)view.findViewById(R.id.tv_month_year);
                String Date=tv_date.getText().toString();
                Toast.makeText(getApplicationContext(),Date+"Detacccc",Toast.LENGTH_SHORT).show();*/
            }
        });




        myCalendar.setCalendarBackgroundColor("#eeeeee");
//        myCalendar.setCalendarBackgroundColor(R.color.gray);

        myCalendar.setHeaderBackgroundColor("#208ABE");
//        myCalendar.setHeaderBackgroundColor(R.color.black);

        myCalendar.setHeaderTextColor("#ffffff");
//        myCalendar.setHeaderTextColor(R.color.white);

        myCalendar.setNextPreviousIndicatorColor("#ffffff");
//        myCalendar.setNextPreviousIndicatorColor(R.color.black);

        myCalendar.setWeekDayLayoutBackgroundColor("#ffffff");
        myCalendar.isSundayOff(true, "#ffffff", "#FFCF1C1C");


    }
    private void showMonthView() {

        myCalendar.showMonthView();

        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                Log.e("date", String.valueOf(date));
            }

            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });

    }

    private void showMonthViewWithBelowEvents() {

        myCalendar.showMonthViewWithBelowEvents();

        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                Log.e("Holiday", String.valueOf(date));
            }

            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });



    }

    private void showWeekView() {

        myCalendar.showWeekView();

        myCalendar.setOnEventClickListener(new OnEventClickListener() {
            @Override
            public void onClick() {
                Log.e("showWeekView","from setOnEventClickListener onClick");
            }

            @Override
            public void onLongClick() {
                Log.e("showWeekView","from setOnEventClickListener onLongClick");

            }
        });

        myCalendar.setOnWeekDayViewClickListener(new OnWeekDayViewClickListener() {
            @Override
            public void onClick(String date, String time) {
                Log.e("showWeekView", "from setOnWeekDayViewClickListener onClick");
                Log.e("tag", "date:-" + date + " time:-" + time);

            }

            @Override
            public void onLongClick(String date, String time) {
                Log.e("showWeekView", "from setOnWeekDayViewClickListener onLongClick");
                Log.e("tag", "date:-" + date + " time:-" + time);

            }
        });


    }

    private void showDayView() {

        myCalendar.showDayView();

        myCalendar.setOnEventClickListener(new OnEventClickListener() {
            @Override
            public void onClick() {
                Log.e("showDayView", "from setOnEventClickListener onClick");

            }

            @Override
            public void onLongClick() {
                Log.e("showDayView", "from setOnEventClickListener onLongClick");

            }
        });

        myCalendar.setOnWeekDayViewClickListener(new OnWeekDayViewClickListener() {
            @Override
            public void onClick(String date, String time) {
                Log.e("showDayView", "from setOnWeekDayViewClickListener onClick");
                Log.e("tag", "date:-" + date + " time:-" + time);
            }

            @Override
            public void onLongClick(String date, String time) {
                Log.e("showDayView", "from setOnWeekDayViewClickListener onLongClick");
                Log.e("tag", "date:-" + date + " time:-" + time);
            }
        });

    }

    private void showAgendaView() {

        myCalendar.showAgendaView();

        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                Log.e("date", String.valueOf(date));
            }

            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_month:
               // showMonthholidaysView();
                return true;
            case R.id.action_month_with_below_events:
               // showMonthViewWithBelowEvents();
                return true;
           *//* case R.id.action_week:
                showWeekView();
                return true;
            case R.id.action_day:
                showDayView();
                return true;
            case R.id.action_agenda:
                showAgendaView();
                return true;
            case R.id.action_today:
                myCalendar.goToCurrentDate();
                return true;*//*
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showMonthholidaysView() {




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }*/

    private class SchoolcalenderData extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Admin_School_Calendar.this);

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Admin_School_Calendar.this, "", "Please Wait...", true);
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
                             Event=object.getString("Event");
                             StartDate=object.getString("StartDate");
                             EndDate=object.getString("EndDate");
                             Activity=object.getString("Activity");
                             event_data.add(Event+"|"+StartDate+"|"+EndDate+"|"+Activity);

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
                      int intt=event_data.size();
                     for(int i=0;i<event_data.size();i++){
                         String st=event_data.get(i);
                         String temp[]=st.split("\\|");
                         String event=temp[0];
                         String StartD=temp[1];
                         String EndD=temp[2];
                         String Acti=temp[3];
                         if(event.equalsIgnoreCase("Holiday")){
                             try {
                                 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                 Date dateS = sdf.parse(StartD);
                                 Date dateE = sdf.parse(EndD);
                                 if(dateS.getTime()==dateE.getTime()){
                                     myCalendar.addHoliday(StartD);
                                 }else{
                                     if(dateS.getTime()< dateE.getTime()){
                                         Date dateBefore = sdf.parse(StartD);
                                         Date dateAfter = sdf.parse(EndD);
                                         long difference = dateAfter.getTime() - dateBefore.getTime();
                                         int daysBetween = (int)(difference / (1000*60*60*24));
                                         myCalendar.addHoliday(StartD);
                                         addnewDate=StartD;
                                         for(int j=1;j<=daysBetween;j++){
                                             Calendar calendar = Calendar.getInstance();
                                             calendar.setTime(sdf.parse(addnewDate));
                                             calendar.add(Calendar.DAY_OF_MONTH, 1);
                                             addnewDate = sdf.format(calendar.getTime());
                                             myCalendar.addHoliday(addnewDate);
                                         }
                                     }
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         }else{
                             try {
                                 List<Date> dates = new ArrayList<Date>();
                                 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                 Date dateS = sdf.parse(StartD);
                                 Date dateE = sdf.parse(EndD);
                                 if(dateS.getTime()==dateE.getTime()){
                                     myCalendar.addEvent(StartD, "10:00", "5:00", Acti);
                                 }else{
                                     if(dateS.getTime()< dateE.getTime()){
                                         Date dateBefore = sdf.parse(StartD);
                                         Date dateAfter = sdf.parse(EndD);


                                         long interval = 24*1000 * 60 * 60; // 1 hour in millis
                                         long endTime =dateAfter.getTime() ; // create your endtime here, possibly using Calendar or Date
                                         long curTime = dateBefore.getTime();
                                         while (curTime <= endTime) {
                                             dates.add(new Date(curTime));
                                             curTime += interval;
                                         }
                                         SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy");
                                         for(int j=0;j<dates.size();j++){
                                             Date lDate =(Date)dates.get(j);
                                             String ds = sdfnew.format(lDate);
                                             System.out.println(" Date is ..." + ds);
                                             Calendar calendar = Calendar.getInstance();
                                             calendar.setTime(sdf.parse(addnewDate));
                                             calendar.add(Calendar.DAY_OF_MONTH, 1);
                                             addnewDate = sdf.format(calendar.getTime());
                                             myCalendar.addEvent(ds, "10:00", "5:00", Acti);
                                             System.out.println(" Date is new ..." + StartD);
                                         }


                                        /* long difference = dateAfter.getTime() - dateBefore.getTime();
                                         int daysBetween = (int)(difference / (1000*60*60*24));
                                         myCalendar.addEvent(StartD, "10:00", "5:00", Acti);
                                         addnewDate=StartD;
                                         for(int j=1;j<=daysBetween;j++){
                                             Calendar calendar = Calendar.getInstance();
                                             calendar.setTime(sdf.parse(addnewDate));
                                             calendar.add(Calendar.DAY_OF_MONTH, 1);
                                             addnewDate = sdf.format(calendar.getTime());
                                             myCalendar.addEvent(StartD, "10:00", "5:00", Acti);
                                         }*/
                                     }
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         }
                     }

                    showcalendarwithdata();

                    break;
                case -2:
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Calendar not Prepared.Try Again", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Calendar not Prepared.Try Again", Snackbar.LENGTH_LONG);
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
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    private void showcalendarwithdata() {



        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                Log.e("date", String.valueOf(date));
                Log.e("Event", String.valueOf(date));
            }

            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });

        myCalendar.setCalendarBackgroundColor("#eeeeee");
//        myCalendar.setCalendarBackgroundColor(R.color.gray);

        myCalendar.setHeaderBackgroundColor("#208ABE");
//        myCalendar.setHeaderBackgroundColor(R.color.black);

        myCalendar.setHeaderTextColor("#ffffff");
//        myCalendar.setHeaderTextColor(R.color.white);

        myCalendar.setNextPreviousIndicatorColor("#ffffff");
//        myCalendar.setNextPreviousIndicatorColor(R.color.black);

        myCalendar.setWeekDayLayoutBackgroundColor("#ffffff");
//        myCalendar.setWeekDayLayoutBackgroundColor(R.color.black);

        myCalendar.setWeekDayLayoutTextColor("#424142");
//        myCalendar.setWeekDayLayoutTextColor(R.color.black);

        // myCalendar.isSaturdayOff(true, "#ffffff", "#ff0000");
//        myCalendar.isSaturdayOff(true, R.color.white, R.color.red);

        // myCalendar.isSundayOff(true, "#658745", "#254632");
//        myCalendar.isSundayOff(true, R.color.white, R.color.red);

         myCalendar.setExtraDatesOfMonthBackgroundColor("#ffffff");

//        myCalendar.setExtraDatesOfMonthBackgroundColor(R.color.black);

        myCalendar.setExtraDatesOfMonthTextColor("#9D9FA1");

//        myCalendar.setExtraDatesOfMonthTextColor(R.color.black);

//        myCalendar.setDatesOfMonthBackgroundColor(R.drawable.event_view);
        myCalendar.setDatesOfMonthBackgroundColor("#ffffff");
//        myCalendar.setDatesOfMonthBackgroundColor(R.color.black);

        myCalendar.setDatesOfMonthTextColor("#000000");
//        myCalendar.setDatesOfMonthTextColor(R.color.black);

      //  myCalendar.setCurrentDateBackgroundColor("#0b8df1");
//        myCalendar.setCurrentDateBackgroundColor(R.color.black);

      //  myCalendar.setCurrentDateTextColor("#ffffff");
//        myCalendar.setCurrentDateTextColor(R.color.black);

        myCalendar.setEventCellBackgroundColor("#233485");
//        myCalendar.setEventCellBackgroundColor(R.color.black);

        myCalendar.setEventCellTextColor("#ffffff");
//        myCalendar.setEventCellTextColor(R.color.black);

        myCalendar.getEventList(new GetEventListListener() {
            @Override
            public void eventList(ArrayList<EventModel> eventList) {

                Log.e("tag", "eventList.size():-" + eventList.size());
                for (int i = 0; i < eventList.size(); i++) {
                    Log.e("tag", "eventList.getStrName:-" + eventList.get(i).getStrName());
                }

            }
        });



      /*  myCalendar.addEvent("5-9-2018", "8:00", "8:15", "Today Event 1");
        myCalendar.addEvent("05-10-2018", "8:15", "8:30", "Today Event 2");
        myCalendar.addEvent("09-10-2018", "8:30", "8:45", "Today Event 3");
        myCalendar.addEvent("08-11-2018", "8:45", "9:00", "Today Event 4");
        myCalendar.addEvent("21-11-2018", "8:00", "8:30", "Today Event 5");
        myCalendar.addEvent("08-11-2018", "9:00", "10:00", "Today Event 6");*/

       /* myCalendar.getEventList(new GetEventListListener() {
            @Override
            public void eventList(ArrayList<EventModel> eventList) {

                Log.e("tag", "eventList.size():-" + eventList.size());
                for (int i = 0; i < eventList.size(); i++) {
                    Log.e("tag", "eventList.getStrName:-" + eventList.get(i).getStrName());
                }

            }
        });*/

//        myCalendar.updateEvent(0, "5-10-2016", "8:00", "8:15", "Today Event 111111");

//        myCalendar.deleteEvent(2);

//        myCalendar.deleteAllEvent();

        myCalendar.setBelowMonthEventTextColor("#000000");
//        myCalendar.setBelowMonthEventTextColor(R.color.black);

        myCalendar.setBelowMonthEventDividerColor("#635478");
//        myCalendar.setBelowMonthEventDividerColor(R.color.black);

        myCalendar.setHolidayCellBackgroundColor("#FA0509");
//        myCalendar.setHolidayCellBackgroundColor(R.color.black);

        myCalendar.setHolidayCellTextColor("#ffffff");
//        myCalendar.setHolidayCellTextColor(R.color.black);

      /*  myCalendar.setHolidayCellClickable(true);
        myCalendar.addHoliday("21-11-2018");
        myCalendar.addHoliday("19-11-2018");
        myCalendar.addHoliday("12-12-2018");
        myCalendar.addHoliday("13-10-2018");
        myCalendar.addHoliday("8-10-2018");
        myCalendar.addHoliday("10-10-2018");*/
        myCalendar.setHolidayCellClickable(true);





//        myCalendar.setCalendarDate(5, 10, 2016);

        showMonthViewWithBelowEvents();
        // showDayView();
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
        myCalendar.deleteAllEvent();
    }


}