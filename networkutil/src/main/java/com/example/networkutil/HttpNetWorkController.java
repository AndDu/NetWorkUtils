package com.example.networkutil;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Yellow on 2017/9/1.
 */

public class HttpNetWorkController {




    public static void doGet(String requestUrl, NetworkClientCallback clientCallback) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setNetworkController(new BaseNetworkController(clientCallback, requestUrl) {
            @Override
            public void uploadPostContent(OutputStream outputStream) throws IOException {

            }
        });
        ThreadPoolController.getInstance().submit(new DeckerPostServer(networkClient));
    }

    public static void doPost(String requestUrl, final Map<String, String> params, NetworkClientCallback clientCallback) {
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
     * 上传文件（单个）
     *
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


    /**
     * 单个图片上传
     *
     * @param bitmap
     * @param fileName
     * @param serverSubmitUrl
     * @param params
     * @param networkClientCallback
     */
    public void uploadImageView(final Bitmap bitmap, String fileName, String serverSubmitUrl, Map<String, String> params,
                                final NetworkClientCallback networkClientCallback) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setNetworkController(new UploadServerController(fileName, serverSubmitUrl, params, networkClientCallback) {

            @Override
            public void uploadPostContent(OutputStream outputStream) throws IOException {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                byte[] array = out.toByteArray();
                int i = 0;
                int temLength = 512;
                int j = array.length / temLength;
                int remainder = array.length % temLength;
                int sum = 0;
                while (i < j) {
                    outputStream.write(array, i * temLength, temLength);
                    sum += temLength;
                    networkClientCallback.onUploadProcess(temLength, sum, array.length);
                    i++;
                }
                if (remainder > 0) {
                    outputStream.write(array, i * temLength, remainder);
                    networkClientCallback.onUploadProcess(remainder, array.length, array.length);
                }
                CloseStreamUtil.close(out);
            }
        });
    }

    public void upload(String serverSubmitUrl, Map<String, String> params, List<UploadMultipartController.Annex> annexList,
                       final NetworkClientCallback networkClientCallback) {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setNetworkController(new UploadMultipartController(networkClientCallback, serverSubmitUrl, annexList, params));
        ThreadPoolController.getInstance().submit(new DeckerUploadServer(networkClient));
    }


}
