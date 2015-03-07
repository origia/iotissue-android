package com.tuvistavie.rockettissue.view.activity;

import android.util.Log;

import com.google.inject.Inject;
import com.tuvistavie.rockettissue.R;
import com.tuvistavie.rockettissue.manager.ISoundDetector;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.util.Observable;
import java.util.Observer;

import roboguice.activity.RoboActivity;


@EActivity(R.layout.activity_main)
public class MainActivity extends RoboActivity implements Observer {
    private static final String TAG = "MainActivity";

    @Inject private ISoundDetector soundDetector;

    @Override
    public void update(Observable observable, Object data) {
        Log.d(TAG, "updated");
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
