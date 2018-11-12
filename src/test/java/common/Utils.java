package common;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class Utils {
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static RequestSpecification REQUEST_SPEC;

    public static RequestSpecification getRequestSpecification() {
        REQUEST_BUILDER = new RequestSpecBuilder();
        REQUEST_BUILDER.setBaseUri(Constants.BASE_URL);
        REQUEST_SPEC = REQUEST_BUILDER.build();
        return REQUEST_SPEC;
    }

}
