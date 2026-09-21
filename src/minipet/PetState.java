package minipet;

import java.awt.Rectangle;

/**
 * 只保存桌宠的“状态”，不负责画图，也不负责窗口。
 */
public class PetState {
    public static final int SIZE = 96;

    private int x;
    private int y;
    private int speedX;
    private int speedY;

    public PetState(PetSettings settings) {
        speedX = settings.getSpeedX();
        speedY = settings.getSpeedY();
        x = settings.getStartX();
        y = settings.getStartY();
    }

    /**
     * 让桌宠移动一步；如果碰到屏幕边缘，就反转相应方向的速度。
     */
    public void advance(Rectangle screenBounds) {
        x += speedX;
        y += speedY;

        int rightEdge = screenBounds.x + screenBounds.width - SIZE;
        int bottomEdge = screenBounds.y + screenBounds.height - SIZE;

        if (x < screenBounds.x || x > rightEdge) {
            speedX = -speedX;
            x = Math.max(screenBounds.x, Math.min(x, rightEdge));
        }

        if (y < screenBounds.y || y > bottomEdge) {
            speedY = -speedY;
            y = Math.max(screenBounds.y, Math.min(y, bottomEdge));
        }
    }

    /** 左键点击后调用：向相反方向移动。 */
    public void reverseDirection() {
        speedX = -speedX;
        speedY = -speedY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeedX() {
        return speedX;
    }
}
