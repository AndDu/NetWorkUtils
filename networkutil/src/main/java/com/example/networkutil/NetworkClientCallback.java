package com.example.networkutil;

public interface NetworkClientCallback {
    public void onDownloadProcess(int readbufferLength, int downloadFileLength, int totalFileLength);

    public void onUploadProcess(int readbufferLength, int uploadFileLength, int totalFileLength);

    public void onFinish(String content);

    public void onFail(Exception e, String message);

    public static NetworkClientCallback NULL = new NetworkClientCallback() {
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