package vidyawell.infotech.bsn.admin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Full_Screen_Live extends AppCompatActivity {

    WebView full_vedio;
    ApplicationControllerAdmin applicationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__screen_live);
        getSupportActionBar().hide();
        applicationController = (ApplicationControllerAdmin) getApplication();
        full_vedio=(WebView)findViewById(R.id.full_vedio);
        Intent in = getIntent();
        String URL = in.getStringExtra("URL");
        WebSettings webSettings = full_vedio.getSettings();
        webSettings.setJavaScriptEnabled(true);
        full_vedio.setWebViewClient(new MyBrowser());
        full_vedio.loadUrl(URL);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
          //  Log.d(TAG, "shouldOverrideUrlLoading: loading ");
           // myprogressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {


                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
        }
    }
}
