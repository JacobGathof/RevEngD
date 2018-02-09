# CSSE374-05 Software Design Project - Team Booyakashah
### Trevor Morton, John McClary, Jake Gathof



### About the project
To run the project, the only command line argument should be a path to a settings file to use. 
        java project.jar C:/path/settings.prop
        
Settings files should define all of the options with which to render the diagram. 
The available options are 
1. path
2. classes
3. filters
4. strategies
5. builder
6. displayer
7. detectors
8. blacklist
9. whitelist
10. command

Each argument must be a comma-separated fully-qualified class or package name. An example of how to format the settings file is provided below. 


path=C:/Users/joeschmo/Software Design/Lab7-1/bin
classes=problem.client.EnumerationAdapter
filters=csse374.revengd.project.parsers.filters.PackageParserFilter
strategies=csse374.revengd.project.parserstrategies.AssociationParserStrategy
builder=csse374.revengd.project.builder.PlantUMLBuilder
displayer=csse374.revengd.project.displayer.PlantDisplayer
detectors=csse374.revengd.project.parsers.detectors.AdapterDetector
blacklist=sun,java
whitelist=java.util.Enumeration,java.util.Iterator


## Team Contribution
All members contributed to the repo in commits, and were present during design decisions.

Jack -  worked on the Filters, and command line parsing that was originally used. 
        edited the IUMLObject and classes that implement it to include what is necessary for most filters.
        Adjusted the UML diagram of our project to reflect our final design in milestone 1.
        
Jake -  worked on the IUMLObjects and original sourceParser. 
        Set up the building and displaying of the UML diagrams
        

Trevor - Worked on MasterParser, main method, command line arguments.  Worked on original UML diagram and designed filters.  Created workaround to avoid using SceneBuilder so it isn't necessary

![UML-Diagram](https://ada.csse.rose-hulman.edu/Booyakashah/RevEngD/blob/master/Release/UML.svg)