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
        
        for(IUMLObject s : objects) {
            if(s != null) {
                builder.append(s.toUML(full) + "\n");
            }
        }

        builder.append("@enduml");
        return builder.toString();
    }
}
