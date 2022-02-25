package vidyawell.infotech.bsn.admin;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class SingleZoom_Activity extends AppCompatActivity {

    LinearLayout download,share,close;
    String IMAGEURL,imagerplace;
    ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    ImageView img_view;
    String HEADLINE,DESC;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_zoom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.relative_galleryviews));

        download=(LinearLayout)findViewById(R.id.download);
        share=(LinearLayout)findViewById(R.id.share);
        close=(LinearLayout)findViewById(R.id.close);

        Intent in=getIntent();
         HEADLINE=in.getStringExtra("HEADLINE");
        DESC=in.getStringExtra("DESC");
        IMAGEURL=in.getStringExtra("IMAGEURL");
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        SpannableString str = new SpannableString("Zoom View");
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);

         img_view=(ImageView)findViewById(R.id.imgview_full_album);
        TextView tv_headline=(TextView)findViewById(R.id.text_headlines);
        TextView tv_des=(TextView)findViewById(R.id.text_album_descs);
        if(DESC.equals("null")){
            tv_des.setText("");
        }else{
            tv_des.setText(DESC);
        }
        tv_headline.setText(HEADLINE);

        imagerplace= ServerApiadmin.MAIN_IPLINK+IMAGEURL;
        imagerplace=imagerplace.replace("..","");
        img_view.setImageURI(Uri.parse(imagerplace));
        Glide.get(getApplicationContext()).clearMemory();
        Glide
                .with(getApplicationContext())
                .load(imagerplace)
                .into(img_view);
        if (ActivityCompat.checkSelfPermission(SingleZoom_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SingleZoom_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        }

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(SingleZoom_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),"No Permission",Toast.LENGTH_SHORT).show();
                } else {
                    MyAsyncTask asyncTask = new MyAsyncTask();
                    asyncTask.execute();
                }


            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drawable mDrawable = img_view.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                        mBitmap, "Design", null);
                Uri uri = Uri.parse(path);
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                if(DESC.equals("null")){
                    share.putExtra(Intent.EXTRA_TEXT, "Event Image");
                    startActivity(Intent.createChooser(share, "Event Image"));
                }else{
                    share.putExtra(Intent.EXTRA_TEXT, DESC);
                    startActivity(Intent.createChooser(share, HEADLINE));
                }



            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }
        });

    }


    public boolean onTouchEvent(MotionEvent motionEvent) {

        mScaleGestureDetector.onTouchEvent(motionEvent);

        return true;

    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {

            mScaleFactor *= scaleGestureDetector.getScaleFactor();

            mScaleFactor = Math.max(0.1f,

                    Math.min(mScaleFactor, 10.0f));

            img_view.setScaleX(mScaleFactor);

            img_view.setScaleY(mScaleFactor);
            // Toast.makeText(getApplicationContext(), "you clicked image ", Toast.LENGTH_LONG).show();
            return true;
        }
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

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(imagerplace);
                //create the new connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //set up some things on the connection
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(false);
                //and connect!
                urlConnection.connect();

                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/picFolder/";
                File newdir = new File(dir);
                newdir.mkdirs();
                Random random=new Random();
                String id = String.format("%04d", random.nextInt(10000));
                String file = dir+id+".jpg";
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
            Toast.makeText(getApplicationContext(),"Image Downloaded.....",Toast.LENGTH_SHORT).show();
        }
    }
}

