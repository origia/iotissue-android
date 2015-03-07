package com.tuvistavie.iotissue.view.activity;

import android.net.ConnectivityManager;
import android.util.Log;

import com.google.inject.Inject;
import com.tuvistavie.iotissue.R;
import com.tuvistavie.iotissue.manager.ISoundDetector;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;

import java.util.Observable;
import java.util.Observer;

import roboguice.activity.RoboActivity;


@EActivity(R.layout.activity_main)
public class MainActivity extends RoboActivity implements Observer {
    private static final String TAG = "MainActivity";

    @Inject ISoundDetector soundDetector;

    @SystemService ConnectivityManager connectivityManager;

    @Override
    public void update(Observable observable, Object data) {
        Log.d(TAG, "sound detected");
    }

    @AfterViews
    public void setupConnectivity() {
        connectivityManager.setNetworkPreference(ConnectivityManager.TYPE_MOBILE);
    }

    @AfterViews
    public void startListening() {
        soundDetector.addObserver(this);
        listenSound();
    }

    @Background
    public void listenSound() {
        soundDetector.startDetecting();
    }
}
