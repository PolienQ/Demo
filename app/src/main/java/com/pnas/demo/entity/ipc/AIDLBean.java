package com.pnas.demo.entity.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/***********
 * @author pans
 * @date 2016/9/20
 * @describ
 */
public class AIDLBean implements Parcelable {

    String str;
    int numbet;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.str);
        dest.writeInt(this.numbet);
    }

    public AIDLBean() {
    }

    protected AIDLBean(Parcel in) {
        this.str = in.readString();
        this.numbet = in.readInt();
    }

    public static final Parcelable.Creator<AIDLBean> CREATOR = new Parcelable.Creator<AIDLBean>() {
        @Override
        public AIDLBean createFromParcel(Parcel source) {
            return new AIDLBean(source);
        }

        @Override
        public AIDLBean[] newArray(int size) {
            return new AIDLBean[size];
        }
    };
}
