package com.example.pnas.demo.ui.area;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.pnas.demo.R;
import com.example.pnas.demo.ui.area.CitycodeUtil;
import com.example.pnas.demo.ui.city.Cityinfo;
import com.example.pnas.demo.ui.city.FileUtil;
import com.example.pnas.demo.view.timeselector.PickerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @项目名: Demo
 * @包名: com.example.pnas.demo.ui.area
 * @创建人: 彭浩楠
 * @描述: TODO
 * @版本号: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新内容: TODO
 */
public class CityPicker extends FrameLayout {

    /**
     * 滑动控件
     */
    private PickerView provincePicker;
    private PickerView cityPicker;
    private PickerView counyPicker;

    /**
     * 选择监听
     */
    private OnSelectingListener onSelectingListener;
    /**
     * 刷新界面
     */
    private static final int REFRESH_VIEW = 0x001;
    /**
     * 临时日期
     */
    private int tempProvinceIndex = -1;
    private int tempCityIndex = -1;
    private int tempCounyIndex = -1;
    private Context context;
    private List<Cityinfo> province_list = new ArrayList<>();
    private HashMap<String, List<Cityinfo>> city_map = new HashMap<>();
    private HashMap<String, List<Cityinfo>> couny_map = new HashMap<>();
    private static ArrayList<String> province_list_code = new ArrayList<>();
    private static ArrayList<String> city_list_code = new ArrayList<>();
    private static ArrayList<String> couny_list_code = new ArrayList<>();

    private CitycodeUtil citycodeUtil;
    private String city_code_string;
    private String city_string;

    public CityPicker(Context context) {
        this(context, null);
    }

    public CityPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CityPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getaddressinfo();
    }

    // 获取城市信息
    private void getaddressinfo() {
        // 读取城市信息string
        JSONParser parser = new JSONParser();
        String area_str = FileUtil.readAssets(context, "area.json");
        province_list = parser.getJSONParserResult(area_str, "area0");
        // citycodeUtil.setProvince_list_code(parser.province_list_code);
        city_map = parser.getJSONParserResultArray(area_str, "area1");
        // System.out.println("city_mapsize" +
        // parser.city_list_code.toString());
        // citycodeUtil.setCity_list_code(parser.city_list_code);
        couny_map = parser.getJSONParserResultArray(area_str, "area2");
        // citycodeUtil.setCouny_list_code(parser.city_list_code);
        // System.out.println("couny_mapsize" +
        // parser.city_list_code.toString());
    }

    public static class JSONParser {

        public List<Cityinfo> getJSONParserResult(String JSONString, String key) {
            List<Cityinfo> list = new ArrayList<Cityinfo>();
            JsonObject result = new JsonParser().parse(JSONString)
                    .getAsJsonObject().getAsJsonObject(key);

            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator.next();
                Cityinfo cityinfo = new Cityinfo();

                cityinfo.setCity_name(entry.getValue().getAsString());
                cityinfo.setId(entry.getKey());
                province_list_code.add(entry.getKey());
                list.add(cityinfo);
            }
            return list;
        }

        public HashMap<String, List<Cityinfo>> getJSONParserResultArray(
                String JSONString, String key) {
            HashMap<String, List<Cityinfo>> hashMap = new HashMap<String, List<Cityinfo>>();
            JsonObject result = new JsonParser().parse(JSONString)
                    .getAsJsonObject().getAsJsonObject(key);

            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator.next();
                List<Cityinfo> list = new ArrayList<Cityinfo>();
                JsonArray array = entry.getValue().getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    Cityinfo cityinfo = new Cityinfo();
                    cityinfo.setCity_name(array.get(i).getAsJsonArray().get(0)
                            .getAsString());
                    cityinfo.setId(array.get(i).getAsJsonArray().get(1)
                            .getAsString());
                    city_list_code.add(array.get(i).getAsJsonArray().get(1)
                            .getAsString());
                    list.add(cityinfo);
                }
                hashMap.put(entry.getKey(), list);
            }
            return hashMap;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.area_code_city_picker, this);
        citycodeUtil = CitycodeUtil.getSingleton();
        // 获取控件引用
        provincePicker = (PickerView) findViewById(R.id.province);

        cityPicker = (PickerView) findViewById(R.id.city);
        counyPicker = (PickerView) findViewById(R.id.couny);
        provincePicker.setData(citycodeUtil.getProvince(province_list));
        provincePicker.setSelected(1);
        cityPicker.setData(citycodeUtil.getCity(city_map, citycodeUtil.getProvince_list_code().get(1)));
        cityPicker.setSelected(1);
        counyPicker.setData(citycodeUtil.getCouny(couny_map, citycodeUtil.getCity_list_code().get(1)));
        counyPicker.setSelected(1);

        setPickerSelectListener();
    }

    private void setPickerSelectListener() {

        provincePicker.setOnSelectListener(new PickerView.OnSelectListener() {

            @Override
            public void onSelect(String text) {
                if (text.isEmpty()) {
                    return;
                }
                tempProvinceIndex = citycodeUtil.province_map_code.get(text);
                // 城市集合
                cityPicker.setData(citycodeUtil.getCity(city_map, citycodeUtil.getProvince_list_code().get(tempProvinceIndex)));
                // 区级集合
                counyPicker.setData(citycodeUtil.getCouny(couny_map, citycodeUtil.getCity_list_code().get(1)));
            }

        });

        cityPicker.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                if (text.isEmpty()) {
                    return;
                }
                tempCityIndex = citycodeUtil.city_map_code.get(text);
                // 区级集合
                counyPicker.setData(citycodeUtil.getCouny(couny_map, citycodeUtil.getCity_list_code().get(tempCityIndex)));
            }
        });

        counyPicker.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                if (text.isEmpty()) {
                    return;
                }
            }
        });

    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_VIEW:
                    if (onSelectingListener != null)
                        onSelectingListener.selected(true);
                    break;
                default:
                    break;
            }
        }

    };

    public interface OnSelectingListener {
        void selected(boolean selected);
    }

    public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
        this.onSelectingListener = onSelectingListener;
    }

}
