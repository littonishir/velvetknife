package com.littonishir.velvetknife;

import android.app.Activity;
import android.view.View;

/**
 * Created by littonishir on 2018/9/13.
 * Assistive Tool(辅助工具）
 */
public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int id) {
        return mActivity != null ? mActivity.findViewById(id) : mView.findViewById(id);
    }
}
