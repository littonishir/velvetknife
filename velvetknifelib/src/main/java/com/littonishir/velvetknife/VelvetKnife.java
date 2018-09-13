package com.littonishir.velvetknife;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.littonishir.velvetknife.annotation.CheckNet;
import com.littonishir.velvetknife.annotation.OnClick;
import com.littonishir.velvetknife.annotation.ViewBind;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by littonishir on 2018/9/13.
 */
public class VelvetKnife {

    private static String netErrMsg;
    private static boolean showToast;

    public static void bind(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    private static void inject(ViewFinder viewFinder, Object object) {
        injectFiled(viewFinder, object);
        injectEvent(viewFinder, object);
    }

    /**
     * 注入属性
     */
    private static void injectFiled(ViewFinder viewFinder, Object object) {
        // 1.获取目标Class
        Class<?> mClass = object.getClass();
        // 2.获取所有属性（包含公有私有）
        Field[] mfields = mClass.getDeclaredFields();
        // 3.遍历属性
        for (Field field : mfields) {
            // 4.查找目标注解
            ViewBind annotation = field.getAnnotation(ViewBind.class);
            if (annotation != null) {
                // 5.获取注解上携带的ID
                int value = annotation.value();
                // 6.根据ID findViewById 找到View
                View view = viewFinder.findViewById(value);
                // 7.view不为空时 操作属性
                if (view != null) {
                    // 8.开启属性操作权限
                    field.setAccessible(true);
                    try {
                        // 9.动态的注入找到的View
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 事件注入
     */
    private static void injectEvent(ViewFinder viewFinder, Object object) {
        // 1.获取类里面所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        // 2.遍历方法
        for (Method method : methods) {
            // 3.查找目标注解
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                // 4.获取注解上携带的ID
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    // 5.根据ID findViewById 找到View
                    View view = viewFinder.findViewById(viewId);
                    // 扩展功能 检测网络
                    CheckNet annotation = method.getAnnotation(CheckNet.class);
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                    if (isCheckNet) {
                        // 获取netErrMsg 是否显示Toast
                        netErrMsg = annotation.netErrMsg();
                        showToast = annotation.showToast();
                    }
                    if (view != null) {
                        // 6.view.setOnClickListener
                        view.setOnClickListener(new DeclaredOnClickListener(method, object, isCheckNet, netErrMsg));
                    }

                }
            }
        }
    }


    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;
        private String mNetMsg;

        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet, String netMsg) {
            this.mObject = object;
            this.mMethod = method;
            this.mIsCheckNet = isCheckNet;
            this.mNetMsg = netMsg;
        }

        @Override
        public void onClick(View v) {
            // 是否检测网络
            if (mIsCheckNet) {
                if (!networkAvailable(v.getContext())) {
                    if (showToast) {
                        Toast.makeText(v.getContext(), mNetMsg, Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            }
            // 点击会调用该方法
            try {
                // 所有方法都可以 包括私有共有
                mMethod.setAccessible(true);
                // 5. 反射执行方法
                mMethod.invoke(mObject, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject, new Object[]{});
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断当前网络是否可用
     */
    private static boolean networkAvailable(Context context) {
        try {
            // 获取连接管理器对象
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
