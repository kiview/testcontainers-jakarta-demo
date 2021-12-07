package dev.wittek.tc.jakarta;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

public abstract class AbstractIT {

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

    static {
        jakartaApp.start();

        String baseUri = "http://" + jakartaApp.getHost() + ":" + jakartaApp.getMappedPort(8080) + "/jakarta-tc";
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .build();
    }

}
