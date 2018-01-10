package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.MethodUMLObject;
import soot.Scene;
import soot.SootClass;

import java.util.ArrayList;
import java.util.List;

public class MethodParserStrategy implements IParserStrategy {
    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();
        clazz.getMethods().forEach(m->{
            IUMLObject obj = new MethodUMLObject(clazz, m);
            umlObjects.add(obj);
        });
        return umlObjects;
    }
}
