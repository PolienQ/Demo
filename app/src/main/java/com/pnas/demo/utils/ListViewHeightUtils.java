package com.pnas.demo.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/***********
 * @author pans
 * @date 2015-12-20 15:10
 * @describ 嵌套的ListView高度工具类
 */
public class ListViewHeightUtils {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        setListViewHeightBasedOnChildren(listView, true);
    }


    /**
     * 根据每一个Item的高度设置ListView的高度
     *
     * @param listView
     * @return 所有Item的总高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, boolean isTotalHeight) {
        if (listView == null) {
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 根据条件设置一条item的高度或全部item的高度
        int count;
        if (isTotalHeight) {
            count = listAdapter.getCount();
        } else {
            count = 1;
        }

        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (count - 1));
        listView.setLayoutParams(params);
    }
}
