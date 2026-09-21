package minipet;

import javax.swing.SwingUtilities;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        PetSettings settings = PetSettings.load();

        SwingUtilities.invokeLater(() -> {
            PetWindow petWindow = new PetWindow(settings);
            PetTray.install(petWindow);
            petWindow.showPet();
        });
    }
}
