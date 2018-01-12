package csse374.revengd.project.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;

public class PlantUMLBuilder implements IBuilder {
    @Override
    public String build(List<IUMLObject> objects) {
    	
        HashSet<String> objSet = new HashSet<String>();
        for(IUMLObject obj : objects) {
            objSet.add(obj.toUML());
        }
        HashSet<String> newObjSet = (HashSet<String>)objSet.clone();
        for(String obj : objSet){
            if(obj.contains(" --* \"1..*\" ")){
                String[] comps = obj.split(" --\\* \"1\\.\\.\\*\" ");
                if(newObjSet.contains(comps[0] + " --> \"1..*\" " + comps[1])){
                    newObjSet.remove(comps[0] + " --> \"1..*\" "+ comps[1]);
                }
            }
            else if(obj.contains(" --* ")){
                String[] comps = obj.split(" --\\* ");
                if(newObjSet.contains(comps[0] + " --> " + comps[1])){
                    newObjSet.remove(comps[0] + " --> " + comps[1]);
                }
            }
        }
    	
        StringBuilder builder = new StringBuilder();
        builder.append("@startuml\n");
        
        for(String s : newObjSet) {
        	builder.append(s + "\n");
        }

        builder.append("@enduml");
        return builder.toString();
    }
}
