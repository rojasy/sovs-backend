package com.uautso.sovs.utils.fileUtils;


import com.uautso.sovs.utils.userextractor.LoggedUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    private final LoggedUser loggedUser;
    String storageDirectoryPath = System.getProperty("user.dir") + "/uploads/";

    @Override
    public String storeBase64ContentToFile(String base64Content, String extension, String fileName) {

        try {
            Path path = Paths.get(storageDirectoryPath);

            if (!Files.exists(path)) Files.createDirectories(path);

            if (fileName == null)
                fileName = UUID.randomUUID() + "-" + System.currentTimeMillis() + "." + extension;

            File file = new File(storageDirectoryPath + fileName);

            FileOutputStream fos = new FileOutputStream(file);

            byte[] decoder = Base64.getDecoder().decode(base64Content);

            fos.write(decoder);

            logger.info("File name: " + fileName + " Created at " + LocalDateTime.now());

            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

 /*   @Override
    public boolean deleteFile(String fileName) {
        // create object of Path
        Path path = Paths.get(storageDirectoryPath + fileName);

        // delete File
        try {
            Files.delete(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    @Override
    public boolean deleteFileFullPath(String fileName) {
        // create object of Path
        Path path = Paths.get(fileName);

        // delete File
        try {
            Files.delete(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String fileNameToBase64(String fileName) {
        // create object of Path
        Path path = Paths.get(storageDirectoryPath + fileName);
        // delete File
        try {
            logger.info("received is - " + fileName);
            logger.info("The path obtained is - " + path);
            byte[] input_file = Files.readAllBytes(path);
            logger.info("The total bytes is - " + input_file.length);

            byte[] encodedBytes = Base64.getEncoder().encode(input_file);
            logger.info("The total encoded bytes is - " + encodedBytes.length);
            String encodedString = new String(encodedBytes);

            return encodedString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String fileNameToBase64FullPath(String fileName) {
        // create object of Path
        Path path = Paths.get(fileName);
        // delete File
        try {
            byte[] input_file = Files.readAllBytes(path);

            byte[] encodedBytes = Base64.getEncoder().encode(input_file);
            String encodedString = new String(encodedBytes);
            return encodedString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean createFolderPath(String folderPath) {
        try {
            Path path = Paths.get(folderPath);

            Files.createDirectories(path);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
