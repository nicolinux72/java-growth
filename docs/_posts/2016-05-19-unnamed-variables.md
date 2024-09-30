---
layout: post
title: Unnamed variables and patterns
categories: misc
author: Nicola Santi
JEPs: [456, 431]
references:
    - title: "title"
      url: "www.google.com"   
---


Programming in Java, it has happened to everyone to declare variables that were never used just because the compiler requested it. 

Starting with JDK 22, we can save ourselves the trouble by replacing the name of the variable, or the entire pattern, with an underscore ‘_’, as is already possible in other languages: *blank identifier* in __golang__, *throwaway variables* in __python__ or even, from the earliest versions, in __kotlin__ or __ruby__.

For example, in the catch block where we do not use the exception:

```java
 void exceptionSideEffect(String s) {
        try {
            float pi = Float.parseFloat(s);
        } catch (NumberFormatException _) { // variable not used
            println("Bad number: %s",s);
        }
    }
```

Also in the try-catch blocks, for resources to be closed automatically but not used directly in our code:  

```java
public class CloseableClass implements AutoCloseable {
    @Override public void close()  {
        println("close method called");
    }
}

void tryWithResourceSideEffect() {
    try (var _ = new CloseableClass()) {
        println("closeableClass instance is never used but still automatically closed ");
    }
}
```

In the destructuring of a record, when we are only interested in certain fields:

```java
enum Color { RED, GREEN, BLUE }
record Point(int x, int y) { }
record ColoredPoint(Point p, Color c) {}

void decustruction(Object object) {
    if (object instanceof ColoredPoint(Point(int x, int y),  _)) {
        //Color in never used
        println("Coordinates x: %d, y: %d",x, y);
    }
}
```

During stream processing:

```java
void otherUnusedVariable1() {
    var list = List.of("XC234", "XC254", "XC764");
    var map = list.stream().collect(toMap(k -> k.substring(2), _ -> "Not Interested"));
    println(map);
}
```

Finally, iterating through the collections:

```java
int otherUnusedVariable2() {
    Iterable<String> iter = List.of("XC234", "XC254", "XC764");
    int total = 0;
    for ( var _: iter) { total++; } //items are not used
    return total;
}
```
