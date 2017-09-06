package com.example.administrator.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.networkutil.BaseNetworkController;
import com.example.networkutil.DeckerGetServer;
import com.example.networkutil.DeckerPostServer;
import com.example.networkutil.HttpNetWorkController;
import com.example.networkutil.NetworkClient;
import com.example.networkutil.NetworkClientCallback;
import com.example.networkutil.ThreadPoolController;

import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private DeckerGetServer deckerGetServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpNetWorkController.doPost("http://www.i5campus.com:9084/mobile/comment/getComments.do?userId=220&pager.currentPage=1",
                null,
                new NetworkClientCallback() {
                    @Override
                    public void onDownloadProcess(int readbufferLength, int downloadFileLength, int totalFileLength) {

                    }

                    @Override
                    public void onUploadProcess(int readbufferLength, int uploadFileLength, int totalFileLength) {

                    }

                    @Override
                    public void onFinish(String content) {
                        Log.e("", content);
                    }

                    @Override
                    public void onFail(Exception e, String message) {
                        Log.e("", message);
                    }
                });
    }
}
