package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import androidx.core.app.ActivityOptionsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Adapters.Gallery_Grid_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Gallery_Grid_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Zoom_Image_Gallery extends AppCompatActivity {

    String TRANID;
    GridView grid_gview;
    List<Gallery_Grid_Helper> gallery_helpers;
    Gallery_Grid_Helper item;
    ApplicationControllerAdmin applicationController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom__image__gallery);
        applicationController=(ApplicationControllerAdmin)getApplication();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.relative_galleryview));
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        SpannableString str = new SpannableString("Gallery View");
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        Intent in=getIntent();
        String HEADLINE=in.getStringExtra("HEADLINE");
        String DESC=in.getStringExtra("DESC");
        TRANID=in.getStringExtra("TRANID");

        TextView tv_headline=(TextView)findViewById(R.id.text_headline);
        TextView tv_desc=(TextView)findViewById(R.id.text_album_desc);
        tv_headline.setText(HEADLINE);
        tv_desc.setText(DESC);
        grid_gview=(GridView)findViewById(R.id.grid_full_album);
        new EventGallerfull().execute();

        grid_gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView tv_grid_heading=(TextView)view.findViewById(R.id.event_title);
                TextView tv_grid_desc=(TextView)view.findViewById(R.id.event_description);
                TextView tv_grid_tran=(TextView)view.findViewById(R.id.imageurl_id);
                View round_img=(View)view.findViewById(R.id.staff_image3);
                Intent intent=new Intent(Zoom_Image_Gallery.this,SingleZoom_Activity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(Zoom_Image_Gallery.this, round_img, getString(R.string.image_trans));
                    intent.putExtra("HEADLINE",tv_grid_heading.getText().toString());
                    intent.putExtra("DESC",tv_grid_desc.getText().toString());
                    intent.putExtra("IMAGEURL",tv_grid_tran.getText().toString());
                    startActivity(intent, options.toBundle());

                }
                else {
                    intent.putExtra("HEADLINE",tv_grid_heading.getText().toString());
                    intent.putExtra("DESC",tv_grid_desc.getText().toString());
                    intent.putExtra("IMAGEURL",tv_grid_tran.getText().toString());
                    startActivity(intent);


                }

            }
        });
    }


    private class EventGallerfull extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Zoom_Image_Gallery.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Zoom_Image_Gallery.this, "", "Please Wait...", true);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int status = 0;
            JsonParser josnparser = new JsonParser(getApplicationContext());
            String response = josnparser.executePost(applicationController.getServicesapplication()+ServerApiadmin.EVENT_FULLGALLERY_API, Para( applicationController.getschoolCode(), applicationController.getBranchcode(), applicationController.getSeesionID()), "1");
            if (response != null) {
                if (response.length() > 5) {
                    try {
                        JSONArray array = new JSONArray(response);
                        gallery_helpers = new ArrayList<Gallery_Grid_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String   Title = object.getString("Headline");
                            String   Description = object.getString("Description");
                            String   PhotoName = object.getString("Column1");
                            item = new Gallery_Grid_Helper("",Title,Description,PhotoName,"");
                            gallery_helpers.add(item);
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
                    Gallery_Grid_Adapter adapter = new Gallery_Grid_Adapter(getApplicationContext(),R.layout.gallery_item_list, gallery_helpers);
                    grid_gview.setAdapter(adapter);
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
        finish();

    }
}
