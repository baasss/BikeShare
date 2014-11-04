lunareclipse
============
Steps to Download and Run
1. Downlod the complete code from github using 'Clone in Desktop'.
2. Create a maven project in eclipse.
    org.apache.maven.archetypes -->  maven.archetype.webapp
    Group ID --> com.mkyong.common
    artifactId --> cmpe273project
    version --> 0.1.0
3. Copy all src and pom.xml to the project.
4. Do mavan build, give 'package' as goal in configuration.
5. A war file will be created under target folder in projects workspace.
6. copy and paste the war file in webapps folder of 'apache-tomcat' server.