package vidyawell.infotech.bsn.admin;

import android.Manifest;
import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import java.util.Collections;
import java.util.List;
import java.util.Random;



import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;


//implements View.OnClickListener,
//        View.OnTouchListener

public class DownloadImageHomework extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener  {
    ImageView image_uploadwomework_1,image_uploadwomework_2,image_uploadwomework_3,image_uploadwomework_4,image_uploadwomework_5,image_uploadwomework_6,image_uploadwomework_7,image_uploadwomework_8,image_uploadwomework_9,image_uploadwomework_10;
    ApplicationControllerAdmin applicationController;
    SharedPreferences sharedprefer;
    Typeface typeface;
    //String attachment1,attachment2,attachment3,attachment4,attachment5,attachment6,attachment7,attachment8,attachment9,attachment10,Subject;
    TextView txt_download;
    RelativeLayout layout_download_image_homework;
    String TransId,StudentCode,TopicName,VoucherNo,IsSubmitted;
    String Attachmentimg,Attachment2,Attachment3,Attachment4,Attachment5,Attachment6,Attachment7,Attachment8,Attachment9,Attachment10;
    WebView img_web_show;
    ProgressBar progressBar1;
    ProgressDialog pd;
    String Attachment,  userAgent,  contentDisposition,  mimetype;
    long contentLength;
   String  file_url;
   LinearLayout ll_attach_1,ll_attach_2,ll_attach_3,ll_attach_4,ll_attach_5,ll_attach_6,ll_attach_7,ll_attach_8,ll_attach_9,ll_attach_10;
    String remarks;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    int id = 1;
    AppCompatButton txt_remark_teacher,re_submit;
    String resubmit_code;
    Button rotate,dra_image;
    Bitmap bmp;
    Bitmap alteredBitmap;
    String drawimage;

    Canvas canvas;
    Paint paint;
    Matrix matrix;
    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    List<String> permissionList = Collections.emptyList();
    ImageView choosenImageView;
    Button choosePicture;
    Button savePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image_homework);
        applicationController=(ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_download_image_homework));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Download File");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        img_web_show=(WebView)findViewById(R.id.img_web_show);
        layout_download_image_homework=(RelativeLayout)findViewById(R.id.layout_download_image_homework);
        txt_download=(TextView)findViewById(R.id.txt_download);
        txt_remark_teacher=(AppCompatButton) findViewById(R.id.txt_remark_teacher);
        re_submit=(AppCompatButton) findViewById(R.id.re_submit);
        rotate=(Button)findViewById(R.id.button1);
        dra_image=(Button)findViewById(R.id.dra_image);
        choosenImageView = (ImageView)findViewById(R.id.ChoosenImageView);
        choosePicture = (Button)findViewById(R.id.ChoosePictureButton);
        savePicture = (Button) findViewById(R.id.SavePictureButton);

        Intent in=getIntent();
        TransId=in.getStringExtra("TransId");
        StudentCode=in.getStringExtra("StudentCode");
        TopicName=in.getStringExtra("TopicName");
        VoucherNo=in.getStringExtra("VoucherNo");
        IsSubmitted=in.getStringExtra("IsSubmitted");
       /* attachment2=in.getStringExtra("attachment2");
        attachment3=in.getStringExtra("attachment3");
        attachment4=in.getStringExtra("attachment4");
        attachment5=in.getStringExtra("attachment5");
        attachment6=in.getStringExtra("attachment6");
        attachment7=in.getStringExtra("attachment7");
        attachment8=in.getStringExtra("attachment8");
        attachment9=in.getStringExtra("attachment9");
        attachment10=in.getStringExtra("attachment10");*/
        //Subject=in.getStringExtra("Subject");

        //Runtime External storage permission for saving download files
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }
        }
        //img_web_show.setOnTouchListener(this);


        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        img_web_show.setWebViewClient(new myWebClient());
        img_web_show.getSettings().setJavaScriptEnabled(true);
        img_web_show.getSettings().setSupportZoom(true);
        img_web_show.getSettings().setBuiltInZoomControls(true);
        img_web_show.getSettings().setDisplayZoomControls(true);
        img_web_show.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        img_web_show.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        img_web_show.getSettings().setLoadWithOverviewMode(true);
        img_web_show.getSettings().setUseWideViewPort(true);


