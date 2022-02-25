package vidyawell.infotech.bsn.admin;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashTwo_Activity extends AppCompatActivity {

    ApplicationControllerAdmin applicationController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_two);
        applicationController=(ApplicationControllerAdmin)getApplication();
        final View img_logo=findViewById(R.id.image_app_logo);

        Intent intent = getIntent();
        String notify= intent.getStringExtra("My_notification");
        if(notify.equals("1")){
            applicationController.setNotify("1");
        }else{
            applicationController.setNotify("0");
        }




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashTwo_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();
              /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(SplashTwo_Activity.this, img_logo, getString(R.string.activity_image_trans));
                    startActivity(intent, options.toBundle());
                    finish();
                }
                else {
                    startActivity(intent);
                    finish();
                }*/

                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        }, 2000);

    }
}
