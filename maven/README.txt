This folder contains Maven POM files that can be used for different purposes. 
To make use of a particular file, copy it to the pom.xml file in the root directory.

pom-1.6.xml - builds jfreechart-1.0.x.jar using JDK 1.6 (excludes JavaFX):

pom-1.8.xml - builds jfreechart-fx-1.0.x.jar using JDK 1.8.0_40 or later.

pom-nb.xml - a pom file for use in NetBeans.  Defines the dependencies required
by the demo files, and includes the demo source files in the build.