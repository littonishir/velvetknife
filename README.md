# velvetknif

## 天鹅绒刀

ViewBind ：取代findViewById

OnClick ： 设置点击事件

CheckNet ：检查网路是否可以用

## 使用

```java
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
```

## 代码持续更新

需要新功能 欢迎提issue 

