package com.pnas.demo.view.timeselector;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.pnas.demo.R;
import com.pnas.demo.utils.LogUtil;
import com.pnas.demo.utils.ScreenUtil;
import com.pnas.demo.utils.ToastUtil;
import com.pnas.demo.utils.ToolUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuli on 2015/11/27.
 */
public class TimeSelector {
    public enum SCROLLTYPE {
        YEAR(1),
        MONTH(2),
        DAY(4),
        HOUR(8),
        MINUTE(16);

        private SCROLLTYPE(int value) {
            this.value = value;
        }

        public int value;

    }

    private int scrollUnits = SCROLLTYPE.YEAR.value + SCROLLTYPE.MONTH.value + SCROLLTYPE.DAY.value + SCROLLTYPE.HOUR.value + SCROLLTYPE.MINUTE.value;
    private ResultHandler handler;
    private Context context;
    private final String FORMAT_STR = "yyyy-MM-dd HH:mm";

    private Dialog seletorDialog;
    private PickerView year_pv;
    private PickerView month_pv;
    private PickerView day_pv;
    private PickerView hour_pv;
    private PickerView minute_pv;

    private final int MAXMINUTE = 59;
    private int MAXHOUR = 23;
    private final int MINMINUTE = 0;
    private int MINHOUR = 0;
    private final int MAXMONTH = 12;

    private ArrayList<String> year, month, day, hour, minute;
    private int startYear, startMonth, startDay, startHour, startMininute, endYear, endMonth, endDay, endHour, endMininute, minute_workStart, minute_workEnd, hour_workStart, hour_workEnd;
    private boolean spanYear, spanMon, spanDay, spanHour, spanMin;
    private Calendar selectedCalender = Calendar.getInstance(); // Handler接收的日期
    private final long ANIMATORDELAY = 200L;
    private final long CHANGEDELAY = 90L;
    private String workStart_str;
    private String workEnd_str;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private Calendar currCalendar;
    private TextView tv_cancel;
    private TextView tv_select;

    private int type; // 1表示时间对话框、2表示日期对话框

