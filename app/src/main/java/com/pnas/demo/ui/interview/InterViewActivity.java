package com.pnas.demo.ui.interview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.ui.recyclerview.DividerItemDecoration;

/***********
 * @author pans
 * @date 2016/8/2
 * @describ
 */
public class InterviewActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private String[] mData = {"第一条", "第二条", "第三条"};

    private Class[] mActivity = {FirstActivity.class, null, null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        initView();
        initData();

    }

    private void initView() {

        mRecyclerView = ((RecyclerView) findViewById(R.id.interview_recycleView));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

    }

    private void initData() {

        mRecyclerView.setAdapter(new InterviewAdapter());

    }

    private class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder> {

        @Override
        public InterviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new InterviewViewHolder(LayoutInflater.from(InterviewActivity.this).
                    inflate(R.layout.item_interview, parent, false));
        }

        @Override
        public void onBindViewHolder(InterviewViewHolder holder, int position) {
            holder.tv.setText(mData[position]);
            holder.tv.setTextColor(getResources().getColor(R.color.black_01));

            holder.setRootViewClickListener(mActivity[position]);
        }

        @Override
        public int getItemCount() {
            return mData.length;
        }

        class InterviewViewHolder extends RecyclerView.ViewHolder {

            private final View rootView;
            TextView tv;

            /**
             * @param itemView 布局layout
             */
            public InterviewViewHolder(View itemView) {
                super(itemView);
                rootView = itemView;
                tv = (TextView) itemView.findViewById(R.id.item_interview_tv);
            }

            public void setRootViewClickListener(final Class activity) {
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presentController(activity);
                    }
                });
            }

            public void setTvClickListener(final Class activity) {

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presentController(activity);
                    }
                });

            }

        }

    }


}
