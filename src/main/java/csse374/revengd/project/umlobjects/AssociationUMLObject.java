package csse374.revengd.project.umlobjects;

import soot.SootClass;

public class AssociationUMLObject implements IUMLObject {
    SootClass source;
    SootClass reference;
    boolean manyToOne;

    public AssociationUMLObject(SootClass source, SootClass reference, boolean manyToOne){
        this.source = source;
        this.reference = reference;
        this.manyToOne = manyToOne;
    }

    @Override
    public String toUML() {
        return source.toString() + " --> " + cleanName(reference.toString());
    }

    private String cleanName(String className){
        if(className.endsWith("[]")){
            return cleanName(className.substring(0, className.length() - 2));
        }
        return className;
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
