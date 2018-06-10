package com.system.download.controller;

import com.system.configuration.AdminProperties;
import com.system.directories.entity.File;
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

    @PostMapping("/logSies")
    public InputStreamResource FileSystemResource(@RequestBody File file1, HttpServletResponse response) throws IOException {
        String path = adminProperties.getDirectorySiesLog();
        return resourceLog(path + file1.getNameFile(), response);
    }

    @GetMapping("/logCompile")
    public InputStreamResource logCompile(HttpServletResponse response) throws IOException {
        return resourceLog(adminProperties.getFileLogCompileSies(), response);
    }

    private InputStreamResource resourceLog(String nameFile, HttpServletResponse response) throws FileNotFoundException {
        java.io.File file = new java.io.File(nameFile);
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        return new InputStreamResource(new FileInputStream(nameFile));
    }

    @PostMapping("/logSies1")
    public ResponseEntity<InputStreamResource> downloadFile11(@RequestBody File file1) throws IOException {
        String path = adminProperties.getDirectorySiesLog();
        String pathAbsoluteFile = path + file1.getNameFile();
        java.io.File file = new java.io.File(pathAbsoluteFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.TEXT_PLAIN).contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/warSies")
    public void doDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fullPath = adminProperties.getFileWarSies();
        java.io.File file = new java.io.File(fullPath);
        java.io.File downloadFile = new java.io.File(file.getName());
        FileInputStream inputStream = new FileInputStream(file);

        // get MIME type of the file
        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=%s", file);
        response.setHeader(headerKey, headerValue);
        int BUFFER_SIZE = 160000;
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();

    }

    @GetMapping("/warSies1")
    public void doDownload1(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
}
