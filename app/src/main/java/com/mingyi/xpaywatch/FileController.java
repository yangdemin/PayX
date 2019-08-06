package com.mingyi.xpaywatch;


import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileController {

    private Context mContext;
    private volatile static FileController m_instance;
    private final String LOG_FILENAME = "LOG_FILE";
    private FileController(){
    }

//    public static synchronized FileController getFileControl() {
//        if (m_instance == null) {
//            synchronized (RpcTestDataControlIF.class) {
//                if (m_instance == null) {
//                    m_instance = new FileController();
//                }
//            }
//        }
//        return m_instance;
//    }

    public void init(Context context) {
        mContext = context;
    }


    public void deleteLogFile(){
        try {
            File file = new File(mContext.getFilesDir(), LOG_FILENAME);

            if(file.exists()){
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFromLogFile() {
        File file = new File(mContext.getFilesDir(), LOG_FILENAME);
        String data = null;
        BufferedReader br = null;
        if(!file.exists())
            return null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n\r");
                line = br.readLine();
            }
            data = sb.toString();
            if(!data.isEmpty()){
                return data;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return data;
        }
    }

    public void writeToLogFile(String s) {
        if (s == null) return;
        FileOutputStream outputStream = null;

        try {
            File file = new File(mContext.getFilesDir(), LOG_FILENAME);
            outputStream = new FileOutputStream(file, true); // will overwrite existing data

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write(s.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
