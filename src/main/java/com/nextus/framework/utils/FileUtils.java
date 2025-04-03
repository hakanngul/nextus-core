package com.nextus.framework.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * FileUtils provides helper methods for basic file operations.
 */
public final class FileUtils {

    private FileUtils() {
        // Utility class
    }

    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }

    public static void copyFile(String sourcePath, String targetPath) throws IOException {
        Files.copy(Path.of(sourcePath), Path.of(targetPath), StandardCopyOption.REPLACE_EXISTING);
    }

    public static String readFileAsString(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }

    public static void writeToFile(String filePath, String content) throws IOException {
        Files.writeString(Path.of(filePath), content);
    }

    public static void createDirectoryIfNotExists(String dirPath) throws IOException {
        Path path = Path.of(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}