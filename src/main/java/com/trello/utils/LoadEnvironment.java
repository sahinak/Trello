package com.trello.utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.trello.utils.xml.ExtractFromEnvXml;

public class LoadEnvironment{

    public static Document env = null;
    public static String USER_KEY, USER_TOKEN;
    public static String IP;
    
   

    public static void loadProperties() throws DocumentException {

        File envFile = new File(FileReference.propertyFilePath+File.separator+"Environment.xml");
        SAXReader reader = new SAXReader();
        env = reader.read(envFile);

        IP = ExtractFromEnvXml.getIP();
    
    }
}
