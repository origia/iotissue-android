package com.tuvistavie.rockettissue.manager;

import java.util.Observer;

public interface ISoundDetector {
    public void addObserver(Observer observer);
    public void startDetecting();
    public void stopDetecting();

}
