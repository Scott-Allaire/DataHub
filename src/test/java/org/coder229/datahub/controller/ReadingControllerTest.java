package org.coder229.datahub.controller;

import org.coder229.datahub.IntegrationTest;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ReadingControllerTest extends IntegrationTest {

    @Test
    public void getReadings() {
        given()
            .port(port)
            .header("Authorization", "Bearer " + getToken())
        .when()
            .get("/reading")
        .then()
            .statusCode(200)
            .assertThat()
            .body("totalElements", greaterThan(0));
    }

    @Test
    public void getReadingsWithStart() {
        given()
            .port(port)
            .header("Authorization", "Bearer " + getToken())
        .when()
            .get("/reading?start=2017-12-12")
        .then()
            .statusCode(200)
            .assertThat()
            .body("totalElements", greaterThan(0));
    }

    @Test
    public void getReadingsWithStartAndEnd() {
        given()
            .port(port)
            .header("Authorization", "Bearer " + getToken())
        .when()
            .get("/reading?start=2017-12-12&end=2018-01-01")
        .then()
            .statusCode(200)
            .assertThat()
            .body("totalElements", greaterThan(0));
    }
}