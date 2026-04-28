# Intralogistics Simulation

## Projektbeschreibung

Dieses Projekt simuliert den ersten Teil eines Lagersystems.
Im aktuellen Stand wird ein **Multishuttle-Lager für Euroboxen** modelliert.

Das Lager besteht aus mehreren Gassen, Ebenen, Lagerfächern, Hebern und Shuttles.
Die Behälter können eingelagert und wieder ausgelagert werden.

---

## Aktueller Stand

Der erste Teil des Projekts konzentriert sich auf das Multishuttle-Lager.

Weitere Bereiche wie:

* Kommissionierbereich
* Versandbereich
* Transportbereich

sind als nächste Schritte geplant.

---

## Projektstruktur

* **Warehouse**: repräsentiert die Struktur des Lagers und enthält alle Gassen (Aisles)
* **StorageService**: enthält die Einlagerungslogik (Storage-Strategien, Prioritäten, Auftragslogik)
* **RetrievalService**: enthält die Auslagerungslogik inklusive Umlagerung bei zweifach tiefen Lagerfächern
* **Aisle**: repräsentiert eine Gasse im Lager
* **Level**: repräsentiert eine Ebene innerhalb einer Gasse
* **StorageSlot**: Lagerfach mit zwei Positionen (FRONT / BACK)
* **Container**: Behälter mit ID, Typ, Priorität und optionaler Auftragsnummer
* **ContainerType**: z. B. SOURCE, ORDER oder EMPTY
* **Priority**: HIGH, MEDIUM oder LOW
* **Lift**: repräsentiert einen Heber
* **LiftType**: Einlager- oder Auslager-Heber
* **Shuttle**: Multishuttle auf einer Ebene
* **Main**: Startklasse des Programms

---

## Lageraufbau

Das Multishuttle-Lager ist für folgende Struktur vorgesehen:

* 4 Gassen
* 10 Ebenen pro Gasse
* Je Gasse ein Einlager-Heber
* Je Gasse ein Auslager-Heber
* Je Gasse und Ebene ein Multishuttle
* Zweifach tiefe Lagerfächer

Das bedeutet:
In jedem Lagerfach können maximal zwei Behälter stehen:

* FRONT
* BACK

---

## Behälter

Ein Behälter besitzt folgende Informationen:

* ID
* Typ
* Priorität
* Auftragsnummer (optional)

Sortenreine Behälter können ohne Auftrag eingelagert werden.
Fertig kommissionierte Behälter können einem Auftrag zugeordnet sein.

---

## Einlagerung

Bei der Einlagerung wird zuerst geprüft, ob der Behälter zu einem bestehenden Auftrag gehört.

* Wenn ja → Einlagerung möglichst in derselben Gasse und Ebene
* Wenn nein → Suche eines freien Lagerplatzes nach Strategie

---

## Einlagerstrategie

Die Einlagerung berücksichtigt:

* Gleichmäßige Verteilung über Gassen
* Nähe von Behältern mit gleicher Auftragsnummer
* Priorität der Behälter

Prioritätslogik (vereinfachtes Modell):

* **HIGH** → bevorzugt niedrige Ebenen (nahe am Auslagerbereich)
* **MEDIUM** → mittlere Ebenen
* **LOW** → höhere Ebenen (weiter entfernt)

Hinweis:
Die Nähe zum Auslagerheber ist vereinfacht modelliert.

---

## Zweifach tiefe Lagerfächer

Jedes Lagerfach besitzt zwei Plätze:

* FRONT
* BACK

Regeln:

* Wenn FRONT frei → dort einlagern
* Wenn FRONT belegt & BACK frei → hinten einlagern
* Wenn beide belegt → Fach ist voll

---

## Auslagerung

Ein Behälter kann über seine ID ausgelagert werden.

* FRONT → direkte Auslagerung
* BACK → FRONT muss zuerst entfernt werden

Ablauf:

1. FRONT wird temporär entfernt
2. BACK wird ausgelagert
3. FRONT wird wieder eingelagert

    * zuerst am ursprünglichen Ort
    * sonst an neuem Platz

---

## Beispielablauf

* Erstellung mehrerer Behälter
* Einlagerung (inkl. Auftragslogik)
* Auslagerung eines Behälters
* Ausgabe der Ergebnisse in der Konsole

---

## Start

Das Programm wird über die Klasse `Main` gestartet.
