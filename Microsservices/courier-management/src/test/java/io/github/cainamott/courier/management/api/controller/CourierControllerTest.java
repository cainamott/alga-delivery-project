package io.github.cainamott.courier.management.api.controller;

import io.github.cainamott.courier.management.domain.model.Courier;
import io.github.cainamott.courier.management.domain.repository.CourierRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourierControllerTest {

    private CourierRepository courierRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/couriers";
    }

    @Test
    void shouldReturn201(){

        String requestBody = """ 
                {
                    "name":"João Lucas",
                    "phone": "11943943957" 
                }
                """;

        RestAssured.given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("João Lucas"))
                .body("phone", Matchers.equalTo("11943943957"));

    }

    @Test
    void shouldReturn200(){
        UUID courierId = courierRepository.saveAndFlush(
                Courier.brandNew(
                        "Mariana Dias",
                        "51999999900"
                )
        ).getId();

        RestAssured.given()
                .pathParam("id", courierId)
                .accept(ContentType.JSON)
                .when()
                .get("/{courierId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Mariana Dias"));
    }
}