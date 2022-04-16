package vidyawell.infotech.bsn.admin;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.siyamed.shapeimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Department_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Document_Adapter;
import vidyawell.infotech.bsn.admin.Adapters.Upload_Document_Adapter;

import vidyawell.infotech.bsn.admin.Helpers.Department_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Document_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Upload_Document_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

import static vidyawell.infotech.bsn.admin.Dialy_Diary.REQUEST_ID_MULTIPLE_PERMISSIONS;

public class Upload_Document extends AppCompatActivity implements Upload_Document_Adapter.customButtonListener,Upload_Document_Adapter.customButtonListener2 {
    Spinner document_type,dipartment_type;
    Button btn_upload,btn_test;
    ApplicationControllerAdmin applicationController;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private int EMPDATA = 2;
    private int SELECT_PDF=3;
    private String userChoosenTask="Permit";
    BottomSheetDialog dialog;
    Typeface typeFace;
    Typeface font2;
    String profile_imageString;

    Upload_Document_Adapter adapter;
    RecyclerView document_list;
    List<Upload_Document_Helper> upload_document_helpers;
    Upload_Document_Helper item;
    private RecyclerView.LayoutManager layoutManager;
    ImageView product_image;
    ImageView delete_member,closebutton;
    Button bottun_louck;
    WebView file_type_web;
    Button button_emp_find;
    TextView click_status,text_close,text_delete;
    TextView department,employee,text_emp_name,text_designation,text_document_type,text_father_name;
    Typeface typeface;
    RelativeLayout layout_upload_document;
    String delete_document_id,pic_string,lock,filetype,login_type_id;
    SharedPreferences sharedpreferences;
    String Emp_code,Emp_fullname,Emp_id,Emp_father_name,Emp_pic,Emp_designation;
    RoundedImageView img_employee;
    LinearLayout admin_dashboard,document_upload_hide,name_hide_layout;
    String DOC_TYPE;
    String admin_and_user_login="0";
    Dialog dialogfulview;

    private static final int STORAGE_PERMISSION_CODE = 123;


    public  ArrayList<Document_Helper> document_helpers = new ArrayList<Document_Helper>();
    public  ArrayList<Department_Helper> department_helpers = new ArrayList<Department_Helper>();

