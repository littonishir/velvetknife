package com.littonishir.velvetknife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.littonishir.velvetknife.annotation.CheckNet;
import com.littonishir.velvetknife.annotation.OnClick;
import com.littonishir.velvetknife.annotation.ViewBind;

public class MainActivity extends AppCompatActivity {
    @ViewBind(R.id.tv)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VelvetKnife.bind(this);
    }
    @OnClick(R.id.tv)
    @CheckNet(netErrMsg = "请检查网路",showToast = true)
    public void test(){
        textView.setText("网络通信正常");
    }
}
