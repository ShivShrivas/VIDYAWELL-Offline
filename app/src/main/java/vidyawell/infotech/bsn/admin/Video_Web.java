package vidyawell.infotech.bsn.admin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Video_Web extends AppCompatActivity {
    WebView file_type_web;
    ProgressBar progressBar1;
    String url="https://www.youtube.com/upload?redirect_to_creator=true&fr=4&ar=1588074028043&nv=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__web);

        file_type_web = (WebView) findViewById(R.id.file_type_web);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

        file_type_web.setWebViewClient(new myWebClient());
        file_type_web.getSettings().setJavaScriptEnabled(true);
        //file_type_web.loadUrl("https://docs.google.com/gview?embedded=true&url="+"https://www.youtube.com/upload");
        //file_type_web.loadUrl("https://www.youtube.com/upload");

        file_type_web.loadUrl(url);
        file_type_web.getSettings().setSupportZoom(true);
        file_type_web.getSettings().setBuiltInZoomControls(true);
        file_type_web.getSettings().setDisplayZoomControls(true);
        file_type_web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        file_type_web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        file_type_web.getSettings().setAppCacheEnabled(true);
        file_type_web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        file_type_web.getSettings().setDomStorageEnabled(true);
        file_type_web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        file_type_web.getSettings().setUseWideViewPort(true);
        file_type_web.getSettings().setSavePassword(true);
        file_type_web.getSettings().setSaveFormData(true);
        file_type_web.getSettings().setEnableSmoothTransition(true);



       /* file_type_web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        file_type_web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        final Activity activity = this;
        file_type_web.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });
        file_type_web.loadUrl("https://www.youtube.com/upload?redirect_to_creator=true&fr=4&ar=1588074028043&nv=1");*/

    }






    public class myWebClient extends WebViewClient {

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
}
