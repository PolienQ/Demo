package com.pnas.demo.ui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pnas.demo.entity.db.StudentInfo;
import com.pnas.demo.ui.db.DbConstants.StudentEntry;

/***********
 * @author pans
 * @date 2016/8/5
 * @describ 数据库访问对象, 负责处理数据库的业务逻辑
 */
public class DbLocalDataSource {

    private static DbLocalDataSource INSTANCE;

    private DbHelper mDbHelp;

    // Prevent direct instantiation.
    private DbLocalDataSource(Context context) {
        mDbHelp = new DbHelper(context);
    }

    public static DbLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DbLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DbLocalDataSource(context);
                }
            }
        }
        return INSTANCE;
    }

    public long addStudent(StudentInfo info, boolean isSql) {

        // 返回-1代表添加失败
        long result = -1;
        SQLiteDatabase db = mDbHelp.getWritableDatabase();

        if (isSql) {
            String sql = "insert into " + StudentEntry.TABLE_NAME + " (" +
                    "name,sex,age,height,birthday) values('王老七','男',51,176,'1990-09-09');";
            db.execSQL(sql);
            result = -2;
        } else {

            ContentValues values = new ContentValues();
            values.put(StudentEntry.COLUMN_NAME_NAME, info.name);
            values.put(StudentEntry.COLUMN_NAME_SEX, info.sex);
            values.put(StudentEntry.COLUMN_NAME_AGE, info.age);
            values.put(StudentEntry.COLUMN_NAME_HEIGHT, info.height);
            values.put(StudentEntry.COLUMN_NAME_BIRTHDAY, info.birthday);

            result = db.insert(StudentEntry.TABLE_NAME, null, values);
        }

        db.close();
        return result;
    }

    public long updateStudent(StudentInfo info, boolean isSql) {

        long result = -1;
        SQLiteDatabase db = mDbHelp.getWritableDatabase();

        if (isSql) {
            String sql = "update " + StudentEntry.TABLE_NAME + " (" +
                    "name,sex,age,height,birthday) values('王老七','男',51,176,'1990-09-09');";
            db.execSQL(sql);
            result = -2;
        } else {

            ContentValues values = new ContentValues();
            values.put(StudentEntry.COLUMN_NAME_AGE, info.age);

            String selection = StudentEntry.COLUMN_NAME_NAME + " LIKE ?";
            String[] selectionArgs = {info.age + ""};

            result = db.update(StudentEntry.TABLE_NAME, values, selection, selectionArgs);
        }

        db.close();

        return result;
    }

    public Long deleteStudents(boolean isSql) {
        long result = -1;
        SQLiteDatabase db = mDbHelp.getWritableDatabase();

        db.beginTransaction();
        try {
            if (isSql) {
                String sql = "delete " + StudentEntry.TABLE_NAME;
                db.execSQL(sql);
                result = -2;
            } else {
                result = db.delete(StudentEntry.TABLE_NAME, null, null);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();

        return result;
    }

    public Long deleteStudent(String name, boolean isSql) {

        long result = -1;
        SQLiteDatabase db = mDbHelp.getWritableDatabase();

        db.beginTransaction();
        try {
            if (isSql) {
                String sql = "delete " + StudentEntry.TABLE_NAME + " " +
                        StudentEntry.COLUMN_NAME_NAME + " LIKE ? where ";
                db.execSQL(sql);
                result = -2;
            } else {
                String selection = StudentEntry.COLUMN_NAME_NAME + " LIKE ?";
                String[] selectionArgs = {name};

                result = db.delete(StudentEntry.TABLE_NAME, selection, selectionArgs);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();

        return result;
    }

    public void execSQL(String sql) {
        SQLiteDatabase db = mDbHelp.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    public StudentInfo getStudent(String name) {

        StudentInfo studentInfo = null;
        SQLiteDatabase db = mDbHelp.getReadableDatabase();

        String[] projection = {
                StudentEntry.COLUMN_NAME_NAME
        };

        String selection = StudentEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = {name};

        Cursor c = db.query(
                StudentEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String sex = c.getString(c.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_SEX));
            int age = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_AGE));
            int height = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_HEIGHT));
            String birthday = c.getString(c.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_BIRTHDAY));
            studentInfo = new StudentInfo(name, sex, age, height, birthday);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        return studentInfo;
    }
}