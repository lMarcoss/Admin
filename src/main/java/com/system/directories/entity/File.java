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

    public File() {
    }

    public File(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
}

