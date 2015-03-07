package com.tuvistavie.rockettissue.manager;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.Observable;

public class SoundDetector extends Observable implements ISoundDetector {
    private static final String TAG = "SoundDetectionManager";
    protected static final int SAMPLE_RATE = 8000;
    protected static final int SAMPLE_DELAY = 50;

    protected AudioRecord audioRecord;
    protected int bufferSize;
    protected boolean detecting = false;

    public SoundDetector() {
        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
    }

    public void startDetecting() {
        detecting = true;
        Log.i(TAG, "Starting to listen for sounds");
        audioRecord.startRecording();
        while (detecting) {
            try {
                Thread.sleep(SAMPLE_DELAY);
            } catch (InterruptedException e) {
                Log.w(TAG, "could not sleep thread");
            }
            detectSound();
        }
    }

    public void stopDetecting() {
        detecting = false;
    }

    protected void detectSound() {
        double currentLevel = getAudioLevel();
        Log.i(TAG, "current level: " + currentLevel);
        if (currentLevel > 50) {
            setChanged();
            notifyObservers(currentLevel);
        }
    }

    protected double getAudioLevel() {
        short[] buffer = new short[bufferSize];
        if (audioRecord != null) {
            int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
            double sumLevel = 0;
            for (int i = 0; i < bufferReadResult; i++) {
                sumLevel += buffer[i];
            }

            if (bufferReadResult == 0) {
                return 0;
            }
            return Math.abs(sumLevel / bufferReadResult);
        }
        return 0.0;
    }
}
