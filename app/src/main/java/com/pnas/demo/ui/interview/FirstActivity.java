package com.pnas.demo.ui.interview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

/***********
 * @author pans
 * @date 2016/8/2
 * @describ
 */
public class FirstActivity extends BaseActivity {

    private ProgressBar mProgressBar;
    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mDBHelper = new DBHelper(this);

        initView();
        initData();

    }

    private void initView() {

        mProgressBar = ((ProgressBar) findViewById(R.id.first_progressBar));
        mProgressBar.setProgress(0);
        mProgressBar.setMax(100);

    }

    private void initData() {

    }

    public void clickProgressBar(View view) {
        final Handler handler = new Handler(Looper.getMainLooper());
        final int stepProgress = mProgressBar.getMax() / 10;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int progress = mProgressBar.getProgress();
                mProgressBar.setProgress(stepProgress + progress);
                if (mProgressBar.getMax() != progress) {
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);

    }

    private class DBHelper extends SQLiteOpenHelper {

        private final static String DATABASE_NAME = "InterviewOpenHelper.db";
        private final static int DATABASE_VERSION = 1;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String sql = "create table diary" +
                    "(" +
                    "_id integer primary key auto increment" +
                    "topic varchar(100)" +
                    "topic varchar(1000)" +
                    ")";
            db.execSQL(sql);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            String sql = "drop table if exists diary";
            db.execSQL(sql);
            onCreate(db);

        }

    }

    public void clickCreateSQLite(View view) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

    }

    public void clickUpdateSQLite(View view) {


    }

    public void clickRemoveSQLite(View view) {

        deleteDatabase(DBHelper.DATABASE_NAME);

    }
}
