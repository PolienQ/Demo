package com.pnas.demo.ui.shadow;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import com.pnas.demo.base.MyApplication;

import org.apache.commons.lang3.StringUtils;


/***********
 * @author 彭浩楠
 * @date 2016/5/19
 * @describ
 */
public class SelectionEditText extends EditText {

    public SelectionEditText(Context context) {
        this(context, null);
    }

    public SelectionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && !StringUtils.isBlank(getText())) {
            MyApplication.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    setSelection(getText().length());
                }
            });
        }
    }

}