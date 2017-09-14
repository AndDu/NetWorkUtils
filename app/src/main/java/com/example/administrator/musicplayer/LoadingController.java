package com.example.administrator.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.networkutil.ex.IllegalViewGroupException;

/**
 * Created by Yellow on 2017/9/7.
 */

public class LoadingController {

    private ViewGroup rl_root; //必须是framelayout 或者 relativelayout

    private Context context;
    private View loadingView;

    public LoadingController(Context context) {
        this.context = context;
        Activity activity = (Activity) context;
        View decorView = activity.getWindow().getDecorView();
        rl_root = (ViewGroup) decorView.findViewById(R.id.rl_root);
    }

    public void showLoadingView() {
        if (loadingView == null) {
            loadingView = LayoutInflater.from(context).inflate(R.layout.test, rl_root, false);
        }
        if (rl_root != null) {
            rl_root.addView(loadingView);
        }
    }

    public void showLoadingView(int layoutId) {
        if (loadingView == null) {
            loadingView = LayoutInflater.from(context).inflate(layoutId, rl_root, false);
        } else if (loadingView.getId() != layoutId) {
            loadingView = LayoutInflater.from(context).inflate(layoutId, rl_root, false);
        }
        if (rl_root != null) {
            rl_root.addView(loadingView);
        }
    }

    public void showLoadingView(View loadingView) {
        this.loadingView = loadingView;
        if (rl_root != null) {
            rl_root.addView(loadingView);
        }
    }

    public void cancelLoding() {
        Thread thread = Thread.currentThread();
        rl_root.removeView(loadingView);
    }


//    public void

}
