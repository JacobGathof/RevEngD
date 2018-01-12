package csse374.revengd.project.builder;

import java.util.HashSet;
import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;

public class SequenceDiagramBuilder implements IBuilder {

	@Override
    public String build(List<IUMLObject> objects) {
    	
    	/*HashSet<String> objSet = new HashSet<String>();
    	 for(IUMLObject obj : objects) {
         	objSet.add(obj.toUML());
         }*/
    	
        StringBuilder builder = new StringBuilder();
        builder.append("@startuml\n");
        for (int i = objects.size()-1; i >= 0; i--){
        	builder.append(objects.get(i).toUML() + "\n");
        }
        /*for(IUMLObject s : objects) {
        	builder.append(s.toUML() + "\n");
        }*/

        builder.append("@enduml");
        String temp = builder.toString();
        return builder.toString();
    }

}
