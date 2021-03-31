package test.com.softwareag.tn.apps.testcases;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.gson.Gson;
import main.java.com.softwareag.tn.apps.testDefs.ServicePartnerProfile;
import main.java.com.softwareag.tn.apps.utils.CommonUtils;
import main.java.com.softwareag.tn.apps.utils.JSON_Schema_ValidationUtils;
import main.java.com.softwareag.tn.apps.utils.dataProvider.TestDataProvider;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.*;
import ru.yandex.qatools.allure.model.SeverityLevel;
import test.com.softwareag.tn.apps.base.TestBase;
import test.com.softwareag.tn.apps.resources.Gson.partnerProfile.Corporation;
import test.com.softwareag.tn.apps.resources.Gson.partnerProfile.CorporationDetails;
import test.com.softwareag.tn.apps.resources.Gson.partnerProfile.Identity;

import static main.java.com.softwareag.tn.apps.utils.LoadTestData.*;

public class PartnerProfile extends TestBase {
    private Map<String, String> responseFromApi = new HashMap<>();
    private ServicePartnerProfile sPartner = new ServicePartnerProfile();
    private String userName, pwd, payload;
    private String INTERNAL_ID;
    private boolean validationCheck;

    //GSON construct classes
    CorporationDetails corp_details = new CorporationDetails();
    Identity identities = new Identity();
    Corporation corporation = new Corporation();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Features("Partner Profile")
    @Title("Create partner profile")
    @Description("Create a partner profile [API - POST: /b2b/partners/]")
    public void createPartnerProfile() throws IOException {
        userName = CommonUtils.NED_USER;
        pwd = CommonUtils.NED_USER_PWD;

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

        assertString(responseFromApi.get("status"), "201");                                 // <--- Assertion 1
        JSONObject response_json = new JSONObject(responseFromApi.get("response"));
        INTERNAL_ID = response_json.getString("internalId");
        System.out.println("INTERNAL ID IS: " + INTERNAL_ID);
    }

    //@Test(dependsOnMethods = {"createPartnerProfile"})
    @Severity(SeverityLevel.CRITICAL)
    @Features("Partner Profile")
    @Title("Create Partner Profile With Same Display Name")
    @Description("Should not be able to create partner profile with same display name [API - POST: /b2b/partners/]")
    public void createPartnerProfileWithSameDisplayName() throws IOException {
        int flag = 0;
        userName = CommonUtils.NED_USER;
        pwd = CommonUtils.NED_USER_PWD;

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

        assertString(responseFromApi.get("status"), "400");                                 // <--- Assertion 1
        JSONObject response_json = new JSONObject(responseFromApi.get("response"));
        JSONArray error = response_json.getJSONArray("errors");
        String identifier = error.getJSONObject(0).getString("identifier");
        String message = error.getJSONObject(0).getString("message");
        System.out.println("ERROR IDENTIFIER IS: " + identifier + "\n ERROR MESSAGE: " + message);
        assertString(identifier, "GEN_ADD_PROF_ERR");                                  // <--- Assertion 2
    }

    //@Test(dependsOnMethods = {"createPartnerProfile"})
    @Severity(SeverityLevel.CRITICAL)
    @Features("Partner Profile")
    @Title("Create Partner Profile With Same External ID")
    @Description("Should not be able to create partner profile with same external ID [API - POST: /b2b/partners/]")
    public void createPartnerProfileWithSameExtID() throws IOException {
        int flag = 0;
        userName = CommonUtils.NED_USER;
        pwd = CommonUtils.NED_USER_PWD;

        corp_details.setCorporationName(CORPORATION_NAME + "_Updated");
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

        assertString(responseFromApi.get("status"), "400");                                 // <--- Assertion 1
        JSONObject response_json = new JSONObject(responseFromApi.get("response"));
        JSONArray error = response_json.getJSONArray("errors");
        String identifier = error.getJSONObject(0).getString("identifier");
        String message = error.getJSONObject(0).getString("message");
        System.out.println("ERROR IDENTIFIER IS: " + identifier + "\n ERROR MESSAGE: " + message);
        assertString(identifier, "GEN_ADD_PROF_ERR");                                       // <--- Assertion 2
    }

