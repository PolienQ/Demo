package com.pnas.demo.ui.memory;

import java.io.File;
import java.io.Serializable;

/***********
 * @author pans
 * @date 2016/9/23
 * @describ
 */
class FileInfo implements Serializable {

    public FileInfo(File file, long size) {
        this.file = file;
        this.size = size;
    }

    public File file;
    public long size;

}
