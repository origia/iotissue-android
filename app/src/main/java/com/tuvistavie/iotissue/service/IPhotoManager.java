package com.tuvistavie.iotissue.service;

import com.theta360.lib.ThetaException;
import com.tuvistavie.iotissue.callback.PictureTaken;

import java.io.IOException;

public interface IPhotoManager {
    public void initializeCamera() throws ThetaException, IOException;
    public void takePicture(PictureTaken pictureTaken) throws ThetaException;
}
