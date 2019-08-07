package com.mingyi.xpaywatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.TextView;
import com.mingyi.xpaywatch.HeartBeatCtrl;
import java.security.Timestamp;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static TextView mTextL1R1, mTextL2R1, mTextL3R1, mTextL4R1;
    private static TextView mTextL1R2, mTextL2R2, mTextL3R2, mTextL4R2;
    private static TextView mTextL1R3, mTextL2R3, mTextL3R3, mTextL4R3;
    private static final String TAG = "APPLOG";
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        String SerialNumber = android.os.Build.SERIAL;
        // String SerialNumber2 = android.os.Build.getSerial();
        String uuid = ANDROID_ID+SerialNumber;
        System.out.println(">>>>ANDROID_ID is:"+uuid);

        setContentView(R.layout.activity_main);
        setTheme(R.style.AppTheme);//恢复原有的样式

        TextView EditUuid =(TextView)findViewById(R.id.uuidNum);
        EditUuid.setText(uuid);

        mTextL1R1 =(TextView)findViewById(R.id.WechatAccount);
        mTextL1R1.setText("17660643600");
        mTextL2R1 =(TextView)findViewById(R.id.AlipayAccount);
        mTextL2R1.setText("17660643600");
        mTextL3R1 =(TextView)findViewById(R.id.QQAccount);
        mTextL3R1.setText("17660643600");
        mTextL4R1 =(TextView)findViewById(R.id.UnionAccount);
        mTextL4R1.setText("17660643600");

        mTextL1R2 =(TextView)findViewById(R.id.WechatName);
        mTextL1R2.setText("yangdemin");
        mTextL2R2 =(TextView)findViewById(R.id.AlipayName);
        mTextL2R2.setText("yangdemin");
        mTextL3R2 =(TextView)findViewById(R.id.QQName);
        mTextL3R2.setText("yangdemin");
        mTextL4R2 =(TextView)findViewById(R.id.UnionName);
        mTextL4R2.setText("yangdemin");

        mTextL1R3 =(TextView)findViewById(R.id.WechatLogin);
        mTextL1R3.setText("Yes");
        mTextL2R3 =(TextView)findViewById(R.id.AlipayLogin);
        mTextL2R3.setText("Yes");
        mTextL3R3 =(TextView)findViewById(R.id.QQLogin);
        mTextL3R3.setText("Yes");
        mTextL4R3 =(TextView)findViewById(R.id.UnionLogin);
        mTextL4R3.setText("Yes");


        HeartBeatCtrl netCtrl = new HeartBeatCtrl();

        // netCtrl.request();
        netCtrl.sendHeart();
//        //将文本框1的文本赋给文本框2
//        EditText editText2 =(EditText)findViewById(R.id.editText2);
//        editText2.setText(str1.toCharArray(), 0, str1.length());


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void logMessage(String message){
//        //添加时间点
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        String log = timestamp.toString() + "\n" + message + "\n\r";
//        Intent intent = new Intent(LOG_TEXT_ACTION);
//        intent.putExtra(LOG_TEXT_EXTRA, log);
//        sendBroadcast(intent);
//        FileController.getFileControl().writeToLogFile(log);
//    }
    private void initLogger(String tag) {
        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
            .tag(tag) // 全局tag
            .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy) {
        @Override
        public boolean isLoggable(int priority, String tag) {
            return BuildConfig.DEBUG;
        }
        });
    }
}
