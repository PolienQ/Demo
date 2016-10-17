package com.pnas.demo.ui.memory;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.base.MyApplication;
import com.pnas.demo.utils.FileUtils;
import com.pnas.demo.utils.SharedPreferencesUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.ButterKnife;

/***********
 * @author pans
 * @date 2016/9/21
 * @describ
 */
public class MemListActivity extends BaseActivity implements Runnable {

    static View.OnClickListener listener;

    @BindView(R.id.mem_list_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.mem_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static ArrayList<FileInfo> mFileList;
    private LinkedList<ArrayList<FileInfo>> mAllFileList;
    private MemAdapter mAdapter;
    private File[] mFiles;
    private Future<?> mFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_list);

        ButterKnife.bind(this);
//        SharedPreferencesUtils.clear();
        init();
        initView();

    }

    private void init() {

        mAllFileList = new LinkedList<>();

        String sizeJson = (String) SharedPreferencesUtils.getParam("MemFileList", "");
        if (TextUtils.isEmpty(sizeJson)) {
            mFileList = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<FileInfo>>() {
            }.getType();
            mFileList = new Gson().fromJson(sizeJson, type);
            printFileInfo();
        }

        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFiles = Environment.getExternalStorageDirectory().getAbsoluteFile().listFiles();
        log("根目录路径 = " + absolutePath + " ,根目录文件个数 = " + mFiles.length);

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAllFileList.add(mFileList);

                mFiles = mFileList.get((Integer) v.getTag()).file.listFiles();
                mSwipeRefreshLayout.setRefreshing(true);
                refreshListData();

            }
        };

    }

    private void initView() {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.red_01, R.color.blue_01, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshListData();
            }

        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MemAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    private void refreshListData() {
        mFuture = MyApplication.getExecutorService().submit(this);
    }

    @Override
    public void run() {
        ArrayList<FileInfo> fileList = new ArrayList<>();
        long size;
        for (File file : mFiles) {
            if (file.isDirectory()) {
                size = getDirSize(file);
                if (size < 52428800) {
                    continue;
                }
                fileList.add(new FileInfo(file, size));
            }
        }
        mFileList = fileList;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAllFileList.size() == 0) {
                    String sizeJson = new Gson().toJson(mFileList);
                    SharedPreferencesUtils.setParam("MemFileList", sizeJson);
                }

                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                showToast("刷新成功");
                printFileInfo();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mAllFileList.size() != 0) {
            mFileList = mAllFileList.pollLast();
            mFiles = mFileList.get(0).file.getParentFile().listFiles();
            mAdapter.notifyDataSetChanged();
            return;
        }
        super.onBackPressed();
    }

    private long getDirSize(File file) {

        long size = 0;
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                size = size + getDirSize(f);
            } else {
                size = size + f.length();
            }
        }
        return size;
    }

    private void printFileInfo() {
        log("**************************************************");
        for (FileInfo info : mFileList) {
            log(info.file.getName() + " \t " + FileUtils.formetFileSize(info.size));
        }
        log("**************************************************");
    }

    static class MemAdapter extends RecyclerView.Adapter<MemViewHolder> {

        @Override
        public MemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MemViewHolder(LayoutInflater.from(MyApplication.getInstance())
                    .inflate(R.layout.item_mem_list, parent, false));
        }

        @Override
        public void onBindViewHolder(MemViewHolder holder, int position) {

            FileInfo fileInfo = mFileList.get(position);

            if (listener != null && mFileList != null) {
                holder.mItemView.setTag(position);
                holder.mItemView.setOnClickListener(listener);
            }
            holder.fileName.setText(fileInfo.file.getName());
            holder.fileSize.setText(FileUtils.formetFileSize(fileInfo.size));

        }

        @Override
        public int getItemCount() {
            return mFileList != null ? mFileList.size() : 0;
        }
    }

    static class MemViewHolder extends RecyclerView.ViewHolder {

        TextView fileName;
        TextView fileSize;
        View mItemView;

        MemViewHolder(View itemView) {
            super(itemView);

            mItemView = itemView;
            fileName = (TextView) itemView.findViewById(R.id.item_mem_name);
            fileSize = (TextView) itemView.findViewById(R.id.item_mem_size);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listener = null;
        mFileList = null;
        try {
            if (mFuture.get() != null) {
                mFuture.cancel(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
