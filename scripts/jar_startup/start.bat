@echo off

REM Imposta la directory del progetto alla cartella padre di scripts\jar_startup
cd ..\..
set PROJECT_DIR=%cd%

REM Debug per verificare la directory del progetto
echo Directory del progetto: %PROJECT_DIR%

REM Trova l'ultimo JAR file generato nella cartella dist che inizia con "infoquiz-"
for /f "delims=" %%i in ('dir /b /o-d "%PROJECT_DIR%\dist\infoquiz-*.jar"') do set JAR_FILE=%%i

REM Verifica (ed esegue) se il JAR file esiste
if exist "%PROJECT_DIR%\dist\%JAR_FILE%" (
    echo Eseguendo JAR file: %JAR_FILE%
    java -jar "%PROJECT_DIR%\dist\%JAR_FILE%"
) else (
    echo Errore: JAR file non trovato nella cartella dist.
)
