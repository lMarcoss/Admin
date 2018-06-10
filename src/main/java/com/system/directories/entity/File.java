package com.system.directories.entity;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 17:14
 */
public class File implements Serializable {
    private String nameFile;
    private long size;
    private String typeSize;

    public File() {
    }

    public File(String nameFile, long size) {
        this.nameFile = nameFile;
        determinateTypeSize(size);
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        determinateTypeSize(size);
    }

    public String getTypeSize() {
        return typeSize;
    }

    public void setTypeSize(long typeSize) {
        this.typeSize = determinateTypeSize(typeSize);
    }

    private String determinateTypeSize(long size) {
        long sizeT;
        if (size != 0) {
            if (size / 1024 < 1024) {
                this.typeSize = "K";
                sizeT = size;
            } else {
                this.typeSize = "M";
                sizeT = (int) size / 1024;
            }
            this.size = (int) sizeT / 1024;
        }
        return null;
    }
}

