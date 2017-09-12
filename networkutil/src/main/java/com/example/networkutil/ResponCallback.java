package com.example.networkutil;

/**
 * Created by Yellow on 2017/9/6.
 */

public abstract class ResponCallback implements NetworkClientCallback {


    @Override
    public void onDownloadProcess(int readbufferLength, int downloadFileLength, int totalFileLength) {

    }

    @Override
    public void onUploadProcess(int readbufferLength, int uploadFileLength, int totalFileLength) {

    }


}
