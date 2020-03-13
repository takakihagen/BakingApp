package com.example.mybakingapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import com.example.mybakingapp.Model.Step;
import android.example.mybakingapp.R;
import android.graphics.LinearGradient;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {
    public StepDetailFragment(){}

    private static final String STEP_OBJECT = "step_object";

    private Step mStep;

    private SimpleExoPlayer mExoPlayer;
    private int mMinScreenSize;

    @BindView(R.id.player_view)
    PlayerView mPlayerView;
    @BindView(R.id.player_view_placeholder)
    ImageView mPlayerImageView;
    @BindView(R.id.tv_step_title)
    TextView mDescriptionTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            mStep = savedInstanceState.getParcelable(STEP_OBJECT);
        }
        View view = inflater.inflate(R.layout.fragment_step_description, container, false);

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthInDP = Math.round(dm.widthPixels / dm.density);
        int heightInDP = Math.round(dm.heightPixels / dm.density);
        mMinScreenSize = widthInDP>heightInDP?heightInDP:widthInDP;

        ButterKnife.bind(this, view);
        if(mStep!=null){
            mDescriptionTextView.setText(mStep.getDescription());
            Log.d("PPPPPPPPPPPP",mStep.getDescription());
        }
        initializePlayer();

        if(mMinScreenSize<600) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (mExoPlayer != null) {
                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) mPlayerView.getLayoutParams();
                    params.height = params.MATCH_PARENT;
                    params.width = params.WRAP_CONTENT;
                    mPlayerImageView.setLayoutParams(params);
                }
            }
        }
        return view;
    }

    public void setStep(Step step){
        mStep = step;
    }

    public Step getmStep(){
        return mStep;
    }

    private void initializePlayer(){
        String videoURL = mStep.getVideoURl();
        String thumbNailURL = mStep.getThumbnailURL();
        if(getContext()!=null) {
            DataSource.Factory dataSourceFactory =
                    new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), getString(R.string.app_name)));
            MediaSource mediaSource;
            if (!videoURL.equals("")) {
                // Create a progressive media source pointing to a stream uri.
                mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(videoURL));
                mExoPlayer = new SimpleExoPlayer.Builder(getContext()).build();
                mPlayerView.setPlayer(mExoPlayer);
                // Prepare the player with the media source.
                mExoPlayer.prepare(mediaSource);
            } else if(!thumbNailURL.equals("")){
                // Create a progressive media source pointing to a stream uri.
                mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(thumbNailURL));
                mExoPlayer = new SimpleExoPlayer.Builder(getContext()).build();
                mPlayerView.setPlayer(mExoPlayer);
                // Prepare the player with the media source.
                mExoPlayer.prepare(mediaSource);
            } else {
                mPlayerView.setVisibility(View.GONE);
                //mPlayerImageView.setVisibility(View.VISIBLE);

            }

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(mMinScreenSize<600) {
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (mExoPlayer != null) {
                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) mPlayerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.WRAP_CONTENT;//width*9/16;
                    mPlayerView.setLayoutParams(params);
                }

            } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (mExoPlayer != null) {
                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) mPlayerView.getLayoutParams();
                    params.height = params.MATCH_PARENT;
                    params.width = params.WRAP_CONTENT;
                    mPlayerImageView.setLayoutParams(params);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(STEP_OBJECT, mStep);
    }
}

