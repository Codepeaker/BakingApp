package in.codepeaker.bakingapp.rest;

import java.util.ArrayList;

import in.codepeaker.bakingapp.model.BakingModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by github.com/codepeaker on 18/12/17.
 */

//https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json

public interface ApiService {
    @GET("baking.json")
    Call<ArrayList<BakingModel>> callRecipeList();
}
