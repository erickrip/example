package test;

import common.Constants;
import common.Utils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ExamplesTest {
    private RequestSpecification reqSpec;
    private Response respValid;
    @BeforeTest
    public void setUp(){
        reqSpec = Utils.getRequestSpecification();
    }

    @Test(priority = 1)
    public void searchMonsterByID(){
        respValid = given()
                .spec(reqSpec)
                .when()
                .get(Constants.POKEMON_SERVICE + "1");

        respValid.then().statusCode(HttpStatus.SC_OK);

        Assert.assertEquals(respValid.path("name"), "bulbasaur");
    }

    @Test(priority = 2)
    public void searchMonsterByName(){
        respValid = given()
                .spec(reqSpec)
                .when()
                .get(Constants.POKEMON_SERVICE + "charizard");

        respValid.then().statusCode(HttpStatus.SC_OK);

        Assert.assertEquals(respValid.path("name"), "charizard");
        Assert.assertEquals(respValid.path("id"), 6);
    }

    @Test(priority = 2)
    public void searchMonsterByNameNotFound(){
        respValid = given()
                .spec(reqSpec)
                .when()
                .get(Constants.POKEMON_SERVICE + "charizarda");

        respValid.then().log().all().statusCode(HttpStatus.SC_NOT_FOUND);

    }

    @Test(priority = 3)
    public void searchRegion(){
        respValid = given()
                .spec(reqSpec)
                .when()
                .get(Constants.LOCATION_SERVICE + "1");
        respValid.then().statusCode(HttpStatus.SC_OK);

        Assert.assertEquals(respValid.path("name"), "canalave-city");
        Assert.assertEquals(respValid.path("region.name"), "sinnoh");

    }



}
