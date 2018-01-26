package csse374.revengd.project.umlobjects;

import soot.SootClass;

import java.util.List;

public class StereotypeUMLObject implements IUMLObject{
    IUMLObject inner;
    String stereotype;

    public StereotypeUMLObject(IUMLObject inner, String stereotype){
        this.inner = inner;
        this.stereotype = stereotype;
    }

    @Override
    public String toUML(boolean full) {
        String innerUML = inner.toUML(full);
        return innerUML + " <<" + stereotype + ">>";
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