//////////////////////download////
        img_web_show.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        img_web_show.getSettings().setLoadsImagesAutomatically(true);
        img_web_show.getSettings().setAppCacheEnabled(true);
        img_web_show.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        img_web_show.getSettings().setDomStorageEnabled(true);
        img_web_show.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        img_web_show.getSettings().setUseWideViewPort(true);
        img_web_show.getSettings().setSavePassword(true);
        img_web_show.getSettings().setSaveFormData(true);
        img_web_show.getSettings().setEnableSmoothTransition(true);


        ll_attach_1=(LinearLayout) findViewById(R.id.ll_attach_1);
        ll_attach_2=(LinearLayout)findViewById(R.id.ll_attach_2);
        ll_attach_3=(LinearLayout)findViewById(R.id.ll_attach_3);
        ll_attach_4=(LinearLayout)findViewById(R.id.ll_attach_4);
        ll_attach_5=(LinearLayout)findViewById(R.id.ll_attach_5);
        ll_attach_6=(LinearLayout)findViewById(R.id.ll_attach_6);
        ll_attach_7=(LinearLayout)findViewById(R.id.ll_attach_7);
        ll_attach_8=(LinearLayout)findViewById(R.id.ll_attach_8);
        ll_attach_9=(LinearLayout)findViewById(R.id.ll_attach_9);
        ll_attach_10=(LinearLayout)findViewById(R.id.ll_attach_10);



        progressBar1.setVisibility(View.GONE);

        if (permissions(permissionList) == false) {
            permissionList = PermissionUtils.checkAndRequestPermissions(this);
            permissions(permissionList);
            //flag=1;
        }

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_web_show.setRotation((float) 90.0); // It will rotate your image in 90 degree

            }
        });

        dra_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDrawImage();
            }
        });


        if(IsSubmitted.equalsIgnoreCase("Verified")){
            ////visible
            txt_remark_teacher.setVisibility(View.GONE);
        }else {
            ////  gone

            txt_remark_teacher.setVisibility(View.VISIBLE);
        }




        txt_remark_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogRemark();
            }
        });


        re_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // resubmit_code ="3";
                new RESUBMITTEDAPIINSERTSUBMIT().execute();
            }
        });

        txt_download.setVisibility(View.GONE);
        rotate.setVisibility(View.GONE);
        txt_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mBuilder = new NotificationCompat.Builder(DownloadImageHomework.this);
                mBuilder.setContentTitle("File Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.drawable.download_button);
                // Start a the operation in a background thread
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                int incr;
                                // Do the "lengthy" operation 20 times
                                for (incr = 0; incr <= 100; incr+=5) {
                                    // Sets the progress indicator to a max value, the current completion percentage and "determinate" state
                                    mBuilder.setProgress(100, incr, false);
                                    // Displays the progress bar for the first time.
                                    mNotifyManager.notify(id, mBuilder.build());
                                    // Sleeps the thread, simulating an operation
                                    try {
                                        // Sleep for 1 second
                                        Thread.sleep(1*1000);
                                    } catch (InterruptedException e) {
                                        Log.d("TAG", "sleep failure");
                                    }
                                }
                                // When the loop is finished, updates the notification
                                mBuilder.setContentText("Download completed")
                                        // Removes the progress bar
                                        .setProgress(0,0,false);
                                mNotifyManager.notify(id, mBuilder.build());
                            }
                        }
                        // Starts the thread by calling the run() method in its Runnable
                ).start();*/

                if (ActivityCompat.checkSelfPermission(DownloadImageHomework.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(),"No Permission",Toast.LENGTH_SHORT).show();
                } else {
                    DownloadImageHomework.MyAsyncTask asyncTask = new DownloadImageHomework.MyAsyncTask();
                    asyncTask.execute();
                    notificationDialog();
                }




            }
        });


        img_web_show.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));
                request.setMimeType("");
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading File...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, ""));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Downloas_File");

                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, ""));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });

