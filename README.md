# FreedomWriter 2020
A fork of some project I made in high school,
to make it usage as a replacement for something like VS Codium.

A lot of the codebase is pretty bad compared to what I'd make now,
so probably something like 80% of it has to be thrown out the window.
But some of this stuff is good, and a perfectly fine foundation.

Specifically, all the UI code should be (re)built in a nice MVC architecture,
some unnecessary classes should be pruned, and some really bad code using
Scanners should be removed and replaced with proper file I/O.


## Compiling
You'll need maven, and Java 8.
Run `mvn package` to build the current snapshot,
and it will appear in the target/ directory.

You can then run it using `java -jar target/*.jar`, or
more specifically depending on how it is named.