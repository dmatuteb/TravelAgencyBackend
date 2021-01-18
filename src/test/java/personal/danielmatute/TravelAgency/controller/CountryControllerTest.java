package personal.danielmatute.TravelAgency.controller;

import io.restassured.http.ContentType;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountryControllerTest {

    private static String id;

    public CountryControllerTest() {
        baseURI = "http://127.0.0.1:8085/countries";
    }

    @Test
    @Order(1)
    public void saveCountry() throws JSONException {
        JSONObject country = new JSONObject();
        country.put("name", "Honduras");

        Response postResponse = given()
                .contentType(ContentType.JSON)
                .body(country.toString())
                .when()
                .post("/")
                .then()
                .extract()
                .response();

        postResponse.prettyPrint();

        String jsonPostResponse = postResponse
                .getBody()
                .asString();

        id = new JSONObject(jsonPostResponse)
                .get("id")
                .toString();

        postResponse
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("name"))
                .body("$", hasKey("_links"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @Order(2)
    public void saveCountryWithoutName(String countryName) throws JSONException {
        JSONObject country = new JSONObject();
        country.put("name", countryName);

        Response postResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(country.toString())
                        .when()
                        .post("/")
                        .then()
                        .extract()
                        .response();

        postResponse.prettyPrint();

        postResponse
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("$", hasItems(notNullValue()));
    }

    @Test
    @Order(3)
    public void saveCountryNameWithMoreThanAllowedCharacterLength() throws JSONException {
        final int length = 51;

        String name = RandomStringUtils.random(length, true, false);

        JSONObject country = new JSONObject();
        country.put("name", name);

        Response postResponse = given()
                .contentType(ContentType.JSON)
                .body(country.toString())
                .when()
                .post("/")
                .then()
                .extract()
                .response();

        postResponse.prettyPrint();

        postResponse
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("$", hasItems(notNullValue()));
    }

    @Test
    @Order(4)
    public void getCountry() {
        Response getResponse = given()
                .pathParam("id", id)
                .when()
                .get("/{id}")
                .then()
                .extract()
                .response();

        getResponse.prettyPrint();

        getResponse
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("name"))
                .body("$", hasKey("_links"));
    }

    @Test
    @Order(5)
    public void editCountry() throws JSONException {
        String newName = "New name";

        JSONObject country = new JSONObject();
        country.put("name", newName);

        Response putResponse = given()
                .contentType(ContentType.JSON)
                .body(country.toString())
                .pathParam("id", id)
                .when()
                .put("/{id}")
                .then()
                .extract()
                .response();

        putResponse.prettyPrint();

        putResponse
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("name"))
                .body("$", hasKey("_links"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @Order(6)
    public void editCountryWithoutName(String countryName) throws JSONException {
        JSONObject country = new JSONObject();
        country.put("name", countryName);

        Response putResponse = given()
                .contentType(ContentType.JSON)
                .body(country.toString())
                .pathParam("id", id)
                .when()
                .put("/{id}")
                .then()
                .extract()
                .response();

        putResponse.prettyPrint();

        putResponse
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("$", hasItems(notNullValue()));
    }

    @Test
    @Order(7)
    public void getCountries() {
        Response getResponse = when()
                .get("/")
                .then()
                .extract()
                .response();

        getResponse.prettyPrint();

        getResponse
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("_embedded", hasKey("countryList"))
                .body("$", hasKey("_links"));
    }

    @Test
    @Order(8)
    public void deleteCountry() {
        Response deleteResponse = given()
                .pathParam("id", id)
                .when()
                .delete("/{id}")
                .then()
                .extract()
                .response();

        deleteResponse
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @Order(9)
    public void getNotExistentCountry() {
        Response getResponse = given()
                .pathParam("id", id)
                .when()
                .get("/{id}")
                .then()
                .extract()
                .response();

        getResponse.prettyPrint();

        getResponse
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


}