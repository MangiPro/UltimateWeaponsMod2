name: Build Mod Jar

on:
  push:
    branches: [ "**" ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Repository auschecken
        uses: actions/checkout@v4

      - name: JDK 21 einrichten
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 21

      - name: Gradle einrichten
        uses: gradle/actions/setup-gradle@v4
        with:
          gradle-version: 8.13

      - name: Mod bauen
        run: gradle build --stacktrace

      - name: Fertiges Jar hochladen
        uses: actions/upload-artifact@v4
        with:
          name: UltimateWeaponsMod-jar
          path: build/libs/*.jar
