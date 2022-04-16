package vidyawell.infotech.bsn.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Adapters.Bus_on_Adapter;
import vidyawell.infotech.bsn.admin.Helpers.Bus_on_Helper;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.JsonParser;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Bus_on_Route extends AppCompatActivity {

    RelativeLayout layout_bus_route;
    Bus_on_Adapter adapter;
    EditText staff_search;
    ListView bus_on_route_list;
    List<Bus_on_Helper> bus_on_helpers;
    Bus_on_Helper item;
    SharedPreferences sharedprefer;
    ApplicationControllerAdmin applicationController;
    private Animation animationDown;
    String[] vacle_number = {"UP-32 AD-2234","UP-33 AD-2234","UP-32 AD-2234","UP-34 AD-2234","UP-32 AD-2234","UP-35 AD-2234"};
    String[] track_time = {"08:00 AM","08:10 AM","08:20 AM","08:30 AM","08:40 AM","09:00 AM"};
    String[] driver_name = {"ABC","DEF","GKL","XYZ","ZXY","DCA"};
    String[] bus_stop = {"Bsn","Gomti nagar","1090","Hazratganj","Charbag","Cms"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_on__route);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT);
        fontChanger.replaceFonts((RelativeLayout) findViewById(R.id.layout_bus_route));
        applicationController = (ApplicationControllerAdmin) getApplicationContext();
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT);
        SpannableString str = new SpannableString("School Buses");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        bus_on_route_list = (ListView) findViewById(R.id.bus_on_route_list);
        layout_bus_route=(RelativeLayout)findViewById(R.id.layout_bus_route);
        staff_search=(EditText)findViewById(R.id.staff_search_v);


        new Vehicle().execute();
        staff_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(adapter != null) {
                    String text = staff_search.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

            }
        });
      /*  bus_on_helpers = new ArrayList<Bus_on_Helper>();
        for (int i = 0; i < vacle_number.length; i++) {
            Bus_on_Helper item = new Bus_on_Helper(vacle_number[i],track_time[i],driver_name[i],bus_stop[i]);
            bus_on_helpers.add(item);
        }
         adapter = new Bus_on_Adapter(this,
         R.layout.bus_onitem_route_bus, bus_on_helpers);
         bus_on_route_list.setAdapter(adapter);*/


        bus_on_route_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             TextView vichile_id =(TextView)findViewById (R.id.vichile_id);


                Intent intent =new Intent(Bus_on_Route.this,Busroute_map.class);
                intent.putExtra(vichile_id.getText().toString(),"Vich_ID");
                startActivity(intent);

            }
        });

    }


    ////////////////////////////// VehicleAPI ////////////////////////


    private class Vehicle extends AsyncTask<String, String, Integer> {
        ProgressDialog progressDialog = new ProgressDialog(Bus_on_Route.this);
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Bus_on_Route.this, "", "Please Wait...", true);
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int status=0;
            JsonParser josnparser=new JsonParser(getApplicationContext());
            String response=josnparser.executePost(ServerApiadmin.BUS_TYPE_API,Para(applicationController.getschoolCode(),applicationController.getSeesionID(),applicationController.getBranchcode(),"5"),"1");
            // String api=ServerApi.CIRCULAR_API_ONE;
            if(response!=null){
                if (response.length()>5){
                    try {
                        JSONArray array= new JSONArray(response);
                        bus_on_helpers = new ArrayList<Bus_on_Helper>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object= array.getJSONObject(i);
                            String VehicleId=object.getString("VehicleId");
                            String VehicleNo=object.getString("VehicleNo");
                            String OwnerName=object.getString("OwnerName");
                            String OwnerAddress=object.getString("OwnerAddress");
                            String VehicleType=object.getString("VehicleType");
                            item = new Bus_on_Helper(VehicleId,VehicleNo,OwnerName,OwnerAddress,VehicleType);
                            bus_on_helpers.add(item);
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
                    bus_on_route_list = (ListView) findViewById(R.id.bus_on_route_list);
                    adapter = new Bus_on_Adapter(getApplicationContext(),R.layout.circular_listitem, bus_on_helpers);
                    bus_on_route_list.setAdapter(adapter);
                    break;
                case -2:
                    Snackbar snackbar = Snackbar
                            .make(layout_bus_route, "Network Congestion! Please try Again", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                    break;

                case -1:
                    // Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_LONG).show();
                    Snackbar snackbar1 = Snackbar
                            .make(layout_bus_route, "Data Not Found", Snackbar.LENGTH_LONG)
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
    public String Para(String schoolcode, String sessionid, String branchcode, String action){
        JSONObject jsonParam1 = new JSONObject();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("SchoolCode", schoolcode);
            jsonParam.put("SessionId", sessionid);
            jsonParam.put("BranchCode", branchcode);
            jsonParam.put("Action", action);
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
