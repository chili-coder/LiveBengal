package com.sohaghlab.livebengal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioAttributes;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class RedioViewActivity extends AppCompatActivity {


  private CircleImageView circleImageView;
  private TextView titleText,codeText;
  private ProgressDialog progressBar;
  private AdView mAdView;
  private PlayerView playerView;
  private  SimpleExoPlayer simpleExoPlayer;


    // String radio_url = "http://sc-bb.1.fm:8017/;?listening-from-radio-garden=1601016386228";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redio_view);

        circleImageView=findViewById(R.id.circleImageView);
        titleText=findViewById(R.id.fmTitle);
        codeText=findViewById(R.id.fmCode);

        playerView=findViewById(R.id.audioPlay);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("লোডিং হচ্ছে...");
       // progressBar.show();
        Intent intent = getIntent();
        String radio_code = intent.getStringExtra("fmcode");
        String radio_title = intent.getStringExtra("title");
        String _img =intent.getStringExtra("image");

        titleText.setText(radio_title);
        codeText.setText(radio_code);
        Glide.with(RedioViewActivity.this).load(_img).error(R.drawable.logo).into(circleImageView);


        ///no internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.no_internet);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;

            Button retry = dialog.findViewById(R.id.retry);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RedioViewActivity.this,MainActivity.class));
                }
            });
            dialog.show();

        } else {

        } //end retry


        //banner ads
        mAdView=findViewById(R.id.adViewRadio);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewRadio);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        /////end baneer



roatedImage();

    }

    private void roatedImage() {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        circleImageView.setAnimation(rotate);

    }

    private void radioPlay() {
        try {

            Intent intent = getIntent();
            String radio_url = intent.getStringExtra("url");
            playerView.setControllerShowTimeoutMs(0);
            playerView.setCameraDistance(30);
           simpleExoPlayer =new SimpleExoPlayer.Builder(RedioViewActivity.this).build();
            playerView.setPlayer(simpleExoPlayer);
            DataSource.Factory dataSource = new DefaultDataSourceFactory(RedioViewActivity.this,
                    Util.getUserAgent(RedioViewActivity.this,"app"));
            MediaSource audioSource=new ProgressiveMediaSource.Factory(dataSource).createMediaSource(Uri.parse(radio_url));
            simpleExoPlayer.prepare(audioSource);
            simpleExoPlayer.setPlayWhenReady(true);


        }catch (Exception e){
            progressBar.dismiss();
        }
    }






    @Override
    protected void onStart() {
        super.onStart();
        radioPlay();
    }

    @Override
    public void onBackPressed() {

        this.finish();
        super.onBackPressed();
        simpleExoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.pause();
    }

}