package com.example.pnas.demo.ui.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pnas.demo.R;
import com.example.pnas.demo.base.BaseActivity;
import com.example.pnas.demo.base.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initView();
        initData();
        initEvent();
    }


    private void initView() {

        mRecyclerView = ((RecyclerView) findViewById(R.id.recyclerview_recycler_view));

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // 设置RecyclerView的方向
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //添加分割线
        mRecyclerView.addItemDecoration(new ItemDivider(this, R.drawable.divider_bg));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void initData() {
        if (mData == null) {
            mData = new ArrayList<>();
        }

        for (int i = 'A'; i <= 'z'; i++) {
            mData.add("" + (char) i);
        }

        //设置adapter
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);


    }

    private void initEvent() {

    }

    /**
     * RecyclerView的适配器
     * 继承Adapter时,泛型为ViewHolder类型,在 onBindViewHolder 方法中可直接获取Holder设置UI
     */
    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 返回下面设置好Item的ViewHolder
            return new RecyclerViewHolder(LayoutInflater.from(MyApplication.getInstance())
                    .inflate(R.layout.item_recycler_view, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            // 设置Item的数据
            holder.tv.setText(mData.get(position));
            holder.tv.setTextColor(Color.BLACK);

        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        /**
         * 设置Item
         */
        class RecyclerViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public RecyclerViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.item_recycler_view_tv);
            }

        }
    }

    private class ItemDivider extends RecyclerView.ItemDecoration {

        private final Drawable mDivider;

        public ItemDivider(Context context, int resId) {
            //在这里我们传入作为Divider的Drawable对象
            mDivider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            super.onDraw(c, parent, state);
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int x = 0; x < childCount; x++) {
                View child = parent.getChildAt(x);
                // 获取该item布局属性
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                // 获取item的bottom的值,该值为分割线的y轴绘制开始点
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);

            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());

        }
    }
}
