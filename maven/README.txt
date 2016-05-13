INFO ABOUT MAVEN POM FILES
--------------------------

This folder contains Maven POM files that can be used for different purposes. 
To make use of a particular file, copy its contents to the pom.xml file in the 
root directory.

pom-1.6.xml - builds JFreeChart without JavaFX support (jfreechart-1.0.x.jar). 
              This requires JDK 1.6 or later, and is the default.

pom-javafx.xml - builds JFreeChart including JavaFX support 
                 (jfreechart-1.5.x.jar).  This requires JDK 1.8.0_40 or later.

pom-dev.xml - a pom file for use in Maven-enabled IDEs such as NetBeans.  
              Defines the dependencies required by the demo files, and includes 
              the demo source files in the build.  This build includes JavaFX 
              support, and so requires JDK 1.8.0_40 or later.