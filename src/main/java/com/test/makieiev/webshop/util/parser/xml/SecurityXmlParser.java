package com.test.makieiev.webshop.util.parser.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.makieiev.webshop.exception.InvalidParseXmlException;
import com.test.makieiev.webshop.model.security.Security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SecurityXmlParser {

    private final String fileName;

    public SecurityXmlParser(String fileName) {
        this.fileName = fileName;
    }

    public Security getSecurity() {
        try {
            File file = new File(fileName);
            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper(xmlModule);
            return objectMapper.readValue(new FileInputStream(file), Security.class);
        } catch (IOException e) {
            throw new InvalidParseXmlException("Cannot parse file from xml!", e);
        }
    }

}