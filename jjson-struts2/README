# jjson-struts2

`jjson-struts2` is a legacy Struts 2 plugin that provides a result type for
returning JSON responses using JJSON.

This software was originally developed for
[Time & Bill](https://timeandbill.de/), a time tracking application. JJSON is now
in maintenance mode because Time & Bill has migrated to Spring.

Maintenance mode means that this module receives security updates, dependency
updates, build and release fixes, and compatibility fixes for existing behavior.
New features are unlikely.

## Maven Coordinates

```xml
<dependency>
  <groupId>de.grobmeier.json</groupId>
  <artifactId>jjson-struts2</artifactId>
  <version>0.0.10</version>
</dependency>
```

## Configure The Result Type

Add the `JsonResult` result type to your Struts 2 package:

```xml
<result-types>
  <result-type name="json" class="de.grobmeier.json.plugins.struts2.JsonResult"/>
</result-types>
```

Then use it from an action:

```xml
<action name="doSomething" class="de.grobmeier.DoSomething">
  <result type="json"/>
</action>
```

## Annotate The Action

Everything that should appear in the JSON response must be annotated with
`@JSON`, including the action class.

```java
@JSON
class DoSomething {
    @JSON
    public String myString = "test";

    @JSON
    public String myString2 = "test2";

    public String getMyString() {
        return myString;
    }

    public String getMyString2() {
        return myString2;
    }
}
```

This returns:

```json
{"myString":"test","myString2":"test2"}
```

You can also annotate methods when the returned value is created on the fly:

```java
@JSON
class DoSomething {
    @JSON
    public String value() {
        return "test";
    }
}
```

This returns:

```json
{"value":"test"}
```

## Project Status

This module is maintenance-only. Struts 2 applications should keep Struts and all
transitive dependencies current, because old Struts versions have had serious
security vulnerabilities.

For new applications, prefer the JSON support in your current web framework
rather than starting with this legacy plugin.
