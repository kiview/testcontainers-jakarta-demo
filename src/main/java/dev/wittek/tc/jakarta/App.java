package dev.wittek.tc.jakarta;


import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@DataSourceDefinition(
        name = "java:app/example",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "${ENV=DB_USER}",
        password = "${ENV=DB_PASSWORD}",
        url = "${ENV=DB_JDBC_URL}"
)
@ApplicationPath("")
public class App extends Application {
}
