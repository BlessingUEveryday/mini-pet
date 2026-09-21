# Mini Pet

[English](README.md)

一个使用 Java Swing 编写的小型桌宠程序。它是一个面向实践的学习项目，用来理解分层 Java 工程、Maven、资源加载、鼠标事件和自动化测试。

## 功能

- 显示一个透明、始终置顶的桌宠窗口。
- 桌宠持续移动，碰到屏幕边缘时反弹。
- 使用项目内置的透明背景角色图片。
- 左键单击桌宠可使它反向移动。
- 按住左键拖拽桌宠；它会保持在可见屏幕范围内。
- 右键单击会打开包含 **Exit** 的菜单；菜单打开期间桌宠会暂停移动。
- 从属性文件中读取初始位置和移动速度。
- 使用 JUnit 测试移动和边界规则。

## 环境要求

- JDK 25
- Maven 3.9 或更新版本

确认 Maven 与 Java 编译器都在使用 JDK 25：

```powershell
java -version
javac -version
mvn -version
```

## 构建、测试与运行

克隆仓库并进入项目目录：

```powershell
git clone https://github.com/BlessingUEveryday/mini-pet.git
cd mini-pet
```

运行自动化测试：

```powershell
mvn test
```

在 Windows 上编译并启动桌宠：

```powershell
mvn compile
java -cp target\classes minipet.Main
```

要关闭桌宠，请右键单击它并选择 **Exit**。

## 配置

在编译前编辑 [pet.properties](src/main/resources/config/pet.properties)，可以修改默认值：

```properties
speed.x=1
speed.y=1
start.x=120
start.y=120
```

- `speed.x` 和 `speed.y` 必须是正整数。
- `start.x` 和 `start.y` 代表桌宠窗口初始位置的像素坐标。

## 工程结构

```text
src/
├── main/
│   ├── java/minipet/          程序源码
│   └── resources/
│       ├── assets/            桌宠图片
│       └── config/            默认配置
└── test/
    └── java/minipet/          JUnit 测试
```

| 类 | 职责 |
| --- | --- |
| `Main` | 读取配置并启动 Swing 用户界面。 |
| `PetWindow` | 管理窗口、计时器、右键菜单与鼠标交互。 |
| `PetState` | 保存位置和速度，并执行移动规则。 |
| `PetPanel` | 加载并绘制桌宠图片。 |
| `PetSettings` | 读取并校验 `pet.properties`。 |

## 学习重点

这个项目规模很小，但使用了真实工程中的常见做法：

- Maven 标准目录结构与依赖管理
- 使用 classpath 资源，而不是写死本机文件路径
- 分离程序状态、界面绘制和窗口交互
- 为非界面逻辑编写 JUnit 测试
- 使用 Git 分支和 Pull Request 逐步开发功能

