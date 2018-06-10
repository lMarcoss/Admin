package com.system.download.service;

import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 20:20
 */
public interface FileStorageService {
    URI loadFileAsResource(String nameFile) throws Exception;
}
