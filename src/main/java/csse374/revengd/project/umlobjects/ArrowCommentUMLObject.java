package csse374.revengd.project.umlobjects;

import soot.SootClass;

import java.util.List;

public class ArrowCommentUMLObject implements IUMLObject{
    IUMLObject inner;
    String comment;

    public ArrowCommentUMLObject(IUMLObject inner, String comment){
        this.inner = inner;
        this.comment = comment;
    }

    @Override
    public String toUML(boolean full) {
        String innerUML = inner.toUML(full);
        return innerUML + " : " + comment;
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
