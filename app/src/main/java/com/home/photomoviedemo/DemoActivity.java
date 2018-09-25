package com.home.photomoviedemo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.home.photomoviedemo.widget.FilterItem;
import com.home.photomoviedemo.widget.FilterType;
import com.home.photomoviedemo.widget.TransferItem;
import com.hw.photomovie.PhotoMovieFactory;
import com.hw.photomovie.render.GLTextureView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangwei on 2018/9/9.
 */
public class DemoActivity extends AppCompatActivity implements IDemoView, View.OnClickListener {

    private DemoPresenter mDemoPresenter = new DemoPresenter();
    private GLTextureView mGLTextureView;
    private ImageView playImageView;
    private MediaPlayer mediaPlayer;
    private Button synthesisButton;
    private LinearLayout optionLinearLayout;
    private FrameLayout slideshowFrameLayout;
    private TransferItem transferItem;
    private FilterItem filterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        initializePreJob();
    }

    private void initializePreJob() {
        mGLTextureView = findViewById(R.id.gl_texture);
        mDemoPresenter.attachView(this);
        optionLinearLayout = findViewById(R.id.optionLinearLayout);
        slideshowFrameLayout = findViewById(R.id.slideshowFrameLayout);
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        playImageView = findViewById(R.id.playImageView);
        playImageView.setOnClickListener(this);
        synthesisButton = findViewById(R.id.synthesisButton);
        synthesisButton.setOnClickListener(this);
    }

    @Override
    public GLTextureView getGLView() {
        return mGLTextureView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDemoPresenter.detachView();
    }

    @Override
    public void setFilters(List<FilterItem> filters) {
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setTransfers(List<TransferItem> items) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDemoPresenter.onPause();
        mGLTextureView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDemoPresenter.onResume();
        mGLTextureView.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playImageView:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playImageView.setImageResource(R.drawable.icon_pause);
                } else {
                    mediaPlayer.pause();
                    playImageView.setImageResource(R.drawable.icon_play);
                }
                break;
            case R.id.synthesisButton:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                /** 加載圖片 */
                ArrayList<String> photos = new ArrayList<String>();
                photos.add("drawable://" + R.drawable.food1);
                photos.add("drawable://" + R.drawable.food2);
                photos.add("drawable://" + R.drawable.food3);
                photos.add("drawable://" + R.drawable.food4);
                photos.add("drawable://" + R.drawable.food5);
                mDemoPresenter.onPhotoPick(photos);

                /** 加載音樂 */
                Uri uri = Uri.parse("android.resource://com.home.photomoviedemo/" + R.raw.music);
                mDemoPresenter.setMusic(uri);

                /** 加載Transfer與Filter的效果, 並顯示 */
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        transferItem = new TransferItem(R.drawable.ic_movie_transfer, "Window", PhotoMovieFactory.PhotoMovieType.WINDOW);
                        mDemoPresenter.onTransferSelect(transferItem);
                        filterItem = new FilterItem(R.drawable.snow, "Snow", FilterType.SNOW);
                        mDemoPresenter.onFilterSelect(filterItem);
                        optionLinearLayout.setVisibility(View.GONE);
                        slideshowFrameLayout.setVisibility(View.VISIBLE);
                    }
                }, 200);
                break;
        }
    }
}
