package in.codepeaker.bakingapp.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.codepeaker.bakingapp.R;
import in.codepeaker.bakingapp.activities.VideoActivity;
import in.codepeaker.bakingapp.constant.Constant;
import in.codepeaker.bakingapp.model.BakingModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView stepsTextView;
    SimpleExoPlayerView simpleExoPlayerView;

    SimpleExoPlayer exoPlayer;
    Unbinder unbinder;
    Bundle data = null;
    long playerPosition;
    String currentVideoURL;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private BandwidthMeter bandwidthMeter;
    private DataSource.Factory mediaDataSourceFactory;
    private TrackSelector trackSelector;
    private ImageView thumbnailImageView;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        ButterKnife.bind(this, view);
        initScreen(view);


        if (getActivity() != null) {
            data = getActivity().getIntent().getExtras();
        }

        if (data == null)
            return null;

        List<BakingModel.StepsBean> stepsBeans = data.getParcelableArrayList(Constant.stepsBean);
        int position;

        if (data.get(Constant.stepsPosition) == null) { //two pane layout
            position = 0;
        } else {
            playerPosition = data.getLong("exoplayerPosition");
            position = (int) data.get(Constant.stepsPosition);

            if (((VideoActivity) getActivity()) != null) {
                if (position == 0) {
                    ((VideoActivity) getActivity()).fabPrevious.hide();
                    ((VideoActivity) getActivity()).fabNext.show();

                } else if (position == stepsBeans.size() - 1) {
                    ((VideoActivity) getActivity()).fabNext.hide();
                    ((VideoActivity) getActivity()).fabPrevious.show();

                } else {
                    ((VideoActivity) getActivity()).fabPrevious.show();
                    ((VideoActivity) getActivity()).fabNext.show();
                }
            }

        }

        initAction(stepsBeans.get(position));

        return view;
    }

    public void initAction(BakingModel.StepsBean stepsBean) {


        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getContext(), "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);

        simpleExoPlayerView.requestFocus();


        if (stepsBean == null) {
            return;
        }


        if (stepsBean.getVideoURL().isEmpty()) {
            simpleExoPlayerView.setVisibility(View.GONE);

        } else {
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources()
                    , R.drawable.ic_baking));
        }

        if (stepsBean.getThumbnailURL().isEmpty()) {
            thumbnailImageView.setVisibility(View.GONE);
        } else {
            thumbnailImageView.setVisibility(View.VISIBLE);
            Picasso picasso = new Picasso.Builder(getActivity())
                    .listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            Log.d("asd", "onImageLoadFailed: " + exception);
                        }
                    }).build();
            picasso.
                    load(stepsBean.getThumbnailURL()).
                    placeholder(R.drawable.ic_baking).
                    into(thumbnailImageView);
        }


        stepsTextView.setText(stepsBean.getDescription());

        currentVideoURL = stepsBean.getVideoURL();
        initializePlayer(Uri.parse(stepsBean.getVideoURL()));
    }

    private void initScreen(View view) {

        stepsTextView = view.findViewById(R.id.steps_text_id);
        simpleExoPlayerView = view.findViewById(R.id.player_view);
        thumbnailImageView = view.findViewById(R.id.thumbnail_image);

    }

    public void initializePlayer(Uri uri) {
        if (exoPlayer == null) {
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);

            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(exoPlayer);

            exoPlayer.setPlayWhenReady(true);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


            MediaSource mediaSource = new ExtractorMediaSource(uri, mediaDataSourceFactory
                    , extractorsFactory, null, null);

            if (playerPosition != 0) {
                exoPlayer.seekTo(playerPosition);
            }

            exoPlayer.prepare(mediaSource);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(Uri.parse(currentVideoURL));
    }

    @Override
    public void onPause() {
        super.onPause();
        playerPosition = exoPlayer.getCurrentPosition();
        releasePlayer();
    }

    public void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
