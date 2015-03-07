package com.tuvistavie.iotissue.view.activity;

import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.google.inject.Inject;
import com.theta360.lib.PtpipInitiator;
import com.theta360.lib.ThetaException;
import com.tuvistavie.iotissue.R;
import com.tuvistavie.iotissue.callback.PictureTaken;
import com.tuvistavie.iotissue.callback.SoundDetected;
import com.tuvistavie.iotissue.service.IPhotoManager;
import com.tuvistavie.iotissue.service.ISoundDetector;
import com.tuvistavie.iotissue.service.RestClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import retrofit.RetrofitError;
import retrofit.mime.TypedFile;
import roboguice.activity.RoboActivity;


@EActivity(R.layout.activity_main)
public class MainActivity extends RoboActivity {
    private static final String TAG = "MainActivity";

    private boolean takingPicture = false;

    @Inject ISoundDetector soundDetector;
    @Inject IPhotoManager photoManager;
    @Inject WifiManager wifiManager;

    @Inject RestClient restClient;

    @AfterViews
    public void setup() {
        listenSound();
        initializePhotoManager();
    }

    @Background
    public void initializePhotoManager() {
        tryInitializePhotoManager();
    }

    private void handlePhotoManagerError() {
        showToast("could not connect to camera");
    }

    @UiThread
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Background
    public void listenSound() {
        soundDetector.startDetecting(new SoundDetected() {
            @Override
            public void onDetected(double volume) {
                Log.i(TAG, "sound detected, taking picture");
                takePicture();
            }
        });
    }

    @Background
    public void takePicture() {
        if (takingPicture) {
            return;
        }
        takingPicture = true;
        PictureTaken pictureTaken = new PictureTaken() {
            @Override
            public void onPicture(byte[] photo) {
                sendPhoto(photo);
                takingPicture = false;
            }
        };
        try {
            photoManager.takePicture(pictureTaken);
        } catch (ThetaException e) {
            showToast("failed to take picture");
            tryInitializePhotoManager();
            takingPicture = false;
        }
    }

    @Background
    public void sendPhoto(byte[] photo) {
        Log.d(TAG, "Photo length: " + photo.length);
        try {
            PtpipInitiator.close();
            wifiManager.setWifiEnabled(false);
            File file = File.createTempFile("image", "jpg", getCacheDir());
            FileUtils.writeByteArrayToFile(file, photo);
            restClient.sendImage(new TypedFile("image/jpeg", file));
        } catch (RetrofitError e) {
            showToast("Could not send image");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            showToast("Could not create file");
        } catch (ThetaException e) {
            showToast("Could not send file");
        } finally {
            wifiManager.setWifiEnabled(true);
            tryInitializePhotoManager();
        }
    }

    public void tryInitializePhotoManager() {
        try {
            Log.i(TAG, "Initializing camera");
            photoManager.initializeCamera();
            Log.i(TAG, "Connected to camera");
        } catch (ThetaException e) {
            handlePhotoManagerError();
        } catch (IOException e) {
            handlePhotoManagerError();
        }
    }
}
