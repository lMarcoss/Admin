package com.system.directories.service;

import com.system.directories.entity.File;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 17:13
 */
@Service
public class DirectoryServiceImpl implements DirectoryService {
    private static Logger LOG = Logger.getLogger(DirectoryServiceImpl.class);

    @Override
    public List<File> listFilesDirectory(String pathDirectory, String patternFile) throws Exception {
        return listFilesForFolder(pathDirectory, patternFile);
    }

    @Override
    public String getNameLastLogByOrder(String pathDirectory, String patternFile, int order) throws Exception {
        List<File> logFiles = listFilesForFolder(pathDirectory, patternFile);
        File file = logFiles.get(logFiles.size() - (1 + order));
        return file.getNameFile();
    }

    public List<File> listFilesForFolder(String pathDirectory, String patternFile) throws Exception {

        List<File> listFiles = new ArrayList<>();
        try {
            for (Path path : Files.newDirectoryStream(Paths.get(pathDirectory),
                    path -> path.toString().contains(patternFile))) {
                java.io.File file = new java.io.File(String.valueOf(path));
                listFiles.add(new File(file.getName(), file.length()));
            }
            listFiles.sort(Comparator.comparing(File::getNameFile));
            return listFiles;
        } catch (IOException e) {
            LOG.info(ExceptionUtils.getStackTrace(e));
            throw new Exception(e.getMessage());
        }
    }
}
