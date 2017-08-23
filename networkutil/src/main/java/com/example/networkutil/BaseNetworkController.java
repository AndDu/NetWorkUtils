package com.example.networkutil;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Administrator on 2017/8/14.
 */

public abstract class BaseNetworkController implements NetworkController {

    private NetworkClientCallback mNetworkClientCallback;
    private String mRequestType;
    private boolean isConnectEffectived;
    //totototo
    private String requestUrl;
    private HttpURLConnection urlConnection;
    private OutputStream serverOutputStream;
    private InputStream inputStream;
    private FileOutputStream fileWrite; //未初始化
    private ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();


    public BaseNetworkController(NetworkClientCallback mNetworkClientCallback, String requestUrl) {
        this.mNetworkClientCallback = mNetworkClientCallback;
        this.requestUrl = requestUrl;
    }

    public BaseNetworkController(NetworkClientCallback mNetworkClientCallback, String requestUrl, FileOutputStream fileWrite) {
        this.mNetworkClientCallback = mNetworkClientCallback;
        this.requestUrl = requestUrl;
        this.fileWrite = fileWrite;
    }

    @Override
    public void setNetworkCallback(NetworkClientCallback callback) {
        mNetworkClientCallback = callback;
    }


    @Override
    public NetworkClientCallback getNetworkCallback() {
        return mNetworkClientCallback;
    }

    @Override
    public void setRequestType(String type) {
        mRequestType = type;
    }

    @Override
    public void openConnect() throws IOException, KeyManagementException {
        if (isConnectEffectived()) {
            System.setProperty("http.keepalive", "false"); //保持长连接。如果是false就仅支持一次链接。true支持多次连接。
            URL url = new URL(requestUrl);
            if (requestUrl.startsWith(TYPE_HTTP)) {
                urlConnection = (HttpURLConnection) url.openConnection();
            } else if (requestUrl.startsWith(TYPE_HTTPS)) {
                try {
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(HttpsURLConnection.getDefaultSSLSocketFactory());
                    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    throw new Exception("requestUrl have to startwith http: or https:");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            /*
            httpUrlConnection.setDoOutput(true);以后就可以使用conn.getOutputStream().write()
//httpUrlConnection.setDoInput(true);以后就可以使用conn.getInputStream().read();
//
//get请求用不到conn.getOutputStream()，因为参数直接追加在地址后面，因此默认是false。
//post请求（比如：文件上传）需要往服务区传输大量的数据，这些数据是放在http的body里面的，因此需要在建立连接以后，往服务端写数据。
//
//因为总是使用conn.getInputStream()获取服务端的响应，因此默认值是true。
             */
//            urlConnection.setDoInput(true); //默认值是true。
            urlConnection.setUseCaches(false);//默认值是true。
            //This method is used to enable streaming of a HTTP request body without internal buffering, when the content length is not known in advance.
//            urlConnection.setChunkedStreamingMode(512);
            urlConnection.setConnectTimeout(CONNECT_TIME);
            urlConnection.setReadTimeout(READ_TIME);
        }
    }

    @Override
    public void requestServer() throws IOException {
        urlConnection.setRequestMethod(mRequestType);
        if (mRequestType.equals(POST)) {
            urlConnection.setDoOutput(true);
            serverOutputStream = urlConnection.getOutputStream();
            uploadPostContent(serverOutputStream);  //j加载上传的
            serverOutputStream.flush();
            CloseUtil.close(serverOutputStream);
        }

    }

    @Override
    public boolean isConnectEffectived() throws IOException {
        return isConnectEffectived = urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    @Override
    public void processingDataFromServer() throws IOException {
        if (isConnectEffectived()) {
            byte[] bytes = new byte[1024];
            int contentLength = urlConnection.getContentLength();
            int bufferSize = 0;
            int bufferSum = 0;
            inputStream = urlConnection.getInputStream();
            while (-1 != (bufferSize = inputStream.read(bytes))) {
                if (fileWrite!=null){
                    fileWrite.write(bytes,0,bytes.length);
                }else {
                    byteArrayOutputStream.write(bytes,0,bytes.length);
                }
                bufferSum+=bufferSize;
                mNetworkClientCallback.onDownloadProcess(bufferSize,bufferSum,contentLength);
            }
        }else {
            close();
            throw new IOException();
        }

    }

    @Override
    public void disconnect() {
        isConnectEffectived=false;
        Log.d("network","请求中断 http 连接");
        if (urlConnection!=null){
            urlConnection.disconnect();
            Log.d("network","中断 http 连接");
        }
        CloseUtil.close(serverOutputStream);
        if (serverOutputStream!=null){
            Log.d("network","中断 http serverOutputStream");
        }

    }

    @Override
    public void close() {
        if (urlConnection!=null){
            urlConnection.disconnect();
        }
        CloseUtil.close(byteArrayOutputStream);
        CloseUtil.close(fileWrite);
        CloseUtil.close(inputStream);
        CloseUtil.close(serverOutputStream);
    }

    @Override
    public String getData() {
        return byteArrayOutputStream.toString();
    }

    private class MyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
