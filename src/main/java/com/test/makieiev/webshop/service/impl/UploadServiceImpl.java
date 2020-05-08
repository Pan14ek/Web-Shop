package com.test.makieiev.webshop.service.impl;

import com.test.makieiev.webshop.service.UploadService;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

public class UploadServiceImpl implements UploadService {

    private static final Logger LOGGER = Logger.getLogger(UploadServiceImpl.class);

    @Override
    public boolean uploadFile(Part filePart, String fileName, String path) {
        checkDirectory(path);
        File file = new File(path, fileName);
        Optional<Part> optionalPart = Optional.ofNullable(filePart);
        if (optionalPart.isPresent()) {
            try (InputStream input = optionalPart.get().getInputStream()) {
                Files.copy(input, file.toPath());
                return true;
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return false;
    }

    private void checkDirectory(String path) {
        File fileSaveDir = new File(path);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
    }

}
