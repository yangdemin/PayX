package com.mingyi.xpaywatch;


import android.content.Context;
import android.graphics.Bitmap;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;

import static android.content.ContentValues.TAG;
import static com.orhanobut.logger.Logger.e;

class HeartBeatCtrl {

    private Context mContext;
    private static final String heartUrl = "http://api.wanshangpay.com/soft/heartbeat";
    private static final String access_token = "973e365088ac177e6729143fc7ad0772";
    private static final String data = "[{'type':'wechat','account':'test1'}]";
    private static final String uuid = "973e365088ac177e6729143fc7ad0772";

    private HeartBeatCtrl() {
    }

    ;

    //send heart
    public void sendHeart(String uuid, String msg) {

    }

    public void connect() {
        Logger.d("准备链接...");
        InetAddress serverAddr;
        try {
            socket = new Socket(Bitmap.Config.Host, Bitmap.Config.SockectPort);
            _connect = true;
            mReceiveThread = new ReceiveThread();
            receiveStop = false;
            mReceiveThread.start();
            LogUtil.e(TAG, "链接成功.");

        } catch (Exception e) {
            Logger.e(TAG, "链接出错." + e.getMessage().toString());
            e.printStackTrace();
        }
    }
}
    private class ReceiveThread extends Thread {
        private byte[] buf;
        private String str = null;

        @Override
        public void run() {
            while (true) {
                try {
                    // LogUtil.e(TAG, "监听中...:"+socket.isConnected());
                    if (socket != null && socket.isConnected()) {

                        if (!socket.isInputShutdown()) {
                            BufferedReader inStream = new BufferedReader(
                                    new InputStreamReader(
                                            socket.getInputStream()));
                            String content = inStream.readLine();
                            if (content == null)
                                continue;
                            Logger.e(TAG, "收到信息:" + content);
                            Logger.e(TAG, "信息长度:" + content.length());
                            if (!content.startsWith("CMD:"))
                                continue;
                            int spacePos = content.indexOf(" ");
                            if (spacePos == -1)
                                continue;
                            String cmd = content.substring(4, spacePos);
//                            String body = StringUtil.DecodeBase64(content
//                                    .substring(spacePos));
                            String body = content.substring(spacePos).trim();
                            Logger.e(TAG, "收到信息(CMD):" + cmd);
                            Logger.e(TAG, "收到信息(BODY):" + body);
                            if (cmd.equals("LOGIN")) {
                                // 登录
                                ReceiveLogin(body);
                                continue;
                            }
                            if (cmd.equals("KEEPLIVE")) {
                                if (!body.equals("1")) {
                                    Log.e(TAG, "心跳时检测到异常，重新登录!");
                                    socket = null;
                                    KeepAlive();
                                } else {
                                    Date now = Calendar.getInstance().getTime();
                                    lastKeepAliveOkTime = now;
                                }
                                continue;
                            }
                        }
                    } else {
                        if (socket != null)
                            Logger.e(TAG, "链接状态:" + socket.isConnected());
                    }

                } catch (Exception e) {
                    Logger.e(TAG, "监听出错:" + e.toString());
                    e.printStackTrace();
                }
            }
        }

        //check heart

        public void KeepAlive() {
            // 判断socket是否已断开,断开就重连
            if (lastKeepAliveOkTime != null) {
                Logger.e(
                        TAG,
                        "上次心跳成功时间:"
                                + DateTimeUtil.dateFormat(lastKeepAliveOkTime,
                                "yyyy-MM-dd HH:mm:ss"));
                Date now = Calendar.getInstance().getTime();
                long between = (now.getTime() - lastKeepAliveOkTime.getTime());// 得到两者的毫秒数
                if (between > 60 * 1000) {
                    Logger.e(TAG, "心跳异常超过1分钟,重新连接:");
                    lastKeepAliveOkTime = null;
                    socket = null;
                }

            } else {
                lastKeepAliveOkTime = Calendar.getInstance().getTime();
            }

            if (!checkIsAlive()) {
                Logger.e(TAG, "链接已断开,重新连接.");
                connect();
                if (loginPara != null)
                    Login(loginPara);
            }

            //此方法是检测是否连接
            boolean checkIsAlive () {
                if (socket == null)
                    return false;
                try {
                    socket.sendUrgentData(0xFF);
                } catch (IOException e) {
                    return false;
                }
                return true;

            }
            //然后发送数据的方法
            public void sendmessage (String msg){
                if (!checkIsAlive())
                    return;
                Logger.e(TAG, "准备发送消息:" + msg);
                try {
                    if (socket != null && socket.isConnected()) {
                        if (!socket.isOutputShutdown()) {
                            PrintWriter outStream = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream())),
                                    true);

                            outStream.print(msg + (char) 13 + (char) 10);
                            outStream.flush();
                        }
                    }
                    Logger.e(TAG, "发送成功!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }