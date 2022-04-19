package vidyawell.infotech.bsn.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import vidyawell.infotech.bsn.admin.Const.Constant;
import vidyawell.infotech.bsn.admin.Const.NotificationUtils;
import vidyawell.infotech.bsn.admin.DAO.UserDao;
import vidyawell.infotech.bsn.admin.Database.RoomDatabaseClass;
import vidyawell.infotech.bsn.admin.Entities.MainUrlData;
import vidyawell.infotech.bsn.admin.Entities.UserCred;
import vidyawell.infotech.bsn.admin.Entities.UserData;
import vidyawell.infotech.bsn.admin.Entities.UserPermissions;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.Config;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Login_Activity extends AppCompatActivity implements Animation.AnimationListener{

    EditText username,password;
    Button login;
    TextView forgetpassword;
    String userid,pasword,school_code,theme_code,schoolcode_code="",sch_code="";
    String userId,loginTypeId,sessionId,fyId;
    Typeface typeface;
    SharedPreferences sharedpreferences;
    ApplicationControllerAdmin applicationController;
    LinearLayout loginlayout;
    Context context;
    RoomDatabaseClass databaseClass;
    UserDao userDao;
    String regId,InstitutionType,BranchWebsite,AVer,ProductTypeId;
    CheckBox check_remember;
    String user_status,school_logo,school_name;
    ImageView imageView_logologin;
    CheckBox check_showpassword;
    Animation animMoveToTop;
    Animation animright,animfadein,animfadeout;
    ImageView imageView_help;
    LinearLayout layout_animtion,layout_aminationicon;
    RelativeLayout relative_fullmessage;
    String BranchWebsiteservices;
    int count=0;

    private static final String TAG = Login_Activity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
       // overridePendingTransition(R.anim.fadein, R.anim.fadeout);
       // final View img_logo=findViewById(R.id.image_app_logo);
        applicationController=(ApplicationControllerAdmin) getApplication();
        databaseClass=Room.databaseBuilder(getApplicationContext(), RoomDatabaseClass.class,"databse_vidyawell").allowMainThreadQueries().build();

        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.login_layout));
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);
        sharedpreferences = getSharedPreferences("APPDATA", Context.MODE_PRIVATE);
        loginlayout=(LinearLayout)findViewById(R.id.login_layout) ;
        username=(EditText)findViewById(R.id.username);
      // username.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(30)});
        password=(EditText)findViewById(R.id.password);
        forgetpassword=(TextView)findViewById(R.id.forgetpassword);
        login=(Button)findViewById(R.id.login);
        imageView_help=findViewById(R.id.imageView_help);
        imageView_help.setImageResource(R.drawable.help);
        layout_animtion=findViewById(R.id.layout_animtion);
        layout_aminationicon=findViewById(R.id.layout_aminationicon);
        relative_fullmessage=findViewById(R.id.relative_fullmessage);
        check_remember=(CheckBox)findViewById(R.id.check_remember);
        String sch_code=sharedpreferences.getString("sch_code","");
        String branch_code=sharedpreferences.getString("branch_code","");
        String school_logo=sharedpreferences.getString("school_logo","");
        applicationController.setschool_logo(school_logo);
        school_code=sch_code+branch_code;
        new GetULR().execute();
        new Getservices().execute();
        if(sch_code.equals("")){
            showdialog();
        }else {
            applicationController.setschoolCode(sch_code);
            applicationController.setBranchcode(branch_code);
        }
        username.setInputType(
                InputType.TYPE_CLASS_TEXT|
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS
        );
        //username.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        imageView_logologin=findViewById(R.id.imageView_logologin);
        String imagerplace= ServerApiadmin.LOGO_API+applicationController.getschool_logo();
        String api = applicationController.getschool_logo();
        imagerplace=imagerplace.replace("..","");
        imageView_logologin.setImageURI(Uri.parse(imagerplace));
        Glide.get(getApplicationContext()).clearMemory();
        Glide
                .with(getApplicationContext())
                .load(imagerplace)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
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
        username.addTextChangedListener(textWatcher);
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
                            .make(loginlayout, "Enter User ID.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }else if(pasword.equals("")){
                    Snackbar snackbar = Snackbar
                            .make(loginlayout, "Enter Password.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }else if(sch_code.equals("")){
                    showdialog();
                }else if(!Constant.isConnection(Login_Activity.this)){
                    Snackbar snackbar = Snackbar
                            .make(loginlayout, "Check Internet Connection.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   // Toast.makeText(getApplicationContext(), "disconnected!!", Toast.LENGTH_SHORT).show();
                                    userDao=databaseClass.userDao();
                                    List<UserCred> userCreds= userDao.getUserCredFromDatabase();
                                    if (userid.trim().equals(userCreds.get(0).userId.trim()) && pasword.trim().equals(userCreds.get(0).password) && userCreds.get(0).authorized.equals("authorized")){
                                        setPermissionOffline();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Something went wrong!! please retry", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }else{

                        new LoginProcess().execute();
                      //  Toast.makeText(getApplicationContext(), "connected!!", Toast.LENGTH_SHORT).show();




                }
            }
        });
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                   // Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    //txtMessage.setText(message);  android:text="BSN/4003/1718"  android:text="20100000005"
                }
            }
        };

        displayFirebaseRegId();
        String userId_save=sharedpreferences.getString("userId_save","");
        String Password_save=sharedpreferences.getString("Password_save","");
        boolean check_box=sharedpreferences.getBoolean("checkbox",false);
        if(userId_save.length()>2){
            username.setText(userId_save);
            password.setText(Password_save);
            check_remember.setChecked(check_box);
        }
        check_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("userId_save", username.getText().toString().trim());
                    editor.putString("Password_save", password.getText().toString().trim());
                    editor.putBoolean("checkbox",true);
                    editor.commit();
                    String userId_save=sharedpreferences.getString("userId_save","");
                    if(userId_save.length()<1){
                        check_remember.setChecked(false);
                        Snackbar snackbar = Snackbar
                                .make(loginlayout, "Enter User Id", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        snackbar.setActionTextColor(Color.RED);
                        snackbar.show();
                    }
                }else{
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("userId_save", "");
                    editor.putString("Password_save", "");
                    editor.putBoolean("checkbox",false);
                    editor.commit();
                    username.setText("");
                    password.setText("");
                }
            }
        });
        check_showpassword=findViewById(R.id.check_showpassword);
        check_showpassword.setText("Show password");
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        check_showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    check_showpassword.setText("Hide password");
                    // edtx_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    check_showpassword.setText("Show password");
                    // edtx_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login_Activity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        animMoveToTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.transalate_anim);
        animright = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.transalate_anim_right);
        animfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeintwo);
        animfadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeoutone);
        layout_aminationicon.setVisibility(View.VISIBLE);
        relative_fullmessage.setVisibility(View.GONE);
        layout_animtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0){
                    count=1;
                    layout_aminationicon.setVisibility(View.VISIBLE);
                    imageView_help.startAnimation(animMoveToTop);
                }else{
                    layout_aminationicon.setVisibility(View.VISIBLE);
                    count=0;
                    imageView_help.startAnimation(animright);
                }
                //  Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
            }
        });
        animMoveToTop.setAnimationListener(this);
        animright.setAnimationListener(this);
    }

    private void setPermissionOffline() {
        userDao=databaseClass.userDao();
        List<UserPermissions> userPermissionsList=userDao.getUserPermissionFromDatabase();
        List<MainUrlData> mainUrlData=userDao.getMainUrlData();

        applicationController.setTimeTable("0");
        applicationController.setClassAttendance("0");
        applicationController.setHomework("0");
        applicationController.setCalendar("0");
        applicationController.setBusesRoute("0");
        applicationController.setVideoGallery("0");
        applicationController.setVisitors("0");
        applicationController.setComplain("0");
        applicationController.setNotice("0");
        applicationController.setCircular("0");
        applicationController.setGuardianComplain("0");
        applicationController.setPhotoGallery("0");
        applicationController.setLeaveapplication("0");
        applicationController.setLiveClass("0");
        applicationController.setAppversion("0");

     if (userPermissionsList.get(0).TimeTable!=null){
         applicationController.setTimeTable(userPermissionsList.get(0).TimeTable);
     }
       if (userPermissionsList.get(0).ClassAttendance!=null){
         applicationController.setClassAttendance(userPermissionsList.get(0).ClassAttendance);
     }
       if (userPermissionsList.get(0).Homework!=null){
         applicationController.setHomework(userPermissionsList.get(0).Homework);
     }
       if (userPermissionsList.get(0).Calendar!=null){
         applicationController.setCalendar(userPermissionsList.get(0).Calendar);
     }
       if (userPermissionsList.get(0).BusesRoute!=null){
         applicationController.setBusesRoute(userPermissionsList.get(0).BusesRoute);
     }
       if (userPermissionsList.get(0).VideoGallery!=null){
         applicationController.setVideoGallery(userPermissionsList.get(0).VideoGallery);
     }
       if (userPermissionsList.get(0).Visitors!=null){
         applicationController.setVisitors(userPermissionsList.get(0).Visitors);
     }
       if (userPermissionsList.get(0).Complain!=null){
         applicationController.setComplain(userPermissionsList.get(0).Complain);
     }
       if (userPermissionsList.get(0).Notice!=null){
         applicationController.setNotice(userPermissionsList.get(0).Notice);
     }
       if (userPermissionsList.get(0).Circular!=null){
         applicationController.setCircular(userPermissionsList.get(0).Circular);
     }
       if (userPermissionsList.get(0).GuardianComplain!=null){
         applicationController.setGuardianComplain(userPermissionsList.get(0).GuardianComplain);
     }
       if (userPermissionsList.get(0).PhotoGallery!=null){
         applicationController.setPhotoGallery(userPermissionsList.get(0).PhotoGallery);
     }
       if (userPermissionsList.get(0).EmployeeLeaveApproval!=null){
         applicationController.setLeaveapplication(userPermissionsList.get(0).EmployeeLeaveApproval);
     }
       if (userPermissionsList.get(0).LiveClass!=null){
         applicationController.setLiveClass(userPermissionsList.get(0).LiveClass);
     }
       if (mainUrlData.get(0).aVer!=null){
           applicationController.setAppversion(mainUrlData.get(0).aVer);
       }

       setUserDataOffline();


    }

    private void setUserDataOffline() {
        userDao =databaseClass.userDao();
        List<UserData> userDataList=userDao.getUserDataFromDatabase();
        applicationController.setLoginType(userDataList.get(0).loginTypeId);
        applicationController.setschooltype(userDataList.get(0).InstitutionType);
        startActivity(new Intent(Login_Activity.this,MainActivity_Admin.class));
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(check_remember.isChecked()==true){
                check_remember.setChecked(false);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private class LoginProcess extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Login_Activity.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
           // Log.d("TAG", "doInBackground:login process 363 "+applicationController.getServicesapplication()+ServerApiadmin.LOGIN_API+"//////"+Para(userid,pasword,applicationController.getschoolCode()+applicationController.getBranchcode()));
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.LOGIN_API,Para(userid,pasword,applicationController.getschoolCode()+applicationController.getBranchcode()),"1");
        //    Log.d("TAG", "doInBackground:user data "+response);
            String api =applicationController.getServicesapplication()+ServerApiadmin.LOGIN_API;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        JSONObject jsonobject = jsonArray.getJSONObject(0);
                        userId=jsonobject.getString("userid");
                        fyId=jsonobject.getString("fyId");
                        sessionId=jsonobject.getString("sessionId");
                        user_status=jsonobject.getString("Active");
                        if(userId.equalsIgnoreCase("null") || userId.equals("")){
                            status=-2;
                        }else{
                            if(user_status.equals("0")){
                                status=-3;
                            }else{
                                    loginTypeId=jsonobject.getString("loginTypeId");
                                if(loginTypeId.equals("4") || loginTypeId.equals("2") ){
                                    sessionId=jsonobject.getString("sessionId");
                                    fyId=jsonobject.getString("fyId");
                                    school_logo=jsonobject.getString("BranchLogo");
                                    school_name=jsonobject.getString("BranchName");
                                    InstitutionType=jsonobject.getString("InstitutionType");
                                    ProductTypeId=jsonobject.getString("ProductTypeId");

                                    status=1;
                                }else{
                                    status=-4;
                                }
                            }
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
            switch (s){
                case 1:
                    userDao=databaseClass.userDao();
                    userDao.insertUserData(new UserData(userid,loginTypeId,sessionId,fyId,"nun","nun","nun","nun",school_name,school_logo,"nun","nun",InstitutionType,ProductTypeId));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("userId", userid);
                    editor.putString("Password", pasword);
                    editor.putString("loginTypeId", loginTypeId);
                    editor.putString("sessionId", sessionId);
                    editor.putString("ProductTypeId", ProductTypeId);
                    editor.putString("fyId", fyId);
                    editor.putString("school_logo", school_logo);
                    editor.commit();

                    applicationController.setUserID(userId);
                    applicationController.setLoginType(loginTypeId);
                    applicationController.setSeesionID(sessionId);
                    applicationController.setFyID(fyId);
                    applicationController.setschool_name(school_name);
                    applicationController.setschool_logo(school_logo);
                    applicationController.setschooltype(InstitutionType);
                    applicationController.setProductTypeId(ProductTypeId);
                    new PermissionAPI().execute();
                    progressDialog.dismiss();
                    break;
                case -2:
                    progressDialog.dismiss();
                    Snackbar snackbar1 = Snackbar
                            .make(loginlayout, "User Credentials are not Valid. Please Try Again.", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   /* Snackbar snackbar1 = Snackbar.make(loginlayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();*/
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                    sharedpreferences = getSharedPreferences("APPDATA", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedpreferences.edit();
                    editor1.clear();
                    editor1.commit();
                    break;
                case -1:
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(loginlayout, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
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
                            .make(loginlayout, "Your Id is Inactive. Please Contact to Administrator", Snackbar.LENGTH_LONG)
                            .setAction("Done", new View.OnClickListener() {
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

    public String Para(String userid,String pasword,String school_code){
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("username", userid);
            jsonParam.put("password", pasword);
            jsonParam.put("bid", school_code);
            jsonParam.put("AccessMode", "M");
            jsonParam.put("GSMID", regId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam.toString();
    }

    private void showdialog() {
        final Dialog dialog = new Dialog(Login_Activity.this);
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
                    new GetULR().execute();
                    new Getservices().execute();
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
                   // new GetULR().execute();
                    new Getservices().execute();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        }
    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));
        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    private void errorDialog() {

        final Dialog dialog = new Dialog(Login_Activity.this,R.style.Theme_AppCompat_Dialog_Alert);
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


    private class PermissionAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this);
        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(Login_Activity.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.PERMISSION_API,Para(applicationController.getschoolCode(),applicationController.getUserID(),applicationController.getSeesionID(),applicationController.getBranchcode()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        ////////////////////////////////
                        applicationController.setTimeTable("0");
                        applicationController.setClassAttendance("0");
                        applicationController.setHomework("0");
                        applicationController.setCalendar("0");
                        applicationController.setBusesRoute("0");
                        applicationController.setVideoGallery("0");
                        applicationController.setVisitors("0");
                        applicationController.setComplain("0");
                        applicationController.setNotice("0");
                        applicationController.setCircular("0");
                        applicationController.setGuardianComplain("0");
                        applicationController.setPhotoGallery("0");
                        applicationController.setLeaveapplication("0");
                        applicationController.setLiveClass("0");
                        ////////////////////////////////////////////////////
                        JSONArray array= new JSONArray(response);
                     //   Log.d("TAG", "doInBackground:permissions  "+response);
                        // student_query_helpers = new ArrayList<Student_Query_Helper>();
                        UserPermissions userPermissions=new UserPermissions();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String PermissionFor=object.getString("PermissionFor");
                            String NameForMobile=object.getString("NameForMobile");
                            String IsPermit=object.getString("IsPermit");
                            String SubMenuId=object.getString("SubMenuId");
                            NameForMobile=NameForMobile.replace(" ","");

                            if(NameForMobile.equalsIgnoreCase("TimeTable")){
                                applicationController.setTimeTable(IsPermit);
                                userPermissions.setTimeTable(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("ClassAttendance")){
                                applicationController.setClassAttendance(IsPermit);
                                userPermissions.setClassAttendance(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("Homework")){
                                applicationController.setHomework(IsPermit);
                                userPermissions.setHomework(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("Calendar")){
                                applicationController.setCalendar(IsPermit);
                                userPermissions.setCalendar(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("BusesRoute")){
                                applicationController.setBusesRoute(IsPermit);
                                userPermissions.setBusesRoute(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("VideoGallery")){
                                applicationController.setVideoGallery(IsPermit);
                                userPermissions.setVideoGallery(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("Visitors")){
                                applicationController.setVisitors(IsPermit);
                                userPermissions.setVisitors(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("Complain")){
                                applicationController.setComplain(IsPermit);
                                userPermissions.setComplain(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("Notice")){
                                applicationController.setNotice(IsPermit);
                                userPermissions.setNotice(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("Circular")){
                                applicationController.setCircular(IsPermit);
                                userPermissions.setCircular(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("GuardianComplain")){
                                applicationController.setGuardianComplain(IsPermit);
                                userPermissions.setGuardianComplain(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("PhotoGallery")){
                                applicationController.setPhotoGallery(IsPermit);
                                userPermissions.setPhotoGallery(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("EmployeeLeaveApproval")){
                                applicationController.setLeaveapplication(IsPermit);
                                userPermissions.setEmployeeLeaveApproval(IsPermit);
                            }else if(NameForMobile.equalsIgnoreCase("LiveClass")){
                                applicationController.setLiveClass(IsPermit);
                                userPermissions.setLiveClass(IsPermit);
                            }
                            userDao=databaseClass.userDao();
                            userDao.deleteUserPermissions();
                            userDao.insertUserPermission(userPermissions);
                            status=1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-1;
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
            //progressDialog.dismiss();
            switch (s){
                case 1:
                   userDao=databaseClass.userDao();
                   userDao.deleteRowInUserCred(applicationController.getUserID());
                    userDao.insertUserCred(new UserCred(applicationController.getServicesapplication(),applicationController.getschoolCode()+applicationController.getBranchcode(),userid,pasword,"authorized"));
                    Intent intent= new Intent(Login_Activity.this,MainActivity_Admin.class);
                    startActivity(intent);
                    finish();

                    break;
                case -1:
                    applicationController.setTimeTable("0");
                    applicationController.setClassAttendance("0");
                    applicationController.setHomework("0");
                    applicationController.setCalendar("0");
                    applicationController.setBusesRoute("0");
                    applicationController.setVideoGallery("0");
                    applicationController.setVisitors("0");
                    applicationController.setComplain("0");
                    applicationController.setNotice("0");
                    applicationController.setCircular("0");
                    applicationController.setGuardianComplain("0");
                    applicationController.setPhotoGallery("0");
                    applicationController.setLeaveapplication("0");
                    applicationController.setLiveClass("0");
                    Intent intent2= new Intent(Login_Activity.this,MainActivity_Admin.class);
                    startActivity(intent2);
                    finish();


                    break;
            }
        }
    }
    public String Para(String SchoolCode, String LoginId, String SessionId, String BranchCode){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("LoginId", LoginId);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam1.put("obj", jsonParam);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }

    private class GetULR extends AsyncTask<String, String, Integer> {
        //   ProgressDialog progressDialog = new ProgressDialog(Login.this);
        @Override
        protected void onPreExecute() {
            // progressDialog = ProgressDialog.show(Login.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
         //   Log.d("TAG", "doInBackground:Get Url "+applicationController.getServicesapplication()+ServerApiadmin.GETURL_API+"/////"+Paracode(school_code));
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.GETURL_API,Paracode(school_code),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        UserDao userDao=databaseClass.userDao();

                        JSONArray jsonArray= new JSONArray(response);
                        JSONObject jsonobject = jsonArray.getJSONObject(0);
                        BranchWebsite=jsonobject.getString("BranchWebsite");
                        AVer=jsonobject.getString("AVer");
                        String BranchLat=jsonobject.getString("BranchLat");
                        String BranchLog=jsonobject.getString("BranchLog");
                        String BranchRedius=jsonobject.getString("BranchRedius");
                        applicationController.setBranchLat(BranchLat);
                        applicationController.setBranchLon(BranchLog);
                        applicationController.setBranchRedius(BranchRedius);
                        applicationController.setAppversion(AVer);
                        userDao.insertUrlData(new MainUrlData(jsonobject.getString("BranchWebsite"),jsonobject.getString("AVer"),jsonobject.getString("IVer"),jsonobject.getString("InstitutionType"),jsonobject.getString("BranchLat"),jsonobject.getString("BranchLog"),jsonobject.getString("BranchRedius"),jsonobject.getString("IsHeadOffice"),jsonobject.getString("GUrl")));
                   //     Log.d("TAG", "doInBackground:Main Url data inserted ");
                        status=1;
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
                    ServerApiadmin.seturl("http://"+BranchWebsite);
                    applicationController.setAppversion(AVer);

                    break;
                case -2:

                    break;
                case -1:
                    Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Paracode(String school_code){
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("Action", "1");
            jsonParam.put("BranchCode", school_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam.toString();
    }


    private class Getservices extends AsyncTask<String, String, Integer> {
           ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this);
        @Override
        protected void onPreExecute() {
             progressDialog = ProgressDialog.show(Login_Activity.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(ServerApiadmin.API_SERV,Paraser(school_code),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        JSONObject jsonobject = jsonArray.getJSONObject(0);
                        BranchWebsiteservices=jsonobject.getString(ServerApiadmin.API_SDKUrl);
                        status=1;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-1;
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
                    applicationController.setServicesapplication("http://"+BranchWebsiteservices);
                    new GetULR().execute();

                    break;
                case -2:

                    break;
                case -1:
                  //  Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Paraser(String school_code){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put(ServerApiadmin.API_CONURL, school_code);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }




    @Override
    public void onAnimationStart(Animation animation) {
        animfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeintwo);
        animfadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeoutone);
        if(count==0){
            relative_fullmessage.setVisibility(View.GONE);
            relative_fullmessage.setAnimation(animfadeout);
        }
    }
    @Override
    public void onAnimationEnd(Animation animation) {
        animfadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeintwo);
        animfadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeoutone);
        if(count==1){
            relative_fullmessage.setAnimation(animfadein);
            relative_fullmessage.setVisibility(View.VISIBLE);
            layout_aminationicon.setVisibility(View.GONE);
        }else{
            relative_fullmessage.setVisibility(View.GONE);
            relative_fullmessage.setAnimation(animfadeout);
        }
    }
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
