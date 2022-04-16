package vidyawell.infotech.bsn.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Change_Password extends AppCompatActivity {

    LinearLayout layout_change_password;
    EditText old_password,new_password,retype_password;
    Button change_pass_submit;
    String password,newpassword;
    SharedPreferences  sharedpreferences ;
    ImageView imageView_nav_logo;
    Typeface typeface;
    ApplicationControllerAdmin applicationControllerAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin=(ApplicationControllerAdmin)getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.layout_change_password));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Change Password");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        old_password=(EditText)findViewById(R.id.old_password);
        new_password=(EditText)findViewById(R.id.new_password);
        retype_password=(EditText)findViewById(R.id.retype_password);
        change_pass_submit=(Button) findViewById(R.id.change_pass_submit);
        layout_change_password=(LinearLayout)findViewById(R.id.layout_change_password);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);

        imageView_nav_logo=findViewById(R.id.imageView_nav_logoch);
        String imagerplace= ServerApiadmin.LOGO_API+applicationControllerAdmin.getschool_logo();
        imagerplace=imagerplace.replace("..","");
        imageView_nav_logo.setImageURI(Uri.parse(imagerplace));
        Glide.get(getApplicationContext()).clearMemory();
        Glide
                .with(getApplicationContext())
                .load(imagerplace)
                .apply(
                        new RequestOptions()
                                .error(R.drawable.vidyawell_logo_2)
                                .fitCenter()
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView_nav_logo);


        change_pass_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(old_password.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(),"Enter Old Password",Toast.LENGTH_LONG).show();
                }else if (new_password.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(),"Enter New Password",Toast.LENGTH_LONG).show();

                }else if(retype_password.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "Enter Re Password", Toast.LENGTH_LONG).show();
                }else {
                    if (new_password.getText().toString().equals(retype_password.getText().toString())){
                        password =old_password.getText().toString();
                        newpassword =new_password.getText().toString();
                        new ChangePassword().execute();
                    }else{
                        Toast.makeText(getApplicationContext(), "New Password not matched", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });

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

    private void showSuccessDialog() {

        final Dialog dialog = new Dialog(Change_Password.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.success_dialog);
        dialog.setTitle("");
        // set the custom dialog components - text, image and button
        TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        text_label.setText("Password has been Changed Successfully");
        TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
        text_message.setText("Password has been Changed Successfully. Please login again");
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

                sharedpreferences = getSharedPreferences("APPDATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent= new Intent(Change_Password.this,Login_Activity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    //////////////////////////////////////////API Change Password////////////////////////////////////

    private class ChangePassword extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Change_Password.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(Change_Password.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.CHANGE_PASSWORD_API,Para(applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            if(response!=null){
                response=response.replace("\"", "");
                if(response.equals("1")){

                    status=1;
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
           // progressDialog.dismiss();
            switch (s){
                case 1:
                    //showSuccessDialog();
                    new ChangeNewPassword().execute();
                    break;
                case -2:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_change_password, "Old Password is Not Matched", Snackbar.LENGTH_LONG)
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
                            .make(layout_change_password, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
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

    public String Para(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {

            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("UserId", applicationControllerAdmin.getUserID());
            jsonParam.put("Password", password);
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    /////////////////////////////////////////////////////Change New Password///////////////////

    private class ChangeNewPassword extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Change_Password.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Change_Password.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationControllerAdmin.getServicesapplication()+ServerApiadmin.CHANGE_PASSWORD_NEW_API,Para1(applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode(),applicationControllerAdmin.getSeesionID()),"1");
            if(response!=null){
                response=response.replace("\"", "");
                if(response.equals("2")){
                    status=1;
                }else {
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
                    showSuccessDialog();
                    break;
                case -2:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_change_password, "Password not Changed. Please Try Again.", Snackbar.LENGTH_LONG)
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
                            .make(layout_change_password, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
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

    public String Para1(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {

            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("UserId", applicationControllerAdmin.getUserID());
            jsonParam.put("PassCode", newpassword);
            jsonParam.put("Password", password);
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


}
