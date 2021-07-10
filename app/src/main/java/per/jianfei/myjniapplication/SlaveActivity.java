package per.jianfei.myjniapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SlaveActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slave);
        android.widget.Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        android.widget.Button button1 = findViewById(R.id.button3);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            Log.d("debug", "开始发送广播");
            sendBroadcast(new Intent("myAction"));
            Log.d("debug", "发送完广播之后SlaveActivity也终止，然后看看意图服务是否还在后台运行");
            finish();
        } else {
            Log.d("debug", "开始发送广播1");
            sendBroadcast(new Intent("myAction1"));
        }
    }
}