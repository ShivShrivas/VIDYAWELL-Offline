package vidyawell.infotech.bsn.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import vidyawell.infotech.bsn.admin.Adapters.Visitor_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Visitor_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static vidyawell.infotech.bsn.admin.MainActivity_Admin.theMonth;

public class Visiter_Activity extends AppCompatActivity {

    ListView list;
    ApplicationControllerAdmin applicationController;
    LinearLayout layout_visit;
    String TransId,photo,VisitorName,Purpose,Purposes,HOWUKNOWs,AnyReference,OrganizationName,Address,VisitTypes,EmailId,PhoneNo,TotalPassMember,OfficeEmail;
    List<Visitor_Helper> visitor_helpers;
    Visitor_Helper item;
     TextView tv_date,tv_month,date_text;
    DatePickerDialog datePickerDialog;
    String selecte_ddate;
    String currentdate;
    Typeface typeface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiterlist_layout);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout) findViewById(R.id.layout_visit));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Visitors List");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
        layout_visit=(LinearLayout)findViewById(R.id.layout_visit);
        list=(ListView)findViewById(R.id.visiter_list);
        tv_date=(TextView)findViewById(R.id.Text_Datev) ;
        tv_month=(TextView)findViewById(R.id.text_monthnamev) ;
        date_text=(TextView)findViewById(R.id.date_text) ;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView v_name=(TextView)view.findViewById(R.id.visiter_names) ;
                TextView status=(TextView)view.findViewById(R.id.visitor_status) ;
                TextView TransId=(TextView)view.findViewById(R.id.text_vistorcode) ;
                TextView photo=(TextView)view.findViewById(R.id.visitor_image) ;
                TextView Purposes=(TextView)view.findViewById(R.id.visiter_purpose) ;
                TextView HOWUKNOWs=(TextView)view.findViewById(R.id.HOWUKNOWs) ;
                TextView AnyReference=(TextView)view.findViewById(R.id.AnyReference) ;
                TextView OrganizationName=(TextView)view.findViewById(R.id.visiter_company) ;
                TextView Address=(TextView)view.findViewById(R.id.V_address) ;
                TextView VisitTypes=(TextView)view.findViewById(R.id.VisitTypes) ;
                TextView EmailId=(TextView)view.findViewById(R.id.EmailId) ;
                TextView PhoneNo=(TextView)view.findViewById(R.id.visiter_mobile) ;
                TextView TotalPassMember=(TextView)view.findViewById(R.id.TotalPassMember) ;
                TextView OfficeEmail=(TextView)view.findViewById(R.id.OfficeEmail) ;

                Intent intent=new Intent(getApplicationContext(),Visitors_Details_Activity.class);
                intent.putExtra("v_name",v_name.getText().toString());
                intent.putExtra("status",status.getText().toString());
                intent.putExtra("TransId",TransId.getText().toString());
                intent.putExtra("photo",photo.getText().toString());
                intent.putExtra("Purposes",Purposes.getText().toString());
                intent.putExtra("HOWUKNOWs",HOWUKNOWs.getText().toString());
                intent.putExtra("AnyReference",AnyReference.getText().toString());
                intent.putExtra("OrganizationName",OrganizationName.getText().toString());
                intent.putExtra("Address",Address.getText().toString());
                intent.putExtra("VisitTypes",VisitTypes.getText().toString());
                intent.putExtra("EmailId",EmailId.getText().toString());
                intent.putExtra("PhoneNo",PhoneNo.getText().toString());
                intent.putExtra("TotalPassMember",TotalPassMember.getText().toString());
                intent.putExtra("OfficeEmail",OfficeEmail.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date fdate = sdf.parse(currentdate);
                    Date todate = sdf.parse(selecte_ddate);
                    if(fdate.compareTo(todate)<=0){
                        startActivityForResult(intent,100);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }



            }
        });

        Typeface typeFace = Typeface.createFromAsset(getAssets(),
                "fonts/" + ServerApiadmin.FONT);
        tv_month.setTypeface(typeFace);
        tv_date.setTypeface(typeFace);
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mDay = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);
        String month=theMonth(mDay);
        tv_date.setText(cDay+"");
        tv_month.setText(month);
        selecte_ddate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        currentdate=mYear+ "-"+ (mDay + 1) + "-" + cDay;
        date_text.setText(cDay+ " - "+ (mDay + 1) + " - " + mYear);
        LinearLayout layout_calendar=(LinearLayout)findViewById(R.id.layout_cal);
        layout_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Visiter_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String month=theMonth(monthOfYear);
                                tv_date.setText(dayOfMonth+"");
                                tv_month.setText(month);
                                selecte_ddate=year+ "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                                date_text.setText(dayOfMonth+ " - "+ (monthOfYear + 1) + " - " + year);
                                new Visitordata().execute();

                            }
                        }, mYear, mMonth, mDay);
               // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        new Visitordata().execute();
    }

    private void showSuccessDialog() {

        final Dialog dialog = new Dialog(Visiter_Activity.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.success_dialog);
        dialog.setTitle("");
        // set the custom dialog components - text, image and button
        TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        text_label.setText("Message Sent Successfully");
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

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new Visitordata().execute();
    }
    private class Visitordata extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Visiter_Activity.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Visiter_Activity.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.VISITOR_API,Para(applicationController.getBranchcode(),applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        visitor_helpers = new ArrayList<Visitor_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobject =array.getJSONObject(i);
                            TransId=jsonobject.getString("TransId");
                            photo=jsonobject.getString("photo");
                            VisitorName=jsonobject.getString("VisitorName");
                            Purposes=jsonobject.getString("Purposes");
                            HOWUKNOWs=jsonobject.getString("HOWUKNOWs");
                            AnyReference=jsonobject.getString("AnyReference");
                            OrganizationName=jsonobject.getString("OrganizationName");
                            Address=jsonobject.getString("Address");
                            VisitTypes=jsonobject.getString("VisitTypes");
                            EmailId=jsonobject.getString("EmailId");
                            PhoneNo=jsonobject.getString("PhoneNo");
                            TotalPassMember=jsonobject.getString("TotalPassMember");
                            OfficeEmail=jsonobject.getString("OfficeEmail");
                            String res=jsonobject.getString("Response");
                            item = new Visitor_Helper(res,TransId,photo,VisitorName,Purposes,HOWUKNOWs,AnyReference,OrganizationName,Address,VisitTypes,EmailId,PhoneNo,TotalPassMember,OfficeEmail);
                            visitor_helpers.add(item);
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
                   // Toast.makeText(getApplicationContext(),applicationController.getActiveempcode(),Toast.LENGTH_LONG).show();
                    list.setVisibility(View.VISIBLE);
                    Visitor_Adapter adapter = new Visitor_Adapter(Visiter_Activity.this,R.layout.vister_list_item, visitor_helpers);
                    list.setAdapter(adapter);
                    break;
                case -2:
                    list.setVisibility(View.GONE);
                    Snackbar snackbar1 = Snackbar
                            .make(layout_visit, "No Appointment for this Date. Please Try Again.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                  Intent in=getIntent();
                                  startActivity(in);
                                  finish();
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                   // Toast.makeText(getApplicationContext(),applicationController.getActiveempcode(),Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    list.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar
                            .make(layout_visit, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent in=getIntent();
                                    startActivity(in);
                                    finish();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                   // Toast.makeText(getApplicationContext(),applicationController.getActiveempcode(),Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public String Para(String BranchCode,String SchoolCode,String SessionId,String FYId,String MeetWith){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("MeetWith", MeetWith);
            jsonParam.put("MeetingDate", selecte_ddate);
            jsonParam.put("SrNo", "3");
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
