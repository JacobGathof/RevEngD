package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.IUMLObject;
import soot.Scene;
import soot.SootClass;

import java.util.List;

public interface IParserStrategy {
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v);
}
