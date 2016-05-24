package com.example.pnas.demo.ui.list_weipan;

import java.util.List;

/***********
 * @author 彭浩楠
 * @date 2016/5/21
 * @describ
 */
public class Entry {

    /**
     * Size of the file.
     */
    public long bytes;

    /**
     * If a directory, the hash is its "current version". If the hash
     * changes between calls, then one of the directory's immediate children
     * has changed.
     */
    public String hash;

    /**
     * Name of the icon to display for this entry.
     */
    public String icon;

    /**
     * True if this entry is a directory, or false if it's a file.
     */
    public boolean isDir;

    /**
     * Last modified date, in "EEE, dd MMM yyyy kk:mm:ss ZZZZZ" form (see
     * {@code RESTUtility#parseDate(String)} for parsing this value.
     */
    public String modified;

    /**
     * For a file, this is the modification time set by the client when the
     * file was added to VDisk. Since this time is not verified (the VDisk
     * server stores whatever the client sends up) this should only be used
     * for display purposes (such as sorting) and not, for example, to
     * determine if a file has changed or not.
     * <p/>
     * <p>
     * This is not set for folders.
     * </p>
     */
    public String clientMtime;

    /**
     * Path to the file from the root.
     */
    public String path;

    /**
     * Name of the root, usually either "VDisk" or "app_folder".
     */
    public String root;

    /**
     * Human-readable (and localized, if possible) description of the file
     * size.
     */
    public String size;

    /**
     * The file's MIME type.
     */
    public String mimeType;

    /**
     * The file's md5.
     */
    public String md5;

    /**
     * The file's sha1.
     */
    public String sha1;

    public String revision;

    public String thumb;

    /**
     * Full unique ID for this file's revision. This is a string, and not
     * equivalent to the old revision integer.
     */
    public String rev;

    /**
     * Whether a thumbnail for this is available.
     */
    public boolean thumbExists;

    /**
     * Whether this entry has been deleted but not removed from the metadata
     * yet. Most likely you'll only want to show entries with isDeleted ==
     * false.
     */
    public boolean isDeleted;

    /**
     * A list of immediate children if this is a directory.
     */
    public List<Entry> contents;
    public boolean isCheck;

    /**
     * Returns the file name if this is a file (the part after the last
     * slash in the path).
     */
    public String fileName() {
        int ind = path.lastIndexOf('/');
        return path.substring(ind + 1, path.length());
    }

    /**
     * Returns the path of the parent directory if this is a file.
     */
    public String parentPath() {
        if (path.equals("/")) {
            return "";
        } else {
            int ind = path.lastIndexOf('/');
            return path.substring(0, ind + 1);
        }
    }

}
