package com.example.pnas.demo.ui.area;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.pnas.demo.R;
import com.example.pnas.demo.utils.ToolUtils;
import com.example.pnas.demo.view.timeselector.PickerView;

/***********
 * @author 彭浩楠
 * @date 2016/4/23
 * @describ
 */
public class AreaCodePopup extends PopupWindow implements View.OnClickListener {

    private final Context mContext;

    private String mAddressCode;
    private CityPicker mCityPicker;

    public AreaCodePopup(Context context) {
        super(context);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.dialog_area_code, null);
        this.setContentView(inflate);

        inflate.findViewById(R.id.dialog_area_code_cancel).setOnClickListener(this);
        inflate.findViewById(R.id.dialog_area_code_confirm).setOnClickListener(this);

        mCityPicker = ((CityPicker) inflate.findViewById(R.id.dialog_area_code_city_picker));

        this.setBackgroundDrawable(null);
        // 设置动画
        this.setAnimationStyle(R.style.dialog_comment_style);
        // 设置宽高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setOutsideTouchable(true);
        this.setFocusable(true);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dialog_area_code_cancel:
                dismissPopupWindow();
                break;

            case R.id.dialog_area_code_confirm:


                break;
        }
    }

    public void dismissPopupWindow() {
        // 销毁弹出框
        dismiss();
        ToolUtils.setWindowAlpha((Activity) mContext, 1f);
    }
}
