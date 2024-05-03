package com.uautso.sovs.utils.fileUtils;

public interface FileStorageService {

   // boolean deleteFile(String fileName);

    String fileNameToBase64(String fileName);

    String fileNameToBase64FullPath(String fileName);

    boolean deleteFileFullPath(String fileName);

    Boolean createFolderPath(String string);

    String storeBase64ContentToFile(String base64Content, String extension, String fileName);
}
