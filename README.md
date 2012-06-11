glee_visualize
==============

This project has two parts: a Java preprocessor and a Javascript based
visualization using Protovis.

The java preprocessor takes in a txt file containing a list of coffeescript files. I usually just generate one using:
```find `pwd` -name "*.coffee" -not -name "*.spec.coffee" > coffeelist.txt```

The preprocessor will take two command line arguments:
```java Processor path_to_out_from_previous_command path_to_new_json_file```

If you don't specify an input file, it will bring up a file browser.
If you don't specify an output file (in addition to an input file), it will print its output to the terminal, so it's easiest to just pipe it into a file:
```java Processor > counts.js```

Once you've got that file, you can load up the visualization (the counts.js file needs to be in the same directory as the index.hmtl).

Note: It won't work just running locally (file access issues). It needs a localhost (I just put a glee_visualize folder in my Iron/client/public directory).

To-do:
* Restructure so that the javascript isn't just inside of a script tag in the html.
* Add inbound vs outbound references instead of just outbound
* Replace Java preprocessor with a Node-based version
