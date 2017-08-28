package com.example.networkutil;

import android.graphics.Bitmap;
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
 * Created by Yellow on 2017/8/24.
 */

public class UploadMultipartController implements NetworkController {


    private NetworkClientCallback mNetworkClientCallback;
    private String serviceSubmitUrl;  //N
    private HttpURLConnection urlConnection;
    private static final int UPFILE_CONNECT_TIME = 40 * 1000;
    private static final int UPFILE_READ_TIME = 40 * 1000;
    private static final String CHARSET = "utf-8";
    protected String CONTENT_TYPE = "multipart/form-data";//内容类型
    protected String BOUNDARY = UUID.randomUUID().toString();//边界标识   随机生成
    protected ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private Map<String, String> mParams;
    protected String PREFIX = "--";//分割符
    protected String LINEEND = "\r\n"; //回车换行
    private List<Annex> annices;

    public UploadMultipartController(NetworkClientCallback mNetworkClientCallback,
                                     String serviceSubmitUrl,
                                     List<Annex> annices,
                                     Map<String, String> mParams) {
        this.mNetworkClientCallback = mNetworkClientCallback;
        this.serviceSubmitUrl = serviceSubmitUrl;
        this.mParams = mParams;
        this.annices = annices;
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

        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Charset", CHARSET);
        urlConnection.setRequestProperty("connection", "keep-alive");
        urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

        DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
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
        if (annices == null) return;
        for (int i = 0; i < annices.size(); i++) {
            Annex annex = annices.get(i);
            String annexType = annex.getAnnexType();
            //拼接上传参数
            builder = new StringBuilder();
            builder.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINEEND)
                    .append("Content-Disposition:form-data;name=\"" + annex.getFileCategory() + "\";" +
                            "filename=\"" + annex.getName() + "\"" + LINEEND)
                    .append("Content-Type:application/octet-stream;charset=" + CHARSET + LINEEND)
                    .append(LINEEND);


            if (annexType.equals(Annex.TYPE_FILE)) {
                File file = new File(String.valueOf(annex.getValue()));
            /*上传附件属性*/
                if (file.exists()) {
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

            } else if (annexType.equals(Annex.TYPE_BITMAP)) {
                dos.write(builder.toString().getBytes());
                Bitmap bitmap = (Bitmap) annex.value;
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    byte[] arr = out.toByteArray();
                    int blockLength = 512;
                    int blockIndex = 0;
                    int blockSize = arr.length / blockLength;
                    int quyu = arr.length / blockLength;
                    int sum = 0;
                    while (blockIndex < blockSize) {
                        dos.write(arr, blockIndex * blockLength, blockLength);
                        sum += blockLength;
                        mNetworkClientCallback.onUploadProcess(blockLength, sum, arr.length);
                        blockIndex++;
                    }
                    if (quyu > 0) {
                        dos.write(arr, blockIndex * blockLength, quyu);
                        mNetworkClientCallback.onUploadProcess(quyu, arr.length, arr.length);
                    }
                    CloseStreamUtil.close(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dos.write(LINEEND.getBytes());
            } else {
                try {
                    throw new Exception("未知文件类型");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        byte[] end_data=(PREFIX+BOUNDARY+PREFIX+LINEEND).getBytes();
        dos.write(end_data);
        dos.flush();

    }

    @Override
    public void uploadPostContent(OutputStream outputStream) throws IOException {

    }

    @Override
    public boolean isConnectEffectived() throws IOException {
        return urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    @Override
    public void processingDataFromServer() throws IOException {
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = new byte[512];
        while (inputStream.read(bytes) != -1) {
            byteArrayOutputStream.write(bytes, 0, bytes.length);
        }
        byteArrayOutputStream.flush();
    }

    @Override
    public void disconnect() {
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

    public static class Annex {
        public static final String TYPE_FILE = "file";
        public static final String TYPE_BITMAP = "bitmap";
        private String annexType = "";// 附件类型
        private String name;
        private Object value;
        private String fileCategory = "files";// 上传的文件类型

        public Annex(String type, String fileCategory) {
            this.annexType = type;
            this.fileCategory = fileCategory;
        }

        public String getAnnexType() {
            return annexType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getFileCategory() {
            return fileCategory;
        }

        public void setFileCategory(String fileCategory) {
            this.fileCategory = fileCategory;
        }
    }
}
