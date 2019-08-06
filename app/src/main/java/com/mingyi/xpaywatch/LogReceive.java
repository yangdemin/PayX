package com.mingyi.xpaywatch;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class LogReceive extends Fragment {
    private ScrollView loggerText;
    private LinearLayout logfield;

    private LogReceiver logReceiver;

    //监听广播，实时添加textview
    private class LogReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
//            String text = arg1.getStringExtra(...);
//            logTextToScroll(text);
        }
    }

    //注册广播
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();

        logReceiver = new LogReceiver();
        intentFilter = new IntentFilter();
        // intentFilter.addAction(LOG_TEXT_ACTION);
        getActivity().registerReceiver(logReceiver, intentFilter);
    }


    @Override
    public void onResume() {
        super.onResume();
        loggerText.post(new Runnable() {
            @Override
            public void run() {
                loggerText.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    //注销receiver
    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(logReceiver);
    }


//    //删除log文件
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        FileController.getFileControl().deleteLogFile();
//    }

    //将带有log的textview 添加进布局中
    private void logTextToScroll(String s){
        if(s == null || s.isEmpty())
            return;

        TextView t = new TextView(getContext());
        s += "\n";
        t.setText(s);
        logfield.addView(t);
    }

    //清除界面的log，可以在界面添加button 或者菜单，通过点击实现清除log
    private void clearLog(){
        for(int i = 0; i < logfield.getChildCount(); i++){
            TextView t = (TextView) logfield.getChildAt(i);
            t.setText("");
            logfield.removeAllViews();
        }
    }

}