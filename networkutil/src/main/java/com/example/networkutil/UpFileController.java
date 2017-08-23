package com.example.networkutil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;

/**
 * Created by Administrator on 2017/8/16.
 */

public class UpFileController implements NetworkController {


    private NetworkClientCallback mNetworkClientCallback;
    private String serviceSubmitUrl;  //N
    private HttpURLConnection urlConnection;
    private static final int UPFILE_CONNECT_TIME=40*1000;
    private static final int UPFILE_READ_TIME=40*1000;


    @Override
    public void setNetworkCallback(NetworkClientCallback callback) {
        this.mNetworkClientCallback=callback;
    }

    @Override
    public NetworkClientCallback getNetworkCallback() {
        return mNetworkClientCallback;
    }

    @Override
    public void setRequestType(String type) {

    }

    @Override
    public void openConnect() throws IOException, KeyManagementException {
        URL url = new URL(serviceSubmitUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setChunkedStreamingMode(512);
        urlConnection.setConnectTimeout(UPFILE_CONNECT_TIME);
        urlConnection.setReadTimeout(UPFILE_READ_TIME);
    }

    @Override
    public void requestServer() throws IOException {
        urlConnection.setRequestMethod(POST);
//        urlConnection.setRequestProperty("Charset","");
    }

    @Override
    public void uploadPostContent(OutputStream outputStream) throws IOException {

    }

    @Override
    public boolean isConnectEffectived() throws IOException {
        return false;
    }

    @Override
    public void processingDataFromServer() throws IOException {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void close() {

    }

    @Override
    public String getData() {
        return null;
    }
}
