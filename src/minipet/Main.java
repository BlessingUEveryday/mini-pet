package minipet;

import javax.swing.SwingUtilities;

/**
 * 程序入口。
 *
 * Java 程序从 main 方法开始运行，作用和 C++ 的 int main() 类似。
 */
public final class Main {
    private Main() {
        // 这个类只负责启动程序，不需要创建 Main 对象。
    }

    public static void main(String[] args) {
        // Swing 窗口相关代码应该在 Swing 的界面线程中启动。
        SwingUtilities.invokeLater(() -> {
            PetWindow petWindow = new PetWindow();
            petWindow.showPet();
        });
    }
}
