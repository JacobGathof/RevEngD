package csse374.revengd.project.umlobjects;

import soot.SootClass;

import java.util.List;

public class MethodColorerUMLObject implements IUMLObject{
    IUMLObject inner;
    String color;

    public MethodColorerUMLObject(IUMLObject inner, String color){
        this.inner = inner;
        this.color = color;
    }

    @Override
    public String toUML(boolean full) {
        String innerResult = inner.toUML(full);
        String[] parts = innerResult.split(":");
        if(parts.length > 2){
            return innerResult;
        }
        else{
            return parts[0] + ": <font color=\"" + color + "\">" + parts[1];
        }
    }

    @Override
    public int getModifiers() {
        return inner.getModifiers();
    }

    @Override
    public SootClass getSootClass() {
        return inner.getSootClass();
    }

    @Override
    public List<String> getPackage() {
        return inner.getPackage();
    }
}
