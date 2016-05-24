package com.example.pnas.demo.ui.timer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pnas.demo.R;
import com.example.pnas.demo.base.BaseActivity;
import com.example.pnas.demo.base.MyApplication;
import com.example.pnas.demo.utils.DialogUtils;
import com.example.pnas.demo.utils.ToolUtils;
import com.example.pnas.demo.view.timeselector.ResultHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/***********
 * @author 彭浩楠
 * @date 2016/1/20
 * @describ
 */
public class TimerActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtDate;
    private Button mBtnDate;
    private TextView mTvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initView();
        initData();
        initEvent();

    }

    private void initView() {

        for (int x = 0; x < 3; x++) {
            findViewById(R.id.timer_btn_01 + x).setOnClickListener(this);
        }

        findViewById(R.id.timer_btn_time).setOnClickListener(this);

        mEtDate = ((EditText) findViewById(R.id.timer_et_date));

        mBtnDate = ((Button) findViewById(R.id.timer_btn_date));

        mTvDate = ((TextView) findViewById(R.id.timer_tv_date));

    }

    private void initData() {

    }

    private void initEvent() {
        mBtnDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Date currentDate = new Date(System.currentTimeMillis());
        String format;

        switch (v.getId()) {
            case R.id.timer_btn_time:
                GregorianCalendar gregorianCalendar = new GregorianCalendar(Locale.CHINA);
                gregorianCalendar.set(Calendar.YEAR, 2011);
                Date time = gregorianCalendar.getTime();
                DateFormat dateInstance = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.CANADA);
                showToast(dateInstance.format(time));

                break;
            case R.id.timer_btn_01:
                DateFormat dateInstanceFull = SimpleDateFormat.getDateInstance(DateFormat.FULL, Locale.CANADA);
                DateFormat dateInstanceLong = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.CANADA);
                DateFormat dateInstanceMedium = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA);
                DateFormat dateInstanceShort = SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.CANADA);
                format = dateInstanceFull.format(currentDate);
                log("Full = " + format);
                format = dateInstanceLong.format(currentDate);
                log("Long = " + format);
                format = dateInstanceMedium.format(currentDate);
                log("Medium = " + format);
                format = dateInstanceShort.format(currentDate);
                log("Short = " + format);

                break;
            case R.id.timer_btn_02:
                DateFormat timeInstanceFull = SimpleDateFormat.getTimeInstance(DateFormat.FULL, Locale.CHINA);
                DateFormat timeInstanceLong = SimpleDateFormat.getTimeInstance(DateFormat.LONG, Locale.CHINA);
                DateFormat timeInstanceMedium = SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
                DateFormat timeInstanceShort = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, Locale.CHINA);
                format = timeInstanceFull.format(currentDate);
                log("Full = " + format);
                format = timeInstanceLong.format(currentDate);
                log("Long = " + format);
                format = timeInstanceMedium.format(currentDate);
                log("Medium = " + format);
                format = timeInstanceShort.format(currentDate);
                log("Short = " + format);

                break;
            case R.id.timer_btn_03:
                DateFormat dateTimeInstanceFull = SimpleDateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.CHINA);
                DateFormat dateTimeInstanceLong = SimpleDateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINA);
                DateFormat dateTimeInstanceMedium = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA);
                DateFormat dateTimeInstanceShort = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.CHINA);
                format = dateTimeInstanceFull.format(currentDate);
                log("Full = " + format);
                format = dateTimeInstanceLong.format(currentDate);
                log("Long = " + format);
                format = dateTimeInstanceMedium.format(currentDate);
                log("Medium = " + format);
                format = dateTimeInstanceShort.format(currentDate);
                log("Short = " + format);

                break;

            case R.id.timer_btn_date:

                /*Calendar calendar = Calendar.getInstance(Locale.CHINA);
//                String date = mEtDate.getText().toString();
//                Date parse = ToolUtils.parse(date, "yyyy-MM-dd HH:mm");
                String date = ToolUtils.format(currentDate, "yyyy-MM-dd HH:mm");
                calendar.setTime(currentDate);*/
                GregorianCalendar calendar = new GregorianCalendar(Locale.CHINA);

                log("当前年月日 " + SimpleDateFormat.getDateTimeInstance(
                        DateFormat.SHORT, DateFormat.SHORT, Locale.CANADA).
                        format(calendar.getTime()));

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;   // 月从0开始 0-11
                // 2016-05-20   每周从 周日开始:日 一 二 三 四 五 六
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);   // 当月的日期 20
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);     // 该周的第几天 6=周五
                int dayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH); // 在当月的第几周 3
                int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);     // 该年的第几天 141
                int hour = calendar.get(Calendar.HOUR);                 // 12进制的小时
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);     // 24进制的小时
                int minute = calendar.get(Calendar.MINUTE);
                int am_pm = calendar.get(Calendar.AM_PM);   // AM=0,PM=1

                String text = year + "年" + month + "月" + dayOfMonth + "日" + hourOfDay + "时" + minute + "分";
                mTvDate.setText(text);

                log("dayOfMonth = " + dayOfMonth + " , dayOfWeek = " + dayOfWeek +
                        " , dayOfWeekInMonth = " + dayOfWeekInMonth + " , dayOfYear = " + dayOfYear);

                DialogUtils.showDateDialog(this, new ResultHandler() {
                    @Override
                    public void handle(String time) {
                        showToast(time);
                    }
                }, "2011-01-01 00:00").show();

                break;

        }

    }
}
