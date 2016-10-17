package com.pnas.demo.ui.db;

import android.provider.BaseColumns;

/***********
 * @author pans
 * @date 2016/8/3
 * @describ 数据库的常量类
 */
public class DbConstants {

    // 实现内容提供者的基本参数
    public interface StudentEntry extends BaseColumns {

        String TABLE_NAME = "student";
        String COLUMN_NAME_NAME = "name";
        String COLUMN_NAME_SEX = "sex";
        String COLUMN_NAME_AGE = "age";
        String COLUMN_NAME_BIRTHDAY = "birthday";
        String COLUMN_NAME_SCORE = "score";

    }

}
