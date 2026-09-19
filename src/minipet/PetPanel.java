package minipet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * 只负责绘制桌宠外观。
 */
public class PetPanel extends JPanel {
    private final PetState state;

    public PetPanel(PetState state) {
        this.state = state;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 身体
            g.setColor(new Color(255, 197, 66));
            g.fillOval(8, 12, 80, 72);

            // 耳朵
            g.fillOval(12, 0, 24, 30);
            g.fillOval(60, 0, 24, 30);

            // 眼睛会稍微朝移动方向偏移。
            int eyeOffset = state.getSpeedX() > 0 ? 3 : -3;
            g.setColor(new Color(50, 45, 35));
            g.fillOval(29 + eyeOffset, 38, 9, 13);
            g.fillOval(58 + eyeOffset, 38, 9, 13);

            // 嘴巴
            g.drawArc(37, 45, 24, 19, 0, -180);
        } finally {
            g.dispose();
        }
    }
}
