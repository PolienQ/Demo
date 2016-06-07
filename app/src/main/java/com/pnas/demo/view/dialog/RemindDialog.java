package com.pnas.demo.view.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.pnas.demo.R;
import com.pnas.demo.utils.ToolUtils;

/***********
 * @author 彭浩楠
 * @date 2016/5/31
 * @describ
 */
public class RemindDialog extends BaseDialog implements View.OnClickListener {

    private final Activity mActivity;

    public RemindDialog(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_remind_dialog);

    }

    @Override
    public void show() {
        super.show();
        ToolUtils.setWindowAlpha(mActivity, 0.7f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ToolUtils.setWindowAlpha(mActivity, 1);
    }

    @Override
    public void onClick(View v) {

    }
}
