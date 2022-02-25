package vidyawell.infotech.bsn.admin;



import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import vidyawell.infotech.bsn.admin.POJO.Example;


public interface RetrofitMaps {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    @GET("api/directions/json?key=AIzaSyDOfnO76U3ytAH2gxoFGN6mJVh341wG8GQ")
    Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);

}
