package com.system.directories.controller;

import com.system.configuration.AdminProperties;
import com.system.directories.entity.File;
import com.system.directories.service.DirectoryService;
import com.system.response.CommonResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 17:12
 */
@RestController
@RequestMapping("/directory")
public class DirectoryController {

    private static Logger LOG = Logger.getLogger(DirectoryController.class);
    @Autowired
    private AdminProperties adminProperties;

    @Autowired
    private DirectoryService directoryService;

    @GetMapping("/listFiles")
    public Response listFilesDirectory() {

        try {
            List<File> listFiles = directoryService.listFilesDirectory(adminProperties.getDirectorySiesLog(), adminProperties.getPatternFileSiesLog());
            return CommonResponse.responseSuccess(listFiles);
        } catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            return CommonResponse.responseError(e);
        }
    }
}
