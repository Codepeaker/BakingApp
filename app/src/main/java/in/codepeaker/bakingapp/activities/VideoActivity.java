package in.codepeaker.bakingapp.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


    private static final String stepsBeanKey = "stepsBeanArrayList";
    public static int position;
    @BindView(R.id.fab_next)
    public FloatingActionButton fabNext;
    @BindView(R.id.fab_previous)
    public FloatingActionButton fabPrevious;
    ArrayList<BakingModel.StepsBean> stepsBeanArrayList;
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

        fabNext.setOnClickListener(this);
        fabPrevious.setOnClickListener(this);

        if (savedInstanceState == null) {
            initScreen();
        } else {
            stepsBeanArrayList = savedInstanceState.getParcelableArrayList(stepsBeanKey);
        }

    }

    private void initScreen() {


        fabPrevious.hide();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        VideoFragment videoFragment = new VideoFragment();

        fragmentTransaction.add(R.id.video_container, videoFragment, "VideoFragment");

        fragmentTransaction.commit();

        Bundle data = getIntent().getExtras();
        if (data == null)
            return;

        position = (int) data.get(Constant.stepsPosition);
        stepsBeanArrayList = data.getParcelableArrayList(Constant.stepsBean);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(stepsBeanKey, stepsBeanArrayList);
    }

    @Override
    public void onClick(View view) {

        VideoFragment videoFragment = (VideoFragment) getSupportFragmentManager()
                .findFragmentByTag("VideoFragment");
        videoFragment.releasePlayer();

        if (position == -1 || position > stepsBeanArrayList.size() - 1) {
            return;
        }

        switch (view.getId()) {
            case R.id.fab_next:

                videoFragment.initAction(stepsBeanArrayList.get(++position));
                setFabVisibility();
                break;

            case R.id.fab_previous:

                videoFragment.initAction(stepsBeanArrayList.get(--position));
                setFabVisibility();
                break;
        }
    }

    private void setFabVisibility() {
        if (position == 0) {
            fabPrevious.hide();
        } else if (position == stepsBeanArrayList.size() - 1) {
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
