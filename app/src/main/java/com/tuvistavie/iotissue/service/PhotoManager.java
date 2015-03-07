package com.tuvistavie.iotissue.service;

import android.util.Log;

import com.theta360.lib.PtpipInitiator;
import com.theta360.lib.ThetaException;
import com.theta360.lib.ptpip.eventlistener.PtpipEventListener;
import com.tuvistavie.iotissue.callback.PictureTaken;

import java.io.IOException;

public class PhotoManager implements IPhotoManager {
    private static final String TAG = "PhotoManager";

    private PtpipInitiator camera;

    @Override
    public void initializeCamera() throws ThetaException, IOException {
        camera = new PtpipInitiator("192.168.1.1");
    }

    @Override
    public void takePicture(final PictureTaken pictureTaken) throws ThetaException {
        if (camera == null) {
            throw new ThetaException("camera not ready");
        }
        camera.initiateCapture(new PtpipEventListener() {
            @Override
            public void onObjectAdded(int objectHandle) {
                try {
                    pictureTaken.onPicture(camera.getObject(objectHandle));
                } catch (ThetaException e) {
                    Log.e(TAG, "could not get photo");
                }
            }
        });
    }
}
