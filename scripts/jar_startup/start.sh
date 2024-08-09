#!/bin/bash

# Imposta la directory del progetto alla cartella padre di startup_scripts
# $ per accedere a variabili o eseguire comandi.
# $0 è una variabile speciale che rappresenta il nome del programma/script
# pwd: Restituisce il percorso assoluto della directory corrente.
PROJECT_DIR=$(cd "$(dirname "$0")/../.." && pwd)


# debug per verificare la directory del progetto
echo "Directory del progetto: $PROJECT_DIR"


# Trova l'ultimo JAR file generato nella cartella target che inizia con "infoquiz-"
# /target/infoquiz-*.jar: path relativa all'interno della directory del progetto,
# che cerca tutti i file .jar
# 2>/dev/null qualsiasi errore sarà ignorato e non verrà mostrato in console.
# Il pipe | è usato per passare l'output di un comando come input per il comando successivo
JAR_FILE=$(ls "$PROJECT_DIR/dist/infoquiz-"*.jar 2>/dev/null | sort -r | head -n 1)


# Verifica (ed esegue) se il JAR file esiste
if [[ -f "$JAR_FILE" ]]; then
    echo "Eseguendo JAR file: $JAR_FILE"
    chmod +x "$JAR_FILE"  # Assicurati che il JAR sia eseguibile
    java -jar "$JAR_FILE"
else
    echo "Errore: JAR file non trovato nella cartella target."
fi