/*        image_uploadwomework_3=(ImageView)findViewById(R.id.image_uploadwomework_3);
        image_uploadwomework_4=(ImageView)findViewById(R.id.image_uploadwomework_4);
        image_uploadwomework_5=(ImageView)findViewById(R.id.image_uploadwomework_5);
        image_uploadwomework_6=(ImageView)findViewById(R.id.image_uploadwomework_6);
        image_uploadwomework_7=(ImageView)findViewById(R.id.image_uploadwomework_7);
        image_uploadwomework_8=(ImageView)findViewById(R.id.image_uploadwomework_8);
        image_uploadwomework_9=(ImageView)findViewById(R.id.image_uploadwomework_9);
        image_uploadwomework_10=(ImageView)findViewById(R.id.image_uploadwomework_10);*/

        new Homework_Topic_GETAPI().execute();



       /* if(TextUtils.isEmpty(Attachment3)){
            image_uploadwomework_3.setVisibility(View.GONE);
        }else {
            image_uploadwomework_3.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(Attachment4)){
            image_uploadwomework_4.setVisibility(View.GONE);
        }else {
            image_uploadwomework_4.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(Attachment5)){
            image_uploadwomework_5.setVisibility(View.GONE);
        }else {
            image_uploadwomework_5.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(Attachment6)){
            image_uploadwomework_6.setVisibility(View.GONE);
        }else {
            image_uploadwomework_6.setVisibility(View.VISIBLE);
        }

        if(TextUtils.isEmpty(Attachment7)){
            image_uploadwomework_7.setVisibility(View.GONE);
        }else {
            image_uploadwomework_7.setVisibility(View.VISIBLE);
        }

        if(TextUtils.isEmpty(Attachment8)){
            image_uploadwomework_8.setVisibility(View.GONE);
        }else {
            image_uploadwomework_8.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(Attachment9)){
            image_uploadwomework_9.setVisibility(View.GONE);
        }else {
            image_uploadwomework_9.setVisibility(View.VISIBLE);
        }

        if(TextUtils.isEmpty(Attachment10)){
            image_uploadwomework_10.setVisibility(View.GONE);
        }else {
            image_uploadwomework_10.setVisibility(View.VISIBLE);
        }*/


        //ll_attach_1.setVisibility(View.VISIBLE);
        ll_attach_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachmentimg;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                //String[] splitsstr=Attachmentimg.split("\\.");

                if(Attachmentimg.split("\\.")[Attachmentimg.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);

                }

            }
        });

        ll_attach_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachment2;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                if(Attachment2.split("\\.")[Attachment2.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);
                }
               // img_web_show.loadUrl(Attachment);
               // img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

            }
        });
        ll_attach_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachment3;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                if(Attachment3.split("\\.")[Attachment3.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);
                }
               // img_web_show.loadUrl(Attachment);
                //img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

            }
        });
        ll_attach_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachment4;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                if(Attachment4.split("\\.")[Attachment4.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);
                }
               // img_web_show.loadUrl(Attachment);
               // img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

            }
        });
        ll_attach_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachment5;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                if(Attachment5.split("\\.")[Attachment5.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);
                }
                //img_web_show.loadUrl(Attachment);
                //img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

            }
        });
        ll_attach_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachment6;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                if(Attachment6.split("\\.")[Attachment6.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);
                }
                //img_web_show.loadUrl(Attachment);
                //img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

            }
        });
        ll_attach_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachment7;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                // img_web_show.loadUrl(Attachment);
               // img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);
                if(Attachment7.split("\\.")[Attachment7.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);
                }
            }
        });
        ll_attach_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachment8;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                //img_web_show.loadUrl(Attachment);
                //img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);
                if(Attachment8.split("\\.")[Attachment8.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);
                }
            }
        });
        ll_attach_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachment9;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                //img_web_show.loadUrl(Attachment);
                //img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);
                if(Attachment9.split("\\.")[Attachment9.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);
                }
            }
        });
        ll_attach_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachment10;
                Attachment=Attachment.replace("..","");
                file_url = Attachment;
                txt_download.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.VISIBLE);

                //img_web_show.loadUrl(Attachment);
                //img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                if(Attachment10.split("\\.")[Attachment10.split("\\.").length-1].equals("pdf")){
                    img_web_show.loadUrl("https://docs.google.com/gview?embedded=true&url="+Attachment);

                } else {
                    img_web_show.loadUrl(Attachment);

                }
            }
        });

        //String Attachment= ServerApi.MAIN_IPLINK+rowItem.getAttachment();
        // Attachment=Attachment.replace("..","");
        //holder.attechment.setText(Attachment);


      /*  image_uploadwomework_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment1;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
        if(attachment2.equals("null")){
            image_uploadwomework_2.setVisibility(View.GONE);
        }else {
            image_uploadwomework_2.setVisibility(View.VISIBLE);
        }

        if(attachment3.equals("null")){
            image_uploadwomework_3.setVisibility(View.GONE);
        }else {
            image_uploadwomework_3.setVisibility(View.VISIBLE);
        }
        if(attachment4.equals("null")){
            image_uploadwomework_4.setVisibility(View.GONE);
        }else {
            image_uploadwomework_4.setVisibility(View.VISIBLE);
        }

        if(attachment5.equals("null")){
            image_uploadwomework_5.setVisibility(View.GONE);
        }else {
            image_uploadwomework_5.setVisibility(View.VISIBLE);
        }
        if(attachment6.equals("null")){
            image_uploadwomework_6.setVisibility(View.GONE);
        }else {
            image_uploadwomework_6.setVisibility(View.VISIBLE);
        }

        if(attachment7.equals("null")){
            image_uploadwomework_7.setVisibility(View.GONE);
        }else {
            image_uploadwomework_7.setVisibility(View.VISIBLE);
        }

        if(attachment8.equals("null")){
            image_uploadwomework_8.setVisibility(View.GONE);
        }else {
            image_uploadwomework_8.setVisibility(View.VISIBLE);
        }
        if(attachment9.equals("null")){
            image_uploadwomework_9.setVisibility(View.GONE);
        }else {
            image_uploadwomework_9.setVisibility(View.VISIBLE);
        }

        if(attachment10.equals("null")){
            image_uploadwomework_10.setVisibility(View.GONE);
        }else {
            image_uploadwomework_10.setVisibility(View.VISIBLE);
        }


        image_uploadwomework_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment2;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
        image_uploadwomework_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment3;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
        image_uploadwomework_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment4;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
        image_uploadwomework_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment5;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
        image_uploadwomework_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment6;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
        image_uploadwomework_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment7;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
        image_uploadwomework_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment8;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
        image_uploadwomework_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment9;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
        image_uploadwomework_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Attachment= ServerApiadmin.MAIN_IPLINK+ attachment10;
                Attachment=Attachment.replace("..","");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(Attachment));
                startActivity(i);
            }
        });
*/



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

   /* @Override
    public void onClick(View v) {



        //canvas = new Canvas(bmp);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        matrix = new Matrix();
      //  canvas.drawBitmap(bmp, matrix, paint);

        float scale = img_web_show.getScale();
        int webViewHeight = Math.round(img_web_show.getContentHeight() * scale);
         bmp = Bitmap.createBitmap(img_web_show.getWidth(), webViewHeight, Bitmap.Config.ARGB_8888);
         canvas = new Canvas(bmp);
        img_web_show.draw(canvas);
        Log.d("Web to image:", Integer.toString(webViewHeight));


        img_web_show.setOnTouchListener(this);

       *//* if (v == txt_download) {

            if (alteredBitmap != null) {
                ContentValues contentValues = new ContentValues(3);
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "Draw On Me");

                Uri imageFileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    OutputStream imageFileOS = getContentResolver().openOutputStream(imageFileUri);
                    alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 90, imageFileOS);
                    Toast t = Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT);
                    t.show();

                } catch (Exception e) {
                    Log.v("EXCEPTION", e.getMessage());
                }
            }
        }*//*
    }*/

