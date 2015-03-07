package com.tuvistavie.iotissue.di;

import com.google.inject.AbstractModule;
import com.tuvistavie.iotissue.service.IPhotoManager;
import com.tuvistavie.iotissue.service.ISoundDetector;
import com.tuvistavie.iotissue.service.PhotoManager;
import com.tuvistavie.iotissue.service.RestClient;
import com.tuvistavie.iotissue.service.SoundDetector;

public class BaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ISoundDetector.class).to(SoundDetector.class).asEagerSingleton();
        bind(IPhotoManager.class).to(PhotoManager.class).asEagerSingleton();
        bind(RestClient.class).toProvider(RestProvider.class).asEagerSingleton();
    }
}
