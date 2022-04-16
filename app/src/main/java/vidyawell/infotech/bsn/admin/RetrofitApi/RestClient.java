package vidyawell.infotech.bsn.admin.RetrofitApi;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient
{

   public static String BASE_URL="http://uploadeventsvc.bsninfotech.org/";
    private ApiService apiService;
    Retrofit retrofit=null;
    public RestClient()
    {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService()
    {
        return apiService;
    }
}