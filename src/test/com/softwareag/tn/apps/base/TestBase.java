package test.com.softwareag.tn.apps.base;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import main.java.com.softwareag.tn.apps.utils.LoadEnvironment;
import main.java.com.softwareag.tn.apps.utils.LoadTestData;
import org.dom4j.DocumentException;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import java.util.Map;

/**
 * Created by Ranjit on 2/28/2018.
 */

public class TestBase{
    public SoftAssert asert;

    @BeforeSuite(alwaysRun = true)
    public void setup() throws DocumentException {
        LoadEnvironment.loadProperties();
        LoadTestData.loadProperties();
    }

    public void assertString(String actual, String expected) {
        asert = new SoftAssert();
        asert.assertEquals(actual, expected);
        asert.assertAll();
    }

    public void assertInt(int actual, int expected) {
        asert = new SoftAssert();
        asert.assertEquals(actual, expected);
        asert.assertAll();
    }

    public void assertBoolean(boolean actual, boolean expected) {
        asert = new SoftAssert();
        asert.assertEquals(actual, expected);
        asert.assertAll();
    }

    public void printPayload(String name, String payload) {
        System.out.println("********* PAYLOAD FOR METHOD: "+name+" ***********");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(payload);
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
        System.out.println("*********************************************************");
    }

    public void printResponse(String name, String response) {
        System.out.println("********* API RESPONSE FOR METHOD: "+name+" ***********");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response);
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
        System.out.println("*********************************************************");
    }
}
