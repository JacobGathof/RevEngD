package csse374.revengd.project;

import soot.SootClass;
import soot.SootMethod;

public class ClassUMLObject implements IUMLObject{
	
	private SootClass clazz;
	
	public ClassUMLObject(SootClass c) {
		this.clazz = c;
	}
	
    @Override
    public String toUML() {
        return "class " + clazz.getName();
    }
    
}
