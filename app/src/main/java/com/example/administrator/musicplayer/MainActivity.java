package com.example.administrator.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.networkutil.DeckerGetServer;
import com.example.uilibrary.Divider;
import com.example.uilibrary.adapter.IAdapter;
import com.example.uilibrary.test.TestMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DeckerGetServer deckerGetServer;
    private ViewGroup viewGroup;
    private LoadingController loadingController;
    private RecyclerView recyclerView;
    private IAdapter<TestMode> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<TestMode> list=new ArrayList<>(0);
        for (int i=0;i<20;i++){
            list.add(new TestMode());
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestRecylerView(this);
        mAdapter.addAllLoad(list);
        recyclerView.addItemDecoration(new Divider(RecyclerView.LayoutManager.));
        recyclerView.setAdapter((RecyclerView.Adapter) mAdapter.getAdapter());

//        loadingController = new LoadingController(this);
//        loadingController.showLoadingView();
//        HttpNetWorkController.doPost("",
//                null,
//                new ResponCallback() {
//                    @Override
//                    public void onFinish(String content) {
////                        loadingController.cancelLoding();
//                        Log.e("",content);
//                    }
//
//
//                    @Override
//                    public void onFail(Exception e, String message) {
////                        loadingController.cancelLoding();
//                        Log.e("",e.toString());
//                    }
//                });

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
