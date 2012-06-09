glee_visualize
==============

This project has two parts: a Java preprocessor and a Javascript based
visualization using Protovis.

The java preprocessor takes in a txt file containing a list of
coffeescript files. I usually just generate one using:
```find `pwd` -name "*.coffee" -not -name "*.spec.coffee" > coffeelist.txt```

It will print its output to the terminal, so it's easiest to just pipe
it into a file:
```java Processor > counts.js```

Once you've got that file, you can load up the visualization (the
counts.js file needs to be in the same directory as the index.hmtl).

Note: It won't work just running locally (file access issues). It needs
a localhost (I just put a glee_visualize folder in my Iron/client/public
directory).

To-do:
* Restructure so that the javascript isn't just inside of a script tag in the html.
* Add command line arguments to processor so you don't have to browse to import or pipe to export
* Add inbound vs outbound references instead of just outbound
* Replace Java preprocessor with a Node-based version
