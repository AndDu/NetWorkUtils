package com.example.networkutil;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Yellow on 2017/9/1.
 */

public class HttpNetWorkController {


    //"http://www.i5campus.com:9084/mobile/comment/getComments.do?userId=220&pager.currentPage=1"
    public void doGet(String requestUrl, NetworkClientCallback clientCallback) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setNetworkController(new BaseNetworkController(clientCallback, requestUrl) {
            @Override
            public void uploadPostContent(OutputStream outputStream) throws IOException {

            }
        });
        ThreadPoolController.getInstance().submit(new DeckerPostServer(networkClient));
    }

    public void doPost(String requestUrl, final Map<String, String> params, NetworkClientCallback clientCallback) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setNetworkController(new BaseNetworkController(clientCallback, requestUrl) {
            @Override
            public void uploadPostContent(OutputStream outputStream) throws IOException {
                if (params == null) return;
                StringBuilder builder = new StringBuilder();
                boolean isStart = true;
                Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
                while (iterator.hasNext()) {
                    if (!isStart) {
                        builder.append("&");
                    } else {
                        isStart = false;
                    }
                    Map.Entry<String, String> map = iterator.next();
                    builder.append(map.getKey()).append("=").append(map.getValue());
                }
                outputStream.write(builder.toString().getBytes());
            }
        });
        ThreadPoolController.getInstance().submit(new DeckerPostServer(networkClient));
    }


    /**
     * @param localFileUrl          本地文件路径
     * @param submitServerUrl       提交的服务器路径
     * @param params                请求参数
     * @param networkClientCallback
     */
    public void uploadFile(String localFileUrl, String submitServerUrl, Map<String, String> params,
                           NetworkClientCallback networkClientCallback) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setNetworkController(new UpFileController(networkClientCallback, submitServerUrl, localFileUrl, params));
        ThreadPoolController.getInstance().submit(new DeckerUploadServer(networkClient));
    }


    public void uploadImageView(){
        NetworkClient networkClient = new NetworkClient();
//        networkClient.setNetworkController(new upload)
    }


}
