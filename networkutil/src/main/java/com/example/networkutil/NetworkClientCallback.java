package com.example.networkutil;

public interface NetworkClientCallback {

     void onDownloadProcess(int readbufferLength, int downloadFileLength, int totalFileLength);

     void onUploadProcess(int readbufferLength, int uploadFileLength, int totalFileLength);

     void onFinish(String content);

     void onFail(Exception e, String message);



    NetworkClientCallback NULL = new NetworkClientCallback() {
        @Override
        public void onDownloadProcess(int readbufferLength,
                                      int downloadFileLength, int totalFileLength) {
        }

        @Override
        public void onUploadProcess(int readbufferLength,
                                    int uploadFileLength, int totalFileLength) {
        }

        @Override
        public void onFinish(String content) {
        }

        @Override
        public void onFail(Exception e, String message) {
        }


    };
}