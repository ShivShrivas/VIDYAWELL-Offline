package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import vidyawell.infotech.bsn.admin.Adapters.Complaint_classlist_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Complaint_classlist_helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Complaint_Composer extends AppCompatActivity {

    private static Button vertical, horizontal, customHorizontal;
    private static LinearLayout layoutHolder;
    private static Button[] buttonHolder;
    private static TextView[] textHolder;
    ListView list_comp;
    List<Complaint_classlist_helper> noticeboard_helpers;
    Complaint_classlist_helper item;
    ApplicationControllerAdmin applicationController;
    String ClassCode,SectionCode;
    EditText edt_search;
    Complaint_classlist_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complant_composer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((LinearLayout) findViewById(R.id.layout_slist));
        applicationController=(ApplicationControllerAdmin)getApplication();
        Intent in=getIntent();
        ClassCode=in.getStringExtra("Class_code");
        SectionCode=in.getStringExtra("Section_code");
        String Class_name=in.getStringExtra("Class_name");
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Feedback/Suggestion List of Class "+ Class_name);
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        edt_search=(EditText)findViewById(R.id.student_search_comp) ;
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = edt_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);

            }
        });


        new Complaint_list().execute();
    }

    private class Complaint_list extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Complaint_Composer.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Complaint_Composer.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.COMPLAINT_CCOUNT,Para(applicationController.getBranchcode(),applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        noticeboard_helpers = new ArrayList<Complaint_classlist_helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String Complain=object.getString("Complain");
                            String ComplainDate=object.getString("ComplainDate");
                            String StdName=object.getString("StdName");
                            String FatherName=object.getString("FatherName");
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            try {
                                Date date = formatter.parse(ComplainDate);
                                SimpleDateFormat formt= new SimpleDateFormat("MMM dd, yyyy");
                                String d=formt.format(date);
                                System.out.println(date);
                                item = new Complaint_classlist_helper(Complain,d,StdName,FatherName);
                                noticeboard_helpers.add(item);
                                status=1;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-2;
                    }
                }else{
                    status=-1;
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
                    list_comp = (ListView) findViewById(R.id.list_comp);
                    adapter = new Complaint_classlist_Adapter(getApplicationContext(),R.layout.item_fullcomplaint_list, noticeboard_helpers);
                    list_comp.setAdapter(adapter);
                    break;
                case -2:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_LONG).show();
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
            jsonParam.put("ClassCode", ClassCode);
            jsonParam.put("SectionCode", SectionCode);
            jsonParam.put("CreatedBy", "1");
            jsonParam.put("Action", "5");
            jsonParam.put("Code", MeetWith);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
    private void showHorizontalLayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
       // layoutHolder.removeAllViews();//Remove all views from Layout before placing new view
        params.setMargins(7,5,7,5);
        params.height=90;
        buttonHolder = new Button[6];//Setting size of Button Array
        //Loop for 3 items
        for (int i = 0; i < 6; i++) {
            final Button button = new Button(this);//Creating Button
            button.setId(i);//Setting Id for using in future
            button.setText("Item " + i);//Setting text
            button.setTextSize(15);//Text Size
            button.setHeight(10);
            button.setWidth(60);
            button.setPadding(5, 5, 5, 5);//paading
            button.setLayoutParams(params);//Setting Layout Params
            button.setTextColor(Color.parseColor("#ffffff"));//Text Colort
            button.setBackgroundResource(R.drawable.gradiant_green_button);
            button.setGravity(Gravity.CENTER);//Gravity of Text
            //  button.setOnClickListener(Complaint_Composer.this);//Setting click listener
            buttonHolder[i] = button;//Setting button reference in array for future use
            layoutHolder.setOrientation(LinearLayout.HORIZONTAL);//Setting Layout orientation
            layoutHolder.addView(button);//Finally adding view
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),
                            "Clicked Button Index :" + button.getText().toString(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
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
