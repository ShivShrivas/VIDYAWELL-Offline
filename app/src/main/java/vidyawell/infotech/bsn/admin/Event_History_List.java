package vidyawell.infotech.bsn.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vidyawell.infotech.bsn.admin.Adapters.EventHistoryAdapeter;
import vidyawell.infotech.bsn.admin.Helpers.EventHistory;
import vidyawell.infotech.bsn.admin.RetrofitApi.ApiService;
import vidyawell.infotech.bsn.admin.RetrofitApi.RestClient;

public class Event_History_List extends AppCompatActivity {
    ApplicationControllerAdmin applicationControllerAdmin;
    RecyclerView recyclerViewForShowingPicturesHistory;
    List<String> headerDateList=new ArrayList<>();
    EventHistoryAdapeter adapeter;
    ImageView noHostoryFoundImg;
    List<EventHistory> eventHistoryList=new ArrayList<>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    boolean status=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_history_list);
        getSupportActionBar().setTitle("Event History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        applicationControllerAdmin = (ApplicationControllerAdmin) getApplication();
        recyclerViewForShowingPicturesHistory=findViewById(R.id.recyclerViewForShowingPicturesHistory);
        noHostoryFoundImg=findViewById(R.id.noHostoryFoundImg);
        recyclerViewForShowingPicturesHistory.setLayoutManager(new LinearLayoutManager(this));

        getEventHistroty();
    }
    private JsonObject paraGetEventHistory(String s, String sch_code, String branch_code) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("Action",s);
        jsonObject.addProperty("SchoolCode",sch_code);
        jsonObject.addProperty("BranchCode",branch_code);
        jsonObject.addProperty("createdBy","1");
        return jsonObject;
    }
    private void getEventHistroty() {


        RestClient restClient=new RestClient();
        ApiService apiService=restClient.getApiService();
        Log.d("TAG", "getEventHistroty: "+paraGetEventHistory("5",applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode()));
        Call<List<EventHistory>> call=apiService.getEventDetailsHistory(paraGetEventHistory("5",applicationControllerAdmin.getschoolCode(),applicationControllerAdmin.getBranchcode()));
        call.enqueue(new Callback<List<EventHistory>>() {
            @Override
            public void onResponse(Call<List<EventHistory>> call, Response<List<EventHistory>> response) {
                Log.d("TAG", "onResponsehistory: "+response.body());
                status= eventHistoryList.addAll(response.body());
                if (response.body().size()<=0){
                        noHostoryFoundImg.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < response.body().size(); i++) {
                    headerDateList.add(response.body().get(i).getSyncDate());
                }
                List<String> newEventDatesList = removeDuplicates(headerDateList);

                adapeter=new EventHistoryAdapeter(Event_History_List.this,eventHistoryList,newEventDatesList);
                recyclerViewForShowingPicturesHistory.setAdapter(adapeter);
                Log.d("TAG", "onResponse:datalist "+headerDateList.size());

            }

            @Override
            public void onFailure(Call<List<EventHistory>> call, Throwable t) {

            }
        });


    }

    private ArrayList<String> removeDuplicates(List<String> headerDateList) {
        // Create a new ArrayList
        ArrayList<String> newList = new ArrayList<String>();

        // Traverse through the first list
        for (String element : headerDateList) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}