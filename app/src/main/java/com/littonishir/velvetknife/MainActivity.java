package com.littonishir.velvetknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.littonishir.velvetknife.annotation.CheckNet;
import com.littonishir.velvetknife.annotation.OnClick;
import com.littonishir.velvetknife.annotation.ViewBind;

public class MainActivity extends AppCompatActivity {
    @ViewBind(R.id.tv)
    TextView textView;
    @ViewBind(R.id.tv1)
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VelvetKnife.bind(this);
    }

    @OnClick({R.id.tv, R.id.tv1})
    @CheckNet(netErrMsg = "请检查网路", showToast = true)
    public void Onclick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                textView.setText("网络通信正常");
                break;
            case R.id.tv1:
                textView1.setText("그녀를 보면");
                break;
        }
    }
}
