package csse374.revengd.project;

import soot.SootClass;
import soot.SootMethod;

public class SuperclassUMLObject implements IUMLObject {

	private SootClass source;
	private SootClass target;
	
	public SuperclassUMLObject(SootClass source, SootClass target) {
		this.source = source;
		this.target = target;
	}
	
    @Override
    public String toUML() {
        return source.getName() + " <|-- " + target.getName();
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
