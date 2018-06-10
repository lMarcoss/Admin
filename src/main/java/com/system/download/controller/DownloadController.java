package com.system.download.controller;

import com.system.directories.entity.File;
import org.apache.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

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


    @PostMapping("/logSies11")
    @Produces({"application/*"})
    public Response downloadFile1(@RequestBody File path) {
        File file = new File(path.getNameFile());

        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition",
                "attachment; filename=" + file.getNameFile());
        return response.build();
    }

    @PostMapping("/logSies")
    public InputStreamResource FileSystemResource(@RequestBody File file1, HttpServletResponse response) throws IOException {
        java.io.File file = new java.io.File(file1.getNameFile());
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file1.getNameFile()));
        return resource;
    }

    // Using ResponseEntity<InputStreamResource>
    @PostMapping("/logSies1")
    public ResponseEntity<InputStreamResource> downloadFile11(@RequestBody File file1) throws IOException {
        java.io.File file = new java.io.File(file1.getNameFile());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.TEXT_PLAIN).contentLength(file.length())
                .body(resource);
    }

    @PostMapping("/warSies")
    public void doDownload(@RequestBody File file1, HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        String fullPath = file1.getNameFile();
        java.io.File downloadFile = new java.io.File(file1.getNameFile());
        FileInputStream inputStream = new FileInputStream(downloadFile);

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
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
        int BUFFER_SIZE = 4096;
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


}
