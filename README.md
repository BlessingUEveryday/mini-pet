# Mini Pet

[Chinese](README.zh-CN.md)

A small desktop pet application built with Java Swing. It is a practical learning project for exploring a layered Java application, Maven, resource loading, mouse events, and automated tests.

## Features

- Displays a transparent, always-on-top desktop pet window.
- Moves continuously and bounces at screen edges.
- Uses a bundled character image with transparent background.
- Left-click the pet to reverse its movement direction.
- Drag the pet with the left mouse button; it stays within the visible screen bounds.
- Right-click the pet to open an **Exit** menu. Movement pauses while the menu is open.
- Provides a system tray icon with **Show Pet**, **Hide Pet**, and **Exit** actions.
- Loads the initial position and movement speed from a properties file.
- Includes JUnit tests for the movement and boundary rules.

## Requirements

- JDK 25
- Maven 3.9 or newer

Check that both Maven and the Java compiler use JDK 25:

```powershell
java -version
javac -version
mvn -version
```

## Build, Test, and Run

Clone the repository and enter its directory:

```powershell
git clone https://github.com/BlessingUEveryday/mini-pet.git
cd mini-pet
```

Run the automated tests:

```powershell
mvn test
```

Build a runnable JAR and launch the pet on Windows:

```powershell
mvn package
java -jar target\mini-pet-1.0.0-SNAPSHOT.jar
```

To stop the pet, right-click it and choose **Exit**, or use the system tray menu.

## Configuration

On first launch, Mini Pet copies its bundled default configuration to:

```text
C:\Users\<your-user-name>\.mini-pet\pet.properties
```

Edit this external file, then restart the pet for changes to take effect:

```powershell
notepad "$env:USERPROFILE\.mini-pet\pet.properties"
```

```properties
speed.x=1
speed.y=1
start.x=120
start.y=120
```

- `speed.x` and `speed.y` must be positive whole numbers.
- `start.x` and `start.y` define the initial window position in pixels.
- The bundled [pet.properties](src/main/resources/config/pet.properties) is only the default template used when no user configuration exists.

## Project Structure

```text
src/
├── main/
│   ├── java/minipet/          Application source code
│   └── resources/
│       ├── assets/            Pet images
│       └── config/            Default settings
└── test/
    └── java/minipet/          JUnit tests
```

| Class | Responsibility |
| --- | --- |
| `Main` | Loads settings and starts the Swing user interface. |
| `PetWindow` | Owns the window, timer, context menu, and mouse interaction. |
| `PetState` | Stores position and velocity, and applies movement rules. |
| `PetPanel` | Loads and draws the pet image. |
| `PetSettings` | Reads and validates `pet.properties`. |

## Learning Focus

This project is intentionally small, but it uses real project conventions:

- Maven standard directories and dependency management
- Classpath resources instead of hard-coded local file paths
- Separation of application state, UI rendering, and window interaction
- JUnit tests for non-UI logic
- Git branches and pull requests for incremental development
