package per.jianfei.myjniapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyBroadReceiver myBroadReceiver;
    private MyBroadcastReceiver1 myBroadcastReceiver1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.text);
        textView.setOnClickListener(this);
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(this);
//        Toast.makeText(this, MyJNI.sayHello(), Toast.LENGTH_SHORT).show();
        myBroadReceiver = new MyBroadReceiver(this);
        android.content.IntentFilter intentFilter = new IntentFilter("myAction");
        registerReceiver(myBroadReceiver, intentFilter);
        myBroadcastReceiver1 = new MyBroadcastReceiver1();
        android.content.IntentFilter intentFilter1 = new IntentFilter("myAction1");
        registerReceiver(myBroadcastReceiver1, intentFilter1);
        MyIntentService.startActionFoo(this, "param1", "param2");

        Intent serviceIntent = new Intent(this, MyService.class);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    private MyService.PlayerBinder mPlayerBinder;
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayerBinder = (MyService.PlayerBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("debug", "onServiceDisconnected");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadReceiver);
        unregisterReceiver(myBroadcastReceiver1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("per.jianfei.myjniapplication", "per.jianfei.myjniapplication.SlaveActivity"));
            startActivity(intent);
        } else {
            mPlayerBinder.play();
        }
    }

    protected static class MyBroadReceiver extends BroadcastReceiver {
        private final MainActivity mainActivity;

        public MyBroadReceiver(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("debug", "收到广播");
            mainActivity.finish();
        }
    }

    protected static class MyBroadcastReceiver1 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("debug", "第二个广播接收器");
        }
    }
}