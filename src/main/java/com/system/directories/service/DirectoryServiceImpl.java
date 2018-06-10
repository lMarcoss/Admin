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
    private static String EXTENSION_FILE = ".log";

    @Override
    public List<File> listFilesDirectory(String pathDirectory) throws Exception {
        return listFilesForFolder(pathDirectory);
    }

    public List<File> listFilesForFolder(String pathDirectory) throws Exception {

        List<File> listFiles = new ArrayList<>();

        //Files.newDirectoryStream(Paths.get(path)).forEach(System.out::println);
        //Files.list(Paths.get(path)).filter(Files::isRegularFile).forEach(System.out::println);

        try {
            for (Path path : Files.newDirectoryStream(Paths.get(pathDirectory),
                    path -> path.toString().endsWith(EXTENSION_FILE))) {
                listFiles.add(new File(path.toString()));
            }
            return listFiles;
        } catch (IOException e) {
            LOG.info(ExceptionUtils.getStackTrace(e));
            throw new Exception(e.getMessage());
        }
    }

    /*
    *
    *
    * List fileNamesList = new ArrayList();
      Files.newDirectoryStream(Paths.get(dir),
      path -> path.toString().endsWith(".java")).forEach(filePath -> fileNamesList.add(filePath.toString()));
    * */
}
