package minipet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * 只负责绘制桌宠外观。
 */
public class PetPanel extends JPanel {
    private final BufferedImage petImage;

    public PetPanel() {
        petImage = PetImageLoader.loadPetImage();
        setOpaque(false);
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
