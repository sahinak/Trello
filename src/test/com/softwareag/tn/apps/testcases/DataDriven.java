package test.com.softwareag.tn.apps.testcases;

import com.google.gson.Gson;
import main.java.com.softwareag.tn.apps.testDefs.ServicePartnerProfile;
import main.java.com.softwareag.tn.apps.utils.CommonUtils;
import main.java.com.softwareag.tn.apps.utils.dataProvider.TestDataProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;
import test.com.softwareag.tn.apps.base.TestBase;
import test.com.softwareag.tn.apps.resources.Gson.partnerProfile.Corporation;
import test.com.softwareag.tn.apps.resources.Gson.partnerProfile.CorporationDetails;
import test.com.softwareag.tn.apps.resources.Gson.partnerProfile.Identity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static main.java.com.softwareag.tn.apps.utils.LoadTestData.*;
import static main.java.com.softwareag.tn.apps.utils.LoadTestData.ID_VALUE;

public class DataDriven extends TestBase implements ITest {
    private Map<String, String> responseFromApi = new HashMap<>();
    private ServicePartnerProfile sPartner = new ServicePartnerProfile();
    private String userName, pwd, payload;
    private String INTERNAL_ID;
    private String testCaseName;
    private boolean validationCheck;

    //GSON construct classes
    CorporationDetails corp_details = new CorporationDetails();
    Identity identities = new Identity();
    Corporation corporation = new Corporation();


    /*@BeforeMethod(groups = "CPP")
    public void issueAnnotationProcessing(Method m) {
        if (m.isAnnotationPresent(Title.class)) {
            Title t = m.getAnnotation(Title.class);
            System.out.println("Test contains issue: " + t.toString());
        }
    }*/

    @BeforeMethod(groups = "CPP")
    public void testCaseRename(Method method, Object[] testData)
    {
        if (testData != null && testData.length > 0)
        {
            HashMap testDataHas = (HashMap) testData[0];

            if (testDataHas.containsKey("DESCRIPTION")){
                this.testCaseName = (String) testDataHas.get("DESCRIPTION") + ":";
            }
            else{
                this.testCaseName = method.getName() + ":";
            }
        }
        else{
            this.testCaseName = method.getName() + ":";
        }
    }

    @Override
    public String getTestName()
    {
        return this.testCaseName;
    }


    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "TestData", groups = "CPP")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Partner Profile")
    //@Title("Create partner profile")
    @Description("Create a partner profile [API - POST: /b2b/partners/]")
    public void createPartnerProfile(HashMap<?, ?> testData) throws IOException {
        userName = CommonUtils.NED_USER;
        pwd = CommonUtils.NED_USER_PWD;

        String TC_TYPE = testData.get("TC_TYPE").toString();
        String CORPORATION_NAME = testData.get("CORPORATION_NAME").toString();
        String ORG_UNIT_NAME= testData.get("ORG_UNIT_NAME").toString();
        String STATUS= testData.get("STATUS").toString();
        String ID_TYPE= testData.get("ID_TYPE").toString();
        String ID_VALUE= testData.get("ID_VALUE").toString();

        System.out.println(TC_TYPE +" > "+ CORPORATION_NAME +" > "+ ORG_UNIT_NAME +" > "+ STATUS +" > "+ ID_TYPE +" > "+ ID_VALUE);

        corp_details.setCorporationName(CORPORATION_NAME);
        corp_details.setOrgUnitName(ORG_UNIT_NAME);
        corp_details.setStatus(STATUS);
        identities.setExternalIdType(ID_TYPE);
        identities.setExternalIdValue(ID_VALUE);
        corporation.setCorporation(corp_details);
        corporation.setIdentity(new Identity[]{identities});

        Gson gson = new Gson();
        payload = gson.toJson(corporation);
        printPayload(Thread.currentThread().getStackTrace()[1].getMethodName(), payload);

        responseFromApi = sPartner.createPartner(userName, pwd, payload);
        printResponse(Thread.currentThread().getStackTrace()[1].getMethodName(), responseFromApi.get("response"));

        if(TC_TYPE.equalsIgnoreCase("Valid_Partner")){
            assertString(responseFromApi.get("status"), "201");                                 // <--- Assertion 1
            JSONObject response_json = new JSONObject(responseFromApi.get("response"));
            INTERNAL_ID = response_json.getString("internalId");
            System.out.println("INTERNAL ID IS: " + INTERNAL_ID);
        }
        if(TC_TYPE.equalsIgnoreCase("Same_Partner")){
            assertString(responseFromApi.get("status"), "400");                                 // <--- Assertion 1
            JSONObject response_json = new JSONObject(responseFromApi.get("response"));
            JSONArray error = response_json.getJSONArray("errors");
            String identifier = error.getJSONObject(0).getString("identifier");
            String message = error.getJSONObject(0).getString("message");
            System.out.println("ERROR IDENTIFIER IS: " + identifier + "\n ERROR MESSAGE: " + message);
            assertString(identifier, "PROF_DIS_NM_NOT_UNIQUE");                                  // <--- Assertion 2
        }
        if (TC_TYPE.equalsIgnoreCase("Same_Ext_Id")){
            assertString(responseFromApi.get("status"), "400");                                 // <--- Assertion 1
            JSONObject response_json = new JSONObject(responseFromApi.get("response"));
            JSONArray error = response_json.getJSONArray("errors");
            String identifier = error.getJSONObject(0).getString("identifier");
            String message = error.getJSONObject(0).getString("message");
            System.out.println("ERROR IDENTIFIER IS: " + identifier + "\n ERROR MESSAGE: " + message);
            assertString(identifier, "GEN_ADD_PROF_ERR");                                       // <--- Assertion 2
        }
    }
}
