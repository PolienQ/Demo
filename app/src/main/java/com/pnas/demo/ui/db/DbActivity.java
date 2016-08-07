package com.pnas.demo.ui.db;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.entity.db.StudentInfo;

/***********
 * @author pans
 * @date 2016/5/26
 * @describ
 */
public class DbActivity extends BaseActivity implements DbAdapter.DbRecyclerViewItemClickListener {

    private RecyclerView mRecyclerView;
    private DbLocalDataSource mDbLocalDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        init();
        initView();
        initData();
        initEvent();
    }

    private void init() {

        mDbLocalDataSource = DbLocalDataSource.getInstance(this);

    }

    private void initView() {

        mRecyclerView = ((RecyclerView) findViewById(R.id.db_recyclerView));

        final int horizontalCount = 5;
        final int dip = pxToDip(2) / 2;
        GridLayoutManager manager = new GridLayoutManager(this, horizontalCount);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);

                // 底部的间距
//                outRect.bottom = dip;
                outRect.set(dip, dip, dip, dip);

                //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
                /*int childLayoutPosition = parent.getChildLayoutPosition(view);
                if (childLayoutPosition % horizontalCount == 0) {
                    outRect.right = dip;
                } else if (childLayoutPosition % horizontalCount - 1 == 0) {
                    // 每行最后一个
                    outRect.right = dip;
                } else {
                    outRect.left = dip;
                    outRect.right = dip;
                }*/
            }
        });

    }

    private void initData() {

        // 添加适配器,添加点击事件
        mRecyclerView.setAdapter(new DbAdapter(this));

    }

    private void initEvent() {

    }

    @Override
    public void onClickItemListener(View view, int position, String data) {
        log("position = " + position + " data = " + data);

        switch (data) {
            case "添加数据":
                mDbLocalDataSource.addStudent(new StudentInfo(), false);
                break;
            case "删除数据":
                mDbLocalDataSource.deleteStudents(false);
                break;
            case "插入数据":

                break;
            case "修改数据":

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteDatabase(DbHelper.DATABASE_NAME);
    }
}
