package com.pnas.demo.ui.db;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.MyApplication;

import java.util.ArrayList;
import java.util.List;

/***********
 * @author pans
 * @date 2016/8/5
 * @describ
 */
public class DbAdapter extends RecyclerView.Adapter<DbAdapter.DbViewHolder> implements View.OnClickListener {

    private List<String> mData;
    private DbRecyclerViewItemClickListener mListener;

    public DbAdapter() {
        this(null);
    }

    public DbAdapter(DbRecyclerViewItemClickListener listener) {

        mListener = listener;
        mData = new ArrayList<>();
        mData.add("添加数据");
        mData.add("删除数据");
        mData.add("插入数据");
        mData.add("修改数据");
        mData.add("查询数据");
        mData.add("升序");
        mData.add("总和");
        mData.add("90分以上");
    }

    @Override
    public DbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getInstance())
                .inflate(R.layout.item_recycler_view, parent, false);
        view.setOnClickListener(this);
        return new DbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DbViewHolder holder, int position) {

        holder.tv.setText(mData.get(position));
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {

        if (mListener != null) {
            int position = (int) v.getTag();
            mListener.onClickItemListener(v, position, mData.get(position));
        }

    }

    class DbViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv;

        public DbViewHolder(View itemView) {
            super(itemView);
            tv = ((TextView) itemView.findViewById(R.id.item_recycler_view_tv));
            tv.setTypeface(MyApplication.getTypeface());

        }
    }

    public interface DbRecyclerViewItemClickListener {
        void onClickItemListener(View view, int position, String data);
    }

}
