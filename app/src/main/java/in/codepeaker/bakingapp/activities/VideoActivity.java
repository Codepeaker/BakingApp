package in.codepeaker.bakingapp.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.codepeaker.bakingapp.R;
import in.codepeaker.bakingapp.constant.Constant;
import in.codepeaker.bakingapp.fragments.VideoFragment;
import in.codepeaker.bakingapp.model.BakingModel;


public class VideoActivity extends AppCompatActivity
        implements View.OnClickListener, VideoFragment.OnFragmentInteractionListener {


    @BindView(R.id.fab_next)
    public FloatingActionButton fabNext;


    @BindView(R.id.fab_previous)
    public FloatingActionButton fabPrevious;

    int position;
    ArrayList<BakingModel.StepsBean> stepsBean;
    Toolbar toolbar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);


        if (savedInstanceState == null) {
            initScreen();
        }

    }


    private void initScreen() {

        fabNext.setOnClickListener(this);
        fabPrevious.setOnClickListener(this);
        fabPrevious.hide();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VideoFragment videoFragment = new VideoFragment();

        fragmentTransaction.add(R.id.video_container, videoFragment, "VideoFragment");

        fragmentTransaction.commit();

        Bundle data = getIntent().getExtras();
        if (data == null)
            return;

        position = (int) data.get(Constant.stepsPosition);
        stepsBean = data.getParcelableArrayList(Constant.stepsBean);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int orientation = newConfig.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else
            Log.w("tag", "other: " + orientation);

    }


    @Override
    public void onClick(View view) {

        VideoFragment videoFragment = (VideoFragment) getSupportFragmentManager()
                .findFragmentByTag("VideoFragment");
        videoFragment.releasePlayer();

        if (position == -1 || position > stepsBean.size() - 1) {
            return;
        }

        switch (view.getId()) {
            case R.id.fab_next:

                videoFragment.initAction(stepsBean.get(++position));
                setFabVisibility();
                break;

            case R.id.fab_previous:

                videoFragment.initAction(stepsBean.get(--position));
                setFabVisibility();
                break;
        }
    }

    private void setFabVisibility() {
        if (position == 0) {
            fabPrevious.hide();
        } else if (position == stepsBean.size() - 1) {
            fabNext.hide();
        } else {
            fabPrevious.show();
            fabNext.show();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
