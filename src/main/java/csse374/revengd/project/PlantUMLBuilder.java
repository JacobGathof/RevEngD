package csse374.revengd.project;

import java.util.HashSet;
import java.util.List;

public class PlantUMLBuilder implements IBuilder {
    @Override
    public String build(List<IUMLObject> objects) {
    	
    	HashSet<String> objSet = new HashSet<String>();
    	 for(IUMLObject obj : objects) {
         	objSet.add(obj.toUML());
         }
    	
        StringBuilder builder = new StringBuilder();
        builder.append("@startuml\n");
        
        for(String s : objSet) {
        	builder.append(s + "\n");
        }
        
        System.out.println(builder.toString());
        
        builder.append("@enduml");
        return builder.toString();
    }
}
