package org.coder229.datahub;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.coder229.datahub.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AuthenticationTest extends IntegrationTest {

    @Value("${app.security.secret}")
    private String secret;

    @Test
    public void login() throws Exception {
        User user = getUser();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(user);
        ExtractableResponse<Response> response = given()
                .port(port)
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract();

        String authorization = response.header("Authorization");
        assertThat(authorization, notNullValue());
        String[] parts = authorization.split(" ");
        assertThat(parts[0], equalTo("Bearer"));

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(parts[1]);
        assertThat(claims, notNullValue());
        assertThat(claims.getBody().getSubject(), equalTo("admin"));
        assertThat(claims.getBody().getExpiration(), notNullValue());
    }

}
