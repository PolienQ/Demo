package com.pnas.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.pnas.demo.view.dialog.BaseConfirmDialog;
import com.pnas.demo.view.dialog.ConfirmDialogBtnClickListener;
import com.pnas.demo.view.timeselector.ResultHandler;
import com.pnas.demo.view.timeselector.TimeSelector;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by 1 on 15/12/22.
 */
public class DialogUtils {

    /**
     * *********
     * 显示时间对话框
     * "2015-12-29 00:00", "2016-12-29 23:59"
     *
     * @param context
     * @param startDate
     * @param endDate
     * @return
     */
    public static TimeSelector showTimerDialog(Context context, ResultHandler handler
            , String startDate, String endDate) {

        TimeSelector dialog = new TimeSelector(context, handler, startDate, endDate);

        dialog.initTimerDialog();

        dialog.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR, TimeSelector.SCROLLTYPE.MONTH
                , TimeSelector.SCROLLTYPE.DAY, TimeSelector.SCROLLTYPE.HOUR
                , TimeSelector.SCROLLTYPE.MINUTE);

        return dialog;
    }

    public static TimeSelector showDateDialog(Activity activity, ResultHandler handler, String startDate) {
//        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date(System.currentTimeMillis()));
        GregorianCalendar calendar = new GregorianCalendar(Locale.CHINA);
        String date = SimpleDateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.SHORT, Locale.CANADA).
                format(calendar.getTime());
        return showDateDialog(activity, handler, startDate, date);
    }

    /**
     * *********
     * 显示日期对话框
     * "2015-01-01 00:00", "2015-12-31 23:59"  // 给定选择值 的区间
     * "1988-01-01 00:00", "2015-12-31 23:59"
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static TimeSelector showDateDialog(Activity activity, ResultHandler handler, String startDate, String endDate) {

        TimeSelector dialog = new TimeSelector(activity, handler, startDate, endDate);

        dialog.initDateDialog();
        dialog.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR, TimeSelector.SCROLLTYPE.MONTH
                , TimeSelector.SCROLLTYPE.DAY);

        return dialog;
    }

    /**
     * @param handler
     * @param startDate
     * @param endDate
     * @param defaultDate yyyy-MM-dd
     * @return
     */
    public static TimeSelector showDateDialog(Activity activity, ResultHandler handler, String startDate, String endDate, String defaultDate) {
        TimeSelector dialog;
        if (StringUtils.isNotBlank(defaultDate)) {
            dialog = new TimeSelector(activity, handler, startDate, endDate, defaultDate);
        } else {
            dialog = new TimeSelector(activity, handler, startDate, endDate);
        }

        dialog.initDateDialog();
        dialog.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR, TimeSelector.SCROLLTYPE.MONTH, TimeSelector.SCROLLTYPE.DAY);

        return dialog;
    }


    /**
     * *********
     * 显示提示对话框
     *
     * @param context
     * @param title   标题
     * @param msg     内容
     * @return
     */
    public static BaseConfirmDialog showNoticeDailog(Context context, String title, String msg) {

        BaseConfirmDialog dialog = new BaseConfirmDialog(context);

        dialog.show(title, msg);

        return dialog;
    }

    /**
     * *********
     * 显示提示对话框
     *
     * @param context
     * @param title   标题
     * @param msg     内容
     * @return
     */
    public static BaseConfirmDialog showNoticeDailog(Context context, String title, String msg, ConfirmDialogBtnClickListener listener) {

        BaseConfirmDialog dialog = null;

        if (null == dialog) {
            dialog = new BaseConfirmDialog(context, listener);
        }
        dialog.show(title, msg);

        return dialog;
    }

    public static BaseConfirmDialog showEditDialog(Context context, String title) {

        BaseConfirmDialog dialog = new BaseConfirmDialog(context) {
            @Override
            protected void init() {
                super.init();
                etAddName.setVisibility(View.VISIBLE);
                llContent.setVisibility(View.GONE);
            }
        };
        dialog.show(title, "");

        return dialog;
    }

}

