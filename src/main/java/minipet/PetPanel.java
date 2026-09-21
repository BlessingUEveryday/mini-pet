package minipet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 只负责绘制桌宠外观。
 */
public class PetPanel extends JPanel {
    private static final String PET_IMAGE_RESOURCE = "/assets/isaac-pet.png";

    private final BufferedImage petImage;

    public PetPanel() {
        petImage = loadPetImage();
        setOpaque(false);
    }

    private static BufferedImage loadPetImage() {
        InputStream resource = PetPanel.class.getResourceAsStream(
                PET_IMAGE_RESOURCE
        );

        if (resource == null) {
            throw new IllegalStateException(
                    "Cannot find resource: "  + PET_IMAGE_RESOURCE
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

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics.create();
        try {
            g.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );

            g.drawImage(petImage, 0, 0, getWidth(), getHeight(), null);
        } finally {
            g.dispose();
        }
    }
}
