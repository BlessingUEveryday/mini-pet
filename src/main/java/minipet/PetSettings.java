package minipet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//Load movement settings from config/pet.properties.

public final class PetSettings {
    private static final String CONFIG_RESOURCE = "/config/pet.properties";

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
        Properties properties = new Properties();

        InputStream resource = PetSettings.class.getResourceAsStream(CONFIG_RESOURCE);

        if (resource == null) {
           throw new IllegalStateException(
                   "Cannot find resources: " + CONFIG_RESOURCE
           );
        }

        try (InputStream input = resource) {
            properties.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read resources: " + CONFIG_RESOURCE,
                    exception
            );
        }

        int speedX = readPositiveInt(properties, "speed.x");
        int speedY = readPositiveInt(properties, "speed.y");
        int startX = readInt(properties, "start.x");
        int startY = readInt(properties, "start.y");
        return new PetSettings(speedX, speedY, startX, startY);
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
