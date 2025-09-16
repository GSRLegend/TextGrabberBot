package com.gsrlegend.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class TessdataUtil {
    
    public static String extractTessdata() throws IOException {
        Path tempDir = Files.createTempDirectory("tessdata");
        tempDir.toFile().deleteOnExit();

        try (InputStream input = TessdataUtil.class.getResourceAsStream("/tessdata/eng.traineddata")) {
            if (input == null) {
                throw new FileNotFoundException("eng.traineddata not found in resources!");
            }
            Path outPath = tempDir.resolve("eng.traineddata");
            Files.copy(input, outPath, StandardCopyOption.REPLACE_EXISTING);
        }

        return tempDir.toAbsolutePath().toString();
    }
}
