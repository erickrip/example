package test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import common.Constants;
import common.Utils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import common.ExtentProperties;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ExamplesTest {
    private RequestSpecification reqSpec;
    private Response respValid;
    protected static ExtentProperties exProp;
    private static String extentReportFile;

    @BeforeSuite
    public void beforeSuite() {
        extentReportFile = Constants.HTML_REPORT;
        exProp = new ExtentProperties();
        // Create object of extent report and specify the report file path.
        exProp.setExtent(new ExtentReports(extentReportFile, true));

        exProp.getExtent().loadConfig(new File(Constants.EXTENT_CONFIG));

    }


    @BeforeTest
    public void setUp(){
        reqSpec = Utils.getRequestSpecification();
    }

    @Test(priority = 1)
    public void searchMonsterByID(){
        startTestCase("Searh a monster by ID", "Search the pokemon with ID 1");
        respValid = given()
                .spec(reqSpec)
                .when()
                .get(Constants.POKEMON_SERVICE + "1");

        respValid.then().statusCode(HttpStatus.SC_OK);

        Assert.assertEquals(respValid.path("name"), "bulbasaur");
        logInfo("Pokemon name: " + respValid.path("name"));
        logPassStep("Status code: " + HttpStatus.SC_OK);
    }

    @Test(priority = 2)
    public void searchMonsterByName(){
        startTestCase("Search a monster by name", "Recover a pokemon with the name Charizard");
        respValid = given()
                .spec(reqSpec)
                .when()
                .get(Constants.POKEMON_SERVICE + "charizard");

        respValid.then().statusCode(HttpStatus.SC_OK);

        Assert.assertEquals(respValid.path("name"), "charizard");
        Assert.assertEquals(respValid.path("id"), 6);

        logInfo("Pokemon name: " + respValid.path("name"));
        logInfo("Pokemon ID: " + respValid.path("id"));
        logPassStep("Test Passed");
    }

    @Test(priority = 2)
    public void searchMonsterByNameNotFound(){
        startTestCase("Search a monster that not exist", "Generate a GET 404 (resource not found)");
        respValid = given()
                .spec(reqSpec)
                .when()
                .get(Constants.POKEMON_SERVICE + "charizarda");

        respValid.then().log().all().statusCode(HttpStatus.SC_NOT_FOUND);

        logPassStep(respValid.prettyPrint());
    }

    @Test(priority = 3)
    public void searchRegion(){
        startTestCase("Search a city region", "Search a city");
        respValid = given()
                .spec(reqSpec)
                .when()
                .get(Constants.LOCATION_SERVICE + "1");
        respValid.then().statusCode(HttpStatus.SC_OK);

        Assert.assertEquals(respValid.path("name"), "canalave-city");
        Assert.assertEquals(respValid.path("region.name"), "sinnoh");

        logInfo("City name: " + respValid.path("name"));
        logInfo("Region name: " + respValid.path("region.name"));
        logPassStep("Test passed");

    }


    @AfterMethod
    public void getResult(ITestResult result){
        switch (result.getStatus()){
            case ITestResult.FAILURE:
                logTestFail(result);
                logFailStep(result.getThrowable().toString());
                break;
            case ITestResult.SUCCESS:
                logTestPass(result);
                break;
            case ITestResult.SKIP:
                logTestSkip(result);
                break;
        }
    }

    @AfterSuite
    public void tearDown(){
        exProp.getExtentTest().log(LogStatus.INFO, "Execution Completed!");
        // close report.
        exProp.getExtent().endTest(exProp.getExtentTest());
        // writing everything to document.
        exProp.getExtent().flush();
    }

    public void startTestCase(String testName, String desc){
        exProp.setExtentTest(exProp.getExtent().startTest(testName, desc));
    }

    private void logTestPass(ITestResult result){
        exProp.getExtentTest().log(LogStatus.PASS, result.getName() + " Passed");
    }

    private void logTestFail(ITestResult result){
        exProp.getExtentTest().log(LogStatus.FAIL, result.getName() + " Failed" );
    }

    private void logTestSkip(ITestResult result){
        exProp.getExtentTest().log(LogStatus.SKIP, result.getName() + " Skipped");
    }

    public void logInfo(String details){
        exProp.getExtentTest().log(LogStatus.INFO, details );
    }

    public void logPassStep(String details){
        exProp.getExtentTest().log(LogStatus.PASS, details);
    }

    public void logFailStep(String details) { exProp.getExtentTest().log(LogStatus.FAIL, details); }


}
