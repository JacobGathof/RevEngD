package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.InstanceVariableUMLObject;
import soot.Scene;
import soot.SootClass;

import java.util.ArrayList;
import java.util.List;

public class InstanceVariableParserStrategy implements IParserStrategy {
    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();
        clazz.getFields().forEach(f->{
            IUMLObject obj = new InstanceVariableUMLObject(clazz, f);
            umlObjects.add(obj);
        });
        return umlObjects;
    }
}
