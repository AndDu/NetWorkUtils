package com.example.networkutil;

import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;

/**
 * Created by Yellow on 2017/8/10.
 */

public interface NetworkController {


    String GET = "GET";
    String POST = "POST";

    String TYPE_HTTP = "http:";
    String TYPE_HTTPS = "https:";

    int CONNECT_TIME = 5 * 1000;
    int READ_TIME = 5 * 1000;


     void setNetworkCallback(NetworkClientCallback callback);

     NetworkClientCallback getNetworkCallback();

     void setRequestType(String type);

     void openConnect() throws IOException, KeyManagementException;

     void requestServer() throws IOException;

     void uploadPostContent(OutputStream outputStream) throws IOException;

     boolean isConnectEffectived() throws IOException;

     void processingDataFromServer() throws IOException;

     void disconnect();

     void close();

     String getData();

}
