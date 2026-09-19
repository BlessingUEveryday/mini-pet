# Mini Pet

这是一个专门用于学习 Java 桌面程序的极小桌宠工程。第一版只使用 JDK 自带的 Swing，不使用 Maven、XML、图片或第三方库。

## 第一版功能

- 显示一个透明背景的小桌宠窗口。
- 桌宠持续移动，碰到屏幕边缘会反弹。
- 左键点击桌宠会让它反向移动。
- 右键点击桌宠会退出程序。

## 文件分工

| 文件 | 作用 |
| --- | --- |
| `Main.java` | 程序入口，负责启动桌宠 |
| `PetState.java` | 保存桌宠的位置和速度，并计算下一步位置 |
| `PetPanel.java` | 负责把桌宠画出来 |
| `PetWindow.java` | 负责窗口、定时器和鼠标事件 |

## 在 PowerShell 中编译与运行

先进入本项目目录：

```powershell
cd D:\NewProject\mini-pet
```

编译：

```powershell
javac -d out src\minipet\*.java
```

运行：

```powershell
java -cp out minipet.Main
```

`out/` 是编译器自动生成的 `.class` 文件目录，不需要手动修改，也不应该提交到 Git。

## 第一次阅读顺序

1. `Main.java`
2. `PetWindow.java` 的构造方法
3. `PetState.java` 的 `advance` 方法
4. `PetPanel.java` 的 `paintComponent` 方法