    String department_id,document,document_name;
    String realpath;
    ProgressBar progressBar1;
    LinearLayout delete_layout,close_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__document);
        applicationController=(ApplicationControllerAdmin) getApplicationContext();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_upload_document));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sharedpreferences = getSharedPreferences("EMPLIST", Context.MODE_PRIVATE);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Upload Document");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        typeface=  Typeface.createFromAsset(getAssets(),"fonts/"+ServerApiadmin.FONT_DASHBOARD);

        document_type=(Spinner)findViewById(R.id.document_type);
        dipartment_type=(Spinner)findViewById(R.id.dipartment_type);
        btn_upload=(Button)findViewById(R.id.btn_upload);
        document_list=(RecyclerView)findViewById(R.id.document_list);
        button_emp_find=(Button)findViewById(R.id.button_emp_find);
        department=(TextView)findViewById(R.id.department);
        employee=(TextView)findViewById(R.id.employee);
        text_emp_name=(TextView)findViewById(R.id.text_emp_name);
        text_designation=(TextView)findViewById(R.id.text_designation);
        text_document_type=(TextView)findViewById(R.id.text_document_type);
        text_father_name=(TextView)findViewById(R.id.text_father_name);
        layout_upload_document=(RelativeLayout)findViewById(R.id.layout_upload_document);
        img_employee=(RoundedImageView)findViewById(R.id.img_employee);
        admin_dashboard=(LinearLayout)findViewById(R.id.admin_dashboard);
        document_upload_hide=(LinearLayout)findViewById(R.id.document_upload_hide);
        name_hide_layout=(LinearLayout)findViewById(R.id.name_hide_layout);
       // upload_document_helpers = new ArrayList<Upload_Document_Helper>();



       //  Toast.makeText(getApplicationContext(),applicationController.getLoginType(),Toast.LENGTH_LONG).show();




            if(applicationController.getLoginType().equalsIgnoreCase("4")){
                admin_dashboard.setVisibility(View.GONE);
                document_upload_hide.setVisibility(View.VISIBLE);
                name_hide_layout.setVisibility(View.VISIBLE);
                admin_and_user_login=applicationController.getActiveempcode();


            }else {
                admin_dashboard.setVisibility(View.VISIBLE);
                img_employee.setVisibility(View.GONE);
                name_hide_layout.setVisibility(View.GONE);
                document_upload_hide.setVisibility(View.GONE);

            }




          new SelectDocumentionAPI().execute();
         //new SelectDocumentionSaveImage().execute();
          new DocumentGETAPI().execute();

          new DepartmentAPI().execute();




        button_emp_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Upload_Document.this,EmployeeList.class);
                TextView      dipart_id= (TextView)findViewById(R.id.text_department_id);

                intent.putExtra("DepartmentId",dipart_id.getText().toString());
                startActivityForResult(intent,EMPDATA);

            }
        });


        dipartment_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               department_id    = ((TextView) view.findViewById(R.id.text_department_id)).getText().toString();


                // Toast.makeText(getApplicationContext(),document,Toast.LENGTH_LONG).show();
                // document_id= document_type.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        document_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                document    = ((TextView) view.findViewById(R.id.text_document_id)).getText().toString();
                document_name    = ((TextView) view.findViewById(R.id.document_name)).getText().toString();

               // Toast.makeText(getApplicationContext(),document,Toast.LENGTH_LONG).show();
               // document_id= document_type.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //////////////////////permission in camera and gallery photo ///////////////

        List<String> permissionList= Collections.emptyList();
        // = PermissionUtils.checkAndRequestPermissions(this);
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N) {
            permissionList=PermissionUtils.checkAndRequestPermissions(this);
            if (permissions(permissionList)) {
            }
        } else {
            if (permissions(permissionList)) {
                permissionList = PermissionUtils.checkAndRequestPermissions(this);
            }

        }

        //////////////////////End ///////////////////////////////////////





        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_imageString="";
                openDialog();

            }
        });

        /*btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adddocument();
            }
        });*/

    }

