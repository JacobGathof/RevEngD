package csse374.revengd.project.umlobjects;

import soot.SootClass;
import soot.SootField;

public class InstanceVariableUMLObject implements IUMLObject{
   
	private SootClass source;
	private SootField target;
	
	public InstanceVariableUMLObject(SootClass source, SootField target) {
		this.source = source;
		this.target = target;
	}
	
    @Override
    public String toUML() {
        return source.getName() + " : " + target.getDeclaration();
    }

	@Override
	public int getModifiers() {
		return target.getModifiers();
	}
	
	@Override
	public SootClass getSootClass() {
		return source;
	}

}