package com.tuvistavie.iotissue.service;

import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

public interface RestClient {
    @Multipart
    @POST("/image")
    String sendImage(@Part("image") TypedFile image);
}
