package com.example.networkutil;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/16.
 * Default request type is POST
 */

public class UpFileController implements NetworkController {


    private boolean isConnectEffectived=true;
    private NetworkClientCallback mNetworkClientCallback;
    private String serviceSubmitUrl;  //N
    private HttpURLConnection urlConnection;
    private static final int UPFILE_CONNECT_TIME = 40 * 1000;
    private static final int UPFILE_READ_TIME = 40 * 1000;
    private static final String CHARSET = "utf-8";
    protected String CONTENT_TYPE = "multipart/form-data";//内容类型
    protected String BOUNDARY = UUID.randomUUID().toString();//边界标识   随机生成
    private String mLocalFilePath;
    protected ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private Map<String, String> mParams;
    protected String PREFIX = "--";//分割符
    protected String LINEEND = "\r\n"; //回车换行


    public UpFileController(NetworkClientCallback mNetworkClientCallback,
                            String serviceSubmitUrl,
                            String mLocalFilePath,
                            Map<String, String> mParams) {
        this.mNetworkClientCallback = mNetworkClientCallback;
        this.serviceSubmitUrl = serviceSubmitUrl;
        this.mLocalFilePath = mLocalFilePath;
        this.mParams = mParams;
    }

    @Override
    public void setNetworkCallback(NetworkClientCallback callback) {
        this.mNetworkClientCallback = callback;
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
        urlConnection.setRequestProperty("Charset", CHARSET);
        urlConnection.setRequestProperty("connection", "keep-alive");
        urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);


        DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
        File file = new File(mLocalFilePath);
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : mParams.entrySet()) {//????
            builder.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINEEND)
                    .append("Content-Disposition:form-data;name=\""
                            + entry.getKey() + "\"" + LINEEND)
                    .append("Content-Type:application/x-www-from-urlencoded;charset=" + CHARSET + LINEEND)
                    .append("Content-Transfer-Encoding:8bit" + LINEEND)
                    .append(LINEEND)
                    .append(entry.getValue())
                    .append(LINEEND);
        }
        dos.write(builder.toString().getBytes());

        if (file.exists()) {
            builder = new StringBuilder();
            /*上传附件属性*/
            builder.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINEEND)
                    .append("Content-Disposition:form-data;name=\"file\";" +
                            "filename=\"" + file.getName() + "\"" + LINEEND)
                    .append("Content-Type:application/octet-stream;charset=" + CHARSET + LINEEND)
                    .append(LINEEND);
            dos.write(builder.toString().getBytes());

            FileInputStream fileInputStream = new FileInputStream(file);
            int contentLength = fileInputStream.available(); //上传文件的大小
            Log.d("上传文件的大小:", String.valueOf(contentLength));
            int sum = 0;
            int length = 0;
            byte[] buffer = new byte[512];
            while ((length = fileInputStream.read(buffer)) != -1) {
                dos.write(buffer, 0, length);
                sum += length;
                mNetworkClientCallback.onUploadProcess(length, sum, contentLength);
            }
            CloseStreamUtil.close(fileInputStream);
            dos.write(LINEEND.getBytes());
        }
    }

    @Override
    public void uploadPostContent(OutputStream outputStream) throws IOException {

    }

    @Override
    public boolean isConnectEffectived() throws IOException {
        return isConnectEffectived;
    }

    @Override
    public void processingDataFromServer() throws IOException {
        byte[] buffer = new byte[512];
        InputStream inputStream = urlConnection.getInputStream();
        while (inputStream.read(buffer) != -1) {
            byteArrayOutputStream.write(buffer, 0, buffer.length);
        }
        byteArrayOutputStream.flush();
    }

    @Override
    public void disconnect() {
        isConnectEffectived=false;
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }

    @Override
    public void close() {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        CloseStreamUtil.close(byteArrayOutputStream);


    }

    @Override
    public String getData() {
        return byteArrayOutputStream.toString();
    }


}
