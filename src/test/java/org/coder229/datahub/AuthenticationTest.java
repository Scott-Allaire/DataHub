package org.coder229.datahub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {
    @LocalServerPort
    int port;

    @Test
    public void login() {
        String body = "{\"username\":\"admin\",\"password\":\"password\"}";
        given()
                .port(port)
                .body(body)
        .when()
                .post("/login")
        .then()
                .statusCode(200)
        .assertThat()
                .header("Authorization", notNullValue());
    }
}
