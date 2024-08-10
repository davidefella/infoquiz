#!/bin/bash

# Imposta la directory del progetto alla cartella padre di scripts/dist_packaging
PROJECT_DIR=$(cd "$(dirname "$0")/../.." && pwd)

# Debug per verificare la directory del progetto
echo "Directory del progetto: $PROJECT_DIR"

# Vai alla directory del progetto
cd "$PROJECT_DIR" || exit

# Rimuovi tutti i file JAR esistenti nella cartella dist
rm -f "$PROJECT_DIR/dist"/*.jar

# Esegui il comando Maven clean package
./mvnw clean package

# Verifica se il comando Maven è stato eseguito con successo
if [ $? -eq 0 ]; then
    echo "Build completata con successo."
else
    echo "Errore durante la build."
    exit 1
fi