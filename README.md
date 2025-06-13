# Projekt: Batching vs Direkte Ausführung

## Projektbeschreibung
Performancegewinn durch Batching: Untersuchung, wie sich batched Queries (mehrere Inserts/Selects in einer Query) auf die Performance auswirken

### Statische Gegebenheiten
Umgebung:

- VM mit dedicated CPU im Rechenzentrum
- Debian Betriebssystem
- HSQLDB läuft im Docker-Container openjdk:19-jdk-alpine3.16
- Testprogramme werden lokal auf dem Server ausgeführt

### Tests

Lesen

- je 10 Einzelabfragen in eine Batchabfrage bündeln

Schreiben

- je 10 Inserts in eine Batchabfrage bündeln

Modifizieren

- je 10 Updates in eine Batchabfrage bündeln


### Sonstige Notizen

- logarithmisch hochgehen mit Testmenge




- Schreibtests:
    - 5 Minuten: Wie viele Batched (50 requests auf einmal) vs Einzelrequests koennen jeweils abgefertigt werden von mehreren Instanzen die auf die Datenbank gleichzeitig zugreifen?
