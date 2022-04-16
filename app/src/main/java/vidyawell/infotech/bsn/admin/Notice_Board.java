package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Adapters.Noticeboard_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Noticeboard_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Notice_Board extends AppCompatActivity {

    Noticeboard_Adapter adapter;
    ListView noticeboard_list;
    List<Noticeboard_Helper> noticeboard_helpers;
    Noticeboard_Helper item;
    ApplicationControllerAdmin applicationController;
    SharedPreferences sharedprefer;
    RelativeLayout layout_notice;
    String notice_ID;
    boolean d_click=true;
    ProgressDialog progressDialogn;

    TextView noticeboard_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice__board);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_notice));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Notice Board");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        noticeboard_search=(TextView)findViewById(R.id.noticeboard_search);

        layout_notice=(RelativeLayout)findViewById(R.id.layout_notice);
        Intent in=getIntent();
        notice_ID=in.getStringExtra("notice_ID");
        progressDialogn = new ProgressDialog(Notice_Board.this);

        if(notice_ID.equals("D")){
            new Notice_board1().execute();
        }else{
            new Notice_board2().execute();
        }
        noticeboard_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(adapter != null){
                    String text = noticeboard_search.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

            }
        });

    }


    private class Notice_board1 extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Notice_Board.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Notice_Board.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.NOTICEBOARD_API_ONE,Para(applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getBranchcode(),applicationController.getFyID(),"9"),"1");
            String api=ServerApiadmin.NOTICEBOARD_API_ONE;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        noticeboard_helpers = new ArrayList<Noticeboard_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String TransId=object.getString("TransId");
                            String NoticeDate=object.getString("NoticeDate");
                            String Headline=object.getString("Headline");
                            String Attachment=object.getString("Attachment");
                            String NoticeDetails=object.getString("NoticeDetails");

                                item = new Noticeboard_Helper(TransId,NoticeDate,Headline,Attachment,NoticeDetails);
                                noticeboard_helpers.add(item);


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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    noticeboard_list = (ListView) findViewById(R.id.noticeboard_list);
                     adapter = new Noticeboard_Adapter(getApplicationContext(),R.layout.notice_listitem, noticeboard_helpers);
                    noticeboard_list.setAdapter(adapter);
                    break;
                case -2:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    //Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar
                            .make(layout_notice, "Data Not Found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();

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
            jsonParam.put("StudentCode", applicationController.getActiveempcode());
            jsonParam.put("Action", action);
            jsonParam.put("Updatedby", applicationController.getLoginType());
            jsonParam.put("loginTypeId", applicationController.getLoginType());
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    private class Notice_board2 extends AsyncTask<String, String, Integer> {

        @Override
        protected void onPreExecute() {
            progressDialogn = ProgressDialog.show(Notice_Board.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.NOTICEBOARD_API_ONE,Para2(applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getBranchcode(),applicationController.getFyID(),"9"),"1");
            String api=ServerApiadmin.NOTICEBOARD_API_ONE;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        noticeboard_helpers = new ArrayList<Noticeboard_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String TransId=object.getString("TransId");
                            String NoticeDate=object.getString("NoticeDate");
                            String Headline=object.getString("Headline");
                            String Attachment=object.getString("Attachment");
                            String NoticeDetails=object.getString("NoticeDetails");
                            if(notice_ID.equals(TransId)){
                                item = new Noticeboard_Helper(TransId,NoticeDate,Headline,Attachment,NoticeDetails);
                                noticeboard_helpers.add(item);
                                d_click=false;
                                status=1;
                            }



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

            switch (s){
                case 1:
                    new Notice_board3().execute();
                    break;
                case -2:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    //Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar
                            .make(layout_notice, "Data Not Found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();

                    break;
            }
        }
    }
    public String Para2(String schoolcode, String sessionid, String branchcode, String fyid, String action){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", schoolcode);
            jsonParam.put("SessionId", sessionid);
            jsonParam.put("BranchCode", branchcode);
            jsonParam.put("FyId", fyid);
            jsonParam.put("StudentCode", applicationController.getActiveempcode());
            jsonParam.put("Action", action);
            jsonParam.put("Updatedby", applicationController.getLoginType());
            jsonParam.put("loginTypeId", applicationController.getLoginType());
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    private class Notice_board3 extends AsyncTask<String, String, Integer> {
        //ProgressDialog progressDialog = new ProgressDialog(Notice_Board.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(Notice_Board.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.NOTICEBOARD_API_ONE,Para3(applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getBranchcode(),applicationController.getFyID(),"9"),"1");
            String api=ServerApiadmin.NOTICEBOARD_API_ONE;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                       // noticeboard_helpers = new ArrayList<Noticeboard_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String TransId=object.getString("TransId");
                            String NoticeDate=object.getString("NoticeDate");
                            String Headline=object.getString("Headline");
                            String Attachment=object.getString("Attachment");
                            String NoticeDetails=object.getString("NoticeDetails");
                            if(notice_ID.equals(TransId)){

                            }else{
                                item = new Noticeboard_Helper(TransId,NoticeDate,Headline,Attachment,NoticeDetails);
                                noticeboard_helpers.add(item);
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
           progressDialogn.dismiss();
            switch (s){
                case 1:
                    noticeboard_list = (ListView) findViewById(R.id.noticeboard_list);
                    adapter = new Noticeboard_Adapter(getApplicationContext(),R.layout.notice_listitem, noticeboard_helpers);
                    noticeboard_list.setAdapter(adapter);
                    break;
                case -2:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    //Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar
                            .make(layout_notice, "Data Not Found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();

                    break;
            }
        }
    }
    public String Para3(String schoolcode, String sessionid, String branchcode, String fyid, String action){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", schoolcode);
            jsonParam.put("SessionId", sessionid);
            jsonParam.put("BranchCode", branchcode);
            jsonParam.put("FyId", fyid);
            jsonParam.put("StudentCode", applicationController.getActiveempcode());
            jsonParam.put("Action", action);
            jsonParam.put("Updatedby", applicationController.getLoginType());
            jsonParam.put("loginTypeId", applicationController.getLoginType());
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
