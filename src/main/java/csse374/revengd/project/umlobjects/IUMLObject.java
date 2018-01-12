package csse374.revengd.project.umlobjects;

import java.util.List;

import soot.SootClass;

public interface IUMLObject {
    String toUML(boolean full);
    public int getModifiers();
    public SootClass getSootClass();
    public List<String> getPackage();
    
}