/*    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                img_web_show.invalidate();
                downx = upx;
                downy = upy;
                break;
            case MotionEvent.ACTION_UP:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                img_web_show.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;

    }*/



    @Override
    public void onClick(View v) {
        if (v == choosePicture) {
                   // Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
                    //android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent intent = new Intent();
            intent.setType("image/*");//
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
                    startActivityForResult(intent, 0);
        } else if (v == savePicture) {

            if (alteredBitmap != null) {
                ContentValues contentValues = new ContentValues(3);
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "Draw On Me");

                Uri imageFileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                try {

                    OutputStream imageFileOS = getContentResolver().openOutputStream(imageFileUri);
                    alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 90, imageFileOS);


                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    drawimage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    //finish();

                    Toast t = Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT);
                   // Toast t1 = Toast.makeText(this, drawimage, Toast.LENGTH_LONG);

                    t.show();
                   // t1.show();

                } catch (Exception e) {
                    Log.v("EXCEPTION", e.getMessage());
                }
            }


        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            Uri imageFileUri = intent.getData();
            try {
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = true;
                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(
                        imageFileUri), null, bmpFactoryOptions);

                bmpFactoryOptions.inJustDecodeBounds = false;
                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(
                        imageFileUri), null, bmpFactoryOptions);

                alteredBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp
                        .getHeight(), bmp.getConfig());
                canvas = new Canvas(alteredBitmap);
                paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(5);
                matrix = new Matrix();
                canvas.drawBitmap(bmp, matrix, paint);

                choosenImageView.setImageBitmap(alteredBitmap);
                choosenImageView.setOnTouchListener(this);
            } catch (Exception e) {
                Log.v("ERROR", e.toString());
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                choosenImageView.invalidate();
                downx = upx;
                downy = upy;
                break;
            case MotionEvent.ACTION_UP:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                choosenImageView.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    private boolean permissions(List<String> listPermissionsNeeded) {

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return true;
        }
        return false;
    }

    private class Homework_Topic_GETAPI extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(DownloadImageHomework.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(DownloadImageHomework.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(applicationController.getServicesapplication()+ ServerApiadmin.HOMEWORK_FILE_DOWANLOAD_Details_API,Para(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID()),"1");
            String api= applicationController.getServicesapplication();

            if(response!=null){
                // response=response.replace("\r","");
                if (response.length()>5){
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        JSONObject jsonobject = jsonArray.getJSONObject(0);
                          Attachmentimg=jsonobject.getString("Attachment");
                         Attachment2=jsonobject.getString("Attachment2");
                         Attachment3=jsonobject.getString("Attachment3");
                         Attachment4=jsonobject.getString("Attachment4");
                         Attachment5=jsonobject.getString("Attachment5");
                         Attachment6=jsonobject.getString("Attachment6");
                         Attachment7=jsonobject.getString("Attachment7");
                         Attachment8=jsonobject.getString("Attachment8");
                         Attachment9=jsonobject.getString("Attachment9");
                         Attachment10=jsonobject.getString("Attachment10");

                        status=1;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status=-2;
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

                    if(Attachmentimg.equals("null")){
                        ll_attach_1.setVisibility(View.GONE);
                    }else {
                        ll_attach_1.setVisibility(View.VISIBLE);
                    }

                    if(Attachment2.equals("null")){
                        ll_attach_2.setVisibility(View.GONE);
                    }else {
                        ll_attach_2.setVisibility(View.VISIBLE);
                    }

                    if(Attachment3.equals("null")){
                        ll_attach_3.setVisibility(View.GONE);
                    }else {
                        ll_attach_3.setVisibility(View.VISIBLE);
                    }

                    if(Attachment4.equals("null")){
                        ll_attach_4.setVisibility(View.GONE);
                    }else {
                        ll_attach_4.setVisibility(View.VISIBLE);
                    }

                    if(Attachment5.equals("null")){
                        ll_attach_5.setVisibility(View.GONE);
                    }else {
                        ll_attach_5.setVisibility(View.VISIBLE);
                    }

                    if(Attachment6.equals("null")){
                        ll_attach_6.setVisibility(View.GONE);
                    }else {
                        ll_attach_6.setVisibility(View.VISIBLE);
                    }

                    if(Attachment7.equals("null")){
                        ll_attach_7.setVisibility(View.GONE);
                    }else {
                        ll_attach_7.setVisibility(View.VISIBLE);
                    }

                    if(Attachment8.equals("null")){
                        ll_attach_8.setVisibility(View.GONE);
                    }else {
                        ll_attach_8.setVisibility(View.VISIBLE);
                    }

                    if(Attachment9.equals("null")){
                        ll_attach_9.setVisibility(View.GONE);
                    }else {
                        ll_attach_9.setVisibility(View.VISIBLE);
                    }


                    if(Attachment10.equals("null")){
                        ll_attach_10.setVisibility(View.GONE);
                    }else {
                        ll_attach_10.setVisibility(View.VISIBLE);
                    }

/*
                    image_uploadwomework_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Attachment= ServerApiadmin.MAIN_IPLINK+ Attachmentimg;
                            Attachment=Attachment.replace("..","");

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.setData(Uri.parse(Attachment));
                            startActivity(i);
                        }
                    });
*/

                    break;
                case -2:

                    Snackbar snackbar = Snackbar
                            .make(layout_download_image_homework, "File Not Found. Please Try Again", Snackbar.LENGTH_LONG)
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
                            .make(layout_download_image_homework, "File Not Found. Please Try Again ", Snackbar.LENGTH_LONG)
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
    public String Para(String SchoolCode, String BranchCode, String SessionId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("StudentCode",StudentCode);
            jsonParam.put("schoolCode", SchoolCode);
            jsonParam.put("branchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("TransId", TransId);
            jsonParam.put("fyId", applicationController.getFyID());
            jsonParam.put("Action","30");
            jsonParam1.put("obj", jsonParam);
//SectionId,ClassId,TopicName,EmployeeCode,schoolCode,SessionId,branchCode,fyId
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();

    }

    public class myWebClient extends WebViewClient {
        int color;


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            progressBar1.setVisibility(View.VISIBLE);
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

            String command = "javascript:document.body.style.background = " + color + ";";
            view.loadUrl(command);
            progressBar1.setVisibility(View.GONE);
        }
    }


    //////////////
    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(file_url);
                //create the new connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //set up some things on the connection
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(false);
                //and connect!
                urlConnection.connect();

                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/FileDownload/";

                //  final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "FileDownload";
                File newdir = new File(dir);
                newdir.mkdirs();
                Random random=new Random();
                String id = String.format("%04d", random.nextInt(10000));
                String file = dir+id+"."+file_url.split("\\.")[file_url.split("\\.").length-1];
                File newfile = new File(file);
                try {
                    newfile.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                FileOutputStream fileOutput = new FileOutputStream(file);
                //this will be used in reading the data from the internet
                int status = urlConnection.getResponseCode();
                InputStream inputStream = urlConnection.getInputStream();
                //this is the total size of the file
                int totalSize = urlConnection.getContentLength();
                int downloadedSize = 0;
                byte[] buffer = new byte[1024];
                int bufferLength = 0; //used to store a temporary size of the buffer
                //now, read through the input buffer and write the contents to the file
                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength);
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength;
                    //this is where you would do something to report the prgress, like this maybe
                    //updateProgress(downloadedSize, totalSize);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"  Downloading.....",Toast.LENGTH_SHORT).show();
        }
    }




/////////////////notification progress baar/////////

    private void notificationDialog() {
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "tutorialspoint_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
               // .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.download_button)
                .setTicker("Tutorialspoint")
                //.setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("File Download")
                .setContentText("Download in progress")
                .setContentInfo("Information");
        notificationManager.notify(1, notificationBuilder.build());
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr+=20) {
                            // Sets the progress indicator to a max value, the current completion percentage and "determinate" state
                            notificationBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            notificationManager.notify(id, notificationBuilder.build());
                            // Sleeps the thread, simulating an operation
                            try {
                                // Sleep for 1 second
                                Thread.sleep(1*500);
                            } catch (InterruptedException e) {
                                Log.d("TAG", "sleep failure");
                            }
                        }
                        // When the loop is finished, updates the notification
                        notificationBuilder.setContentText("Download completed")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        notificationManager.notify(id, notificationBuilder.build());
                    }
                }
                // Starts the thread by calling the run() method in its Runnable
        ).start();
    }
