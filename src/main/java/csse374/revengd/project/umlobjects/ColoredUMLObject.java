package csse374.revengd.project.umlobjects;

import soot.SootClass;

import java.util.List;

public class ColoredUMLObject implements IUMLObject {
    IUMLObject inner;
    String color;

    public ColoredUMLObject(IUMLObject inner, String color){
        this.inner = inner;
        this.color = color;
    }

    @Override
    public String toUML(boolean full) {
        return inner.toUML(full) + " #" + color;
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
