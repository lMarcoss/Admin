package com.system.download.service;

import org.apache.log4j.Logger;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 20:20
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static Logger LOG = Logger.getLogger(FileStorageServiceImpl.class);

    @Override
    public URI loadFileAsResource(String pathFileName) throws Exception {

        return null;
    }
}
