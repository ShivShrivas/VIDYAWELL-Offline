package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Notification_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Notification_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;


public class Notification extends AppCompatActivity {


    ListView noticefication_list;
    List<Notification_Helper> notification_helpers;
    Notification_Helper item;
    ApplicationControllerAdmin applicationController;
    SharedPreferences sharedprefer;
    RelativeLayout layout_notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

            applicationController = (ApplicationControllerAdmin) getApplicationContext();
            TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT);
            fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_notification));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            androidx.appcompat.app.ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
            TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
            SpannableString str = new SpannableString("Notification");
            str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            getSupportActionBar().setTitle(str);
            sharedprefer = getSharedPreferences("STUPROFILE", Context.MODE_PRIVATE);
            String theme_code = sharedprefer.getString("Theme_Code", "0");
            noticefication_list = (ListView) findViewById(R.id.noticefication_list);
            layout_notification=(RelativeLayout)findViewById(R.id.layout_notification);

            noticefication_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv_letter=(TextView)view.findViewById(R.id.letter_n);
                    TextView ID_Notification=(TextView)view.findViewById(R.id.id_Notification);
                    if(tv_letter.getText().toString().equalsIgnoreCase("C")){
                        Intent intent = new Intent(Notification.this, Circular.class);
                        intent.putExtra("notice_ID",ID_Notification.getText().toString());
                        startActivity(intent);
                    }else if(tv_letter.getText().toString().equalsIgnoreCase("N")){
                        Intent intent = new Intent(Notification.this, Notice_Board.class);
                        intent.putExtra("notice_ID",ID_Notification.getText().toString());
                        startActivity(intent);
                    }else if(tv_letter.getText().toString().equalsIgnoreCase("LC")){
                        Intent intent = new Intent(Notification.this, LiveClass.class);
                        intent.putExtra("notice_ID",ID_Notification.getText().toString());
                        startActivity(intent);
                    }
                }
            });
        new Notificationdetails().execute();
    }

    private class Notificationdetails extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Notification.this);

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(Notification.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.NOTIFICATION_API,Para(applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getBranchcode(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");
            if(response!=null) {
                if (response.length() > 5) {
                    try {
                        JSONArray array = new JSONArray(response);
                        notification_helpers = new ArrayList<Notification_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String TransId = object.getString("TransId");
                            String UserId = object.getString("UserId");
                            String StudentPhoto = object.getString("StudentPhoto");
                            String Title = object.getString("Title");
                            String font = object.getString("CreatedOn");
                            String Notification = object.getString("SlotMessage");
                            String link = object.getString("link");
                            String letter = object.getString("Notification");
                            if(Title.equals("null")){

                            }else{
                                item = new Notification_Helper(TransId, UserId, StudentPhoto, Title, font, Notification, link, letter);
                                notification_helpers.add(item);
                                status = 1;
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status = -2;
                    }
                } else {
                    status = -1;
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
                    Notification_Adapter adapter = new Notification_Adapter(getApplicationContext(),R.layout.notification_listitem, notification_helpers);
                    noticefication_list.setAdapter(adapter);
                    break;
                case -2:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    //Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar
                            .make(layout_notification, "Data Not Found Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Para(String schoolCode, String SessionId, String branchCode, String fyId, String StudentCode){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("schoolCode", schoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("branchCode", branchCode);
            jsonParam.put("fyId", fyId);
            jsonParam.put("StudentCode", StudentCode);
            jsonParam.put("userId", applicationController.getUserID());
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
       /* Intent intent=new Intent(this,MainActivity.class);
        //intent.putExtra("EMPID",id);
        startActivity(intent);
        finish();*/
    }


}