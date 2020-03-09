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


## Main todo list
Add in features to support multi-user projects, which needs:

	- An integrated "IM-lite" system, for users to communicate
	- A peer-to-peer file editing protocol, for allowing users to edit the same file simultaneously
	- Some sort of log system for this?
	- Possible git support?
	
Also will need line numbers. Really needs line numbers.

	- Support line wrap.
	- Also support no line wrap.
	- And be able to add in breakpoints per line. Configurable, so gdb can be used with it.
	
Add in syntax highlighting.

	- Support for definitions for different languages. As a bare minimum, support GLSL, C, and C++. Maybe Java.
	- Support for dynamically loading in new ones.

Add in a new, proper theming engine

	- Support dark mode!!!!
	- Support nicely colored widgets
	
Proper unit testing

	- Test util classes, incorporate things, etc.
	
Integration of command line from within the editor

	- Allow running shell scripts, should just have something like bash launched for every query.
	- Support different kinds of shells, like SQL?
	- Themed separately (or not) from main editor
	
Options to allow launching QEMU/VirtualBox/docker/etc from within the editor

	- The entire point of repurposing this editor is to make writing code for Feral Waypoint easier.
	
Integrate a file explorer, profiler, some other IDE-lite features, etc.

	- Should be a very powerful tool for writing code with, something comparable to Atom/VSCodium without 1000GB of RAM usage.
	
Consider a C++ source port with Qt?

	- Really shouldn't be needing a ton of RAM.