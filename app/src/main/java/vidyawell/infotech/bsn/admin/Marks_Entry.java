package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.ClassList_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.MarksEntry_Adapter;
import vidyawell.infotech.bsn.admin.Const.CustomTypefaceSpan;
import vidyawell.infotech.bsn.admin.Database.MyDatabaseHelper;
import vidyawell.infotech.bsn.admin.Database.Note;
import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.MerksEnter_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Marks_Entry extends AppCompatActivity implements MarksEntry_Adapter.chatcustomButtonListener{

    ApplicationControllerAdmin applicationController;
    ListView listmarks_entry;
    MarksEntry_Adapter Adapter;
    MerksEnter_Helper Helper;
    List<Note> helpers;
    String count[]={"","","","","","","","","","","","",""};
    String class_ID,section_ID,branch_ID,stream_ID,medium_ID,ExamCode,MaxMarks,MinMarks,practicalmarks,subject_ID,YearlyOrHalf;
    LinearLayout class_attendance_layout;
    TextView text_pract;
    MyDatabaseHelper database;
    String V_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks__entry);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        applicationController=(ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.layout_entrymarks));
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        SpannableString str = new SpannableString("Obtained Marks Entry Sheet");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        str.setSpan (new CustomTypefaceSpan("",font2), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(str);
        database=new MyDatabaseHelper(this);
        class_attendance_layout=findViewById(R.id.layout_entrymarks);
        text_pract=findViewById(R.id.text_pract);
        listmarks_entry=findViewById(R.id.listmarks_entry);
        Intent intent=getIntent();
        class_ID=intent.getStringExtra("ClassId");
        section_ID=intent.getStringExtra("SectionId");
        branch_ID=intent.getStringExtra("BranchId");
        stream_ID=intent.getStringExtra("StreamId");
        medium_ID=intent.getStringExtra("MediumId");
        ExamCode=intent.getStringExtra("ExamCode");
        MaxMarks=intent.getStringExtra("MaxMarks");
        MinMarks=intent.getStringExtra("MinMarks");
        subject_ID=intent.getStringExtra("subject_ID");
        YearlyOrHalf=intent.getStringExtra("YearlyOrHalf");
        practicalmarks="0";
     /*   class_ID="000B010000009";
        section_ID="000B010000006";
        branch_ID="000B010000004";
        stream_ID="000B010000004";
        medium_ID="000B010000002";
        ExamCode="000B010000001";*/

        if(practicalmarks.equalsIgnoreCase("0")){
            text_pract.setVisibility(View.GONE);
        }else{
            text_pract.setVisibility(View.VISIBLE);
        }
        helpers = new ArrayList<Note>();
        helpers.addAll(database.getAllNotes());
        if(helpers.size()>0){
            Adapter = new MarksEntry_Adapter(getApplicationContext(),R.layout.item_marksentry, helpers);
            listmarks_entry.setAdapter(Adapter);
            Adapter.setCustomButtonListner(Marks_Entry.this);
        }else{
            new Studentlist().execute();
        }


        LayoutInflater inflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.markentry_listview_footer, listmarks_entry, false);
        Button buttonfooter = (Button) footer.findViewById(R.id.att_submit_footer);
        buttonfooter.setFocusable(false);
        listmarks_entry.addFooterView(footer, null, false);
        buttonfooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            int pending_lock=database.getUnlockCount("0");
            if(pending_lock==0){
                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                V_date=mYear+ "-"
                        + (mMonth + 1) + "-" + mDay ;
                new SubmitresultEntry().execute();
            }else{
                Snackbar snackbar1 = Snackbar
                        .make(class_attendance_layout, "Lock All Student Marks Entries", Snackbar.LENGTH_LONG)
                        .setAction("Sheet not Completed", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                            }
                        });
                snackbar1.setActionTextColor(Color.RED);
                snackbar1.show();
            }
            }
        });
    }

    private class Studentlist extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Marks_Entry.this);
        @Override
        protected void onPreExecute() {
              progressDialog = ProgressDialog.show(Marks_Entry.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.STUDENTLIST_API,Paradetails(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        //helpers = new ArrayList<MerksEnter_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   StudentCode=object.getString("StudentCode");
                            String   Student=object.getString("Student");
                          //  Helper = new MerksEnter_Helper(Student,StudentCode,(i+1)+".",MinMarks,MaxMarks,practicalmarks);
                           // helpers.add(Helper);
                            database.insertNote(StudentCode,Student,"","","","","0",MaxMarks,"5","5","0",(i+1)+".","0","0");
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
                    helpers = new ArrayList<Note>();
                    helpers.addAll(database.getAllNotes());
                    if(helpers.size()>0){
                        Adapter = new MarksEntry_Adapter(getApplicationContext(),R.layout.item_marksentry, helpers);
                        listmarks_entry.setAdapter(Adapter);
                        Adapter.setCustomButtonListner(Marks_Entry.this);
                    }

                    break;
                case -1:
                    if(Adapter!= null){
                        listmarks_entry.setAdapter(null);
                    }
                    Snackbar snackbar = Snackbar
                            .make(class_attendance_layout, "Student List Not Found", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -2:
                    if(Adapter!= null){
                        listmarks_entry.setAdapter(null);
                    }
                    Snackbar snackbar1 = Snackbar
                            .make(class_attendance_layout, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;
            }
        }
    }
    public String Paradetails(String SchoolCode,String BranchCode,String SessionId,String FYId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("Action","8");
            jsonParam.put("ClassId",class_ID);
            jsonParam.put("SectionId",section_ID);
            jsonParam.put("BranchId",branch_ID);
            jsonParam.put("StreamId",stream_ID);
            jsonParam.put("MediumId",medium_ID);
            jsonParam.put("ExamCode",ExamCode);
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

    @Override
    public void onButtonClickListner(int position, Note value,double ntrymark_theory,double entrymark_enrichment,double entrymark_notebook,double entrymark_practical,String islock) {

        String code=value.getCode();
        String theorymark=ntrymark_theory+"";
        String enrichmark=entrymark_enrichment+"";
        String notemark=entrymark_notebook+"";
        double total=ntrymark_theory+entrymark_enrichment+entrymark_notebook;
        database.updateNote(code,theorymark,enrichmark,notemark,islock,"0",total+"","0");
        if(islock.equalsIgnoreCase("1")){
            Snackbar snackbar1 = Snackbar
                    .make(class_attendance_layout, code+"/"+theorymark+"/"+enrichmark+"/"+notemark+"/"+"/"+islock, Snackbar.LENGTH_LONG)
                    .setAction("Locked", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/

                        }
                    });
            snackbar1.setActionTextColor(Color.RED);
            snackbar1.show();
        }
    }

    ///////////////////////////////Send Services///////////////////////////////////////
    private class SubmitresultEntry extends AsyncTask<String, String, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.INSERTMARKS_API,Parasend("1"),"1");
            if(response!=null){
                Log.d("RES",response);
                response=response.replace("\"","");
                    if(response.equalsIgnoreCase("1")){
                        status=1;
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
            switch (s){
                case 1:
                    savemarksheet();
                    database.deleteNote();
                    break;
                case -2:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
                case -1:

                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public String Parasend(String action){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamnew = new JSONObject();
        try {
            jsonParam.put("Action", action);
            jsonParam.put("SchoolCode", applicationController.getschoolCode());
            jsonParam.put("BranchCode", applicationController.getBranchcode());
            jsonParam.put("SessionId", applicationController.getSeesionID());
            jsonParam.put("FYId", applicationController.getFyID());
            jsonParam.put("CreatedBy", applicationController.getActiveempcode());
            jsonParam.put("ClassId", class_ID);
            jsonParam.put("SectionId", section_ID);
            jsonParam.put("BranchId", branch_ID);
            jsonParam.put("StreamId", stream_ID);
            jsonParam.put("MediumId", medium_ID);
            jsonParam.put("ExamCode", ExamCode);
            jsonParam.put("YearlyOrHalf", YearlyOrHalf);
            jsonParam.put("SubjectCode", subject_ID);
            jsonParam.put("V_date", V_date);
            jsonParam.put("MinMarkse", MinMarks);
            jsonParam.put("MaxMarkse", MaxMarks);
            JSONArray jsonArr = new JSONArray();
            for (int i = 0; i < helpers.size(); i++) {
                Note name=helpers.get(i);
                JSONObject pnObj = new JSONObject();
                pnObj.put("SrNo", (i+1)+"");
                pnObj.put("RollNo","0");
                pnObj.put("StudentCode", name.getCode());
                pnObj.put("PeriodicTest", "0");
                pnObj.put("NoteBook", name.getnotebook());
                pnObj.put("SubjectEnrichment", name.getenrichment());
                if(YearlyOrHalf.equalsIgnoreCase("1")){
                    pnObj.put("YearlyExam", "0");
                    pnObj.put("HalfYearlyExam",  name.gettheory());
                }else{
                    pnObj.put("YearlyExam", name.gettheory());
                    pnObj.put("HalfYearlyExam","0");
                }
                pnObj.put("Total",name.gettotal());
                pnObj.put("Grade", "");
                pnObj.put("GradeCode", "");
                jsonArr.put(pnObj);
            }
            jsonParamnew.put("StudentmarkList", jsonArr);
            jsonParamnew.toString();
            String dataformate=jsonParamnew.getString("StudentmarkList");
            jsonParam.put("StudentListJSON", dataformate);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    private void savemarksheet(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Students marks has been submitted successfully");
        alertDialogBuilder.setTitle("Marks Submitted Successfully");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
