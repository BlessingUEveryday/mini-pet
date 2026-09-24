package minipet;

import java.awt.GridLayout;
import java.text.ParseException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public final class SettingsDialog {
    private SettingsDialog() {
    }

    public static void show(PetWindow petWindow) {
        PetSettings currentSettings = PetSettings.load();

        JSpinner speedXSpinner = createSpinner(
                currentSettings.getSpeedX()
        );
        JSpinner speedYSpinner = createSpinner(
                currentSettings.getSpeedY()
        );
        JSpinner startXSpinner = createSpinner(
                currentSettings.getStartX()
        );
        JSpinner startYSpinner = createSpinner(
                currentSettings.getStartY()
        );

        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.add(new JLabel("Horizontal speed (x):"));
        panel.add(speedXSpinner);
        panel.add(new JLabel("Vertical speed (y):"));
        panel.add(speedYSpinner);
        panel.add(new JLabel("Start position X:"));
        panel.add(startXSpinner);
        panel.add(new JLabel("Start position Y:"));
        panel.add(startYSpinner);

        int result = JOptionPane.showConfirmDialog(
                petWindow,
                panel,
                "Mini Pet Settings",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            PetSettings updatedSettings = PetSettings.create(
                    readSpinnerValue(speedXSpinner),
                    readSpinnerValue(speedYSpinner),
                    readSpinnerValue(startXSpinner),
                    readSpinnerValue(startYSpinner)
            );

            updatedSettings.save();
            petWindow.applySettings(updatedSettings);
        } catch (ParseException exception) {
            JOptionPane.showMessageDialog(
                    petWindow,
                    "All settings must be whole numbers.",
                    "Invalid Settings",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (IllegalStateException exception) {
            JOptionPane.showMessageDialog(
                    petWindow,
                    exception.getMessage(),
                    "Cannot Save Settings",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private static JSpinner createSpinner(int initialValue) {
        return new JSpinner(new SpinnerNumberModel(
                initialValue,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                1
        ));
    }

    private static int readSpinnerValue(JSpinner spinner)
        throws ParseException {
        spinner.commitEdit();
        return ((Number) spinner.getValue()).intValue();
    }
}
