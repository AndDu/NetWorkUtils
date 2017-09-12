package com.example.administrator.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.networkutil.BaseNetworkController;
import com.example.networkutil.DeckerGetServer;
import com.example.networkutil.DeckerPostServer;
import com.example.networkutil.HttpNetWorkController;
import com.example.networkutil.JsonParse;
import com.example.networkutil.NetworkClient;
import com.example.networkutil.NetworkClientCallback;
import com.example.networkutil.ResponCallback;
import com.example.networkutil.ThreadPoolController;

import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private DeckerGetServer deckerGetServer;
    private ViewGroup viewGroup;
    private LoadingController loadingController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingController = new LoadingController(this);
        loadingController.showLoadingView();
        HttpNetWorkController.doPost("http://www.i5campus.com:9084/mobile/comment/getComments.do?userId=220&pager.currentPage=1",
                null,
                new ResponCallback() {
                    @Override
                    public void onFinish(String content) {
                        loadingController.cancelLoding();
                    }


                    @Override
                    public void onFail(Exception e, String message) {
                        loadingController.cancelLoding();
                    }
                });
    }

    public void onClick(View v){
        final View view = LayoutInflater.from(this).inflate(R.layout.test, viewGroup, false);
        viewGroup.addView(view);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewGroup.removeView(view);
            }
        },5000);
    }
}
