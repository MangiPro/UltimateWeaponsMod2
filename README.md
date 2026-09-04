# Ultimate Weapons Mod – Minecraft 1.21.11 (Fabric)

Vollstaendige Neuentwicklung des Mods gegen die fuer Minecraft 1.21.11
bestaetigte Fabric-/Yarn-API (Stand September 2026). 1.21.11 ist ein grosser
Sprung gegenueber 1.21.1: u.a. neues Item-Modell-System (assets/.../items/),
neues Ruestungs-/Equipment-System (ArmorMaterial + equipment/*.json statt
Layer-Texturen direkt am Item), Codec-basierte Saved-Data (statt NBT-Methoden)
und sehr viele umbenannte Klassen/Methoden, da Yarn sich in seiner letzten
Ausbaustufe stark an die offiziellen Mojang-Namen angeglichen hat
(z.B. `Item.Settings` -> `Item.Properties`, `PlayerEntity` -> `Player`,
`World` -> `Level`, `StatusEffect` -> `MobEffect`, `postHit` -> `hurtEnemy`).

## Fertiges Jar OHNE eigene Java/Gradle-Installation bekommen (GitHub Actions)

Das Projekt enthaelt bereits eine fertige Workflow-Datei
(`.github/workflows/build.yml`), die den Mod automatisch fuer dich baut,
sobald du den Code zu GitHub hochlaedst - komplett ohne Java oder Gradle auf
deinem eigenen Rechner.

1. Gehe auf https://github.com/new und erstelle ein neues (auch "Private"
   moeglich) Repository, z.B. "UltimateWeaponsMod".
2. Auf der leeren Repo-Seite auf "uploading an existing file" klicken.
3. **Alle Dateien und Ordner** aus diesem entpackten Zip per Drag&Drop dort
   hineinziehen (wichtig: den Inhalt des Ordners `UltimateWeaponsMod`, nicht
   den Ordner selbst, sonst landet alles eine Ebene zu tief).
4. Unten auf "Commit changes" klicken.
5. Oben im Repo auf den Reiter "Actions" klicken. Dort startet automatisch
   ein Lauf namens "Build Mod Jar" (dauert ca. 3-6 Minuten).
6. Ist der Lauf fertig (gruener Haken), draufklicken, ganz unten bei
   "Artifacts" auf "UltimateWeaponsMod-jar" klicken - das laedt eine ZIP mit
   der fertigen `.jar`-Datei herunter.
7. Die `.jar` (nicht die `-sources.jar`, falls beide drin sind) in deinen
   Fabric-`mods`-Ordner kopieren.

Falls der Actions-Lauf mit einem roten X fehlschlaegt: auf den Lauf klicken,
den Log-Ausschnitt (insbesondere "error:" / "cannot find symbol") kopieren
und mir schicken - dann kann ich den Code gezielt fixen.

## Bauen (alternativ lokal, falls du Java 21 installiert hast)

Voraussetzung: JDK 21 (z.B. Adoptium Temurin 21) installiert und im PATH.

Im Projektordner ausfuehren:

```
./gradlew build          # Linux/macOS
gradlew.bat build        # Windows
```

Der Gradle-Wrapper laedt beim ersten Aufruf automatisch die passende
Gradle-Version sowie Minecraft/Fabric/Yarn herunter (Internetverbindung
noetig, dauert beim ersten Mal ein paar Minuten).

Das fertige Mod-Jar liegt danach hier:

```
build/libs/ultimateweapons-2.0.0.jar
```

Diese Datei (NICHT die `-sources.jar`) einfach in den `mods`-Ordner deiner
Fabric-Minecraft-1.21.11-Installation kopieren. Fabric Loader + Fabric API
(passend zu 1.21.11, siehe gradle.properties fuer die genaue Version) muessen
dort ebenfalls installiert sein.

## Was wurde umgesetzt

- Ultimate Sword/Axe/Mace/Spear + Netherite Spear (Basis-Item) mit allen
  geforderten Verzauberungen (ueber die Crafting-Rezepte).
- Ultimate Helmet/Chestplate/Leggings/Boots mit Netherite-Werten +
  "solange getragen"-Effekten (Haste 10 + Regeneration 1 / +10 Herzen /
  Resistance 2 + Saturation / Speed 3).
- Ultimate Spear: +2 Bloecke Reichweite (7 statt 5), solange in der Hand.
- Ultimate Mace: alle 10 Treffer -> 2s Betaeubung (Bewegung + Blickrichtung
  eingefroren) + Blitzeinschlag beim Ziel.
- Alle acht Ultimate-Items sind unzerstoerbar und nur EIN einziges Mal pro
  Welt craftbar (persistenter Weltzustand via `SavedData`); beim ersten
  Craften erscheint eine rote Chat-Nachricht im gesamten Server.

## Wichtiger Hinweis zur Zuverlaessigkeit

Ich habe die komplette API gegen die offizielle Fabric-Dokumentation und die
Yarn-Mapping-Historie fuer 1.21.11 geprueft (docs.fabricmc.net/1.21.11/...,
mappings.dev), kann den Code in dieser Sandbox aber **nicht tatsaechlich
kompilieren** (kein Netzwerkzugriff auf die Minecraft-/Fabric-Maven-Server
zum Herunterladen der Abhaengigkeiten). Die grossen strukturellen Aenderungen
(Item-Registrierung, Ruestungs-/Equipment-API, Saved-Data, Mob-Effekte,
Mixin-Ziele) sind alle direkt aus der 1.21.11-Dokumentation bzw. den
1.21.11-Mappings belegt. Bei ein paar sehr tief verschachtelten Detail-Paketen
(z.B. genaue Package-Pfade einzelner neuerer Klassen) bin ich auf plausible,
aber nicht 100%ig verifizierte Annahmen angewiesen.

**Falls `./gradlew build` einen Fehler wirft:** Bitte die Fehlermeldung
(insbesondere "cannot find symbol" / "package does not exist") hierher
kopieren – das laesst sich dann gezielt und schnell fixen.

## Platzhalter-Texturen

Da die hochgeladenen PNGs Rezept-Diagramme (Screenshots) und keine
Spiel-Texturen waren, habe ich einfache eigene Pixel-Art-Platzhalter fuer
alle Items sowie eine schlichte Ruestungs-Textur erzeugt. Diese sind bewusst
minimalistisch gehalten – am einfachsten ersetzt man sie durch eigene
16x16/32x32-PNGs unter `src/main/resources/assets/ultimateweapons/textures/`.
