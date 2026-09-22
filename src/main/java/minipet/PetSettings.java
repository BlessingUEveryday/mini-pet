package minipet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

//Load movement settings from config/pet.properties.

public final class PetSettings {
    private static final String DEFAULT_CONFIG_RESOURCE = "/config/pet.properties";

    private static final Path USER_CONFIG_PATH = Path.of(
            System.getProperty("user.home"),
            ".mini-pet",
            "pet.properties"
    );

    private final int speedX;
    private final int speedY;
    private final int startX;
    private final int startY;

    private PetSettings(int speedX, int speedY, int startX, int startY) {
        this.speedX = speedX;
        this.speedY = speedY;
        this.startX = startX;
        this.startY = startY;
    }

    public static PetSettings load() {
        createUserConfigIfMissing();

        Properties properties = new Properties();

        try (InputStream input = Files.newInputStream(USER_CONFIG_PATH)) {
            properties.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read settings file: " + USER_CONFIG_PATH,
                    exception
            );
        }

        int speedX = readPositiveInt(properties, "speed.x");
        int speedY = readPositiveInt(properties, "speed.y");
        int startX = readInt(properties, "start.x");
        int startY = readInt(properties, "start.y");

        return new PetSettings(speedX, speedY, startX, startY);
    }

    private static void createUserConfigIfMissing() {
        if (Files.exists(USER_CONFIG_PATH)) {
            return;
        }

        try {
            Files.createDirectories(USER_CONFIG_PATH.getParent());

            InputStream resource = PetSettings.class.getResourceAsStream(DEFAULT_CONFIG_RESOURCE);

            if (resource == null) {
                throw new IllegalStateException(
                        "Cannot find default resource: "
                        + DEFAULT_CONFIG_RESOURCE
                );
            }

            try (InputStream input = resource) {
                Files.copy (input, USER_CONFIG_PATH);
            }
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot create settings file: "
                    + USER_CONFIG_PATH,
                    exception
            );
        }
    }


    private static int readPositiveInt(Properties properties, String key) {
        int number = readInt(properties, key);

        if (number <= 0) {
            throw new IllegalStateException(key + " must be positive.");
        }

        return number;
    }

    private static int readInt(Properties properties, String key) {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new IllegalStateException("Missing config value:" + key);
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalStateException(
                    key + " must be a whole number.",
                    exception
            );
        }
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getStartX() { return startX; }

    public int getStartY() { return startY; }
}
