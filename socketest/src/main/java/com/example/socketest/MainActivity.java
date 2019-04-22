package com.example.socketest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void send(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                TestDemo td = new TestDemo("10.34.138.12", 55555);
                try {
                    // td.sendGet(); //send HTTP GET Request

                    td.sendPost(); // send HTTP POST Request
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
