package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Complaint_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Complaint_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Complaint_Box extends AppCompatActivity {

    ListView list_comp;
    List<Complaint_Helper> complaint_helpers;
    Complaint_Helper item;
    ApplicationControllerAdmin applicationController;
    RelativeLayout layout_complaint;
    TextView text_classsection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__box);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_complaints));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Feedback/Suggestion");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        text_classsection=findViewById(R.id.text_classsection);
        if(applicationController.getschooltype().equals("0")){
            text_classsection.setText("Class (Section) ");
        }else{
            text_classsection.setText("Course (Semester)");
        }
        layout_complaint=(RelativeLayout)findViewById(R.id.layout_complaints);
        list_comp=(ListView)findViewById(R.id.list_comp);
        list_comp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_class_code=(TextView)view.findViewById(R.id.text_class_ID) ;
                TextView tv_section_code=(TextView)view.findViewById(R.id.text_section_ID) ;
                TextView tv_class_name=(TextView)view.findViewById(R.id.text_class_name);
                Intent intent = new Intent(Complaint_Box.this, Complaint_Composer.class);
                intent.putExtra("Class_code",tv_class_code.getText().toString());
                intent.putExtra("Section_code",tv_section_code.getText().toString());
                intent.putExtra("Class_name",tv_class_name.getText().toString());
                startActivity(intent);
            }
        });

        ImageView imgcomp_hist=(ImageView)findViewById(R.id.comp_hist);
        imgcomp_hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Complaint_Box.this, Complaint_History.class);
                startActivity(intent);
            }
        });

        new ComplaintCount().execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complaint, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
          //  Toast.makeText(getApplicationContext(),"toast",Toast.LENGTH_SHORT).show();
            Intent in=new Intent(getApplicationContext(),Complaint_View.class);
            startActivity(in);
            return(true);
        case android.R.id.home:
            // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
            // if this doesn't work as desired, another possibility is to call `finish()` here.
            onBackPressed();
            return true;
    }
        return(super.onOptionsItemSelected(item));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class ComplaintCount extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Complaint_Box.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Complaint_Box.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.COMPLAINT_CCOUNT,Para(applicationController.getBranchcode(),applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");
            if(response!=null){
                if (response.length()>3) {
                    try {
                        JSONArray array = new JSONArray(response);
                        complaint_helpers = new ArrayList<Complaint_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobject = array.getJSONObject(i);
                            String ClassName = jsonobject.getString("ClassName");
                            String ClassId = jsonobject.getString("ClassId");
                            String SectionId = jsonobject.getString("SectionId");
                            String SectionName = jsonobject.getString("SectionName");
                            String Compcount = jsonobject.getString("Compcount");
                            item = new Complaint_Helper(ClassName, Compcount, ClassId, SectionId, SectionName);
                            complaint_helpers.add(item);
                            status = 1;
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
                    Complaint_Adapter adpater=new Complaint_Adapter(getApplicationContext(),R.layout.item_complaint_list,complaint_helpers);
                    list_comp.setAdapter(adpater);
                    break;
                case -2:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_complaint, "Complain not found. Please Try Again.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
                case -1:
                    Snackbar snackbar = Snackbar
                            .make(layout_complaint, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
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
    public String Para(String BranchCode,String SchoolCode,String SessionId,String FYId,String MeetWith){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId", FYId);
            jsonParam.put("Code", MeetWith);
            jsonParam.put("CreatedBy", "1");
            jsonParam.put("Action", "4");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }
}
