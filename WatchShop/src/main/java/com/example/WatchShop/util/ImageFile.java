package com.example.WatchShop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

/**
 * Handle image file class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@Slf4j
public final class ImageFile {
    public static final String PATH_IMAGE = "WatchShop\\src\\main\\resources\\static\\image\\";
    public static final String URL_API_IMAGE = "http://localhost:8080/api/images/";

    private ImageFile() { }

    /**
     * Save the image.
     *
     * @param imageFile need to save
     * @return file name
     */
    public static String saveImageFile(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            return "";
        }

        Path path = Paths.get(PATH_IMAGE);
        System.err.println(path.toAbsolutePath());

        InputStream inputStream = imageFile.getInputStream();
        String extension = getFileExtension(imageFile);
        String filename = randomFileName(extension);
        Files.copy(inputStream, path.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    /**
     * Random file name.
     *
     * @param extension of the file
     * @return file name
     */

    public static String randomFileName(String extension) {
        String randomUUID = UUID.randomUUID().toString().replace("-", "");
        long timeStamp = new Date().getTime();
        return randomUUID + timeStamp + "." + extension;
    }

    /**
     * Get file extension.
     *
     * @param file need to get the file extension
     * @return file name extension
     */
    private static String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return (originalFileName != null && originalFileName.contains("."))
                ? originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
                : "";
    }

    /**
     * Delete file.
     *
     * @param imageName need to delete
     * @return status of delete
     */
    public static boolean deleteImageFile(String imageName) {
        Path path = Paths.get(PATH_IMAGE, imageName);
        try {
            Files.delete(path);
            return true;
        } catch (IOException e) {
            log.error("Error deleting", e);
            return false;
        }
    }
}
