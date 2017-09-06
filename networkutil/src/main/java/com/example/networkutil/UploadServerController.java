package com.example.networkutil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Yellow on 2017/9/4.
 */

public class UploadServerController extends UpFileController {

    private String mFileName;

    public UploadServerController(String fileName, String serviceSubmitUrl, Map<String, String> mParams,NetworkClientCallback networkClientCallback) {
        super(networkClientCallback, serviceSubmitUrl, null, mParams);
        mFileName = fileName;

    }

    @Override
    public void requestServer() throws IOException {
        urlConnection.setRequestMethod(POST);
        urlConnection.setRequestProperty("Charset", CHARSET);
        urlConnection.setRequestProperty("connection", "keep-alive");
        urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

        DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : mParams.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEEND);
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEEND);
            sb.append("Content-Type: application/x-www-form-urlencoded; charset=" + CHARSET + LINEEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEEND);
            sb.append(LINEEND);
            sb.append(entry.getValue());
            sb.append(LINEEND);
        }
        dos.write(sb.toString().getBytes());

         /* 然后拼接上传的附件参数属性 */
        sb = new StringBuilder();
        sb.append(PREFIX);
        sb.append(BOUNDARY);
        sb.append(LINEEND);
        sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + mFileName + "\"" + LINEEND);
        sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEEND);
        sb.append(LINEEND);
        dos.write(sb.toString().getBytes());

        //上传附件
        uploadPostContent(dos);

        dos.write(LINEEND.getBytes());
        byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINEEND).getBytes();
        dos.write(end_data);
        dos.flush();
    }
}
