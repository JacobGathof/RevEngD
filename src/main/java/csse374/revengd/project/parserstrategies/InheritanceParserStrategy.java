package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.InheritanceRelationUMLObject;
import csse374.revengd.project.umlobjects.InterfaceUMLObject;
import soot.Scene;
import soot.SootClass;

import java.util.ArrayList;
import java.util.List;

public class InheritanceParserStrategy implements IParserStrategy {
    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();
        clazz.getInterfaces().forEach(i ->{
            IUMLObject inter = new InterfaceUMLObject(i);
            IUMLObject obj = new InheritanceRelationUMLObject(clazz, i);
            umlObjects.add(obj);
            umlObjects.add(inter);
            dependencies.add(i);
        });
        return umlObjects;
    }
}
