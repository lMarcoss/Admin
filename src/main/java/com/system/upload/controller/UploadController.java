package com.system.upload.controller;

import com.system.configuration.AdminProperties;
import net.lingala.zip4j.core.ZipFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private static final Logger LOG = Logger.getLogger(UploadController.class);

    @Autowired
    private AdminProperties adminProperties;

    private final String EXTENSION_ZIP = ".zip";

    @PostMapping(value = "/zip", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFileZip(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            // file is empty
            return new ResponseEntity("Please select a file to upload", HttpStatus.NO_CONTENT);
        }

        byte[] bytes = new byte[0];
        try {
            String fileName = file.getOriginalFilename();
            bytes = file.getBytes();
            Path path = Paths.get(adminProperties.getDirectoryFileZip() + fileName);
            Files.write(path, bytes);
            unCompressPasswordProtectedFiles(String.valueOf(path));
            return new ResponseEntity("Se ha guardado el archivo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
    }

    private File createFolder(Path path) {
        File file = new File(String.valueOf(path));
        File folder = new File(String.valueOf(path).replace(EXTENSION_ZIP, ""));
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    /**
     * Method for unzipping password protected file
     *
     * @param sourcePath
     * @throws ZipException
     */
    private void unCompressPasswordProtectedFiles(String sourcePath) throws ZipException {
        String destPath = getFileName(sourcePath);
        System.out.println("Destination " + destPath);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(sourcePath);
            // If it is encrypted then provide password
            if (zipFile.isEncrypted()) {
                zipFile.setPassword("pass@123");
            }
            zipFile.extractAll(destPath);
        } catch (net.lingala.zip4j.exception.ZipException e) {
            e.printStackTrace();
        }

    }

    private String getFileName(String filePath) {
        // Get the folder name from the zipped file by removing .zip extension
        return filePath.substring(0, filePath.lastIndexOf("."));
    }


}