    //@Test(dependsOnMethods = {"createPartnerProfile"})
    @Severity(SeverityLevel.BLOCKER)
    @Features("Partner Profile")
    @Title("Get All Partner profiles")
    @Description("Get All Partner profile [API - GET: /b2b/partners/]")
    public void get_All_PartnerProfile() throws IOException, ProcessingException {
        userName = CommonUtils.NED_USER;
        pwd = CommonUtils.NED_USER_PWD;
        int flag = 0;
        responseFromApi = sPartner.get_All_Partner(userName, pwd);
        //printResponse(Thread.currentThread().getStackTrace()[1].getMethodName(), responseFromApi.get("response"));

        assertString(responseFromApi.get("status"), "200");                                 // <--- Assertion 1
        JSONObject response_json = new JSONObject(responseFromApi.get("response"));
        String rj= response_json.toString();

        File schemaFile = new File(System.getProperty("user.dir")+"/src/test/com/softwareag/tn/apps/resources/jsonSchema/getPartnerSchema.json");
        String sf = FileUtils.readFileToString(schemaFile);
        validationCheck= JSON_Schema_ValidationUtils.isJsonValid(sf, rj);
        System.out.println("VALID JSON:: "+validationCheck);
        assertBoolean(validationCheck, true);                                               // <--- Assertion 2

        boolean isPresent= JSON_Schema_ValidationUtils.isStringPresent(rj, CORPORATION_NAME);
        assertBoolean(isPresent, true);                                                     // <--- Assertion 3

        JSONArray partnerProfiles = response_json.getJSONArray("partnerProfiles");
        for (int i = 0; i < partnerProfiles.length(); i++) {
            JSONObject corporation = (JSONObject) partnerProfiles.getJSONObject(i).get("corporation");
            String corporationName = corporation.getString("corporationName");
            System.out.println("CorporationNames: " + corporationName);
            if (corporationName.equals(CORPORATION_NAME)) {
                flag = 1;
                break;
            }
        }
        if (flag == 0)
            assertBoolean(true, false);                                               // <--- Assertion 4
        else
            assertBoolean(true, true);
    }

    //@Test(dependsOnMethods = {"get_All_PartnerProfile"})
    @Severity(SeverityLevel.CRITICAL)
    @Features("Partner Profile")
    @Title("Get ExternalIDs for the created the partner profile")
    @Description("Get External ID [API - GET: /b2b/partners/{INTERNAL_ID}/identities]")
    public void get_Identities_For_PartnerProfile() throws IOException {
        userName = CommonUtils.NED_USER;
        pwd = CommonUtils.NED_USER_PWD;
        int flag = 0;
        responseFromApi = sPartner.get_Identity(userName, pwd, INTERNAL_ID);
        printResponse(Thread.currentThread().getStackTrace()[1].getMethodName(), responseFromApi.get("response"));

        assertString(responseFromApi.get("status"), "200");                                 // <--- Assertion 1
        JSONObject response_json = new JSONObject(responseFromApi.get("response"));
        JSONArray identities = response_json.getJSONArray("identities");
        String externalIdValue = identities.getJSONObject(0).getString("externalIdValue");
        //System.out.println("externalIdValue: " + externalIdValue);
        assertString(externalIdValue, ID_VALUE);                                                    // <--- Assertion 2
    }

    //@Test(dependsOnMethods = {"get_Identities_For_PartnerProfile"})
    @Severity(SeverityLevel.CRITICAL)
    @Features("Partner Profile")
    @Title("Get Corporation Details for the created the partner profile")
    @Description("Get ECorporation Details [API - GET: /b2b/partners/{INTERNAL_ID}/corporation]")
    public void get_Corporation_Details_For_PartnerProfile() throws IOException {
        userName = CommonUtils.NED_USER;
        pwd = CommonUtils.NED_USER_PWD;
        int flag = 0;
        responseFromApi = sPartner.get_Corporation(userName, pwd, INTERNAL_ID);
        printResponse(Thread.currentThread().getStackTrace()[1].getMethodName(), responseFromApi.get("response"));

        assertString(responseFromApi.get("status"), "200");                                 // <--- Assertion 1
        JSONObject response_json = new JSONObject(responseFromApi.get("response"));
        String corporationName = response_json.getString("corporationName");
        System.out.println("corporationName: " + corporationName);
        assertString(corporationName, CORPORATION_NAME);                                            // <--- Assertion 2
    }

    //@Test(dependsOnMethods = {"get_Corporation_Details_For_PartnerProfile"})
    @Severity(SeverityLevel.CRITICAL)
    @Features("Partner Profile")
    @Title("Delete Created Partner Profile")
    @Description("Delete Partner Profile [API - DELETE: /b2b/partners/{INTERNAL_ID}]")
    public void delete_PartnerProfile() throws IOException {
        userName = CommonUtils.NED_USER;
        pwd = CommonUtils.NED_USER_PWD;
        int flag = 0;
        responseFromApi = sPartner.delete_Corporation(userName, pwd, INTERNAL_ID);
        printResponse(Thread.currentThread().getStackTrace()[1].getMethodName(), responseFromApi.get("response"));
        assertString(responseFromApi.get("status"), "200");                                 // <--- Assertion 1
    }

    ////@Test(dataProviderClass = TestDataProvider.class, dataProvider = "TestData")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Partner Profile")
    @Title("Create partner profile")
    @Description("Create a partner profile [API - POST: /b2b/partners/]")
    public void testExcel(HashMap<?, ?> testData) throws IOException {
        userName = CommonUtils.NED_USER;
        pwd = CommonUtils.NED_USER_PWD;
        String CORP = testData.get("CORPORATION_NAME").toString();
        System.out.println(CORP);
    }
}