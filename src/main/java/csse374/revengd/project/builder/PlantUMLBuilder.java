package csse374.revengd.project.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;
import net.sourceforge.plantuml.graph2.Plan;

public class PlantUMLBuilder implements IBuilder {
    boolean full;

    public PlantUMLBuilder(boolean full){
        this.full = full;
    }

    @Override
    public String build(List<IUMLObject> objects) {
        StringBuilder builder = new StringBuilder();
        builder.append("@startuml\n");
        builder.append("skinparam class {\n" +
                "\tBorderColor<<Singleton>> blue\n" +
                "}\n");
        
        for(IUMLObject s : objects) {
        	builder.append(s.toUML(full) + "\n");
        }

        builder.append("@enduml");
        return builder.toString();
    }
}
