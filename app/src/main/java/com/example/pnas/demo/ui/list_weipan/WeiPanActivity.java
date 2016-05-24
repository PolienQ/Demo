package com.example.pnas.demo.ui.list_weipan;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pnas.demo.R;
import com.example.pnas.demo.utils.DialogUtils;
import com.example.pnas.demo.utils.ToastUtil;
import com.example.pnas.demo.view.dialog.BaseConfirmDialog;
import com.example.pnas.demo.view.dialog.ConfirmDialogBtnClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/***********
 * @author 彭浩楠
 * @date 2016/5/21
 * @describ
 */
public class WeiPanActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private List<Entry> mData;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ActionBar mActionBar;
    private PopupWindow mPopupWindow;
    private Entry mSelectEntry;
    private WeiPanAdapter mWeiPanAdapter;
    private boolean isEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_pan);

        initToolBar();
        initView();
        initData();
        initEvent();

    }

    private void initToolBar() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {

            mActionBar.setTitle("新浪网盘");

            // 隐藏 后退 按钮
            mActionBar.setDisplayHomeAsUpEnabled(false);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weipan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.weipan_icon_upload:

                break;

            case R.id.weipan_icon_download:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        mSwipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.weipan_head_swipe_refresh));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView = ((ListView) findViewById(R.id.weipan_list_view));

    }

    private void initData() {

        if (mData == null) {
            mData = new ArrayList<>();
        }

        for (int x = 1; x < 20; x++) {
            Entry entry = new Entry();
            entry.path = "/aaa/bbb/ccc/文件名" + x;
            entry.size = x * (new Random().nextInt(231) + 1) + "";
            mData.add(entry);
        }


        mWeiPanAdapter = new WeiPanAdapter();
        mListView.setAdapter(mWeiPanAdapter);

    }

    private void initEvent() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entry entry = mData.get(position);
                if (isEditMode) {
                    updateItemView(view, entry);
                } else {
                    if (entry.isDir) {
                        ToastUtil.showShortToast("正在打开 : " + entry.path);
                    }
                }
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                startEditMode();
                return false;

            }
        });

    }

    private void updateItemView(View view, Entry entry) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_checkbox);
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
            entry.isCheck = false;
        } else {
            checkBox.setChecked(true);
            entry.isCheck = true;
        }

    }

    private void startEditMode() {
        isEditMode = true;
        mWeiPanAdapter.notifyDataSetChanged();

    }

    private void stopEditMode() {
        isEditMode = false;
        mWeiPanAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (isEditMode) {
            stopEditMode();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mData.clear();
                        for (int x = 1; x < 20; x++) {
                            Entry entry = new Entry();
                            entry.path = "/aaa/bbb/ccc/文件名" + x;
                            entry.size = x * (new Random().nextInt(231) + 1) + "";
                            mData.add(entry);
                        }
                        mWeiPanAdapter.notifyDataSetChanged();

                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private class WeiPanAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData == null ? null : mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.item_weipan_file, null);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tvSize = (TextView) convertView.findViewById(R.id.tv_size);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.ivOption = (ImageView) convertView.findViewById(R.id.iv_option);
                holder.cbCheck = (CheckBox) convertView.findViewById(R.id.cb_checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Entry entry = mData.get(position);

            if (isEditMode) {
                holder.ivOption.setVisibility(View.INVISIBLE);
                holder.cbCheck.setVisibility(View.VISIBLE);
            } else {
                holder.ivOption.setVisibility(View.VISIBLE);
                holder.cbCheck.setVisibility(View.GONE);
                holder.cbCheck.setChecked(false);
            }

            holder.tvName.setText(entry.fileName());
            holder.tvSize.setText(entry.size);
//            Date date = RESTUtility.parseDate(entry.modified);
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
//            DateFormat format = DateFormat.getDateInstance();
//            DateFormat format = DateFormat.getTimeInstance();
//            DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();
            holder.tvTime.setText(format.format(date));

            // 图标
//            if (entry.isDir) {
            holder.ivIcon.setImageResource(R.mipmap.directory_icon);
//            holder.tvSize.setVisibility(View.INVISIBLE);
            /*} else {
                holder.tvSize.setVisibility(View.VISIBLE);
                // 根据文件的后缀获取类型和图标ID
                Object[] mimeType = Utils.getMIMEType(entry.fileName());
                // mimeType[0] 是文件的类型
                // mimeType[1] 是文件类型的图标ID
                holder.ivIcon.setImageResource((Integer) mimeType[1]);
            }*/

            holder.ivOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mSelectEntry = entry;

                    showPopupWindow(v);
                }
            });

            return convertView;
        }

        private class ViewHolder {

            public TextView tvName;
            public TextView tvTime;
            public TextView tvSize;

            public ImageView ivIcon;
            public ImageView ivOption;

            public CheckBox cbCheck;
        }
    }

    private void showPopupWindow(View v) {

        // 获取Item
        View itemView = (View) v.getParent();
        if (mPopupWindow == null) {
            View contentView = View.inflate(WeiPanActivity.this, R.layout.item_weipan_file_pop, null);
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = itemView.getHeight(); // 获取控件的容器高度
            mPopupWindow = new PopupWindow(contentView, width, height, true);

            // 点击PopupWindow其他区域隐藏PopupWindow
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), ""));
            mPopupWindow.setOutsideTouchable(true);

            contentView.findViewById(R.id.ll_move).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            // 删除
            contentView.findViewById(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(WeiPanActivity.this, mSelectEntry.path, Toast.LENGTH_SHORT).show();

                    mData.remove(mSelectEntry);
                    mWeiPanAdapter.notifyDataSetChanged();
                    mPopupWindow.dismiss();

//                    CloudEngine.getInstance().deleteFileData(mSelectEntry.path, WeiPanActivity.this);

                }
            });

            // 重命名
            contentView.findViewById(R.id.ll_rename).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final BaseConfirmDialog dialog = DialogUtils.showEditDialog(WeiPanActivity.this, "重命名");
                    dialog.setListener(new ConfirmDialogBtnClickListener() {
                        @Override
                        public void onOKBtnClick() {
                            String etAddName = dialog.getEtAddName();

                            mSelectEntry.path = mSelectEntry.path.replace(mSelectEntry.fileName(), etAddName);

                            mPopupWindow.dismiss();

                        }

                        @Override
                        public void onCancelBtnClick() {

                        }
                    });

                }
            });

        }

        if (isShowBottom(itemView)) {
            // 显示在控件的上方
            mPopupWindow.showAsDropDown(itemView, 0, -2 * itemView.getHeight());
        } else {
            // 显示在控件的下方
            mPopupWindow.showAsDropDown(itemView);
        }

    }

    private boolean isShowBottom(View itemView) {

        // 获取屏幕的高度
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        // 获取itemView的高度
        int itemViewHeight = itemView.getHeight();
        // itemView距离屏幕顶端的高度
        int[] location = new int[2];
        itemView.getLocationOnScreen(location);
        int itemViewY = location[1];

        // 屏幕的高度 - 控件的高度 - 控件到顶部的距离 = 控件底部到屏幕底部的距离
        int distance = screenHeight - itemViewHeight - itemViewY;

        // 如果底部的高度小于控件的高度 返回 true
        return distance < itemViewHeight;
    }
}
