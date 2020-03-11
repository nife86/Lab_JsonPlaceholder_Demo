package com.example.lab_JsonPlaceholder_Demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class ProgressDialogUtil {
    private static AlertDialog mAlertDialog;

    public static void showProgressDialog(Context context, String s) {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(context, R.style.CustomProgressDialog).create();
        }

        View loadView = LayoutInflater.from(context).inflate(R.layout.process_dialog, null);
        mAlertDialog.setView(loadView);
        mAlertDialog.setCanceledOnTouchOutside(false);

        TextView tv_jsonDownload = loadView.findViewById(R.id.tv_jsonDownload);
        tv_jsonDownload.setText(s);

        mAlertDialog.show();
    }

    public static void dismissProgressDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }
}
