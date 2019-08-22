package com.mohamedfoad.rewardvideostartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    StartAppSDK.init(this, "ApplicationID", true);

  }

  public void btnShowRewardedClick(View view) {
    final StartAppAd rewardedVideo = new StartAppAd(this);

    /*
     * This is very important: set the video listener to be triggered after video
     * has finished playing completely
     */
    rewardedVideo.setVideoListener(new VideoListener() {

      @Override
      public void onVideoCompleted() {
        Toast.makeText(MainActivity.this, "Rewarded video has completed - grant the user his reward", Toast.LENGTH_LONG).show();
        Log.e("startApp", "video completed " );
      }
    });

    /*
     * Load rewarded by specifying AdMode.REWARDED
     * We are using AdEventListener to trigger ad show
     */
    rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
      @Override
      public void onReceiveAd(Ad arg0) {
        Log.e("startApp", "video loaded " );
        rewardedVideo.showAd();
      }

      @Override
      public void onFailedToReceiveAd(Ad arg0) {
        /*
         * Failed to load rewarded video:
         * 1. Check that FullScreenActivity is declared in AndroidManifest.xml:
         * See https://github.com/StartApp-SDK/Documentation/wiki/Android-InApp-Documentation#activities
         * 2. Is android API level above 16?
         */
        Log.e("MainActivity", "Failed to load rewarded video with reason: " + arg0.getErrorMessage());
      }
    });
  }
}
