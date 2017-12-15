package csse374.revengd.project.umlobjects;

import java.util.List;

import soot.SootClass;

public interface IUMLObject {
    String toUML();
    public int getModifiers();
    public SootClass getSootClass();
}
