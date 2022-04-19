package vidyawell.infotech.bsn.admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vidyawell.infotech.bsn.admin.Adapters.ImageAdapter;
import vidyawell.infotech.bsn.admin.DAO.UserDao;
import vidyawell.infotech.bsn.admin.Database.RoomDatabaseClass;
import vidyawell.infotech.bsn.admin.RetrofitApi.ApiService;
import vidyawell.infotech.bsn.admin.RetrofitApi.RestClient;

public class Submit_Event_Details extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_images_upload_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
        if (arrayList.size()>=1){
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)){
            getLocations();
        }else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Device Location is turn off ");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }

    private void getLocations() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null){
                        longitude= String.valueOf(location.getLongitude());
                        latitude= String.valueOf(location.getLatitude());
                    }
                }
            });
        }else {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},10);
    }





    @Override
    protected void onStop() {
        super.onStop();
        adapter.notifyDataSetChanged();
        if (arrayList.size()>=1){
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.event_history){
           startActivity(new Intent(Submit_Event_Details.this,Event_History_List.class));
        }

        return super.onOptionsItemSelected(item);


    }
    String longitude,latitude;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    LinearLayout layout;
    RecyclerView recyclerView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;

    ApplicationControllerAdmin applicationControllerAdmin;
    private String mCurrentPhotoPath;
    private ImageView imageView;
    ImageAdapter adapter;
    EditText evenetDesc;
    String eventname;
    RoomDatabaseClass db;
    public static final int RequestPermissionCode = 1;
    public ArrayList<Bitmap> arrayList=new ArrayList<>();
    ArrayList<String> arrayListBitmapAsString=new ArrayList<>();
    UserDao userDao;
    Spinner spinner;
    Button submitEventPicturesBtn;
    RestClient restClient=new RestClient();
    ApiService apiService=restClient.getApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_event_details);
        applicationControllerAdmin=(ApplicationControllerAdmin)getApplication();
        getSupportActionBar().setTitle("Submit Event Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ProgressDialog progress = new ProgressDialog(Submit_Event_Details.this);
        progress.setMessage("Please Wait ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getEventList();

        layout=findViewById(R.id.linearLayoutCamera);
        recyclerView=findViewById(R.id.recyclerViewForShowingPictures);
        imageView=findViewById(R.id.imageView);
        evenetDesc=findViewById(R.id.evenetDesc);
        spinner=findViewById(R.id.spinner_event);
        submitEventPicturesBtn=findViewById(R.id.submitEventPicturesBtn);

        submitEventPicturesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress.show();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyddMMHHmmss", Locale.getDefault());
                String id = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.getDefault());
                String currentDateTime = sdf2.format(new Date());
                if (id!=null && eventname!=null){
                    if (evenetDesc.getText().toString().trim()!=null && !evenetDesc.getText().toString().trim().isEmpty()){
                        if (arrayList.size()>0){
                            if (evenetDesc.length() > 0 && arrayList.size() > 0) {


                                RestClient restClient = new RestClient();
                                ApiService apiService = restClient.getApiService();
                                Log.e("TAG", "onClick: " + paraUploadEventdata("1", applicationControllerAdmin.getProductTypeId(), "UE" + id, eventname, evenetDesc.getText().toString().trim(), currentDateTime, "eventPhoto", latitude, longitude, applicationControllerAdmin.getschoolCode(), applicationControllerAdmin.getBranchcode(), applicationControllerAdmin.getFyID(), applicationControllerAdmin.getSeesionID(), "createdOn", "createdby",applicationControllerAdmin.getActiveempcode(), "updatedOn", "updatedby", "isActive", "isDeleted", "isDeleted", arrayList));
                                Call<List<JsonObject>> call = apiService.uploadEventDetails( paraUploadEventdata("1", applicationControllerAdmin.getProductTypeId(), "UE" + id, eventname, evenetDesc.getText().toString().trim(), currentDateTime, "eventPhoto", latitude, longitude, applicationControllerAdmin.getschoolCode(), applicationControllerAdmin.getBranchcode(),  applicationControllerAdmin.getFyID(), applicationControllerAdmin.getSeesionID(), "createdOn", "createdby", applicationControllerAdmin.getActiveempcode(),"UserCode","updatedOn", "updatedby", "isActive", "isDeleted", arrayList));
                                call.enqueue(new Callback<List<JsonObject>>() {
                                    @Override
                                    public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                                        Log.d("TAG", "onResponse:upload images    " + response.body());
                                        Toast.makeText(Submit_Event_Details.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                                        progress.dismiss();

                                        clearAll();
                                    }


                                    @Override
                                    public void onFailure(Call<List<JsonObject>> call, Throwable t) {
                                        Log.d("TAG", "onFailure: " + t.getMessage());
                                        progress.dismiss();

                                    }
                                });
                            }else {
                                Toast.makeText(Submit_Event_Details.this, "Please fill all details properly", Toast.LENGTH_LONG).show();
                                progress.dismiss();

                            }



                        }else {
                            Toast.makeText(Submit_Event_Details.this, "Please Capture Atleast One Image", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    }else {
                        Toast.makeText(Submit_Event_Details.this, "Please Enter Some details About Event", Toast.LENGTH_SHORT).show();
                        progress.dismiss();

                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Please Choose an Event...", Toast.LENGTH_SHORT).show();
                    progress.dismiss();

                }

            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventname = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(Submit_Event_Details.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 7);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                               if (permissionDeniedResponse.isPermanentlyDenied()){
                                   Toast.makeText(Submit_Event_Details.this, "Please Give Permission from app settings", Toast.LENGTH_SHORT).show();
                               }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        })
                        .check();


            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter= new ImageAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private JsonObject paraGetEventTitile(String s, String sch_code, String branch_code) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("Action",s);
        jsonObject.addProperty("SchoolCode",sch_code);
        jsonObject.addProperty("BranchCode",branch_code);

        return jsonObject;
    }

    private void getEventList() {


            Call<List<JsonObject>> listCall=apiService.getEventTitleList(paraGetEventTitile("2",applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode()));
            listCall.enqueue(new Callback<List<JsonObject>>() {
                @Override
                public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                    ArrayList<String> arrayListSpinner = new ArrayList<>();
                    Log.d("TAG", "onResponse:event list "+response.body());
                    for (int i=0;i<response.body().size();i++){
                        arrayListSpinner.add(response.body().get(i).get("CategoryName").getAsString());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_text_spiner, arrayListSpinner);
                    arrayAdapter.setDropDownViewResource(R.layout.custom_text_spiner);
                    spinner.setAdapter(arrayAdapter);
                }

                @Override
                public void onFailure(Call<List<JsonObject>> call, Throwable t) {

                }
            });
        }



    private void clearAll() {
        arrayList.clear();
        adapter.notifyDataSetChanged();
        evenetDesc.setText("");

    }

    private JsonObject paraUploadEventdata(String s, String s1, String eventId, String eventname, String evenetDesc, String currentDateTime, String eventPhoto, String lat, String longt, String sch_code, String branch_code, String fyId, String sessionId, String createdOn, String createdby,String userCode, String updatedOn, String updatedby, String isActive, String isDeleted, String deleted, ArrayList<Bitmap> arrayListImages) {
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("Action", s);
            jsonObject.addProperty("ProductId", s1);
            jsonObject.addProperty("eventId", eventId);
            jsonObject.addProperty("eventName", eventname);
            jsonObject.addProperty("eventDesc", evenetDesc);
            jsonObject.addProperty("eventDateTime", currentDateTime);
            jsonObject.addProperty("eventPhoto", eventPhoto);
            jsonObject.addProperty("lat", lat);
            jsonObject.addProperty("long",longt);
            jsonObject.addProperty("schoolCode", sch_code);
            jsonObject.addProperty("branchCode", branch_code);
            jsonObject.addProperty("fyId", fyId);
            jsonObject.addProperty("sessionId", sessionId);
            jsonObject.addProperty("createdOn", "1");
            jsonObject.addProperty("createdBy", "1");
            jsonObject.addProperty("UserCode", userCode);
            jsonObject.addProperty("updatedOn", "1");
            jsonObject.addProperty("updatedBy", "1");
            jsonObject.addProperty("isActive", "1");
            jsonObject.addProperty("isDeleted", "1");
            JsonArray jsonArray = new JsonArray();
            for (int i = 0; i < arrayListImages.size(); i++) {
                jsonArray.add(paraGetImageBase64(eventId, arrayListImages.get(i), i));

            }
            jsonObject.add("eventPhotos", (JsonElement) jsonArray);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private JsonObject paraGetImageBase64(String eventId, Bitmap bitmap, int i) {
        JsonObject jsonObject = new JsonObject();

        try {
            jsonObject.addProperty("id", String.valueOf(i + 1));
            jsonObject.addProperty("photos", BitMapToString(getResizedBitmap(bitmap, 300)));
//            Log.d("TAG", "paraGetImageBase64: "+BitMapToString(bitmap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            arrayList.add(bitmap);
        }
    }


    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] result) {
        super.onRequestPermissionsResult(requestCode, permissions, result);
        switch (requestCode) {
            case RequestPermissionCode:
                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Submit_Event_Details.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Submit_Event_Details.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;

            case 10:
                if (requestCode == 10) {
                    if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                        getLocations();
                    } else {
                        Toast.makeText(this, "Please enable location from app permissions setting!!", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
    }
