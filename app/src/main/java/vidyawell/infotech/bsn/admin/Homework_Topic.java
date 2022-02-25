package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Adapters.Homework_Topic_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Student_Query_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Homework_Topic_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_Query_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Homework_Topic extends AppCompatActivity   {

    EditText topic_search;
    ApplicationControllerAdmin applicationControllerAdmin;
    Homework_Topic_Adapter adapter;
    ListView homework_topic_list;
    List<Homework_Topic_Helper> homework_topic_helpers;
    Homework_Topic_Helper item;
    RelativeLayout homework_topic_layout;
    String SectionId,ClassId;
    //String section_ID,class_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework__topic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin = (ApplicationControllerAdmin) getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.homework_topic_layout));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Homework Topic Class");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        topic_search=(EditText)findViewById(R.id.topic_search);
        homework_topic_layout=(RelativeLayout)findViewById(R.id.homework_topic_layout);
        homework_topic_list=(ListView)findViewById(R.id.homework_topic_list);


        Intent intent=getIntent();
        SectionId=intent.getStringExtra("SectionId");
        ClassId=intent.getStringExtra("ClassId");




        new Homework_Topic_API().execute();

        topic_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()>1){
                    adapter.getFilter().filter(s);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String text = topic_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });


        homework_topic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"Click", Toast.LENGTH_SHORT).show();

                TextView TopicName=(TextView)view.findViewById(R.id.txt_topic);
                //TextView SectionId=(TextView)view.findViewById(R.id.txt_section_id);
                //TextView txt_class_id=(TextView)view.findViewById(R.id.txt_class_id);
                //TextView txt_emp_code=(TextView)view.findViewById(R.id.txt_emp_code);


                Intent intent =new Intent(Homework_Topic.this,Homwork_Checked_Status_Button.class);
                //intent.putExtra("TopicName",TopicName.getText().toString());
                intent.putExtra("TopicName",Base64.encodeToString(TopicName.getText().toString().getBytes(), Base64.DEFAULT));
                intent.putExtra("SectionId",SectionId);
                intent.putExtra("ClassId",ClassId);
                //intent.putExtra("EmployeeCode",txt_emp_code.getText().toString());
                startActivityForResult(intent,100);
            }
        });


    }



    private class Homework_Topic_API extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Homework_Topic.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Homework_Topic.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationControllerAdmin.getServicesapplication()+ ServerApiadmin.HOMEWORK_TOPIC_API,Para(applicationControllerAdmin.getActiveempcode(),applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            String api=applicationControllerAdmin.getServicesapplication();
            if(response!=null){
                response=response.replace("\r","");
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        homework_topic_helpers = new ArrayList<Homework_Topic_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                             String   TopicName=object.getString("TopicName");
                             //String   SectionId=object.getString("SectionId");
                            // String   EmployeeCode=object.getString("EmployeeCode");
                             //String   ClassId=object.getString("ClassId");
                            // String   HomeWorkShowDate=object.getString("HomeWorkShowDate");
                            ////encode edit text vlaue

                           // TopicName = Base64.encodeToString(TopicName.getBytes(), Base64.DEFAULT);

                           /* SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            try {
                                Date date = formt.parse(HomeWorkShowDate);
                                SimpleDateFormat formt1 = new SimpleDateFormat("dd/MM/yyyy");
                                HomeWorkShowDate = formt1.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }*/

                            item = new Homework_Topic_Helper(TopicName);
                            homework_topic_helpers.add(item);
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

                  //  homework_topic_list=(ListView)findViewById(R.id.homework_topic_list);
                    //teachers_list.setVisibility(View.VISIBLE);
                    adapter = new Homework_Topic_Adapter(getApplicationContext(),R.layout.homework_item_topic, homework_topic_helpers);
                    homework_topic_list.setAdapter(adapter);


                    break;
                case -2:
                    // toast_message="Student Query Data Not Found. Please Try Again";
                    // showErrorDialog();
                    Snackbar snackbar = Snackbar
                            .make(homework_topic_layout, "Homework Topic Data Not Found. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    //Toast.makeText(getApplicationContext(),"Student Query Data Not Found. Please Try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    // toast_message="Network Congestion! Please try Again";
                    // showErrorDialog();
                    Snackbar snackbar1 = Snackbar
                            .make(homework_topic_layout, "Network Congestion! Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    //teachers_list.setVisibility(View.GONE);

                    break;
            }
        }
    }
    public String Para(String EmployeeCode,String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("EmployeeCode", EmployeeCode);
            jsonParam.put("schoolCode", SchoolCode);
            jsonParam.put("branchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("SectionId",SectionId);
            jsonParam.put("ClassId", ClassId);
            jsonParam.put("fyId", applicationControllerAdmin.getFyID());
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new Homework_Topic_API().execute();
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
