# JJSON

JJSON is a small Java library for working with JSON. It provides a simple JSON
object model, a parser for JSON strings, and annotation-based conversion between
POJOs and JSON.

This software was originally developed for
[Time & Bill](https://timeandbill.de/), a time tracking application. JJSON is now
in maintenance mode because Time & Bill has migrated to Spring.

Maintenance mode means that JJSON receives security updates, dependency updates,
build and release fixes, and compatibility fixes for existing behavior. New
features are unlikely.

## Modules

- [jjson](jjson/README) contains the core JSON model and annotation mapper.
- [jjson-struts2](jjson-struts2/README) contains a legacy Struts 2 result type
  for returning JSON responses.

## Maven Coordinates

Core library:

```xml
<dependency>
  <groupId>de.grobmeier.json</groupId>
  <artifactId>jjson</artifactId>
  <version>0.1.8</version>
</dependency>
```

Struts 2 plugin:

```xml
<dependency>
  <groupId>de.grobmeier.json</groupId>
  <artifactId>jjson-struts2</artifactId>
  <version>0.0.10</version>
</dependency>
```

## Project Status

The Java JSON ecosystem is mature. Jackson, Gson, Moshi, Jakarta JSON-B, and
Jakarta JSON-P are better choices for most new projects.

JJSON can still be useful for small legacy Java projects, existing users of the
`de.grobmeier.json:jjson` coordinates, and applications that need a compact JSON
object model with stable historical behavior.

## Build

```bash
mvn test
```

Release helper:

```bash
scripts/release.sh help
```
