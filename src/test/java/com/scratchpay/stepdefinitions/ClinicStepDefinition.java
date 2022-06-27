package com.scratchpay.stepdefinitions;

import com.scratchpay.utilies.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class ClinicStepDefinition {

    Response response;
    static String accessToken;
    String baseuri = ConfigurationReader.get("baseurl");


    @Given("I enter email as {string} and password as {string} and request GET method from {string}")
    public void i_enter_email_as_and_password_as_and_request_get_method_from(String email, String password, String endPoint) {
        response = given().contentType(ContentType.JSON).and().accept(ContentType.JSON)
                .and().param("email", email)
                .and().param("password", password)
                .when().get(baseuri + endPoint);

        assertEquals(response.statusCode(), 200);
        assertEquals(response.path("ok"),true);
        assertEquals(response.path("data.session.loggedIn"), true);
        assertEquals(response.path("data.session.role"), "clinic");
        assertEquals(response.path("data.auth"), true);
        ArrayList<String> listOfPermission = response.path("data.permissions");
        assertEquals(listOfPermission.size(), 31);
    }

    @Then("I should be able to get access token")
    public void i_should_be_able_to_get_access_token() {
        JsonPath jsonPath = response.jsonPath();
        accessToken = "Bearer " + jsonPath.getString("data.session.token");
        assertNotNull(jsonPath.getString("data.session.token"));
    }


    @Given("I enter the access token and request GET method with term parameter as {string}")
    public void i_enter_the_access_token_and_request_get_method_with_term_parameter_as(String searchTerm) {
        response = given().contentType(ContentType.JSON).and().accept(ContentType.JSON).and()
                .param("term", searchTerm)
                .header("Authorization", accessToken).and()
                .when().get(baseuri + "/api/clinics");
        response.prettyPrint();
    }

    @Given("I enter the access token and request GET method with {int} as id number")
    public void i_enter_the_access_token_and_request_get_method_with_as_id_number(int id) {
        response = given().header("Authorization", accessToken)
                .and().pathParam("id", id)
                .when().get(baseuri + "/api/clinics/{id}/emails");
        response.prettyPrint();
    }


    @Then("I should get a message about authorization as {string}")
    public void i_should_get_a_message_about_authorization_as(String expectedErrorMessage) {
        String actualErrorMessage = response.path("data.error");
        assertEquals(expectedErrorMessage, actualErrorMessage);
        assertEquals(response.statusCode(), 400);
        assertEquals(response.path("ok"), false);
    }


    @Then("I should get the following list")
    public void i_should_get_the_following_list(List<String> expectedListOfClinics) {
        assertEquals(response.statusCode(), 200);
        assertEquals(response.path("ok"), true);
        List<String> actualListOfClinics = response.path("data.displayName");
        assertEquals(expectedListOfClinics, actualListOfClinics);
    }
}
