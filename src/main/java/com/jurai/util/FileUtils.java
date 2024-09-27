package com.jurai.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String getResourceContent(String resourcePath) throws IOException {
        InputStream inputStream = FileUtils.class.getResource(resourcePath).openStream();

        if (inputStream == null) {
            EventLogger.logError("failed loading resource " + resourcePath);
        }

        // Read the InputStream using BufferedReader
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            return content.toString();
        } catch(Exception e) {
            EventLogger.logError("failed loading resource content: " + resourcePath);
            return "";
        }
    }

}
