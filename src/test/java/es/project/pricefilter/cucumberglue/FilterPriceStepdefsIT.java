package es.project.pricefilter.cucumberglue;

import es.project.pricefilter.infrastructure.adapter.api.dto.input.FilterInput;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class FilterPriceStepdefsIT extends BaseStepDefs {

    private Response response;

    @When("A valid filter request is received with param {}, {} and {}")
    public void aValidFilterRequestIsReceivedWithParamAnd(int brandId, int productId, String applicationDate) {
        DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
        FilterInput filter = new FilterInput();
        filter.setBrandId(brandId);
        filter.setProductId(productId);
        filter.setApplicationDate(LocalDateTime.parse(applicationDate, dtf));

        response = given()
                .contentType("application/json")
                .body(filter)
                .when()
                .post("http://localhost:" + port + "/api/rest/v1/price-filter")
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
    public void aOutdatedFilterRequestIsReceived(DocString jsonReq) {
        response = given()
                .contentType("application/json")
                .body(jsonReq.getContent())
                .when()
                .post("http://localhost:" + port + "/api/rest/v1/price-filter")
                .then()
                .extract()
                .response();
    }

    @Then("A {int} status code response is returned")
    public void aStatusCodeResponseIsReturned(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }
}
