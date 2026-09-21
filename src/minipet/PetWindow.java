package minipet;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * 负责桌宠窗口、定时更新与鼠标交互。
 */
public class PetWindow extends JWindow {
    private static final int FRAME_DELAY_MS = 16;

    private final PetState state;
    private final Rectangle screenBounds;
    private final Timer timer;

    private int dragOffsetX;
    private int dragOffsetY;
    private boolean dragging;
    private boolean movedDuringDrag;

    public PetWindow(PetSettings settings) {
        state = new PetState(settings);
        screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .getBounds();

        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(PetState.SIZE, PetState.SIZE);
        PetPanel panel = new PetPanel(state);
        add(panel);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    showContextMenu(panel, event);
                } else if (SwingUtilities.isLeftMouseButton(event)) {
                    dragOffsetX = event.getX();
                    dragOffsetY = event.getY();
                    dragging = true;
                    movedDuringDrag = false;
                    timer.stop();
                }
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                if (!dragging) {
                    return;
                }

                movedDuringDrag = true;

                int newX = event.getXOnScreen() - dragOffsetX;
                int newY = event.getYOnScreen() - dragOffsetY;

                state.setPosition(newX, newY, screenBounds);
                setLocation(state.getX(), state.getY());
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                if (!SwingUtilities.isLeftMouseButton(event) || !dragging) {
                    return;
                }

                if (!movedDuringDrag) {
                    state.reverseDirection();
                }
                dragging = false;
                timer.start();
            }
        };

        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);

        timer = new Timer(FRAME_DELAY_MS, event -> updatePet());
    }

    public void showPet() {
        setLocation(state.getX(), state.getY());
        setVisible(true);
        timer.start();
    }

    private void updatePet() {
        state.advance(screenBounds);
        setLocation(state.getX(), state.getY());
        repaint();
    }

    private void stopAndExit() {
        timer.stop();
        dispose();
        System.exit(0);
    }

    private void showContextMenu(PetPanel panel, MouseEvent event) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem changeColorItem = new JMenuItem("Change Color");
        changeColorItem.addActionListener(actionEvent -> panel.changeToNextColor());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(actionEvent -> stopAndExit());

        menu.add(changeColorItem);
        menu.add(exitItem);

        menu.show(panel, event.getX(), event.getY());
    }
}
