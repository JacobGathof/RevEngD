package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.ClassUMLObject;
import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.InheritanceRelationUMLObject;
import csse374.revengd.project.umlobjects.SuperclassUMLObject;
import soot.Scene;
import soot.SootClass;

import java.util.ArrayList;
import java.util.List;

public class SuperClassParserStrategy implements IParserStrategy{
    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();
        if(clazz.hasSuperclass()) {
            dependencies.add(clazz.getSuperclass());
            umlObjects.add(new ClassUMLObject(clazz.getSuperclass()));
            umlObjects.add(new SuperclassUMLObject(clazz, clazz.getSuperclass()));
        }
        return umlObjects;
    }
}
