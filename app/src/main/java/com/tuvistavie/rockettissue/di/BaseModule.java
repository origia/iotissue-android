package com.tuvistavie.rockettissue.di;

import com.google.inject.AbstractModule;
import com.tuvistavie.rockettissue.manager.ISoundDetector;
import com.tuvistavie.rockettissue.manager.SoundDetector;

public class BaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ISoundDetector.class).to(SoundDetector.class).asEagerSingleton();
    }
}