/////////////////////image upload///////////

    private void openDialog() {

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        TextView camera_sel = (TextView) view.findViewById(R.id.take_pic);
        TextView gallery_sel = (TextView) view.findViewById(R.id.take_library);
        TextView upload_pdf = (TextView) view.findViewById(R.id.upload_pdf);
        TextView close = (TextView) view.findViewById(R.id.close);
        camera_sel.setTypeface(typeFace);
        gallery_sel.setTypeface(typeFace);
        close.setTypeface(typeFace);
       // final boolean result= Utility.checkPermission(Upload_Document.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
        }

        camera_sel.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                userChoosenTask ="Take Photo";
               // if(result)
                    cameraIntent();
                    dialog.dismiss();
            }

        });
        gallery_sel.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                userChoosenTask ="Choose from Library";
               // if(result)
                    galleryIntent();
                    //pdf();
                dialog.dismiss();

            }

        });

        upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoosenTask ="Upload PDF";
                //if(result)
                    pdf();
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                    else if(userChoosenTask.equals("Upload PDF")) {
                        pdf();
                    }
                } else {
                    chkallowpermision();
                    //Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();
                }



                /*else if(userChoosenTask.equals("Upload PDF")) {
                    pdf();
                }else {

                }*/
                break;
        }
       /* switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                       //  pdf();
                } else if(userChoosenTask.equals("Upload PDF")) {
                    pdf();
                }else {

                }
                break;
        }*/
    }
  /*  private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Upload_Document.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(Upload_Document.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();
                        //pdf();
                }else if(items[item].equals("Upload PDF")){
                    userChoosenTask ="Upload PDF";
                    if(result)
                        pdf();
                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }*/



    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }



    private void pdf(){
        File file = new File(Environment.getExternalStorageDirectory(),
                ".pdf");
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), SELECT_PDF);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==EMPDATA){

            Emp_code=sharedpreferences.getString("EmployeeCode","0");
            Emp_fullname=sharedpreferences.getString("FullName","");
            Emp_id=sharedpreferences.getString("EmployeeId","");
            Emp_father_name=sharedpreferences.getString("FatherName","");
            Emp_pic=sharedpreferences.getString("EmployeePhotoPath","");
            Emp_designation=sharedpreferences.getString("Designation","");


            if(Emp_code.equalsIgnoreCase("0")){
          }else {
              admin_and_user_login=Emp_code;
              name_hide_layout.setVisibility(View.VISIBLE);
              img_employee.setVisibility(View.VISIBLE);
              document_upload_hide.setVisibility(View.VISIBLE);
              text_emp_name.setText(Emp_fullname);
              text_father_name.setText(Emp_father_name);
              text_designation.setText(Emp_designation);

              if(Emp_pic.equalsIgnoreCase("null")){
                  img_employee.setImageResource(R.drawable.ic_user_image);
              }else {
                  String imagerplace= ServerApiadmin.MAIN_IPLINK+Emp_pic;
                  imagerplace=imagerplace.replace("..","");
                  img_employee.setImageURI(Uri.parse(imagerplace));
                  Glide.get(getApplicationContext()).clearMemory();
                  Glide
                          .with(getApplicationContext())
                          .load(imagerplace)
                          .into(img_employee);
                  sharedpreferences = getSharedPreferences("EMPLIST", Context.MODE_PRIVATE);
                  SharedPreferences.Editor editor = sharedpreferences.edit();
                  editor.clear();
                  editor.commit();
                 // Toast.makeText(getApplicationContext(),Emp_code,Toast.LENGTH_LONG).show();
                 // new DocumentGETAPI().execute();
              }
              //Toast.makeText(getApplicationContext(),Emp_code,Toast.LENGTH_LONG).show();
          }
        }else{
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE)
                   onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA)
                    onCaptureImageResult(data);
                else if(requestCode==SELECT_PDF)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        pdfatteched(data);
                    }
            }
        }
        //new DocProfilestatus().execute();
        //new PersonalDocProfiledata().execute();
        new DocumentGETAPI().execute();
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte[] imageBytes = bytes.toByteArray();
        profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        DOC_TYPE="1";
        new SelectDocumentionSaveImage().execute();

    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte[] imageBytes = bytes.toByteArray();
        profile_imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        DOC_TYPE="1";
        new SelectDocumentionSaveImage().execute();
       // new PersonalDocProfileupdate().execute();
    }



      @RequiresApi(api = Build.VERSION_CODES.KITKAT)
      private void pdfatteched(Intent data) {
          realpath=convertMediaUriToPath(data.getData());
          if(realpath==null){
              enternal();
          }else {
             // Toast.makeText(this, realpath+"", Toast.LENGTH_LONG).show();
              DOC_TYPE="2";
              profile_imageString=getBase64FromPath(realpath);
              new SelectDocumentionSaveImage().execute();

          }

      }

    public String convertMediaUriToPath(Uri uri) {
        String path = null;
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj,  null, null, null);
        if(cursor ==null){
            enternal();
        }else {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();

        }
        return path;
    }



    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {
            File file = new File(String.valueOf(path));
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }



/////





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
    public void onButtonClickListner(int position, Upload_Document_Helper value) {


    }

    @Override
    public void onButtonClickListner2(int position, Upload_Document_Helper value) {


        delete_document_id=  value.getDocumentId();
        lock = value.getIsLocked();

        filetype =value.getIsFileType();

        login_type_id=value.getgetLoginType();


        pic_string = value.getDocumentPath();
        showdialog();

    }

