package com.system.upload.controller;

import com.system.configuration.AdminProperties;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
            unzipFile(path);
            return new ResponseEntity("Se ha guardado el archivo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
    }

    private void unzipFile(Path path) throws Exception {
        File nameFileZip = new File(String.valueOf(path));
        if (!nameFileZip.getName().endsWith(EXTENSION_ZIP)) {
            return;
        }
        byte[] buffer = new byte[1024];
        try {
            File folder = createFolder(path);
            //get the zip file content
            ZipInputStream zis;
            zis = new ZipInputStream(new FileInputStream(String.valueOf(path)));

            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(folder + File.separator + fileName);

                LOG.info("file unzip : " + newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
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
     * This method is used to unzip a password protected zip file.
     *
     * @param sourceZipFile  of type String indicating the source zip file
     * @param destinationDir of type String indicating the directory where
     *                       the zip file will be extracted.
     * @param password       of type String indicating the password.
     */
    /*
    public static void unzipDirWithPassword(final String sourceZipFile, final String destinationDir, final String password) {
        RandomAccessFile randomAccessFile = null;//ISevenZipInArchive


        ISevenZipInArchive inArchive = null;
        try {
            randomAccessFile = new RandomAccessFile(sourceZipFile, "r");
            inArchive = SevenZip.openInArchive(null, // autodetect archive type
                    new RandomAccessFileInStream(randomAccessFile));

// Getting simple interface of the archive inArchive
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

            for (final ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {

                final int[] hash = new int[]{0};
                if (!item.isFolder()) {
                    ExtractOperationResult result;
                    result = item.extractSlow(new ISequentialOutStream() {
                        public int write(final byte[] data) throws SevenZipException {
                            try {
                                if (item.getPath().indexOf(File.separator) > 0) {
                                    String path = destinationDir + File.separator + item.getPath().substring(0, item.getPath().lastIndexOf(File.separator));

                                    File folderExisting = new File(path);
                                    if (!folderExisting.exists())
                                        new File(path).mkdirs();
                                }
                                OutputStream out = new FileOutputStream(destinationDir + File.separator + item.getPath());
                                out.write(data);
                                out.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            hash[0] |= Arrays.hashCode(data);
                            return data.length; // Return amount of proceed data
                        }
                    }, password); /// password.
                    if (result == ExtractOperationResult.OK) {
                        System.out.println(String.format("%9X | %s",
                                hash[0], item.getPath()));
                    } else {
                        System.err.println("Error extracting item: " + result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inArchive != null) {
                try {
                    inArchive.close();
                } catch (SevenZipException e) {
                    System.err.println("Error closing archive: " + e);
                    e.printStackTrace();
                }
            }
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    System.err.println("Error closing file: " + e);
                    e.printStackTrace();
                }
            }
        }
    }
*/
}
