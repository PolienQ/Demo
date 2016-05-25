package com.pnas.demo.ui.photo;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.pnas.demo.R;

/***********
 * @author 彭浩楠
 * @date 2016/5/24
 * @describ
 */
public class SelectPopupWindow extends PopupWindow {

    public SelectPopupWindow(Context context) {
        super(context);
        View inflate = View.inflate(context, R.layout.item_weipan_file_pop, null);
    }
}
