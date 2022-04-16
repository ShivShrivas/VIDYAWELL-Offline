package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Gallery_Grid_Vedio_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Gallery_Grid_Vedio_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Vedios_gallery_Activity extends AppCompatActivity {


    WebView staff_image2;
    TextView text_headline_video,text_album_desc_video;
    VideoView event_vedio;
    String TRANID;
    ListView list_full_vedio;
    List<Gallery_Grid_Vedio_Helper> gallery_grid_vedio_helpers;
    Gallery_Grid_Vedio_Helper item;
    ApplicationControllerAdmin applicationController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio__gallery);
        applicationController = (ApplicationControllerAdmin) getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.relative_gallery));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        SpannableString str = new SpannableString("Video Gallery");
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        Intent in = getIntent();
        String HEADLINE = in.getStringExtra("HEADLINE");
        String DESC = in.getStringExtra("DESC");
        TRANID = in.getStringExtra("TRANID");

        list_full_vedio = (ListView) findViewById(R.id.list_full_vedio);

        View headerView = getLayoutInflater().inflate(R.layout.vedio_header, null);
        text_headline_video=(TextView)headerView.findViewById(R.id.text_headline_video);
        text_album_desc_video=(TextView)headerView.findViewById(R.id.text_album_desc_video);
        list_full_vedio.addHeaderView(headerView);
        text_headline_video.setText(HEADLINE);
        text_album_desc_video.setText(DESC);
        new EventGallerfullVedio().execute();
    }

    private class EventGallerfullVedio extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Vedios_gallery_Activity.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Vedios_gallery_Activity.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status = 0;
            JsonParser josnparser = new JsonParser(getApplicationContext());
            String response = josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.EVENT_FULLGALLERY_VEDIO_API, Para( applicationController.getschoolCode(), applicationController.getBranchcode(), applicationController.getSeesionID()), "1");
            if (response != null) {
                if (response.length() > 5) {
                    try {
                        JSONArray array = new JSONArray(response);
                        gallery_grid_vedio_helpers = new ArrayList<Gallery_Grid_Vedio_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String   Title = object.getString("Title");
                            String   VedioAlbum = object.getString("VedioAlbum");
                            String   VedioUrl = object.getString("VedioUrl");
                            item = new Gallery_Grid_Vedio_Helper(Title,VedioAlbum,VedioUrl,"");
                            gallery_grid_vedio_helpers.add(item);
                            status = 1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status = -1;
                    }
                } else {
                    status = -2;
                }
            } else {
                status = -1;
            }
            return status;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            switch (s) {
                case 1:
                    Gallery_Grid_Vedio_Adapter adapter = new Gallery_Grid_Vedio_Adapter(getApplicationContext(),R.layout.gallery_item_full_vedios, gallery_grid_vedio_helpers);
                    list_full_vedio.setAdapter(adapter);
                    break;
                case -2:
                    // Toast.makeText(getApplicationContext(),"Subject not found",Toast.LENGTH_LONG).show();
                    break;
                case -1:
                    // Toast.makeText(getApplicationContext(),"Network Congestion! Please try Again",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public String Para(String SchoolCode, String BranchCode,  String SessionId) {
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", SchoolCode);
            jsonParam.put("BranchCode", BranchCode);
            jsonParam.put("SessionId", SessionId);
            jsonParam.put("TransId", TRANID);

            jsonParam1.put("obj", jsonParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParam1.toString();
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
