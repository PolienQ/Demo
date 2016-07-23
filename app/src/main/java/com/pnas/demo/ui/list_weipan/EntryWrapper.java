package com.pnas.demo.ui.list_weipan;

/***********
 * @author pans
 * @date 2016/5/23
 * @describ
 */
public class EntryWrapper {

    public Entry mEntry;
    public boolean isCheck;

    @Override
    public int hashCode() {
        if (mEntry != null && mEntry.path != null) {
            return mEntry.path.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
//        return super.equals(o);
        if (mEntry != null && o != null && o instanceof Entry) {
            Entry entry = (Entry) o;
            if (entry.path != null) {
                return entry.path.equals(mEntry.path);
            }
        }
        return false;
    }
}
