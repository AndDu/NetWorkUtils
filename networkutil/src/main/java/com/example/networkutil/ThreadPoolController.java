package com.example.networkutil;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2017/8/16.
 */

public class ThreadPoolController {

    private final static int mThreadNum=5;
    private ExecutorService executorService=Executors.newFixedThreadPool(mThreadNum);
    private volatile static ThreadPoolController threadPoolControllerl;


    public void submit(TaskController taskController){
        RequestServerTask serverTask = new RequestServerTask(taskController);
        executorService.submit(serverTask);
    }

    private ThreadPoolController(){

    }

    public static ThreadPoolController getInstance(){

        if (threadPoolControllerl==null){
            synchronized (ThreadPoolController.class){
                if (threadPoolControllerl==null){
                    threadPoolControllerl=new ThreadPoolController();
                }
            }
        }
        return threadPoolControllerl;
    }




    public void cancel(TaskController taskController){
        if (taskController!=null){
            taskController.cancel();
        }
    }


    public static class RequestServerTask implements Runnable{

        private TaskController taskController;

        public RequestServerTask(TaskController taskController) {
            this.taskController = taskController;
        }

        @Override
        public void run() {
            Log.d("ThreadPoolController","开始执刑！");
            taskController.excute();
        }
    }



}
