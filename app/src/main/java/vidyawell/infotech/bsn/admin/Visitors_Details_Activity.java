package vidyawell.infotech.bsn.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Visitors_Details_Activity  extends AppCompatActivity {
    Button btn_waiting,btn_reject,btn_walkin;
    ListView list;
    LinearLayout layout_visit;
    String status_update,remark;
    ApplicationControllerAdmin applicationController;
    EditText editText_remark;
    Typeface typeface;
    String v_name,status,TransId,photo,Purposes,HOWUKNOWs,AnyReference,OrganizationName,Address,VisitTypes,EmailId,PhoneNo,TotalPassMember,OfficeEmail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiter_details_layout);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout) findViewById(R.id.layout_visitdetails));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Visitor Details");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
        layout_visit=(LinearLayout)findViewById(R.id.layout_visitdetails);
        Intent intent = getIntent();
        v_name=intent.getStringExtra("v_name");
        status=intent.getStringExtra("status");
        TransId=intent.getStringExtra("TransId");
        photo=intent.getStringExtra("photo");
        Purposes=intent.getStringExtra("Purposes");
        HOWUKNOWs=intent.getStringExtra("HOWUKNOWs");
        AnyReference=intent.getStringExtra("AnyReference");
        OrganizationName=intent.getStringExtra("OrganizationName");
        Address=intent.getStringExtra("Address");
        VisitTypes=intent.getStringExtra("VisitTypes");
        EmailId=intent.getStringExtra("EmailId");
        PhoneNo=intent.getStringExtra("PhoneNo");
        TotalPassMember=intent.getStringExtra("TotalPassMember");
        OfficeEmail=intent.getStringExtra("OfficeEmail");
        CircleImageView student_image=(CircleImageView)findViewById(R.id.main_visiter_big_g);
        if(photo.equals("null")){
            student_image.setImageResource(R.drawable.ic_user_avatar_main_picture);
        }else{
            String imagerplace= ServerApiadmin.MAIN_IPLINK+photo;
            imagerplace=imagerplace.replace("..","");
            student_image.setImageURI(Uri.parse(imagerplace));

            Glide
                    .with(this)
                    .load(imagerplace)
                    .into(student_image);
        }
        TextView name=(TextView)findViewById(R.id.visiter_name);
        TextView visiter_email=(TextView)findViewById(R.id.visiter_email);
        TextView visiter_mobile=(TextView)findViewById(R.id.visiter_mobile);
        TextView visiter_address=(TextView)findViewById(R.id.visiter_address);
        TextView refrence_by=(TextView)findViewById(R.id.refrence_by);
        TextView purpose_v=(TextView)findViewById(R.id.purpose_v);
        TextView number_person=(TextView)findViewById(R.id.number_person);
        TextView comp_name=(TextView)findViewById(R.id.comp_name);
        TextView visiter_ofcemail=(TextView)findViewById(R.id.purpose_ofcmail);
        TextView visit_Type=(TextView)findViewById(R.id.visit_type);

        name.setText(v_name);
        visiter_email.setText(EmailId);
        visiter_mobile.setText(PhoneNo);
        visiter_address.setText(Address);
        refrence_by.setText(AnyReference);
        purpose_v.setText(Purposes);
        number_person.setText(TotalPassMember);
        comp_name.setText(OrganizationName);
        visiter_ofcemail.setText(OfficeEmail);
        visit_Type.setText(VisitTypes);

        btn_waiting=(Button)findViewById(R.id.button_waiting);
        btn_reject=(Button)findViewById(R.id.button_reject);
        btn_walkin=(Button)findViewById(R.id.button_walking);
        editText_remark=(EditText)findViewById(R.id.edit_remark);
        if(status.equals("3")){
            btn_waiting.setEnabled(false);
            btn_walkin.setEnabled(false);
            btn_waiting.setBackgroundColor(Color.GRAY);
            btn_walkin.setBackgroundColor(Color.GRAY);
        }else if(status.equals("1")){
            btn_waiting.setEnabled(false);
            btn_reject.setEnabled(false);
            btn_waiting.setBackgroundColor(Color.GRAY);
            btn_reject.setBackgroundColor(Color.GRAY);
        }
        btn_waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_update="2";
                remark=editText_remark.getText().toString();
                if(remark.length()==0){
                    remark="NA";
                }
                new Visitordatasubmit().execute();
               // Toast.makeText(getApplicationContext(), "btn_waiting", Toast.LENGTH_SHORT).show();
            }
        });
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_update="3";
                remark=editText_remark.getText().toString();
                if(remark.length()==0){
                    remark="NA";
                }
                if(status.equals("3")){
                     Toast.makeText(getApplicationContext(), "Visitor Already Rejected", Toast.LENGTH_SHORT).show();
                }else{
                    new Visitordatasubmit().execute();
                }

            }
        });
        btn_walkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_update="1";
                remark=editText_remark.getText().toString();
                if(remark.length()==0){
                    remark="NA";
                }
                if(status.equals("1")){
                    Toast.makeText(getApplicationContext(), "Visitor Already Walk In", Toast.LENGTH_SHORT).show();
                }else{
                    new Visitordatasubmit().execute();
                }
               // Toast.makeText(getApplicationContext(), "btn_walkin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSuccessDialog() {

        final Dialog dialog = new Dialog(Visitors_Details_Activity.this,R.style.Theme_AppCompat_Dialog_Alert);
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

                finish();
                dialog.dismiss();
            }
        });

        dialog.show();



    }

    private class Visitordatasubmit extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Visitors_Details_Activity.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Visitors_Details_Activity.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.VISITOR_APIUPDATE,Para(applicationController.getBranchcode(),applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getFyID(),applicationController.getUserID() ),"1");
            if(response!=null){
              status=1;
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
                    showSuccessDialog();
                    break;
                case -2:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_visit, "Visitor status not updated. Please Try Again.", Snackbar.LENGTH_LONG)
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
                    break;
                case -1:
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
            jsonParam.put("UpdatedBy", MeetWith);
            jsonParam.put("TransId", TransId);
            jsonParam.put("Response", status_update);
            jsonParam.put("Remark", remark);
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
