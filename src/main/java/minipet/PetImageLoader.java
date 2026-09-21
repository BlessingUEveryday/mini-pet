package minipet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public final class PetImageLoader {
    private static final String PET_IMAGE_RESOURCE = "/assets/isaac-pet.png";

    private PetImageLoader() {
    }

    public static BufferedImage loadPetImage() {
        InputStream resource = PetImageLoader.class.getResourceAsStream(
                PET_IMAGE_RESOURCE
        );

        if (resource == null) {
            throw new IllegalStateException(
                    "Cannot find resource: " + PET_IMAGE_RESOURCE
            );
        }

        try (InputStream input = resource) {
            BufferedImage image = ImageIO.read(input);

            if (image == null) {
                throw new IllegalStateException(
                        "Unsupported image format: " + PET_IMAGE_RESOURCE
                );
            }

            return image;
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read resource: " + PET_IMAGE_RESOURCE,
                    exception
            );
        }
    }
}
