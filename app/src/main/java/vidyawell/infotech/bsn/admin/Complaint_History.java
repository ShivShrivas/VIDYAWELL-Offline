package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Complaint_Adapter_history;
import vidyawell.infotech.bsn.admin.Helpers.Complaint_Helper_history;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Complaint_History extends AppCompatActivity {


    ListView complaint_history_list;
    List<Complaint_Helper_history> complaint_helpers;
    Complaint_Helper_history item;
    SharedPreferences sharedprefer;
    ApplicationControllerAdmin applicationController;
    String Complain_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.font_complaint_history));
        applicationController = (ApplicationControllerAdmin) getApplicationContext();
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Feedback/Suggestion History");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);

        sharedprefer = getSharedPreferences("STUPROFILE", Context.MODE_PRIVATE);
        String theme_code = sharedprefer.getString("Theme_Code", "0");

        new ComplaintAPI_History().execute();
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
    //////////////////////////////////////Complaint history api/////////////////////////
    private class ComplaintAPI_History extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Complaint_History.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Complaint_History.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.COMPLAINT_API_FIRST,Para("2",applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getBranchcode(),applicationController.getschoolCode(),applicationController.getActiveempcode() ,"1"),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        complaint_helpers = new ArrayList<Complaint_Helper_history>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String Complain=object.getString("Complain");
                            String ComplainDate=object.getString("ComplainDate");
/////////////////////////////////Date Formate API response///////////////////////////////////////
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            try {
                                Date date = formatter.parse(ComplainDate);
                                SimpleDateFormat formt= new SimpleDateFormat("MMM dd, yyyy");
                                String d=formt.format(date);
                                System.out.println(date);
                                item = new Complaint_Helper_history(Complain,d,"");
                                complaint_helpers.add(item);
                                status=1;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
///////////////////////////////////////////////////////End////////////////////////////////////////
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
                    complaint_history_list = (ListView) findViewById(R.id.complaint_history_list);
                    Complaint_Adapter_history adapter = new Complaint_Adapter_history(Complaint_History.this,R.layout.item_complaint_hist, complaint_helpers);
                    complaint_history_list.setAdapter(adapter);
                    break;
                case -2:
                    Toast.makeText(getApplicationContext(),"Feedback not found",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Para(String action,String sessionid,String fyid,String branchcode,String schoolcode,String Code,String UpdatedBy){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("SessionId", sessionid);
            jsonParam.put("FYId", fyid);
            jsonParam.put("BranchCode", branchcode);
            jsonParam.put("SchoolCode", schoolcode);
            jsonParam.put("ComplainBy", Code);
            jsonParam.put("Code", Code);
            jsonParam.put("UpdatedBy", "1");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

}
