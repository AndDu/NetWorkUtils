package com.example.networkutil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;

/**
 * Created by Administrator on 2017/8/10.
 */

public class NetworkClient {


    public static final String GET = NetworkController.GET;
    public static final String POST = NetworkController.POST;

    private String mRequestType = GET;
    private NetworkController mNetworkController;
    private NetworkClientCallback mNetworkClientCallback;

    public NetworkClient setNetworkController(NetworkController networkController) {
        mNetworkController = networkController;
        return this;
    }

    public void setmRequestType(String mRequestType) {
        this.mRequestType = mRequestType;
    }

    protected void disConnect() {
        mNetworkClientCallback = NetworkClientCallback.NULL;
        mNetworkController.setNetworkCallback(NetworkClientCallback.NULL);
        mNetworkController.disconnect();
    }


    protected void connect() {
        mNetworkClientCallback = mNetworkController.getNetworkCallback();
        try {
            mNetworkController.openConnect();
            mNetworkController.setRequestType(mRequestType);
            mNetworkController.requestServer();
            if (mNetworkController.isConnectEffectived()) {
                mNetworkController.processingDataFromServer();
                mNetworkClientCallback.onFinish(mNetworkController.getData());
            } else {
                mNetworkClientCallback.onFail(null, "服务器处理异常，下载失败");
            }
            mNetworkController.close();
        } catch (IOException e) {
//            LogUtil.set("network", "-------NC-------中断 ").error();
            mNetworkClientCallback.onFail(null, "网络连接超时，请检查网络！");
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

}
