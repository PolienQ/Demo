package com.pnas.demo.ui.expandable;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***********
 * @author pans
 * @date 2016/3/18
 * @describ
 */
public class ExpandableActivity extends BaseActivity {

    private ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        initView();
        initData();

    }

    private void initView() {
        mExpandableListView = ((ExpandableListView) findViewById(R.id.expandable_list_view));
    }

    private void initData() {

        HashMap<Integer, List<String>> hashMap = new HashMap<>();

        for (int x = 0; x < 10; x++) {
            List<String> list = new ArrayList<>();
            for (int y = 1; y < 11; y++) {
                list.add("child + " + y);
            }
            hashMap.put(x, list);
        }

        mExpandableListView.setAdapter(new ExpandableAdapter(hashMap));
    }

}
