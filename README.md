# Supensour
![Maven Central](https://img.shields.io/maven-central/v/com.supensour/supensour?color=blue&label=Maven%20Central&logo=apache-maven&logoColor=ff0090)
![Build](https://github.com/supensour/supensour/workflows/Build/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.supensour%3Asupensour&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.supensour%3Asupensour)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.supensour%3Asupensour&metric=coverage)](https://sonarcloud.io/dashboard?id=com.supensour%3Asupensour)

## Table of Contents
- [Table of Contents](#table-of-contents)
- [About](#about)
- [Add Dependency](#add-dependency)
- [Features](#features)

## About
Supensour is a spring-boot based multi-module library.

## Add Dependency
1. Import supensour's parent pom inside your pom.xml, under `project/dependencyManagement/dependencies`,
as a dependency so that its modules can be imported without version.
By doing this, version conflicts can be avoided.
```xml
<project>
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>com.supensour</groupId>
        <artifactId>supensour</artifactId>
        <version>${supensour.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
</project>
```
2. Import needed module(s)\
For example if supensour-core is needed:
```xml
<project>
  <dependencies>
    <dependency>
      <groupId>com.supensour</groupId>
      <artifactId>supensour-core</artifactId>
    </dependency>
  </dependencies>
</project>
```

## Features
Coming soon~
