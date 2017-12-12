package com.example.networkutil.ex;

/**
 * Created by Yellow on 2017/9/13.
 */

public class SeverExcetion extends Exception {


    public static final String SERVER_ERROR="server connect fail";

    public SeverExcetion(String message) {
        super(message);
    }
}
