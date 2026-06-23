# jjson

`jjson` is the core JJSON library. It is a small Java JSON library with a JSON
object model, a parser, and annotation-based POJO mapping.

This software was originally developed for
[Time & Bill](https://timeandbill.de/), a time tracking application. JJSON is now
in maintenance mode because Time & Bill has migrated to Spring.

Maintenance mode means that JJSON receives security updates, dependency updates,
build and release fixes, and compatibility fixes for existing behavior. New
features are unlikely.

## Maven Coordinates

```xml
<dependency>
  <groupId>de.grobmeier.json</groupId>
  <artifactId>jjson</artifactId>
  <version>0.1.8</version>
</dependency>
```

## Features

- Use annotations to create JSON strings from objects.
- Map JSON strings into POJOs.
- Create a Java JSON object model from a JSON string.
- Create a JSON string from the Java JSON object model.

The release artifact shades the small part of Apache Commons Lang used by the
library, so consumers do not need to manage a Commons Lang dependency for JJSON.

## Decode JSON Into A POJO

```java
JSONAnnotationDecoder decoder = new JSONAnnotationDecoder();
AnnotatedSetTestClass result =
    decoder.decode(AnnotatedSetTestClass.class, "{\"test1\":\"OK\"}");
```

`result.getTest1()` returns `OK`.

## Encode A POJO To JSON

```java
AnnotatedTestClass value = new AnnotatedTestClass();
JSONAnnotationEncoder encoder = new JSONAnnotationEncoder();
String json = encoder.encode(value);
```

The class must have `@JSON` at class level. Fields or methods that should be
included in the JSON output must also be annotated with `@JSON`. Fields should be
public or have matching getter methods.

## Object API

Create JSON manually:

```java
JSONObject object = new JSONObject();
object.put("mykey", new JSONString("myvalue"));
String json = object.toJSON();
```

This returns:

```json
{"mykey":"myvalue"}
```

Parse JSON into the object model:

```java
JSONDecoder decoder = new JSONDecoder("{\"key\":\"value\",\"key2\":{\"key3\":\"value2\"}}");
JSONValue result = decoder.decode();
String json = result.toJSON();
```

## Project Status

The Java JSON ecosystem is mature. Jackson, Gson, Moshi, Jakarta JSON-B, and
Jakarta JSON-P are better choices for most new projects.

JJSON can still be useful for small legacy Java projects, existing users of the
`de.grobmeier.json:jjson` coordinates, and applications that need a compact JSON
object model with stable historical behavior.
