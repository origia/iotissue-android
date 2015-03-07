package com.tuvistavie.iotissue.di;

import com.google.inject.AbstractModule;
import com.tuvistavie.iotissue.manager.ISoundDetector;
import com.tuvistavie.iotissue.manager.SoundDetector;

public class BaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ISoundDetector.class).to(SoundDetector.class).asEagerSingleton();
    }
}
