package com.tuvistavie.iotissue.di;

import com.google.inject.Provider;
import com.tuvistavie.iotissue.Configuration;
import com.tuvistavie.iotissue.service.RestClient;

import retrofit.RestAdapter;

public class RestProvider implements Provider<RestClient> {
    @Override
    public RestClient get() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Configuration.ENDPOINT)
                        .build();
        return restAdapter.create(RestClient.class);
    }
}
