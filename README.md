
This is a **console-based Hangman game**, where the player tries to guess a hidden word by entering one letter at a time.  
The word is chosen randomly based on the selected **difficulty level** and **category** from a predefined list.  
The number of attempts is limited, and with each incorrect guess, a new part of the hangman figure is drawn.

---

### Project Overview

This is a standard **Java project** built using the **Apache Maven** build automation tool.

The project consists of the following directories and files:

- **`pom.xml`** – Maven build descriptor (Project Object Model).  
  It defines project dependencies and build configuration steps.

- **`app-java/src/`** – Directory containing the application’s source code and tests:
  - **`app-java/src/main`** – contains the main application source code
  - **`app-java/src/test`** – contains the application’s test code

- **`mvnw`** and **`mvnw.cmd`** – Maven Wrapper scripts for Unix and Windows,  
  allowing you to run Maven commands without a local installation.

- **`pmd.xml`** and **`spotbugs-excludes.xml`** – configuration files for code quality tools (linters).  
  They define rules for **PMD** and **SpotBugs** used in the project.

- **`.mvn/`** – service directory used by Maven, containing configuration files for the build system.

---

## ⚙️ Building the Project

To build the project and verify that everything works correctly, run:

```bash
cd app-java
mvn clean verify
mvn clean package build
```

For Unix (Linux, macOS, Cygwin, WSL):

```bash
./mvnw clean verify
```

For Windows:
```bash
mvnw.cmd clean verify
```

After a successful build, you can run the application using:
```bash
java -jar target/hangman-1.0.jar
```
To see all available commands and options:
```bash
java -jar target/hangman-1.0.jar --help
```


