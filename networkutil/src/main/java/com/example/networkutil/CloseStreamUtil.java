package com.example.networkutil;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/14.
 */

public class CloseStreamUtil {

    public static void close( Closeable closeable){
        if (closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
