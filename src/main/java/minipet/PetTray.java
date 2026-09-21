package minipet;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import javax.swing.SwingUtilities;

public final class PetTray {
    private PetTray() {
    }

    public static void install(PetWindow petWindow) {
        if (!SystemTray.isSupported()) {
            return;
        }

        PopupMenu menu = new PopupMenu();

        MenuItem showItem = new MenuItem("Show Pet");
        showItem.addActionListener(
                event -> SwingUtilities.invokeLater(petWindow::showPet)
        );

        MenuItem hideItem = new MenuItem("Hide Pet");
        hideItem.addActionListener(
                event -> SwingUtilities.invokeLater(petWindow::hidePet)
        );

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(
                event -> SwingUtilities.invokeLater(petWindow::exitPet)
        );

        menu.add(showItem);
        menu.add(hideItem);
        menu.add(exitItem);

        TrayIcon trayIcon = new TrayIcon(
                PetImageLoader.loadPetImage(),
                "Mini Pet",
                menu
        );
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(
                event -> SwingUtilities.invokeLater(petWindow::showPet)
        );

        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException exception) {
            System.err.println(
                    "Cannot add system tray icon: " + exception.getMessage()
            );
        }
    }
}
