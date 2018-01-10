package csse374.revengd.project.umlobjects;

import soot.SootClass;

import java.awt.*;

public class DependencyUMLObject implements IUMLObject{
    SootClass source;
    SootClass reference;

    public DependencyUMLObject(SootClass source, SootClass reference){
        this.source = source;
        this.reference = reference;
    }

    @Override
    public String toUML() {
        if(reference.toString().endsWith("[]")){
            return source.toString() + " --> " + reference.toString().substring(0, reference.toString().length() - 2);
        }
        return source.toString() + " --> " + reference.toString();
    }

    @Override
    public int getModifiers() {
        return source.getModifiers();
    }

    @Override
    public SootClass getSootClass() {
        return source;
    }
}
