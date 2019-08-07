package com.mingyi.xpaywatch;

public class Reception {

    private content content;
    private int status;
    private static class content {
        private int code;
        private String msg;
        private int data;
        private String exe_time;
    }

    //定义 输出返回数据 的方法
    public void show() {
        System.out.println(status);
        System.out.println(content.code);
        System.out.println(content.msg);
        System.out.println(content.data);
        System.out.println(content.exe_time);
    }

}