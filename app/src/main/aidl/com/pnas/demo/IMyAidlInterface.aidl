// IMyAidlInterface.aidl
package com.pnas.demo;

import com.pnas.demo.entity.ipc.AIDLBean;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getStr();

    int getInt();

}
