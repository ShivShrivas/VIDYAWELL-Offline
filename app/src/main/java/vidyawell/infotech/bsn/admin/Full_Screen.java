package vidyawell.infotech.bsn.admin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

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
