# Selenium Login Tests

Maven-projekt med Selenium + JUnit 5 för inloggningstester mot [saucedemo.com](https://www.saucedemo.com/).

## Köra lokalt

Krav:
- Java 17+
- Maven 3.9+
- Google Chrome installerad

Kör tester:

```bash
mvn clean test
```

Om du vill köra headless lokalt:

```bash
HEADLESS=true mvn clean test
```

## Köra via GitHub Actions

Workflow finns i `.github/workflows/maven-tests.yml` och kör automatiskt `mvn clean test` vid push och pull request.

## Ladda upp till GitHub

1. Skapa ett tomt repo på GitHub (utan README/.gitignore om möjligt).
2. Kör i projektmappen:

```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/<ditt-anvandarnamn>/<repo-namn>.git
git push -u origin main
```

Efter push: öppna fliken **Actions** i GitHub-repot för att se testkörningen.