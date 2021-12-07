package dev.wittek.tc.jakarta;

import dev.wittek.tc.jakarta.book.Book;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class SingletonPatternIT extends AbstractIT {

    @Test
    void startsUp() {
        given(requestSpecification)
                .when()
                .get("/time")
                .then()
                .statusCode(200);
    }

    @Test
    void canQueryTuringAwardWinner() {
        given(requestSpecification)
                .when()
                .get("/turing")
                .then()
                .statusCode(200)
                .body(equalTo("Alfred Aho, Jeffrey Ullman"));
    }

    @Test
    void canAddBook() {
        int numberOfBooks = given(requestSpecification)
                .when()
                .get("/book/list")
                .then()
                .statusCode(200).extract().body().jsonPath().getInt("size()");

        given(requestSpecification)
                .when()
                .header("Content-Type", "application/json")
                .body(new Book(4711L, "foo", "bar"))
                .post("/book/add")
                .then()
                .statusCode(204);

        given(requestSpecification)
                .when()
                .get("/book/list")
                .then()
                .statusCode(200).body("size()", equalTo(numberOfBooks + 1));
    }

}
