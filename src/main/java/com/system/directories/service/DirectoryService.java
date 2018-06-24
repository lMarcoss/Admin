package com.system.directories.service;

import com.system.directories.entity.File;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 17:12
 */
public interface DirectoryService {
    List<File> listFilesDirectory(String pathDirectory, String patternFile) throws Exception;

    String getNameLastLogByOrder(String pathDirectory, String patternFile, int order) throws Exception;
}
