package com.example.pnas.demo.view.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.example.pnas.demo.R;


/***********
 *
 * @author 彭浩楠
 * @date 2015-12-14 11:31
 * @describ 对话框的基类
 *
 */
public class BaseDialog extends Dialog {

    protected String errCode;
    protected ConfirmDialogBtnClickListener listener;

    public BaseDialog(Activity activity) {
        super(activity, R.style.YxDialog_Alert);
    }

    public BaseDialog(Context context) {
        super(context, R.style.YxDialog_Alert);
    }

    public BaseDialog(Context context, int theme) {
        super(context, R.style.YxDialog_Alert);
    }

    public BaseDialog(Context context, ConfirmDialogBtnClickListener dialogBtnClickListener) {
        super(context, R.style.YxDialog_Alert);
        listener = dialogBtnClickListener;
    }

    public void setListener(ConfirmDialogBtnClickListener listener) {
        this.listener = listener;
    }

    public String getDialogName() {
        return getClass().getSimpleName();
    }

}
