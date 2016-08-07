package com.pnas.demo.ui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pnas.demo.utils.LogUtil;

/***********
 * @author pans
 * @date 2016/8/3
 * @describ 数据库辅助子类
 */
public class DbHelper extends SQLiteOpenHelper {

    // 库名和版本号
    public static final String DATABASE_NAME = "DbDemo.db";
    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";
    private static final String DATE_TYPE = " date";

    private static final String COMMA_SEP = ",";
    private static final String NOT_NULL = " not null";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DbConstants.StudentEntry.TABLE_NAME + " (" +
                    DbConstants.StudentEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    DbConstants.StudentEntry.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    DbConstants.StudentEntry.COLUMN_NAME_SEX + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    DbConstants.StudentEntry.COLUMN_NAME_AGE + BOOLEAN_TYPE + NOT_NULL + COMMA_SEP +
                    DbConstants.StudentEntry.COLUMN_NAME_HEIGHT + BOOLEAN_TYPE + NOT_NULL + COMMA_SEP +
                    DbConstants.StudentEntry.COLUMN_NAME_BIRTHDAY + DATE_TYPE +
                    " )";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        启用或禁用数据库的预写日志。预写日志不能被用于只读的数据库，因此如果数据是以只读的方式被打开，这个标记值会被忽略。
//          setWriteAheadLoggingEnabled(false);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        // 此回调优先于其他回调.在配置数据连接时会调用这个方法，确保预写日志或外键支持等功能可用
        /* 这个方法只应该调用配置数据库连接参数的方法，
         * 如enableWriteAheadLogging()，setForeignKeyConstraintsEnabled(boolean)
         * ，setLocale(Locale)，setMaximumSize(long)，或者执行PRAGMA语句。*/
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // 在数据库配置完打开后调用;在创建,升级,降级后再次回调
        LogUtil.d("数据库onOpen方法被调用");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         * 这个方法
         * 1、在第一次打开数据库的时候才会走
         * 2、在清除数据之后再次运行-->打开数据库，这个方法会走
         * 3、没有清除数据，不会走这个方法
         * 4、数据库升级的时候这个方法不会走
         */
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
         * 1、第一次创建数据库的时候，这个方法不会走
         * 2、清除数据后再次运行(相当于第一次创建)这个方法不会走
         * 3、数据库已经存在，而且版本升高的时候，这个方法才会调用
         */

        switch (newVersion) {
            case 1:
                // 增加"添加日期,年级"的列
            case 2:
                // 移除"年级"的列
            case 3:
                // 添加"分数"的列
                break;
        }

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 父类默认会在调用该方法的时候抛出异常,需要降级时需复写本方法才能正常降级

        /*
         * 执行数据库的降级操作
         * 1、只有新版本比旧版本低的时候才会执行
         * 2、如果不执行降级操作，会抛出异常
         */
        // 这个方法是在事务中执行的。如果有异常被抛出，所有的改变都会被回滚。

        switch (newVersion) {
            case 0:
                // 移除"添加日期"的列
                break;
            case 1:
                // 添加
                break;
            case 2:

                break;
        }

    }
}
