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

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class RedioViewActivity extends AppCompatActivity {

  private MediaPlayer mediaPlayer;
  private CircleImageView circleImageView;
  private TextView titleText,codeText;
  private ProgressDialog progressBar;
    private AdView mAdView;

  private ImageView pauseImage,playImage;
    // String radio_url = "http://sc-bb.1.fm:8017/;?listening-from-radio-garden=1601016386228";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redio_view);

        circleImageView=findViewById(R.id.circleImageView);
        titleText=findViewById(R.id.fmTitle);
        codeText=findViewById(R.id.fmCode);
        pauseImage=findViewById(R.id.imageViewPause);
        playImage=findViewById(R.id.imageViewPlay);


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



           playImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        getWindow().setFormat(PixelFormat.TRANSLUCENT);
                        Intent intent = getIntent();
                        String radio_url = intent.getStringExtra("url");
                        Uri uri = Uri.parse(radio_url);
                        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                        mediaPlayer.start();
                    }catch (Exception e){

                    }

                    pauseImage.setVisibility(View.VISIBLE);
                    playImage.setVisibility(View.GONE);

                }
            });

            pauseImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mediaPlayer.pause();
                    pauseImage.setVisibility(View.GONE);
                    playImage.setVisibility(View.VISIBLE);
                }
            });


//
//            mediaPlayer.setAudioAttributes(
//                    new AudioAttributes.Builder()
//                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                            .setUsage(AudioAttributes.USAGE_MEDIA)
//                            .build()
//            );
//            mediaPlayer.setDataSource(radio_url);
//            mediaPlayer.prepare(); // might take long! (for buffering, etc)
//            mediaPlayer.start();

        }catch (Exception e){
            progressBar.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer=null;
        }
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        radioPlay();
    }
}