/////////////////////dailog box ///////////////////

    private void showdialogRemark() {
        final Dialog dialog = new Dialog(DownloadImageHomework.this);
        //  R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
        dialog.setContentView(R.layout.dialog_remark);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)dialog.findViewById(R.id.dialog_layout_vedio));
        dialog.setCanceledOnTouchOutside(false);

     final EditText   txt_write=(EditText)dialog.findViewById(R.id.txt_write);
     TextView   button_cancel=(TextView)dialog.findViewById(R.id.button_cancel);
     TextView   button_submitcode=(TextView)dialog.findViewById(R.id.button_submitcode);




        dialog.show();

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_submitcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remarks =   txt_write.getText().toString();
                 new RemarkAPIINSERTSUBMIT().execute();
                dialog.dismiss();
            }
        });
    }
//////////////////////////API remark insert /////////////////



    private class RemarkAPIINSERTSUBMIT extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(DownloadImageHomework.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.HOMEWORK_TOPIC_Submit_API,ParaSub(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            //Log.d("request!", response);
            String api = applicationController.getServicesapplication();
            if(response!=null){
                response=response.replace("\"", "");
                response=response.replace("\\", "");
                if(response.equals("2")) {
                    status = 1;
                }else{
                    status=4;
                }

            }else{
                status=0;
            }
            return status;
        }
        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            //  progressDialog.dismiss();
            switch (s){
                case 1:
                   // Toast.makeText(getApplicationContext(),"File Upload successful",Toast.LENGTH_LONG).show();
                    finish();

                    break;


                case 0:
                    Toast.makeText(getApplicationContext(),"Remark not submitted. Please Try Again",Toast.LENGTH_LONG).show();

                    break;

                case 4:
                    Toast.makeText(getApplicationContext(),"Remark not submitted. Please Try Again ",Toast.LENGTH_LONG).show();

                    break;
                default:
                    Toast.makeText(getApplicationContext(),"Remark not submitted. Please Try Again ",Toast.LENGTH_LONG).show();

                    break;

            }
        }
    }

    public String ParaSub(String schoolCode, String branchCode, String SessionId, String fyId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();

        //JSONObject attachmentList = new JSONObject();


        try {
            jsonParam.put("schoolCode", schoolCode);
            jsonParam.put("branchCode", branchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("fyId",fyId);
            jsonParam.put("IsSubmitted","1");
            jsonParam.put("TransId",TransId);
            jsonParam.put("VoucherNO",VoucherNo);
            jsonParam.put("TopicName",TopicName);
            jsonParam.put("EmployeeCode",applicationController.getActiveempcode());
            jsonParam.put("Remark",remarks);
            jsonParam.put("StudentCode",StudentCode);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    ///////////////////////////////////resubmit api /////


    private class RESUBMITTEDAPIINSERTSUBMIT extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(DownloadImageHomework.this);
        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(Dialy_Diary.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePostinsert(applicationController.getServicesapplication()+ServerApiadmin.HOMEWORK_TOPIC_Submit_API,ParareSub(applicationController.getschoolCode(),applicationController.getBranchcode(),applicationController.getSeesionID(),applicationController.getFyID()),"1");
            //Log.d("request!", response);
            String api = applicationController.getServicesapplication();
            if(response!=null){
                response=response.replace("\"", "");
                response=response.replace("\\", "");
                if(response.equals("2")) {
                    status = 1;
                }else{
                    status=4;
                }

            }else{
                status=0;
            }
            return status;
        }
        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            //  progressDialog.dismiss();
            switch (s){
                case 1:
                     Toast.makeText(getApplicationContext(),"Homework resubmission permision has been done ",Toast.LENGTH_LONG).show();
                     finish();

                    break;


                case 0:
                    Toast.makeText(getApplicationContext(),"Resubmission not submitted. Please Try Again",Toast.LENGTH_LONG).show();

                    break;

                case 4:
                    Toast.makeText(getApplicationContext(),"Resubmission not submitted. Please Try Again ",Toast.LENGTH_LONG).show();

                    break;
                default:
                    Toast.makeText(getApplicationContext(),"Resubmission not submitted. Please Try Again ",Toast.LENGTH_LONG).show();

                    break;

            }
        }
    }

    public String ParareSub(String schoolCode, String branchCode, String SessionId, String fyId){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();

        //JSONObject attachmentList = new JSONObject();


        try {
            jsonParam.put("schoolCode", schoolCode);
            jsonParam.put("branchCode", branchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("fyId",fyId);
            jsonParam.put("IsSubmitted","3");
            jsonParam.put("TransId",TransId);
            jsonParam.put("VoucherNO",VoucherNo);
            jsonParam.put("TopicName",TopicName);
            jsonParam.put("EmployeeCode",applicationController.getActiveempcode());
            jsonParam.put("Remark","");
            jsonParam.put("StudentCode",StudentCode);
            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
    }


    private void showDialogDrawImage() {



        final Dialog dialog = new Dialog(DownloadImageHomework.this,R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.draw_image);
        dialog.setTitle("");

        choosenImageView = (ImageView)dialog.findViewById(R.id.ChoosenImageView);
        choosePicture = (Button)dialog.findViewById(R.id.ChoosePictureButton);
        savePicture = (Button) dialog.findViewById(R.id.SavePictureButton);

        savePicture.setOnClickListener(this);
        choosePicture.setOnClickListener(this);
        choosenImageView.setOnTouchListener(this);


        // set the custom dialog components - text, image and button
        // TextView text_toplabel = (TextView) dialog.findViewById(R.id.text_toplabel);
        // TextView text_label = (TextView) dialog.findViewById(R.id.text_label);
        // TextView text_message = (TextView) dialog.findViewById(R.id.text_message);
        // text_message.setText("");
        // text_toplabel.setTypeface(typeface);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.button_submitcode);
        TextView button_cancel = (TextView) dialog.findViewById(R.id.button_cancel);

        dialogButton.setTypeface(typeface);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


            }
        });



        dialog.show();
    }


}
