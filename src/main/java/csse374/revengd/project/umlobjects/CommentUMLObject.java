package csse374.revengd.project.umlobjects;

import java.util.List;

import soot.SootClass;

public class CommentUMLObject implements IUMLObject {

	IUMLObject inner;

    public CommentUMLObject(IUMLObject inner){
        this.inner = inner;
    }

    @Override
    public String toUML(boolean full) {
        String innerUML = inner.toUML(full);
        return  "'" + innerUML;
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
