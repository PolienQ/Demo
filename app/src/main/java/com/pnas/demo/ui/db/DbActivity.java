package com.pnas.demo.ui.db;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.entity.db.StudentInfo;

import java.util.ArrayList;
import java.util.Random;

/***********
 * @author pans
 * @date 2016/5/26
 * @describ
 */
public class DbActivity extends BaseActivity implements DbAdapter.DbRecyclerViewItemClickListener {

    private RecyclerView mRecyclerView;
    private DbLocalDataSource mDbLocalDataSource;
    private TextView mTextView;

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

        mDbLocalDataSource = DbLocalDataSource.getInstance();

    }

    private void initView() {

        mRecyclerView = ((RecyclerView) findViewById(R.id.db_recyclerView));
        mTextView = ((TextView) findViewById(R.id.db_result));

        final int horizontalCount = 8;
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
        long result;
        switch (data) {
            case "添加数据":
                result = mDbLocalDataSource.addStudent(getStudentInfo(), false);
                if (result != -1) {
                    log("添加了" + 1 + "条数据");
                }
                break;
            case "删除数据":
//                result = mDbLocalDataSource.deleteStudents(false);
                result = mDbLocalDataSource.deleteStudent("张1", false);
                showToast(result == 0 ? "删除失败" : "删除" + result + "条数据");
                break;

            case "修改数据":
                StudentInfo info = getStudentInfo();
                result = mDbLocalDataSource.updateStudent(info, false);
                showToast("更新了" + result + "条数据");

                break;
            case "查询数据":
                printInfo(mDbLocalDataSource.getStudent());
                break;
            case "查询张1":
                printInfo(mDbLocalDataSource.getStudent(null,
                        DbConstants.StudentEntry.COLUMN_NAME_NAME + " LIKE ?", new String[]{"张1"},
                        null, null, null));
                break;
            case "升序":
                printInfo(mDbLocalDataSource.getStudent(null, null, null,
                        null, null, DbConstants.StudentEntry.COLUMN_NAME_SCORE /*+ " DESC"*/));

                break;
            case "总和":

                break;
            case "90分以上":

                break;
        }
    }

    private StudentInfo getStudentInfo() {
        return new StudentInfo("张" + new Random().nextInt(5), "男", 23, new Random().nextInt(20) + 80, "1945-05-09");
    }

    private void printInfo(ArrayList<StudentInfo> student) {

        if (student == null || student.size() == 0) {
            showToast("无数据");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (StudentInfo studentInfo : student) {
            sb.append(studentInfo.toString());
            sb.append("\r\n");
        }
        mTextView.setText(sb.toString());
        showToast(student.size() + "条数据");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteDatabase(DbHelper.DATABASE_NAME);
    }
}
