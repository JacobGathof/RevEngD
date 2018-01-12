# CSSE374-05 Software Design Project - Team Booyakashah
### Trevor Morton, John McClary, Jake Gathof


### About the project
This application can analyze a Java project and generate and display the UML diagram or sequence diagram representing the class structure. 
The project currently features several command line arguments: 

1.  -c,--classes <args>     classes to analyse
2.  -p,--path <arg>        root project path
3.  -po,--protected        protected scope
4.  -pr,--private          private scope
5.  -pu,--public           public scope
6.  -r,--recursive <arg>   recursive depth of <arg>
7.  -sd,--sequence         use sequence diagram


The command line argument should also contain a path to the root folder at the start and all of the base files you want to examine with a fully qualified name.



## Team Contribution
All members contributed to the repo in commits, and were present during design decisions.

Jack -  worked on the Filters, and command line parsing that was originally used. 
        edited the IUMLObject and classes that implement it to include what is necessary for most filters.
        Adjusted the UML diagram of our project to reflect our final design in milestone 1.
        
Jake -  worked on the IUMLObjects and original sourceParser. 
        Set up the building and displaying of the UML diagrams
        

Trevor - Worked on MasterParser, main method, command line arguments.  Worked on original UML diagram and designed filters.  Created workaround to avoid using SceneBuilder so it isn't necessary

