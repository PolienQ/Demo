package com.pnas.demo.ui.year;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.MyApplication;
import com.pnas.demo.view.SmartTabLayout.SmartTabLayout;
import com.pnas.demo.view.SmartTabLayout.utils.v4.FragmentPagerItem;
import com.pnas.demo.view.SmartTabLayout.utils.v4.FragmentPagerItemAdapter;
import com.pnas.demo.view.SmartTabLayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

/***********
 * @author pans
 * @date 2016/1/4
 * @describ
 */
public class YearTabActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private ArrayList<String> mData;
    private SmartTabLayout mSmartTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

        initView();
        initData();
    }

    private void initView() {

        mViewPager = ((ViewPager) findViewById(R.id.year_view_pager));

        mSmartTabLayout = ((SmartTabLayout) findViewById(R.id.year_view_pager_tab));

    }

    private void initData() {
        if (mData == null) {
            mData = new ArrayList<>();
        }

        for (int x = 2014; x < 2016; x++) {
            mData.add(x + "");
        }

        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (String str : mData) {
            pages.add(FragmentPagerItem.of(str, DemoFragment.class));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);
        mViewPager.setAdapter(adapter);
//        mViewPager.setCurrentItem(mData.size() - 1);
        mSmartTabLayout.setViewPager(mViewPager);


    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            String str = mData.get(position);

            View view = View.inflate(MyApplication.getInstance(), R.layout.pager_year, null);
            TextView tv = (TextView) view.findViewById(R.id.year_tv);
            tv.setText("第" + str + "个页面");

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

    }
}
