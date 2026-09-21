package minipet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 只负责绘制桌宠外观。
 */
public class PetPanel extends JPanel {
    private static final Path PET_IMAGE_PATH = Path.of("assets", "isaac-pet.png");

    private final BufferedImage petImage;

    public PetPanel() {
        petImage = loadPetImage();
        setOpaque(false);
    }

    private static BufferedImage loadPetImage() {
        try (InputStream input = Files.newInputStream(PET_IMAGE_PATH)) {
            BufferedImage image = ImageIO.read(input);

            if (image == null) {
                throw new IllegalStateException(
                        "Unsupported image format: " + PET_IMAGE_PATH);
            };

            return image;
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read " + PET_IMAGE_PATH + ".",
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
