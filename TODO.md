# 1️⃣ EJECUTAR SOLO TESTS
./gradlew test

# 2️⃣ EJECUTAR TESTS + GENERAR REPORTE JACOCO
./gradlew test jacocoTestReport

# 3️⃣ VER REPORTE JACOCO (HTML)
# Abrir: build/reports/jacoco/html/index.html

# 4️⃣ VERIFICAR COBERTURA MÍNIMA
./gradlew jacocoTestCoverageVerification

# 5️⃣ GENERAR REPORTE ALLURE
./gradlew test allureReport

# 6️⃣ ABRIR REPORTE ALLURE EN EL NAVEGADOR
./gradlew allureServe

# 7️⃣ EJECUTAR ANÁLISIS DE SONARQUBE
./gradlew sonarqube

# 8️⃣ TODO EN UNO (tests + reportes + sonar)
./gradlew clean test jacocoTestReport sonarqube

# Abrir en navegador
open build/reports/jacoco/html/index.html  # Mac/Linux
start build/reports/jacoco/html/index.html # Windows

# Instalar Allure CLI primero (una sola vez)
# Mac: brew install allure
# Windows: scoop install allure
# Linux: sudo apt-get install allure

# Generar y abrir reporte
./gradlew allureServe

# 1. Iniciar SonarQube (Docker)
docker start <container_id>

# 2. Abrir navegador
http://localhost:9000

# 3. Login (primera vez)
# Usuario: admin
# Password: admin (te pedirá cambiarla)

# Si ya tienes el container
docker start <tu_container_sonarqube>

# Si necesitas crear uno nuevo
docker run -d --name sonarqube \
  -p 9000:9000 \
  -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true \
  sonarqube:latest


name: CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

jobs:
  build-and-test:
    name: Build, Test & Quality Analysis
    runs-on: ubuntu-latest

    steps:
      # 1️⃣ Checkout del código
      - name: 📥 Checkout code
        uses: actions/checkout@v4
        with:
          fetch-depth: 0  # Para análisis de SonarQube

      # 2️⃣ Configurar Java 17
      - name: ☕ Setup Java 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: gradle

      # 3️⃣ Dar permisos a gradlew
      - name: 🔐 Grant execute permission for gradlew
        run: chmod +x gradlew

      # 4️⃣ Compilar proyecto
      - name: 🏗️ Build with Gradle
        run: ./gradlew build -x test

      # 5️⃣ Ejecutar tests
      - name: 🧪 Run Unit Tests
        run: ./gradlew test

      # 6️⃣ Generar reporte JaCoCo
      - name: 📊 Generate JaCoCo Coverage Report
        run: ./gradlew jacocoTestReport

      # 7️⃣ Verificar cobertura mínima
      - name: ✅ Verify Code Coverage
        run: ./gradlew jacocoTestCoverageVerification

      # 8️⃣ Subir reporte JaCoCo como artifact
      - name: 📤 Upload JaCoCo Coverage Report
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: jacoco-report
          path: build/reports/jacoco/html/

      # 9️⃣ Generar reporte Allure
      - name: 🎨 Generate Allure Report
        if: always()
        run: ./gradlew allureReport

      # 🔟 Subir reporte Allure
      - name: 📤 Upload Allure Report
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: allure-report
          path: build/reports/allure-report/

      # 1️⃣1️⃣ Análisis de SonarQube
      - name: 🔍 SonarQube Analysis
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
          SONAR_HOST_URL: ${{ secrets.SONAR_HOST_URL }}
        run: ./gradlew sonarqube

      # 1️⃣2️⃣ Publicar resultados de tests
      - name: 📋 Publish Test Results
        uses: EnricoMi/publish-unit-test-result-action@v2
        if: always()
        with:
          files: |
            build/test-results/**/*.xml

      # 1️⃣3️⃣ Comentar en PR con cobertura
      - name: 💬 Comment PR with Coverage
        if: github.event_name == 'pull_request'
        uses: madrapps/jacoco-report@v1.6.1
        with:
          paths: build/reports/jacoco/jacoco.xml
          token: ${{ secrets.GITHUB_TOKEN }}
          min-coverage-overall: 70
          min-coverage-changed-files: 60

  # 🚀 Job de Deploy (opcional)
  deploy:
    name: Deploy to Production
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main' && github.event_name == 'push'

    steps:
      - name: 🚀 Deploy Application
        run: echo "Deploying to production..."
        # Aquí irían los pasos reales de deploy
```

### 5.2 Configurar Secrets en GitHub

1. Ve a tu repositorio en GitHub
2. Settings → Secrets and variables → Actions
3. Crear nuevos secrets:
```
SONAR_TOKEN = tu_token_de_sonarqube
SONAR_HOST_URL = http://tu-servidor-sonar:9000

# Si SonarQube está en localhost, necesitas SonarCloud o un servidor público
# Alternativa: Usar SonarCloud (gratis para proyectos open source)
# https://sonarcloud.io/