package csse374.revengd.project;

import soot.SootClass;
import soot.SootMethod;

public class MethodUMLObject implements IUMLObject{
	
	private SootClass source;
	private SootMethod target;
	
	public MethodUMLObject(SootClass source, SootMethod target) {
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
