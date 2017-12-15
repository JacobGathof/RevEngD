package csse374.revengd.project.umlobjects;

import soot.SootClass;

public class InheritanceRelationUMLObject implements IUMLObject {
    SootClass child;
    SootClass parent;

    public InheritanceRelationUMLObject(SootClass c, SootClass p){
        child = c;
        parent = p;
    }


    @Override
    public String toUML() {
    	if(parent.isInterface()) {
    		return parent.getName() + " <|.. " + child.getName();
    	}
    	return parent.getName() + " <|-- " + child.getName();
    }


	@Override
	public int getModifiers() {
		return child.getModifiers();
	}


	@Override
	public SootClass getSootClass() {
		return child;
	}
}
