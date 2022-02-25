package vidyawell.infotech.bsn.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Full_Screen extends AppCompatActivity {

    WebView full_vedio;
    ApplicationControllerAdmin applicationController;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__screen);
        getSupportActionBar().hide();
        applicationController = (ApplicationControllerAdmin) getApplication();
        full_vedio=(WebView)findViewById(R.id.full_vedio);
        Intent in = getIntent();
         URL = in.getStringExtra("URL");
        WebSettings webSettings = full_vedio.getSettings();
        webSettings.setJavaScriptEnabled(true);
        full_vedio.loadUrl(URL);
    }
}
