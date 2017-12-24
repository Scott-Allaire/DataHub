package org.coder229.datahub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coder229.datahub.model.User;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class AuthenticationTest extends IntegrationTest {

    @Test
    public void login() throws Exception {
        User user = getUser();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(user);
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
