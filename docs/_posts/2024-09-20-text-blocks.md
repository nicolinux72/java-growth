---
layout: post
title: Record
categories: misc
author: Nicola Santi
JEPs: [378]
references:
    - title: Text Blocks (JEP 378)
      url: https://javaalmanac.io/features/textblocks/
---
Text blocks, which are already present in many languages, make it possible to express literal
strings over several lines, so where carriage returns ‘\n’ do not have to be escaped. They are very convenient.

```java
var block = """
        row 1
         row 2
          row 3
        """;
```
All spaces preceding the **first** line are automatically removed. Thus, the string *block* here
above is equal to:

```java
var sameAs = "row 1\n row 2\n  row 3\n";
```
Again for the sake of convenience, any spaces at the end of the line are also removed:

```java
var block = """
    row 1
    row 2  """;

var sameAs = "row 1\nrow 2";
```
If, on the other hand, these spaces are necessary, they can be preserved by escaping ‘\s'
```java
var block = """
    row 1
    row 2  \s
    """;

var sameAs = "row 1\nrow 2   \n";
```
Finally, it is possible to ignore carriage returns in the literal, as is done, for example, in bash scripts:
```java
var block = """
        row 1 \
        same row 1
        """;
var sameAs = "row 1 same row 1\n";
```
