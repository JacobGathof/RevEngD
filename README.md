# CSSE374-05 Software Design Project - Team Booyakashah
### Trevor Morton, John McClary, Jake Gathof


### About the project
This application can analyze a Java project and generate and display the UML diagram representing the class structure. 
The project currently features 4 command line arguments: 

1. [-r] - Parse the project recursively
2. [-pu] - Only include public fields, methods, and classes
3. [-po] - Include public and protected fields, methods, and classes
4. [-pr] - Include fields, methods, and classes of any visibility


## Team Contribution
All members contributed to the repo in commits, and were present during design decisions.

Jack worked on the Filters, and command line parsing that was originally used. 
Jack also edited the IUMLObject and classes that implement it to include what is necessary for most filters.

The project is configured to use Log4J, which you can also use for your own classes. See [SceneBuilder API](/src/main/java/csse374/revengd/soot/SceneBuilder.java) for an example. 
