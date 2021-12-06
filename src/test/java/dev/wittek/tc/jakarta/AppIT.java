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

@Testcontainers
class AppIT {
    static Network network = Network.newNetwork();

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14")
            .withNetwork(network)
            .withNetworkAliases("postgres");

    @Container
    static GenericContainer<?> jakartaApp = new GenericContainer<>("payara/micro:5.2021.9-jdk11")
            .withExposedPorts(8080)
            .withCopyFileToContainer(MountableFile.forHostPath("target/jakarta-tc.war"), "/opt/payara/deployments/jakarta-tc.war")
            .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
            .withEnv("DB_USER", "test")
            .withEnv("DB_PASSWORD", "test")
            .withEnv("DB_JDBC_URL", "jdbc:postgresql://postgres:5432/test")
            .withNetwork(network)
            .dependsOn(postgres);

    static RequestSpecification requestSpecification;

    @BeforeAll
    static void setup() {
        String baseUri = "http://" + jakartaApp.getHost() + ":" + jakartaApp.getMappedPort(8080) + "/jakarta-tc";
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .build();
    }

    @Test
    void startsUp() {
        given(requestSpecification)
                .when()
                .get("/time")
                .then()
                .statusCode(200);
    }

    @Test
    void canAddBook() {
        given(requestSpecification)
                .when()
                .get("/book/list")
                .then()
                .statusCode(200).body("size()", equalTo(4));

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
                .statusCode(200).body("size()", equalTo(5));
    }

}
