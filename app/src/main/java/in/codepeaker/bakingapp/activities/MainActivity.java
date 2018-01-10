package in.codepeaker.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import in.codepeaker.bakingapp.R;
import in.codepeaker.bakingapp.adapters.ReciperAdapter;
import in.codepeaker.bakingapp.constant.Constant;
import in.codepeaker.bakingapp.idilingresource.SimpleIdlingResource;
import in.codepeaker.bakingapp.model.BakingModel;
import in.codepeaker.bakingapp.rest.ApiService;
import in.codepeaker.bakingapp.rest.RestClient;
import in.codepeaker.bakingapp.utils.AppUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<BakingModel> bakingModels;
    private RecyclerView recipeRecyclerView;
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initScreen();

        getIdlingResource();

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        if (savedInstanceState == null)
            initAction();
        else {
            bakingModels = savedInstanceState.getParcelableArrayList("recipelist");

            if (bakingModels != null) setRecyclerView(bakingModels);
        }
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    private void initAction() {

        ApiService apiService = new RestClient().getApiService();

        apiService.callRecipeList().enqueue(new Callback<ArrayList<BakingModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BakingModel>> call, Response<ArrayList<BakingModel>> response) {
                if (response.code() == 200) {
                    bakingModels = response.body();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<BakingModel>>() {
                    }.getType();

                    AppUtils.setSharedPreferences(MainActivity.this, Constant.bakingmodel, gson.toJson(bakingModels, type));

                    setRecyclerView(bakingModels);

                    if (mIdlingResource != null)
                        mIdlingResource.setIdleState(true);
                } else {
                    Toast.makeText(MainActivity.this, "please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BakingModel>> call, Throwable t) {
                Log.d("asdasd", t.toString());
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipelist", bakingModels);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void initScreen() {
        recipeRecyclerView = findViewById(R.id.recipe_recyclerview);
    }

    public void setRecyclerView(ArrayList<BakingModel> bakingModels) {

        if (findViewById(R.id.landscape_main_activity) != null) {
            recipeRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,
                    2));

        } else {
            recipeRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        }


        ReciperAdapter reciperAdapter = new ReciperAdapter(
                MainActivity.this
                , bakingModels
        );

        recipeRecyclerView.setAdapter(reciperAdapter);

    }
}
