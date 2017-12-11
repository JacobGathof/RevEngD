package csse374.revengd.project;

import java.util.List;

public class PlantUMLBuilder implements IBuilder {
    @Override
    public String build(List<IUMLObject> objects) {
        StringBuilder builder = new StringBuilder();
        builder.append("@startuml\n");
        
        for(IUMLObject obj : objects) {
        	builder.append(obj.toUML() + "\n");
        }
        
        builder.append("@enduml");
        return builder.toString();
    }
}
