package com.run.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.run.common.R;


public class UToastDialog {
    public static UToastDialog mToastDialog;
    private Toast toast;

    private UToastDialog() {
    }

    public static UToastDialog getToastDialog() {
        if (mToastDialog == null) {
            mToastDialog = new UToastDialog();
        }
        return mToastDialog;
    }

    /**
     * 显示
     */
    @SuppressLint("WrongConstant")
    public void ToastShow(Context context, String msg, Double count) {
        if (canToast) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_hint_layout, null);
            TextView tv_title = view.findViewById(R.id.tv_title);
            TextView tv_count = view.findViewById(R.id.tv_count);
            if (count > 50) {
                count = 50.00;
            }
            tv_count.setText("+" + count);
            tv_title.setText(msg);
            toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, 0); // Toast显示的位置
            toast.setDuration(5000); // Toast显示的时间
            toast.setView(view);
            toast.show();
        }
    }


    public boolean isCanToast() {
        return canToast;
    }

    public void setCanToast(boolean canToast) {
        this.canToast = canToast;
    }

    private boolean canToast = false;

    public void ToastCancel() {
        if (canToast = false)
            if (toast != null) {
                toast.cancel();
            }
    }
}