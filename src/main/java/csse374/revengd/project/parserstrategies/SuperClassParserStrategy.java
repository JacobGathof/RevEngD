package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.*;
import soot.Scene;
import soot.SootClass;

import java.util.ArrayList;
import java.util.List;

public class SuperClassParserStrategy implements IParserStrategy{
    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();

        if(clazz.isInterface()){
            umlObjects.add(new InterfaceUMLObject(clazz));
        }
        else if(clazz.isAbstract()){
            umlObjects.add(new AbstractClassUMLObject(clazz));
        }
        else{
            umlObjects.add(new ClassUMLObject(clazz));
        }

        if(clazz.hasSuperclass()) {
            dependencies.add(clazz.getSuperclass());
            umlObjects.add(new ClassUMLObject(clazz.getSuperclass()));
            umlObjects.add(new SuperclassUMLObject(clazz, clazz.getSuperclass()));
        }
        return umlObjects;
    }
}
