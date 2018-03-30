package com.example.networkutil;

/**
 * Created by Yellow on 2017/8/16.
 */

public class DeckerGetServer implements TaskController {


    private NetworkClient networkClient;


    public DeckerGetServer(NetworkClient networkClient) {
        this.networkClient = networkClient;
        networkClient.setmRequestType(NetworkClient.GET);
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
