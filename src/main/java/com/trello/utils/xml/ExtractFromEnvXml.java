package com.trello.utils.xml;

import org.dom4j.Node;

import com.trello.utils.LoadEnvironment;

public class ExtractFromEnvXml extends LoadEnvironment {
    // --------- Method to get Setup details -------------------------
    public static String getIP() {
        Node node = env.selectSingleNode("Environment/Setup/IP");
        return node.getText();
    }
   

    

}
