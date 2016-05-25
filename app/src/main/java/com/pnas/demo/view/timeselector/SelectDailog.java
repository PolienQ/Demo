package com.pnas.demo.view.timeselector;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.pnas.demo.R;
import com.pnas.demo.utils.ScreenUtil;

import java.util.List;

/**
 * Created by 1 on 15/12/22.
 */
public class SelectDailog {

    private Dialog seletorDialog;
    private Context context;
    private List<String> datas;
    private ResultHandler handler;

    private int position;
    private PickerView selectView;
    public SelectDailog(Context context,ResultHandler handler, List<String> datas){
        this.context = context;
        this.datas = datas;
        this.handler = handler;

        initDialog();
    }

    private void initDialog(){
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, R.style.time_dialog);
            seletorDialog.setCancelable(false);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.dialog_selector);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(context).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }

        selectView = (PickerView)seletorDialog.findViewById(R.id.selectView);

        TextView textView = (TextView) seletorDialog.findViewById(R.id.tv_cancel);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletorDialog.dismiss();
            }
        });

        textView = (TextView) seletorDialog.findViewById(R.id.tv_select);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler.handle(datas.get(position));
                seletorDialog.dismiss();
            }
        });
    }


    public void show(){

        selectView.setOnSelectListener(new PickerView.OnSelectListener(){
            @Override
            public void onSelect(String text) {

                position = datas.indexOf(text);
            }
        });

        selectView.setData(datas);
        selectView.setSelected(0);
        selectView.setCanScroll(true);

        seletorDialog.show();
    }
}
