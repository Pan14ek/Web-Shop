package com.test.makieiev.webshop.service;

import javax.servlet.http.Part;

/**
 * The service is responsible for upload information
 *
 * @author Oleksii_Makieiev1
 */
public interface UploadService {

    /**
     * The method upload information with the special path
     *
     * @param part     is part from request
     * @param fileName is file name
     * @param path     is path for adding file
     * @return true if successfully added file to special directory
     */
    boolean uploadFile(Part part, String fileName, String path);

}