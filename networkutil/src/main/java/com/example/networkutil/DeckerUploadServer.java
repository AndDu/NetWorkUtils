package com.example.networkutil;

/**
 * Created by Yellow on 2017/8/16.
 */

public class DeckerUploadServer implements TaskController {

    private NetworkClient networkClient;

    public DeckerUploadServer(NetworkClient networkClient) {
        this.networkClient = networkClient;
        networkClient.setmRequestType(NetworkClient.POST);
}

    @Override
    public void cancel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                networkClient.disConnect();
            }
        }).start();
    }

    @Override
    public void excute() {
        networkClient.connect();
    }
}
