package minipet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

//Load movement settings from config/pet.properties.

public final class PetSettings {
    private static final Path CONFIG_PATH = Path.of("config", "pet.properties");

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

        try (InputStream input = Files.newInputStream(CONFIG_PATH)) {
            properties.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read config/pet.properties.",
                    exception);
        };

        int speedX = readPositiveInt(properties, "speed.x");
        int speedY = readPositiveInt(properties, "speed.y");
        int startX = readInt(properties, "start.x");
        int startY = readInt(properties, "start.y");
        return new PetSettings(speedX, speedY, startX, startY);
    }

    private static int readPositiveInt(Properties properties, String key) {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new IllegalStateException("Missing config value:" + key);
        }

        try {
            int number = Integer.parseInt(value);

            if (number <= 0) {
                throw new IllegalStateException(key + " must be positive");
            }

            return number;
        } catch (NumberFormatException exception) {
            throw new IllegalStateException(key + " must be a whole number.",
                                            exception
            );
        }
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
