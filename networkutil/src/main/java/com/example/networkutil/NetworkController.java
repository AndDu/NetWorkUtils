package com.example.networkutil;

import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;

/**
 * Created by Administrator on 2017/8/10.
 */

public interface NetworkController {


    String GET = "get";
    String POST = "post";

    String TYPE_HTTP = "http:";
    String TYPE_HTTPS = "https:";

    int CONNECT_TIME = 5 * 1000;
    int READ_TIME = 5 * 1000;


    public void setNetworkCallback(NetworkClientCallback callback);

    public NetworkClientCallback getNetworkCallback();

    public void setRequestType(String type);

    public void openConnect() throws IOException, KeyManagementException;

    public void requestServer() throws IOException;

    public void uploadPostContent(OutputStream outputStream) throws IOException;

    public boolean isConnectEffectived() throws IOException;

    public void processingDataFromServer() throws IOException;

    public void disconnect();

    public void close();

    public String getData();

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
