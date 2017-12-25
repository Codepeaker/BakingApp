package in.codepeaker.bakingapp.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import in.codepeaker.bakingapp.IngredientsWidgetProvider;
import in.codepeaker.bakingapp.R;
import in.codepeaker.bakingapp.constant.Constant;
import in.codepeaker.bakingapp.fragments.DetailFragment;
import in.codepeaker.bakingapp.fragments.VideoFragment;
import in.codepeaker.bakingapp.model.BakingModel;
import in.codepeaker.bakingapp.service.WidgetUpdateService;
import in.codepeaker.bakingapp.utils.AppUtils;


public class DetailActivity extends AppCompatActivity implements
        DetailFragment.OnFragmentInteractionListener,
        VideoFragment.OnFragmentInteractionListener {

    ArrayList<BakingModel.StepsBean> stepsBeans;
    ArrayList<BakingModel.IngredientsBean> ingredientsBeans;
    private boolean mTwoPane = false;
    private int recipePosition;
    private String recipename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle data = getIntent().getExtras();
        if (data == null)
            return;

        if (data.get(Constant.fromWidget) != null && (boolean) data.get(Constant.fromWidget)) {
            int recipeposition = (int) data.get(Constant.fillInIntentRecipePosition);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<BakingModel>>() {
            }.getType();
            String bakingModelString = AppUtils.getStringpreferences(this,
                    Constant.bakingmodel);
            ArrayList<BakingModel> bakingModels = gson.fromJson(bakingModelString, type);
            stepsBeans = bakingModels.get(recipeposition).getSteps();

            return;
        }

        stepsBeans = data.getParcelableArrayList(Constant.stepsBean);
        ingredientsBeans = data.getParcelableArrayList(Constant.ingredients);
        recipename = data.getString(Constant.recipeName);

        if (data.getInt(Constant.recipePosition) != -1) {
            recipePosition = data.getInt(Constant.recipePosition);
        }


        if (findViewById(R.id.video_linear_layout) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                VideoFragment videoFragment = new VideoFragment();
                fragmentTransaction.add(R.id.video_container, videoFragment, "VideoFragment");
                fragmentTransaction.commit();

//                videoFragment.initAction(stepsBeans.get(0), 0, stepsBeans.size());

            }
        } else {
            mTwoPane = false;
        }

    }

    @Override
    public void onFragmentInteraction(int position) {

        if (mTwoPane) {

            VideoFragment videoFragment = (VideoFragment) getSupportFragmentManager().findFragmentByTag("VideoFragment");
            videoFragment.releasePlayer();
            videoFragment.initAction(stepsBeans.get(position), position, stepsBeans.size());


        } else {
            Intent intent = new Intent(DetailActivity.this, VideoActivity.class);
            intent.putExtra(Constant.stepsBean, stepsBeans);
            intent.putExtra(Constant.stepsPosition, position);
            startActivity(intent);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_widget:
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(
                        this, IngredientsWidgetProvider.class
                ));

                if (appWidgetIds.length == 0) {
                    Toast.makeText(this, "please make a widget first", Toast.LENGTH_SHORT).show();
                } else {

                    WidgetUpdateService.startActionIngredWidget(this,
                            ingredientsBeans, recipename);

                    AppUtils.setSharedPreferences(DetailActivity.this, Constant.recipePosition,
                            recipePosition);

                    Toast.makeText(this, "ingred added", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
