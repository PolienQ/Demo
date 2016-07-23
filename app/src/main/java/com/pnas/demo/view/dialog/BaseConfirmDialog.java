package com.pnas.demo.view.dialog;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.utils.ToolUtils;


/***********
 * @author pans
 * @date 2015-12-14 11:31
 * @describ 中间位置的取消, 确认对话框
 */
public class BaseConfirmDialog extends BaseDialog implements OnClickListener {
    private Window window = null;
    protected TextView tvConfirmTitle, tvConfirmOk, tvConfirmCancel, tvConfirmContent, tvConfirmContent2, tvTitle;
    protected LinearLayout llContent;
    protected EditText etAddName;

    public BaseConfirmDialog(Context context) {
        super(context);
    }

    public BaseConfirmDialog(Context context, ConfirmDialogBtnClickListener listener) {
        super(context, listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_base_confirm_dialog);

        tvConfirmTitle = (TextView) findViewById(R.id.tv_confirm_title);

        tvConfirmContent = (TextView) findViewById(R.id.tv_confirm_content);
        tvConfirmContent2 = (TextView) findViewById(R.id.tv_confirm_content_2);

        tvConfirmOk = (TextView) findViewById(R.id.tv_confirm_ok);
        tvConfirmOk.setOnClickListener(this);

        tvConfirmCancel = (TextView) findViewById(R.id.tv_confirm_cancel);
        tvConfirmCancel.setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setOnClickListener(this);

        llContent = (LinearLayout) findViewById(R.id.ll_content);
        llContent.setOnClickListener(this);

        etAddName = (EditText) findViewById(R.id.et_addbookname);

        init();
    }

    protected void init() {
        tvConfirmOk.setText(R.string.base_ok);

        tvConfirmCancel.setText(R.string.base_cancel);
    }

    public String getEtAddName() {
        return etAddName.getText().toString();
    }

    public void showDialog(int x, int y) {
        windowDeploy(x, y);
        // 设置触摸对话框以外的地方取消对话框  true:点击dialog之外关闭  false:点击dialog之外不关闭
        setCanceledOnTouchOutside(false);
        show();
    }

    // 设置窗口显示
    private void windowDeploy(int x, int y) {
        window = getWindow(); // 得到对话框
//        window.setWindowAnimations(R.style.dialog_window_anim); // 设置窗口弹出动画
        window.setBackgroundDrawableResource(R.color.Transparent);
        // //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        // 根据x，y坐标设置窗口需要显示的位置
        wl.x = x; // x小于0左移，大于0右移
        wl.y = y; // y小于0上移，大于0下移
        // wl.alpha = 0.6f; //设置透明度
        // wl.gravity = Gravity.BOTTOM; //设置重力
        window.setAttributes(wl);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_confirm_cancel:
                if (null != listener)
                    listener.onCancelBtnClick();
                dismiss();
                break;
            case R.id.tv_confirm_ok:

                if (null != listener)
                    listener.onOKBtnClick();
                ToolUtils.hideSoftInput((Activity) mContext);
                dismiss();
                break;
        }
    }

    public void show(String title, String message) {
        if (!isShowing())
            show();

        tvConfirmTitle.setText(title);
        tvConfirmContent.setText(message);
    }

    public void showError(String errCode, String errorMessage) {
        this.errCode = errCode;
        super.show();
        if (!TextUtils.isEmpty(errorMessage)) {
            tvConfirmContent.setText(errorMessage);
        } else {
            tvConfirmContent.setText("网络连接失败");
        }

    }
}