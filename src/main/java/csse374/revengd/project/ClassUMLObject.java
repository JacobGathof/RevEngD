package csse374.revengd.project;

import soot.SootClass;
import soot.SootMethod;

public class ClassUMLObject implements IUMLObject{
	
	private SootClass source;
	private SootClass target;
	
	public ClassUMLObject(SootClass source, SootClass target) {
		this.source = source;
		this.target = target;
	}
	
    @Override
    public String toUML() {
        return source.getName() + " <|-- " + target.getName();
    }
    
}
