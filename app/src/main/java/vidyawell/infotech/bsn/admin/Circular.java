package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import vidyawell.infotech.bsn.admin.Adapters.Circular_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Circular_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Circular extends AppCompatActivity {

    Circular_Adapter adapter;
    ListView circular_list;
    List<Circular_Helper> circular_helpers;
    Circular_Helper item;
    ApplicationControllerAdmin applicationController;
    RelativeLayout layout_circular;
    EditText circular_search;
    String notice_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_circular));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Circular");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);

        Intent in=getIntent();
        notice_ID=in.getStringExtra("notice_ID");

        layout_circular=(RelativeLayout)findViewById(R.id.layout_circular);
        circular_search=findViewById(R.id.circular_search);
        //new Circular1().execute();


        circular_search.addTextChangedListener(new TextWatcher() {
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
                if(adapter !=null){
                    String text = circular_search.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

            }
        });

       new Circular1().execute();


    }



    private class Circular1 extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Circular.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Circular.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.CIRCULAR_API_ONE,Para(applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getBranchcode(),applicationController.getFyID(),"4"),"1");
           // String api=ServerApi.CIRCULAR_API_ONE;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        circular_helpers = new ArrayList<Circular_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String TransId=object.getString("TransId");
                            String CircularDate=object.getString("CircularDate");
                            String Headline=object.getString("Headline");
                            String Attachment=object.getString("Attachment");
                            String CircularDetails=object.getString("CircularDetails");
                            if(notice_ID.equals(TransId)){
                                item = new Circular_Helper(TransId,CircularDate,Headline,Attachment,CircularDetails);
                                circular_helpers.add(item);
                                status=1;
                            }

                        }

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String TransId=object.getString("TransId");
                            String CircularDate=object.getString("CircularDate");
                            String Headline=object.getString("Headline");
                            String Attachment=object.getString("Attachment");
                            String CircularDetails=object.getString("CircularDetails");
                            if(notice_ID.equals(TransId)){

                            }else{
                                item = new Circular_Helper(TransId,CircularDate,Headline,Attachment,CircularDetails);
                                circular_helpers.add(item);
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    circular_list = (ListView) findViewById(R.id.circular_list);
                    adapter = new Circular_Adapter(getApplicationContext(),R.layout.circular_listitem, circular_helpers);
                    circular_list.setAdapter(adapter);
                    break;
                case -2:
                    if(adapter!=null){
                        adapter.clear();
                    }
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                   // Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar
                            .make(layout_circular, "Data Not Found", Snackbar.LENGTH_LONG)
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
    public String Para(String schoolcode, String sessionid, String branchcode, String fyid,  String action){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", schoolcode);
            jsonParam.put("SessionId", sessionid);
            jsonParam.put("BranchCode", branchcode);
            jsonParam.put("FyId", fyid);
            jsonParam.put("StudentCode", applicationController.getActiveempcode());
            jsonParam.put("Action", action);
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
