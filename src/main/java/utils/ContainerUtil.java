package utils;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class ContainerUtil {
    public static PostgreSQLContainer<?> postgreSQLContainer;

    private ContainerUtil() {
    }

    public static PostgreSQLContainer<?> run() {
        postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres"));
        ConnectionManager.isTest = true;

        return postgreSQLContainer;
    }

    public static void stop() {
        ConnectionManager.isTest = false;
        postgreSQLContainer.stop();
    }
}
