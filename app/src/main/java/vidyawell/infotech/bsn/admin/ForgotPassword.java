package vidyawell.infotech.bsn.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.InputFilter;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import vidyawell.infotech.bsn.admin.Const.Constant;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class ForgotPassword extends AppCompatActivity {

    EditText username,password;
    ImageView imageView_logologin;
    ApplicationControllerAdmin applicationController;
    Typeface typeface;
    SharedPreferences sharedpreferences;
    String school_code;
    String sch_code="",schoolcode_code="";
    Button login;
    LinearLayout layout_forgot_password;
    String userid,pasword;
    String massage,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        applicationController=(ApplicationControllerAdmin) getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.layout_forgot_password));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("ForgotPassword");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        sharedpreferences = getSharedPreferences("APPDATA", Context.MODE_PRIVATE);

        String sch_code=sharedpreferences.getString("sch_code","");
        String branch_code=sharedpreferences.getString("branch_code","");
        String school_logo=sharedpreferences.getString("school_logo","");
        applicationController.setschool_logo(school_logo);
        school_code=sch_code+branch_code;

        login=(Button)findViewById(R.id.login);
        layout_forgot_password=(LinearLayout)findViewById(R.id.layout_forgot_password);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        if(sch_code.equals("")){
            showdialog();
        }else {
            applicationController.setschoolCode(sch_code);
            applicationController.setBranchcode(branch_code);
        }

        imageView_logologin=findViewById(R.id.imageView_logologin);
        String imagerplace= ServerApiadmin.LOGO_API+applicationController.getschool_logo();
        imagerplace=imagerplace.replace("..","");
        imageView_logologin.setImageURI(Uri.parse(imagerplace));
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
                .into(imageView_logologin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sch_code=sharedpreferences.getString("sch_code","");
                String branch_code=sharedpreferences.getString("branch_code","");
                applicationController.setschoolCode(sch_code);
                applicationController.setBranchcode(branch_code);
                schoolcode_code=sch_code;
                userid=username.getText().toString().trim();
                pasword=password.getText().toString().trim();
                if(userid.equals("")){
                    Snackbar snackbar = Snackbar
                            .make(layout_forgot_password, "Enter User ID.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }else  if(pasword.equals("")){
                    Snackbar snackbar = Snackbar
                            .make(layout_forgot_password, "Registered Mobile No.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }else if(sch_code.equals("")){
                    showdialog();
                }else if(!Constant.isConnection(ForgotPassword.this)){
                    Snackbar snackbar = Snackbar
                            .make(layout_forgot_password, "Check Internet Connection.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }else{
                       new ForgotProcess().execute();
                }
            }
        });



    }

////////////////////////API Forgot Password//////////

    private class ForgotProcess extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ForgotPassword.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.FORGOT_PASSWORD,Para(userid,pasword),"1");
            if(response!=null){
                if (response.length()>2){
                    response=response.replace("\"","");
                    String[] separated = response.split("\\$");
                    String Code =separated[0];

                    if (Code.equalsIgnoreCase("1")) {
                        String Code1 =separated[1];
                        if (Code1.equalsIgnoreCase("Email")) {
                            massage = "Password has been sent to your registered email";
                            title="Password Sent";
                        } else {
                            massage = "Password has been sent to your registered mobile no";
                            title="Password Sent";

                        }
                        status=1;
                    }  else{

                        massage=Code;
                        title="Error!!";
                    status=1;
                }
                    }else{
                    status=-2;
                }
            }else{
                status=-3;
            }

            return status;
        }
        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            switch (s){

                case 1:
                    massagealert();

                    break;
                case -2:

                    Snackbar snackbar1 = Snackbar
                            .make(layout_forgot_password, "User Credentials are not Valid. Please Try Again.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();

                    break;
                case -1:

                    Snackbar snackbar = Snackbar
                            .make(layout_forgot_password, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;
                case -3:
                    progressDialog.dismiss();
                    Snackbar snackbarb = Snackbar
                            .make(layout_forgot_password, "Password has been not sent", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbarb.setActionTextColor(Color.RED);
                    snackbarb.show();
                    break;
                case -4:
                    progressDialog.dismiss();
                    errorDialog();
                    break;

            }
        }
    }

    public String Para(String userid,String pasword){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("UserName", userid);
            jsonParam.put("MobileNo", pasword);
           // jsonParam.put("bid", school_code);
            jsonParam.put("SchoolCode", applicationController.getschoolCode());
            jsonParam.put("BranchCode", applicationController.getBranchcode());
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }



    private void errorDialog() {

        final Dialog dialog = new Dialog(ForgotPassword.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.error_dialog);
        dialog.setTitle("");
        // set the custom dialog components - text, image and button
        TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        text_label.setText("Only for Admin Department");
        TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
        text_message.setText("User Credentials are not Valid. Please Try Again.");
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
                dialog.dismiss();
            }
        });

        dialog.show();


    }



    private void showdialog() {
        final Dialog dialog = new Dialog(ForgotPassword.this);
        dialog.setContentView(R.layout.dialog_schoolcode_admin);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final EditText text_schoolcode = (EditText) dialog.findViewById(R.id.editText_schcode);
        text_schoolcode.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(6)});
        TextView tagline = dialog.findViewById(R.id.textView_scode);
        TextView submitButton = dialog.findViewById(R.id.button_submitcode);
        TextView cancelButton =  dialog.findViewById(R.id.button_cancel);
        dialog.show();
        tagline.setTypeface(typeface);
        cancelButton.setTypeface(typeface);
        submitButton.setTypeface(typeface);
        text_schoolcode.setTypeface(typeface);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school_code=text_schoolcode.getText().toString().trim();
                if(school_code.equals("")){
                    Snackbar.make(v, "Enter School Code", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else if(school_code.length()==4){
                    sch_code=school_code.substring(0,2);
                    String branch_code=school_code.substring(2,4);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("sch_code", sch_code);
                    editor.putString("branch_code", branch_code);
                    editor.commit();
                    applicationController.setschoolCode(sch_code);
                    applicationController.setBranchcode(branch_code);

                    dialog.dismiss();
                }else if(school_code.length()==6){
                    sch_code=school_code.substring(0,4);
                    String branch_code=school_code.substring(4,6);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("sch_code", sch_code);
                    editor.putString("branch_code", branch_code);
                    editor.commit();
                    applicationController.setschoolCode(sch_code);
                    applicationController.setBranchcode(branch_code);

                    dialog.dismiss();
                }else{
                    Snackbar.make(v, "Enter Correct School Code", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



    }

    private void massagealert(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(massage);
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        });



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


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
