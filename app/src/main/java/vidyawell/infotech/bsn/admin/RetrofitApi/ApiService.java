package vidyawell.infotech.bsn.admin.RetrofitApi;


import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vidyawell.infotech.bsn.admin.Helpers.EventHistory;

public  interface ApiService {



@Headers("ApiKey:b5b9e2c5er5e1bceb83fda6g1fq7")
    @POST("api/UploadEvent/GetEventCategoryList")
    Call<List<JsonObject>> getEventTitleList(@Body JsonObject object);


@Headers("ApiKey:b5b9e2c5er5e1bceb83fda6g1fq7")
    @POST("api/UploadEvent/UploadEventDetails")
    Call<List<JsonObject>> uploadEventDetails(@Body JsonObject object);




@Headers("ApiKey:b5b9e2c5er5e1bceb83fda6g1fq7")
    @POST("api/UploadEvent/GetUploadEventHist")
    Call<List<EventHistory>> getEventDetailsHistory(@Body JsonObject object);



}
