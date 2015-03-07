package com.tuvistavie.iotissue.service;

import com.tuvistavie.iotissue.callback.SoundDetected;

public interface ISoundDetector {
    public void startDetecting(SoundDetected soundDetected);
    public void stopDetecting();

}
