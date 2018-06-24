package com.system.download.controller;

import com.system.configuration.AdminProperties;
import com.system.directories.entity.File;
import com.system.directories.service.DirectoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 20:00
 */
@RestController
@RequestMapping("/download")
public class DownloadController {
    private static Logger LOG = Logger.getLogger(DownloadController.class);

    @Autowired
    private AdminProperties adminProperties;

    @Autowired
    private DirectoryService directoryService;

    @PostMapping("/logSies")
    public InputStreamResource FileSystemResource(@RequestBody File file1, HttpServletResponse response) throws IOException {
        String path = adminProperties.getDirectorySiesLog();
        return resourceLog(path + file1.getNameFile(), response);
    }

    @GetMapping("/lastLogSies")
    public InputStreamResource downloadLastLogSiesByOrder(@RequestParam(value = "order", required = false) String order, HttpServletResponse response) throws Exception {
        String path = adminProperties.getDirectorySiesLog();
        int orderLast;
        if (order == null || order.equals("")) {
            orderLast = 0;
        } else {
            try {
                orderLast = Integer.valueOf(order);
                if (orderLast <= 0) {
                    orderLast = 0;
                }
            } catch (Exception e) {
                orderLast = 0;
            }
        }
        return resourceLastLogByOrder(path, orderLast, response);
    }

    @GetMapping("/logSies/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile11(@PathVariable String fileName) throws IOException {
        String path = adminProperties.getDirectorySiesLog();
        String pathAbsoluteFile = path + fileName;
        java.io.File file = new java.io.File(pathAbsoluteFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.TEXT_PLAIN).contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/logCompile")
    public InputStreamResource logCompile(HttpServletResponse response) throws IOException {
        return resourceLog(adminProperties.getFileLogCompileSies(), response);
    }

    @GetMapping("/warSies")
    public void warSies(HttpServletRequest request, HttpServletResponse response) throws IOException {
        java.io.File file = new java.io.File(adminProperties.getFileWarSies());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s", file.getName()));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    private InputStreamResource resourceLog(String nameFile, HttpServletResponse response) throws FileNotFoundException {
        java.io.File file = new java.io.File(nameFile);
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        return new InputStreamResource(new FileInputStream(nameFile));
    }

    private InputStreamResource resourceLastLogByOrder(String path, int order, HttpServletResponse response) throws Exception {
        String nameFile = directoryService.getNameLastLogByOrder(adminProperties.getDirectorySiesLog(), adminProperties.getPatternFileSiesLog(), order);
        return resourceLog(path + nameFile, response);
    }

}
