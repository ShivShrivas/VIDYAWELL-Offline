package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Approved_Vedio_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Approved_Vedio_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Approved_Vedio extends AppCompatActivity implements Approved_Vedio_Adapter.customButtonListener {

    ListView list_approved_list;
    ApplicationControllerAdmin applicationController;
    List<Approved_Vedio_Helper>approved_vedio_helpers;
    Approved_Vedio_Adapter adapter;
    RelativeLayout layout_approved_vedio;
    String transectioid,voucher_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved__vedio);
        applicationController=(ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_approved_vedio));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Approve Video");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);

        list_approved_list=(ListView)findViewById(R.id.list_approved_list);
        layout_approved_vedio=(RelativeLayout)findViewById(R.id.layout_approved_vedio);


        new APPROVED_GET_API().execute();
    }

    @Override
    public void onButtonClickListner(int position, Approved_Vedio_Helper value) {

        transectioid =value.getTransId();
        voucher_no =value.getVoucherNo();

        new Approved_Update().execute();

    }

    private class APPROVED_GET_API extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Approved_Vedio.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.APPROVED_GET_API,Paradetails(applicationController.getSeesionID(),applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getFyID(),applicationController.getActiveempcode()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        approved_vedio_helpers = new ArrayList<Approved_Vedio_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String   ClassName=object.getString("ClassName");
                            String   SubjectName=object.getString("SubjectName");
                            String SectionName=object.getString("SectionName");
                            String TopicName=object.getString("TopicName");
                            String SubmissionDate=object.getString("SubmissionDate");
                            String VedioPath=object.getString("VedioPath");
                             String TransId =object.getString("TransId");
                             String  VoucherNo=object.getString("VoucherNo");
                             String  IsVedioApproval=object.getString("IsVedioApproval");


                                Approved_Vedio_Helper item = new Approved_Vedio_Helper(ClassName,SubjectName,SectionName,TopicName,VedioPath,TransId,VoucherNo,IsVedioApproval);
                                approved_vedio_helpers.add(item);

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
                status=-2;
            }
            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            // progressDialog.dismiss();
            switch (s){
                case 1:
                    //list_womework.setVisibility(View.VISIBLE);
                     adapter = new Approved_Vedio_Adapter(getApplicationContext(),R.layout.item_approve_vedio, approved_vedio_helpers);
                    list_approved_list.setAdapter(adapter);
                    adapter.setCustomButtonListner(Approved_Vedio.this);
                    break;
                case -2:

                    if(adapter !=null){
                        adapter.clear();
                    }
                   /* Snackbar snackbar1 = Snackbar
                            .make(layout_approved_vedio, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Data not Found", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();*/
                    break;
                case -1:
                    Snackbar snackbar2 = Snackbar
                            .make(layout_approved_vedio, "Network Problem. Please Try Again ", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar2.setActionTextColor(Color.RED);
                    snackbar2.show();
                    break;
            }
        }
    }
    public String Paradetails(String SessionId,String schoolCode,String branchCode,String fyId,String EmployeeId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();//Action,IsApproved,TransId,VoucherNO,schoolCode,SessionId,branchCode,fyId
        try {
            jsonParam.put("Action", "23");
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("schoolCode", schoolCode);
            jsonParam.put("branchCode", branchCode);
            jsonParam.put("fyId",fyId);
            jsonParam.put("EmployeeId",EmployeeId);
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
    ///////////////////////////API transection pass update api////////////////////////////////////////////

    private class Approved_Update extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Approved_Vedio.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.UPDATE_VEDIO_API,ParaSub(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            //Log.d("request!", response);
            if(response!=null){
                response=response.replace("\"", "");
                if(Integer.parseInt(response)== 2){
                    status = 1;

                }else{
                    status = -2;
                }
            }else{
                status=-1;
            }
            return status;
        }
        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            //  progressDialog.dismiss();
            switch (s){
                case 1:
                    new APPROVED_GET_API().execute();

                    Toast.makeText(getApplicationContext(),"Approved Vedio",Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_approved_vedio, "Viedo not Approved. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_approved_vedio, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    break;


            }
        }
    }

    public String ParaSub(String schoolCode, String branchCode, String SessionId, String fyId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "24");
            jsonParam.put("schoolCode", schoolCode);
            jsonParam.put("branchCode", branchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("fyId",fyId);
            jsonParam.put("IsApproved","1");
            jsonParam.put("TransId",transectioid);
            jsonParam.put("VoucherNO",voucher_no);

            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new APPROVED_GET_API().execute();

    }
}