/* if(adapter!=null){
        adapter=null;
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
                    break;*/

    ///////////////////////////////alertbox//////
    private void alert (){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure want to delete document?");
        alertDialogBuilder.setTitle("");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                      new DeleteAPI().execute();
                        dialogfulview.dismiss();
                      //  dialog.dismiss();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void lockedid(){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Document locked has been administrator");
        alertDialogBuilder.setTitle("Document locked");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }



    private void showdialog() {

        dialogfulview = new Dialog(Upload_Document.this,R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
        dialogfulview.setContentView(R.layout.dialog_product_details);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((LinearLayout)dialogfulview.findViewById(R.id.dialog_layout));
        dialogfulview.setCanceledOnTouchOutside(false);
        product_image=dialogfulview.findViewById(R.id.product_image);
       // text_product_name =dialog.findViewById(R.id.text_product_name);
        file_type_web =dialogfulview.findViewById(R.id.file_type_web);

        delete_member=dialogfulview.findViewById(R.id.delete_member);
        closebutton=dialogfulview.findViewById(R.id.closebutton);
        bottun_louck=dialogfulview.findViewById(R.id.bottun_louck);
        click_status=dialogfulview.findViewById(R.id.click_status);
        text_close=dialogfulview.findViewById(R.id.text_close);
        text_delete=dialogfulview.findViewById(R.id.text_delete);
        progressBar1=dialogfulview.findViewById(R.id.progressBar1);
        delete_layout=dialogfulview.findViewById(R.id.delete_layout);
        close_layout=dialogfulview.findViewById(R.id.close_layout);
        //bottun_louck.setTypeface(font2);
        text_close.setTypeface(font2);
        text_delete.setTypeface(font2);
        click_status.setTypeface(font2);
        bottun_louck.setTypeface(font2);
        dialogfulview.show();



        bottun_louck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(bottun_louck.getText().toString().equalsIgnoreCase("NOT APPROVED")) {
                   alert_verified();

               }else {
                   not_verified_alert();

               }
            }
        });



        if(login_type_id.equalsIgnoreCase("4")){
            bottun_louck.setVisibility(View.VISIBLE);
            click_status.setVisibility(View.GONE);
            bottun_louck.setEnabled(false);
        }else {
            bottun_louck.setVisibility(View.VISIBLE);

        }


        if(lock.equalsIgnoreCase("false")){
            bottun_louck.setBackgroundResource(R.drawable.round_button_red);
            bottun_louck.setText("Not Approved");
            click_status.setText("Click Here for Approval");
            delete_layout.setVisibility(View.VISIBLE);


        }else {
            bottun_louck.setBackgroundResource(R.drawable.round_button_green);
            click_status.setText("Document Status");
            bottun_louck.setText("Approved");
            delete_layout.setVisibility(View.GONE);

        }


     if(filetype.equalsIgnoreCase("1")){
         String imagerplace= ServerApiadmin.MAIN_IPLINK+pic_string;
         imagerplace=imagerplace.replace("..","");
         product_image.setImageURI(Uri.parse(imagerplace));
         Glide.get(getApplicationContext()).clearMemory();
         Glide
                 .with(getApplicationContext())
                 .load(imagerplace)
                 .apply(new RequestOptions()
                         .diskCacheStrategy(DiskCacheStrategy.NONE)
                         .skipMemoryCache(true))
                 .into(product_image);
         file_type_web.setVisibility(View.GONE);
         product_image.setVisibility(View.VISIBLE);

     }else {
         file_type_web.setVisibility(View.VISIBLE);
         product_image.setVisibility(View.GONE);
         String imagerplace= ServerApiadmin.MAIN_IPLINK+pic_string;
         imagerplace=imagerplace.replace("..","");
         //file_type_web.setWebChromeClient(new WebChromeClient());
         file_type_web.setWebViewClient(new myWebClient());
         file_type_web.getSettings().setJavaScriptEnabled(true);
         file_type_web.loadUrl("https://docs.google.com/gview?embedded=true&url="+imagerplace);

     }

        delete_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                dialogfulview.dismiss();
            }
        });


        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lock.equalsIgnoreCase("true")){
                    lockedid();
                }else {
                    alert();
                }

            }
        });





    }

    public void onProgressChanged(WebView view, int progress)
    {
        //Make the bar disappear after URL is loaded, and changes string to Loading...
        setTitle("Loading...");
        setProgress(progress * 100); //Make the bar disappear after URL is loaded

        // Return the app name after finish loading
        if(progress == 100)
            setTitle(R.string.app_name);
    }

    //////////////////////////select document type api///////////////////////////

    private class SelectDocumentionAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Upload_Document.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Upload_Document.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SELECT_DOCUMENT,Paradetails(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        document_helpers = new ArrayList<Document_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);

                            String   DocumentName=object.getString("DocumentName");
                            String   DocumentId=object.getString("DocumentId");

                            final Document_Helper sched = new Document_Helper();
                            sched.setDocumentName(DocumentName);
                            sched.setDocumentid(DocumentId);

                            document_helpers.add(sched);

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
                    Resources res = getResources();
                    Document_Adapter adapter = new Document_Adapter(getApplicationContext(), R.layout.spinner_document_type_item, document_helpers,res);
                    document_type.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_upload_document, "Examination Data Not Found", Snackbar.LENGTH_LONG)
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
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_upload_document, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Paradetails(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("Action","4");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }





    //////////////////////////image save ////////////////////


    private class SelectDocumentionSaveImage extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Upload_Document.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Upload_Document.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.IMAGE_SAVE,Paradetails(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID(),document,profile_imageString),"1");
           // Log.d("RES",response);
            if(response!=null){
                response=response.replace("\"","");
                if (response.equalsIgnoreCase("1")){
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    imagesave();
                    new DocumentGETAPI().execute();

                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_upload_document, document_name+" Document Already submitted", Snackbar.LENGTH_LONG)
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
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_upload_document, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Paradetails(String SchoolCode,String BranchCode,String SessionId,String FYId,String DocumentId,String DocumentPath){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",FYId);
            jsonParam.put("DocumentId",DocumentId);
            jsonParam.put("EmployeeCode",admin_and_user_login);
            jsonParam.put("DocumentPath",DocumentPath);
            jsonParam.put("LoginId",applicationController.getLoginType());
            jsonParam.put("Action","1");
            jsonParam.put("IsFileType",DOC_TYPE);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }




    ////////////////////////////delete api//////

    private class DeleteAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Upload_Document.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Upload_Document.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.IMAGE_SAVE,Paradetailsdelete(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID(),delete_document_id),"1");
            if(response!=null){
                response=response.replace("\"","");
                if (response.equalsIgnoreCase("3")){
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    new DocumentGETAPI().execute();
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_upload_document, "Document not deleted.Please try later", Snackbar.LENGTH_LONG)
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
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_upload_document, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Paradetailsdelete(String SchoolCode,String BranchCode,String SessionId,String FYId,String DocumentId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("FYId",FYId);
            jsonParam.put("DocumentId",DocumentId);
            jsonParam.put("EmployeeCode",admin_and_user_login);
            jsonParam.put("Action","3");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }



    ////////////////////////////////////get API list////////////////////////////






    public class DocumentGETAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Upload_Document.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Upload_Document.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.IMAGE_GETAPI,Paradetails3(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        upload_document_helpers = new ArrayList<Upload_Document_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);

                            String DocumentId=object.getString("DocumentId");
                            String DocumentName=object.getString("DocumentName");
                            String EmployeeCode=object.getString("EmployeeCode");
                            String EmpName=object.getString("EmpName");
                            String IsLocked=object.getString("IsLocked");
                            String DocumentPath=object.getString("DocumentPath");
                            String IsFileType=object.getString("IsFileType");
                            item = new Upload_Document_Helper(DocumentId,DocumentName,EmployeeCode,EmpName,IsLocked,DocumentPath,IsFileType,applicationController.getLoginType());
                            upload_document_helpers.add(item);
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
                    adapter = new  Upload_Document_Adapter(getApplicationContext(),R.layout.item_upload_document, upload_document_helpers);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    document_list.setLayoutManager(mLayoutManager);
                    adapter.setcustomButtonListener(Upload_Document.this);
                    adapter.setcustomButtonListener2(Upload_Document.this);
                    document_list.setAdapter(adapter);
                    GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);
                    document_list.setLayoutManager(manager);
                    adapter.notifyDataSetChanged();
                    break;
                case -2:
                    if(adapter!=null){
                        adapter=null;
                        document_list.setAdapter(adapter);
                    }
                    Snackbar snackbar1 = Snackbar
                            .make(layout_upload_document, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                case -1:
                  //  Toast.makeText(getApplicationContext(),admin_and_user_login,Toast.LENGTH_LONG).show();

                    if(adapter!=null){
                        adapter=null;
                        document_list.setAdapter(adapter);
                      /*  Intent intent = getIntent();
                        finish();
                        startActivity(intent);*/

                    }
                    break;
            }
        }
    }
    public String Paradetails3(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId",  SessionId);
            jsonParam.put("EmployeeCode",admin_and_user_login);
            jsonParam.put("Action","4");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }



    private void imagesave(){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Document has been uploaded successfully");
        alertDialogBuilder.setTitle("Success!");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }


    //////////////select department////////////////////////////


    private class DepartmentAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Upload_Document.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Upload_Document.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.SELECT_DEPARTMENT,Paradepart(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        department_helpers = new ArrayList<Department_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);

                            String   DepartmentName=object.getString("DepartmentName");
                            String   DepartmentId=object.getString("DepartmentId");
                            final Department_Helper sched = new Department_Helper();
                            sched.setDepartName(DepartmentName);
                            sched.setDepartmentid(DepartmentId);

                            department_helpers.add(sched);

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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    Resources res = getResources();
                    Department_Adapter adapter = new Department_Adapter(getApplicationContext(), R.layout.spinner_department_item, department_helpers,res);
                    dipartment_type.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_upload_document, "Department Data Not Found", Snackbar.LENGTH_LONG)
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
                case -1:
                    Snackbar snackbar1 = Snackbar
                            .make(layout_upload_document, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Paradepart(String SchoolCode,String BranchCode,String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("Action","4");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }



//////////////////////////Louck API//////////////////////////////

    private class LouckAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Upload_Document.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Upload_Document.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.IMAGE_SAVE,Paralouck(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID(),delete_document_id),"1");
            // Log.d("RES",response);
            if(response!=null){
                response=response.replace("\"","");
                if (response.equalsIgnoreCase("2")){
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
            progressDialog.dismiss();
            switch (s){
                case 1:
                    new DocumentGETAPI().execute();
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_upload_document, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_upload_document, "Network Problem. Please Try Again", Snackbar.LENGTH_LONG)
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
    public String Paralouck(String SchoolCode,String BranchCode,String SessionId,String FYId,String DocumentId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("LoginId",applicationController.getLoginType());
            jsonParam.put("DocumentId",DocumentId);
            jsonParam.put("EmployeeCode",admin_and_user_login);
            jsonParam.put("IsLocked","1");
            jsonParam.put("Action","2");
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


 private void alert_verified(){
     android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
     alertDialogBuilder.setMessage("Do You want to Approved this document.");
     alertDialogBuilder.setTitle("For document Approval");
     alertDialogBuilder.setPositiveButton("YES",
             new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface arg0, int arg1) {
                     new LouckAPI().execute();
                     dialogfulview.dismiss();
                 }
             });
     alertDialogBuilder.setNegativeButton("NO",
             new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface arg0, int arg1) {


                 }
             });


     final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
     alertDialog.show();
 }




 private void not_verified_alert(){
     android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
     alertDialogBuilder.setMessage("Document Already Approved");
     alertDialogBuilder.setTitle("Approved!");
     alertDialogBuilder.setPositiveButton("OK",
             new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface arg0, int arg1) {
                     dialogfulview.dismiss();
                 }
             });


     final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
     alertDialog.show();
 }


 ///////////////////Daynamic permission////////////////////////////////

    /*public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select PDF"), SELECT_PDF);

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }*/

    private void enternal(){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please select .pdf file from internal storage(File Manager)");
        alertDialogBuilder.setTitle("Error!");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar1.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar1.setVisibility(View.GONE);
        }
    }




    private boolean permissions(List<String> listPermissionsNeeded) {

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private  void  chkallowpermision(){
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Upload_Document.this).create();
        alertDialog.setTitle("Permission necessary");
        alertDialog.setMessage("External storage permission is necessary");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        List<String> permissionList = PermissionUtils.checkAndRequestPermissions(Upload_Document.this);

                        if (permissions(permissionList)) {

                            //dispatchTakePictureIntent();  // call your camera instead of this method
                        }

                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}