    /**
     * @param context
     * @param resultHandler 选取时间后回调
     * @param startDate     format："yyyy-MM-dd HH:mm"
     * @param endDate
     */
    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate) {
        this.context = context;
        this.handler = resultHandler;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        currCalendar = Calendar.getInstance();
        Date parse = ToolUtils.parse(startDate, FORMAT_STR);
        if (parse == null) {
            throw new RuntimeException("startDate 不正确");
        }
        startCalendar.setTime(parse);
        parse = ToolUtils.parse(endDate, FORMAT_STR);
        if (parse == null) {
            throw new RuntimeException("endDate 不正确");
        }
        endCalendar.setTime(parse);
        currCalendar.setTime(new Date(System.currentTimeMillis()));
    }

    /**
     * 构造函数
     *
     * @param context
     * @param resultHandler
     * @param startDate
     * @param endDate
     * @param currDate
     */
    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate, String currDate) {
        this(context, resultHandler, startDate, endDate);
        Date parse = ToolUtils.parse(currDate, "yyyy-MM-dd");
        if (parse == null) {
            throw new RuntimeException("currDate 不正确");
        }
        currCalendar.setTime(parse);
    }

    /**
     * @param context
     * @param startDate
     * @param endDate
     * @param workStartTime 工作日起始时间 如：朝九晚五  format："09:00"
     * @param workEndTime   工作日结束时间  format："17:00"
     */
    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate, String workStartTime, String workEndTime) {
        this(context, resultHandler, startDate, endDate);
        this.workStart_str = workStartTime;
        this.workEnd_str = workEndTime;
    }

    /*********
     * 加载时间对对话框
     */
    public void initTimerDialog() {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, R.style.time_dialog);
            seletorDialog.setCancelable(false);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.dialog_time_selector);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = ScreenUtil.getInstance(context).getScreenWidth();
            window.setAttributes(lp);
        }

        type = 1;
        initTimerView();
    }

    /*********
     * 加载时间对对话框的View
     */
    private void initTimerView() {
        year_pv = (PickerView) seletorDialog.findViewById(R.id.year_pv);
        month_pv = (PickerView) seletorDialog.findViewById(R.id.month_pv);
        day_pv = (PickerView) seletorDialog.findViewById(R.id.day_pv);
        hour_pv = (PickerView) seletorDialog.findViewById(R.id.hour_pv);
        minute_pv = (PickerView) seletorDialog.findViewById(R.id.minute_pv);
        tv_cancel = (TextView) seletorDialog.findViewById(R.id.tv_cancel);
        tv_select = (TextView) seletorDialog.findViewById(R.id.tv_select);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletorDialog.dismiss();
            }
        });

        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(ToolUtils.format(selectedCalender.getTime(), FORMAT_STR));
                seletorDialog.dismiss();
            }
        });

    }


    /*********
     * 加载日期对对话框
     */
    public void initDateDialog() {
        initDateDialog(false);
    }

    /*********
     * 加载日期对对话框
     */
    public void initDateDialog(boolean cancel) {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, R.style.time_dialog);
            seletorDialog.setCancelable(cancel);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.dialog_date_selector);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = ScreenUtil.getInstance(context).getScreenWidth();
            window.setAttributes(lp);
        }
        type = 2;
        initDateView();
    }

    /*********
     * 加载日期对对话框的View
     */
    private void initDateView() {
        year_pv = (PickerView) seletorDialog.findViewById(R.id.year_pv);
        month_pv = (PickerView) seletorDialog.findViewById(R.id.month_pv);
        day_pv = (PickerView) seletorDialog.findViewById(R.id.day_pv);
        tv_cancel = (TextView) seletorDialog.findViewById(R.id.tv_cancel);
        tv_select = (TextView) seletorDialog.findViewById(R.id.tv_select);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletorDialog.dismiss();
            }
        });

        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(ToolUtils.format(selectedCalender.getTime(), FORMAT_STR));
                seletorDialog.dismiss();
            }
        });

    }

    public void show() {
        if (startCalendar.getTime().getTime() >= endCalendar.getTime().getTime()) {
            ToastUtil.showLongToast("起始时间应小于结束时间");
            return;
        }

        if (!excuteWorkTime()) {
            return;
        }

        initParameter();
        initArrayList();
        initTimer();
        loadComponent();
        excuteScroll();
        addListener();
        seletorDialog.show();
    }

    private boolean excuteWorkTime() {

        if (!ToolUtils.isEmpty(workStart_str) && !ToolUtils.isEmpty(workEnd_str)) {
            String[] start = workStart_str.split(":");
            String[] end = workEnd_str.split(":");
            hour_workStart = Integer.parseInt(start[0]);
            minute_workStart = Integer.parseInt(start[1]);
            hour_workEnd = Integer.parseInt(end[0]);
            minute_workEnd = Integer.parseInt(end[1]);
            Calendar workStartCalendar = Calendar.getInstance();
            Calendar workEndCalendar = Calendar.getInstance();
            workStartCalendar.setTime(startCalendar.getTime());
            workEndCalendar.setTime(endCalendar.getTime());
            workStartCalendar.set(Calendar.HOUR_OF_DAY, hour_workStart);
            workStartCalendar.set(Calendar.MINUTE, minute_workStart);
            workEndCalendar.set(Calendar.HOUR_OF_DAY, hour_workEnd);
            workEndCalendar.set(Calendar.MINUTE, minute_workEnd);


            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            Calendar startWorkTime = Calendar.getInstance();
            Calendar endWorkTime = Calendar.getInstance();

            startTime.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY));
            startTime.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE));
            endTime.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY));
            endTime.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE));

            startWorkTime.set(Calendar.HOUR_OF_DAY, workStartCalendar.get(Calendar.HOUR_OF_DAY));
            startWorkTime.set(Calendar.MINUTE, workStartCalendar.get(Calendar.MINUTE));
            endWorkTime.set(Calendar.HOUR_OF_DAY, workEndCalendar.get(Calendar.HOUR_OF_DAY));
            endWorkTime.set(Calendar.MINUTE, workEndCalendar.get(Calendar.MINUTE));

            if (startTime.getTime().getTime() == endTime.getTime().getTime() || (startWorkTime.getTime().getTime() < startTime.getTime().getTime() && endWorkTime.getTime().getTime() < startTime.getTime().getTime())) {
                Toast.makeText(context, "时间参数错误", Toast.LENGTH_LONG).show();
                return false;
            }
            startCalendar.setTime(startCalendar.getTime().getTime() < workStartCalendar.getTime().getTime() ? workStartCalendar.getTime() : startCalendar.getTime());
            endCalendar.setTime(endCalendar.getTime().getTime() > workEndCalendar.getTime().getTime() ? workEndCalendar.getTime() : endCalendar.getTime());
            MINHOUR = workStartCalendar.get(Calendar.HOUR_OF_DAY);
            MAXHOUR = workEndCalendar.get(Calendar.HOUR_OF_DAY);

        }
        return true;

    }

    private void initParameter() {
        startYear = startCalendar.get(Calendar.YEAR);
        startMonth = startCalendar.get(Calendar.MONTH) + 1;
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);

        // 时间
        if (type == 1) {
            startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
            startMininute = startCalendar.get(Calendar.MINUTE);
        }

        endYear = endCalendar.get(Calendar.YEAR);
        endMonth = endCalendar.get(Calendar.MONTH) + 1;
        endDay = endCalendar.get(Calendar.DAY_OF_MONTH);

        if (type == 1) {
            endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
            endMininute = endCalendar.get(Calendar.MINUTE);
        }

        spanYear = startYear != endYear;
        spanMon = (!spanYear) && (startMonth != endMonth);
        spanDay = (!spanMon) && (startDay != endDay);

        if (type == 1) {
            spanHour = (!spanDay) && (startHour != endHour);
            spanMin = (!spanHour) && (startMininute != endMininute);
        }

        selectedCalender.setTime(startCalendar.getTime());
    }

    private void initTimer() {

        if (spanYear) {
            for (int i = startYear; i <= endYear; i++) {
                year.add(String.valueOf(i));
            }
            for (int i = startMonth; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
            for (int i = startHour; i <= MAXHOUR; i++) {
                hour.add(fomatTimeUnit(i));
            }
            for (int i = startMininute; i <= MAXMINUTE; i++) {
                minute.add(fomatTimeUnit(i));
            }
        } else if (spanMon) {
            year.add(String.valueOf(startYear));
            for (int i = startMonth; i <= endMonth; i++) {
                month.add(fomatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
            for (int i = startHour; i <= MAXHOUR; i++) {
                hour.add(fomatTimeUnit(i));
            }
            for (int i = startMininute; i <= MAXMINUTE; i++) {
                minute.add(fomatTimeUnit(i));
            }
        } else if (spanDay) {
            year.add(String.valueOf(startYear));
            month.add(fomatTimeUnit(startMonth));
            for (int i = startDay; i <= endDay; i++) {
                day.add(fomatTimeUnit(i));
            }
            for (int i = startHour; i <= MAXHOUR; i++) {
                hour.add(fomatTimeUnit(i));
            }
            for (int i = startMininute; i <= MAXMINUTE; i++) {
                minute.add(fomatTimeUnit(i));
            }

        } else if (spanHour) {
            year.add(String.valueOf(startYear));
            month.add(fomatTimeUnit(startMonth));
            day.add(fomatTimeUnit(startDay));
            for (int i = startHour; i <= endHour; i++) {
                hour.add(fomatTimeUnit(i));
            }
            for (int i = startMininute; i <= MAXMINUTE; i++) {
                minute.add(fomatTimeUnit(i));
            }

        } else if (spanMin) {
            year.add(String.valueOf(startYear));
            month.add(fomatTimeUnit(startMonth));
            day.add(fomatTimeUnit(startDay));
            hour.add(fomatTimeUnit(startHour));
            for (int i = startMininute; i <= endMininute; i++) {
                minute.add(fomatTimeUnit(i));
            }
        }

    }

    private String fomatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void initArrayList() {
        if (year == null) year = new ArrayList<>();
        if (month == null) month = new ArrayList<>();
        if (day == null) day = new ArrayList<>();
        if (hour == null) hour = new ArrayList<>();
        if (minute == null) minute = new ArrayList<>();
        year.clear();
        month.clear();
        day.clear();
        hour.clear();
        minute.clear();
    }


    private void addListener() {
        year_pv.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
                monthChange();


            }
        });
        month_pv.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
                dayChange();


            }
        });
        day_pv.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));

                if (type == 1) {
                    hourChange();

                }
            }
        });

        if (type == 1) {
            hour_pv.setOnSelectListener(new PickerView.OnSelectListener() {
                @Override
                public void onSelect(String text) {
                    selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
                    minuteChange();


                }
            });
            minute_pv.setOnSelectListener(new PickerView.OnSelectListener() {
                @Override
                public void onSelect(String text) {
                    selectedCalender.set(Calendar.MINUTE, Integer.parseInt(text));


                }
            });
        }
    }

    private void loadComponent() {
        year_pv.setData(year);
        month_pv.setData(month);
        day_pv.setData(day);

        int _year = currCalendar.get(Calendar.YEAR);//获取年份
        int _month = currCalendar.get(Calendar.MONTH) + 1;//获取月份
        int _day = currCalendar.get(Calendar.DAY_OF_MONTH);//获取日
        int _hour = currCalendar.get(Calendar.HOUR_OF_DAY);//小时
        int _minute = currCalendar.get(Calendar.MINUTE);//分

        String text = "当前的初始化的时间为 : " + _year + "年" + _month + "月" +
                _day + "日" + _hour + "时" + _minute + "分";
        LogUtil.d(text);

        selectedCalender.setTime(currCalendar.getTime());
        year_pv.setSelected(_year + ""); // 年
        selectedCalender.set(Calendar.YEAR, _year);
        monthChange(false);
        month_pv.setSelected(_month < 10 ? "0" + _month : "" + _month);

        selectedCalender.set(Calendar.MONTH, _month - 1);
        dayChange(false);
        day_pv.setSelected(_day < 10 ? "0" + _day : "" + _day);
        selectedCalender.set(Calendar.DAY_OF_MONTH, _day);

        if (type == 1) {

            hour_pv.setData(hour);
            minute_pv.setData(minute);

            // 时
            hourChange(false);
            hour_pv.setSelected(_hour < 10 ? "0" + _hour : "" + _hour);

            // 分
            selectedCalender.set(Calendar.HOUR_OF_DAY, _hour);
            minuteChange();
            minute_pv.setSelected(_minute < 10 ? "0" + _minute : _minute + "");
        }
    }

    private void excuteScroll() {
        year_pv.setCanScroll(year.size() > 1 && (scrollUnits & SCROLLTYPE.YEAR.value) == SCROLLTYPE.YEAR.value);
        month_pv.setCanScroll(month.size() > 1 && (scrollUnits & SCROLLTYPE.MONTH.value) == SCROLLTYPE.MONTH.value);
        day_pv.setCanScroll(day.size() > 1 && (scrollUnits & SCROLLTYPE.DAY.value) == SCROLLTYPE.DAY.value);

        // 时间对话框
        if (type == 1) {
            hour_pv.setCanScroll(hour.size() > 1 && (scrollUnits & SCROLLTYPE.HOUR.value) == SCROLLTYPE.HOUR.value);
            minute_pv.setCanScroll(minute.size() > 1 && (scrollUnits & SCROLLTYPE.MINUTE.value) == SCROLLTYPE.MINUTE.value);
        }
    }

    private void monthChange() {
        monthChange(true);
    }

    private void monthChange(boolean isChangeDay) {
        month.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            for (int i = startMonth; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i));
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                month.add(fomatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(month.get(0)) - 1);
        month_pv.setData(month);
        month_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, month_pv);
        if (isChangeDay) {
            month_pv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dayChange();
                }
            }, CHANGEDELAY);
        }
    }

    private void dayChange() {
        dayChange(true);
    }

    private void dayChange(boolean isChangeHour) {
        day.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (int i = startDay; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (int i = 1; i <= endDay; i++) {
                day.add(fomatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day.get(0)));
        day_pv.setData(day);
        day_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, day_pv);
        if (isChangeHour) {
            day_pv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (type == 1) {
                        hourChange();
                    }
                }
            }, CHANGEDELAY);
        }
    }

    private void hourChange() {
        hourChange(true);
    }

    private void hourChange(boolean isMinuteChange) {
        hour.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);

        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
            for (int i = startHour; i <= MAXHOUR; i++) {
                hour.add(fomatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
            for (int i = MINHOUR; i <= endHour; i++) {
                hour.add(fomatTimeUnit(i));
            }
        } else {

            for (int i = MINHOUR; i <= MAXHOUR; i++) {
                hour.add(fomatTimeUnit(i));
            }

        }
        selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour.get(0)));

        hour_pv.setData(hour);
        hour_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, hour_pv);
        if (isMinuteChange) {
            hour_pv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    minuteChange();
                }
            }, CHANGEDELAY);
        }

    }

    private void minuteChange() {
        minute.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
        int selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY);

        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
            for (int i = startMininute; i <= MAXMINUTE; i++) {
                minute.add(fomatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
            for (int i = MINMINUTE; i <= endMininute; i++) {
                minute.add(fomatTimeUnit(i));
            }
        } else if (selectedHour == hour_workStart) {
            for (int i = minute_workStart; i <= MAXMINUTE; i++) {
                minute.add(fomatTimeUnit(i));
            }
        } else if (selectedHour == hour_workEnd) {
            for (int i = MINMINUTE; i <= minute_workEnd; i++) {
                minute.add(fomatTimeUnit(i));
            }
        } else {
            for (int i = MINMINUTE; i <= MAXMINUTE; i++) {
                minute.add(fomatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.MINUTE, Integer.parseInt(minute.get(0)));
        minute_pv.setData(minute);
        minute_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, minute_pv);
        excuteScroll();
    }

    private void excuteAnimator(long ANIMATORDELAY, View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(ANIMATORDELAY).start();
    }

    /**
     * 设置选取时间文本 默认"选择"
     */
    public void setNextBtTip(String str) {
        tv_select.setText(str);
    }

    public int setScrollUnit(SCROLLTYPE... scrolltypes) {
        scrollUnits = 0;
        for (SCROLLTYPE scrolltype : scrolltypes) {
            scrollUnits ^= scrolltype.value;
        }
        return scrollUnits;
    }


}
