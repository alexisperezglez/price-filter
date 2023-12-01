package es.project.pricefilter.cucumberglue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class FilterPriceStepdefsIT extends BaseStepDefs {

    private static final String HOST = "http://localhost:";
    private static final String PRICES_ENDPOINT = "/api/rest/v1/prices";

    private Response response;

    @When("A valid filter request is received with param {}, {} and {}")
    public void aValidFilterRequestIsReceivedWithParamAnd(int brandId, int productId, String applicationDate) {
        Map<String, String> filter = Map.of(
                "brandId", String.valueOf(brandId),
                "productId", String.valueOf(productId),
                "applicationDate", applicationDate
        );

        response = given()
                .contentType("application/json")
                .queryParams(filter)
                .when()
                .get(String.format("%s%d%s", HOST, port, PRICES_ENDPOINT))
                .then()
                .extract()
                .response();
    }

    @Then("A response list with values is returned")
    public void aResponseListWithValuesIsReturned() {
        assertAll(() -> {
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.jsonPath().get("$"));
        });
    }

    @When("An outdated filter request is received")
    public void aOutdatedFilterRequestIsReceived(Map<String, String> req) {
        response = given()
                .contentType("application/json")
                .queryParams(req)
                .when()
                .get(String.format("%s%d%s", HOST, port, PRICES_ENDPOINT))
                .then()
                .extract()
                .response();
    }

    @Then("A {int} status code response is returned")
    public void aStatusCodeResponseIsReturned(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }
}
