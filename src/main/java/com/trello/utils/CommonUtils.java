package com.trello.utils;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class CommonUtils extends LoadEnvironment {

    public static String getBaseURI() {
        //return "http://"+IP+":"+HTTP_PORT;
        return "https://"+IP;
    }

   

}

