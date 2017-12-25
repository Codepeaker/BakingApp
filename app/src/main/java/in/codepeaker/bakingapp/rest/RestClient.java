package in.codepeaker.bakingapp.rest;

import in.codepeaker.bakingapp.constant.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by github.com/codepeaker on 19/12/17.
 */

public class RestClient{

    private ApiService apiService;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService(){
        return this.apiService;
    }
}
