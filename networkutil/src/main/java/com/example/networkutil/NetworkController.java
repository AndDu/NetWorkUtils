package com.example.networkutil;

import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;

/**
 * Created by Administrator on 2017/8/10.
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

    NetworkController NULL=new NetworkController() {
        @Override
        public void setNetworkCallback(NetworkClientCallback callback) {

        }

        @Override
        public NetworkClientCallback getNetworkCallback() {
            return null;
        }

        @Override
        public void setRequestType(String type) {

        }

        @Override
        public void openConnect() throws IOException, KeyManagementException {

        }

        @Override
        public void requestServer() throws IOException {

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
    };

}
